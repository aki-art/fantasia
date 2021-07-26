package io.github.akiart.fantasia.common.world;

import io.github.akiart.fantasia.lib.FastNoiseLite;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

// Responsible for generating one column at a time for the surface terrain (hills, valleys, etc.)
public class SurfaceGenerator {
    private BiomeProvider biomeSource;
    private int seed;
    private final int chunkCountY;
    private final int chunkCountX;
    private final int chunkCountZ;
    private final int sampleCount;
    private final int chunkHeight;
    private final int chunkWidth;
    int top = 128;
    private final int minY;
    private final int maxY;

    // Fast Noise Lite 2 key (not fully implemented yet):
    // JQAAAIA/AACAPwAAgD8AAIA/EwCkcL0/EQAEAAAAAAAAQBAA9ijcPxkAEQADAAAAAAAAQBAAMzOzQAkAAM3MzD0AAAAAPwAAAAAAAQQAAAAAABSu50AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA7FG4PgDhehQ/AAAAAAA=

    FastNoiseLite surfaceNoise;
    FastNoiseLite surfaceWarpA;
    FastNoiseLite surfaceWarpB;

    private final float[] BOX_BLUR_WEIGHTS;
    private final int BOX_BLUR_EXTENT = 5;
    private final int BOX_BLUR_RADIUS = MathHelper.floor(BOX_BLUR_EXTENT / 2f);
    private float biomeWeightSum = 0;
    private float terrainScale = 2.3f;

    public SurfaceGenerator(BiomeProvider biomeSource, int seed, int chunkWidth, int chunkHeight, int minY, int maxY, float terrainScale) {
        this.biomeSource = biomeSource;
        this.seed = seed;

        this.chunkCountY = 256 / chunkHeight;
        this.chunkCountZ = 16 / chunkWidth;
        this.chunkCountX = 16 / chunkWidth;
        this.sampleCount = chunkCountX * chunkCountY * chunkCountZ;

        this.chunkHeight = chunkHeight;
        this.chunkWidth = chunkWidth;
        this.terrainScale = remap(terrainScale, 0, 1, 0.15f, 1f); // so that the biome configs can use similar numbers with my generator
        this.minY = MathHelper.intFloorDiv(minY, chunkHeight);
        this.maxY = MathHelper.intFloorDiv(maxY, chunkHeight);

        // Main noise to control surface. This is used as a 3D noise, not as a heightmap, the flat terrain is achieved by
        // gradually adding/substracting to all values along the Y Axis, resulting in solid lower part and empty top.
        surfaceNoise = new FastNoiseLite(seed);
        surfaceNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        surfaceNoise.SetFrequency(0.02f);
        surfaceNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        surfaceNoise.SetFractalOctaves(4);

        // Warping works by sampling the noise just a little off position, leading to swoopier more interesting shapes and more overhangs.
        surfaceWarpA = new FastNoiseLite(seed);
        surfaceWarpA.SetDomainWarpAmp(0.1f);
        surfaceWarpA.SetFrequency(5.6f);
        surfaceWarpA.SetFractalType(FastNoiseLite.FractalType.DomainWarpProgressive);
        surfaceWarpA.SetFractalLacunarity(2f);
        surfaceWarpA.SetFractalGain(0.5f);
        surfaceWarpA.SetFractalOctaves(3);

        surfaceWarpB = new FastNoiseLite(seed);
        surfaceWarpB.SetDomainWarpAmp(0.36f);
        surfaceWarpB.SetFrequency(1.7f);
        surfaceWarpB.SetFractalType(FastNoiseLite.FractalType.DomainWarpProgressive);
        surfaceWarpB.SetFractalLacunarity(2f);
        surfaceWarpB.SetFractalGain(0.58f);
        surfaceWarpB.SetFractalOctaves(4);

        // Box blur is used to smooth out the height and scale differences of surface biomes
        BOX_BLUR_WEIGHTS = new float[25];

        for (int x = -BOX_BLUR_RADIUS; x <= BOX_BLUR_RADIUS; ++x) {
            for (int z = -BOX_BLUR_RADIUS; z <= BOX_BLUR_RADIUS; ++z) {
                float weight = 10.0F / MathHelper.sqrt((float) (x * x + z * z) + 0.2F);
                BOX_BLUR_WEIGHTS[x + BOX_BLUR_RADIUS + (z + BOX_BLUR_RADIUS) * BOX_BLUR_EXTENT] = weight;
                biomeWeightSum += weight;
            }
        }
    }

    public int getMaxY() { return maxY; }
    public int getMinY() { return minY; }

    // Used to flatten out the 3D noise to a 2D flat terrain.
    private float getYBias(float y) {
        return  - y * (0.05f / terrainScale);
    }

    private float getSurfaceValue(float x, float y, float z, float yScale, float yOffset) {
        FastNoiseLite.Vector3 warpedPosB = new FastNoiseLite.Vector3(x / terrainScale, (y / (terrainScale * yScale)), z / terrainScale);
        surfaceWarpA.DomainWarp(warpedPosB);
        return getSurfaceValueB(warpedPosB.x, warpedPosB.y, warpedPosB.z);
    }

    private float getSurfaceValueB(float x, float y, float z) {
        FastNoiseLite.Vector3 warpedPosA = new FastNoiseLite.Vector3(x, y, z);
        surfaceWarpB.DomainWarp(warpedPosA);
        return getYBias(y) + surfaceNoise.GetNoise(warpedPosA.x, warpedPosA.y, warpedPosA.z);
    }

    public float[] getNoiseColumn(int x, int z) {
        float[] column = new float[chunkCountY + 1];
        fillNoiseColumn(column, x, z);
        return column;
    }

    public float remap (float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }

    // start of some optimizations. the 3D array might not be the fastest though
    public void fillSamples(float[][][] samples, int chunkX, int chunkZ) {
        for(int x = 0; x < chunkCountX; x++) {
            for(int z = 0; z < chunkCountZ; z++) {
                fillNoiseColumn(samples[x][z], chunkX + x * chunkWidth, chunkZ + z * chunkWidth);
            }
        }
    }

    public void fillNoiseColumn(float[] column, int x, int z) {

        // Box blur with weighted box values

        // Biomes have a scale and depth value which needs some blurring, so the biome terrains transition are smoother
        // This is still a little too steep

        float scaleSum = 0;
        float depthSum = 0;

        for (int xo = -BOX_BLUR_RADIUS; xo <= BOX_BLUR_RADIUS; ++xo) {
            for (int yo = -BOX_BLUR_RADIUS; yo <= BOX_BLUR_RADIUS; ++yo) {
                Biome biome = biomeSource.getNoiseBiome((x + xo) >> 2, 255, (z + yo) >> 2);

                float sampleDepth = biome.getDepth();
                float sampleScale = biome.getScale();

                float weight = BOX_BLUR_WEIGHTS[xo + BOX_BLUR_RADIUS + (yo + BOX_BLUR_RADIUS) * BOX_BLUR_EXTENT];

                scaleSum += weight * sampleScale;
                depthSum += weight * sampleDepth;
            }
        }

        float depth = depthSum / biomeWeightSum;
        depth = MathHelper.clamp(depth, 0, 1);

        float scale = scaleSum / biomeWeightSum;
        scale = MathHelper.clamp(scale, 0, 1);

        float depthOffset = top + top * depth / 2f; // * (1f / 4f);
        for (int y = minY; y < maxY; ++y) {
            //column[y] = getSurfaceValue(x, (float)(y * 4) - depthOffset, z, scale * 2f, 0);
            column[y] = 1f;
        }
    }
}
