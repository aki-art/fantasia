package io.github.akiart.fantasia.common.item;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer.ISTERs;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.block.FWoodType;
import io.github.akiart.fantasia.common.entity.FEntityTypes;
import io.github.akiart.fantasia.common.entity.item.FBoatEntity;
import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.item.foods.Foods;
import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import io.github.akiart.fantasia.common.item.itemType.*;
import io.github.akiart.fantasia.common.item.itemType.frostWork.FrostworkPickaxeItem;
import io.github.akiart.fantasia.common.item.registrySet.CrystalRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.StoneItemSet;
import io.github.akiart.fantasia.common.item.registrySet.StoneRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.tree.BasicTreeRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.tree.ThinTreeRegistryItem;
import io.github.akiart.fantasia.util.Constants;
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
	public static final RegistryObject<Item> SNOW_BERRY = ItemRegistryUtil.registerFood("snow_berry", Foods.SNOW_BERRY);

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
	public static final BasicTreeRegistryItem FROZEN_ELM = ItemRegistryUtil.registerTreeItems(FBlocks.FROZEN_ELM, FWoodType.FROZEN_ELM);
	//public static final BasicTreeRegistryItem FROZEN_SPRUCE = ItemRegistryUtil.registerTreeItems(FBlocks.FROZEN_SPRUCE);
	//public static final BasicTreeRegistryItem ELM = ItemRegistryUtil.registerTreeItems(FBlocks.ELM);
	public static final ThinTreeRegistryItem ASPEN = ItemRegistryUtil.registerThinTreeItems(FBlocks.ASPEN, FWoodType.ASPEN);

	// Boats
	public static final RegistryObject<FBoatItem> FROZEN_ELM_BOAT = ItemRegistryUtil.registerBoat("frozen_elm_boat", FBoatEntity.Type.FROZEN_ELM);
	//public static final RegistryObject<FBoatItem> FROZEN_SPRUCE_BOAT = ItemRegistryUtil.registerBoat("frozen_spruce_boat", FBoatEntity.Type.FROZEN_SPRUCE);
	//public static final RegistryObject<FBoatItem> ELM_BOAT = ItemRegistryUtil.registerBoat("elm_boat", FBoatEntity.Type.ELM);
	public static final RegistryObject<FBoatItem> ASPEN_BOAT = ItemRegistryUtil.registerBoat("aspen_boat", FBoatEntity.Type.ASPEN);

	// Bushes
	public static final RegistryObject<BlockItem> SNOWBERRY_BUSH = ItemRegistryUtil.registerFromBlock(FBlocks.SNOWBERRY_BUSH_BOTTOM);

	// Tools

	// Frostwork
	public static final RegistryObject<FrostworkPickaxeItem> FROSTWORK_PICKAXE = ItemRegistryUtil.register("frostwork_pickaxe", () -> new FrostworkPickaxeItem(FItemTier.FROSTWORK, 9, 4f, (new Item.Properties().tab(FItemGroup.FANTASIA))));

	// Javelins
	public static final RegistryObject<JavelinItem> WOODEN_JAVELIN = ItemRegistryUtil.registerJavelin("wooden_javelin", ItemTier.WOOD, 0.5f , -2.4f);
	public static final RegistryObject<JavelinItem> STONE_JAVELIN = ItemRegistryUtil.registerJavelin("stone_javelin", ItemTier.STONE, 0.5f , -2.4f);
	public static final RegistryObject<JavelinItem> GOLD_JAVELIN = ItemRegistryUtil.registerJavelin("gold_javelin", ItemTier.GOLD, 0.5f , -2.4f);
	public static final RegistryObject<JavelinItem> IRON_JAVELIN = ItemRegistryUtil.registerJavelin("iron_javelin", ItemTier.IRON, 0.5f , -2.4f);
	public static final RegistryObject<JavelinItem> DIAMOND_JAVELIN = ItemRegistryUtil.registerJavelin("diamond_javelin", ItemTier.DIAMOND, 0.5f , -2.4f);
	public static final RegistryObject<JavelinItem> NETHERITE_JAVELIN = ItemRegistryUtil.registerJavelin("netherite_javelin", ItemTier.NETHERITE, 0.5f , -2.4f);
	public static final RegistryObject<JavelinItem> WOLFRAMITE_JAVELIN = ItemRegistryUtil.registerJavelin("wolframite_javelin", FItemTier.WOLFRAMITE, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> GHASTLY_JAVELIN = ItemRegistryUtil.registerJavelin("ghastly_javelin", FItemTier.GHASTLY, 3 , -2.4f);
	public static final RegistryObject<JavelinItem> FROSTWORK_BOLT = ItemRegistryUtil.registerJavelin("frostwork_bolt", FItemTier.WOLFRAMITE, 0.5f , -2.4f);
	public static final RegistryObject<SaberToothJavelinItem> SABER_TOOTH_JAVELIN = ItemRegistryUtil.registerJavelin("saber_tooth_javelin",
			() -> new SaberToothJavelinItem(0.5f, -2.4f,
					new Item.Properties()
							.stacksTo(16)
							.tab(FItemGroup.FANTASIA)
							.setISTER(() -> () -> ISTERs.createJavelinISTER("saber_tooth_javelin"))));


	// Spawn Eggs
	public static final RegistryObject<FSpawnEggItem> PTARMIGAN_SPAWN_EGG = ItemRegistryUtil.registerEgg("ptarmigan_spawn_egg", FEntityTypes.PTARMIGAN, Constants.Colors.CREAM, Constants.Colors.LIGHT_BROWN);
	public static final RegistryObject<FSpawnEggItem> VALRAVN_SPAWN_EGG = ItemRegistryUtil.registerEgg("valravn_spawn_egg", FEntityTypes.VALRAVN2, Constants.Colors.ALMOST_BLACK, Constants.Colors.RED);
	public static final RegistryObject<FSpawnEggItem> SABER_CAT_SPAWN_EGG = ItemRegistryUtil.registerEgg("saber_cat_spawn_egg", FEntityTypes.SABER_CAT, Constants.Colors.GINGER, Constants.Colors.CREAM);

	// Misc
	public static final RegistryObject<BlockItem> FROZEN_DIRT = ItemRegistryUtil.registerFromBlock(FBlocks.FROZEN_DIRT);
	public static final RegistryObject<BlockItem> ICICLE = ItemRegistryUtil.register("icicle", () -> new IcicleItem(FBlocks.ICICLE.get(), new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static final RegistryObject<BlockItem> FANTASIA_PORTAL_BLOCK = ItemRegistryUtil.registerFromBlock(FBlocks.FANTASIA_PORTAL_BLOCK);
	public static final RegistryObject<PtarmiganEggItem> PTARMIGAN_EGG = ItemRegistryUtil.register("ptarmigan_egg", () -> new PtarmiganEggItem(new Item.Properties().stacksTo(16).tab(FItemGroup.FANTASIA)));
	public static RegistryObject<Item> ACID_BUCKET = ITEMS.register("acid_bucket", () -> new BucketItem(FFluids.ACID_SOURCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(FItemGroup.FANTASIA)));

	// Test & Debug
	//public static final RegistryObject<BlockItem> ASPEN_LOG = ItemRegistryUtil.registerFromBlock(FBlocks.ASPEN_LOG);

	public static final RegistryObject<BlockItem> TEST_GLOWSTONE = ItemRegistryUtil.registerFromBlock(FBlocks.TEST_GLOWSTONE);
	public static final RegistryObject<BlockItem> TEST_CRYSTAL_LENS = ItemRegistryUtil.registerFromBlock(FBlocks.TEST_CRYSTAL_LENS);
	public static final RegistryObject<SignItem> sign = ItemRegistryUtil.register("test_sign", () -> new SignItem(new Item.Properties()
				.tab(FItemGroup.FANTASIA)
				.stacksTo(16),
				FBlocks.sign.get(),
				FBlocks.wallSign.get()));

}
