package io.github.akiart.fantasia.common.capability;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.capability.acid.AcidCapabilityHandler;
import io.github.akiart.fantasia.common.capability.acid.AcidCapabilityStorage;
import io.github.akiart.fantasia.common.capability.acid.IAcidCapability;
import io.github.akiart.fantasia.common.capability.entity.DruidHoodPacifiable;
import io.github.akiart.fantasia.common.capability.entity.DruidHoodPacifierProvider;
import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;


@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Capabilities {

    @CapabilityInject(IAcidCapability.class)
    public static Capability<IAcidCapability> ACID = null;

    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(IAcidCapability.class, new AcidCapabilityStorage(), AcidCapabilityHandler::new);
        MinecraftForge.EVENT_BUS.register(Capabilities.class);
    }

    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() != null) {

            if(FTags.EntityTypes.DRUID_HOOD_AFFECTED.contains(e.getObject().getEntity().getType())) {
                e.addCapability(DruidHoodPacifiable.ID, new DruidHoodPacifierProvider());
            }

            e.addCapability(new ResourceLocation(Fantasia.ID, "capability_acid"), new ICapabilitySerializable<CompoundNBT>() {

                final IAcidCapability instance = ACID.getDefaultInstance();

                {
                    instance.setEntity(e.getObject());
                }

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction direction) {
                    return ACID.orEmpty(capability, LazyOptional.of(() -> instance));
                }

                @Override
                public CompoundNBT serializeNBT() {
                    return (CompoundNBT) ACID.getStorage().writeNBT(ACID, instance, null);
                }

                @Override
                public void deserializeNBT(CompoundNBT nbt) {
                    ACID.getStorage().readNBT(ACID, instance, null, nbt);
                }
            });
        }
    }
}
