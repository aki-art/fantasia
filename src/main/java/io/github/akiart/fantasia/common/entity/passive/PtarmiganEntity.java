package io.github.akiart.fantasia.common.entity.passive;

import com.google.common.collect.Maps;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.ai.behaviour.BehaviourManager;
import io.github.akiart.fantasia.common.entity.ai.behaviour.EggLayerBehaviour;
import io.github.akiart.fantasia.common.entity.ai.behaviour.PartyBehaviour;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.lib.GeckoLibExtension.IBasicAnimatable;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Map;

public class PtarmiganEntity extends AnimalEntity implements IBasicAnimatable, IFlyingAnimal {

    public static final String ID = "ptarmigan";
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    private static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(PtarmiganEntity.class, DataSerializers.INT);
    public static final Map<Integer, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        map.put(0, new ResourceLocation(Fantasia.ID, "textures/entity/ptarmigan/ptarmigan_white.png"));
        map.put(1, new ResourceLocation(Fantasia.ID, "textures/entity/ptarmigan/ptarmigan_brown.png"));
    });

    private final AnimationFactory animationFactory;
    private BehaviourManager<PtarmiganEntity> behaviourManager;
    private PartyBehaviour partyGoer;

    public PtarmiganEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);

        this.noCulling = true;
        this.animationFactory = new AnimationFactory(this);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
        registerBehaviours();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setRecordPlayingNearby(BlockPos pos, boolean isPartying) {
        partyGoer.setJuke(pos);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        PtarmiganEntity entity = FEntities.PTARMIGAN.get().create(world);
        if (entity != null && mate instanceof PtarmiganEntity) {
            entity.setColor(random.nextBoolean() ? getColor() : ((PtarmiganEntity) mate).getColor());
        }

        return entity;
    }

    @Override
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT nbt) {
        data = super.finalizeSpawn(world, difficulty, reason, data, nbt);
        setRandomColor();
        return data;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        behaviourManager.tick();
    }

    public void animateIdle(AnimationBuilder builder) {
        if (!partyGoer.isActive() && Math.random() < 0.3D)
            builder.addAnimation("animation.fantasia:ptarmigan.idle2", false);
    }

    public <E extends IAnimatable> boolean isMoving(AnimationEvent<E> event) {
        return (event.getLimbSwingAmount() > 0.15f || event.getLimbSwingAmount() < -0.15f) && getDeltaMovement().lengthSqr() > 0.1D;
    }

    public <E extends IAnimatable> PlayState locomotionAnimPredicate(AnimationEvent<E> event) {
        if (event.getAnimatable() instanceof PtarmiganEntity) {

            AnimationBuilder builder = new AnimationBuilder();

            if (isOnGround()) {
                if (event.isMoving())
                    builder.addAnimation("animation.fantasia:ptarmigan.walk", true);
                else animateIdle(builder);

                if (partyGoer.isActive()) {
                    builder.addAnimation("animation.fantasia:ptarmigan.jam", true);
                }

            } else builder.addAnimation("animation.fantasia:ptarmigan.flying", true);

            event.getController().setAnimation(builder);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "locomotionController", 0, this::locomotionAnimPredicate));
     }

    public boolean isFood(ItemStack item) {
        return TEMPTATION_ITEMS.test(item);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        // goalSelector.addGoal(3, new AvoidEntityGoal<>(this, StoatEntity.class, 6.0F, 1.0D, 1.2D));
        goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    private void registerBehaviours() {
        behaviourManager = new BehaviourManager<>(this);
        behaviourManager.add(new EggLayerBehaviour(6000, 12000, FItems.PTARMIGAN_EGG.get()));
        partyGoer = new PartyBehaviour();
        behaviourManager.add(partyGoer);
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public boolean causeFallDamage(float f1, float f2) {
        return false;
    }

    @Override
    public String getID() {
        return ID;
    }

    public int getColor() {
        return entityData.get(DATA_TYPE_ID);
    }

    public void setColor(int color) {
        entityData.set(DATA_TYPE_ID, color);
    }

    public void setRandomColor() {
        setColor(random.nextInt(TEXTURE_BY_TYPE.size()));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TYPE_ID, 1);
    }

    public ResourceLocation getResourceLocation() {
        return TEXTURE_BY_TYPE.getOrDefault(getColor(), TEXTURE_BY_TYPE.get(0));
    }

    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("PtarmiganColor", this.getColor());
    }

    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.setColor(nbt.getInt("PtarmiganColor"));
    }

    @Override
    public ResourceLocation getTexture() {
        return getResourceLocation();
    }
}
