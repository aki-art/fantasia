package io.github.akiart.fantasia.common.item.itemGroup;

import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FItemGroup {
	public static final ItemGroup FANTASIA = (new ItemGroup("fantasia") {
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon() {
			return new ItemStack(FBlocks.HIEMSITE.block.get());
		}
	}).setRecipeFolderName("fantasia");
}
