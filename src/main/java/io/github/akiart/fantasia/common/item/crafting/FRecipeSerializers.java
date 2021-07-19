package io.github.akiart.fantasia.common.item.crafting;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FRecipeSerializers {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Fantasia.ID);

    public static final RegistryObject<SpecialRecipeSerializer<TippedSaberToothJavelinRecipe>> TIPPED_SABER_TOOTH_JAVELIN = RECIPE_SERIALIZERS.register("crafting_special_tipped_saber_tooth_javelin", () -> new SpecialRecipeSerializer<>(TippedSaberToothJavelinRecipe::new));
    public static final RegistryObject<SpecialRecipeSerializer<CleanSaberToothJavelinRecipe>> CLEAN_SABER_TOOTH_JAVELIN = RECIPE_SERIALIZERS.register("crafting_special_clean_saber_tooth_javelin", () -> new SpecialRecipeSerializer<>(CleanSaberToothJavelinRecipe::new));

}
