package io.github.akiart.fantasia.client.world;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FantasiaDimensionRenderInfo extends DimensionRenderInfo {

    boolean hasLoadedShader = false;
    Minecraft minecraft;
    ResourceLocation testShader = new ResourceLocation(Fantasia.ID, "shaders/post/mygreen.json");
    ResourceLocation testShader2 = new ResourceLocation("shaders/post/green.json");
    public FantasiaDimensionRenderInfo() {
        super(213.0F, true, DimensionRenderInfo.FogType.NORMAL, false, false);
        // setSkyRenderHandler(new FantasiaSkyRenderHandler());
        minecraft = Minecraft.getInstance();
    }

    @Override
    public Vector3d getBrightnessDependentFogColor(Vector3d vec, float val) {
       // tryColor();
        return vec.multiply((val * 0.94F + 0.06F), val * 0.94F + 0.06F, val * 0.91F + 0.09F);
    }

    @Override
    public boolean forceBrightLightmap() {
       // tryColor();
//        if(!hasLoadedShader) {
//           // Fantasia.LOGGER.info("reloadng shader");
//            Minecraft.getInstance().gameRenderer.loadEffect(testShader);
//            hasLoadedShader = true;
//           // initTransparency();
//        }
//
        return false;
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
       // tryColor();
        return false;
    }
}