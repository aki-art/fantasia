package io.github.akiart.fantasia.common.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CavingRopeAnchorModel  extends EntityModel<Entity> {
    private final ModelRenderer bone;

    public CavingRopeAnchorModel() {
        texWidth = 32;
        texHeight = 32;

        bone = new ModelRenderer(this);
        bone.setPos(-0.5F, 12.4F, 0.0F);
        bone.texOffs(12, 11).addBox(3.5F, -3.4F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        bone.texOffs(4, 9).addBox(-3.5F, 1.6F, -0.5F, 7.0F, 1.0F, 1.0F, 0.0F, false);
        bone.texOffs(8, 11).addBox(-4.5F, -3.4F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        bone.texOffs(0, 7).addBox(-4.5F, -4.4F, -0.5F, 9.0F, 1.0F, 1.0F, 0.0F, false);
        bone.texOffs(13, 0).addBox(-1.5F, -4.4F, -0.5F, 3.0F, 1.0F, 1.0F, 0.5F, false);
        bone.texOffs(0, 0).addBox(-2.5F, 0.6F, -1.5F, 5.0F, 4.0F, 3.0F, 0.0F, false);
        bone.texOffs(4, 11).addBox(0.5F, 4.6F, -0.5F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        bone.texOffs(0, 9).addBox(-1.5F, 3.6F, -0.5F, 1.0F, 14.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay){
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
