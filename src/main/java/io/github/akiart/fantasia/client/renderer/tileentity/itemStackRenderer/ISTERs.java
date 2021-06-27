package io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer;

import io.github.akiart.fantasia.common.tileentity.FChestTileEntity;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;

public class ISTERs {

    public static ItemStackTileEntityRenderer createChestTileEntity(WoodType woodType) {
        return  new FChestISTER<>(getChestTEForWoodType(woodType));
    }

    protected static Supplier<FChestTileEntity> getChestTEForWoodType(WoodType woodType) {
        return () -> new FChestTileEntity(woodType);
    }

    public static ItemStackTileEntityRenderer createJavelinISTER(ResourceLocation texture) {
        return new JavelinISTER<>(texture);
    }
}
