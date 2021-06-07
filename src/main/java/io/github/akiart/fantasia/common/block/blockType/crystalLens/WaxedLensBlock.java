package io.github.akiart.fantasia.common.block.blockType.crystalLens;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.block.AbstractBlock.Properties;

public class WaxedLensBlock extends AbstractCrystalPaneBlock {
	
	private AbstractFunctionalLensBlock parent;
	
	public WaxedLensBlock(Supplier<? extends AbstractFunctionalLensBlock> parent, Properties properties) {
		super(properties);
		this.parent = parent.get();
	}

	@Override
	public Vector3d getBeaconColorA() {
		return parent.getBeaconColorA();
	}

	@Override
	public Vector3d getBeaconColorB() {
		return parent.getBeaconColorB();
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip,
		ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltips.blocks.no_effect").withStyle(TextFormatting.DARK_GRAY));
		tooltip.add(new TranslationTextComponent("tooltips.blocks.safe_for_decoration").withStyle(TextFormatting.DARK_GRAY));
	}
}
