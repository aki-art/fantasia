package io.github.akiart.fantasia.common.world.gen.feature.placement;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class ProjectDownToContextFeature extends Feature<ContextAwareFeatureConfig> {

    public ProjectDownToContextFeature(Codec<ContextAwareFeatureConfig> codec) {
        super(codec);
    }
    public boolean place(ISeedReader reader, ChunkGenerator chunkGenerator, Random random, BlockPos pos, ContextAwareFeatureConfig config) {
        BlockPos.Mutable checkPos = pos.mutable();
        for(int y = pos.getY(); y >= 0; y--) {
            checkPos.setY(y);
            boolean valid = isValidPlacement(reader, checkPos, config);
            if(valid) {
               // reader.setBlock(checkPos, Blocks.LANTERN.defaultBlockState(), Constants.BlockFlags.DEFAULT);
                return config.feature.get().place(reader, chunkGenerator, random, checkPos);
            }

        }

        return false;
    }

    private boolean isValidPlacement(ISeedReader reader, BlockPos pos, ContextAwareFeatureConfig config) {
        return (config.placeOn.isEmpty() || config.placeOn.contains(reader.getBlockState(pos.below()))) &&
                (config.placeIn.isEmpty() || config.placeIn.contains(reader.getBlockState(pos))) &&
                (config.placeUnder.isEmpty() || config.placeUnder.contains(reader.getBlockState(pos.above())));
    }
}
