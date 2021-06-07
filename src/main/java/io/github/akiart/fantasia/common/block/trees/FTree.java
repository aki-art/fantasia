package io.github.akiart.fantasia.common.block.trees;

import java.util.Random;

import io.github.akiart.fantasia.common.world.gen.feature.FConfiguredFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants.BlockFlags;

public class FTree {

	private final RegistryKey<ConfiguredFeature<?, ?>> key;

	public FTree(RegistryKey<ConfiguredFeature<?, ?>> key) {
		this.key = key;
	}

	protected ConfiguredFeature<?, ?> getTreeFeature(ServerWorld world, Random random) {
		return world.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY).get(key);
	}

	public boolean attemptGrowTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state,
		Random rand) {

		ConfiguredFeature<?, ?> configuredfeature = this.getTreeFeature(world, rand);

		world.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
		((BaseTreeFeatureConfig) configuredfeature.config).setFromSapling();

		if (configuredfeature.place(world, chunkGenerator, rand, pos)) {
			return true;
		} else {
			world.setBlock(pos, state, BlockFlags.NO_RERENDER);
			return false;
		}
	}
}
