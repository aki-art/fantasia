package io.github.akiart.fantasia.common.item.itemType.frostWork;

import io.github.akiart.fantasia.common.entity.projectile.FrostworkPickaxeProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FrostworkPickaxeItem extends PickaxeItem {

    public FrostworkPickaxeItem(IItemTier itemTier, int baseDamage, float attackSpeed, Properties properties) {
        super(itemTier, baseDamage, attackSpeed, properties);
    }

    @Override
    public UseAction getUseAnimation(ItemStack item) {
        return UseAction.CROSSBOW;
    }

    @Override
    public int getUseDuration(ItemStack item) {
        return 72000;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ActionResultType result = super.useOn(context);
        if(result == ActionResultType.FAIL) {
            return use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
        }
        return result;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return ActionResult.consume(itemstack);
        }
    }

    private int getPowerForCharge(float charge) {
        return MathHelper.fastFloor(16.5f - 17.5 * Math.exp(-0.1f * (charge / 20000f)));
    }

    @Override
    public void releaseUsing(ItemStack itemStack, World world, LivingEntity userEntity, int duration) {
        if (userEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) userEntity;

            int power = getPowerForCharge(getUseDuration(itemStack));
            if (power >= 1) {

                if (!world.isClientSide) {
                    damageItem(itemStack, userEntity, player);
                    spawnExplosionController(world, player, power);
                }
            }
        }
    }

    private void spawnExplosionController(World world, PlayerEntity player, int power) {
        FrostworkPickaxeProjectileEntity projectile = new FrostworkPickaxeProjectileEntity(world);
        projectile.setPos(player.getX(), player.getY(), player.getZ());
        projectile.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 0.03F, 1.0F);
        projectile.setPower(power);
        world.addFreshEntity(projectile);
    }

    private void damageItem(ItemStack itemStack, LivingEntity userEntity, PlayerEntity player) {
        itemStack.hurtAndBreak(1, player, (entity) -> {
            entity.broadcastBreakEvent(userEntity.getUsedItemHand());
        });
    }
}
