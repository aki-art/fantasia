package io.github.akiart.fantasia.client.entityRenderer;

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

    public PtarmiganEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager, new GeckoLibModel<>());
        this.shadowRadius = 0.3f;
    }

    void scale(IBone bone, float scale) {
        bone.setScaleX(scale);
        bone.setScaleX(scale);
        bone.setScaleZ(scale);
    }


    @Override
    public void render(GeoModel model, T animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn,
                        @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn,
                        int packedOverlayIn, float r, float g, float b, float a) {

        if(animatable.isBaby()) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            IBone head = model.getBone("head").get();
            head.setScaleX(2f);
            head.setScaleY(2f);
            head.setScaleZ(2f);
        }

        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, r, g, b, a);
    }
//
//    @Override
//    public void renderEarly(T animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer,
//                            IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue,
//                            float partialTicks) {
//        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
//                red, green, blue, partialTicks);
//
//        if (animatable.isBaby()) {
//            stackIn.scale(0.5F, 0.5F, 0.5F);
//        }
//    }

}
