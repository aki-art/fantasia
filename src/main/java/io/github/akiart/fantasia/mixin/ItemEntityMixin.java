package io.github.akiart.fantasia.mixin;

import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    private static final double MAXIMUM_SPEED = 0.06f;
    private static final float SPEED = 5.0E-4F;
    private static final double HORIZONTAL_DRAG = 0.99f;
    private static final float GRAVITY = 0.04f;
    private static final float HEIGHT_OFFSET = 0.11111111F;

    // makes items float to the top of acid like they would in water
    // there is no forge event for item tick, and AcidFluidBlock-s entityInside doesn't update frequently enough, makes the item jittery
    @Inject(
            method = "tick()V",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/item/ItemEntity;isInLava()Z"
                    )
            ),
            at = @At(
                    value="JUMP",
                    opcode = Opcodes.IFEQ,
                    ordinal = 0
            )
    )
    private void tick(CallbackInfo info) {
        ItemEntity entity = (ItemEntity) (Object) this;
        if(entity.isInWater() && entity.getFluidHeight(FTags.Fluids.ACID) > entity.getEyeHeight() - HEIGHT_OFFSET) {
            setUnderwaterMovement(entity);
        }
    }

    private void setUnderwaterMovement(Entity entity) {
        Vector3d movement = entity.getDeltaMovement();
        double y = movement.y + (double)(movement.y < MAXIMUM_SPEED ? SPEED : 0);
        y += GRAVITY;
        entity.setDeltaMovement(movement.x * HORIZONTAL_DRAG, y, movement.z * HORIZONTAL_DRAG);
    }
}
