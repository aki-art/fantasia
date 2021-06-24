package io.github.akiart.fantasia.common.item;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.entity.item.FBoatEntity;
import io.github.akiart.fantasia.common.item.foods.Foods;
import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import io.github.akiart.fantasia.common.item.itemType.FBoatItem;
import io.github.akiart.fantasia.common.item.itemType.frostWork.FrostworkBoltItem;
import io.github.akiart.fantasia.common.item.itemType.IcicleItem;
import io.github.akiart.fantasia.common.item.itemType.JavelinItem;
import io.github.akiart.fantasia.common.item.itemType.PtarmiganEggItem;
import io.github.akiart.fantasia.common.item.registrySet.CrystalRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.StoneItemSet;
import io.github.akiart.fantasia.common.item.registrySet.StoneRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.TreeRegistryItem;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Fantasia.ID);

	// Food
	public static final RegistryObject<Item> ROAST_PTARMIGAN = ItemRegistryUtil.registerFood("roast_ptarmigan", Foods.ROAST_PTARMIGAN);
	public static final RegistryObject<Item> RAW_PTARMIGAN = ItemRegistryUtil.registerFood("raw_ptarmigan", Foods.RAW_PTARMIGAN);
	public static final RegistryObject<Item> PTARMIGAN_STEW = ItemRegistryUtil.registerFood("ptarmigan_stew", Foods.PTARMIGAN_STEW);

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

	public static final RegistryObject<FBoatItem> FROZEN_ELM_BOAT = ItemRegistryUtil.register("frozen_elm_boat", () ->
			new FBoatItem(FBoatEntity.FROZEN_ELM, new Item.Properties().stacksTo(1).tab(FItemGroup.FANTASIA)));

	// Bushes
	public static final RegistryObject<BlockItem> SNOWBERRY_BUSH = ItemRegistryUtil.registerFromBlock(FBlocks.SNOWBERRY_BUSH);

	// Tools

	// Javelins
	public static final RegistryObject<BlockItem> ICICLE = ItemRegistryUtil.register("icicle", () -> new IcicleItem(FBlocks.ICICLE.get(), new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));

	public static final RegistryObject<JavelinItem> WOODEN_JAVELIN = ItemRegistryUtil.register("wooden_javelin", () -> new JavelinItem(ItemTier.WOOD, 3 , -2.4f,new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<JavelinItem> STONE_JAVELIN = ItemRegistryUtil.register("stone_javelin", () -> new JavelinItem(ItemTier.STONE, 3 , -2.4f,new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<JavelinItem> GOLD_JAVELIN = ItemRegistryUtil.register("gold_javelin", () -> new JavelinItem(ItemTier.GOLD, 3 , -2.4f,new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<JavelinItem> IRON_JAVELIN = ItemRegistryUtil.register("iron_javelin", () -> new JavelinItem(ItemTier.IRON, 3 , -2.4f,new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<JavelinItem> DIAMOND_JAVELIN = ItemRegistryUtil.register("diamond_javelin", () -> new JavelinItem(ItemTier.DIAMOND, 3 , -2.4f,new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<JavelinItem> NETHERITE_JAVELIN = ItemRegistryUtil.register("netherite_javelin", () -> new JavelinItem(ItemTier.NETHERITE, 3 , -2.4f,new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<JavelinItem> WOLFRAMITE_JAVELIN = ItemRegistryUtil.register("wolframite_javelin", () -> new JavelinItem(FItemTier.WOLFRAMITE, 3 , -2.4f,new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<JavelinItem> GHASTLY_JAVELIN = ItemRegistryUtil.register("ghastly_javelin", () -> new JavelinItem(FItemTier.GHASTLY, 3 , -2.4f,new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<FrostworkBoltItem> FROSTWORK_BOLT = ItemRegistryUtil.register("frostwork_bolt", () -> new FrostworkBoltItem(0 , -2.4f,new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));

	// Misc
	public static final RegistryObject<BlockItem> FROZEN_DIRT = ItemRegistryUtil.registerFromBlock(FBlocks.FROZEN_DIRT);
	public static final RegistryObject<BlockItem> FANTASIA_PORTAL_BLOCK = ItemRegistryUtil.registerFromBlock(FBlocks.FANTASIA_PORTAL_BLOCK);
	public static final RegistryObject<PtarmiganEggItem> PTARMIGAN_EGG = ItemRegistryUtil.register("ptarmigan_egg", () -> new PtarmiganEggItem(new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	
	// Test & Debug
	public static final RegistryObject<BlockItem> TEST_CRYSTAL_LENS = ItemRegistryUtil.registerFromBlock(FBlocks.TEST_CRYSTAL_LENS);
}
