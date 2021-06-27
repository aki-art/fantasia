package io.github.akiart.fantasia.common.tileentity;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.client.FAtlases;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT, _interface = IChestLid.class)
public class FChestTileEntity extends ChestTileEntity {

    RenderMaterial materialLeft;
    RenderMaterial materialRight;
    RenderMaterial materialSingle;

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

    public RenderMaterial getMaterial(ChestType type) {
        switch(type) {
            case LEFT:
                return materialLeft == null ? Atlases.CHEST_LOCATION_LEFT : materialLeft;
            case RIGHT:
                return materialRight == null ? Atlases.CHEST_LOCATION_RIGHT : materialRight;
            case SINGLE:
            default:
                return materialSingle == null ? Atlases.CHEST_LOCATION : materialSingle;
        }
    }

    public void setRenderMaterial(WoodType wood) {
        materialLeft = FAtlases.CHEST_LEFT_MATERIALS.getOrDefault(wood, Atlases.CHEST_LOCATION_LEFT);
        materialRight = FAtlases.CHEST_RIGHT_MATERIALS.getOrDefault(wood, Atlases.CHEST_LOCATION_RIGHT);
        materialSingle = FAtlases.CHEST_MATERIALS.getOrDefault(wood, Atlases.CHEST_LOCATION);
    }

    @Override
    public TileEntityType<?> getType() {
        return FTileEntityTypes.CHEST.get();
    }
}
