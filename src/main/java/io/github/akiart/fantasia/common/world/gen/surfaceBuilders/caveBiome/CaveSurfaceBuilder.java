package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.Fantasia;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class CaveSurfaceBuilder extends FSurfaceBuilder<FSurfaceBuilderConfig> {

    public CaveSurfaceBuilder(Codec<FSurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void apply(Random random, IChunk chunkIn, Biome biomeIn, int worldX, int worldZ, int startHeight, double noise,
                      BlockState defaultBlock, BlockState defaultFluid, int sealevel, long seed, FSurfaceBuilderConfig config) {

        BlockState top = config.getTopMaterial();
        BlockState cover = config.getUnderMaterial();
        BlockState base = config.getCaveWallMaterial();
        BlockState border = config.getBorderMaterial();
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

            if (isAir(worldX, y, worldZ)) {
                chunkIn.setBlockState(blockPos, air, false);
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
