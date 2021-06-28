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
	public static final TreeRegistryItem ELM = ItemRegistryUtil.registerTreeItems(FBlocks.ELM);

	// Boats
	public static final RegistryObject<FBoatItem> FROZEN_ELM_BOAT = ItemRegistryUtil.registerBoat("frozen_elm_boat", FBoatEntity.Type.FROZEN_ELM);
	public static final RegistryObject<FBoatItem> FROZEN_SPRUCE_BOAT = ItemRegistryUtil.registerBoat("frozen_spruce_boat", FBoatEntity.Type.FROZEN_SPRUCE);
	public static final RegistryObject<FBoatItem> ELM_BOAT = ItemRegistryUtil.registerBoat("elm_boat", FBoatEntity.Type.ELM);

	// Bushes
	public static final RegistryObject<BlockItem> SNOWBERRY_BUSH = ItemRegistryUtil.registerFromBlock(FBlocks.SNOWBERRY_BUSH);

	// Tools

	// Javelins
	public static final RegistryObject<JavelinItem> WOODEN_JAVELIN = ItemRegistryUtil.registerJavelin("wooden_javelin", ItemTier.WOOD, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> STONE_JAVELIN = ItemRegistryUtil.registerJavelin("stone_javelin", ItemTier.STONE, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> GOLD_JAVELIN = ItemRegistryUtil.registerJavelin("gold_javelin", ItemTier.GOLD, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> IRON_JAVELIN = ItemRegistryUtil.registerJavelin("iron_javelin", ItemTier.IRON, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> DIAMOND_JAVELIN = ItemRegistryUtil.registerJavelin("diamond_javelin", ItemTier.DIAMOND, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> NETHERITE_JAVELIN = ItemRegistryUtil.registerJavelin("netherite_javelin", ItemTier.NETHERITE, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> WOLFRAMITE_JAVELIN = ItemRegistryUtil.registerJavelin("wolframite_javelin", FItemTier.WOLFRAMITE, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> GHASTLY_JAVELIN = ItemRegistryUtil.registerJavelin("ghastly_javelin", FItemTier.GHASTLY, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> FROSTWORK_BOLT = ItemRegistryUtil.registerJavelin("frostwork_bolt", FItemTier.WOLFRAMITE, 0 , -2.4f);

	// Misc
	public static final RegistryObject<BlockItem> FROZEN_DIRT = ItemRegistryUtil.registerFromBlock(FBlocks.FROZEN_DIRT);
	public static final RegistryObject<BlockItem> ICICLE = ItemRegistryUtil.register("icicle", () -> new IcicleItem(FBlocks.ICICLE.get(), new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<BlockItem> FANTASIA_PORTAL_BLOCK = ItemRegistryUtil.registerFromBlock(FBlocks.FANTASIA_PORTAL_BLOCK);
	public static final RegistryObject<PtarmiganEggItem> PTARMIGAN_EGG = ItemRegistryUtil.register("ptarmigan_egg", () -> new PtarmiganEggItem(new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	
	// Test & Debug
	public static final RegistryObject<BlockItem> TEST_CRYSTAL_LENS = ItemRegistryUtil.registerFromBlock(FBlocks.TEST_CRYSTAL_LENS);
	public static final RegistryObject<SignItem> sign = ItemRegistryUtil.register("test_sign", () -> new SignItem(new Item.Properties()
				.tab(FItemGroup.FANTASIA)
				.stacksTo(16),
				FBlocks.sign.get(),
				FBlocks.wallSign.get()));

}
