package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemBlock;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemStage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class SpeleothemClusterFeature extends Feature<SpeleothemClusterFeatureConfig> {

    private static final int DRIP_MIN_DISTANCE = 4;
    private static final int DRIP_MAX_DISTANCE = 64;

    public SpeleothemClusterFeature(Codec<SpeleothemClusterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos pos, SpeleothemClusterFeatureConfig config) {

        Direction direction = config.restriction.getRandomDirection(random);
        int spread = config.spread.sample(random);
        boolean success;
        boolean placeDripCluster = false;
        BlockPos.Mutable dripOrigin = pos.mutable();

        if (direction == Direction.DOWN) {
            for (int d = dripOrigin.getY() - DRIP_MIN_DISTANCE; d > 0; --d) {
                dripOrigin.setY(d);
                if (reader.getBlockState(dripOrigin).getMaterial() == Material.STONE) {
                    placeDripCluster = true;
                    break;
                }
            }
        }

        success = placeCluster(reader, random, pos, config, direction, spread);

        if (success && placeDripCluster) {
            placeCluster(reader, random, dripOrigin, config, direction.getOpposite(), spread);
        }

        return success;
    }

    private boolean placeCluster(ISeedReader reader, Random random, BlockPos pos, SpeleothemClusterFeatureConfig config, Direction direction, int spread) {
        boolean success = false;
        Iterable<BlockPos> randomPositions = BlockPos.randomBetweenClosed(
                random,
                config.attempts,
                pos.getX() - spread,
                pos.getY() - spread,
                pos.getZ() - spread,
                pos.getX() + spread,
                pos.getY() + spread,
                pos.getZ() + spread);

        for (BlockPos randomPos : randomPositions) {
            float dist = Math.min(randomPos.distManhattan(pos), spread);
            float distanceFromOrigin = dist / (float) spread;
            if (tryPlaceSpeleothem(reader, random, randomPos, direction, config, distanceFromOrigin)) {
                success = true;
            }
        }
        return success;
    }

    private boolean tryPlaceSpeleothem(ISeedReader reader, Random random, BlockPos startPos, Direction direction, SpeleothemClusterFeatureConfig config, float distanceFromOrigin) {
        BlockPos.Mutable pos = startPos.mutable();
        int length = config.length.sample(random);
        //length -= distanceFromOrigin * 2;

        if(length < 1) return false;

        if (!canGrowFrom(reader, direction, pos)) {
            return false;
        }

        // check if it can place
        for (int i = 0; i < length; i++) {
            if (!canReplace(reader, pos.relative(direction, i))) return false;
        }

        Direction dir;
        AttachFace face;

        if (direction.getAxis() == Direction.Axis.Y) {
            face = direction == Direction.DOWN ? AttachFace.CEILING : AttachFace.FLOOR;
            dir = Direction.NORTH;
        } else {
            face = AttachFace.WALL;
            dir = direction.getOpposite();
        }

        placeSpeleothem(reader, random, direction, dir, face, config, pos, length);
        return true;
    }

    private boolean canGrowFrom(ISeedReader reader, Direction direction, BlockPos.Mutable pos) {
        BlockPos basePos = pos.relative(direction.getOpposite());
        BlockState state = reader.getBlockState(basePos);
        return state.isFaceSturdy(reader, pos, direction.getOpposite());
    }

    private void placeSpeleothem(ISeedReader reader, Random random, Direction direction, Direction horizontalDirection, AttachFace face, SpeleothemClusterFeatureConfig config, BlockPos.Mutable pos, int length) {
        for (int i = 1; i <= length; i++) {
            Block block = config.state.getBlock();
            if (block instanceof SpeleothemBlock) {
                SpeleothemStage stage = ((SpeleothemBlock) block).getStageForRelativePosition(random, i - 1, length - i, length);
                reader.setBlock(
                        pos.relative(direction, i - 1),
                        config.state.setValue(SpeleothemBlock.STAGE, stage).setValue(SpeleothemBlock.FACE, face).setValue(SpeleothemBlock.FACING, horizontalDirection),
                        Constants.BlockFlags.DEFAULT);
            } else {
                reader.setBlock(pos.relative(direction, i - 1), config.state, Constants.BlockFlags.NO_RERENDER);
            }
        }
    }

    private boolean canReplace(ISeedReader reader, BlockPos pos) {
        return reader.isEmptyBlock(pos);
    }
}
