package io.github.akiart.fantasia.common.world;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.world.biome.BiomeRegistryObject;
import io.github.akiart.fantasia.common.world.biome.FBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

// This is just providing a single biome for now, but later will be expanded
public class FBiomeProvider extends BiomeProvider {
	private final Registry<Biome> biomeRegistry;
	private CaveBiomeProvider caveBiomes;

	public FBiomeProvider(long seed, Registry<Biome> biomeRegistry) {

		super(getStartBiomes(biomeRegistry));

		this.biomeRegistry = biomeRegistry;
		this.caveBiomes = new CaveBiomeProvider(biomeRegistry);

	}

	public FBiomeProvider(Registry<Biome> biomeRegistry) {
		this(0, biomeRegistry);
	}

	public Registry<Biome> getBiomeRegistry() {
		return biomeRegistry;
	}

	public static final Codec<FBiomeProvider> CODEC = RecordCodecBuilder.create((instance) -> instance
		.group(RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((source) -> source.biomeRegistry))
		.apply(instance, instance.stable(FBiomeProvider::new)));

	private static List<Biome> getStartBiomes(Registry<Biome> registry) {

		return FBiomes.biomeList.stream()
			.map(s -> registry.get(s.getKey().location()))
			.collect(Collectors.toList());
	}

	public Biome getBiome(BiomeRegistryObject biomeObj) {
		return biomeRegistry.getOrThrow(biomeObj.getKey());
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		Biome surface = getBiome(FBiomes.FROZEN_FOREST);

		if(y < 118 >> 2) {
			return caveBiomes.getNoiseBiome(surface, x, y, z);
		}

		return surface;
	}

	public HashSet<Biome> getAllVerticalBiomes(int x, int z) {
		HashSet<Biome> results = new HashSet<Biome>();
		for (int y = 0; y <= 256; y = y + 4) {
			results.add(getNoiseBiome(x >> 2, y >> 2, z >> 2));
		}

		return results;
	}

	@Override
	protected Codec<? extends BiomeProvider> codec() {
		return CODEC;
	}

	@Override
	public BiomeProvider withSeed(long seed) {
		return new FBiomeProvider(seed, biomeRegistry);
	}
}
