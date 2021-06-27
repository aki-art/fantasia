package io.github.akiart.fantasia.client.renderer.tileentity;

import io.github.akiart.fantasia.common.tileentity.FChestTileEntity;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;

public class FChestTileEntityRenderer<T extends FChestTileEntity & IChestLid> extends ChestTileEntityRenderer<T> {
    public FChestTileEntityRenderer(TileEntityRendererDispatcher renderer) {
        super(renderer);
    }

    @Override
    protected RenderMaterial getMaterial(T tileEntity, ChestType chestType) {
        return tileEntity.getMaterial(chestType);
    }
}
