package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class IcyCavernSurfaceBuilder extends CaveSurfaceBuilder {
    public IcyCavernSurfaceBuilder(Codec<FSurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    protected BlockState getTop(BlockState configTop, double noise) {
        return noise > 0.3f ? configTop : Blocks.SNOW_BLOCK.defaultBlockState();
    }

    @Override
    protected BlockState getBase(BlockState configTop, double noise) {
        return noise > 0.5f ? configTop : Blocks.BLUE_ICE.defaultBlockState();
    }
}
