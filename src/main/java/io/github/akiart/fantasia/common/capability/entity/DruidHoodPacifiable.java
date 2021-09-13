package io.github.akiart.fantasia.common.capability.entity;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DruidHoodPacifiable implements IDruidHoodPacifible {

    @CapabilityInject(IDruidHoodPacifible.class)
    public static final Capability<IDruidHoodPacifible> INSTANCE = null;

    public static final ResourceLocation ID = new ResourceLocation(Fantasia.ID, "druid_hood_pacifiable_capability");

    private final Map<UUID, Integer> traitors = new HashMap<>();
    private static final int COOLDOWN = 200;

    @Override
    public Map<UUID, Integer> getTraitors() {
        return traitors;
    }

    @Override
    public boolean isTraitor(Entity entity) {
        if(traitors.containsKey(entity.getUUID())) {
            int damageTime = traitors.get(entity.getUUID());
            return entity.tickCount - damageTime < COOLDOWN;
        }

        return false;
    }

    @Override
    public void addTraitor(UUID uuid, int time) {
        traitors.put(uuid, time);
    }
}
