package io.github.akiart.fantasia.common.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.SignTileEntity;

public class FSignTileEntity extends SignTileEntity {
    @Override
    public TileEntityType<?> getType() {
        return FTileEntityTypes.SIGN.get();
    }
}
