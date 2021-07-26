package io.github.akiart.fantasia.common.world.gen.feature;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.world.gen.feature.placement.ContextAwareFeature;
import io.github.akiart.fantasia.common.world.gen.feature.placement.ContextAwareFeatureConfig;
import io.github.akiart.fantasia.common.world.gen.feature.placement.ProjectDownToContextFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Fantasia.ID);

    //public static final RegistryObject<Feature<BaseTreeFeatureConfig>> BOULDER = FEATURES.register("boulder", () -> new FTreeFeature(BaseTreeFeatureConfig.CODEC).withConfiguration(FROZEN_TREE_CONFIG));
    public static final RegistryObject<Feature<CrystalPileFeatureConfig>> CRYSTAL_PILE = FEATURES.register("crystal_pile", () -> new CrystalPileFeature(CrystalPileFeatureConfig.CODEC));
    public static final RegistryObject<Feature<AlcoveFeatureConfig>> ALCOVE = FEATURES.register("alcove", () -> new AlcoveFeature4(AlcoveFeatureConfig.CODEC));
    public static final RegistryObject<Feature<SpeleothemFeatureConfig>> SPELEOTHEM = FEATURES.register("speleothem", () -> new SpeleothemFeature(SpeleothemFeatureConfig.CODEC));
    public static final RegistryObject<Feature<SpeleothemClusterFeatureConfig>> SPELEOTHEM_CLUSTER = FEATURES.register("speleothem_cluster", () -> new SpeleothemClusterFeature(SpeleothemClusterFeatureConfig.CODEC));
    public static final RegistryObject<Feature<PillarFeatureConfig>> PILLAR = FEATURES.register("pillar", () -> new PillarFeature(PillarFeatureConfig.CODEC));

    // use to restrict other features to certain contexts
    public static final RegistryObject<Feature<ContextAwareFeatureConfig>> CONTEXT_AWARE = FEATURES.register("context_aware", () -> new ContextAwareFeature(ContextAwareFeatureConfig.CODEC));
    public static final RegistryObject<Feature<ContextAwareFeatureConfig>> PROJECT_DOWN = FEATURES.register("project_to_context", () -> new ProjectDownToContextFeature(ContextAwareFeatureConfig.CODEC));
}
