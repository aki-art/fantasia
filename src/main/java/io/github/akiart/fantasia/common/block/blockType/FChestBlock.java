package io.github.akiart.fantasia.common.block.blockType;

import io.github.akiart.fantasia.common.tileentity.FChestTileEntity;
import io.github.akiart.fantasia.common.tileentity.FTileEntityTypes;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.WoodType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class FChestBlock extends ChestBlock {

    WoodType woodType;

    public FChestBlock(Properties properties, WoodType woodType) {
        super(properties, FTileEntityTypes.CHEST::get);
        this.woodType = woodType;
    }

    @OnlyIn(Dist.CLIENT)
    public static Item.Properties setISTER(Item.Properties props, WoodType wood) {
        return props; //.setISTER(() -> () -> new FChestItemStackTileEntityRenderer<>(() -> new FChestTileEntity(wood)));
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader reader) {
        FChestTileEntity te = new FChestTileEntity();
        te.setRenderMaterial(woodType);
        return te;
    }
}
