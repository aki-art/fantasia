package io.github.akiart.fantasia.common.entity.model;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.passive.PtarmiganEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PtarmiganModel extends AnimatedGeoModel<PtarmiganEntity> {
    @Override
    public ResourceLocation getModelLocation(PtarmiganEntity object) {
        return new ResourceLocation(Fantasia.ID, "geo/ptarmigan.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PtarmiganEntity object) {
        return new ResourceLocation(Fantasia.ID, "textures/entity/ptarmigan/ptarmigan_white.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PtarmiganEntity object) {
        return new ResourceLocation(Fantasia.ID, "animations/ptarmigan.animation.json");
    }
}
