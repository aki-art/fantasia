package io.github.akiart.fantasia.common.particles;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Fantasia.ID);
	
	public static final RegistryObject<BasicParticleType> SNOW = PARTICLES.register("snow_particle", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> DRIPPING_ACID = PARTICLES.register("dripping_acid", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> FALLING_ACID = PARTICLES.register("falling_acid", () -> new BasicParticleType(true));
}
