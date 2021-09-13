package io.github.akiart.fantasia.common.item;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import io.github.akiart.fantasia.common.item.itemTypes.CoinItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Fantasia.ID);

    // Misc
    public static final RegistryObject<Item> COIN = ItemRegistryUtil.register("coin", CoinItem::new);
    public static final RegistryObject<Item> ACID_BUCKET = ITEMS.register("acid_bucket",
            () -> new BucketItem(FFluids.ACID_SOURCE, new Item.Properties()
                    .craftRemainder(Items.BUCKET)
                    .stacksTo(1)
                    .tab(FItemGroup.FANTASIA)));

    public static final RegistryObject<Item> DRUID_HOOD = ItemRegistryUtil.register("druid_hood",
            () -> new ArmorItem(FArmorMaterial.DRUID, EquipmentSlotType.HEAD, defaultProperty()));

    public static final RegistryObject<BlockItem> TEST_ACID_LOGGABLE = ItemRegistryUtil.registerFromBlock(FBlocks.TEST_ACID_LOGGABLE);

    private static Item.Properties defaultProperty() {
        return new Item.Properties().tab(ItemGroup.TAB_COMBAT);
    }
}
