package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.item.crafting.FRecipeSerializers;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class FRecipeProvider extends FRecipeProviderBase {

    public FRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    // Weird mapping here, this is for all recipes not just shapeless
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        cookFoods(consumer);
        CustomRecipeBuilder.special(FRecipeSerializers.TIPPED_SABER_TOOTH_JAVELIN.get()).save(consumer, new ResourceLocation(Fantasia.ID, "tipped_saber_tooth_javelin").toString());
        CustomRecipeBuilder.special(FRecipeSerializers.CLEAN_SABER_TOOTH_JAVELIN.get()).save(consumer, new ResourceLocation(Fantasia.ID, "clean_saber_tooth_javelin").toString());
    }

    protected void cookFoods(Consumer<IFinishedRecipe> consumer) {
        cook(consumer, FItems.RAW_PTARMIGAN.get(), FItems.ROAST_PTARMIGAN.get(), 100);
    }
}
