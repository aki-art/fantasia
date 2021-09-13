package io.github.akiart.fantasia.common.capability.entity;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

public class DruidHoodPacifiableStorage  implements Capability.IStorage<IDruidHoodPacifible> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IDruidHoodPacifible> capability, IDruidHoodPacifible instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();

        ListNBT list = new ListNBT();
        Map<UUID, Integer> traitors = instance.getTraitors();

        if(!traitors.isEmpty()) {
            for (UUID uuid : traitors.keySet()) {
                CompoundNBT entry = new CompoundNBT();
                entry.putUUID("UUID", uuid);
                entry.putInt("Timer", traitors.get(uuid));
                list.add(entry);
            }
            tag.put("Traitors", list);
            Fantasia.LOGGER.info("TYPE " + list.getType());
        }

        return tag;
    }

    @Override
    public void readNBT(Capability<IDruidHoodPacifible> capability, IDruidHoodPacifible instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT && instance instanceof DruidHoodPacifiable) {
            CompoundNBT tag = (CompoundNBT) nbt;

            ListNBT list = tag.getList("Traitors", 9);
            for(INBT inbt : list) {
                CompoundNBT cnbt = (CompoundNBT)inbt;
                if(cnbt.contains("UUID") && cnbt.contains("Timer")) {
                    instance.addTraitor(
                            NBTUtil.loadUUID(cnbt.get("UUID")),
                            cnbt.getInt("Timer")
                    );
                }
            }
        }
    }
}