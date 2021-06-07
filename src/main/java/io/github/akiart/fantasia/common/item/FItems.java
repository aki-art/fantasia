package io.github.akiart.fantasia.common.item;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.item.registrySet.CrystalRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.StoneItemSet;
import io.github.akiart.fantasia.common.item.registrySet.StoneRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.TreeRegistryItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Fantasia.ID);

	// Stones
	
	public static final StoneRegistryItem OBSIDIAN_BRICKS = ItemRegistryUtil.registerStoneItems(FBlocks.OBSIDIAN_BRICKS);

	public static final StoneItemSet SANGUITE = ItemRegistryUtil.createStoneItemSet(FBlocks.SANGUITE);
	public static final StoneItemSet SUT = ItemRegistryUtil.createStoneItemSet(FBlocks.SUT);
	public static final StoneItemSet EDELSTONE = ItemRegistryUtil.createStoneItemSet(FBlocks.EDELSTONE);
	public static final StoneItemSet SCALESTONE = ItemRegistryUtil.createStoneItemSet(FBlocks.SCALESTONE);
	public static final StoneItemSet MARLSTONE = ItemRegistryUtil.createStoneItemSet(FBlocks.MARLSTONE);
	public static final StoneItemSet MUDSTONE = ItemRegistryUtil.createStoneItemSet(FBlocks.MUDSTONE);
	public static final StoneItemSet PITH = ItemRegistryUtil.createStoneItemSet(FBlocks.PITH);
	
	// Stone Speleothems
	public static final RegistryObject<BlockItem> EDELSTONE_SPELEOTHEM = ItemRegistryUtil.registerSimpleBlockItem(FBlocks.EDELSTONE_SPELEOTHEM);
	
	// Crystals
	public static final CrystalRegistryItem HIEMSITE = ItemRegistryUtil.registerCrystalItems(FBlocks.HIEMSITE);
	public static final CrystalRegistryItem GEHENNITE = ItemRegistryUtil.registerCrystalItems(FBlocks.GEHENNITE);
	
	// Plants

	// GOLDEN_BIRCH_LEAVES

	// Trees
	public static final TreeRegistryItem FROZEN_ELM = ItemRegistryUtil.registerTreeItems(FBlocks.FROZEN_ELM);
	public static final TreeRegistryItem FROZEN_SPRUCE = ItemRegistryUtil.registerTreeItems(FBlocks.FROZEN_SPRUCE);

	// Bushes
	public static final RegistryObject<BlockItem> SNOWBERRY_BUSH = ItemRegistryUtil.registerFromBlock(FBlocks.SNOWBERRY_BUSH);
	
	// Misc
	public static final RegistryObject<BlockItem> FROZEN_DIRT = ItemRegistryUtil.registerFromBlock(FBlocks.FROZEN_DIRT);
	public static final RegistryObject<BlockItem> ICICLE = ItemRegistryUtil.registerSimpleBlockItem(FBlocks.ICICLE);
	
	// Test & Debug
	public static final RegistryObject<BlockItem> TEST_CRYSTAL_LENS = ItemRegistryUtil.registerFromBlock(FBlocks.TEST_CRYSTAL_LENS);
}
