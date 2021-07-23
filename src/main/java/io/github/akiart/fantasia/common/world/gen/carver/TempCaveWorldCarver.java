package io.github.akiart.fantasia.common.world.gen.carver;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome.FSurfaceBuilderConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class TempCaveWorldCarver extends CaveWorldCarver {
    public TempCaveWorldCarver(Codec<ProbabilityConfig> config, int seed) {
        super(config, seed);
    }

    @Override
    protected boolean canReplaceBlock(BlockState state) {
        return state.getMaterial() == Material.STONE;
    }

    @Override
    protected boolean canReplaceBlock(BlockState solid, BlockState fluid) {
        return canReplaceBlock(solid) || (solid.is(Blocks.SAND) || solid.is(Blocks.GRAVEL)) && !fluid.getFluidState().is(FluidTags.WATER);
    }

    @Override
    protected boolean carveBlock(IChunk chunk, Function<BlockPos, Biome> getBiome, BitSet p_230358_3_, Random p_230358_4_, BlockPos.Mutable blockPos, BlockPos.Mutable p_230358_6_, BlockPos.Mutable p_230358_7_, int p_230358_8_, int p_230358_9_, int p_230358_10_, int x, int z, int p_230358_13_, int y, int p_230358_15_, MutableBoolean isSurface) {
        int i = p_230358_13_ | p_230358_15_ << 4 | y << 8;
        if (p_230358_3_.get(i)) {
            return false;
        } else {
            p_230358_3_.set(i);
            blockPos.set(x, y, z);
            BlockState blockstate = chunk.getBlockState(blockPos);
            BlockState blockstate1 = chunk.getBlockState(p_230358_6_.setWithOffset(blockPos, Direction.UP));
            if (blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(Blocks.MYCELIUM)) {
                isSurface.setTrue();
            }

            if (!canReplaceBlock(blockstate, blockstate1)) {
                return false;
            } else {
                if (y < 11) {
                    chunk.setBlockState(blockPos, FBlocks.ACID.get().defaultBlockState(), false);
                } else {
                    ISurfaceBuilderConfig surfaceBuilderConfig = getBiome.apply(blockPos).getGenerationSettings().getSurfaceBuilderConfig();
                    BlockState air = surfaceBuilderConfig instanceof FSurfaceBuilderConfig ? ((FSurfaceBuilderConfig)surfaceBuilderConfig).getAirMaterial() : CAVE_AIR;
                    chunk.setBlockState(blockPos, air, false);
                    if (isSurface.isTrue()) {
                        p_230358_7_.setWithOffset(blockPos, Direction.DOWN);
                        if (chunk.getBlockState(p_230358_7_).is(Blocks.DIRT)) {
                            chunk.setBlockState(p_230358_7_, surfaceBuilderConfig.getTopMaterial(), false);
                        }
                    }
                }

                return true;
            }
        }
    }
}
