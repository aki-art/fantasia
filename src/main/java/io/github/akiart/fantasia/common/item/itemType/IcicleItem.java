package io.github.akiart.fantasia.common.item.itemType;

import com.google.common.collect.Multimap;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.projectile.IcicleEntity;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class IcicleItem extends BlockItem {

    public IcicleItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        //tooltip.add(new TranslationTextComponent("").withStyle(TextFormatting.DARK_GRAY));
        tooltip.add(new StringTextComponent("When thrown").withStyle(TextFormatting.GRAY));
        tooltip.add(new StringTextComponent(" 3 Attack Damage").withStyle(TextFormatting.DARK_PURPLE));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ActionResultType result = super.useOn(context);
        if(result == ActionResultType.FAIL) {
            return use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
        }
        return result;
    }

    private void damageItem(ItemStack itemStack, LivingEntity userEntity, PlayerEntity player) {
        itemStack.hurtAndBreak(1, player, (entity) -> {
            entity.broadcastBreakEvent(userEntity.getUsedItemHand());
        });
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            IcicleEntity icicle = new IcicleEntity(world, player);
            icicle.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 2.5F, 1.0F);
            icicle.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
            world.addFreshEntity(icicle);
        }

        if (!player.abilities.instabuild) {
            itemstack.shrink(1);
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 1.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }

    @Override
    public boolean hurtEnemy(ItemStack item, LivingEntity enemy, LivingEntity source) {
        item.hurtAndBreak(1, source, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }
}
