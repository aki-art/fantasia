package io.github.akiart.fantasia.common.world.gen.feature.placement;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class ContextAwareFeature extends Feature<ContextAwareFeatureConfig> {
    public ContextAwareFeature(Codec<ContextAwareFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(ISeedReader reader, ChunkGenerator chunkGenerator, Random random, BlockPos pos, ContextAwareFeatureConfig config) {
        if (isValidPlacement(reader, pos, config)) {
            return config.feature.get().place(reader, chunkGenerator, random, pos);
        }

        return false;
    }

    private boolean isValidPlacement(ISeedReader reader, BlockPos pos, ContextAwareFeatureConfig config) {
        return config.placeOn.isEmpty() || config.placeOn.contains(reader.getBlockState(pos.below())) &&
                (config.placeIn.isEmpty() || config.placeIn.contains(reader.getBlockState(pos))) &&
                (config.placeUnder.isEmpty() || config.placeUnder.contains(reader.getBlockState(pos.above())));
    }
}