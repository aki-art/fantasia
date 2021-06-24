package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.block.Block;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class FRecipeProviderBase extends RecipeProvider {

    public FRecipeProviderBase(DataGenerator generator) {
        super(generator);
    }

    protected void cook(Consumer<IFinishedRecipe> consumer, IItemProvider in, IItemProvider out, int time) {
        smeltAny(consumer, in, out, time, CookingRecipeSerializer.SMELTING_RECIPE);
        smeltAny(consumer, in, out, time / 2, CookingRecipeSerializer.SMOKING_RECIPE);
        smeltAny(consumer, in, out, time * 3, CookingRecipeSerializer.BLASTING_RECIPE);
    }

    protected void smeltAny(Consumer<IFinishedRecipe> consumer, IItemProvider in, IItemProvider out, int time, CookingRecipeSerializer<?> process) {
        CookingRecipeBuilder
                .cooking(Ingredient.of(in), out, 0.3f, time, process)
                .unlockedBy("has_food", has(in))
                .save(consumer, getCookRecipeName(in, out, process.getRegistryName().getPath()));
    }

    protected String getItemName(IItemProvider item) {
        return item.asItem().getRegistryName().getPath();
    }

    protected ResourceLocation getCookRecipeName(IItemProvider in, IItemProvider out, String type) {
        return  new ResourceLocation(Fantasia.ID, type + "_" + getItemName(in) + "_to_" + getItemName(out));
    }
}
