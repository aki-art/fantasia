package io.github.akiart.fantasia.common.item.crafting;

import io.github.akiart.fantasia.common.item.itemType.TippedSaberToothJavelinItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
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
        int potionIdx = -1;

        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if(!stack.isEmpty()) {
                if(isDippableJavelin(stack)) {
                    result = stack.copy();
                }
                else if(isValidPotion(stack)) {
                   potion = PotionUtils.getPotion(stack);
                    potionIdx = i;
                }
            }
        }

        if(result != ItemStack.EMPTY && potion != Potions.EMPTY) {
            ((TippedSaberToothJavelinItem)result.getItem()).setPotion(result, potion);
            result.setCount(1);
            return result;
        }

        return ItemStack.EMPTY;
    }

    // only allow clean javelins
    protected boolean isDippableJavelin(ItemStack stack) {
        return stack.getItem() instanceof TippedSaberToothJavelinItem && PotionUtils.getMobEffects(stack).isEmpty();
    }

    protected boolean isValidPotion(ItemStack stack) {
        return stack.getItem() == Items.LINGERING_POTION && !PotionUtils.getPotion(stack).getEffects().isEmpty();
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
