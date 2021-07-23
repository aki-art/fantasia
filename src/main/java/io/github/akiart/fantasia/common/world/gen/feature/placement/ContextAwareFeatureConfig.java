package io.github.akiart.fantasia.common.world.gen.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;
import java.util.function.Supplier;

public class ContextAwareFeatureConfig implements IFeatureConfig {

    public static final Codec<ContextAwareFeatureConfig> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    ConfiguredFeature.CODEC
                            .fieldOf("feature")
                            .forGetter(config -> config.feature),
                    BlockState.CODEC.listOf()
                            .optionalFieldOf("place_on", null)
                            .forGetter(config -> config.placeOn),
                    BlockState.CODEC.listOf()
                            .optionalFieldOf("place_in", null)
                            .forGetter(config -> config.placeIn),
                    BlockState.CODEC.listOf()
                            .optionalFieldOf("place_under", null)
                            .forGetter(config -> config.placeUnder))
                    .apply(builder, ContextAwareFeatureConfig::new));

    public final Supplier<ConfiguredFeature<?, ?>> feature;
    public final List<BlockState> placeOn;
    public final List<BlockState> placeIn;
    public final List<BlockState> placeUnder;

    public ContextAwareFeatureConfig(Supplier<ConfiguredFeature<?, ?>> feature, List<BlockState> placeOn, List<BlockState> placeIn, List<BlockState> placeUnder) {
        this.feature = feature;
        this.placeOn = placeOn;
        this.placeIn = placeIn;
        this.placeUnder = placeUnder;
    }
}