package io.github.akiart.fantasia.common.block.blockType.crystalLens;

import io.github.akiart.fantasia.common.tileentity.CrystalLensTileEntity;
import io.github.akiart.fantasia.common.tileentity.FTileEntityTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class TestCrystalLensBlock extends Block {

	private static final VoxelShape TEST = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);
	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

	public TestCrystalLensBlock(Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
	}

	public TestCrystalLensBlock() {
		super(AbstractBlock.Properties.copy(Blocks.GLASS));
	}
		   
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return TEST;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getClickedFace());
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player,
		Hand hand, BlockRayTraceResult hit) {

		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		}

		TileEntity tileEntity = world.getBlockEntity(pos);
		if (tileEntity instanceof CrystalLensTileEntity) {
			((CrystalLensTileEntity) tileEntity).test();
			return ActionResultType.CONSUME;
		}

		return ActionResultType.PASS;
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return FTileEntityTypes.CRYSTAL_LENS.get().create();
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
