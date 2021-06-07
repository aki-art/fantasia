package io.github.akiart.fantasia.common.world.gen.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.Heightmap;

import java.util.Arrays;

public class FeatureUtil {
    public static int sampleGroundDistance(ISeedReader world, BlockPos pos, int xSize, int zSize) {
        int y = pos.getY();
        int[] samples = new int[9];

        samples[0] = y - getWorldHeight(world, pos);
        samples[1] = y - getWorldHeight(world, pos.south(zSize).east());
        samples[2] = y - getWorldHeight(world, pos.south(2 * zSize));
        samples[3] = y - getWorldHeight(world, pos.east(xSize));
        samples[4] = y - getWorldHeight(world, pos.south(zSize).east(xSize));
        samples[5] = y - getWorldHeight(world, pos.south(2 * zSize).east(xSize));
        samples[6] = y - getWorldHeight(world, pos.east(2 * xSize));
        samples[7] = y - getWorldHeight(world, pos.south(2 * zSize).east(2 * xSize));
        samples[8] = y - getWorldHeight(world, pos.south(zSize).east(2 * xSize));

        return Arrays.stream(samples).max().getAsInt();
    }

    public static int sampleGroundDistance2(ISeedReader world, BlockPos pos, int xSize, int zSize) {
        int y = pos.getY();
        int[] samples = new int[9];

        samples[0] = y - getWorldHeight(world, pos);
        samples[1] = y - getWorldHeight(world, pos.east());
        samples[2] = y - getWorldHeight(world, pos.west());

        samples[3] = y - getWorldHeight(world, pos.south(zSize));
        samples[4] = y - getWorldHeight(world, pos.south(zSize).east());
        samples[5] = y - getWorldHeight(world, pos.south(zSize).west());

        samples[6] = y - getWorldHeight(world, pos.north(zSize));
        samples[7] = y - getWorldHeight(world, pos.north(zSize).east());
        samples[8] = y - getWorldHeight(world, pos.north(zSize).west());

        return Arrays.stream(samples).max().getAsInt();
    }

    public static int getWorldHeight(ISeedReader world, BlockPos pos) {
        return world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) - 1;
    }
}
