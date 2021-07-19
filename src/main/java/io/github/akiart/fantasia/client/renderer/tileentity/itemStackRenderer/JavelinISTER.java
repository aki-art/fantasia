package io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.akiart.fantasia.common.entity.model.JavelinModel;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import io.github.akiart.fantasia.common.item.itemType.TippedSaberToothJavelinItem;
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

public class JavelinISTER<T extends JavelinEntity> extends ItemStackTileEntityRenderer {

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
            //matrixStackIn.last().normal().svdDecompose().getMiddle();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder vertexBuilder = ItemRenderer.getFoilBufferDirect(bufferIn, model.renderType(texture), false, itemStackIn.hasFoil());
            model.renderToBuffer(matrixStackIn, vertexBuilder, combinedLightIn, combinedOverlayIn);
            matrixStackIn.popPose();
        }
    }

    // ResourceLocation saberToothOverlay = new ResourceLocation(Fantasia.ID, "item/saber_tooth_javelin_overlay.png");

    protected void renderGUI(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.popPose();
        matrixStackIn.pushPose();


        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        IBakedModel itemModel = renderer.getItemModelShaper().getModelManager().getModel(itemModelLocation);

        matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
        itemModel.handlePerspective(transformType, matrixStackIn);

        if (!(itemStackIn.getItem() instanceof TippedSaberToothJavelinItem)) {
            IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(bufferIn, Atlases.translucentCullBlockSheet(), true, itemStackIn.hasFoil());
            renderer.renderModelLists(itemModel, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, vertexBuilder);
        }
        else {
            ForgeHooksClient.drawItemLayered(renderer, itemModel, itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedLightIn, true);
            float uses = ((TippedSaberToothJavelinItem) itemStackIn.getItem()).getPotionUsesLeftForDisplay(itemStackIn);

            // potion use durability bar
            if (uses < 1f && transformType == ItemCameraTransforms.TransformType.GUI) {
                int color = TippedSaberToothJavelinItem.getColor(itemStackIn);

                float barWidth = 13f;
                int w = Math.round(barWidth - uses * barWidth);

                matrixStackIn.popPose();
                matrixStackIn.pushPose();

                matrixStackIn.scale(-1, 1, -1);
                matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
                matrixStackIn.scale(1f / 16f, 1f / 16f, 1f / 16f);

                GuiUtils.drawGradientRect(matrixStackIn.last().pose(), -1, 1, 4, 14, 6, Constants.Colors.BLACK, Constants.Colors.BLACK);
                GuiUtils.drawGradientRect(matrixStackIn.last().pose(), -1, 1 + w, 5, 14, 6, color, color);
            }
        }

        matrixStackIn.popPose();
    }

    private boolean shouldRender2D(ItemCameraTransforms.TransformType transformType) {
        return transformType == ItemCameraTransforms.TransformType.GUI ||
                transformType == ItemCameraTransforms.TransformType.GROUND ||
                transformType == ItemCameraTransforms.TransformType.FIXED;
    }
}
