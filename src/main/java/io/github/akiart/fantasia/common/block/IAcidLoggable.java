package io.github.akiart.fantasia.common.block;

import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.state.properties.FBlockStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public interface IAcidLoggable  extends IBucketPickupHandler, ILiquidContainer {

    default boolean isAcidLogged(BlockState state) {
        return state.getValue(FBlockStateProperties.ACIDLOGGED);
    }

    default boolean canPlaceLiquid(IBlockReader level, BlockPos pos, BlockState state, Fluid fluid) {
        return !isAcidLogged(state) && fluid == FFluids.ACID_SOURCE.get();
    }

    default boolean placeLiquid(IWorld pLevel, BlockPos pPos, BlockState state, FluidState pFluidState) {
        if (!isAcidLogged(state) && pFluidState.getType() == FFluids.ACID_SOURCE.get()) {

            if (!pLevel.isClientSide()) {
                pLevel.setBlock(pPos, state.setValue(FBlockStateProperties.ACIDLOGGED, true), 3);
                pLevel.getLiquidTicks().scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
            }

            return true;
        }

        return false;
    }

    default Fluid takeLiquid(IWorld world, BlockPos pos, BlockState state) {
        if (isAcidLogged(state)) {
            world.setBlock(pos, state.setValue(FBlockStateProperties.ACIDLOGGED, false), 3);
            return FFluids.ACID_SOURCE.get();
        } else {
            return Fluids.EMPTY;
        }
    }
}