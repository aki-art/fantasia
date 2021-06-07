package io.github.akiart.fantasia.common.entity.ai;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FEntities;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fantasia.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeAttributes {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(FEntities.PTARMIGAN.get(), ChickenEntity.createAttributes().build());
    }
}