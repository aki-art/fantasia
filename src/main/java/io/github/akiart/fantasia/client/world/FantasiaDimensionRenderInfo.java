package io.github.akiart.fantasia.client.world;

import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FantasiaDimensionRenderInfo extends DimensionRenderInfo {
    public static final float CLOUD_HEIGHT = 213f;

    public FantasiaDimensionRenderInfo() {
        super(CLOUD_HEIGHT, true, DimensionRenderInfo.FogType.NORMAL, false, false);
    }

    @Override
    public Vector3d getBrightnessDependentFogColor(Vector3d vec, float val) {
        return vec.multiply((val * 0.94F + 0.06F), val * 0.94F + 0.06F, val * 0.91F + 0.09F);
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return false;
    }
}