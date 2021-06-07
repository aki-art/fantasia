package io.github.akiart.fantasia.common.block.blockType.crystalLens;

import javax.annotation.Nullable;

import io.github.akiart.fantasia.common.util.RotatableBoxVoxelShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class AbstractCrystalPaneBlock extends Block  {

	public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
	
	private static final float DAY_LENGTH = 24000f;
	private static final float BEACON_COLOR_SPEED = 200f;
	
	private final RotatableBoxVoxelShape VOXEL_SHAPE = RotatableBoxVoxelShape.createXZSymmetric(0D, 7D, 9D);

	public AbstractCrystalPaneBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
	}

	public abstract Vector3d getBeaconColorA();
	public abstract Vector3d getBeaconColorB();


	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VOXEL_SHAPE.getForDirection(state.getValue(FACING));
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	private Vector3d lerp(Vector3d a, Vector3d b, float t) {
		return a.add((b.subtract(a).multiply(t, t, t)));
	}

	private float[] getColorComponents(Vector3d vec) {
		return new float[] { (float) vec.x, (float) vec.y, (float) vec.z };
	}

	@Nullable
	public float[] getBeaconColorMultiplier(BlockState state, IWorldReader world, BlockPos pos, BlockPos beaconPos) {
		if (isLensBlock(state) && state.getValue(FACING).getAxis() == Axis.Y) {
			if (world instanceof IWorld) {
				IWorld iworld = (IWorld) world;
				float time = getDaytimeProgress(iworld);
				float t = ((float) Math.sin(time * BEACON_COLOR_SPEED) + 1f) / 2f;
				Vector3d color = lerp(getBeaconColorA(), getBeaconColorB(), t);
				return getColorComponents(color);
			} else
				return getColorComponents(getBeaconColorA());
		}

		return null;
	}

	private float getDaytimeProgress(IWorld iworld) {
		return (float) iworld.dayTime() / DAY_LENGTH; // IWorld.dayTime(): gets time of day in ticks
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		
		// if we placed on the very edge, then consider player rotation instead
//		Direction targetedFaceDirection = context.getFace();
//		BlockState targetedBlock = getTargetedBlock(context, targetedFaceDirection);
//
//		if (isLensBlock(targetedBlock)) {
//			Direction targetDir = targetedBlock.get(FACING);
//			if (targetDir == targetedFaceDirection) {
//				return this.getDefaultState().with(FACING, targetDir);
//			}
//		}
//		if (isAimingAtBeaconTop(targetedFaceDirection, targetedBlock)) {
//			return getDefaultState();
//		}
//		if (targetedFaceDirection.getAxis() == Axis.Y) {
//			return getHorizontalRotation(context);
//		}
//		return getDefaultState();
		
		return this.defaultBlockState().setValue(FACING, context.getClickedFace());
	}

	private BlockState getTargetedBlock(BlockItemUseContext context, Direction targetedFaceDirection) {
		return context.getLevel().getBlockState(context.getClickedPos().relative(targetedFaceDirection.getOpposite()));
	}

	private BlockState getHorizontalRotation(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getClockWise());
	}

	private boolean isAimingAtBeaconTop(Direction face, BlockState targetedBlock) {
		return targetedBlock.is(Blocks.BEACON) && face == Direction.UP;
	}

	private boolean isLensBlock(BlockState targetedBlock) {
		return targetedBlock.getBlock() instanceof AbstractFunctionalLensBlock;
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
