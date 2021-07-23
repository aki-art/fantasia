package io.github.akiart.fantasia.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.akiart.fantasia.FTags;
import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.potion.FEffects;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FogRenderer {

    private static final int ACID_COLOR = 0xCBFF5CFF; // RGBA
    private static final float ACID_R = (float)(ACID_COLOR >> 16 & 255) / 255.0f;
    private static final float ACID_G = (float)(ACID_COLOR >> 8 & 255) / 255.0f;
    private static final float ACID_B = (float)(ACID_COLOR & 255) / 255.0f;

    @SubscribeEvent
    public static void onFogRender(EntityViewRenderEvent.RenderFogEvent event) {
        if(isInAcid(event.getInfo())) {
            RenderSystem.fogStart(0.25f);
            RenderSystem.fogEnd(6f);
            RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
            RenderSystem.setupNvFogDistance();
        }
    }

    @SubscribeEvent
    public static void getFogColor(EntityViewRenderEvent.FogColors event) {
        if(isInAcid(event.getInfo())) {
            event.setRed(ACID_R);
            event.setBlue(ACID_G);
            event.setGreen(ACID_B);
        }
    }

    @SubscribeEvent
    public static void getFogDensity(EntityViewRenderEvent.FogDensity event) {
        if(isInAcid(event.getInfo())) {
            event.setDensity(0.25f);
        }
        else event.setDensity(1f); // test
    }

    private static boolean isInAcid(ActiveRenderInfo info) {
        return info.getFluidInCamera().is(FTags.Fluids.ACID);
    }
}
