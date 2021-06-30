package io.github.akiart.fantasia;

import io.github.akiart.fantasia.client.renderer.tileentity.FChestTileEntityRenderer;
import io.github.akiart.fantasia.client.world.FantasiaDimensionRenderInfo;
import io.github.akiart.fantasia.common.block.FWoodType;
import io.github.akiart.fantasia.common.block.trees.StripMap;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.item.FItemModelProperties;
import io.github.akiart.fantasia.common.world.FChunkGenerator;
import io.github.akiart.fantasia.common.world.gen.blockplacer.FBlockPlacerTypes;
import io.github.akiart.fantasia.common.world.gen.feature.FFeatures;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.commons.lang3.tuple.Pair;
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
    public static final ResourceLocation DIMENSION_ID = new ResourceLocation(ID, "fantasia");
    public static final RegistryKey<World> FANTASIA_WORLD_KEY = RegistryKey.create(Registry.DIMENSION_REGISTRY, DIMENSION_ID);
    public static final ResourceLocation FANTASIA_EFFECTS = new ResourceLocation(ID, "fantasia_effects");

    public Fantasia() {
//        {
//            final Pair<Config.Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config.Common::new);
//            ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, specPair.getRight());
//            Config.common = specPair.getLeft();
//        }
//        {
//            final Pair<Config.Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config.Client::new);
//            ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, specPair.getRight());
//            Config.client = specPair.getLeft();
//        }

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
        bus.addListener(this::clientSetup);

        if(Config.common != null) {
            //Fantasia.LOGGER.info("config exists, test is {}", Config.common.testSettings.testBoolean.get());
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        DimensionRenderInfo.EFFECTS.put(FANTASIA_EFFECTS, new FantasiaDimensionRenderInfo());
        event.enqueueWork(() -> {
            FConfiguredFeatures.registerConfiguredFeatures();
            Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(ID, "biome_source"), FBiomeProvider.CODEC);
            Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(ID, "chunk_generator"), FChunkGenerator.CODEC);

            StripMap.registerStripMaps();
            FWoodType.values().forEach(WoodType::register);
        });
    }


    public void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            FWoodType.values().forEach(Atlases::addWoodType);
        });

        ClientRegistry.bindTileEntityRenderer(FTileEntityTypes.SIGN.get(), SignTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(FTileEntityTypes.CHEST.get(), FChestTileEntityRenderer::new);

        FItemModelProperties.setItemModelProperties();
    }
}
