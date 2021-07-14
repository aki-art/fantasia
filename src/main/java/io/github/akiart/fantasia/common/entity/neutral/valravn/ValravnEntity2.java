package io.github.akiart.fantasia.common.entity.neutral.valravn;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.FTameableEntity;
import io.github.akiart.fantasia.common.entity.ai.brain.FMemoryModuleTypes;
import io.github.akiart.fantasia.lib.GeckoLibExtension.IBasicAnimatable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ResourceLocation;
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

// Experimental, messing with Brains
public class ValravnEntity2 extends FTameableEntity implements IBasicAnimatable {

    public static final String ID = "valravn2";
    private final AnimationFactory factory = new AnimationFactory(this); // GeckoLib

    protected static final ImmutableList<SensorType<? extends Sensor<? super ValravnEntity2>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.HURT_BY
    );

    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            FMemoryModuleTypes.CANT_REACH_FLIGHT_TARGET_SINCE.get(),
            FMemoryModuleTypes.FLIGHT_TARGET.get(),
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.LIVING_ENTITIES,
            MemoryModuleType.VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.UNIVERSAL_ANGER,
            MemoryModuleType.AVOID_TARGET,
            MemoryModuleType.ADMIRING_ITEM,
            MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM,
            MemoryModuleType.ADMIRING_DISABLED,
            MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM,
            MemoryModuleType.HUNTED_RECENTLY,
            MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
            MemoryModuleType.NEAREST_REPELLENT,
            MemoryModuleType.ATE_RECENTLY
    );


    public ValravnEntity2(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new FlyingMovementController(this, 5, false);
    }

    public ValravnEntity2(World world) {
        this(FEntities.VALRAVN.get(), world);
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, world);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    // AI

    @Override
    public Brain<ValravnEntity2> getBrain() {
        return (Brain<ValravnEntity2>) super.getBrain();
    }

    @Override
    protected Brain.BrainCodec<ValravnEntity2> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return ValravnBrain.makeBrain(brainProvider().makeBrain(dynamic));
    }

    @Override
    protected void customServerAiStep() {
        level.getProfiler().push("valravnBrain");
        getBrain().tick((ServerWorld) level, this);
        level.getProfiler().pop();
       //  ValravnTasks.updateActivity(this);
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPacketSender.sendEntityBrain(this);
    }

    // not breedable
    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity parent) {
        return null;
    }

    // riding
    // it is primarily the kidnapped victims who "ride" this entity

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public boolean canRiderInteract() {
        return false;
    }

    @Override
    public boolean canBeRiddenInWater(Entity rider) {
        return true;
    }

    // Misc

    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        return !this.isLeashed();
    }

    // Geckolib stuff

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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "locomotionController", 0, this::animPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public ResourceLocation getModel() {
        return new ResourceLocation(Fantasia.ID, "geo/valravn.geo.json");
    }

    @Override
    public ResourceLocation getAnimation() {
        return new ResourceLocation(Fantasia.ID, "animations/valravn.animation.json");
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Fantasia.ID, "textures/entity/valravn/black.png");
    }
}
