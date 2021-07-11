package io.github.akiart.fantasia.common.world.gen.feature;

import com.google.common.collect.ImmutableList;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.world.gen.treedecorators.IcicleTreeDecorator;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class FConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> FROZEN_ELM = createKey("frozen_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FROZEN_SPRUCE = createKey("frozen_spruce");
    public static ConfiguredFeature<?, ?> FROZEN_TREE_FEATURE;
    public static ConfiguredFeature<?, ?> FROZEN_SPRUCE_FEATURE;

    protected static BaseTreeFeatureConfig FROZEN_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(FBlocks.FROZEN_ELM.log.get().defaultBlockState()),
            new SimpleBlockStateProvider(FBlocks.FROZEN_ELM.leaves.get().defaultBlockState()),
            new BlobFoliagePlacer(FeatureSpread.fixed(3), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(6, 2, 2),
            new TwoLayerFeature(1, 0, 1)))
            .decorators(ImmutableList.of(new IcicleTreeDecorator(0.5f)))
            .ignoreVines()
            .build();

//    protected static BaseTreeFeatureConfig FROZEN_SPRUCE_CONFIG = (new BaseTreeFeatureConfig.Builder(
//            new SimpleBlockStateProvider(FBlocks.FROZEN_SPRUCE.log.get().defaultBlockState()),
//            new SimpleBlockStateProvider(FBlocks.FROZEN_SPRUCE.leaves.get().defaultBlockState()),
//            new SpruceFoliagePlacer(/*radius*/FeatureSpread.fixed(3), /*offset*/FeatureSpread.fixed(0), /*trunkHeight*/FeatureSpread.of(3, 1)), new StraightTrunkPlacer(10, 2, 2),
//            new TwoLayerFeature(1, 0, 1)))
//            .decorators(ImmutableList.of(new IcicleTreeDecorator(0.3f)))
//            .ignoreVines()
//            .build();

//    protected static BaseTreeFeatureConfig ASPEN_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
//            new SimpleBlockStateProvider(FBlocks.ASPEN.log.get().defaultBlockState()),
//            new SimpleBlockStateProvider(FBlocks.ASPEN.leaves.get().defaultBlockState()),
//            new SpruceFoliagePlacer(/*radius*/FeatureSpread.fixed(3), /*offset*/FeatureSpread.fixed(0), /*trunkHeight*/FeatureSpread.of(3, 1)), new StraightTrunkPlacer(10, 2, 2),
//            new TwoLayerFeature(1, 0, 1)))
//            .decorators(ImmutableList.of(new IcicleTreeDecorator(0.3f)))
//            .ignoreVines()
//            .build();

    public static void registerConfiguredFeatures() {
        FROZEN_TREE_FEATURE = register(FROZEN_ELM, Feature.TREE.configured(FROZEN_TREE_CONFIG));
   //     FROZEN_SPRUCE_FEATURE = register(FROZEN_SPRUCE, Feature.TREE.configured(FROZEN_SPRUCE_CONFIG));
    }

    private static RegistryKey<ConfiguredFeature<?, ?>> createKey(final String name) {
        return RegistryKey.create(Registry.CONFIGURED_FEATURE_REGISTRY, new ResourceLocation(Fantasia.ID, name));
    }

    private static ConfiguredFeature<?, ?> register(final RegistryKey<ConfiguredFeature<?, ?>> key,
                                                    final ConfiguredFeature<?, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key.location(), configuredFeature);
    }
}
