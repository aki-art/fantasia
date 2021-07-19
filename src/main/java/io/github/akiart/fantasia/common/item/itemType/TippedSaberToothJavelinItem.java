package io.github.akiart.fantasia.common.item.itemType;

import com.google.common.collect.Maps;
import io.github.akiart.fantasia.Config;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import io.github.akiart.fantasia.common.entity.projectile.SaberToothJavelinEntity;
import io.github.akiart.fantasia.common.item.FItemTier;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;

public class TippedSaberToothJavelinItem extends JavelinItem {

    public TippedSaberToothJavelinItem(float damage, float attackSpeed, Properties properties) {
        super(FItemTier.BONE, damage, attackSpeed, properties);
    }

    private static final HashMap<Potion, Integer> potionColorOverrides = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Potions.POISON, Constants.Colors.ACID_GREEN);
        map.put(Potions.HARMING, Constants.Colors.RED);
        map.put(Potions.WEAKNESS, Constants.Colors.INDIGO);
    });

    // TODO remove when done testing
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if(!context.getLevel().isClientSide() && context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.TARGET)) {
            decreasePotionUses(context.getItemInHand());
        }

        return super.useOn(context);
    }

    public int getMaxPotionUses() {
        return Config.common.equipment.sabertoothJavelinUses.get();
    }

    public void setPotionUsesLeft(ItemStack stack, int uses) {
        stack.getOrCreateTag().putInt("F_PotionUses", Math.max(0, uses));
        if(uses <= 0) {
            PotionUtils.setPotion(stack, Potions.EMPTY);
            stack.removeTagKey("F_TipColor");
        }
        else {
            int color = ((getColor(stack) | 0xFF000000) - 0xFF000000) | ((int) (getPotionUsesLeftForDisplay(stack) * 255) << 24);
            setColor(stack, color);
        }
    }

    public static int getColor(ItemStack stack) {
        return !stack.hasTag() ? -1 : stack.getTag().getInt("F_TipColor");
    }

    public void decreasePotionUses(ItemStack stack) {
        setPotionUsesLeft(stack, getPotionUsesLeft(stack) - 1);
    }

    public int getPotionUsesLeft(ItemStack stack) {
        return !stack.hasTag() ? 0 : stack.getTag().getInt("F_PotionUses");
    }

    public void setPotion(ItemStack stack, Potion potion, int uses) {
        PotionUtils.setPotion(stack, potion);
        setPotionUsesLeft(stack, uses);
        setColor(stack, potionColorOverrides.getOrDefault(potion, PotionUtils.getColor(potion)));
    }

    public void setPotion(ItemStack stack, Potion potion) {
        int uses = potion.getEffects().isEmpty() ? 0 : getMaxPotionUses();
        setPotion(stack, potion, uses);
    }

    protected void setColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("F_TipColor", color);
    }

    public float getPotionUsesLeftForDisplay(ItemStack stack)
    {
        return getPotionUsesLeft(stack) / (float) getMaxPotionUses();
    }

    @Override
    protected JavelinEntity createJavelinEntity(ItemStack itemStack, World world, PlayerEntity player) {
        SaberToothJavelinEntity entity = new SaberToothJavelinEntity(FEntities.JAVELIN.get(), world, player, itemStack);
        entity.setEffectsFromItem(itemStack);
        return entity;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        PotionUtils.addPotionTooltip(stack, tooltip, 0.125F);
        tooltip.add(new TranslationTextComponent("tooltip.item.sabertooth_javelin.uses_left", getPotionUsesLeft(stack), getMaxPotionUses()).withStyle(TextFormatting.DARK_GRAY));
        tooltip.add(StringTextComponent.EMPTY);
    }
}
