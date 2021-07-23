package io.github.akiart.fantasia.client.renderer.entityRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.akiart.fantasia.common.entity.model.CavingRopeAnchorModel;
import io.github.akiart.fantasia.common.entity.projectile.CavingRopeAnchorEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class CavingRopeAnchorEntityRenderer extends EntityRenderer<CavingRopeAnchorEntity> {

    private final CavingRopeAnchorModel model = new CavingRopeAnchorModel();

    public CavingRopeAnchorEntityRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public void render(CavingRopeAnchorEntity entity, float yaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {

        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.yRotO, entity.yRot) - 90.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot) + 90.0F));

        IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBufferDirect(buffer, model.renderType(getTextureLocation(entity)), false, entity.isFoil());
        model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();

        super.render(entity, yaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(CavingRopeAnchorEntity entity) {
        return entity.getResourceLocation();
    }
}
