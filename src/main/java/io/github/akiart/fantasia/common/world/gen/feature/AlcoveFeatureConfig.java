package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.fantasia.common.world.gen.blockplacer.StalagmiteBlockPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class AlcoveFeatureConfig implements IFeatureConfig {
    public final BlockStateProvider ceilingDecorator;

    public BlockStateProvider getCeilingDecorator() {
        return ceilingDecorator;
    }

    public BlockStateProvider getFloorDecorator() {
        return floorDecorator;
    }

    public final BlockStateProvider floorDecorator;
    private final BlockState state;
    private final BlockState top;
    private final BlockState flooring;
    public final BlockPlacer ceilingBlockPlacer;
    private final FeatureSpread height;
    private final int verticalOffset;
    private final float shapeOffset;
    private final float xMul;
    private final float zMul;
    private final boolean freeze;

    public BlockState getState() {
        return state;
    }

    public BlockState getTop() {
        return top;
    }

    public BlockState getFlooring() {
        return flooring;
    }

    public FeatureSpread getHeight() {
        return height;
    }

    public int getVerticalOffset() {
        return verticalOffset;
    }

    public float getShapeOffset() {
        return shapeOffset;
    }

    public float getxMul() {
        return xMul;
    }

    public float getzMul() {
        return zMul;
    }

    public boolean isFrozen() {
        return freeze;
    }

    public BlockPlacer getCeilingBlockPlacer() {
        return ceilingBlockPlacer;
    }

    public AlcoveFeatureConfig(BlockState state, BlockState top, BlockState flooring, FeatureSpread height, int verticalOffset, float shapeOffset, float xMul, float zMul, boolean freeze, BlockStateProvider ceilingDecorator, BlockPlacer ceilingBlockPlacer,  BlockStateProvider floorDecorator) {
        this.state = state;
        this.top = top;
        this.flooring = flooring;
        this.height = height;
        this.verticalOffset = verticalOffset;
        this.freeze = freeze;
        this.shapeOffset = shapeOffset;
        this.xMul = xMul;
        this.zMul = zMul;
        this.ceilingDecorator = ceilingDecorator;
        this.ceilingBlockPlacer = ceilingBlockPlacer;
        this.floorDecorator = floorDecorator;
    }

    public static final Codec<AlcoveFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder
                .group(
                        BlockState.CODEC.fieldOf("base_state").forGetter((config) -> {
                            return config.state;
                        }),
                        BlockState.CODEC.fieldOf("top_state").forGetter((config) -> {
                            return config.top;
                        }),
                        BlockState.CODEC.fieldOf("floor_state").forGetter((config) -> {
                            return config.flooring;
                        }),
                        FeatureSpread.CODEC.fieldOf("height").forGetter((config) -> {
                            return config.height;
                        }),
                        Codec.INT.fieldOf("verticalOffset").forGetter((config) -> {
                            return config.verticalOffset;
                        }),
                        Codec.FLOAT.fieldOf("shapeOffset").orElse(0.23f).forGetter((config) -> {
                            return config.shapeOffset;
                        }),
                        Codec.FLOAT.fieldOf("xMul").forGetter((config) -> {
                            return config.xMul;
                        }),
                        Codec.FLOAT.fieldOf("zMul").forGetter((config) -> {
                            return config.zMul;
                        }),
                        Codec.BOOL.fieldOf("snowy").forGetter((config) -> {
                            return config.freeze;
                        }),
                        BlockStateProvider.CODEC.fieldOf("ceiling_decorator").forGetter((config) -> {
                            return config.ceilingDecorator;
                        }),
                        BlockPlacer.CODEC.fieldOf("ceiling_block_placer").orElse(new StalagmiteBlockPlacer(1, 2)).forGetter((config) -> {
                            return config.ceilingBlockPlacer;
                        }),
                        BlockStateProvider.CODEC.fieldOf("floor_decorator").orElse(null).forGetter((config) -> {
                            return config.floorDecorator;
                        }))
                .apply(builder, AlcoveFeatureConfig::new);
    });

}
