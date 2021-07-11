package io.github.akiart.fantasia.common.item.registrySet.tree;

import io.github.akiart.fantasia.common.block.registrySet.trees.AbstractTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.BasicTreeRegistryObject;
import net.minecraft.block.WoodType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.SignItem;
import net.minecraftforge.fml.RegistryObject;

public class BasicTreeRegistryItem extends AbstractTreeRegistryItem<BasicTreeRegistryObject> {

    public final RegistryObject<BlockItem> leaves;
    public final RegistryObject<BlockItem> sapling;
    public final RegistryObject<BlockItem> log;
    public final RegistryObject<BlockItem> strippedLog;
    public final RegistryObject<BlockItem> strippedWood;
    public final RegistryObject<BlockItem> wood;

    public BasicTreeRegistryItem(BasicTreeRegistryObject tree, WoodType woodType) {
        super(tree, woodType);
        leaves = tryRegisterFromBlock(tree.leaves);
        sapling = tryRegisterFromBlock(tree.sapling);
        strippedLog = tryRegisterFromBlock(tree.strippedLog);
        strippedWood = tryRegisterFromBlock(tree.strippedWood);
        wood = tryRegisterFromBlock(tree.wood);
        log = tryRegisterFromBlock(tree.log);
    }
}
