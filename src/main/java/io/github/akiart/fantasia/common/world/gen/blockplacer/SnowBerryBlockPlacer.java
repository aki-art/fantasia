package io.github.akiart.fantasia.common.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.common.block.blockType.plants.SnowBerryBushBottomBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class SnowBerryBlockPlacer extends BlockPlacer {
    public static final Codec<SnowBerryBlockPlacer> CODEC;
    public static final SnowBerryBlockPlacer INSTANCE = new SnowBerryBlockPlacer();

    static {
        CODEC = Codec.unit(() -> INSTANCE);
    }

    @Override
    public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
        Block block = state.getBlock();
        if(block instanceof SnowBerryBushBottomBlock) {
            ((SnowBerryBushBottomBlock) state.getBlock()).placeAt(world, pos, Constants.BlockFlags.BLOCK_UPDATE, random.nextInt(4));
        }
    }

    @Override
    protected BlockPlacerType<?> type() {
        return FBlockPlacerTypes.SNOWBERRY_BLOCK_PLACER.get();
    }
}
