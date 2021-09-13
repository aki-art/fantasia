package io.github.akiart.fantasia.mixin;

import io.github.akiart.fantasia.common.capability.Capabilities;
import io.github.akiart.fantasia.common.capability.acid.IAcidCapability;
import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)

public abstract class EntityMixin {

    @Shadow
    protected boolean wasEyeInWater;

    @Inject(
            at = @At("RETURN"),
            method = "updateInWaterStateAndDoFluidPushing()Z", // weird method name, it updates both water and lava
            cancellable = true
    )
    private void updateInWaterStateAndDoWaterCurrentPushing(CallbackInfoReturnable<Boolean> callback) {

        Entity entity = (Entity) (Object) this;
        entity.getCapability(Capabilities.ACID).ifPresent(capability -> {
            capability.tick();
            capability.setEyeWasInAcid(entity.isEyeInFluid(FTags.Fluids.ACID));
            if(capability.wasInAcid()) {
                entity.wasTouchingWater = true;
                callback.setReturnValue(true);
            }
            if(capability.wasEyeInAcid()) {
                wasEyeInWater = true;
//                if(!entity.isSwimming()) {
//                    entity.setSwimming(entity.isSprinting() && !entity.isPassenger());
//                }
            }
        });
    }

    @Inject(
            at = @At("TAIL"),
            method = "updateSwimming()V"
    )
    private void updateSwimming(CallbackInfo callback) {

    }
}
