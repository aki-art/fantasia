package io.github.akiart.fantasia.common.block.registrySet.trees;

import io.github.akiart.fantasia.common.block.blockType.FSaplingBlock;
import io.github.akiart.fantasia.common.block.trees.FTree;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class BasicTreeRegistryObject extends AbstractTreeRegistryObject {

    public final RegistryObject<RotatedPillarBlock> log;
    public final RegistryObject<RotatedPillarBlock> strippedLog;
    public final RegistryObject<RotatedPillarBlock> strippedWood;
    public final RegistryObject<RotatedPillarBlock> wood;

    public final RegistryObject<LeavesBlock> leaves;
    public final RegistryObject<FSaplingBlock> sapling;

    public BasicTreeRegistryObject(String name, Supplier<FTree> tree, MaterialColor plankColor, MaterialColor barkColor, WoodType woodType) {
        super(name, plankColor, woodType);
        leaves = createLeaves(name);
        sapling = createSapling(name, tree);
        log = createLog(name, barkColor);
        strippedLog = createStrippedLog(name, plankColor, barkColor) ;
        wood = createWood(name, log::get) ;
        strippedWood = createStrippedWood(name, barkColor) ;
    }
}
