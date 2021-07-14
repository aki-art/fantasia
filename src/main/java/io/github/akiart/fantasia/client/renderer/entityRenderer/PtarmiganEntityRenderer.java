package io.github.akiart.fantasia.client.renderer.entityRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.akiart.fantasia.common.entity.model.GeckoLibModel;
import io.github.akiart.fantasia.lib.GeckoLibExtension.IBasicAnimatable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class PtarmiganEntityRenderer <T extends LivingEntity & IBasicAnimatable> extends GeoEntityRenderer<T> {

    private final static float BABY_BODY_SCALE = .5f;
    private final static float BABY_HEAD_SCALE = 2f;

    public PtarmiganEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager, new GeckoLibModel<>());
        this.shadowRadius = 0.3f;
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn,
                        @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn,
                        int packedOverlayIn, float r, float g, float b, float a) {

        if(animatable.isBaby()) {
            matrixStackIn.scale(BABY_BODY_SCALE, BABY_BODY_SCALE, BABY_BODY_SCALE);
            IBone head = model.getBone("head").get();
            head.setScaleX(BABY_HEAD_SCALE);
            head.setScaleY(BABY_HEAD_SCALE);
            head.setScaleZ(BABY_HEAD_SCALE);
        }

        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, r, g, b, a);
    }
}
