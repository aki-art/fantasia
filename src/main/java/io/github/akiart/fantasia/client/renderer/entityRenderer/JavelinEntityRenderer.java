package io.github.akiart.fantasia.client.renderer.entityRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.akiart.fantasia.common.entity.model.JavelinModel;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class JavelinEntityRenderer extends EntityRenderer<JavelinEntity> {

    private final JavelinModel model = new JavelinModel();

    public JavelinEntityRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    public void render(JavelinEntity javelinEntity, float yaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light) {

        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, javelinEntity.yRotO, javelinEntity.yRot) - 90.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, javelinEntity.xRotO, javelinEntity.xRot) + 90.0F));

        IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBufferDirect(buffer, model.renderType(getTextureLocation(javelinEntity)), false, javelinEntity.isFoil());
        model.renderToBuffer(matrixStack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();

        super.render(javelinEntity, yaw, partialTicks, matrixStack, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(JavelinEntity javelin) {
        return javelin.getResourceLocation();
    }
}
