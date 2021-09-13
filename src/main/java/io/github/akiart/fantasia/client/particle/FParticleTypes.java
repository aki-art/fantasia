package io.github.akiart.fantasia.client.particle;
import io.github.akiart.fantasia.Fantasia;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Fantasia.ID);

    public static final RegistryObject<BasicParticleType> ACID_BUBBLE = PARTICLES.register("acid_bubble", () -> new BasicParticleType(true));
}
