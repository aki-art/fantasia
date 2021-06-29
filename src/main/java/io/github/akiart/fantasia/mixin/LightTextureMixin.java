package io.github.akiart.fantasia.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightTexture.class)
public class LightTextureMixin {
    @Shadow
    @Final
    private DynamicTexture texture;
    @Shadow
    @Final
    private NativeImage lightPixels;
    @Shadow
    private float field_21528;
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
            method = "update",
            at = @At(
                    value = "JUMP",
                    ordinal = 1,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )

    private void updateLightTexture(float partialTicks, CallbackInfo info) {
        ClientWorld world = minecraft.level;
        if(world == null) {
            return;
        }

        for(int skyLight = 0; skyLight < 16; skyLight++) {
            for(int blockLight = 0; blockLight < 16; blockLight++) {
                lightPixels.setPixelRGBA(255, 0, 0);
            }
        }

        texture.upload();
        minecraft.getProfiler().pop();
        info.cancel();
    }
}
