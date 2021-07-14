package io.github.akiart.fantasia.client.renderer.entityRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.item.FBoatEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class FBoatRenderer extends EntityRenderer<FBoatEntity> {

    ResourceLocation defaultTex = new ResourceLocation(Fantasia.ID, "textures/entity/boat/frozen_elm.png");
    protected final BoatModel model = new BoatModel();

    public FBoatRenderer(EntityRendererManager manager) {
        super(manager);
        this.shadowRadius = 0.8F;
    }

    public void render(FBoatEntity entity, float yaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int packedLight) {
        matrix.pushPose();
        matrix.translate(0.0D, 0.375D, 0.0D);
        matrix.mulPose(Vector3f.YP.rotationDegrees(180.0F - yaw));

        float hurtTicks = (float) entity.getHurtTime() - partialTicks;
        float hurt = entity.getDamage() - partialTicks;
        if (hurt < 0.0F) hurt = 0.0F;

        if (hurtTicks > 0.0F) {
            matrix.mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(hurtTicks) * hurtTicks * hurt / 10.0F * (float) entity.getHurtDir()));
        }

        float bubbleAngle = entity.getBubbleAngle(partialTicks);
        if (!MathHelper.equal(bubbleAngle, 0.0F)) {
            matrix.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entity.getBubbleAngle(partialTicks), true));
        }

        matrix.scale(-1.0F, -1.0F, 1.0F);
        matrix.mulPose(Vector3f.YP.rotationDegrees(90.0F));

        model.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);

        IVertexBuilder builder = buffer.getBuffer(model.renderType(getTextureLocation(entity)));
        model.renderToBuffer(matrix, builder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        if (!entity.isUnderWater()) {
            IVertexBuilder waterVertexBuilder = buffer.getBuffer(RenderType.waterMask());
            model.waterPatch().render(matrix, waterVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);
        }

        matrix.popPose();
        super.render(entity, yaw, partialTicks, matrix, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(FBoatEntity entity) {
        FBoatEntity.Type type = entity.getFBoatType();
        return type != null ? type.getTexture() : defaultTex;
    }
}
