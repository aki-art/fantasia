package io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.model.JavelinModel;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import io.github.akiart.fantasia.common.item.itemType.SaberToothJavelinItem;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class JavelinISTER extends ItemStackTileEntityRenderer {

    JavelinModel model = new JavelinModel();
    ModelResourceLocation itemModelLocation;
    ResourceLocation texture;

    public JavelinISTER(ResourceLocation texture, ModelResourceLocation itemModelLocation) {
        this.texture = texture;
        this.itemModelLocation = itemModelLocation;
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (shouldRender2D(transformType)) {
            renderGUI(itemStackIn, transformType, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        } else {
            matrixStackIn.pushPose();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);

            IVertexBuilder vertexBuilder = ItemRenderer.getFoilBufferDirect(bufferIn, model.renderType(texture), false, itemStackIn.hasFoil());
            model.renderToBuffer(matrixStackIn, vertexBuilder, combinedLightIn, combinedOverlayIn);

            matrixStackIn.popPose();
        }
    }

    protected void renderGUI(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.popPose();
        matrixStackIn.pushPose();

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        IBakedModel itemModel = renderer.getItemModelShaper().getModelManager().getModel(itemModelLocation);
        itemModel = itemModel.getOverrides().resolve(itemModel, itemStackIn, null, null);

        matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
        itemModel.handlePerspective(transformType, matrixStackIn);

        if (itemModel.isLayered()) {
            ForgeHooksClient.drawItemLayered(renderer, itemModel, itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedLightIn, true);
        }
        else {
            IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(bufferIn, Atlases.translucentCullBlockSheet(), true, itemStackIn.hasFoil());
            renderer.renderModelLists(itemModel, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, vertexBuilder);
        }

        matrixStackIn.popPose();
    }

    private boolean shouldRender2D(ItemCameraTransforms.TransformType transformType) {
        return transformType == ItemCameraTransforms.TransformType.GUI ||
                transformType == ItemCameraTransforms.TransformType.GROUND ||
                transformType == ItemCameraTransforms.TransformType.FIXED;
    }
}
