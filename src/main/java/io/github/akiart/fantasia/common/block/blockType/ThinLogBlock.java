package io.github.akiart.fantasia.common.block.blockType;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.Tags;

public class ThinLogBlock extends SixWayBlock implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ThinLogBlock(AbstractBlock.Properties properties) {
        super(0.3f, properties);

        registerDefaultState(defaultBlockState()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, true)
                .setValue(DOWN, true)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    protected boolean canConnectTo(BlockState blockState) {
        Block block = blockState.getBlock();
        return block == this || BlockTags.LEAVES.contains(block) || block instanceof LeavesBlock;
    }

    protected boolean isDirt(BlockState blockState) {
        return Tags.Blocks.DIRT.contains(blockState.getBlock());
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader blockReader, BlockPos blockPos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, PathType pathType) {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState thisState, Direction direction, BlockState thatState, IWorld world, BlockPos thisBlockState, BlockPos thatBlockState) {
        boolean isConnecting = canConnectTo(thatState) || direction == Direction.DOWN && isDirt(thatState);
        return thisState.setValue(PROPERTY_BY_DIRECTION.get(direction), isConnecting);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader reader = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidState = reader.getFluidState(blockPos);

        return getStateForPlacement(reader, blockPos, fluidState);
    }

    public BlockState getStateForPlacement(IBlockReader reader, BlockPos blockPos, FluidState fluidState) {

        BlockState north = reader.getBlockState(blockPos.north());
        BlockState east = reader.getBlockState(blockPos.east());
        BlockState south = reader.getBlockState(blockPos.south());
        BlockState west = reader.getBlockState(blockPos.west());
        BlockState up = reader.getBlockState(blockPos.above());
        BlockState down = reader.getBlockState(blockPos.below());

        return defaultBlockState()
                .setValue(NORTH, canConnectTo(north))
                .setValue(EAST, canConnectTo(east))
                .setValue(SOUTH, canConnectTo(south))
                .setValue(WEST, canConnectTo(west))
                .setValue(UP, canConnectTo(up))
                .setValue(DOWN, canConnectTo(down) || isDirt(down))
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }
}
