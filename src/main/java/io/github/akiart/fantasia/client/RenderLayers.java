package io.github.akiart.fantasia.client;

import java.util.ArrayList;

import io.github.akiart.fantasia.client.renderer.entityRenderer.*;
import io.github.akiart.fantasia.client.renderer.tileentity.CrystalLensTileEntityRenderer;
import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.block.registrySet.trees.BasicTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.ThinTreeRegistryObject;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.tileentity.FTileEntityTypes;
import io.github.akiart.fantasia.lib.GeckoLibExtension.IBasicAnimatable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public final class RenderLayers {

    // Because different models are rendered in hand and in inventory for these items, one of them must be loaded manually.
    @SubscribeEvent
    public static void onModelLoadingStart(ModelRegistryEvent event) {
        addJavelinModels(
                "wooden_javelin",
                "stone_javelin",
                "gold_javelin",
                "iron_javelin",
                "diamond_javelin",
                "netherite_javelin",
                "wolframite_javelin",
                "ghastly_javelin",
                "frostwork_bolt");
    }

    private static void addJavelinModels(String... names) {
        for (String name : names) {
            ModelLoader.addSpecialModel(new ModelResourceLocation("fantasia:" + name + "_inventory#inventory"));
        }
    }

    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event) {
        ArrayList<Block> cutouts = new ArrayList<>();
        ArrayList<Block> translucents = new ArrayList<>();

        BlockRegistryUtil.getSpeleothems().forEach(b -> {
            cutouts.add(b.get());
        });

        BlockRegistryUtil.getCrystals().forEach(b -> {
            translucents.add(b.lens.get());
        });

        translucents.add(FBlocks.ACID.get());
        RenderTypeLookup.setRenderLayer(FFluids.ACID_SOURCE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(FFluids.ACID_FLOWING.get(), RenderType.translucent());

        BlockRegistryUtil.getTrees().forEach(b -> {
            if(b instanceof BasicTreeRegistryObject)
                cutouts.add(((BasicTreeRegistryObject)b).sapling.get());
            else if(b instanceof ThinTreeRegistryObject)
                cutouts.add(((ThinTreeRegistryObject)b).sapling.get());
        });

        cutouts.add(FBlocks.FROZEN_ELM.sapling.get());

        cutouts.add(FBlocks.SNOWBERRY_BUSH_BOTTOM.get());
        cutouts.add(FBlocks.SNOWBERRY_BUSH_TOP.get());

        translucents.add(FBlocks.TEST_CRYSTAL_LENS.get());

        for (Block block : cutouts)
            RenderTypeLookup.setRenderLayer(block, RenderType.cutout());

        for (Block block : translucents)
            RenderTypeLookup.setRenderLayer(block, RenderType.translucent());

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(FTileEntityTypes.CRYSTAL_LENS.get(), CrystalLensTileEntityRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(FEntities.PTARMIGAN.get(), PtarmiganEntityRenderer::new);
        registerSimpleGLAnim(FEntities.SABER_CAT.get(), 0.8f);
        registerSimpleGLAnim(FEntities.VALRAVN.get(), 0.8f);
        registerSimpleGLAnim(FEntities.VALRAVN2.get(), 0.8f);

        RenderingRegistry.registerEntityRenderingHandler(FEntities.PTARMIGAN_EGG.get(), renderer -> new SpriteRenderer<>(renderer, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(FEntities.JAVELIN.get(), JavelinEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(FEntities.ICICLE.get(), IcicleEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(FEntities.BOAT.get(), FBoatRenderer::new);
    }

    private static <T extends LivingEntity & IBasicAnimatable> void registerSimpleGLAnim(EntityType<T> entityClass, float shadowSize) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, (renderer) -> new SimpleGLEntityRenderer<>(renderer, shadowSize));
    }
}