package io.github.akiart.fantasia.common.entity.model;

import io.github.akiart.fantasia.lib.GeckoLibExtension.IBasicAnimatable;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

// used for all generic geckolib driven models.
public class GeckoLibModel<T extends IBasicAnimatable> extends AnimatedGeoModel<T> {

    @Override
    public ResourceLocation getModelLocation(T object) {
        return object.getModel();
    }

    @Override
    public ResourceLocation getTextureLocation(T object) {
        return object.getTexture();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {
        return animatable.getAnimation();
    }
}
