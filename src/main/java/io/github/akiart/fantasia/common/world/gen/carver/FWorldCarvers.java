package io.github.akiart.fantasia.common.world.gen.carver;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FWorldCarvers {
    public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, Fantasia.ID);

    public static final RegistryObject<WorldCarver<ProbabilityConfig>> TEMP_CAVE = WORLD_CARVERS.register("temp_cave", () -> new TempCaveWorldCarver(ProbabilityConfig.CODEC, 256));

}
