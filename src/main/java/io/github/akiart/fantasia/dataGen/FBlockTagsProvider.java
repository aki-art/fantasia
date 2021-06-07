package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.block.registrySet.CrystalRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.TreeRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FBlockTagsProvider extends BlockTagsProvider {

	ITag.INamedTag<Block> BASE_STONE_FANTASIA = createTag("base_stone_fantasia");
	ITag.INamedTag<Block> CRYSTAL = createTag("crystal");

	public FBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
		super(generatorIn, Fantasia.ID, existingFileHelper);
	}

	private ITag.INamedTag<Block> createTag(String name) {
		return BlockTags.bind(new ResourceLocation(Fantasia.ID, name).toString());
	}

	@Override
	protected void addTags() {
		BlockRegistryUtil.getTrees().forEach(t -> tree(t));
		BlockRegistryUtil.getStones().forEach(s -> stone(s));
		BlockRegistryUtil.getCrystals().forEach(s -> crystal(s));

		tag(BASE_STONE_FANTASIA).add(FBlocks.EDELSTONE.raw.block.get(), FBlocks.SANGUITE.raw.block.get(),
			FBlocks.MARLSTONE.raw.block.get());

		tag(Tags.Blocks.DIRT).add(FBlocks.FROZEN_DIRT.get());
	}

	private void crystal(CrystalRegistryObject crystal) {
		tag(CRYSTAL).add(crystal.polished.get(), crystal.crystal.get(), crystal.block.get());
	}

	private void stone(StoneRegistryObject stone) {

		tag(BlockTags.WALLS).add(stone.wall.get());
		tag(BlockTags.SLABS).add(stone.slab.get());
		tag(BlockTags.STAIRS).add(stone.stairs.get());

		if (stone.hasRedStoneComponents()) {
			tag(BlockTags.BUTTONS).add(stone.button.get());
			tag(BlockTags.STONE_PRESSURE_PLATES).add(stone.pressurePlate.get());
		}
	}

	private void tree(TreeRegistryObject tree) {
		ITag.INamedTag<Block> LOGS = createTag(tree.getName() + "_logs");
		tag(LOGS).add(tree.log.get(), tree.wood.get(), tree.strippedLog.get(), tree.strippedWood.get());
		tag(BlockTags.PLANKS).add(tree.planks.get());
		tag(BlockTags.LOGS).add(tree.log.get(), tree.wood.get(), tree.strippedLog.get(),
			tree.strippedWood.get());
		tag(BlockTags.LOGS_THAT_BURN).add(tree.log.get(), tree.wood.get(), tree.strippedLog.get(),
			tree.strippedWood.get());
		tag(BlockTags.LEAVES).add(tree.leaves.get());
		tag(BlockTags.WOODEN_BUTTONS).add(tree.button.get());
		tag(BlockTags.WOODEN_DOORS).add(tree.door.get());
		tag(BlockTags.WOODEN_PRESSURE_PLATES).add(tree.pressurePlate.get());
		tag(BlockTags.WOODEN_SLABS).add(tree.slab.get());
		tag(BlockTags.WOODEN_STAIRS).add(tree.stairs.get());
		tag(BlockTags.WOODEN_FENCES).add(tree.fence.get());
		tag(BlockTags.FENCE_GATES).add(tree.fenceGate.get());
		tag(BlockTags.WOODEN_TRAPDOORS).add(tree.trapDoor.get());
	}
}
