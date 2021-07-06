package io.github.akiart.fantasia.common.world.gen.feature.placement;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraft.world.gen.placement.SimplePlacement;

// Places things in squiggly strips
public class StripsPlacement extends SimplePlacement<StripsPlacementConfig> {

    private SimplexNoiseGenerator secondaryNoise;

    public StripsPlacement(Codec<StripsPlacementConfig> codec) {
        super(codec);
        secondaryNoise = new SimplexNoiseGenerator(new SharedSeedRandom(0)); // figure seed out
    }

    @Override
    protected Stream<BlockPos> place(Random random, StripsPlacementConfig config, BlockPos pos) {

        double x = (double) pos.getX() / config.noiseFactor;
        double z = (double) pos.getZ() / config.noiseFactor;
        double noiseVal = Biome.BIOME_INFO_NOISE.getValue(x, z, false);

        noiseVal = -Math.abs(noiseVal * 16 - 0.5D) + 0.5D; // - secondaryNoise.getValue(x, z);
        int i = MathHelper.ceil((noiseVal + config.noiseOffset) * (double)config.noiseToCountRatio);

        return IntStream.range(0, i).mapToObj(count -> pos);
    }
}