package io.github.akiart.fantasia.common.block.registrySet.trees;

import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.blockType.FSaplingBlock;
import io.github.akiart.fantasia.common.block.blockType.ThinLogBlock;
import io.github.akiart.fantasia.common.block.trees.FTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

// Thin trees have slim log blocks and are a bit of an inbetween of trees and bamboos
public class ThinTreeRegistryObject extends AbstractTreeRegistryObject {

    public final RegistryObject<ThinLogBlock> log;
    public final RegistryObject<ThinLogBlock> wood;

    public final RegistryObject<ThinLogBlock> strippedLog;
    public final RegistryObject<ThinLogBlock> strippedWood;

    public final RegistryObject<LeavesBlock> leaves;
    public final RegistryObject<FSaplingBlock> sapling;

    public ThinTreeRegistryObject(String name, Supplier<FTree> tree, MaterialColor plankColor, MaterialColor barkColor, WoodType woodType) {
        super(name, plankColor, woodType);
        leaves = createLeaves(name);
        sapling = createSapling(name, tree);
        log = createThinLog(name, barkColor);
        strippedLog = createThinStrippedLog(name, barkColor);
        wood = createThinWood(name, log::get);
        strippedWood = createThinStrippedWood(name, barkColor);
    }

    protected RegistryObject<ThinLogBlock> createThinLog(String name, MaterialColor barkColor) {
        return BlockRegistryUtil.register(name + "_log",
                () -> new ThinLogBlock(AbstractBlock.Properties.of(Material.WOOD, barkColor)
                        .sound(SoundType.WOOD)
                        .strength(2.0f)));
    }

    protected RegistryObject<ThinLogBlock> createThinStrippedLog(String name, MaterialColor barkColor) {
        return BlockRegistryUtil.register(name + "_stripped_log",
                () -> new ThinLogBlock(AbstractBlock.Properties.of(
                        Material.WOOD, barkColor)
                        .sound(SoundType.WOOD)
                        .strength(2.0f)));
    }

    protected RegistryObject<ThinLogBlock> createThinWood(String name, Supplier<Block> log) {
        return BlockRegistryUtil.register(name + "_wood",
                () -> new ThinLogBlock(AbstractBlock.Properties.copy(log.get())));
    }

    protected RegistryObject<ThinLogBlock> createThinStrippedWood(String name, MaterialColor barkColor) {
        return BlockRegistryUtil.register(name + "_stripped_wood",
                () -> new ThinLogBlock(AbstractBlock.Properties.of(Material.WOOD, barkColor)
                        .sound(SoundType.WOOD)
                        .strength(2.0f)));
    }

    @Override
    public RegistryObject<ThinLogBlock> getLog() {
        return log;
    }

    @Override
    public RegistryObject<ThinLogBlock> getWood() {
        return wood;
    }

    @Override
    public RegistryObject<ThinLogBlock> getStrippedLog() {
        return strippedLog;
    }

    @Override
    public RegistryObject<ThinLogBlock> getStrippedWood() {
        return strippedWood;
    }

    public RegistryObject<LeavesBlock> getLeaves() {
        return leaves;
    }

    public RegistryObject<FSaplingBlock> getSapling() {
        return sapling;
    }
}
