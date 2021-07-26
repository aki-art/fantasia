package io.github.akiart.fantasia.common.world;

import io.github.akiart.fantasia.lib.FastNoiseLite;
import net.minecraft.util.math.MathHelper;

// Responsible for generating the huge underground cave rooms. The connecting thin caves are a separate carver.
public class CavityGenerator {
    FastNoiseLite cavityNoise;
    private int caveWorldFloor = 8; // What Y level where solid floor starts.

    public CavityGenerator(int seed) {

        cavityNoise = new FastNoiseLite(seed);
        cavityNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        cavityNoise.SetFrequency(0.010f);
        cavityNoise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        cavityNoise.SetFractalGain(-0.38f);
        cavityNoise.SetFractalOctaves(3);
    }

    // Makes caves larger towards lower Y levels and almost disappear by the surface.
    protected double getYOffset(float y) {
        return (0.00005f * y * y) - 0.4f;
    }

    protected float getCavityNoise(int x, int y, int z) {
        float scale = 1f;
        float val = cavityNoise.GetNoise(x / scale, (y * 1.66f) / scale, z / scale);
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
            column[yChunk] = value; //Math.min(value, column[yChunk]);
        }
    }
}
