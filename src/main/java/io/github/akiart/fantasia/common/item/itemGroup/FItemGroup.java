package io.github.akiart.fantasia.common.item.itemGroup;

import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FItemGroup {
    public static final ItemGroup FANTASIA = (new ItemGroup("fantasia") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(FItems.COIN.get());
        }
    }).setRecipeFolderName("fantasia");

    public static final ItemGroup FANTASIA_BUILDING_BLOCKS = (new ItemGroup("fantasia_building_blocks") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(FItems.COIN.get());
        }
    }).setRecipeFolderName("fantasia_building_blocks");
}