package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.util.DirectionRestriction;
import io.github.akiart.fantasia.common.world.gen.blockplacer.StalagmiteBlockPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class SpeleothemFeatureConfig  implements IFeatureConfig {
    public final BlockState state;
    public final BlockPlacer blockPlacer;
    public final FeatureSpread length;
    public final DirectionRestriction direction;

    public static final Codec<SpeleothemFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> builder
            .group(
                    BlockState.CODEC
                            .fieldOf("state")
                            .forGetter(config -> config.state),
                    FeatureSpread.CODEC
                            .fieldOf("length")
                            .forGetter(config -> config.length),
                    DirectionRestriction.CODEC
                            .optionalFieldOf("direction_restriction", DirectionRestriction.NONE)
                            .forGetter(config -> config.direction),
                    BlockPlacer.CODEC.fieldOf("ceiling_block_placer")
                            .orElse(new StalagmiteBlockPlacer(1, 2))
                            .forGetter((config) -> config.blockPlacer))
            .apply(builder, SpeleothemFeatureConfig::new));

    public SpeleothemFeatureConfig(BlockState state, FeatureSpread length, DirectionRestriction direction, BlockPlacer blockPlacer) {
        this.state = state;
        this.blockPlacer = blockPlacer;
        this.length = length;
        this.direction = direction;
    }
}
