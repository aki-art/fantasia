package io.github.akiart.fantasia.common.world.gen.carver;

import io.github.akiart.fantasia.lib.FastNoiseLite;

// not an actual carver yet, these were moved out of terrain gen and just tossed in theri own class for later use
public class SpagetCaveCarver {
    FastNoiseLite spagetCavesValue;
    FastNoiseLite spagetCavesCellular;
    FastNoiseLite spagetCavesWarp;

    public SpagetCaveCarver(int seed) {
        // Fast Noise Lite 2 key: EwCPwvU+GgABEAAAAIhBHwAWAAEAAAALAAMAAAACAAAAAwAAAAQAAAAAAAAAPwEUAP//AAAAAAAAPwAAAAA/AAAAAD8AAAAAPwEXAAAAgL8AAIA/PQoXQFK4HkATAAAAoEAGAACPwnU8AOF6FD8=
        spagetCavesValue = new FastNoiseLite(seed);
        spagetCavesValue.SetNoiseType(FastNoiseLite.NoiseType.Value);
        spagetCavesValue.SetFrequency(0.02f);

        spagetCavesCellular = new FastNoiseLite(seed);
        spagetCavesCellular.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        spagetCavesCellular.SetFractalType(FastNoiseLite.FractalType.FBm);
        spagetCavesCellular.SetFrequency(0.02f);
        spagetCavesCellular.SetFractalOctaves(1);
        spagetCavesCellular.SetCellularJitter(0.7f);
        spagetCavesCellular.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
        spagetCavesCellular.SetCellularReturnType(FastNoiseLite.CellularReturnType.Distance2Div);
        spagetCavesCellular.SetCellularIndices(2f, 3f);

        spagetCavesWarp = new FastNoiseLite();
        spagetCavesWarp.SetDomainWarpAmp(0.015f);
        spagetCavesWarp.SetFrequency(17f);
        spagetCavesWarp.SetFractalOctaves(2);
        spagetCavesWarp.SetFractalType(FastNoiseLite.FractalType.DomainWarpProgressive);
        spagetCavesWarp.SetFractalLacunarity(7f);
        spagetCavesWarp.SetFractalGain(0.3f);
    }
    private float minSmooth(float a, float b, float smoothness) {
        float smooth = Math.max(0, Math.abs(smoothness));
        float h = Math.max(0, smooth - Math.abs(a - b));
        h *= (1f / smooth);
        float mul = (1f / 6f) * h * h * h * smooth;
        return mul + Math.min(a, b);
    }

    private float getSpagetNoiseVal(int seed, float x, float y, float z, float scale) {
        float value = getSpagetBase(seed, x , y , z , scale);
        return value - 0.33f;
    }

    private float getSpagetBase(int seed, float x, float y, float z, float scale) {

        FastNoiseLite.Vector3 warp = new FastNoiseLite.Vector3(x * scale, y * scale, z * scale);
        spagetCavesWarp.DomainWarp(warp);

        x = warp.x;
        y = warp.y;
        z = warp.z;

        float n0 = spagetCavesCellular.GetNoise(x + 0.5f, y + 0.5f, z + 0.5f);
        spagetCavesCellular.SetSeed(seed + 1);
        float n1 = spagetCavesCellular.GetNoise(x, y, z);
        spagetCavesCellular.SetSeed(seed);
        float c = getVCValue(x, y, z);

        return minSmooth(n0, n1, c);
    }

    public static float remap (float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }

    private float getVCValue(float x, float y, float z) {
        float scale = 5f;
        float value = spagetCavesValue.GetNoise(x / scale, y / scale, z / scale);
        return remap(value, -1f, 1f, 2.36f, 2.48f);
    }

    boolean shouldCarve(int x, int y, int z) {

        float result = getSpagetNoiseVal(0, x, y, z, 0.48f);
        return result > 0;//h > getYOffset(y);
    }
}
