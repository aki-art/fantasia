package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.Fantasia;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SpeleothemFeature extends Feature<SpeleothemFeatureConfig> {

    public SpeleothemFeature(Codec<SpeleothemFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, SpeleothemFeatureConfig config) {

        Fantasia.LOGGER.info("directionrestriction: " + config.direction.getName());

        for(int offset = 0; offset < config.length.sample(rand); offset ++)  {
            reader.setBlock(pos.relative(config.direction.getRandomDirection(rand), offset), config.state, 2); //temp
        }
        return true;
    }
}