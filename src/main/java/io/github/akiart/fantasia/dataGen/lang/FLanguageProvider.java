package io.github.akiart.fantasia.dataGen.lang;

import io.github.akiart.fantasia.common.potion.FEffects;
import io.github.akiart.fantasia.common.world.biome.BiomeRegistryObject;
import io.github.akiart.fantasia.common.world.biome.FBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class FLanguageProvider extends LanguageProvider {
    public FLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        new FItemLanguageProvider(this).addTranslations();
        new FBlockLanguageProvider(this).addTranslations();
        new FEnchantmentLanguageProvider(this).addTranslations();

        effects();
        biomes();
        tooltips();
    }

    private void effects() {
        add(FEffects.ACID_REPEL.get().getDescriptionId(), "Acid Repel");
        add(FEffects.FROST_RESISTANCE.get().getDescriptionId(), "Frost Resistance");
    }

    private void biomes() {
        addBiome(FBiomes.ARCTIC_TUNDRA, "Arctic Tundra");
        addBiome(FBiomes.ASPEN_FOREST, "Aspen Forest");
        addBiome(FBiomes.FROZEN_FOREST, "Frozen Forest");
        addBiome(FBiomes.BASALT_BEACH, "Basalt Beach");
        addBiome(FBiomes.FORLORN_SEA, "Forlorn Sea");
        addBiome(FBiomes.ROCKY_MOUNTAINS, "Rocky Mountains");
        addBiome(FBiomes.ROCKY_MOUNTAINS_EDGE, "Rocky Mountains Edge");
        addBiome(FBiomes.GRIMCAP_GROVE, "Grimcap Grove");
        addBiome(FBiomes.ICY_CAVERN, "Icy Cavern");
    }

    private void tooltips() {
        add("tooltip.item.sabertooth_javelin.uses_left", "%s/%s uses left");
    }

    private void addBiome(BiomeRegistryObject biome, String name) {
        add("biome.fantasia." + biome.getKey().location().getPath(), name);
    }

}
