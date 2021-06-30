package io.github.akiart.fantasia.common.world.gen.feature.placement;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.stream.Stream;

public class AirRestrictedPlacement<T extends AirRestrictedPlacementConfig> extends Placement<T> {

    public AirRestrictedPlacement(Codec<T> codec) {
        super(codec);
    }

    public Stream<BlockPos> getPositions(WorldDecoratingHelper decoratingHelper, Random random, T config, BlockPos blockPos) {
        BlockPos result = lowestMatching(decoratingHelper, config, blockPos);
        return result != null ? Stream.of(result) : Stream.of();
    }

    BlockPos.Mutable lowestMatching(WorldDecoratingHelper decoratingHelper, T config, BlockPos start) {
        BlockPos.Mutable pos = start.mutable();
        pos.setY(0);
        while(pos.getY() < 255)
            pos.above();

            if(decoratingHelper.getBlockState(pos).is(config.getBlock().getBlock())) {
                return pos;
        }

        return null;
    }
}