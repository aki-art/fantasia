package io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave;

import io.github.akiart.fantasia.common.util.DirectionRestriction;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.block.AbstractBlock.Properties;

public class CrystalColumnBlock extends SpeleothemBlock {

	public CrystalColumnBlock(DirectionRestriction restriction, float altMiddleChance, Properties properties) {
		super(restriction, altMiddleChance, properties);
	}

	public CrystalColumnBlock(float altMiddleChance, Properties properties) {
		this(DirectionRestriction.NONE, altMiddleChance, properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}
}
