package io.github.akiart.fantasia.common.item.itemType;

import io.github.akiart.fantasia.common.entity.projectile.PtarmiganEggEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class PtarmiganEggItem extends Item {

    // Chicken eggs are hardcoded to spawn chickens only, so just making a custom one here
    public PtarmiganEggItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

        if (!world.isClientSide) {
            PtarmiganEggEntity eggentity = new PtarmiganEggEntity(world, player);
            eggentity.setItem(itemstack);
            eggentity.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 1.5F, 1.0F);
            world.addFreshEntity(eggentity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.abilities.instabuild) {
            itemstack.shrink(1);
        }

        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }
}

