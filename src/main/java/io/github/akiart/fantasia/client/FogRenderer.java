package io.github.akiart.fantasia.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.akiart.fantasia.common.potion.FEffects;
import io.github.akiart.fantasia.common.tags.FTags;
import io.github.akiart.fantasia.util.Color;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FogRenderer {

    private static final float ACID_R = (float)Color.getR(Constants.Colors.ACID_GREEN);
    private static final float ACID_G = (float)Color.getG(Constants.Colors.ACID_GREEN);
    private static final float ACID_B = (float)Color.getB(Constants.Colors.ACID_GREEN);

    @SubscribeEvent
    public static void onFogRender(EntityViewRenderEvent.RenderFogEvent event) {
        if(isInAcid(event.getInfo())) {
            boolean hasAcidRepel = false;
            Entity entity = event.getInfo().getEntity();
            if(entity instanceof LivingEntity) {
                hasAcidRepel = ((LivingEntity)entity).hasEffect(FEffects.ACID_REPEL.get());
            }

            RenderSystem.fogStart(hasAcidRepel ? 1f : 0.25f);
            RenderSystem.fogEnd(hasAcidRepel ? 18f : 8f);
            RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
            RenderSystem.setupNvFogDistance();
        }
    }

    @SubscribeEvent
    public static void getFogColor(EntityViewRenderEvent.FogColors event) {
        if(isInAcid(event.getInfo())) {
            event.setRed(ACID_R);
            event.setGreen(ACID_G);
            event.setBlue(ACID_B);
        }
    }

    private static boolean isInAcid(ActiveRenderInfo info) {
        return info.getFluidInCamera().is(FTags.Fluids.ACID);
    }
}