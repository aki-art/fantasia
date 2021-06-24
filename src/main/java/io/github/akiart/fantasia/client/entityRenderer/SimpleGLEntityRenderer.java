package io.github.akiart.fantasia.client.entityRenderer;

import io.github.akiart.fantasia.common.entity.model.GeckoLibModel;
import io.github.akiart.fantasia.lib.GeckoLibExtension.IBasicAnimatable;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

// used for quickly throwing in a model for testing
public class SimpleGLEntityRenderer<T extends LivingEntity & IBasicAnimatable> extends GeoEntityRenderer<T> {
    public SimpleGLEntityRenderer(EntityRendererManager renderManager, float shadowRadius) {
        super(renderManager, new GeckoLibModel<>());
        this.shadowRadius = shadowRadius;
    }
}