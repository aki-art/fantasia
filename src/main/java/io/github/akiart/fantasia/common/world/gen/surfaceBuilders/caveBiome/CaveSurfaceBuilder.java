package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

import java.util.Random;

public class CaveSurfaceBuilder extends FSurfaceBuilder<FSurfaceBuilderConfig> {

    public CaveSurfaceBuilder(Codec<FSurfaceBuilderConfig> codec) {
        super(codec);
    }

    protected BlockState getTop(BlockState configTop, double noise) {
        return configTop;
    }

    protected BlockState getBase(BlockState configTop, double noise) {
        return configTop;
    }

    @Override
    public void apply(Random random, IChunk chunkIn, Biome biomeIn, int worldX, int worldZ, int startHeight, double noise,
                      BlockState defaultBlock, BlockState defaultFluid, int sealevel, long seed, FSurfaceBuilderConfig config) {

        BlockState top = getTop(config.getTopMaterial(), noise);
        BlockState cover = config.getUnderMaterial();
        BlockState base = config.getCaveWallMaterial();
        BlockState border = getBase(config.getBorderMaterial(), noise);
        BlockState air = config.getAirMaterial();

        BlockPos.Mutable blockPos = new BlockPos.Mutable();

        int i = -1;
        int coverDepth = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int x = worldX & 15;
        int z = worldZ & 15;

        // TODO: biomes could be layered, this assumes single occurrence
        int lowY = findY(chunkIn, 0, startHeight, x, z, biomeIn, true);
        int topY = findY(chunkIn, lowY, startHeight, x, z, biomeIn, false);


        for (int y = topY; y >= lowY; --y) {
            blockPos.set(x, y, z);

            if (chunkIn.getBlockState(blockPos).getMaterial() == Material.AIR) {
                BlockState acid = biomeIn.getTemperature(blockPos) < 0f ? Constants.BlockStates.ACID_ICE : Constants.BlockStates.ACID;
                chunkIn.setBlockState(blockPos, y > 12 ? air : acid, false);
                i = -1;
            } else {
                if(i++ == -1) {
                    chunkIn.setBlockState(blockPos, top, false);
                }
                //else if(config.getBorderThickness() > 0 && isBorder(chunkIn, lowY, topY, blockPos, biomeIn, config.getBorderThickness())) {
                //    chunkIn.setBlockState(blockPos, border, false);
                //}
                else if(i < coverDepth) {
                    chunkIn.setBlockState(blockPos, cover, false);
                }
                else chunkIn.setBlockState(blockPos, base, false);
            }
        }
    }
}
