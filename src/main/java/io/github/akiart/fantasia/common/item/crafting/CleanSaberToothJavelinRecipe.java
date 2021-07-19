package io.github.akiart.fantasia.common.item.crafting;

import io.github.akiart.fantasia.common.item.itemType.SaberToothJavelinItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;

public class CleanSaberToothJavelinRecipe extends TippedSaberToothJavelinRecipe {
    public CleanSaberToothJavelinRecipe(ResourceLocation location) {
        super(location);
    }

    @Override
    protected boolean isValidPotion(ItemStack stack) {
        return stack.getItem() == Items.POTION && PotionUtils.getPotion(stack) == Potions.WATER;
    }

    @Override
    protected boolean isDippableJavelin(ItemStack stack) {
        return stack.getItem() instanceof SaberToothJavelinItem;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return FRecipeSerializers.CLEAN_SABER_TOOTH_JAVELIN.get();
    }
}
