package io.github.akiart.fantasia.common.item.registrySet;

import io.github.akiart.fantasia.common.block.registrySet.TreeRegistryObject;
import io.github.akiart.fantasia.common.item.ItemRegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class TreeRegistryItem {

	private final TreeRegistryObject tree;

	public final RegistryObject<Item> boat;
	public final RegistryObject<BlockItem> button;
	// public final RegistryObject<BlockItem> chest;
	public final RegistryObject<BlockItem> door;
	public final RegistryObject<BlockItem> fence;
	public final RegistryObject<BlockItem> fenceGate;
	public final RegistryObject<BlockItem> leaves;
	public final RegistryObject<BlockItem> log;
	public final RegistryObject<BlockItem> planks;
	public final RegistryObject<BlockItem> pressurePlate;
	public final RegistryObject<BlockItem> sapling;
	public final RegistryObject<BlockItem> sign;
	public final RegistryObject<BlockItem> slab;
	public final RegistryObject<BlockItem> stairs;
	public final RegistryObject<BlockItem> strippedLog;
	public final RegistryObject<BlockItem> strippedWood;
	public final RegistryObject<BlockItem> trapDoor;
	public final RegistryObject<BlockItem> wood;

	public TreeRegistryItem(TreeRegistryObject tree) {

		this.tree = tree;

		button = tryRegister(tree.button);
		boat = null;
		// chest = tryRegister("_chest", tree.chest);
		door = tryRegister(tree.door);
		fence = tryRegister(tree.fence);
		fenceGate = tryRegister(tree.fenceGate);
		leaves = tryRegister(tree.leaves);
		log = tryRegister(tree.log);
		planks = tryRegister(tree.planks);
		pressurePlate = tryRegister(tree.pressurePlate);
		sapling = tryRegister(tree.sapling);
		sign = null;
		slab = tryRegister(tree.slab);
		stairs = tryRegister(tree.stairs);
		strippedLog = tryRegister(tree.strippedLog);
		strippedWood = tryRegister(tree.strippedWood);
		trapDoor = tryRegister(tree.trapDoor);
		wood = tryRegister(tree.wood);
	}

	public TreeRegistryObject getTree() {
		return this.tree;
	}

	private RegistryObject<BlockItem> tryRegister(RegistryObject<? extends Block> block) {
		return block != null ? ItemRegistryUtil.registerFromBlock(block) : null;
	}

	public String getName() {
		return this.tree.getName();
	}
}
