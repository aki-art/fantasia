package io.github.akiart.fantasia.common.world;


import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import io.github.akiart.fantasia.common.world.biome.FBiomes;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.gen.MaxMinNoiseMixer;

/* TEMPORARY
* this file was written super early in development. It is reintroduced as a temporary means to test other features. */
public class CaveBiomeProvider {
    private long seed;

    private final Noise temperatureNoise;
    private final Noise humidityNoise;
    private final Noise altitudeNoise;
    private final Noise weirdnessNoise;

    private final BiasedMinMaxNoiseMixer temperatureNoiseMixer;
    private final MaxMinNoiseMixer humidityNoiseMixer;
    private final MaxMinNoiseMixer altitudeNoiseMixer;
    private final MaxMinNoiseMixer weirdnessNoiseMixer;

    private final int firstOctave = -6;

    private List<Pair<Biome.Attributes, Supplier<Biome>>> biomes(Registry<Biome> biomeRegistry) {
        return ImmutableList.of(
                Pair.of(
                        new Biome.Attributes(0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                        () -> biomeRegistry.get(FBiomes.GRIMCAP_GROVE.getKey())),
                Pair.of(
                        new Biome.Attributes(0.0F, -0.5F, 0.0F, 0.0F, 0.0F),
                        () -> biomeRegistry.get(FBiomes.ICY_CAVERN.getKey())));
    }

    private final DoubleList defaultAmp = new DoubleArrayList(ImmutableList.of(1.0D, 1.0D));
    private static final Noise DEFAULT_NOISE = new Noise(-6, ImmutableList.of(1.0D, 1.0D));

    private final List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes;

    public CaveBiomeProvider(Registry<Biome> biomeRegistry) {

        this.seed = 0;

        this.temperatureNoise = DEFAULT_NOISE;
        this.weirdnessNoise = DEFAULT_NOISE;
        this.altitudeNoise = DEFAULT_NOISE;
        this.humidityNoise = DEFAULT_NOISE;

        this.temperatureNoiseMixer = BiasedMinMaxNoiseMixer.getNoiseMixer(new SharedSeedRandom(seed),
                temperatureNoise.getNumberOfOctaves(),
                temperatureNoise.getAmplitudes(), 1f);

        this.humidityNoiseMixer = MaxMinNoiseMixer.create(new SharedSeedRandom(seed + 1L), firstOctave, defaultAmp);
        this.altitudeNoiseMixer = MaxMinNoiseMixer.create(new SharedSeedRandom(seed + 2L), firstOctave, defaultAmp);
        this.weirdnessNoiseMixer = MaxMinNoiseMixer.create(new SharedSeedRandom(seed + 3L), firstOctave, defaultAmp);

        this.biomeAttributes = biomes(biomeRegistry);
    }

    // /4
    public Biome getNoiseBiome(Biome surfaceBiome, int x, int y, int z) {
        Biome.Attributes biome$attributes = new Biome.Attributes(
                (float) this.temperatureNoiseMixer.getValue(surfaceBiome.getBaseTemperature(), x, y, z),
                (float) this.humidityNoiseMixer.getValue(x, y, z),
                (float) this.altitudeNoiseMixer.getValue(x, y, z),
                (float) this.weirdnessNoiseMixer.getValue(x, y, z), 0.0F);

        return this.biomeAttributes
                .stream()
                .min(Comparator
                        .comparing((attributeBiomePair) -> attributeBiomePair.getFirst().fitness(biome$attributes)))
                .map(Pair::getSecond)
                .map(Supplier::get)
                .orElse(BiomeRegistry.THE_VOID);
    }

    static class Noise {
        private final int numOctaves;
        private final DoubleList amplitudes;

        public Noise(int numOctaves, List<Double> amplitudes) {
            this.numOctaves = numOctaves;
            this.amplitudes = new DoubleArrayList(amplitudes);
        }

        public int getNumberOfOctaves() {
            return this.numOctaves;
        }

        public DoubleList getAmplitudes() {
            return this.amplitudes;
        }
    }
}
