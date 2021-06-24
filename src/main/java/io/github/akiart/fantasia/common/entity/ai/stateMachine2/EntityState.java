package io.github.akiart.fantasia.common.entity.ai.stateMachine2;

import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public abstract class EntityState {
    protected String name;
    protected int tickFrequency = 1;
    MobEntity entity;

    public EntityState(MobEntity entity) {
        this.entity = entity;
    }

    protected void enter() {

    }

    protected void exit() {

    }

    protected void clientTick() {

    }

    protected void serverTick() {

    }

    protected abstract EntityState getNextTransition();

    public int getTickFrequency() { return tickFrequency; }

    void setName(String name) { this.name = name; }

    String getName() {
        return this.name;
    }

    @Nullable
    Vector3d getTargetLocation() {
        return null;
    }

}
