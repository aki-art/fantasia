package io.github.akiart.fantasia.client.entityRenderer;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.item.FBoatEntity;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;

public class FBoatRenderer extends BoatRenderer {

    ResourceLocation defaultTex = new ResourceLocation(Fantasia.ID, "textures/entity/boat/frozen_elm.png");
    public FBoatRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(BoatEntity entity) {
        if(entity instanceof FBoatEntity) {
            return ((FBoatEntity) entity).getActualType().getTextureLocation();
        }

        else return defaultTex;
    }
}
