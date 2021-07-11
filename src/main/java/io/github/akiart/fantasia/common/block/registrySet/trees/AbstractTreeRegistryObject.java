package io.github.akiart.fantasia.common.block.registrySet.trees;

import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.blockType.FChestBlock;
import io.github.akiart.fantasia.common.block.blockType.FSaplingBlock;
import io.github.akiart.fantasia.common.block.blockType.FSignBlock;
import io.github.akiart.fantasia.common.block.blockType.FWallSignBlock;
import io.github.akiart.fantasia.common.block.trees.FTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

// Holds common block types of sets belonging to trees and tree like things
public abstract class AbstractTreeRegistryObject {
    private final String name;

    // private final RegistryObject<Block> barrel
    // private final RegistryObject<Block> bookShelf
    private final RegistryObject<WoodButtonBlock> button;
    private final RegistryObject<FChestBlock> chest;
    // private final RegistryObject<Block> craftingTable;
    private final RegistryObject<DoorBlock> door;
    private final RegistryObject<TrapDoorBlock> trapDoor;
    private final RegistryObject<FenceBlock> fence;
    private final RegistryObject<FenceGateBlock> fenceGate;
    // private final RegistryObject<Block> ladder;


    private final RegistryObject<Block> planks;
    private final RegistryObject<PressurePlateBlock> pressurePlate;

    private final RegistryObject<FSignBlock> sign;
    private final RegistryObject<FWallSignBlock> wallSign;

    private final RegistryObject<SlabBlock> slab;
    private final RegistryObject<StairsBlock> stairs;

    //private final RegistryObject<ShelfBlock> shelf;

    protected AbstractTreeRegistryObject(String name, MaterialColor plankColor, WoodType woodType) {
        this.name = name;

        planks = createPlanks(name, plankColor);
        stairs = createStairs(name);
        slab = createSlab(name, plankColor);
        door = createDoor(name, plankColor);
        trapDoor = createTrapDoor(name, plankColor);
        button = createButton(name);
        pressurePlate = createPressurePlate(name, plankColor);
        fence = createFence(name, plankColor);
        fenceGate = createFenceGate(name, plankColor);
        sign = createSign(name, woodType);
        wallSign = createWallSign(name, woodType);
        chest = createChest(name, woodType);
    }

    public String getName() {
        return this.name;
    }

    public RegistryObject<WoodButtonBlock> getButton() {
        return button;
    }

    public RegistryObject<FChestBlock> getChest() {
        return chest;
    }

    public RegistryObject<DoorBlock> getDoor() {
        return door;
    }

    public RegistryObject<TrapDoorBlock> getTrapDoor() {
        return trapDoor;
    }

    public RegistryObject<FenceBlock> getFence() {
        return fence;
    }

    public RegistryObject<FenceGateBlock> getFenceGate() {
        return fenceGate;
    }

    public RegistryObject<Block> getPlanks() {
        return planks;
    }

    public RegistryObject<PressurePlateBlock> getPressurePlate() {
        return pressurePlate;
    }

    public RegistryObject<FSignBlock> getSign() {
        return sign;
    }

    public RegistryObject<FWallSignBlock> getWallSign() {
        return wallSign;
    }

    public RegistryObject<SlabBlock> getSlab() {
        return slab;
    }

    public RegistryObject<StairsBlock> getStairs() {
        return stairs;
    }

    protected RegistryObject<FChestBlock> createChest(String name, WoodType woodType) {
        return BlockRegistryUtil.register(name + "_chest",
                () -> new FChestBlock(AbstractBlock.Properties.copy(Blocks.CHEST), woodType));
    }

    protected RegistryObject<FWallSignBlock> createWallSign(String name, WoodType woodType) {
        return BlockRegistryUtil.register(name + "_wall_sign",
                () -> new FWallSignBlock(AbstractBlock.Properties.copy(Blocks.OAK_SIGN), woodType));
    }

    protected RegistryObject<FSignBlock> createSign(String name, WoodType woodType) {
        return BlockRegistryUtil.register(name + "_sign",
                () -> new FSignBlock(AbstractBlock.Properties.copy(Blocks.OAK_SIGN), woodType));
    }

    protected RegistryObject<FenceGateBlock> createFenceGate(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_fence_gate",
                () -> new FenceGateBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)));
    }

    protected RegistryObject<FenceBlock> createFence(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_fence",
                () -> new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)));
    }

    protected RegistryObject<PressurePlateBlock> createPressurePlate(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_pressure_plate",
                () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                        AbstractBlock.Properties.of(Material.WOOD, plankColor)
                                .noCollission()
                                .strength(0.5F)
                                .sound(SoundType.WOOD)));
    }

    protected RegistryObject<WoodButtonBlock> createButton(String name) {
        return BlockRegistryUtil.register(name + "_button",
                () -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.DECORATION)
                        .noCollission()
                        .strength(0.5F)
                        .sound(SoundType.WOOD)));
    }

    protected RegistryObject<TrapDoorBlock> createTrapDoor(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_trapdoor",
                () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(3.0F)
                        .sound(SoundType.WOOD)
                        .noOcclusion()
                        .isValidSpawn(BlockRegistryUtil::neverAllowSpawn)));
    }

    protected RegistryObject<DoorBlock> createDoor(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_door",
                () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(3.0F)
                        .sound(SoundType.WOOD)
                        .noOcclusion()));
    }

    protected RegistryObject<SlabBlock> createSlab(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_slab",
                () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)));
    }

    protected RegistryObject<RotatedPillarBlock> createStrippedWood(String name, MaterialColor barkColor) {
        return BlockRegistryUtil.register(name + "_stripped_wood",
                () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, barkColor)
                        .sound(SoundType.WOOD)
                        .strength(2.0f)));
    }

    protected RegistryObject<RotatedPillarBlock> createWood(String name, Supplier<Block> log) {
        return BlockRegistryUtil.register(name + "_wood",
                () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(log.get())));
    }

    protected RegistryObject<RotatedPillarBlock> createStrippedLog(String name, MaterialColor plankColor, MaterialColor barkColor) {
        return BlockRegistryUtil.register(name + "_stripped_log",
                () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? plankColor : barkColor).sound(SoundType.WOOD).strength(2.0f)));
    }

    protected RegistryObject<StairsBlock> createStairs(String name) {
        return BlockRegistryUtil.register(name + "_stairs",
                () -> new StairsBlock(() -> planks.get().defaultBlockState(), AbstractBlock.Properties.copy(planks.get())));
    }

    protected RegistryObject<FSaplingBlock> createSapling(String name, Supplier<FTree> tree) {
        return BlockRegistryUtil.register(name + "_sapling",
                () -> new FSaplingBlock(tree, Block.Properties.copy(Blocks.ACACIA_SAPLING)));
    }

    protected RegistryObject<Block> createPlanks(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_planks",
                () -> new Block(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)));
    }

    protected RegistryObject<RotatedPillarBlock> createLog(String name, MaterialColor barkColor) {
        return BlockRegistryUtil.register(name + "_log",
                () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, barkColor)
                        .sound(SoundType.WOOD)
                        .strength(2.0f)));
    }

    protected RegistryObject<LeavesBlock> createLeaves(String name) {
        return BlockRegistryUtil.register(name + "_leaves",
                () -> new LeavesBlock(AbstractBlock.Properties.of(Material.LEAVES)
                        .strength(0.2F)
                        .randomTicks()
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .isViewBlocking((state, world, pos) -> false)
                        .isSuffocating((state, world, pos) -> false)
                        .harvestTool(ToolType.HOE)));
    }
}
