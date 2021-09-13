package io.github.akiart.fantasia.common.item.itemGroup;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.item.itemTypes.CoinItem;
import io.github.akiart.fantasia.common.item.itemTypes.ILargeStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FItemModelProperties {
    public static void setItemModelProperties() {
        ResourceLocation count = new ResourceLocation(Fantasia.ID, "count");

        ItemModelsProperties.register(FItems.COIN.get(), count, FItemModelProperties::getCountProperty);
    }

    private static float getCountProperty(ItemStack itemStack, ClientWorld world, LivingEntity entity) {
        if (itemStack.getItem() instanceof ILargeStack) {
            int count = ((ILargeStack) itemStack.getItem()).getCount(itemStack);
            if (count < 5) return 0;
            else if (count < 35) return 0.33f;
            else if (count < 100) return 0.66f;
            else return 1f;
        }

        return 0;
    }
}
