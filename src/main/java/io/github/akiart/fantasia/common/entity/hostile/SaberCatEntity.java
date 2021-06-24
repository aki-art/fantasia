package io.github.akiart.fantasia.common.entity.hostile;

import com.google.common.collect.Sets;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FMountableEntity;
import io.github.akiart.fantasia.lib.GeckoLibExtension.IBasicAnimatable;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
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
import java.util.Set;
import java.util.function.Predicate;

public class SaberCatEntity extends FMountableEntity implements IBasicAnimatable {
    public static final String ID = "saber_cat";
    private static final Set<Item> TAME_FOOD = Sets.newHashSet(Items.PORKCHOP); // todo: use raw meat tag
    private final AnimationFactory factory = new AnimationFactory(this);

    public SaberCatEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity entity) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if(event.isMoving())
            builder.addAnimation("animation.fantasia:saber_cat.walk", false);
        else
        builder.addAnimation("animation.fantasia:saber_cat.idle", false);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private float standAnimO = 2f;

    @Override
    public void positionRider(Entity rider) {
        super.positionRider(rider);

        if (rider instanceof MobEntity) {
            MobEntity mobentity = (MobEntity) rider;
            this.yBodyRot = mobentity.yBodyRot;
        }

        if (this.standAnimO > 0.0F) {
            float f3 = MathHelper.sin(this.yBodyRot * ((float) Math.PI / 180F));
            float f = MathHelper.cos(this.yBodyRot * ((float) Math.PI / 180F));
            float f1 = 0.7F * this.standAnimO;
            float f2 = 0.15F * this.standAnimO;
            rider.setPos(this.getX() + (double) (f1 * f3), this.getY() + this.getPassengersRidingOffset() + rider.getMyRidingOffset() + (double) f2, this.getZ() - (double) (f1 * f));
            if (rider instanceof LivingEntity) {
                ((LivingEntity) rider).yBodyRot = this.yBodyRot;
            }
        }

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Fantasia.ID, "textures/entity/saber_cat/brown.png");
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!this.isTame() && TAME_FOOD.contains(itemstack.getItem())) {
            return tameEntity(player, itemstack, 0.1f);

        } else if (this.isTame() && this.isOwnedBy(player)) {
            mount(player);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    private ActionResultType sitEntity() {
        if (!this.level.isClientSide) {
            this.setOrderedToSit(!this.isOrderedToSit());
        }
        return ActionResultType.sidedSuccess(this.level.isClientSide);
    }
}
