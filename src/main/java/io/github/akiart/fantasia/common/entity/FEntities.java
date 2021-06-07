package io.github.akiart.fantasia.common.entity;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.passive.PtarmiganEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Fantasia.ID);

    public static final RegistryObject<EntityType<PtarmiganEntity>> PTARMIGAN = ENTITIES.register("ptarmigan", () ->
            EntityType.Builder.of(PtarmiganEntity::new, EntityClassification.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(10).build(new ResourceLocation(Fantasia.ID, "ptarmigan").toString()));
    //private void register(String name, )
}
