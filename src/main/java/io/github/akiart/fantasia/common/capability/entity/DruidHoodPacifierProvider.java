package io.github.akiart.fantasia.common.capability.entity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class DruidHoodPacifierProvider implements ICapabilitySerializable<CompoundNBT> {

    private final IDruidHoodPacifible instance;

    public DruidHoodPacifierProvider() {
        instance = DruidHoodPacifiable.INSTANCE.getDefaultInstance();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction direction) {
        return DruidHoodPacifiable.INSTANCE.orEmpty(capability, LazyOptional.of(() -> instance));
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) DruidHoodPacifiable.INSTANCE.getStorage().writeNBT(DruidHoodPacifiable.INSTANCE, instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        DruidHoodPacifiable.INSTANCE.getStorage().readNBT(DruidHoodPacifiable.INSTANCE, instance, null, nbt);
    }


    public static void register() {
        CapabilityManager.INSTANCE.register(IDruidHoodPacifible.class, new DruidHoodPacifiableStorage(), DruidHoodPacifiable::new);
    }
}