package io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.tileentity.FChestTileEntity;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.model.ModelResourceLocation;
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

    public static ItemStackTileEntityRenderer createJavelinISTER(String name) {
        ResourceLocation texture = new ResourceLocation(Fantasia.ID, "textures/entity/javelin/" + name + ".png");
        ModelResourceLocation itemModelLocation = new ModelResourceLocation("fantasia:" + name + "_inventory#inventory");
        return new JavelinISTER<>(texture, itemModelLocation);
    }
}
