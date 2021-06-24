package io.github.akiart.fantasia.common.entity.ai.behaviour;

import net.minecraft.entity.LivingEntity;

public abstract class Behaviour {

    private boolean active = false;
    private boolean suspended = false;
    protected LivingEntity entity;

    public abstract boolean canUse();

    public void step() {

        if(suspended) return;

        boolean shouldBeActive = canUse();

        if(!active && shouldBeActive) enter();
        else if(active && !shouldBeActive) exit();
        else if(active) tick();

        active = shouldBeActive;
    }

    public Behaviour withEntity(LivingEntity entity) {
        this.entity = entity;
        return this;
    }

    public boolean isActive() { return active && !suspended; }

    public void suspend() { suspended = true; }
    public void resume() { suspended = false; }
    public void toggle() { suspended = !suspended; }

    public void stop() {
        if(active) exit();
        suspend();
    }

    public void enter() {}
    public void exit() {}
    public void tick() {}
}
