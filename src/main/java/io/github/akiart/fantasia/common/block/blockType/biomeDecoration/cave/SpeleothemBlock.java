package io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave;

import java.util.Random;

import javax.annotation.Nullable;

import io.github.akiart.fantasia.common.util.DirectionRestriction;
import io.github.akiart.fantasia.common.util.RotatableBoxVoxelShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// TODO: still could use some cleanup
import net.minecraft.block.AbstractBlock.Properties;

public class SpeleothemBlock extends HorizontalFaceBlock {

	public static final EnumProperty<SpeleothemStage> STAGE = EnumProperty.create("stage", SpeleothemStage.class);

	private static final int MAX_UPDATE_DISTANCE = 4;
	private static final float FALL_DAMAGE_MULTIPLIER = 1.5f;
	private final float ALT_MIDDLE_CHANCE;
	private final DirectionRestriction RESTRICTION;

	RotatableBoxVoxelShape VOXEL_SHAPE_FULL_HEIGHT = RotatableBoxVoxelShape.createXZSymmetric(3D, 0, 16D);
	RotatableBoxVoxelShape VOXEL_SHAPE_TIP = RotatableBoxVoxelShape.createXZSymmetric(3D, 0, 12D);
	RotatableBoxVoxelShape VOXEL_SHAPE_SMOL = RotatableBoxVoxelShape.createXZSymmetric(3D, 0, 8D);

	public SpeleothemBlock(DirectionRestriction restriction, float altMiddleChance, Properties properties) {
		super(properties);
		this.ALT_MIDDLE_CHANCE = altMiddleChance;
		this.RESTRICTION = restriction;
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, SpeleothemStage.TINY).setValue(FACE, AttachFace.CEILING));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

		AttachFace face = state.getValue(FACE);
		Direction direction = state.getValue(FACING);

		switch (state.getValue(STAGE)) {
		case TINY:
		case SMALLTIP:
			return VOXEL_SHAPE_SMOL.get(face, direction);
		case TIP:
			return VOXEL_SHAPE_TIP.get(face, direction);
		default:
			return VOXEL_SHAPE_FULL_HEIGHT.get(face, direction);
		}
	}

	@Override
	public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		entityIn.causeFallDamage(fallDistance, FALL_DAMAGE_MULTIPLIER);
	}

	@Override
	public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		scheduleATick(worldIn, pos);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world,
		BlockPos currentPos, BlockPos facingPos) {
		scheduleATick(world, currentPos);

		return !state.canSurvive(world, currentPos) ? Blocks.AIR.defaultBlockState()
			: super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	private void scheduleATick(IWorld worldIn, BlockPos pos) {
		if (!worldIn.isClientSide()) {
			worldIn.getBlockTicks().scheduleTick(pos, this, 1);
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		if (destroyIfInvalid(state, world, pos))
			return;

		SpeleothemStage stage = getStageForWorldPosition(state, world, pos, rand, getConnectedDirection(state));
		trySetState(state, pos, stage, world);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		for (Direction direction : context.getNearestLookingDirections()) {

			if (!RESTRICTION.allowedDirection(direction))
				continue;

			BlockState blockstate;
			Direction dir;
			AttachFace face;

			if (direction.getAxis() == Direction.Axis.Y) {
				face = direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR;
				dir = context.getHorizontalDirection();
			}
			else {
				face = AttachFace.WALL;
				dir = direction.getOpposite();
			}

			blockstate = this.defaultBlockState().setValue(FACE, face).setValue(FACING, dir);

			if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
				return blockstate;
			}
		}

		return null;
	}

	private SpeleothemStage getStageForWorldPosition(BlockState state, ServerWorld world, BlockPos pos, Random rand,
		Direction facing) {
		int below = countInDirection(facing.getOpposite(), state, pos, world);
		int above = countInDirection(facing, state, pos, world);
		int length = below + above + 1;

		return getStageForRelativePosition(rand, below, above, length);
	}

	public SpeleothemStage getStageForRelativePosition(Random rand, int below, int above, int length) {
		SpeleothemStage stage;
		switch (length) {
		case 1:
			stage = SpeleothemStage.TINY;
			break;
		case 2:
			stage = below == 0 ? SpeleothemStage.SMALLBASE : SpeleothemStage.SMALLTIP;
			break;
		default:
			if (below == 0) {
				stage = SpeleothemStage.BASE;
			}
			else if (above == 0) {
				stage = SpeleothemStage.TIP;
			}
			else
				stage = getMiddlePiece(rand);
			break;
		}
		return stage;
	}

	private boolean destroyIfInvalid(BlockState state, ServerWorld world, BlockPos pos) {
		if (!state.canSurvive(world, pos)) {
			world.destroyBlock(pos, true);
			return true;
		}
		return false;
	}

	private SpeleothemStage getMiddlePiece(Random rand) {
		return hasAltMiddle() && rand.nextFloat() < ALT_MIDDLE_CHANCE ? SpeleothemStage.MIDDLE_ALT : SpeleothemStage.MIDDLE;
	}

	private void trySetState(BlockState state, BlockPos pos, SpeleothemStage newStage, IWorld world) {
		if (isStageDifferent(newStage, state.getValue(STAGE)))
			world.setBlock(pos, state.setValue(STAGE, newStage), 2);
	}

	protected int countInDirection(Direction facing, BlockState state, BlockPos pos, IWorld world) {
		int i;
		Direction blockFacing = getConnectedDirection(state);
		for (i = 0; i <= MAX_UPDATE_DISTANCE && isSameInDirectionAndOffset(facing, pos, world, i, blockFacing); ++i) {
		}
		return i;
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {

		Direction facing = getConnectedDirection(state);
		BlockState blockUnder = worldIn.getBlockState(pos.relative(facing.getOpposite()));

		boolean onSelf = blockUnder.is(asBlock());
		boolean onSolid = !onSelf && canAttach(worldIn, pos, getConnectedDirection(state).getOpposite());
		boolean onSelfAndSameDirection = isSame(facing, blockUnder);

		return onSolid || onSelfAndSameDirection;
	}

	public boolean hasAltMiddle() {
		return ALT_MIDDLE_CHANCE > 0;
	}

	private boolean isStageDifferent(SpeleothemStage stage, SpeleothemStage currentStage) {
		return currentStage != stage && !(currentStage.isMiddlePiece() && stage.isMiddlePiece());
	}

	protected boolean isSame(Direction facing, BlockState state) {
		return state.is(asBlock()) && tryGetFacing(state) == facing;
	}

	private boolean isSameInDirectionAndOffset(Direction originalFacing, BlockPos pos, IWorld world, int offset,
		Direction blockFacing) {
		return isSame(blockFacing, world.getBlockState(pos.relative(originalFacing, offset + 1)));
	}

	protected Direction tryGetFacing(BlockState state) {
		return state.hasProperty(FACE) && state.hasProperty(FACING) ? getConnectedDirection(state) : null;
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(STAGE, FACING, FACE);
	}
}
