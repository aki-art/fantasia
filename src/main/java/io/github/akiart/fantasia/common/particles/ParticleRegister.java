package io.github.akiart.fantasia.common.particles;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.particles.particleTypes.SnowParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Fantasia.ID, bus = Bus.MOD)
public class ParticleRegister {
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerParticles(ParticleFactoryRegisterEvent event) {
		Fantasia.LOGGER.info("registering the particle");
		Minecraft mc = Minecraft.getInstance();
		mc.particleEngine.register(FParticleTypes.SNOW.get(), SnowParticle.Factory::new);
	}
}
