package io.github.akiart.fantasia.common.world;


import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.gen.MaxMinNoiseMixer;

public class BiasedMinMaxNoiseMixer {

    private float bias;
    private MaxMinNoiseMixer baseMixer;
    private int center = 110;

    public static BiasedMinMaxNoiseMixer getNoiseMixer(SharedSeedRandom seed, int octaveCount, DoubleList amplitudes, float bias) {
        return new BiasedMinMaxNoiseMixer(seed, octaveCount, amplitudes, bias);
    }

    private BiasedMinMaxNoiseMixer(SharedSeedRandom seed, int octaveCount, DoubleList amplitudes, float bias) {
        this.baseMixer = MaxMinNoiseMixer.create(seed, octaveCount, amplitudes);
        this.bias = bias;
    }

    int counter = 0;

    public double getValue(float surfaceTemperature, double x, double y, double z) {

        return baseMixer.getValue(x, y, z);
        //return noiseVal * MathHelper.clamp(110 - y, 0, 256) / 256D;
    }
}
