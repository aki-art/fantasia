package io.github.akiart.fantasia.common.item.registrySet;

import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.item.ItemRegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;

public class StoneRegistryItem {

	private final StoneRegistryObject stone;
	
	public final RegistryObject<BlockItem> block;
	public final RegistryObject<BlockItem> stairs;
	public final RegistryObject<BlockItem> wall;
	public final RegistryObject<BlockItem> slab;
	public final RegistryObject<BlockItem> pressure_plate;
	public final RegistryObject<BlockItem> button;

	public StoneRegistryItem(StoneRegistryObject stone) {
		this.stone = stone;

		block = ItemRegistryUtil.registerFromBlock(stone.block);
		stairs = ItemRegistryUtil.registerFromBlock(stone.stairs);
		wall = ItemRegistryUtil.registerFromBlock(stone.wall);
		slab = ItemRegistryUtil.registerFromBlock(stone.slab);

		pressure_plate = registerOptional(stone.pressurePlate);
		button = registerOptional(stone.button);
	}
	
	public String getName() {
		return stone.getName();
	}
	
	public StoneRegistryObject getStoneRegistryObject() {
		return stone;
	}
	
	public boolean hadRedstoneComponents() {
		return stone.hasRedStoneComponents();
	}

	private <T extends Block> RegistryObject<BlockItem> registerOptional(RegistryObject<T> block) {
		return block != null ? ItemRegistryUtil.registerFromBlock(block) : null;
	}
}
