package io.github.akiart.fantasia.common.entity.ai.behaviour;

import net.minecraft.entity.LivingEntity;

import java.util.*;

/* A simplistic way to handle AI
Allows certain tasks to be only ran at 200 or 4000 tick intervals to lessen the performance impact (hopefully).
Unlike Goals, these can run concurrently and independently of each other.
*/

public class BehaviourManager<T extends LivingEntity> {
    List<Behaviour> behaviours;
    T entity;
    long ticker = 0;

    public BehaviourManager(T entity) {
        this.entity = entity;
        behaviours = new ArrayList<>();
    }

    public void add(Behaviour behaviour) {
        behaviours.add(behaviour.withEntity(entity));
    }

    public void tick() {
        for (Behaviour behaviour : behaviours) {
            behaviour.step();
        }
    }
}
