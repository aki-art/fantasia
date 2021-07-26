package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class PillarFeatureConfig implements IFeatureConfig {
    public final BlockState state;
    public final FeatureSpread width;

    public static final Codec<PillarFeatureConfig> CODEC = RecordCodecBuilder.create((builder -> builder
            .group(
                    BlockState.CODEC
                            .fieldOf("state")
                            .forGetter(config -> config.state),
                    FeatureSpread.CODEC
                            .fieldOf("width")
                            .forGetter(config -> config.width)
            )
            .apply(builder, PillarFeatureConfig::new)));

    public PillarFeatureConfig(BlockState state, FeatureSpread width) {
        this.state = state;
        this.width = width;
    }
}
