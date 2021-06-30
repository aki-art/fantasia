package io.github.akiart.fantasia.common.world.gen.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class AirRestrictedPlacementConfig implements IPlacementConfig {

    public BlockState getBlock() {
        return block;
    }

    private final BlockState block;

    public AirRestrictedPlacementConfig(BlockState block) {
        this.block = block;
    }

    public static final Codec<AirRestrictedPlacementConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder
                .group(
                        BlockState.CODEC.fieldOf("air_block").forGetter((config) -> { return config.block; }))
                .apply(builder, AirRestrictedPlacementConfig::new);
    });
}
