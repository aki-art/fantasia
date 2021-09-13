package io.github.akiart.fantasia.mixin;

import io.github.akiart.fantasia.common.capability.Capabilities;
import io.github.akiart.fantasia.common.potion.FEffects;
import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.ForgeMod;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    protected boolean jumping;

    @Shadow
    protected abstract void jumpInLiquid(ITag<Fluid> fluidTag);

    private static final int EFFECT_DURATION = 800;

    // gives short Acid Repel and Frost Resisance effect if recently used a Totem of Undying,
    // to make sure the player doesn't immediately die again
    // LivingAttackEvent is not an option to replace this, because the forge event happens earlier, and the totem wipes all effects first
    @Inject(
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/entity/LivingEntity;removeAllEffects()Z"
            ),
            method = "checkTotemDeathProtection(Lnet/minecraft/util/DamageSource;)Z"
    )
    private void checkTotemDeathProtection(CallbackInfoReturnable<Boolean> callback) {
        LivingEntity entity = (LivingEntity) (Object) this;
        entity.addEffect(new EffectInstance(FEffects.ACID_REPEL.get(), EFFECT_DURATION));
        // TODO: entity.addEffect(new EffectInstance(FEffects.FROST_RESISTANCE.get(), EFFECT_DURATION));
    }

    // FIXME: this could be incompatible with modded entities that override aiStep completely
    @Inject(
            at = @At("TAIL"),
            method = "aiStep()V"
    )
    private void aiStep(CallbackInfo info) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if(jumping && entity.isAffectedByFluids()) {
            entity.getCapability(Capabilities.ACID).ifPresent(cap -> {
                if (cap.isInAcid()) {
                    double fluidHeight = entity.getFluidHeight(FTags.Fluids.ACID);
                    if (fluidHeight > 0) {
                        jumpInLiquid(FTags.Fluids.ACID);
                    }
                }
            });
        }
    }

    // Makes living entities understand how deep acid they are in
    // using this value the entity should float up thinking it is in water, which is good enough
//    @ModifyVariable(
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/LivingEntity;isInWater()Z",
//                    ordinal = 7
//            ),
//            method = "aiStep()V"
//    )
//    private double d7(double original) {
//            if(original == 0) {
//                LivingEntity entity = (LivingEntity) (Object) this;
//                return entity.getFluidHeight(FTags.Fluids.ACID);
//            }
//
//            return original;
//    }
}
