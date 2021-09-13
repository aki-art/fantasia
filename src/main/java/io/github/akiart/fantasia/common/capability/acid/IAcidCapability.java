package io.github.akiart.fantasia.common.capability.acid;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public interface IAcidCapability {
    boolean wasInAcid();
    void setWasInAcid(boolean value);
    boolean isInAcid();
    void setEntity(Entity entity);
    void tick();
    void setIsInAcid(boolean value);
    boolean wasEyeInAcid();
    void setEyeWasInAcid(boolean value);
    default boolean isUnderAcid() {
        return wasEyeInAcid() && isInAcid();
    }
}
