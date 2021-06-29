package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.lib.FastNoiseLite;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

import java.util.Random;

public class GrimCapSurfaceBuilder extends FSurfaceBuilder<CaveBiomeSBConfig> {

    public GrimCapSurfaceBuilder(Codec<CaveBiomeSBConfig> codec) {
        super(codec);
    }

    @Override
    public void apply(Random random, IChunk chunkIn, Biome biomeIn, int worldX, int worldZ, int startHeight, double noise,
                      BlockState defaultBlock, BlockState defaultFluid, int sealevel, long seed, CaveBiomeSBConfig config) {

        BlockState top = config.getTopMaterial();
        BlockState under = config.getUnderMaterial();
        BlockState base = config.getCaveWallMaterial();
        BlockState border = config.getBorderMaterial();
        BlockState air = config.getAirMaterial();

        BlockPos.Mutable blockPos = new BlockPos.Mutable();

        int i = -1;
        int j = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int x = worldX & 15;
        int z = worldZ & 15;

        for (int y = startHeight; y >= 0; --y) {

            blockPos.set(x, y, z);

            if (!isMatchingBuilder(chunkIn, worldX, y, worldZ, biomeIn, blockPos))
                continue;

            if (isAir(worldX, y, worldZ)) {
                chunkIn.setBlockState(blockPos, air, false);
            } else {
                chunkIn.setBlockState(blockPos, base, false);
            }
        }
    }


}
