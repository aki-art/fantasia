package io.github.akiart.fantasia.common.block.blockType;

import io.github.akiart.fantasia.common.tileentity.FSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class FWallSignBlock extends WallSignBlock {

    public FWallSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new FSignTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
