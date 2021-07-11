package io.github.akiart.fantasia.common.world.gen.blockplacer;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FBlockPlacerTypes {
    public static final DeferredRegister<BlockPlacerType<?>> BLOCK_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, Fantasia.ID);
    public static final RegistryObject<BlockPlacerType<StalagmiteBlockPlacer>> STALAGMITE_BLOCK_PLACER = BLOCK_PLACER_TYPES.register("stalagmite_block_placer", () -> new BlockPlacerType<>(StalagmiteBlockPlacer.CODEC));
    public static final RegistryObject<BlockPlacerType<SnowBerryBlockPlacer>> SNOWBERRY_BLOCK_PLACER = BLOCK_PLACER_TYPES.register("snowberry_block_placer", () -> new BlockPlacerType<>(SnowBerryBlockPlacer.CODEC));
}
