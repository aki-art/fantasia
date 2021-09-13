package io.github.akiart.fantasia.client.particle;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.client.particle.particleTypes.AcidBubbleParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fantasia.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleFactoryRegister {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
        Minecraft mc = Minecraft.getInstance();
        mc.particleEngine.register(FParticleTypes.ACID_BUBBLE.get(), AcidBubbleParticle.Factory::new);
        //mc.particleEngine.register(FParticleTypes.DRIPPING_ACID.get(), AcidDripParticle.DrippingAcidFactory::new);
        //mc.particleEngine.register(FParticleTypes.FALLING_ACID.get(), AcidDripParticle.FallingAcidFactory::new);
    }
}
