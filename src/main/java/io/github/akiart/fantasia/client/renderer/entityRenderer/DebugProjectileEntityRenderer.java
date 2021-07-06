package io.github.akiart.fantasia.client.renderer.entityRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.model.JavelinModel;
import io.github.akiart.fantasia.common.entity.projectile.FrostworkPickaxeProjectileEntity;
import io.github.akiart.fantasia.common.entity.projectile.IcicleEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class DebugProjectileEntityRenderer extends EntityRenderer<FrostworkPickaxeProjectileEntity> {

    private final JavelinModel model = new JavelinModel();
    private final ResourceLocation texture = new ResourceLocation(Fantasia.ID, "textures/entity/javelin/icicle.png");


    public DebugProjectileEntityRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    public void render(FrostworkPickaxeProjectileEntity icicle, float f1, float f2, MatrixStack matrixStack, IRenderTypeBuffer buffer, int i) {

        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(f2, icicle.yRotO, icicle.yRot) - 90.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(f2, icicle.xRotO, icicle.xRot) + 90.0F));

        IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBufferDirect(buffer, model.renderType(getTextureLocation(icicle)), false, false);
        model.renderToBuffer(matrixStack, ivertexbuilder, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();

        super.render(icicle, f1, f2, matrixStack, buffer, i);
    }

    @Override
    public ResourceLocation getTextureLocation(FrostworkPickaxeProjectileEntity icicle) {
        return texture;
    }
}
