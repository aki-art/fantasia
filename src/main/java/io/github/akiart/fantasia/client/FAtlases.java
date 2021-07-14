package io.github.akiart.fantasia.client;

import io.github.akiart.fantasia.common.block.FWoodType;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FAtlases {
  public static final Map<WoodType, ChestMaterial> CHEST_MAT = FWoodType.values().collect(Collectors.toMap(Function.identity(), ChestMaterial::new));

    // Registering atlases so tile entity can use them later.
    @SubscribeEvent
    public static void onTextureStitchedPre(TextureStitchEvent.Pre event) {
        if (event.getMap().location().equals(Atlases.CHEST_SHEET)) {
            CHEST_MAT.values().forEach(m -> {
                event.addSprite(m.getLeft().texture());
                event.addSprite(m.getRight().texture());
                event.addSprite(m.getSingle().texture());
            });
        }
    }
}
