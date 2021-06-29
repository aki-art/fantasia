package io.github.akiart.fantasia.client.world;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.world.biome.FBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeAmbience {

    static ResourceLocation grimcap_grove = new ResourceLocation(Fantasia.ID, "shaders/post/mygreen.json");
    static ResourceLocation frozen_forest = new ResourceLocation(Fantasia.ID, "shaders/post/test2.json");
    static HashMap<ResourceLocation, ResourceLocation> biomeShaders = new HashMap<>();
    static Biome lastVisitedBiome = null;

    public static void registerAmbiance(ResourceLocation biome, String shaderName) {
        biomeShaders.put(biome, new ResourceLocation(Fantasia.ID, "shaders/post/" + shaderName + ".json"));
    }

    @SubscribeEvent
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        //Fantasia.LOGGER.info("onCameraSetup");
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if(player == null) return;
        Biome biome = player.level.getBiome(player.blockPosition());

        if(lastVisitedBiome != null && lastVisitedBiome.equals(biome)) return;

        if(biomeShaders.containsKey(biome.getRegistryName())) {
            minecraft.gameRenderer.shutdownEffect();
            minecraft.gameRenderer.loadEffect(biomeShaders.get(biome.getRegistryName()));
        }
//        if(biome.getRegistryName().equals(FBiomes.FROZEN_FOREST.getKey().location())) {
//            minecraft.gameRenderer.shutdownEffect();
//            minecraft.gameRenderer.loadEffect(frozen_forest);
//        }
//        else {
//            minecraft.gameRenderer.shutdownEffect();
//            minecraft.gameRenderer.loadEffect(grimcap_grove);
//        }

        lastVisitedBiome = biome;
    }
}
