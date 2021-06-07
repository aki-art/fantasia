package io.github.akiart.fantasia.common.item.registrySet;

import io.github.akiart.fantasia.common.block.registrySet.CrystalRegistryObject;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.item.ItemRegistryUtil;
import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import io.github.akiart.fantasia.common.item.itemType.CrystalItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class CrystalRegistryItem {

	private final CrystalRegistryObject crystalRegistryObject;

	public final RegistryObject<BlockItem> block;
	public final RegistryObject<BlockItem> polished;
	public final RegistryObject<BlockItem> lens;
	public final RegistryObject<BlockItem> crystal;
	public final RegistryObject<BlockItem> waxedLens;
	// public final RegistryObject<BlockItem> LANTERN;

	public CrystalRegistryItem(CrystalRegistryObject crystalSet) {
		this.crystalRegistryObject = crystalSet;
		
		block = ItemRegistryUtil.registerFromBlock(crystalSet.block);
		polished = ItemRegistryUtil.registerFromBlock(crystalSet.polished);
		lens = ItemRegistryUtil.registerFromBlock(crystalSet.lens);
		waxedLens = ItemRegistryUtil.registerFromBlock(crystalSet.waxedLens);
		crystal = FItems.ITEMS.register(
			getName() + "_crystal", () -> new CrystalItem(crystalSet.crystal.get(), new Item.Properties().tab(
				FItemGroup.FANTASIA)));

		// LANTERN = ItemRegistryUtil.registerFromBlock(crystal.lantern);
	}

	public String getName() {
		return crystalRegistryObject.getName();
	}

	public CrystalRegistryObject getCrystal() {
		return crystalRegistryObject;
	}
}
