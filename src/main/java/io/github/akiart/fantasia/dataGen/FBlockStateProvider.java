package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FBlockStateProvider extends FBlockStateProviderBase {

	public FBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Fantasia.ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		
		snowyBlock(FBlocks.FROZEN_DIRT.get());

		registerStones();
		BlockRegistryUtil.getCrystals().forEach(this::crystal);
		
		registerPlants();
		
		speleothemBlock(FBlocks.ICICLE);
		simpleBlock(FBlocks.FANTASIA_PORTAL_BLOCK.get());
		
		registerDebugStuff();
	}

	private void registerStones() {
		BlockRegistryUtil.getStones().forEach(this::stones);
		speleothemBlock(FBlocks.EDELSTONE_SPELEOTHEM);
	}

	private void registerTrees() {
		BlockRegistryUtil.getTrees().forEach(this::tree);
	}

	private void registerPlants() {
		registerTrees();
		berryBush(FBlocks.SNOWBERRY_BUSH_BOTTOM.get(), FBlocks.SNOWBERRY_BUSH_TOP.get());
	}

	private void registerDebugStuff() {
		simpleBlock(FBlocks.TEST_CRYSTAL_LENS.get());
		ResourceLocation tex = new ResourceLocation("block/acacia_planks");
		simpleBlock(FBlocks.sign.get(), models().getBuilder(getName(FBlocks.sign.get())).texture("particle", tex));
		simpleBlock(FBlocks.wallSign.get(), models().getBuilder(getName(FBlocks.wallSign.get())).texture("particle", tex));
		simpleBlock(FBlocks.testChest.get(), models().getBuilder(getName(FBlocks.testChest.get())).texture("particle", tex));
	}
}
