package io.github.akiart.fantasia.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class FMountableEntity extends FTameableEntity {

    protected FMountableEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void mount(PlayerEntity player) {
        //this.setEating(false);
        //this.setStanding(false);
        if (!this.level.isClientSide) {
            player.yRot = yRot;
            player.xRot = xRot;
            player.startRiding(this);
        }
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public boolean canBeControlledByRider() {
        return getControllingPassenger() instanceof LivingEntity;
    }

    public void travel(Vector3d motion) {
        if(!isAlive()) return;
        if(isMounted()) {
            Entity controller = getControllingPassenger();
            if(getControllingPassenger() != null && getControllingPassenger() instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) getControllingPassenger();
                this.yRot = livingentity.yRot;
                this.yRotO = yRot;
                this.xRot = livingentity.xRot * 0.5F;

                this.setRot(this.yRot, this.xRot);

                this.yBodyRot = this.yRot;
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;

                this.flyingSpeed = this.getSpeed() * 0.1F;

                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vector3d((double)f, motion.y, (double)f1));
                } else if (livingentity instanceof PlayerEntity) {
                    this.setDeltaMovement(Vector3d.ZERO);
                }
            }
        }

        super.travel(motion);
    }

    protected boolean isMounted() {
        return isVehicle() && canBeControlledByRider();
    }
}
