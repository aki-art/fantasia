package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

import java.util.Random;

public class TopSurfaceBuilder extends FSurfaceBuilder<CaveBiomeSBConfig> {

    public TopSurfaceBuilder(Codec<CaveBiomeSBConfig> codec) {
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
        int j = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int chunkX = worldX & 15;
        int chunkZ = worldZ & 15;

        for(int y = startHeight; y >= 0; --y) {

            blockPos.set(chunkX, y, chunkZ);

            if (!isMatchingBuilder(chunkIn, worldX, y, worldZ, biomeIn, blockPos))
                continue;

            BlockState state = chunkIn.getBlockState(blockPos);

            if (isAir(worldX, y, worldZ)) {
                i = -1;
                chunkIn.setBlockState(blockPos, air, false);
            } else if (state.is(defaultBlock.getBlock())) {
                if (i == -1) {
                    if (j <= 0) {
                        top = Blocks.AIR.defaultBlockState();
                        under = defaultBlock;
                    } else if (y >= sealevel - 4 && y <= sealevel + 1) {
                        top =  config.getTopMaterial();
                        under = config.getUnderMaterial();
                    }

                    if (y < sealevel && (top == null || top.isAir())) {
                        if (biomeIn.getTemperature(blockPos.set(worldX, y, worldZ)) < 0.15F) {
                            top = Blocks.ICE.defaultBlockState();
                        } else {
                            top = defaultFluid;
                        }

                        blockPos.set(chunkX, y, chunkZ);
                    }

                    i = j;
                    if (y >= sealevel - 1) {
                        chunkIn.setBlockState(blockPos, top, false);
                    } else {
                        chunkIn.setBlockState(blockPos, under, false);
                    }
                } else if (i > 0) {
                    --i;
                    chunkIn.setBlockState(blockPos, base, false);
                }
            }
        }
    }
}
