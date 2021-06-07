package io.github.akiart.fantasia.common.world;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

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
	
	public FChunkGenerator(BiomeProvider biomeProvider, long seed, Supplier<DimensionSettings> settings) {
		super(biomeProvider, seed, settings);
		this.seed = seed;
		this.settings = settings;
		
		if(!(biomeProvider instanceof FBiomeProvider)) {
			Fantasia.LOGGER.warn("FChunkGenerator trying to use a BiomeProvider that isn't a FBiomeProvider.");
			// return;
		}
		
		FBiomeProvider fBiomeProvider = (FBiomeProvider)biomeProvider;
	}
}
