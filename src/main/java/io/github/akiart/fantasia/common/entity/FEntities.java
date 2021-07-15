package io.github.akiart.fantasia.common.entity;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.hostile.SaberCatEntity;
import io.github.akiart.fantasia.common.entity.item.FBoatEntity;
import io.github.akiart.fantasia.common.entity.neutral.valravn.deletelater.ValravnEntity;
import io.github.akiart.fantasia.common.entity.neutral.valravn.ValravnEntity2;
import io.github.akiart.fantasia.common.entity.passive.PtarmiganEntity;
import io.github.akiart.fantasia.common.entity.projectile.FrostworkPickaxeProjectileEntity;
import io.github.akiart.fantasia.common.entity.projectile.IcicleEntity;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import io.github.akiart.fantasia.common.entity.projectile.PtarmiganEggEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Fantasia.ID);

    public static final RegistryObject<EntityType<PtarmiganEntity>> PTARMIGAN = ENTITIES.register(PtarmiganEntity.ID, () -> FEntityTypes.PTARMIGAN);
    public static final RegistryObject<EntityType<SaberCatEntity>> SABER_CAT = ENTITIES.register(SaberCatEntity.ID, () -> FEntityTypes.SABER_CAT);
    public static final RegistryObject<EntityType<ValravnEntity>> VALRAVN = ENTITIES.register(ValravnEntity.ID, () -> FEntityTypes.VALRAVN);
    public static final RegistryObject<EntityType<ValravnEntity2>> VALRAVN2 = ENTITIES.register(ValravnEntity2.ID, () -> FEntityTypes.VALRAVN2);

    public static final RegistryObject<EntityType<PtarmiganEggEntity>> PTARMIGAN_EGG = ENTITIES.register(PtarmiganEggEntity.ID, () ->
            EntityType.Builder
                    .<PtarmiganEggEntity>of(PtarmiganEggEntity::new, EntityClassification.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(new ResourceLocation(Fantasia.ID, PtarmiganEggEntity.ID).toString()));

    public static final RegistryObject<EntityType<JavelinEntity>> JAVELIN = ENTITIES.register("javelin", () ->
            EntityType.Builder
                    .<JavelinEntity>of(JavelinEntity::new, EntityClassification.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(Fantasia.ID, "javelin").toString()));

    public static final RegistryObject<EntityType<FrostworkPickaxeProjectileEntity>> FROSTWORK_PICKAXE_PROJECTILE = ENTITIES.register(FrostworkPickaxeProjectileEntity.ID, () ->
            EntityType.Builder
                    .<FrostworkPickaxeProjectileEntity>of(FrostworkPickaxeProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(6)
                    .build(new ResourceLocation(Fantasia.ID, FrostworkPickaxeProjectileEntity.ID).toString()));

    public static final RegistryObject<EntityType<IcicleEntity>> ICICLE = ENTITIES.register("icicle", () ->
            EntityType.Builder
                    .<IcicleEntity>of(IcicleEntity::new, EntityClassification.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(Fantasia.ID, "icicle").toString()));

    public static final RegistryObject<EntityType<FBoatEntity>> BOAT = ENTITIES.register("boat", () ->
            EntityType.Builder
                    .<FBoatEntity>of(FBoatEntity::new, EntityClassification.MISC)
                    .sized(1.375f, 0.5625f)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(Fantasia.ID, "boat").toString()));
}
