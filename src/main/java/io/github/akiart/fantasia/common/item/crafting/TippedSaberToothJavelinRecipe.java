package io.github.akiart.fantasia.common.item.crafting;

import io.github.akiart.fantasia.common.item.itemType.SaberToothJavelinItem;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class TippedSaberToothJavelinRecipe extends SpecialRecipe {

    public TippedSaberToothJavelinRecipe(ResourceLocation location) {
        super(location);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        int javelinCount = 0;
        int potionCount = 0;

        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);

            if(!stack.isEmpty()) {
                if(isDippableJavelin(stack)) {
                    javelinCount += stack.getCount();
                }
                else if(isValidPotion(stack)) {
                    potionCount += stack.getCount();
                }
                else return false; // something else is in the crafting table

                if(javelinCount > 1 || potionCount > 1) return false; // too many items
            }
        }

        return javelinCount == 1 && potionCount == 1;
    }

    @Override
    public ItemStack assemble(CraftingInventory inventory) {
        ItemStack result = ItemStack.EMPTY;
        Potion potion = Potions.EMPTY;

        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if(!stack.isEmpty()) {
                if(isDippableJavelin(stack)) {
                    result = stack.copy();
                }
                else if(isValidPotion(stack)) {
                   potion = PotionUtils.getPotion(stack);
                }
            }
        }

        if(result != ItemStack.EMPTY && potion != Potions.EMPTY) {
            ((SaberToothJavelinItem)result.getItem()).setPotion(result, potion);
            result.setCount(1);
            return result;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inventory) {
        NonNullList<ItemStack> result = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);

        for(int i = 0; i < result.size(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            if (!itemstack.isEmpty()) {

                // default behavior in case a modded potion has something else set
                if (itemstack.hasContainerItem()) {
                    result.set(i, itemstack.getContainerItem());
                }
                // leave a glass bottle behind
                else if (isValidPotion(itemstack)) {
                    ItemStack glassBottle = new ItemStack(Items.GLASS_BOTTLE);
                    glassBottle.setCount(1);
                    result.set(i, glassBottle);
                }
            }
        }

        return result;
    }

    // only allow clean javelins
    protected boolean isDippableJavelin(ItemStack stack) {
        return stack.getItem() instanceof SaberToothJavelinItem && PotionUtils.getMobEffects(stack).isEmpty();
    }

    // instanceof instead of direct item check makes modded potions extending lingering pots also work
    protected boolean isValidPotion(ItemStack stack) {
        return stack.getItem() instanceof LingeringPotionItem && !PotionUtils.getMobEffects(stack).isEmpty();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return FRecipeSerializers.TIPPED_SABER_TOOTH_JAVELIN.get();
    }
}
