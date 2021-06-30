package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.lib.FastNoiseLite;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public abstract class FSurfaceBuilder<T extends FSurfaceBuilderConfig> extends SurfaceBuilder<T> {
    protected FastNoiseLite noise;
    protected static final int CAVE_WORLD_CEILING = 110;
    protected static final int CAVE_WORLD_FLOOR = 16;

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
        // Got this from https://mycurvefit.com/
        return (-1.110223f * Math.exp(-16) + 0.005636364f * y + 0.00007272727f * y * y) - 0.9f;
        //return (-0.5f + 0.03318182f * y - (0.0001363636f * y * y)) - 0.5f;
    }

    protected boolean isAir(int x, int y, int z) {
        float h = getCaveNoise(x, y, z);
        if (y <= CAVE_WORLD_FLOOR) {
            h -= 16f * Math.exp(-0.4f * (y - 2f));
        }
        return h > getYOffset(y);
    }

    protected boolean isMatchingBuilder(IChunk chunkIn, int x, int y, int z, Biome biomeIn, BlockPos.Mutable blockpos$mutable) {
        ConfiguredSurfaceBuilder<?> thisBuilder = biomeIn.getGenerationSettings().getSurfaceBuilder().get();
        ConfiguredSurfaceBuilder<?> thatBuilder = chunkIn.getBiomes().getNoiseBiome(x >> 2, y >> 2, z >> 2).getGenerationSettings().getSurfaceBuilder().get();
        return thatBuilder.equals(thisBuilder);
    }

    private boolean isBiomeDifferentAt(IChunk chunkIn, BlockPos pos, Biome biomeIn) {
        Biome neighbour = chunkIn.getBiomes().getNoiseBiome(pos.getX() >> 2, pos.getY() >> 2, pos.getZ() >> 2);
        return !neighbour.equals(biomeIn);
    }

    protected int findY(IChunk chunkIn, int start, double height, int x, int z, Biome biomeIn, boolean findLowest) {

        if (chunkIn.getBiomes() == null) return 0;

        // biomes have a resolution of 4, so need to squish coords
        x = x >> 2;
        z = z >> 2;
        height = Math.ceil(Math.abs(height / 4));
        start = Math.abs(start / 4);

        for (int y = start; y <= height; y++) {
            Biome biome = chunkIn.getBiomes().getNoiseBiome(x, y, z);
            if (biome.equals(biomeIn) == findLowest) { // XNOR
                return y * 4;
            }
        }

        return 0;
    }


    protected boolean isBorder(IChunk chunkIn, int lowY, int topY, BlockPos pos, Biome biomeIn, int distance) {
        if (chunkIn.getBiomes() == null) return false;

        if (isBiomeDifferentAt(chunkIn, pos.below(distance), biomeIn)) return true;
        if (isBiomeDifferentAt(chunkIn, pos.east(distance), biomeIn)) return true;
        if (isBiomeDifferentAt(chunkIn, pos.south(distance), biomeIn)) return true;
        //if(isBiomeDifferentAt(chunkIn, pos.west(distance), biomeIn)) return true;
        //if(isBiomeDifferentAt(chunkIn, pos.north(distance), biomeIn)) return true;
        //if(isBiomeDifferentAt(chunkIn, pos.above(distance), biomeIn)) return true;

        return false;
    }
}
