package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.item.ItemRegistryUtil;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FItemModelProvider extends FItemModelProviderBase {

	public FItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Fantasia.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		ItemRegistryUtil.stones.forEach(b -> stone(b));
		ItemRegistryUtil.crystals.forEach(b -> crystal(b));
		ItemRegistryUtil.trees.forEach(b -> tree(b));
		
		withExistingParent(getName(FItems.EDELSTONE_SPELEOTHEM.get()), getBlockLocation("edelstone_speleothem_tip"));
		withExistingParent(getName(FItems.ICICLE.get()), getBlockLocation("icicle_tip"));
		withExistingParent(getName(FItems.SNOWBERRY_BUSH.get()), getBlockLocation("snowberry_bush_top"));
		registerDebugStuff();
	}
	
	private void registerDebugStuff() {
		fromBlock(FBlocks.TEST_CRYSTAL_LENS.get());
	}
}
