package io.github.akiart.fantasia.common.entity.ai.brain;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.rmi.registry.Registry;
import java.util.List;
import java.util.Optional;

public class FMemoryModuleTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, Fantasia.ID);

    public static final RegistryObject<MemoryModuleType<List<LivingEntity>>> NEAREST_SCARECROWS = MEMORY_MODULE_TYPES.register("nearest_scarecrows", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<Long>> CANT_REACH_FLIGHT_TARGET_SINCE = MEMORY_MODULE_TYPES.register("cant_reach_flight_target_since", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<WalkTarget>> FLIGHT_TARGET = MEMORY_MODULE_TYPES.register("fly_target", () -> new MemoryModuleType<>(Optional.empty()));
}
