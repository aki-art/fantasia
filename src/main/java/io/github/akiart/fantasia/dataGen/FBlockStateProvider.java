package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FBlockStateProvider extends FBlockStateProviderBase {

	public FBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Fantasia.ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		
		snowyBlock(FBlocks.FROZEN_DIRT.get());
		registerStones();
		BlockRegistryUtil.getCrystals().forEach(b -> crystal(b));
		
		registerPlants();
		
		speleothemBlock(FBlocks.ICICLE);
		simpleBlock(FBlocks.FANTASIA_PORTAL_BLOCK.get());
		
		registerDebugStuff();
	}

	private void registerStones() {
		BlockRegistryUtil.getStones().forEach(b -> stones(b));
		speleothemBlock(FBlocks.EDELSTONE_SPELEOTHEM);
	}

	private void registerTrees() {
		BlockRegistryUtil.getTrees().forEach(b -> tree(b));
	}

	private void registerPlants() {
		registerTrees();
		doublePlantBlock(FBlocks.SNOWBERRY_BUSH.get());
	}

	private void registerDebugStuff() {
		simpleBlock(FBlocks.TEST_CRYSTAL_LENS.get());
	}
}
