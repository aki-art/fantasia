package io.github.akiart.fantasia.common.block;

import java.util.HashSet;
import java.util.function.Supplier;

import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemBlock;
import io.github.akiart.fantasia.common.block.blockType.crystalLens.AbstractFunctionalLensBlock;
import io.github.akiart.fantasia.common.block.registrySet.*;
import io.github.akiart.fantasia.common.block.registrySet.trees.AbstractTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.BasicTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.ThinTreeRegistryObject;
import io.github.akiart.fantasia.common.block.trees.FTree;
import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.util.DirectionRestriction;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;

public class BlockRegistryUtil {

    private static HashSet<StoneRegistryObject> stones = new HashSet<StoneRegistryObject>();
    private static HashSet<RegistryObject<? extends SpeleothemBlock>> speleothems = new HashSet<RegistryObject<? extends SpeleothemBlock>>();
    private static HashSet<CrystalRegistryObject> crystals = new HashSet<CrystalRegistryObject>();
    private static HashSet<AbstractTreeRegistryObject> trees = new HashSet<>();

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return FBlocks.BLOCKS.register(name, supplier);
    }

    public static RegistryObject<AirBlock> registerAir(String name) {
        return register(name, () -> new AirBlock(AbstractBlock.Properties.of(Material.AIR).noCollission().noDrops().air()));
    }

    public static StoneRegistryObject registerStones(String name, float hardness, float resistance, MaterialColor color,
                                                     boolean redstone) {
        StoneRegistryObject obj = new StoneRegistryObject(name, hardness, resistance, color, redstone);
        stones.add(obj);
        return obj;
    }

    public static BasicTreeRegistryObject  createTree(String name, Supplier<FTree> tree, MaterialColor plankColor, MaterialColor barkColor,
                                                     MaterialColor leavesColor, WoodType woodType) {
        BasicTreeRegistryObject obj = new BasicTreeRegistryObject(name, tree, plankColor, barkColor, woodType);
        trees.add(obj);
        return obj;
    }

    public static ThinTreeRegistryObject createThinTree(String name, Supplier<FTree> tree, MaterialColor plankColor, MaterialColor barkColor,
                                                        MaterialColor leavesColor, WoodType woodType) {
        ThinTreeRegistryObject obj = new ThinTreeRegistryObject(name, tree, plankColor, barkColor, woodType);
        trees.add(obj);
        return obj;
    }

    public static RegistryObject<SpeleothemBlock> registerSpeleothem(RegistryObject<Block> baseBlock) {
        return BlockRegistryUtil.createSpeleothem(baseBlock.getId().getPath() + "_speleothem",
                () -> new SpeleothemBlock(DirectionRestriction.VERTICAL_ONLY, 0.33f,
                        AbstractBlock.Properties.copy(baseBlock.get())));
    }

    public static <T extends SpeleothemBlock> RegistryObject<T> createSpeleothem(String name, Supplier<T> supplier) {
        RegistryObject<T> result = register(name, supplier);
        speleothems.add(result);
        return result;
    }

    public static StoneSet registerStoneSet(String name, float hardness, float resistance, MaterialColor color,
                                            int variant) {
        return new StoneSet(name, hardness, resistance, color, variant);
    }

    public static CrystalRegistryObject createCrystal(String name, DirectionRestriction restriction,
                                                      float altMiddleChance, boolean glowy, float hardnessAndResistance, MaterialColor color,
                                                      Supplier<? extends AbstractFunctionalLensBlock> lens) {
        CrystalRegistryObject obj = new CrystalRegistryObject(name, restriction, altMiddleChance, glowy,
                hardnessAndResistance, color, lens);
        crystals.add(obj);
        return obj;
    }

    public static HashSet<StoneRegistryObject> getStones() {
        return stones;
    }

    public static HashSet<CrystalRegistryObject> getCrystals() {
        return crystals;
    }

    public static HashSet<? extends AbstractTreeRegistryObject> getTrees() {
        return trees;
    }

    public static HashSet<RegistryObject<? extends SpeleothemBlock>> getSpeleothems() {
        return speleothems;
    }

    public static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    public static Boolean notSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    public static RegistryObject<FlowingFluidBlock> registerFluid(String name) {
        return FBlocks.BLOCKS.register(name,
                () -> new FlowingFluidBlock(FFluids.ACID_SOURCE, Block.Properties
                        .copy(Blocks.WATER)
                        .noCollission()
                        .strength(100f)
                        .lightLevel(state -> 4)
                        .noDrops()));
    }
}
