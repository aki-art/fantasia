package io.github.akiart.fantasia.common.world.biome;

import java.util.ArrayList;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FBiomes {
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Fantasia.ID);
	public static ArrayList<BiomeRegistryObject> biomeList = new ArrayList<BiomeRegistryObject>();
	
	public static BiomeRegistryObject FROZEN_FOREST = create("frozen_forest");
	
	private static BiomeRegistryObject create(String name) {
		return BiomeRegistryObject.create(name, biomeList);
	}
}
