package io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave;

import io.github.akiart.fantasia.common.util.DirectionRestriction;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import net.minecraft.block.AbstractBlock.Properties;

public class IcicleBlock extends SpeleothemBlock {

	public IcicleBlock(Properties properties) {
		super(DirectionRestriction.VERTICAL_ONLY, 0f, properties);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {

		Direction facing = getConnectedDirection(state);
		BlockState blockUnder = worldIn.getBlockState(pos.relative(facing.getOpposite()));

		boolean onSelf = blockUnder.is(asBlock());
		boolean onSolid = !onSelf && canAttach(worldIn, pos, getConnectedDirection(state).getOpposite());
		boolean onLeaf = BlockTags.LEAVES.contains(blockUnder.getBlock());
		boolean onSelfAndSameDirection = isSame(facing, blockUnder);

		return onSolid || onSelfAndSameDirection || onLeaf;
	}
}
