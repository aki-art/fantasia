package io.github.akiart.fantasia.common.block.registrySet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;

public class StoneRegistryObject {

	public final RegistryObject<Block> block;
	public final RegistryObject<StairsBlock> stairs;
	public final RegistryObject<SlabBlock> slab;
	public final RegistryObject<WallBlock> wall;
	public final RegistryObject<PressurePlateBlock> pressurePlate;
	public final RegistryObject<StoneButtonBlock> button;

	private final String name;

	public StoneRegistryObject(String name, float hardness, float resistance, MaterialColor color,
		boolean redstoneComponents) {

		this.name = name;

		block = BlockRegistryUtil.register(name,
			() -> new Block(AbstractBlock.Properties.of(Material.STONE, color)
				.requiresCorrectToolForDrops()
				.strength(hardness, resistance)));

		stairs = BlockRegistryUtil.register(name + "_stairs",
			() -> new StairsBlock(() -> block.get().defaultBlockState(), AbstractBlock.Properties.copy(block.get())));

		slab = BlockRegistryUtil.register(name + "_slab", () -> new SlabBlock(AbstractBlock.Properties.copy(block.get())));

		wall = BlockRegistryUtil.register(name + "_wall", () -> new WallBlock(AbstractBlock.Properties.copy(block.get())));

		pressurePlate = redstoneComponents ? BlockRegistryUtil.register(name + "_pressure_plate",
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, AbstractBlock.Properties.copy(block.get())
				.strength(0.5F))) : null;

		button = redstoneComponents ? BlockRegistryUtil.register(name + "_button",
			() -> new StoneButtonBlock(AbstractBlock.Properties.of(Material.DECORATION)
				.noCollission()
				.strength(0.5F))) : null;
	}

	public String getName() {
		return name;
	}

	public boolean hasRedStoneComponents() {
		return button != null && pressurePlate != null;
	}
		
	public List<RegistryObject<? extends Block>> getItems() {
		List<RegistryObject<? extends Block>> result = Arrays.asList(block, stairs, slab, wall);
		
		if(hasRedStoneComponents()) {
			result.add(pressurePlate);
			result.add(button);
		}

		return result;
	}
}
