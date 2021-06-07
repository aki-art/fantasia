package io.github.akiart.fantasia;

import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.world.FChunkGenerator;
import io.github.akiart.fantasia.common.world.gen.blockplacer.FBlockPlacerTypes;
import io.github.akiart.fantasia.common.world.gen.feature.FFeatures;
import io.github.akiart.fantasia.common.world.gen.util.Extents;
import net.minecraft.util.math.vector.Vector3i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.particles.FParticleTypes;
import io.github.akiart.fantasia.common.sound.FSounds;
import io.github.akiart.fantasia.common.tileentity.FTileEntityTypes;
import io.github.akiart.fantasia.common.world.FBiomeProvider;
import io.github.akiart.fantasia.common.world.biome.FBiomes;
import io.github.akiart.fantasia.common.world.gen.feature.FConfiguredFeatures;
import io.github.akiart.fantasia.common.world.gen.surfaceBuilders.FSurfaceBuilders;
import io.github.akiart.fantasia.common.world.gen.treedecorators.FTreeDecorators;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(Fantasia.ID)
public class Fantasia {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String ID = "fantasia";

	public Fantasia() {

		GeckoLib.initialize();

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		FParticleTypes.PARTICLES.register(bus);
		FSounds.SOUNDS.register(bus);
		FBlocks.BLOCKS.register(bus);
		FItems.ITEMS.register(bus);
		FTileEntityTypes.TILE_ENTITY_TYPES.register(bus);
		FTreeDecorators.TREE_DECORATOR_TYPES.register(bus);
		FSurfaceBuilders.SURFACE_BUILDERS.register(bus);
		FFeatures.FEATURES.register(bus);
		FBiomes.BIOMES.register(bus);
		FEntities.ENTITIES.register(bus);
		FBlockPlacerTypes.BLOCK_PLACER_TYPES.register(bus);

		bus.addListener(this::setup);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {

			FConfiguredFeatures.registerConfiguredFeatures();
			Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(ID, "biome_source"),
				FBiomeProvider.CODEC);
			Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(ID,
			 "chunk_generator"), FChunkGenerator.CODEC);

		});
	}
//	
//	public static void registerStripMaps() {
//		Map<Block, Block> stripMap = new HashMap<>();
//		stripMap.putAll(AxeItem.BLOCK_STRIPPING_MAP);
//		stripMap.put(FBlocks.FROZEN_TREE.LOG.get(), FBlocks.FROZEN_TREE.STRIPPED_LOG.get());
//		stripMap.put(FBlocks.FROZEN_TREE.WOOD.get(), FBlocks.FROZEN_TREE.STRIPPED_WOOD.get());
//		AxeItem.BLOCK_STRIPPING_MAP = stripMap;
//	}
}
