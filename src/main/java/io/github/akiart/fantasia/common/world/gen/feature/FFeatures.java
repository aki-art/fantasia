package io.github.akiart.fantasia.common.world.gen.feature;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Fantasia.ID);

    //public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BOULDER = FEATURES.register("boulder", () -> new FTreeFeature(BaseTreeFeatureConfig.CODEC).withConfiguration(FROZEN_TREE_CONFIG));
    public static final RegistryObject<Feature<CrystalPileFeatureConfig>> CRYSTAL_PILE = FEATURES.register("crystal_pile", () -> new CrystalPileFeature(CrystalPileFeatureConfig.CODEC));
    public static final RegistryObject<Feature<AlcoveFeatureConfig>> ALCOVE = FEATURES.register("alcove", () -> new AlcoveFeature4(AlcoveFeatureConfig.CODEC));
}
