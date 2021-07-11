package io.github.akiart.fantasia.common.world.biome;

import java.util.ArrayList;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FBiomes {
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Fantasia.ID);

    public static ArrayList<BiomeRegistryObject> biomeList = new ArrayList<BiomeRegistryObject>();

    // Surface Biomes
	public static BiomeRegistryObject ARCTIC_TUNDRA = create("arctic_tundra");
	public static BiomeRegistryObject ASPEN_FOREST = create("aspen_forest");
	public static BiomeRegistryObject BASALT_BEACH = create("basalt_beach");
	public static BiomeRegistryObject FORLORN_SEA = create("forlorn_sea");
	public static BiomeRegistryObject FROZEN_FOREST = create("frozen_forest");
	public static BiomeRegistryObject ROCKY_MOUNTAINS = create("rocky_mountains");
	public static BiomeRegistryObject ROCKY_MOUNTAINS_EDGE = create("rocky_mountains_edge");
	// public static BiomeRegistryObject SNOWY_ARCTIC_TUNDRA = create("snowy_arctic_tundra");

	// Cave Biomes
	public static BiomeRegistryObject GRIMCAP_GROVE = create("grimcap_grove");
	public static BiomeRegistryObject ICY_CAVERN = create("icy_cavern");

	// Debug Biomes
	public static final BiomeRegistryObject BLUE = create("blue");

	private static BiomeRegistryObject create(String name) {
		return BiomeRegistryObject.create(name, biomeList);
	}
}
