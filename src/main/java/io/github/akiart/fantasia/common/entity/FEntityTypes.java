package io.github.akiart.fantasia.common.entity;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.hostile.SaberCatEntity;
import io.github.akiart.fantasia.common.entity.neutral.valravn.ValravnEntity2;
import io.github.akiart.fantasia.common.entity.neutral.valravn.deletelater.ValravnEntity;
import io.github.akiart.fantasia.common.entity.passive.PtarmiganEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class FEntityTypes {
    public static final EntityType<PtarmiganEntity> PTARMIGAN =
            EntityType.Builder
                    .of(PtarmiganEntity::new, EntityClassification.CREATURE)
                    .sized(0.4F, 0.7F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(Fantasia.ID, PtarmiganEntity.ID).toString());

    public static final EntityType<SaberCatEntity> SABER_CAT =
            EntityType.Builder
                    .of(SaberCatEntity::new, EntityClassification.CREATURE)
                    .sized(0.9F, 2F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(Fantasia.ID, SaberCatEntity.ID).toString());

    public static final EntityType<ValravnEntity> VALRAVN =
            EntityType.Builder
                    .of(ValravnEntity::new, EntityClassification.CREATURE)
                    .sized(0.7F, 0.7F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(Fantasia.ID, ValravnEntity.ID).toString());

    public static final EntityType<ValravnEntity2> VALRAVN2 =
            EntityType.Builder
                    .<ValravnEntity2>of(ValravnEntity2::new, EntityClassification.CREATURE)
                    .sized(0.7F, 0.7F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(Fantasia.ID, ValravnEntity2.ID).toString());
}
