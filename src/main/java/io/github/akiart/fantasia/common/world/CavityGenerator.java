package io.github.akiart.fantasia.common.world;

import io.github.akiart.fantasia.lib.FastNoiseLite;
import net.minecraft.util.math.MathHelper;

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
    protected double getYOffset(float y) {
        // Got this from https://mycurvefit.com/
        return (-1.110223f * Math.exp(-16) + 0.005636364f * y + 0.00007272727f * y * y) - 0.9f;
    }

    protected float getCavityNoise(int x, int y, int z) {
        float val = cavityNoise.GetNoise(x, y * 1.66f, z);
        val *= 1.34f;
        return val;
    }

    protected void fillNoiseColumn(float column[], int x, int z, int startYChunk, int chunkHeight) {
        for(int yChunk = startYChunk; yChunk > 0; yChunk--) {
            int y = yChunk * chunkHeight;
            float value = getCavityNoise(x, y, z);

            if (y <= caveWorldFloor) { // trying to thicken it up near the bottom so there is a smooth flooring
                value += 16f * Math.exp(-0.4f * (y - 2f));
            }

            value += getYOffset(y);
            column[yChunk] = Math.min(value, column[yChunk]);
        }
    }
}
