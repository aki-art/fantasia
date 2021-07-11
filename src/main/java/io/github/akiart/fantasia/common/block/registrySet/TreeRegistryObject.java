package io.github.akiart.fantasia.common.block.registrySet;

import java.util.function.Supplier;

import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.blockType.FChestBlock;
import io.github.akiart.fantasia.common.block.blockType.FSaplingBlock;
import io.github.akiart.fantasia.common.block.blockType.FSignBlock;
import io.github.akiart.fantasia.common.block.blockType.FWallSignBlock;
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
    public final RegistryObject<FChestBlock> chest;
    // public final RegistryObject<Block> craftingTable;
    public final RegistryObject<DoorBlock> door;
    public final RegistryObject<TrapDoorBlock> trapDoor;
    public final RegistryObject<FenceBlock> fence;
    public final RegistryObject<FenceGateBlock> fenceGate;
    // public final RegistryObject<Block> ladder;

    public final RegistryObject<LeavesBlock> leaves;
    public final RegistryObject<FSaplingBlock> sapling;

    public final RegistryObject<Block> planks;
    public final RegistryObject<PressurePlateBlock> pressurePlate;
    public final RegistryObject<FSignBlock> sign;
    public final RegistryObject<FWallSignBlock> wallSign;

    public final RegistryObject<SlabBlock> slab;
    public final RegistryObject<StairsBlock> stairs;

    public final RegistryObject<RotatedPillarBlock> log;
    public final RegistryObject<RotatedPillarBlock> strippedLog;
    public final RegistryObject<RotatedPillarBlock> strippedWood;
    public final RegistryObject<RotatedPillarBlock> wood;

    //public final RegistryObject<ShelfBlock> shelf;

    public TreeRegistryObject(String name, Supplier<FTree> tree, MaterialColor plankColor, MaterialColor barkColor, WoodType woodType) {
        this(name, tree, plankColor, barkColor, woodType, true);
    }

    protected TreeRegistryObject(String name, Supplier<FTree> tree, MaterialColor plankColor, MaterialColor barkColor, WoodType woodType, boolean registerLogs) {
        this.name = name;
        this.woodType = woodType;

        leaves = createLeaves(name);
        planks = createPlanks(name, plankColor);
        sapling = createSapling(name, tree);
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

        log = registerLogs ? createLog(name, barkColor) : null;
        strippedLog = registerLogs ? createStrippedLog(name, plankColor, barkColor) : null;
        wood = registerLogs ? createWood(name) : null;
        strippedWood = registerLogs ? createStrippedWood(name, barkColor) : null;
    }

    private RegistryObject<FChestBlock> createChest(String name, WoodType woodType) {
        return BlockRegistryUtil.register(name + "_chest",
                () -> new FChestBlock(AbstractBlock.Properties.copy(Blocks.CHEST), woodType));
    }

    private RegistryObject<FWallSignBlock> createWallSign(String name, WoodType woodType) {
        return BlockRegistryUtil.register(name + "_wall_sign",
                () -> new FWallSignBlock(AbstractBlock.Properties.copy(Blocks.OAK_SIGN), woodType));
    }

    private RegistryObject<FSignBlock> createSign(String name, WoodType woodType) {
        return BlockRegistryUtil.register(name + "_sign",
                () -> new FSignBlock(AbstractBlock.Properties.copy(Blocks.OAK_SIGN), woodType));
    }

    private RegistryObject<FenceGateBlock> createFenceGate(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_fence_gate",
                () -> new FenceGateBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)));
    }

    private RegistryObject<FenceBlock> createFence(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_fence",
                () -> new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)));
    }

    private RegistryObject<PressurePlateBlock> createPressurePlate(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_pressure_plate",
                () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                        AbstractBlock.Properties.of(Material.WOOD, plankColor)
                                .noCollission()
                                .strength(0.5F)
                                .sound(SoundType.WOOD)));
    }

    private RegistryObject<WoodButtonBlock> createButton(String name) {
        return BlockRegistryUtil.register(name + "_button",
                () -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.DECORATION)
                        .noCollission()
                        .strength(0.5F)
                        .sound(SoundType.WOOD)));
    }

    private RegistryObject<TrapDoorBlock> createTrapDoor(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_trapdoor",
                () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(3.0F)
                        .sound(SoundType.WOOD)
                        .noOcclusion()
                        .isValidSpawn(BlockRegistryUtil::neverAllowSpawn)));
    }

    private RegistryObject<DoorBlock> createDoor(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_door",
                () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(3.0F)
                        .sound(SoundType.WOOD)
                        .noOcclusion()));
    }

    private RegistryObject<SlabBlock> createSlab(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_slab",
                () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)));
    }

    private RegistryObject<RotatedPillarBlock> createStrippedWood(String name, MaterialColor barkColor) {
        return BlockRegistryUtil.register(name + "_stripped_wood",
                () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, barkColor)
                        .sound(SoundType.WOOD)
                        .strength(2.0f)));
    }

    private RegistryObject<RotatedPillarBlock> createWood(String name) {
        return BlockRegistryUtil.register(name + "_wood",
                () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(log.get())));
    }

    private RegistryObject<RotatedPillarBlock> createStrippedLog(String name, MaterialColor plankColor, MaterialColor barkColor) {
        return BlockRegistryUtil.register(name + "_stripped_log",
                () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, (state) -> {
                    return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? plankColor : barkColor;
                }).sound(SoundType.WOOD).strength(2.0f)));
    }

    private RegistryObject<StairsBlock> createStairs(String name) {
        return BlockRegistryUtil.register(name + "_stairs",
                () -> new StairsBlock(() -> planks.get().defaultBlockState(), AbstractBlock.Properties.copy(planks.get())));
    }

    private RegistryObject<FSaplingBlock> createSapling(String name, Supplier<FTree> tree) {
        return BlockRegistryUtil.register(name + "_sapling",
                () -> new FSaplingBlock(tree, Block.Properties.copy(Blocks.ACACIA_SAPLING)));
    }

    private RegistryObject<Block> createPlanks(String name, MaterialColor plankColor) {
        return BlockRegistryUtil.register(name + "_planks",
                () -> new Block(AbstractBlock.Properties.of(Material.WOOD, plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD)));
    }

    private RegistryObject<RotatedPillarBlock> createLog(String name, MaterialColor barkColor) {
        return BlockRegistryUtil.register(name + "_log",
                () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD, barkColor)
                        .sound(SoundType.WOOD)
                        .strength(2.0f)));
    }

    private RegistryObject<LeavesBlock> createLeaves(String name) {
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

    public String getName() {
        return this.name;
    }

    public WoodType getWoodType() {
        return this.woodType;
    }
}
