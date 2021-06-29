package io.github.akiart.fantasia.client.world;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ISkyRenderHandler;

public class FantasiaSkyRenderHandler implements ISkyRenderHandler {
    @Override
    public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {

        Minecraft minecraft = Minecraft.getInstance();

        minecraft.getProfiler().push("lightTex");
        DynamicTexture lightTexture = (DynamicTexture)minecraft.getTextureManager().getTexture(new ResourceLocation("light_map"));
        if(lightTexture == null) return;

        NativeImage lightPixels = lightTexture.getPixels();
        if(lightPixels == null) return;

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                lightPixels.setPixelRGBA(255, 0, 0);
            }
        }

        lightTexture.upload();
        minecraft.getProfiler().pop();
    }
}
