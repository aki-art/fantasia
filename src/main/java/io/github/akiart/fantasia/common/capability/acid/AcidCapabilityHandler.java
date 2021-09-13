package io.github.akiart.fantasia.common.capability.acid;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class AcidCapabilityHandler implements IAcidCapability {

    private boolean isInAcid;
    private boolean wasInAcid;
    private boolean wasEyeInAcid;
    private Entity entity;
    private static double MIN_ACID_LEVEL = 0.9D;
    private static double ACID_MOTION_SCALE = 0.014D;

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void tick() {
        if(isInAcid) {
            entity.updateFluidHeightAndDoFluidPushing(FTags.Fluids.ACID, ACID_MOTION_SCALE);

            double fluidHeight = entity.getFluidHeight(FTags.Fluids.ACID);

            if(fluidHeight > MIN_ACID_LEVEL) {
                entity.setSwimming(true);
            }
            else if(fluidHeight < 0.005f){
                isInAcid = false;
                return;
            }

            if(!wasInAcid && entity.tickCount > 0) {
                doAcidSplashEffect(entity);
            }

            wasInAcid = true;
        }
        else if(wasInAcid) {
            // update to clear
            entity.updateFluidHeightAndDoFluidPushing(FTags.Fluids.ACID, ACID_MOTION_SCALE);
            entity.setSwimming(false);
            isInAcid = false;
            wasInAcid = false;
        }
    }

    // largely a copy paste of Entity.doWaterSplashEffect()
    // spawns particles and plays sounds for swimming
    protected void doAcidSplashEffect(Entity entity) {
        Entity controllingEntity = entity.isVehicle() && entity.getControllingPassenger() != null ? entity.getControllingPassenger() : entity;
        float f = controllingEntity == entity ? 0.2F : 0.9F;
        Vector3d vector3d = controllingEntity.getDeltaMovement();
        float speed = MathHelper.sqrt(vector3d.x * vector3d.x * (double) 0.2F + vector3d.y * vector3d.y + vector3d.z * vector3d.z * (double) 0.2F) * f;

        speed = Math.min(speed, 1);

        entity.playSound(SoundEvents.GENERIC_SPLASH, speed, 1.0F + (entity.random.nextFloat() - entity.random.nextFloat()) * 0.4F);

        float y = (float) MathHelper.floor(entity.getY());

        EntitySize dimensions = entity.getType().getDimensions();

        for (int i = 0; (float) i < 1.0F + dimensions.width * 20.0F; ++i) {
            double x = entity.getRandomX(dimensions.width);
            double z = entity.getRandomZ(dimensions.width);
            entity.level.addParticle(ParticleTypes.BUBBLE, entity.getX() + x, y + 1.0F, entity.getZ() + z, vector3d.x, vector3d.y - entity.random.nextDouble() * (double) 0.2F, vector3d.z);
        }

        for (int i = 0; (float) i < 1.0F + dimensions.width * 20.0F; ++i) {
            double x = entity.getRandomX(dimensions.width);
            double z = entity.getRandomZ(dimensions.width);
            entity.level.addParticle(ParticleTypes.SPLASH, entity.getX() + x, y + 1.0F, entity.getZ() + z, vector3d.x, vector3d.y, vector3d.z);
        }
    }

    @Override
    public boolean isInAcid() {
        return isInAcid;
    }

    @Override
    public boolean wasInAcid() {
        return wasInAcid;
    }

    @Override
    public void setIsInAcid(boolean value) {
        isInAcid = value;
    }

    @Override
    public boolean wasEyeInAcid() {
        return wasEyeInAcid;
    }

    @Override
    public void setEyeWasInAcid(boolean value) {
        wasEyeInAcid = value;
    }

    @Override
    public void setWasInAcid(boolean value) {
        wasInAcid = value;
    }

    void initState(boolean isInAcid, boolean wasInAcid) {
        this.isInAcid = isInAcid;
        this.wasInAcid = wasInAcid;
    }
}
