package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.common.world.gen.util.Extents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.Arrays;
import java.util.Random;

public abstract class TerrainFeature<T extends IFeatureConfig> extends Feature<T> {
    public TerrainFeature(Codec<T> codec) {
        super(codec);
    }

    // ideally the feature placement puts it in here anyway, but just in case,
    // because these featueres are big and they need to maximize the space they can expand to
    protected BlockPos snapToChunkCenter(ISeedReader world, BlockPos pos) {
        ChunkPos chunkPos = world.getChunk(pos).getPos();
        return new BlockPos(chunkPos.getMinBlockX() + 8, pos.getY(), chunkPos.getMinBlockZ() + 8);
    }

    protected int getWorldHeight(ISeedReader world, BlockPos pos) {
        if(!world.isAreaLoaded(pos, 1)) return world.getMaxBuildHeight();
        return world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) - 1;
    }

    protected int sampleGroundDistance(ISeedReader world, BlockPos pos, int xSize, int zSize) {
        int y = pos.getY();
        int[] samples = new int[9];

        xSize = pos.getX() - xSize;
        zSize = pos.getZ() - zSize;

        samples[0] = y - getWorldHeight(world, pos);
        samples[1] = y - getWorldHeight(world, pos.east(xSize));
        samples[2] = y - getWorldHeight(world, pos.west(xSize));

        samples[3] = y - getWorldHeight(world, pos.south(zSize));
        samples[4] = y - getWorldHeight(world, pos.south(zSize).east(xSize));
        samples[5] = y - getWorldHeight(world, pos.south(zSize).west(xSize));

        samples[6] = y - getWorldHeight(world, pos.north(zSize));
        samples[7] = y - getWorldHeight(world, pos.north(zSize).east(xSize));
        samples[8] = y - getWorldHeight(world, pos.north(zSize).west(xSize));

        return Arrays.stream(samples).max().getAsInt();
    }
}
