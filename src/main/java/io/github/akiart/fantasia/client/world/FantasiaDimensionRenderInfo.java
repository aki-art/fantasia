package io.github.akiart.fantasia.client.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FantasiaDimensionRenderInfo extends DimensionRenderInfo {
    public FantasiaDimensionRenderInfo() {
        super(213.0F, true, DimensionRenderInfo.FogType.NORMAL, false, false);
        //setSkyRenderHandler(new FantasiaSkyRenderHandler());
    }

    @Override
    public Vector3d getBrightnessDependentFogColor(Vector3d vec, float val) {
        return vec.multiply((val * 0.94F + 0.06F), val * 0.94F + 0.06F, val * 0.91F + 0.09F);
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }
}