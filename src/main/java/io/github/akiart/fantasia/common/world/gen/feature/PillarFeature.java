package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemBlock;
import io.github.akiart.fantasia.common.world.gen.util.VoxelUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.util.Constants;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

public class PillarFeature extends Feature<PillarFeatureConfig> {
    public PillarFeature(Codec<PillarFeatureConfig> codec) {
        super(codec);
    }

    // sample 8 blocks around the center
    private int getFloor(ISeedReader reader, BlockPos startPos, int width) {
        int[] samples = new int[8];

        samples[0] = projectDown(reader, startPos.offset(-width, 0, 0));
        samples[1] = projectDown(reader, startPos.offset(-width, 0, width));
        samples[2] = projectDown(reader, startPos.offset(-width, 0, -width));

        samples[3] = projectDown(reader, startPos.offset(0, 0, width));
        samples[4] = projectDown(reader, startPos.offset(0, 0, -width));

        samples[5] = projectDown(reader, startPos.offset(width, 0, 0));
        samples[6] = projectDown(reader, startPos.offset(width, 0, width));
        samples[7] = projectDown(reader, startPos.offset(width, 0, -width));

        return Arrays.stream(samples).min().getAsInt();
    }

    // find the nearest floor block
    private int projectDown(ISeedReader reader, BlockPos offset) {
        BlockPos.Mutable p = offset.mutable();
        for(int y = offset.getY(); y > 0; --y) {
            p.setY(y);
            if(!canReplace(reader.getBlockState(p))) {
                return y;
            }
        }

        return -1;
    }

    private int getCeiling(ISeedReader reader, BlockPos startPos, int width) {
        int[] samples = new int[8];

        samples[0] = projectUp(reader, startPos.offset(-width, 0, 0));
        samples[1] = projectUp(reader, startPos.offset(-width, 0, width));
        samples[2] = projectUp(reader, startPos.offset(-width, 0, -width));

        samples[3] = projectUp(reader, startPos.offset(0, 0, width));
        samples[4] = projectUp(reader, startPos.offset(0, 0, -width));

        samples[5] = projectUp(reader, startPos.offset(width, 0, 0));
        samples[6] = projectUp(reader, startPos.offset(width, 0, width));
        samples[7] = projectUp(reader, startPos.offset(width, 0, -width));

        return Arrays.stream(samples).max().getAsInt();
    }

    private int projectUp(ISeedReader reader, BlockPos offset) {
        BlockPos.Mutable p = offset.mutable();
        for(int y = offset.getY(); y < 255; y++) {
            p.setY(y);
            if(!canReplace(reader.getBlockState(p))) {
                return y;
            }
        }

        return -1;
    }

    private boolean canReplace(BlockState state) {
        return !state.getMaterial().blocksMotion() ||
                state.getBlock() instanceof SpeleothemBlock ||
                state.getMaterial() == Material.ICE;
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos startPos, PillarFeatureConfig config) {

        if(random.nextInt(5) != 0) return false;
        int width = config.width.sample(random);

        reader.setBlock(startPos, Blocks.GLOWSTONE.defaultBlockState(), 2);

        int ceiling = getCeiling(reader, startPos, width);
        int floor = getFloor(reader, startPos, width);
        int length = ceiling - floor;

        startPos = new BlockPos(startPos.getX(), ceiling, startPos.getZ());

        Set<Vector3i> circle = VoxelUtil.getFilledCircle(width);

        for (Vector3i p : circle) {
            BlockPos.Mutable pos = new BlockPos(p).offset(startPos).mutable();
            int segmentLength = getSegmentLength(p.getX(), p.getZ(), width, length);
            segmentLength = (int)Math.min(segmentLength, length / 2f); // make it not double place blocks of the overlapping halves

            for (int y = startPos.getY(); y >= startPos.getY() - segmentLength; --y) {
                pos.setY(y);
                reader.setBlock(pos, config.state, Constants.BlockFlags.BLOCK_UPDATE);
            }

            for (int y = floor; y <= floor + segmentLength; ++y) {
                pos.setY(y);
                reader.setBlock(pos, config.state, Constants.BlockFlags.BLOCK_UPDATE);
            }
        }

        return true;
    }

    private int getSegmentLength(float x, float z, float width, float height) {
        width *= 2f;

        double y = Math.exp(-(((x * x) / width + ((z * z) / width))));
        return MathHelper.floor(y * height);
    }
}
