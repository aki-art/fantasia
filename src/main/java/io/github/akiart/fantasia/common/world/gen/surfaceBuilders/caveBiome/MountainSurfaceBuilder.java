package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.lib.FastNoiseLite;
import jdk.nashorn.internal.ir.annotations.Immutable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

import java.util.Random;
import java.util.Set;

public class MountainSurfaceBuilder extends FSurfaceBuilder<FSurfaceBuilderConfig> {

    private static BlockState SNOW = Blocks.SNOW_BLOCK.defaultBlockState();
    private static final Set<Direction> CHECK_DIRECTIONS = ImmutableSet.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    FastNoiseLite noise;

    public MountainSurfaceBuilder(Codec<FSurfaceBuilderConfig> codec) {
        super(codec);
        noise = new FastNoiseLite();
        noise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        noise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        noise.SetFractalOctaves(4);
    }

    private boolean isFlatArea(IWorld world, IChunk chunk, BlockPos pos) {

        if(world == null) {
            Fantasia.LOGGER.info("world is null");
            return false;
        }

        if(!chunk.getBlockState(pos.above()).isAir()) {
            return false;
        }

        int counter = 0;
        for(Direction direction: CHECK_DIRECTIONS) {
            if(!chunk.getBlockState(pos.relative(direction)).isAir()) {
                counter++;
            }

            if(counter >= 3) return true;
        }

        return false;
    }

    private boolean isSnow(int x, int y, int z, int floor) {
        //float yOffset = (y - (128 + 12));
        //yOffset = yOffset > 0 ? -yOffset / 60f : yOffset / 12f;
        float val = noise.GetNoise(x * 10f, z * 10f) + 0.3f;
        if(val > 0) {
        }
        return val > 0;
    }

    @Override
    public void apply(Random random, IChunk chunkIn, Biome biomeIn, int worldX, int worldZ, int startHeight, double noise,
                      BlockState defaultBlock, BlockState defaultFluid, int sealevel, long seed, FSurfaceBuilderConfig config) {

        this.noise.SetSeed((int)seed);
        BlockState top = config.getTopMaterial();
        BlockState base = config.getCaveWallMaterial();
        BlockPos.Mutable blockPos = new BlockPos.Mutable();

        int i = -1;
        int coverDepth = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int x = worldX & 15;
        int z = worldZ & 15;

        int lowY = findY(chunkIn, 8, startHeight, x, z, biomeIn, true);

        for (int y = startHeight; y >= lowY; --y) {

            blockPos.set(x, y, z);
            // BlockState state = chunkIn.getBlockState(blockPos);

            if (chunkIn.getBlockState(blockPos).isAir()) {
                i = -1;
            } else {
                if (i++ < coverDepth && isSnow(worldX, y, worldZ, sealevel)) {
                    chunkIn.setBlockState(blockPos, SNOW, false);
                } else chunkIn.setBlockState(blockPos, base, false);
            }
        }
    }
}
