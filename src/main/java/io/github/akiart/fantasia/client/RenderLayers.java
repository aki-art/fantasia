package io.github.akiart.fantasia.client;

import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.fluid.FFluids;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderLayers {

    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event) {
        ArrayList<Block> cutouts = new ArrayList<>();
        ArrayList<Block> translucents = new ArrayList<>();

        translucents.add(FBlocks.ACID.get());

        cutouts.add(FBlocks.TEST_ACID_LOGGABLE.get());

        RenderTypeLookup.setRenderLayer(FFluids.ACID_SOURCE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(FFluids.ACID_FLOWING.get(), RenderType.translucent());

        for (Block block : cutouts)
            RenderTypeLookup.setRenderLayer(block, RenderType.cutout());

        for (Block block : translucents)
            RenderTypeLookup.setRenderLayer(block, RenderType.translucent());
    }
}
