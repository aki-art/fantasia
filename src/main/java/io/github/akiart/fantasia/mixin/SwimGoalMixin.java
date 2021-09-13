package io.github.akiart.fantasia.mixin;

import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Allows entities to repeatedly jump in acid and try to swim
@Mixin(SwimGoal.class)
public class SwimGoalMixin {

    @Final
    @Shadow
    private MobEntity mob;

    @Inject(
            at = @At("RETURN"),
            method = "canUse()Z",
            cancellable = true
    )
    private void canUse(CallbackInfoReturnable<Boolean> callback) {
        if(!callback.getReturnValue()) {
            callback.setReturnValue(mob.getFluidHeight(FTags.Fluids.ACID) > 0);
        }
    }
}
