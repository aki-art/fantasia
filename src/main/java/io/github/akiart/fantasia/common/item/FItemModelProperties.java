package io.github.akiart.fantasia.common.item;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.item.itemType.SaberToothJavelinItem;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FItemModelProperties {

    public static void setItemModelProperties() {
        ResourceLocation thrown = new ResourceLocation(Fantasia.ID, "thrown");
        ResourceLocation charging = new ResourceLocation(Fantasia.ID, "charging");
        ResourceLocation potionLevel = new ResourceLocation(Fantasia.ID, "potion_level");

        ItemModelsProperties.register(FItems.WOODEN_JAVELIN.get(), thrown, FItemModelProperties::isBeingUsed);
        ItemModelsProperties.register(FItems.FROSTWORK_PICKAXE.get(), charging, FItemModelProperties::isBeingUsed);
        ItemModelsProperties.register(FItems.SABER_TOOTH_JAVELIN.get(), potionLevel, FItemModelProperties::getPotionLevel);
    }

    private static float getPotionLevel(ItemStack itemStack, ClientWorld world, LivingEntity entity) {
//        if(itemStack.getItem() instanceof SaberToothJavelinItem) {
//            return ((SaberToothJavelinItem)itemStack.getItem()).getPotionUsesLeftForDisplay(itemStack);
//        }

        return 0.5f;
    }

    private static float isBeingUsed(ItemStack itemStack, ClientWorld world, LivingEntity entity) {
        return entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack ? 1.0F : 0.0F;
    }
}
