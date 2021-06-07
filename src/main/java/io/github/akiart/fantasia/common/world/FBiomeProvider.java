package io.github.akiart.fantasia.common.world;

import java.util.List;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.akiart.fantasia.common.world.biome.BiomeRegistryObject;
import io.github.akiart.fantasia.common.world.biome.FBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

// This is just providing a single biome for now, but later will be expanded
public class FBiomeProvider extends BiomeProvider {
	private final Registry<Biome> biomeRegistry;

	public FBiomeProvider(long seed, Registry<Biome> biomeRegistry) {

		super(getStartBiomes(biomeRegistry));

		this.biomeRegistry = biomeRegistry;

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
		return getBiome(FBiomes.FROZEN_FOREST);
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
