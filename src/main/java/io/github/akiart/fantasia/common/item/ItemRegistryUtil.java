package io.github.akiart.fantasia.common.item;

import java.util.HashSet;
import java.util.function.Supplier;

import io.github.akiart.fantasia.common.block.registrySet.CrystalRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneSet;
import io.github.akiart.fantasia.common.block.registrySet.TreeRegistryObject;
import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import io.github.akiart.fantasia.common.item.registrySet.CrystalRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.StoneItemSet;
import io.github.akiart.fantasia.common.item.registrySet.StoneRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.TreeRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;

public class ItemRegistryUtil {

	public static HashSet<StoneRegistryItem> stones = new HashSet<>();
	public static HashSet<RegistryObject<BlockItem>> blockItems = new HashSet<>();
	public static HashSet<CrystalRegistryItem> crystals = new HashSet<>();
	public static HashSet<TreeRegistryItem> trees = new HashSet<>();
	public static HashSet<RegistryObject<? extends Item>> simpleItems = new HashSet<>();

	public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
		RegistryObject<T> reg = FItems.ITEMS.register(name, item);
		simpleItems.add(reg);
		return reg;
	}

	public static RegistryObject<Item> registerFood(String id, Food food) {
		return register(id, () -> new Item(new Item.Properties()
				.tab(FItemGroup.FANTASIA)
				.food(Foods.COOKED_CHICKEN)));
	}

	public static RegistryObject<BlockItem> registerFromBlock(RegistryObject<? extends Block> parent) {
		return FItems.ITEMS.register(parent.getId().getPath(),
			() -> new BlockItem(parent.get(), new Item.Properties().tab(FItemGroup.FANTASIA)));
	}

	public static StoneRegistryItem registerStoneItems(StoneRegistryObject parent) {
		StoneRegistryItem obj = new StoneRegistryItem(parent);
		stones.add(obj);
		return obj;
	}

	public static CrystalRegistryItem registerCrystalItems(CrystalRegistryObject parent) {
		CrystalRegistryItem obj = new CrystalRegistryItem(parent);
		crystals.add(obj);
		return obj;
	}

	public static TreeRegistryItem registerTreeItems(TreeRegistryObject parent) {
		TreeRegistryItem obj = new TreeRegistryItem(parent);
		trees.add(obj);
		return obj;
	}

	public static StoneItemSet createStoneItemSet(StoneSet parent) {
		return new StoneItemSet(parent);
	}

	public static RegistryObject<BlockItem> registerSimpleBlockItem(RegistryObject<? extends Block> block) {
		RegistryObject<BlockItem> item = registerFromBlock(block);
		blockItems.add(item);
		return item;
	}
}
