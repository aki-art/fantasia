package io.github.akiart.fantasia.client;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FWoodType;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.ResourceLocation;

public class ChestMaterial {

    private final RenderMaterial left;
    private final RenderMaterial right;
    private final RenderMaterial single;

    public ChestMaterial(WoodType woodType) {
        String woodName = FWoodType.getName(woodType);
        left = chestTexture(woodName + "_left");
        right = chestTexture(woodName +  "_right");
        single = chestTexture(woodName);
    }

    public RenderMaterial getLeft() {
        return left;
    }

    public RenderMaterial getRight() {
        return right;
    }

    public RenderMaterial getSingle() {
        return single;
    }

    public RenderMaterial getForType(ChestType type) {
        switch(type) {
            case LEFT:
                return left;
            case RIGHT:
                return right;
            case SINGLE:
            default:
                return single;
        }
    }

    private RenderMaterial chestTexture(String path) {
        return new RenderMaterial(Atlases.CHEST_SHEET, new ResourceLocation(Fantasia.ID, "entity/chest/" + path));
    }
}
