package io.github.akiart.fantasia.mixin;


import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(DimensionGeneratorSettings.class)
public class WorldSeedMixin {
    // Stored the world seed for later use in world generation
    // https://github.com/TelepathicGrunt/WorldBlender/blob/af8ad56ee290baf53e8c0352fad3cc21c2c8647d/src/main/java/com/telepathicgrunt/worldblender/mixin/dimensions/DimensionGeneratorSettingsMixin.java
    @Inject(method = "<init>(JZZLnet/minecraft/util/registry/SimpleRegistry;Ljava/util/Optional;)V",
            at = @At(value = "RETURN"))
    private void giveUsRandomSeeds2(long seed, boolean generateFeatures, boolean bonusChest, SimpleRegistry<Dimension> registry, Optional<String> s, CallbackInfo callbackInfo) {
        LogManager.getLogger().info("mixin works, {}", seed);
    }
}