package io.github.akiart.fantasia.common.block.blockType.crystalLens;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.particles.FParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// TODO: custom recipes
import net.minecraft.block.AbstractBlock.Properties;

public class HiemsiteLensBlock extends AbstractFunctionalLensBlock {

	private final static Vector3d lightBlue = new Vector3d(.65f, 96f, 1f);
	private final static Vector3d paleLavender = new Vector3d(.56f, .5f, 1f);

	public HiemsiteLensBlock(Properties builder) {
		super(builder);
		conversionMap = Util.make(new HashMap<>(), map -> {
			map.put(Blocks.WATER.getRegistryName(), () -> Blocks.ICE);
			map.put(Blocks.LAVA.getRegistryName(), () -> Blocks.MAGMA_BLOCK);
			map.put(Blocks.MAGMA_BLOCK.getRegistryName(), () -> Blocks.BASALT);
			map.put(Blocks.DIRT.getRegistryName(), FBlocks.FROZEN_DIRT);
		});
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		
		//TODO: remove this, this is only here to fix some old blocks crashing in testing
		if(!state.hasProperty(FACING)) {
			world.setBlockAndUpdate(pos, state.setValue(FACING, Direction.DOWN));
			return;
		}
		if (world.dimensionType().ultraWarm()) {
			world.destroyBlock(pos, true);
			return;
		}

		if (world.getMaxLocalRawBrightness(pos) < minLightLevel) {
			setActive(pos, state, world, false);
			return;
		}

		BlockPos target = findTargetBlock(state, pos, world);
		if (target != null && target != pos) {
			setActive(pos, state, world, true);
			blockEffect(state, target, world);
		} else
			setActive(pos, state, world, false);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip,
		ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltip.blocks.hiemsite_lens").withStyle(TextFormatting.DARK_GRAY));
	}

	@Override
	public void blockEffect(BlockState lensState, BlockPos effectedPos, ServerWorld world) {
		freezeBlock(lensState, effectedPos, world);
	}

	private ResourceLocation getLocation(BlockState state) {
		return state.getBlock().getRegistryName();
	}

	private BlockState getResult(ResourceLocation in) {
		return conversionMap.get(in).get().defaultBlockState();
	}

	private void freezeBlock(BlockState lensState, BlockPos pos, ServerWorld world) {
		BlockState state = world.getBlockState(pos);
		ResourceLocation target = getLocation(state);

		if (canFreeze(target, pos, world)) {
			BlockState result = getResult(target);
			if (result != null)
				world.setBlockAndUpdate(pos, result);

		} else if (state != null && lensState.getValue(FACING) == Direction.DOWN) {
			tryToSnowOn(pos, state, world);
		}
	}

	private boolean canFreeze(ResourceLocation in, BlockPos pos, ServerWorld world) {
		if (conversionMap.containsKey(in)) {
			FluidState fluidState = world.getFluidState(pos);
			return fluidState.isEmpty() || fluidState.isSource();
		}
		return false;
	}

	private void tryToSnowOn(BlockPos pos, BlockState under, ServerWorld world) {
		if (canBeSnowedOn(under, world, pos)) {
			world.setBlockAndUpdate(pos.above(), Blocks.SNOW.defaultBlockState());
		}
	}

	private boolean canBeSnowedOn(BlockState state, ServerWorld world, BlockPos pos) {
		return state.isFaceSturdy(world, pos, Direction.UP) && world.isEmptyBlock(pos.above());
	}

	@Override
	public Vector3d getBeaconColorA() {
		return lightBlue;
	}

	@Override
	public Vector3d getBeaconColorB() {
		return paleLavender;
	}

	@Override
	public IParticleData getParticle() {
		return FParticleTypes.SNOW.get();
	}
}
