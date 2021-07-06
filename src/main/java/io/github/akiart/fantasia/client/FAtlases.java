package io.github.akiart.fantasia.client;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FWoodType;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FAtlases {
    public static final Map<WoodType, RenderMaterial> CHEST_MATERIALS = FWoodType.values().collect(Collectors.toMap(Function.identity(), wood -> chestTexture(wood, "")));
    public static final Map<WoodType, RenderMaterial> CHEST_LEFT_MATERIALS = FWoodType.values().collect(Collectors.toMap(Function.identity(),  wood -> chestTexture(wood, "_left")));
    public static final Map<WoodType, RenderMaterial> CHEST_RIGHT_MATERIALS = FWoodType.values().collect(Collectors.toMap(Function.identity(),  wood -> chestTexture(wood, "_right")));
    //public static final Map<JavelinEntity.Type, RenderMaterial> JAVELIN_MATERIALS = FWoodType.values().collect(Collectors.toMap(Function.identity(), wood -> chestTexture(wood, "_right")));

    public static RenderMaterial chestTexture(WoodType woodType, String suffix) {
        ResourceLocation location = new ResourceLocation(woodType.name());
        return new RenderMaterial(Atlases.CHEST_SHEET, new ResourceLocation(Fantasia.ID, "entity/chest/" + location.getPath() + suffix));
    }

    // Registering atlases so tile entity can use them later.
    @SubscribeEvent
    public static void onTextureStitchedPre(TextureStitchEvent.Pre event) {
        if (event.getMap().location().equals(Atlases.CHEST_SHEET)) {
            CHEST_MATERIALS.values().forEach(m -> event.addSprite(m.texture()));
            CHEST_LEFT_MATERIALS.values().forEach(m -> event.addSprite(m.texture()));
            CHEST_RIGHT_MATERIALS.values().forEach(m -> event.addSprite(m.texture()));
        }
    }
}
