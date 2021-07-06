package io.github.akiart.fantasia.common.item.itemType.frostWork;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.projectile.FrostworkPickaxeProjectileEntity;
import io.github.akiart.fantasia.common.entity.projectile.IcicleEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Set;

public class FrostworkPickaxeItem extends PickaxeItem {

    int explosionTimer = 0;
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

    @Override
    public void releaseUsing(ItemStack itemStack, World world, LivingEntity userEntity, int duration) {
        if (userEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) userEntity;

            if (getUseDuration(itemStack) - duration >= 10) {

                if (!world.isClientSide) {
                    damageItem(itemStack, userEntity, player);
                    FrostworkPickaxeProjectileEntity projectile = new FrostworkPickaxeProjectileEntity(world);
                    projectile.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 2.5F, 1.0F);
                    projectile.setPower(8);
                    world.addFreshEntity(projectile);
                    Fantasia.LOGGER.info("spawned projectile");
                }
            }
        }
    }

    private void damageItem(ItemStack itemStack, LivingEntity userEntity, PlayerEntity player) {
        itemStack.hurtAndBreak(1, player, (entity) -> {
            entity.broadcastBreakEvent(userEntity.getUsedItemHand());
        });
    }
}
