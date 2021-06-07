package io.github.akiart.fantasia.common.block.registrySet;

import java.util.function.Supplier;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.blockType.FSaplingBlock;
import io.github.akiart.fantasia.common.block.trees.FTree;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

public class TreeRegistryObject {
	private final String name;
	private final WoodType woodType;

	// public final RegistryObject<Block> barrel
	// public final RegistryObject<Block> bookShelf
	public final RegistryObject<WoodButtonBlock> button;
	// public final RegistryObject<Block> chest;
	// public final RegistryObject<Block> craftingTable;
	public final RegistryObject<DoorBlock> door;
	public final RegistryObject<FenceBlock> fence;
	public final RegistryObject<FenceGateBlock> fenceGate;
	// public final RegistryObject<Block> ladder;
	public final RegistryObject<LeavesBlock> leaves;
	public final RegistryObject<RotatedPillarBlock> log;
	public final RegistryObject<Block> planks;
	public final RegistryObject<PressurePlateBlock> pressurePlate;
	public final RegistryObject<FSaplingBlock> sapling;
	// public final RegistryObject<Block> sign;
	// public final RegistryObject<Block> wallSign;
	public final RegistryObject<SlabBlock> slab;
	public final RegistryObject<StairsBlock> stairs;
	public final RegistryObject<RotatedPillarBlock> strippedLog;
	public final RegistryObject<RotatedPillarBlock> strippedWood;
	public final RegistryObject<TrapDoorBlock> trapDoor;
	public final RegistryObject<RotatedPillarBlock> wood;

	public TreeRegistryObject(String name, Supplier<FTree> tree, MaterialColor plankColor, MaterialColor barkColor) {

		this.name = name;
		woodType = WoodType.register(WoodType.create(Fantasia.ID + ":" + name));

		leaves = BlockRegistryUtil.register(name + "_leaves",
			() -> new LeavesBlock(AbstractBlock.Properties.of(Material.LEAVES)
				.strength(0.2F)
				.randomTicks()
				.sound(SoundType.GRASS)
				.noOcclusion()
				.isViewBlocking((state, world, pos) -> false)
				.isSuffocating((state, world, pos) -> false)
				.harvestTool(ToolType.HOE)));

		log = BlockRegistryUtil.register(name + "_log",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, barkColor)
				.sound(SoundType.WOOD)
				.strength(2.0f)));

		planks = BlockRegistryUtil.register(name + "_planks",
			() -> new Block(AbstractBlock.Properties.of(Material.WOOD, plankColor)
				.strength(2.0F, 3.0F)
				.sound(SoundType.WOOD)));

		sapling = BlockRegistryUtil.register(name + "_sapling",
			() -> new FSaplingBlock(tree, Block.Properties.copy(Blocks.ACACIA_SAPLING)));

		stairs = BlockRegistryUtil.register(name + "_stairs",
			() -> new StairsBlock(() -> planks.get().defaultBlockState(), AbstractBlock.Properties.copy(planks.get())));

		strippedLog = BlockRegistryUtil.register(name + "_stripped_log",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, (state) -> {
				return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? plankColor : barkColor;
			}).sound(SoundType.WOOD).strength(2.0f)));

		wood = BlockRegistryUtil.register(name + "_wood",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.copy(log.get())));

		strippedWood = BlockRegistryUtil.register(name + "_stripped_wood",
			() -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, barkColor)
				.sound(SoundType.WOOD)
				.strength(2.0f)));

		slab = BlockRegistryUtil.register(name + "_slab",
			() -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
				.strength(2.0F, 3.0F)
				.sound(SoundType.WOOD)));

		door = BlockRegistryUtil.register(name + "_door",
			() -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
				.strength(3.0F)
				.sound(SoundType.WOOD)
				.noOcclusion()));

		trapDoor = BlockRegistryUtil.register(name + "_trapdoor",
			() -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
				.strength(3.0F)
				.sound(SoundType.WOOD)
				.noOcclusion()
				.isValidSpawn(BlockRegistryUtil::neverAllowSpawn)));

		button = BlockRegistryUtil.register(name + "_button",
			() -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.DECORATION)
				.noCollission()
				.strength(0.5F)
				.sound(SoundType.WOOD)));

		pressurePlate = BlockRegistryUtil.register(name + "_pressure_plate",
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
				AbstractBlock.Properties.of(Material.WOOD, plankColor)
					.noCollission()
					.strength(0.5F)
					.sound(SoundType.WOOD)));

		fence = BlockRegistryUtil.register(name + "_fence",
			() -> new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
				.strength(2.0F, 3.0F)
				.sound(SoundType.WOOD)));

		fenceGate = BlockRegistryUtil.register(name + "_fence_gate",
			() -> new FenceGateBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
				.strength(2.0F, 3.0F)
				.sound(SoundType.WOOD)));
	}

	public String getName() {
		return this.name;
	}

	public WoodType getWoodType() {
		return this.woodType;
	}
}
