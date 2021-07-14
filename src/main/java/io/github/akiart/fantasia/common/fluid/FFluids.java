package io.github.akiart.fantasia.common.fluid;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Fantasia.ID);

    public static final RegistryObject<FlowingFluid> ACID_SOURCE = FLUIDS.register("acid", () -> new AcidFluid.Source(AcidFluid.getProperties()));
    public static final RegistryObject<FlowingFluid> ACID_FLOWING = FLUIDS.register("acid_flowing", () -> new AcidFluid.Flowing(AcidFluid.getProperties()));
}
