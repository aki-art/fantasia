package io.github.akiart.fantasia.common.block.blockType.crystalLens;

import java.util.HashMap;
import java.util.Random;
import java.util.function.Supplier;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants.BlockFlags;

import net.minecraft.block.AbstractBlock.Properties;

public abstract class AbstractFunctionalLensBlock extends AbstractCrystalPaneBlock {

	protected static final int MAX_SEARCH_LENGTH = 16;

	// TODO: expose these are datapack recipes
	protected HashMap<ResourceLocation, Supplier<? extends Block>> conversionMap;
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	protected int minLightLevel = 7;

	public AbstractFunctionalLensBlock(Properties builder) {
		super(builder);
	}

	public ActionResultType onBlockActivated(World worldIn, PlayerEntity player, Hand handIn,
		BlockRayTraceResult resultIn) {

		if (worldIn.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			Fantasia.LOGGER.info("activat");

			return ActionResultType.CONSUME;
		}
	}

	public abstract void blockEffect(BlockState lensState, BlockPos pos, ServerWorld world);

	// !! do not cache at constructor, particles are not registered yet
	public abstract IParticleData getParticle();

	public int getMinLightLevel() {
		return minLightLevel;
	}

	protected BlockPos findTargetBlock(BlockState state, BlockPos start, ServerWorld world) {

		if (!isSufficientlyLit(state, start, world))
			return null;

		Direction direction = state.getValue(FACING);
		for (int offset = 1; offset <= MAX_SEARCH_LENGTH; offset++) {
			BlockPos offsetPos = start.relative(direction, offset);
			if (!world.isEmptyBlock(offsetPos)) {
				return offsetPos;
			}
		}

		return null;
	}

	public void setActive(BlockPos pos, BlockState state, IWorld world, boolean value) {
		world.setBlock(pos, state.setValue(ACTIVE, value), BlockFlags.DEFAULT);
	}

	protected int getNeighborEmission(Direction direction, BlockPos pos, ServerWorld world) {
		return world.getBlockState(pos.relative(direction)).getLightValue(world, pos);
	}

	protected int getNeighborLight(Direction direction, BlockPos pos, ServerWorld world) {
		return world.getMaxLocalRawBrightness(pos.relative(direction));
	}

	private Direction getBrighterNeighbor(Direction axis, BlockPos pos, ServerWorld world) {
		Direction b = axis.getOpposite();

		// If there is a lightsource right next to the lens, use that
		int isASource = getNeighborEmission(axis, pos, world);
		int isBSource = getNeighborEmission(b, pos, world);

		if (isASource != isBSource) {
			return isASource < isBSource ? axis : b;
		}

		// look for light level right next to block
		int lightA = getNeighborLight(axis, pos, world);
		int lightB = getNeighborLight(b, pos, world);

		// if they are equal, look one block further out
		if (lightA == lightB) {
			int lightA2 = getNeighborLight(axis, pos.relative(axis), world);
			int lightB2 = getNeighborLight(b, pos.relative(b), world);

			if (lightA2 != lightB2) {
				return lightA2 < lightB2 ? axis : b;
			}
			// everything is equally lit, it cancels out
			else
				return null;
		}

		return lightA < lightB ? axis : b;
	}

	private boolean isUnderStrongSunlight(BlockPos pos, ServerWorld world) {
		return world.getBrightness(LightType.SKY, pos) > minLightLevel;
	}

	protected boolean isSufficientlyLit(BlockState state, BlockPos pos, ServerWorld world) {
		Direction axis = state.getValue(FACING);

		// Sunlight trumps the rest
//		if (axis == Direction.DOWN && isUnderStrongSunlight(pos, world))
//			return Direction.DOWN;
//		else
//			return getBrighterNeighbor(state.get(FACING), pos, world);

		return world.getMaxLocalRawBrightness(pos.relative(state.getValue(FACING))) > minLightLevel;
	}

	protected boolean isActive(BlockState state) {
		return state.hasProperty(ACTIVE) && state.getValue(ACTIVE);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {

		if (!isActive(stateIn))
			return;

		double x = (double) pos.getX() + 0.5D;
		double y = (double) pos.getY() + 0.5D;
		double z = (double) pos.getZ() + 0.5D;

		Axis axis = stateIn.getValue(FACING).getAxis();
		int offset = stateIn.getValue(FACING).getAxisDirection().getStep();

		double xSpeed = 0d, ySpeed = 0d, zSpeed = 0d;

		switch (axis) {
		case X:
			xSpeed = offset;
			break;
		case Y:
			ySpeed = offset;
			break;
		case Z:
			zSpeed = offset;
			break;
		}

		worldIn.addParticle(getParticle(), x, y, z, xSpeed, ySpeed, zSpeed);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, ACTIVE);
	}
}
