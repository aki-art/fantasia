package io.github.akiart.fantasia.client;

import java.util.ArrayList;

import io.github.akiart.fantasia.client.entityRenderer.PtarmiganEntityRenderer;
import io.github.akiart.fantasia.client.tileEntityRenderer.CrystalLensTileEntityRenderer;
import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.tileentity.FTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.example.registry.EntityRegistry;
import software.bernie.example.registry.TileRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public final class RenderLayers {

    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event) {
        ArrayList<Block> cutouts = new ArrayList<Block>();
        ArrayList<Block> translucents = new ArrayList<Block>();

        BlockRegistryUtil.getSpeleothems().forEach(b -> {
            cutouts.add(b.get());
        });

        BlockRegistryUtil.getCrystals().forEach(b -> {
            translucents.add(b.lens.get());
        });

        BlockRegistryUtil.getTrees().forEach(b -> {
            cutouts.add(b.sapling.get());
        });

        cutouts.add(FBlocks.SNOWBERRY_BUSH.get());

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
        RenderingRegistry.registerEntityRenderingHandler(FEntities.PTARMIGAN.get(),
                PtarmiganEntityRenderer::new);
    }
}
