package io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.akiart.fantasia.common.tileentity.FChestTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class FChestISTER<T extends FChestTileEntity> extends ItemStackTileEntityRenderer {
    private final Supplier<T> tileEntity;

    public FChestISTER(Supplier<T> tileEntity) {
        this.tileEntity = tileEntity;
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TileEntityRendererDispatcher.instance.renderItem(tileEntity.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
