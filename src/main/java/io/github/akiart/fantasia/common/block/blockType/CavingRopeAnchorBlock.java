package io.github.akiart.fantasia.common.block.blockType;

import io.github.akiart.fantasia.common.util.RotatableBoxVoxelShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CavingRopeAnchorBlock extends HorizontalFaceBlock {

    private static final RotatableBoxVoxelShape SHAPES = RotatableBoxVoxelShape.create(5.0D, 0.0D, 10.0D, 11.0D, 10.0D, 16.0D);

    public CavingRopeAnchorBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
    }

    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.getValue(FACE), state.getValue(FACING));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, FACE);
    }
}
