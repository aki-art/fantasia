package io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.model.JavelinModel;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;

public class JavelinISTER<T extends JavelinEntity> extends ItemStackTileEntityRenderer {

    JavelinModel model = new JavelinModel();
    ModelResourceLocation itemModelLocation = new ModelResourceLocation("fantasia:gold_javelin_inventory#inventory");
    ResourceLocation texture;

    public JavelinISTER(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.pushPose();

        if (isInventory(transformType)) {
            // trying to render to inventory
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
            IBakedModel itemModel = renderer.getItemModelShaper().getModelManager().getModel(itemModelLocation);
            itemModel = ForgeHooksClient.handleCameraTransforms(matrixStackIn, itemModel, transformType, false);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            //itemModel.getTransforms().gui.apply(false, matrixStackIn);
            ForgeHooksClient.drawItemLayered(renderer, itemModel, itemStackIn, matrixStackIn, bufferIn, 15728880, combinedOverlayIn, true);

//            RenderType rendertype = RenderTypeLookup.getRenderType(itemStackIn, fabulous);
//            IVertexBuilder ivertexbuilder= ItemRenderer.getFoilBufferDirect(bufferIn, rendertype, true, itemStackIn.hasFoil());
//            renderer.renderModelLists(itemModel, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, ivertexbuilder);

        }

        else {
            // render as 3d model
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder builder = ItemRenderer.getFoilBufferDirect(bufferIn, model.renderType(texture), false, itemStackIn.hasFoil());
            model.renderToBuffer(matrixStackIn, builder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        matrixStackIn.popPose();
    }

    private boolean isInventory(ItemCameraTransforms.TransformType transformType) {
        return transformType == ItemCameraTransforms.TransformType.GUI ||
                transformType == ItemCameraTransforms.TransformType.GROUND ||
                transformType == ItemCameraTransforms.TransformType.FIXED;
    }
}
