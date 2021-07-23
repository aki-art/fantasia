package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.item.ItemRegistryUtil;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FItemModelProvider extends FItemModelProviderBase {

	public FItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Fantasia.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		ItemRegistryUtil.stones.forEach(this::stone);
		ItemRegistryUtil.crystals.forEach(this::crystal);
		ItemRegistryUtil.trees.forEach(this::tree);
		ItemRegistryUtil.simpleItems.forEach(this::miscItem);

		javelin(FItems.WOODEN_JAVELIN.get(), getBlockTexture(Blocks.OAK_PLANKS));
		javelin(FItems.STONE_JAVELIN.get(), getBlockTexture(Blocks.STONE));
		javelin(FItems.GOLD_JAVELIN.get(), getBlockTexture(Blocks.GOLD_BLOCK));
		javelin(FItems.IRON_JAVELIN.get(), getBlockTexture(Blocks.IRON_BLOCK));
		javelin(FItems.DIAMOND_JAVELIN.get(), getBlockTexture(Blocks.DIAMOND_BLOCK));
		javelin(FItems.NETHERITE_JAVELIN.get(), getBlockTexture(Blocks.NETHERITE_BLOCK));
		javelin(FItems.WOLFRAMITE_JAVELIN.get(), getBlockTexture(Blocks.NETHERITE_BLOCK));
		javelin(FItems.GHASTLY_JAVELIN.get(), getBlockTexture(Blocks.SLIME_BLOCK));
		javelin(FItems.FROSTWORK_BOLT.get(), getBlockTexture(Blocks.LAPIS_BLOCK));
		javelin(FItems.SABER_TOOTH_JAVELIN.get(), getBlockTexture(Blocks.SANDSTONE), 3);

		withExistingParent(getName(FItems.EDELSTONE_SPELEOTHEM.get()), getBlockLocation("edelstone_speleothem_tip"));
		withExistingParent(getName(FItems.ICICLE.get()), getBlockLocation("icicle_tip"));
		// withExistingParent(getName(FItems.SNOWBERRY_BUSH.get()), getBlockLocation("snowberry_bush_top"));
		fromBlock(FBlocks.FANTASIA_PORTAL_BLOCK.get());
		//fromBlock(FBlocks.CAVING_ROPE_ANCHOR.get());
		//ItemModelBuilder builder = generate(getName(FItems.CAVING_ROPE_ANCHOR.get()) + "_inventory", getItemTexture(FItems.CAVING_ROPE_ANCHOR.get()));
		//javelin(FItems.CAVING_ROPE_ANCHOR.get(), getBlockTexture(Blocks.OAK_PLANKS));
		miscItem(FItems.ACID_BUCKET);
		miscItem(FItems.CAVING_ROPE_ANCHOR);
		miscItem(FItems.ROPE);
		registerDebugStuff();
	}
	
	private void registerDebugStuff() {
		fromBlock(FBlocks.TEST_CRYSTAL_LENS.get());
		miscItem(FItems.sign);
		fromBlock(FBlocks.testChest.get());

	}
}
