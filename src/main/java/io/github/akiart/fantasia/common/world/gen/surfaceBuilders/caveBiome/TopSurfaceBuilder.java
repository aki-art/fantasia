package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.Fantasia;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

import java.util.Random;

public class TopSurfaceBuilder extends FSurfaceBuilder<FSurfaceBuilderConfig> {

    public TopSurfaceBuilder(Codec<FSurfaceBuilderConfig> codec) {
        super(codec);
    }

    boolean testDone = false;

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
        int coverDepth = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int x = worldX & 15;
        int z = worldZ & 15;

        int lowY = findY(chunkIn, 8, startHeight, x, z, biomeIn, true); // FIXME: at 0 the same biome is set for some hacky nonsense. should be sorted out and fixed
        // Fantasia.LOGGER.info(lowY);

        for(int y = startHeight; y >= lowY; --y)  {

            blockPos.set(x, y, z);
            // BlockState state = chunkIn.getBlockState(blockPos);

            if (chunkIn.getBlockState(blockPos).getMaterial() == Material.AIR) {
                i = -1;
                chunkIn.setBlockState(blockPos, air, false);
            } else {
                if(i++ == -1) {
                    chunkIn.setBlockState(blockPos, top, false);
                }
                else if(i < coverDepth) {
                    chunkIn.setBlockState(blockPos, cover, false);
                }
                else chunkIn.setBlockState(blockPos, base, false);
            }
        }
    }
}
