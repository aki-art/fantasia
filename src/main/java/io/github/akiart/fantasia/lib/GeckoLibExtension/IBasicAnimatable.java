package io.github.akiart.fantasia.lib.GeckoLibExtension;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.passive.PtarmiganEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

// provides defaults generated from ID so not every single entity has to provide the same results every time
public interface IBasicAnimatable extends IAnimatable {

    public String getID();

    public default ResourceLocation getModel() {
        return new ResourceLocation(Fantasia.ID, "geo/" + getID() + ".geo.json");
    }

    public default ResourceLocation getAnimation() {
        return new ResourceLocation(Fantasia.ID, "animations/" + getID() + ".animation.json");
    }

    public default ResourceLocation getTexture() {
        return new ResourceLocation(Fantasia.ID, "textures/entity/" + getID() + ".png");
    }
}
