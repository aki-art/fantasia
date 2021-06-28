package io.github.akiart.fantasia.common.block.blockType;

import io.github.akiart.fantasia.common.tileentity.FChestTileEntity;
import io.github.akiart.fantasia.common.tileentity.FTileEntityTypes;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class FChestBlock extends ChestBlock {

    WoodType woodType;

    public FChestBlock(Properties properties, WoodType woodType) {
        super(properties, FTileEntityTypes.CHEST::get);
        this.woodType = woodType;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader reader) {
        FChestTileEntity te = new FChestTileEntity();
        te.setRenderMaterial(woodType);
        return te;
    }
}
