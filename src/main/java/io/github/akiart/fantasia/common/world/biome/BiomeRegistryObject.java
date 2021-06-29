package io.github.akiart.fantasia.common.world.biome;

import java.util.ArrayList;
import java.util.function.Function;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.client.world.BiomeAmbience;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.fml.RegistryObject;

public class BiomeRegistryObject {

	private final RegistryKey<Biome> registryKey;
	public int ID; // used for mapping registry ID-s for world gen.

	public BiomeRegistryObject(String name) {
		this.registryKey = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Fantasia.ID, name));
		this.ID = name.hashCode(); // this ensures a unique ID that is not influenced by Biome ID-s shifting
		FBiomes.BIOMES.register(name, BiomeMaker::theVoidBiome); // actual config is from datapacks
	}

	public static BiomeRegistryObject create(String name, ArrayList<BiomeRegistryObject> list) {
		BiomeRegistryObject result = new BiomeRegistryObject(name);
		list.add(result);
		BiomeAmbience.registerAmbiance(result.getKey().location(), name);
		return result;
	}

	public RegistryKey<Biome> getKey() {
		return this.registryKey;
	}

	public Biome get(Registry<Biome> registry) {
		return registry.get(registryKey);
	}
}
