package io.github.akiart.fantasia.common.capability.entity;

import net.minecraft.entity.Entity;

import java.util.Map;
import java.util.UUID;

public interface IDruidHoodPacifible {
    Map<UUID, Integer> getTraitors();
    boolean isTraitor(Entity entity);
    void addTraitor(UUID uuid, int time);
}
