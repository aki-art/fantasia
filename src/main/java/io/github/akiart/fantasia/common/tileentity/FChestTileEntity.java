package io.github.akiart.fantasia.common.tileentity;

import io.github.akiart.fantasia.client.ChestMaterial;
import io.github.akiart.fantasia.client.FAtlases;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT, _interface = IChestLid.class)
public class FChestTileEntity extends ChestTileEntity {
    ChestMaterial material;

    public FChestTileEntity() {
        this(FTileEntityTypes.CHEST.get());
    }

    public FChestTileEntity(WoodType woodType) {
        this(FTileEntityTypes.CHEST.get());
        setRenderMaterial(woodType);
    }

    public FChestTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public RenderMaterial getRenderMaterial(ChestType type) {
        return material.getForType(type);
    }

    public void setRenderMaterial(WoodType wood) {
        material = FAtlases.CHEST_MAT.get(wood);
    }

    @Override
    public TileEntityType<?> getType() {
        return FTileEntityTypes.CHEST.get();
    }
}
