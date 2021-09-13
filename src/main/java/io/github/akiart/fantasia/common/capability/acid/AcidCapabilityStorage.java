package io.github.akiart.fantasia.common.capability.acid;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class AcidCapabilityStorage implements Capability.IStorage<IAcidCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IAcidCapability> capability, IAcidCapability instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putBoolean("isInAcid", instance.isInAcid());
        tag.putBoolean("wasInAcid", instance.wasInAcid());
        return tag;
    }

    @Override
    public void readNBT(Capability<IAcidCapability> capability, IAcidCapability instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT && instance instanceof AcidCapabilityHandler) {
            CompoundNBT tag = (CompoundNBT) nbt;
            ((AcidCapabilityHandler) instance).initState(
                    tag.getBoolean("isInAcid"),
                    tag.getBoolean("wasInAcid")
            );
        }
    }
}
