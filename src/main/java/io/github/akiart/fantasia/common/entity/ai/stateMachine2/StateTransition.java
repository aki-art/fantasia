package io.github.akiart.fantasia.common.entity.ai.stateMachine2;

import net.minecraft.entity.MobEntity;

import java.util.function.Predicate;

public class StateTransition {
    EntityState target;
    Predicate<MobEntity> predicate;

    public StateTransition(EntityState target, Predicate<MobEntity> predicate) {
        this.target = target;
        this.predicate = predicate;
    }

    public boolean shouldTransition(MobEntity entity) {
        return predicate.test(entity);
    }
}
