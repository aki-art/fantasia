// Made with Blockbench 3.8.4

package io.github.akiart.fantasia.common.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class JavelinModel extends EntityModel<Entity>{
    private final ModelRenderer bb_main;
    private final ModelRenderer b_r1;
    private final ModelRenderer a_r1;

    public JavelinModel() {
        texWidth = 64;
        texHeight = 64;

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);

        b_r1 = new ModelRenderer(this);
        b_r1.setPos(0.0F, -3.0F, 0.0F);
        bb_main.addChild(b_r1);
        setRotationAngle(b_r1, 1.5708F, 0.7854F, 0.0F);
        b_r1.texOffs(0, 0).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 32.0F, 0.0F, true);

        a_r1 = new ModelRenderer(this);
        a_r1.setPos(0.0F, -3.0F, 0.0F);
        bb_main.addChild(a_r1);
        setRotationAngle(a_r1, 1.5708F, -0.7854F, 0.0F);
        a_r1.texOffs(0, 0).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 32.0F, 0.0F, true);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}