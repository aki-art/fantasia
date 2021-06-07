package io.github.akiart.fantasia.common.world.gen.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.world.gen.util.FeatureUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.Set;

public class AlcoveFeature4 extends TerrainFeature<AlcoveFeatureConfig>{

    private static final int SIZE = 24;
    private static final int TERRAIN_CREEP = 0;
    private static final int MAX_VERTICAL_OFFSET = 5;

    public AlcoveFeature4(Codec<AlcoveFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, AlcoveFeatureConfig config) {

        pos = snapToChunkCenter(world, pos);
        // Ground into floor, or cancel if can't, this feature should really not hover mid-air
        int groundDistance = sampleGroundDistance(world, pos, MathHelper.floor(SIZE / 2f), MathHelper.floor(SIZE / 2f));
        if (Math.abs(groundDistance) > MAX_VERTICAL_OFFSET) return false;
        pos = pos.below(groundDistance);

        Set<Vector3i> blocks = Sets.newHashSet();
        Set<Vector3i> airBlocks = Sets.newHashSet();

        generateShape(world, rand, pos, blocks, airBlocks, config);
        setBlockStates(world, pos, blocks, airBlocks, config);

        setBlock(world, pos.west(SIZE).north(SIZE), Blocks.LIME_STAINED_GLASS.defaultBlockState());
        setBlock(world, pos.east(SIZE).north(SIZE), Blocks.LIME_STAINED_GLASS.defaultBlockState());
        setBlock(world, pos.west(SIZE).south(SIZE), Blocks.LIME_STAINED_GLASS.defaultBlockState());
        setBlock(world, pos.east(SIZE).south(SIZE), Blocks.LIME_STAINED_GLASS.defaultBlockState());
        return true;
    }

    private void setBlockStates(ISeedReader world, BlockPos startPos, Set<Vector3i> blocks, Set<Vector3i> airBlocks, AlcoveFeatureConfig config) {
        for (Vector3i block : blocks) {
            //setBlockState(world, startPos.add(block), Blocks.BIRCH_LOG.getDefaultState());
            BlockState state;
            if(airBlocks.contains(block.above())) {
                state = Blocks.MAGENTA_WOOL.defaultBlockState();
            }
            else  {
                state = Blocks.GRASS_BLOCK.defaultBlockState();
            }

            world.setBlock(startPos.offset(block), state, Constants.BlockFlags.DEFAULT_AND_RERENDER);
        }
    }

    private void generateShape(ISeedReader world, Random rand, BlockPos pos, Set<Vector3i> blocks, Set<Vector3i> airBlocks, AlcoveFeatureConfig config) {


        int heigthMult = config.getHeight().sample(rand);

        Direction facing = Direction.getRandom(rand);
        boolean isRotated = facing.getAxis() == Direction.Axis.X;

        double bias = isRotated ? 0.02D : 0.05D;
        double xMul = isRotated ? config.getxMul() : config.getzMul();
        double zMul = isRotated ? config.getzMul() : config.getxMul();

        int entranceOffsetX = !isRotated ? getEntranceOffset(facing) : 0;
        int entranceOffsetZ = isRotated ? getEntranceOffset(facing) : 0;

        int maxFloorHeigth = pos.getY() + TERRAIN_CREEP;

        for(int x = -SIZE; x < SIZE; x++) {
            for(int z = -SIZE; z < SIZE; z++) {
                int floorHeigth = Math.max(getWorldHeight(world, pos.offset(x, 0, z)), maxFloorHeigth);
                generateColumn(floorHeigth, x, z, heigthMult, blocks, airBlocks, xMul, zMul, bias, config.getShapeOffset(), entranceOffsetX, entranceOffsetZ);
            }
        }
    }

    private int getEntranceOffset(Direction facing) {
        return 4 * facing.getAxisDirection().getStep();
    }

    private void generateColumn(int floorHeigth, int x, int z, int heigthMult, Set<Vector3i> blocks, Set<Vector3i> airBlocks, double xMul, double zMul, double bias, double offset, int offsetX, int offsetZ) {

        int y = getHeigth(x, z, heigthMult, xMul, zMul, bias, offset);
        if(y < 0) return;

        int ceiling = getHeigth(x - offsetX, z - offsetZ, heigthMult, xMul, zMul, bias, offset);
        ceiling = Math.max(ceiling, 0);

        while (y-- >= ceiling) {
            blocks.add(new Vector3i(x, y, z));
        }

        while (y-- > 0) {
            airBlocks.add(new Vector3i(x, y, z));
        }

        blocks.add(new Vector3i(x, 0, z));

//        while (y-- >= 0) {
//            blocks.add(new Vector3i(x, y, z));
//        }
    }

    private int getHeigth(float x, float z, double heightMultiplier, double xMul, double zMul, double bias, double offset) {
        // visual graph: https://www.geogebra.org/3d/uhj3mrjf
        double fx = xMul * bias * x * x;
        double fz = zMul * (0.07D - bias) * z * z;
        return MathHelper.floor(Math.exp(-(fx + fz) * offset) * heightMultiplier);
    }
}
