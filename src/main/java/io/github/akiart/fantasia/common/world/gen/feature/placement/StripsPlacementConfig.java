package io.github.akiart.fantasia.common.world.gen.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;

public class StripsPlacementConfig implements IPlacementConfig  {

    public final int noiseToCountRatio;
    public final double noiseFactor;
    public final double noiseCutoff;;
    public final double noiseOffset;

    public StripsPlacementConfig(int noiseToCountRatio, double noiseFactor, double noiseCutoff, double noiseOffset) {
        this.noiseCutoff = noiseCutoff;
        this.noiseFactor = noiseFactor;
        this.noiseOffset = noiseOffset;
        this.noiseToCountRatio = noiseToCountRatio;
    }

    public static final Codec<StripsPlacementConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder
                .group(
                        Codec.INT.fieldOf("noise_to_count_ratio").forGetter((config) -> { return config.noiseToCountRatio; }),
                        Codec.DOUBLE.fieldOf("noise_factor").forGetter((config) -> { return config.noiseFactor; }),
                        Codec.DOUBLE.fieldOf("noise_cutoff").forGetter((config) -> { return config.noiseCutoff; }),
                        Codec.DOUBLE.fieldOf("noise_offset").forGetter((config) -> { return config.noiseOffset; }))
                .apply(builder, StripsPlacementConfig::new);
    });
}
