package io.github.akiart.fantasia.common.item.itemType;

import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.projectile.CavingRopeAnchorEntity;
import io.github.akiart.fantasia.common.entity.projectile.CavingRopeAnchorThrownEntity;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class CavingAnchorBlockItem extends Item {

    public CavingAnchorBlockItem(Item.Properties properties) {
        super(properties);
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
    public int getUseDuration(ItemStack item) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, World world, LivingEntity userEntity, int duration) {
        if (userEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) userEntity;

            if (getUseDuration(itemStack) - duration >= 10) {

                if (!world.isClientSide) {
                    spawnJavelinEntity(itemStack, world, player);

                    if (!player.abilities.instabuild) {
                        player.inventory.removeItem(itemStack);
                    }
                }
            }
        }
    }

    protected void spawnJavelinEntity(ItemStack itemStack, World world, PlayerEntity player) {
        CavingRopeAnchorEntity entity =  new CavingRopeAnchorEntity(world, player, itemStack);
        entity.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 2.5F, 1.0F);

        entity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;

        world.addFreshEntity(entity);
        world.playSound(null, entity, SoundEvents.TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public UseAction getUseAnimation(ItemStack item) {
        return UseAction.CROSSBOW;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return (stack.getItem() instanceof CavingAnchorBlockItem && enchantment == Enchantments.LOYALTY) || enchantment.category.canEnchant(stack.getItem());
    }


//    @Override
//    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
//        ItemStack itemstack = player.getItemInHand(hand);
//
//        if (!world.isClientSide) {
//            CavingRopeAnchorEntity anchor = new CavingRopeAnchorEntity(world, player);
//            anchor.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 2.5F, 1.0F);
//            world.addFreshEntity(anchor);
//        }
//
//        if (!player.abilities.instabuild) {
//            itemstack.shrink(1);
//        }
//
//        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WITCH_THROW, SoundCategory.NEUTRAL, 1.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
//
//        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
//    }
}
