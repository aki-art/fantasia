package io.github.akiart.fantasia.common.world;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.lib.FastNoiseLite;
import net.minecraft.block.BlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.tags.Tag;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.NoiseSettings;

// temporary until i write my own terrain generation
public class FChunkGenerator extends NoiseChunkGenerator {
	private final long seed;
	protected final Supplier<DimensionSettings> settings;


	public static final Codec<FChunkGenerator> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder
				.group(
						BiomeProvider.CODEC
							.fieldOf("biome_source")
							.forGetter((config) -> { return config.biomeSource; }),
						Codec.LONG
							.fieldOf("seed").stable()
							.forGetter((config) -> { return config.seed; }),
						DimensionSettings.CODEC
							.fieldOf("settings")
							.forGetter((config) -> { return config.settings; }))
				.apply(builder, builder.stable(FChunkGenerator::new));
	});

	@Override
	public int getSpawnHeight() {
		return 100;
	}

	// apply features to all biomes, not just the one sampled at Y8 like vanilla does
	@Override
	public void applyBiomeDecoration(WorldGenRegion region, StructureManager structureManager) {

//		if(!(biomeSource instanceof FBiomeProvider)) {
//			super.applyBiomeDecoration(region, structureManager);
//			return;
//		}
//
//		int chunkPosX = region.getCenterX();
//		int chunkPosZ = region.getCenterZ();
//
//		int x = chunkPosX << 4;
//		int z = chunkPosZ << 4;
//
//		BlockPos blockpos = new BlockPos(x, 0, z);
//		Set<Biome> biomes = ((FBiomeProvider)biomeSource).getAllVerticalBiomes((chunkPosX << 2) + 2, (chunkPosZ << 2) + 2);
//		SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
//		long seed = sharedseedrandom.setDecorationSeed(region.getSeed(), x, z);
//
//		for(Biome biome : biomes) {
//			try {
//				biome.generate(structureManager, this, region, seed, sharedseedrandom, blockpos);
//			} catch (Exception exception) {
//				CrashReport crashreport = CrashReport.forThrowable(exception, "Biome decoration");
//				crashreport.addCategory("Generation").setDetail("CenterX", chunkPosX).setDetail("CenterZ", chunkPosZ).setDetail("Seed", seed).setDetail("Biome", biome);
//				throw new ReportedException(crashreport);
//			}
//		}
	}

		// build surface to all biomes, not just the one sampled at world height like vanilla does
	public void buildSurfaceAndBedrock(WorldGenRegion region, IChunk chunk) {

		if(!(biomeSource instanceof FBiomeProvider)) {
			super.buildSurfaceAndBedrock(region, chunk);
			return;
		}

		ChunkPos chunkpos = chunk.getPos();
		int chunkX = chunkpos.getMinBlockX();
		int chunkZ = chunkpos.getMinBlockZ();

		SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
		sharedseedrandom.setBaseChunkSeed(chunkpos.x, chunkpos.z);

		double noiseScale = 0.0625D;

		for(int xOffset = 0; xOffset < 16; ++xOffset) {
			for(int zOffset = 0; zOffset < 16; ++zOffset) {

				int x = chunkX + xOffset;
				int z = chunkZ + zOffset;
				int y = chunk.getHeight(Heightmap.Type.WORLD_SURFACE_WG, xOffset, zOffset) + 1;

				double noiseVal = surfaceNoise.getSurfaceNoiseValue(
						(double)x * noiseScale,
						(double)z * noiseScale,
						noiseScale,
						(double)xOffset * noiseScale) * 15.0D;

				Set<Biome> biomes = ((FBiomeProvider)biomeSource).getAllVerticalBiomes((x << 2) + 2, (z << 2) + 2);

				for(Biome biome : biomes) {
					biome.buildSurfaceAt(sharedseedrandom, chunk, x, z, y, noiseVal, defaultBlock, defaultFluid, getSeaLevel(), region.getSeed());
				}
			}
		}

		setBedrock(chunk, sharedseedrandom);
	}

	public FChunkGenerator(BiomeProvider biomeProvider, long seed, Supplier<DimensionSettings> settings) {
		super(biomeProvider, seed, settings);
		this.seed = seed;
		this.settings = settings;
	}
}
