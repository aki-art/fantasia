package io.github.akiart.fantasia.common.item.registrySet;

import io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer.ISTERs;
import io.github.akiart.fantasia.common.block.registrySet.TreeRegistryObject;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.item.ItemRegistryUtil;
import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraftforge.fml.RegistryObject;

public class TreeRegistryItem {

	private final TreeRegistryObject tree;

	// public final RegistryObject<BlockItem> barrel;
	// public final RegistryObject<BlockItem> bookShelf;
	public final RegistryObject<BlockItem> button;
	public final RegistryObject<BlockItem> chest;
	// public final RegistryObject<BlockItem> craftingTable;
	public final RegistryObject<BlockItem> door;
	public final RegistryObject<BlockItem> fence;
	public final RegistryObject<BlockItem> fenceGate;
	// public final RegistryObject<BlockItem> ladder;
	public final RegistryObject<BlockItem> leaves;
	public final RegistryObject<BlockItem> log;
	public final RegistryObject<BlockItem> planks;
	public final RegistryObject<BlockItem> pressurePlate;
	public final RegistryObject<BlockItem> sapling;
	// public final RegistryObject<BlockItem> shelf;
	public final RegistryObject<SignItem> sign;
	public final RegistryObject<BlockItem> slab;
	public final RegistryObject<BlockItem> stairs;
	public final RegistryObject<BlockItem> strippedLog;
	public final RegistryObject<BlockItem> strippedWood;
	public final RegistryObject<BlockItem> trapDoor;
	public final RegistryObject<BlockItem> wood;

	public TreeRegistryItem(TreeRegistryObject tree) {

		this.tree = tree;

		button = tryRegisterFromBlock(tree.button);
		chest = FItems.ITEMS.register(getName() + "_chest",
				() -> new BlockItem(tree.chest.get(),
						new Item.Properties()
						.tab(FItemGroup.FANTASIA)
						.setISTER(() -> () -> ISTERs.createChestTileEntity(tree.getWoodType()))));

		door = tryRegisterFromBlock(tree.door);
		fence = tryRegisterFromBlock(tree.fence);
		fenceGate = tryRegisterFromBlock(tree.fenceGate);
		leaves = tryRegisterFromBlock(tree.leaves);
		log = tryRegisterFromBlock(tree.log);
		planks = tryRegisterFromBlock(tree.planks);
		pressurePlate = tryRegisterFromBlock(tree.pressurePlate);
		sapling = tryRegisterFromBlock(tree.sapling);
		sign = ItemRegistryUtil.register(getName() + "_sign", () -> new SignItem(new Item.Properties()
				.tab(FItemGroup.FANTASIA)
				.stacksTo(16),
				tree.sign.get(),
				tree.wallSign.get()));

		slab = tryRegisterFromBlock(tree.slab);
		stairs = tryRegisterFromBlock(tree.stairs);
		strippedLog = tryRegisterFromBlock(tree.strippedLog);
		strippedWood = tryRegisterFromBlock(tree.strippedWood);
		trapDoor = tryRegisterFromBlock(tree.trapDoor);
		wood = tryRegisterFromBlock(tree.wood);
	}

	public TreeRegistryObject getTree() {
		return tree;
	}

	private RegistryObject<BlockItem> tryRegisterFromBlock(RegistryObject<? extends Block> block) {
		return block != null ? ItemRegistryUtil.registerFromBlock(block) : null;
	}

	public String getName() {
		return tree.getName();
	}
}
