package io.github.akiart.fantasia.common.world.biome;

import java.util.ArrayList;
import java.util.function.Function;

import io.github.akiart.fantasia.Fantasia;
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
	public Function<Vector3i, BlockState> baseBlockSupplier;

	public BiomeRegistryObject(String name) {
		this.registryKey = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Fantasia.ID, name));
		this.ID = name.hashCode(); // this ensures a unique ID that is not influenced by Biome ID-s shifting
		FBiomes.BIOMES.register(name, BiomeMaker::theVoidBiome); // actual config is from datapacks
	}

	public static BiomeRegistryObject create(String name, ArrayList<BiomeRegistryObject> list) {
		BiomeRegistryObject result = new BiomeRegistryObject(name);
		list.add(result);
		return result;
	}

	public BiomeRegistryObject withBaseBlock(Block block) {
		return withBaseBlock((pos) -> {
			return block.defaultBlockState();
		});
	}

	public BiomeRegistryObject withBaseBlock(Function<Vector3i, BlockState> provider) {
		this.baseBlockSupplier = provider;
		return this;
	}

	public BlockState getBlockForPos(int x, int y, int z) {
		return getBlockForPos(new Vector3i(x, y, z));
	}

	public BlockState getBlockForPos(Vector3i pos) {
		return baseBlockSupplier.apply(pos);
	}

	public RegistryKey<Biome> getKey() {
		return this.registryKey;
	}

	public Biome get(Registry<Biome> registry) {
		return registry.get(registryKey);
	}
}
