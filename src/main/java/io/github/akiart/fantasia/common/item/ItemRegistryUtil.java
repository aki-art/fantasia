package io.github.akiart.fantasia.common.item;

import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ItemRegistryUtil {
    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return FItems.ITEMS.register(name, item);
    }

    public static RegistryObject<BlockItem> registerFromBlock(RegistryObject<? extends Block> parent) {
        return FItems.ITEMS.register(parent.getId().getPath(),
                () -> new BlockItem(parent.get(), new Item.Properties().tab(FItemGroup.FANTASIA)));
    }
}
