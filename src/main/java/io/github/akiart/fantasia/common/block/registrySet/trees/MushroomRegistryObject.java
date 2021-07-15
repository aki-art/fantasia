package io.github.akiart.fantasia.common.block.registrySet.trees;

import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;

public class MushroomRegistryObject extends AbstractTreeRegistryObject {

    protected MushroomRegistryObject(String name, MaterialColor plankColor, WoodType woodType) {
        super(name, plankColor, woodType);
    }

    @Override
    public RegistryObject<? extends Block> getLog() {
        return null;
    }

    @Override
    public RegistryObject<? extends Block> getStrippedLog() {
        return null;
    }

    @Override
    public RegistryObject<? extends Block> getWood() {
        return null;
    }

    @Override
    public RegistryObject<? extends Block> getStrippedWood() {
        return null;
    }
}
