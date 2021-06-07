package io.github.akiart.fantasia.common.entity.passive;

import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

public class PtarmiganEntity extends AnimalEntity implements IAnimatable {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);

    private AnimationFactory factory = new AnimationFactory(this);
    public float destPos;
    private BlockPos jukeboxPosition;
    private boolean partyParrot;

    public PtarmiganEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    @OnlyIn(Dist.CLIENT)
    public void setRecordPlayingNearby(BlockPos pos, boolean isPartying) {
        this.jukeboxPosition = pos;
        this.partyParrot = isPartying;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isPartying() {
        return this.partyParrot;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.jukeboxPosition == null || !this.jukeboxPosition.closerThan(this.position(), 3.46D) || !this.level.getBlockState(this.jukeboxPosition).is(Blocks.JUKEBOX)) {
            this.partyParrot = false;
            this.jukeboxPosition = null;
        }

        this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);

    }

    private static final String WALK_ANIM = "animation.fantasia:ptarmigan.walk";
    private static final String IDLE_ANIM = "animation.fantasia:ptarmigan.idle2";
    private static final String FLY_ANIM = "animation.fantasia:ptarmigan.flying";

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.getAnimatable() instanceof PtarmiganEntity) {

            AnimationBuilder builder = new AnimationBuilder();
            PtarmiganEntity bird = (PtarmiganEntity) event.getAnimatable();
            if (bird.onGround) {
                if(isPartying()) {
                    builder.addAnimation("animation.fantasia:ptarmigan.jam", true);
                }
                else if (isWalking(event)) {
                    builder.addAnimation(WALK_ANIM, true);
                } else {
                    builder.addAnimation(IDLE_ANIM, true);
                }
            } else {
                builder.addAnimation(FLY_ANIM, true);
            }
            event.getController().setAnimation(builder);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> boolean isWalking(AnimationEvent<E> event) {
        return event.getLimbSwingAmount() > 0.15f || event.getLimbSwingAmount() < -0.15f;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
