package io.github.akiart.fantasia.common.entity;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.rmi.registry.Registry;
import java.util.List;
import java.util.Optional;

public class FMemoryModuleTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, Fantasia.ID);

    public static final RegistryObject<MemoryModuleType<List<LivingEntity>>> NEAREST_SCARECROWS = MEMORY_MODULE_TYPES.register("nearest_scarecrows", () -> new MemoryModuleType<>(Optional.empty()));
}
