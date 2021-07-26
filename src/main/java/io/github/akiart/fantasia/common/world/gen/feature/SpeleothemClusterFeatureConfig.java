package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.fantasia.common.util.DirectionRestriction;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class SpeleothemClusterFeatureConfig implements IFeatureConfig {

    public final BlockState state;
    public final DirectionRestriction restriction;
    public final FeatureSpread length;
    public final FeatureSpread spread;
    public final int attempts;
    public final boolean placeOppositeDripCluster;

    public static final Codec<SpeleothemClusterFeatureConfig> CODEC = RecordCodecBuilder.create(builder -> builder
            .group(
                    BlockState.CODEC
                            .fieldOf("state")
                            .forGetter(config -> config.state),
                    Codec.STRING
                            .optionalFieldOf("restriction", "none")
                            .forGetter(config -> config.restriction.getName()),
                    FeatureSpread.CODEC
                            .fieldOf("length")
                            .forGetter((config) -> config.length),
                    FeatureSpread.CODEC
                            .fieldOf("spread")
                            .forGetter((config) -> config.spread),
                    Codec.INT
                        .fieldOf("attempts")
                        .forGetter(config -> config.attempts),
                    Codec.BOOL
                        .fieldOf("drip_counterpart")
                        .forGetter(config -> config.placeOppositeDripCluster)

            )
            .apply(builder, SpeleothemClusterFeatureConfig::new));

    public SpeleothemClusterFeatureConfig(BlockState state, String directionRestrictionName, FeatureSpread length, FeatureSpread spread, int attempts, boolean placeOppositeDripCluster) {
        this.state = state;
        this.length = length;
        this.spread = spread;
        this.attempts = attempts;
        this.restriction = DirectionRestriction.byName(directionRestrictionName);
        this.placeOppositeDripCluster = placeOppositeDripCluster;
    }
}
