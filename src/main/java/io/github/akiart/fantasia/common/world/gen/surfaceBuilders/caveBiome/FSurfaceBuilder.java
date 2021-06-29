package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.lib.FastNoiseLite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import java.util.ArrayList;

public abstract class FSurfaceBuilder<T extends CaveBiomeSBConfig> extends SurfaceBuilder<T> {
    protected FastNoiseLite noise;
    protected static final int CAVE_WORLD_CEILING = 110;

    public FSurfaceBuilder(Codec<T> codec) {
        super(codec);
        noise = new FastNoiseLite();
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        noise.SetFrequency(0.010f);
        noise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        noise.SetFractalGain(-0.38f);
        noise.SetFractalOctaves(4);
    }

    protected float getCaveNoise(int x, int y, int z) {
        y *= 1.66f;
        float val = noise.GetNoise(x, y, z);
        val *= -1.34f;
        return val;
    }

    protected double getYOffset(double y) {
        return (-1.110223f * Math.exp(-16) + 0.005636364f * y + 0.00007272727f * y * y) - 0.6f;
    }

    protected boolean isAir(int x, int y, int z) {
        if(y > CAVE_WORLD_CEILING) {
            return getCaveNoise(x, y, z) > 1.5f;
        }
        else {
            return getCaveNoise(x, y, z) > getYOffset(y);
        }
    }

    protected boolean isMatchingBuilder(IChunk chunkIn, int x, int y, int z, Biome biomeIn, BlockPos.Mutable blockpos$mutable) {
        ConfiguredSurfaceBuilder<?> thisBuilder = biomeIn.getGenerationSettings().getSurfaceBuilder().get();
        ConfiguredSurfaceBuilder<?> thatBuilder = chunkIn.getBiomes().getNoiseBiome(x >> 2, y >> 2, z >> 2).getGenerationSettings().getSurfaceBuilder().get();
        return thatBuilder.equals(thisBuilder);
    }
}
