package io.github.akiart.fantasia.common.world.gen.surfaceBuilders;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome.FSurfaceBuilderConfig;
import io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome.CaveSurfaceBuilder;
import io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome.MountainSurfaceBuilder;
import io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome.TopSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FSurfaceBuilders {
	public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Fantasia.ID);
	
	//public static final RegistryObject<SurfaceBuilder<?>> FILL_ALL = SURFACE_BUILDERS.register("fill_all", () -> new CaveBiomeSurfaceBuilder(SurfaceBuilderConfig.CODEC));
	//public static final RegistryObject<SurfaceBuilder<?>> ALPINE_TUNDRA = SURFACE_BUILDERS.register("alpine_tundra", () -> new AlpineTundraSurfaceBuilder(HeightConstrictedSurfaceBuilderConfig.CODEC));
	public static final RegistryObject<SurfaceBuilder<?>> HEIGHT_CONSTRICTED = SURFACE_BUILDERS.register("height_constricted", () -> new HeightConstrictedSurfaceBuilder(HeightConstrictedSurfaceBuilderConfig.CODEC));
	public static final RegistryObject<SurfaceBuilder<?>> GRIMCAP_GROVE = SURFACE_BUILDERS.register("grimcap_grove", () -> new CaveSurfaceBuilder(FSurfaceBuilderConfig.CODEC));
	public static final RegistryObject<SurfaceBuilder<?>> TOP = SURFACE_BUILDERS.register("top", () -> new TopSurfaceBuilder(FSurfaceBuilderConfig.CODEC));
	public static final RegistryObject<SurfaceBuilder<?>> MOUNTAIN = SURFACE_BUILDERS.register("mountain", () -> new MountainSurfaceBuilder(FSurfaceBuilderConfig.CODEC));

}
