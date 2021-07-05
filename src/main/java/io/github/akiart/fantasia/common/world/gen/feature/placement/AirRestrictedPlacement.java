package io.github.akiart.fantasia.common.world.gen.feature.placement;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public class AirRestrictedPlacement<T extends AirRestrictedPlacementConfig> extends Placement<T> {

    public AirRestrictedPlacement(Codec<T> codec) {
        super(codec);
    }

    public Stream<BlockPos> getPositions(WorldDecoratingHelper decoratingHelper, Random random, T config, BlockPos blockPos) {

        BlockPos.Mutable pos = blockPos.mutable();
        Set<BlockPos> positions = new HashSet<>();
        pos.setY(0);
        while(pos.getY() < 255)
            pos.above();

        if(decoratingHelper.getBlockState(pos).is(config.getBlock().getBlock())) {
            positions.add(pos);
        }
        return positions.stream();
    }
}