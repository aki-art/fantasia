package io.github.akiart.fantasia.common.block.blockType;

import io.github.akiart.fantasia.common.tileentity.FSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class FSignBlock extends StandingSignBlock {

    public FSignBlock(Properties properties, WoodType wood) {
        super(properties, wood);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new FSignTileEntity();
    }
}
