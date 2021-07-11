package io.github.akiart.fantasia.common.block.registrySet.trees;

import net.minecraft.block.WoodType;
import net.minecraft.block.material.MaterialColor;

public class HugeTreeRegistryObject extends AbstractTreeRegistryObject {

    // 32x32 large texture blocks for logs
    // no sapling

    protected HugeTreeRegistryObject(String name, MaterialColor plankColor, WoodType woodType) {
        super(name, plankColor, woodType);
    }
}
