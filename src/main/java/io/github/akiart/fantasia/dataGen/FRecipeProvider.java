package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;

import java.util.function.Consumer;

public class FRecipeProvider extends FRecipeProviderBase {

    public FRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    // Weird mapping here, this is for all recipes not just shapeless
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        cookFoods(consumer);
    }

    protected void cookFoods(Consumer<IFinishedRecipe> consumer) {
        cook(consumer, FItems.RAW_PTARMIGAN.get(), FItems.ROAST_PTARMIGAN.get(), 100);
    }
}
