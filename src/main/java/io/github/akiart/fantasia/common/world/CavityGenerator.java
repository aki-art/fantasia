package io.github.akiart.fantasia.common.world;

import io.github.akiart.fantasia.lib.FastNoiseLite;

// Responsible for generating the huge underground cave rooms. The connecting thin caves are not included in this.
public class CavityGenerator {
    FastNoiseLite cavityNoise;
    private int caveWorldFloor = 8; // What Y level where solid floor starts.

    public CavityGenerator(int seed) {

        cavityNoise = new FastNoiseLite(seed);
        cavityNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        cavityNoise.SetFrequency(0.010f);
        cavityNoise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        cavityNoise.SetFractalGain(-0.38f);
        cavityNoise.SetFractalOctaves(4);
    }

    // Makes caves larger towards lower Y levels and almost disappear by the surface.
    protected double getYOffset(double y, int floor) {
        // Got this from https://mycurvefit.com/
        return (-1.110223f * Math.exp(-16) + 0.005636364f * y + 0.00007272727f * y * y) - 0.9f;
    }

    int counter = 0;
    protected boolean isAir(int x, int y, int z) {
        float h = getCavityNoise(x, y, z);
        if (y <= caveWorldFloor) {
            h -= 16f * Math.exp(-0.4f * (y - 2f));
        }

        return false;
    }

    protected float getCavityNoise(int x, int y, int z) {
        float val = cavityNoise.GetNoise(x, y * 1.66f, z);
        val *= -1.34f;
        return val;
    }
}
