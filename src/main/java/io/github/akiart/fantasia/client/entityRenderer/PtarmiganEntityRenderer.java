package io.github.akiart.fantasia.client.entityRenderer;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.model.PtarmiganModel;
import io.github.akiart.fantasia.common.entity.passive.PtarmiganEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PtarmiganEntityRenderer extends GeoEntityRenderer<PtarmiganEntity> {
    public PtarmiganEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager, new PtarmiganModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

    @Override
    public ResourceLocation getTextureLocation(PtarmiganEntity entity) {
        return new ResourceLocation(Fantasia.ID, "textures/entity/ptarmigan/ptarmigan_white.png");
    }
}
