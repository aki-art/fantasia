package io.github.akiart.fantasia.common.entity.neutral.valravn.deletelater;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FTameableFlyingEntity;
import io.github.akiart.fantasia.lib.GeckoLibExtension.IBasicAnimatable;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.UUID;

// wild behaviour AI overview: https://i.imgur.com/a5MhiAt.png
public class ValravnEntity extends FTameableFlyingEntity implements IBasicAnimatable, IAngerable {

    public static final String ID = "valravn";

    private int state = State.SEARCHING;
    private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
    private static final DataParameter<Integer> DATA_REMAINING_ANGER_TIME = EntityDataManager.defineId(ValravnEntity.class, DataSerializers.INT);

    private UUID persistentAngerTarget;
    private UUID persistentVictimTarget;
    private BlockPos anchorPoint = BlockPos.ZERO;
    protected boolean isCarryingVictim = false;
    boolean victimHadNoAI;
    boolean victimWasVulnerable;
    boolean victimAIWasReset = false;
    protected LivingEntity victim;


//    public boolean isPushable() {
//        return !isCarryingVictim;
//    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        addPersistentAngerSaveData(nbt);
        if (persistentVictimTarget != null) {
            nbt.putUUID("Victim", persistentVictimTarget);
        }

        nbt.putInt("AX", anchorPoint.getX());
        nbt.putInt("AY", anchorPoint.getY());
        nbt.putInt("AZ", anchorPoint.getZ());
        nbt.putInt("State", this.state);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);

        if (!level.isClientSide) {
            ServerWorld world = (ServerWorld)level;
            readPersistentAngerSaveData(world, nbt);

            if(nbt.hasUUID("Victim")) {
                persistentVictimTarget = nbt.getUUID(("Victim"));
                Entity entity = world.getEntity(persistentVictimTarget);
                if(entity instanceof LivingEntity && isEntityValidVictim((LivingEntity) entity, false)) {
                    setVictim((LivingEntity) entity);
                }
            }
        }

        if (nbt.contains("State"))
            setState(nbt.getInt("State"));

        if (nbt.contains("AX"))
            this.anchorPoint = new BlockPos(nbt.getInt("AX"), nbt.getInt("AY"), nbt.getInt("AZ"));

    }

    public ValravnEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new FlyingMovementController(this, 5, false);
    }

    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        anchorPoint = blockPosition().above(5);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, nbt);
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (!player.level.isClientSide()) {
            dropVictim();
            player.sendMessage(new StringTextComponent("State: " + state), Util.NIL_UUID);
        }
        return super.mobInteract(player, hand);
    }

    public LivingEntity getVictim() {
        return victim;
    }

    public boolean hasChosenVictim() {
        return victim != null;
    }

    public void dropVictim() {
        setVictim(null);
        unRide();
        isCarryingVictim = false;
    }

    @Override
    public void ejectPassengers() {
        for(Entity entity : getPassengers()) {
            if(entity instanceof LivingEntity) {
                resetVictimAI((LivingEntity)entity);
            }
        }
        super.ejectPassengers();
    }

    @Override
    protected void removePassenger(Entity entity) {
        if(entity instanceof LivingEntity) {
            resetVictimAI((LivingEntity)entity);
        }
        super.removePassenger(entity);
    }

    public int getState() {
        return state;
    }

    double getFollowDistance() {
        return getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    public void setVictim(@Nullable LivingEntity entity) {
        victim = entity;
        persistentVictimTarget = entity != null ? entity.getUUID() : null;
        Fantasia.LOGGER.info("set victim:");
    }

    public void pickUpVictim(LivingEntity victim) {
        //((CreatureEntity)victim).setLeashedTo(this, true);
        victim.startRiding(this);

        victimHadNoAI = ((MobEntity) victim).isNoAi();
        victimWasVulnerable = victim.isInvulnerable();
        victimAIWasReset = false;
        isCarryingVictim = true;

        victim.setInvulnerable(true);
        //((MobEntity) victim).setNoAi(true);
        ((MobEntity) victim).goalSelector.disableControlFlag(Goal.Flag.MOVE);

        victim.yRot = MathHelper.wrapDegrees(victim.yRot);
        setState(State.CARRYING_VICTIM);
    }

    protected void resetVictimAI(LivingEntity victim) {
        if (victim != null && victim.isAlive()) {
            //((MobEntity) victim).setNoAi(victimHadNoAI);
            ((MobEntity) victim).goalSelector.enableControlFlag(Goal.Flag.MOVE);
            victim.setInvulnerable(victimWasVulnerable);
        }

        Fantasia.LOGGER.info("victim AI was reset");
        victimAIWasReset = true;
    }

    public void setState(int state) {
        this.state = state;

        if (state == State.CARRYING_VICTIM) {
            if (getPassengers().size() != 0) {
                resumeCarryingVictim();
            }
        }

        Fantasia.LOGGER.info("set state: " + state);
    }

    private void resumeCarryingVictim() {
        Entity rider = getPassengers().get(0);
        if (rider instanceof LivingEntity) {
            LivingEntity victim = (LivingEntity) rider;
            if (isEntityValidVictim(victim, false)) {
                this.victim = victim;
                return;
            }
        }

        setState(State.SEARCHING);
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, world);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FLYING_SPEED, 2D)
                .add(Attributes.FOLLOW_RANGE, 30D);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Fantasia.ID, "textures/entity/valravn/black.png");
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return !isTame();
    }

    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity entity) {
        return null;
    }

    private final AnimationFactory factory = new AnimationFactory(this); // GeckoLib stuff

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "locomotionController", 0, this::animPredicate));
    }

    private <P extends IAnimatable> PlayState animPredicate(AnimationEvent<P> event) {
        AnimationBuilder builder = new AnimationBuilder();

        if (isOnGround()) {
            event.getController().setAnimation(builder.addAnimation("animation.fantasia:valravn.standing", true));
            if (!event.isMoving() && random.nextFloat() < 0.33f) {
                event.getController().setAnimation(builder.addAnimation("animation.fantasia:valravn.standing_idle", false));
            }
        } else {
            event.getController().setAnimation(builder.addAnimation("animation.fantasia:valravn.flying", true));
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public boolean isCarryingVictim() {
        return isCarryingVictim;
    }

    protected boolean isStillCarryingVictim() {
        return !isDeadOrDying() || victim != null && victim.isAlive() || getPassengers().get(0).equals(victim);
    }

    public void aiStep() {

        isCarryingVictim = isCarryingVictim && isStillCarryingVictim();

        if (!victimAIWasReset && !isCarryingVictim) {
            resetVictimAI(victim);
        } else if (isCarryingVictim) {
            victim.yRot = MathHelper.rotLerp(yRot, victim.yRot, 0.1f);
        }

        yRot = MathHelper.wrapDegrees(yRot);
        super.aiStep();
    }


    protected void reassessTameGoals() {

    }

    protected void registerWildGoals() {
        //goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new ValravnRandomFlyingGoal(this, 3D));
        goalSelector.addGoal(2, new StealBabyGoal(this, 2f));
        goalSelector.addGoal(3, new RunAwayGoal(this, 128, 3D, 4D));

        targetSelector.addGoal(2, new HurtByTargetGoal(this));
        targetSelector.addGoal(3, new FindBabyGoal(this));
        targetSelector.addGoal(4, new ResetAngerGoal<>(this, true));
    }

    protected void registerTameGoals() {
        //goalSelector.addGoal(0, new SwimGoal(this));
        //goalSelector.addGoal(5, new SitGoal(this));
        //goalSelector.addGoal(6, new RetreatGoal(this));
        //goalSelector.addGoal(7, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
        //goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));

        targetSelector.addGoal(0, new HurtByTargetGoal(this));
        targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
    }

    @Override
    protected void registerGoals() {
        if(isTame()) {
            registerTameGoals();
        }
        else registerWildGoals();
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int value) {
        entityData.set(DATA_REMAINING_ANGER_TIME, value);
    }

    @Override
    public double getPassengersRidingOffset() {
        return -0.5D;
    }

    @Override
    public boolean canBeControlledByRider() {
        return false;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        persistentAngerTarget = target;
    }

    public static boolean isEntityValidVictim(LivingEntity entity, boolean needsSky) {
        if (entity == null || entity.isDeadOrDying()) return false;

        if (!EntityPredicates.ATTACK_ALLOWED.test(entity))
            return false;

        return entity instanceof AnimalEntity &&
                entity.isBaby() &&
                (!needsSky || entity.level.canSeeSky(entity.blockPosition())) &&
                entity.getMobType() != CreatureAttribute.UNDEAD;
        // && some blacklist check
    }

    @Override
    public void startPersistentAngerTimer() {
        setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(random));
    }

    public static class State {
        public static final int COMBAT_WILD = 0,
                SEARCHING = 1,
                STALKING = 2,
                SWOOPING = 3,
                CARRYING_VICTIM = 4,
                TAME_BIRD = 5,
                TAME_KNIGHT = 6;
    }
}
