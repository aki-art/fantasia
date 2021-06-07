package io.github.akiart.fantasia.common.block.registrySet;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

public interface IRegistryBlockSet {
	public List<RegistryObject<? extends Block>> getItems();
	public String getName();
}
