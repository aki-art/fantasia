package io.github.akiart.fantasia.common.world.gen.feature.placement;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FDecorators {	public static final DeferredRegister<Placement<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.DECORATORS, Fantasia.ID);

    public static final RegistryObject<Placement<StripsPlacementConfig>> STRIPS_PLACEMENT =
            DECORATORS.register("squiggly_placer", () -> new StripsPlacement(StripsPlacementConfig.CODEC));

    public static final RegistryObject<Placement<AirRestrictedPlacementConfig>> AIR_RESTRICTED_PLACEMENT =
            DECORATORS.register("air_restricted", () -> new AirRestrictedPlacement<>(AirRestrictedPlacementConfig.CODEC));
}
