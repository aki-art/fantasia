package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.IcicleBlock;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemBlock;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemStage;
import io.github.akiart.fantasia.common.world.gen.util.FeatureUtil;
import io.github.akiart.fantasia.common.world.gen.util.Vector2i;
import io.github.akiart.fantasia.common.world.gen.util.VoxelUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.loot.LootTables;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructurePiece;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AlcoveFeature extends Feature<AlcoveFeatureConfig> {

    private static final int MAX_SPREAD = 23;
    private static final int MAX_HEIGTH = 36;
    private static final int TERRAIN_CREEP = 2;
    private static final int MAX_VERTICAL_OFFSET = 5;

    public AlcoveFeature(Codec<AlcoveFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, AlcoveFeatureConfig config) {
        Timestamp start = new Timestamp(System.currentTimeMillis());

        // Snap to 3x3 chunk corner, otherwise parts will be chopped off. probably do this with feature placement instead
        ChunkPos chunkPos = world.getChunk(pos).getPos();
        BlockPos startPos = new BlockPos(chunkPos.getMinBlockX() - 16, pos.getY(), chunkPos.getMinBlockZ() - 16);

        Direction facing = Direction.getRandom(rand);

        // Ground into floor, or cancel if can't, this feature should really not hover mid-air
        int groundDistance = FeatureUtil.sampleGroundDistance(world, startPos, MAX_SPREAD, MAX_SPREAD);
        if (Math.abs(groundDistance) > MAX_VERTICAL_OFFSET) return false;
        startPos = startPos.below(groundDistance);

        HashSet<Vector3i> blocks = new HashSet<>(); // all blocks contained
        HashMap<Vector2i, FloorMarker> floorPlan = new HashMap<>(); // a 2d top down map of the structure
        HashSet<Vector3i> floorBlocks = new HashSet<>(); // the actual blocks of the flooring
        HashSet<Vector3i> insideAir = new HashSet<>();

        generateCeiling(rand, facing, config, blocks, floorPlan, insideAir);
        generateFloor(world, startPos, blocks, floorPlan, floorBlocks, insideAir);
        VoxelUtil.smooth(blocks, Vector3i.ZERO, getExtents(), 3);
        //clearArea(world, startPos, floorPlan);
        setStates(world, startPos, blocks, floorBlocks, insideAir, config);

        if (config.isFrozen()) {
            freezeUp(world, rand, startPos, blocks);
        }

        decorateFloor(world, rand, startPos, blocks, floorBlocks, insideAir);

        Fantasia.LOGGER.info("Alcove generation finished in {} ms", new Timestamp(System.currentTimeMillis()).getTime() - start.getTime());
        return true;
    }

    private void decorateFloor(ISeedReader world, Random rand, BlockPos startPos, HashSet<Vector3i> blocks, HashSet<Vector3i> floorBlocks, HashSet<Vector3i> insideAir) {
        boolean placedChest = false;
        for (Vector3i block : floorBlocks) {
            if (blocks.contains(block.above())) continue;

            BlockPos pos = startPos.offset(block).above();

            if (rand.nextFloat() < 0.03) {
                placeIcicle(world, rand, pos, Direction.UP);
            }

            if (!insideAir.contains(block)) continue;

            if (rand.nextFloat() < 0.1f) {
                placeIcicle(world, rand, pos, Direction.UP);
            }

            if (rand.nextFloat() < 0.05f) {
                placeBones(world, rand, pos);
            }

            if (!placedChest && rand.nextFloat() < 0.2f) {
                world.setBlock(pos, StructurePiece.reorient(world, pos, Blocks.CHEST.defaultBlockState()), 2);
                LockableLootTileEntity.setLootTable(world, rand, pos, LootTables.SIMPLE_DUNGEON);
                placedChest = true;
            }
        }
    }

    private boolean isInside(HashMap<Vector2i, FloorMarker> floorPlan, Vector3i block) {
        return floorPlan.getOrDefault(new Vector2i(block), FloorMarker.INVALID) == FloorMarker.INSIDE;
    }

    private void placeBones(ISeedReader world, Random rand, BlockPos pos) {
        // TODO: once bone decorations are added put them here
        setBlock(world, pos, Blocks.SKELETON_SKULL.defaultBlockState().setValue(SkullBlock.ROTATION, rand.nextInt(15)));
    }

    private void freezeUp(ISeedReader world, Random rand, BlockPos startPos, HashSet<Vector3i> blocks) {
        for (Vector3i block : blocks) {
            BlockPos pos = startPos.offset(block);
            if (block.getY() > 2 && !blocks.contains(block.below()) && rand.nextFloat() < 0.1f) {
                placeIcicle(world, rand, pos, Direction.DOWN);
            } else if (isSurfaceBlock(block, blocks) && world.isEmptyBlock(pos.above())) {
                setBlock(world, pos.above(), Blocks.SNOW.defaultBlockState());
            }
            // place some ice and snow in the walls, with a higher chance near bottom
            if (rand.nextFloat() < (1f / (block.getY() + 1)) * 0.25f) {
                setBlock(world, pos, rand.nextFloat() > 0.33f ? Blocks.SNOW_BLOCK.defaultBlockState() : Blocks.ICE.defaultBlockState());
            }
        }
    }

    private void placeIcicle(ISeedReader world, Random rand, BlockPos pos, Direction direction) {
        int length = rand.nextInt(Math.min(3, FeatureUtil.getWorldHeight(world, pos) - 1)) + 1;
        for (int y = 1; y <= length; y++) {
            SpeleothemStage icicleStage = FBlocks.ICICLE.get().getStageForRelativePosition(rand, y - 1, length - y, length);
            AttachFace facing = direction == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING;
            setBlock(world, pos.relative(direction, y), FBlocks.ICICLE.get().defaultBlockState().setValue(SpeleothemBlock.STAGE, icicleStage).setValue(IcicleBlock.FACE, facing));
        }
    }

    private void setStates(ISeedReader world, BlockPos startPos, HashSet<Vector3i> blocks, HashSet<Vector3i> floor, HashSet<Vector3i> inside, AlcoveFeatureConfig config) {
        for (Vector3i block : inside) {
            setBlock(world, startPos.offset(block), Blocks.CAVE_AIR.defaultBlockState());
        }

        for (Vector3i block : blocks) {
            BlockState state;
            if (floor.contains(block)) {
                state = Blocks.PURPLE_WOOL.defaultBlockState();//config.getFlooring();
            } else if (isSurfaceBlock(block, blocks)) {
                state = FBlocks.FROZEN_DIRT.get().defaultBlockState().setValue(SnowyDirtBlock.SNOWY, true);
            } else state = config.getState();

            setBlock(world, startPos.offset(block), state);
        }
    }

    private boolean isSurfaceBlock(Vector3i pos, HashSet<Vector3i> blocks) {
        return blocks.contains(pos) && !blocks.contains(pos.above());
    }

    private void clearArea(ISeedReader world, BlockPos startPos, HashMap<Vector2i, Integer> floorPlan) {
        for (Vector2i p : floorPlan.keySet()) {
            for (int y = MAX_HEIGTH; y >= TERRAIN_CREEP; y--) {
                BlockPos pos = startPos.offset(p.getX(), y, p.getZ());
                clearBlockState(world, pos);
            }
        }
    }

    private void clearBlockState(ISeedReader world, BlockPos pos) {
        BlockState current = world.getBlockState(pos);
        if (!BlockTags.BASE_STONE_OVERWORLD.contains(current.getBlock()) /* TODO: && FBlockTags.BASE_STONE_FANTASIA.contains(current.getBlock())*/) {
            setBlock(world, pos, Blocks.AIR.defaultBlockState());
        }
    }

    private void generateCeiling(Random rand, Direction facing, AlcoveFeatureConfig config, HashSet<Vector3i> blocks, HashMap<Vector2i, FloorMarker> floorPlan, HashSet<Vector3i> insideAir) {
        int heigthMult = config.getHeight().sample(rand);

        for (int x = 0; x < MAX_SPREAD * 2; x++) {
            for (int z = 0; z < MAX_SPREAD * 2; z++) {
                buildColumn(x, z, heigthMult, facing, config, blocks, floorPlan, insideAir);
            }
        }
    }

    private void generateFloor(ISeedReader world, BlockPos pos, HashSet<Vector3i> blocks, HashMap<Vector2i, FloorMarker> floorPlan, HashSet<Vector3i> floorBlocks, HashSet<Vector3i> insideAir) {
        for (Vector2i p : floorPlan.keySet()) {
            int worldHeigth = FeatureUtil.getWorldHeight(world, pos.offset(p.toVector3i()));
            int floorHeigth = pos.getY();
            int y = MathHelper.clamp(worldHeigth - floorHeigth, 0, 3);

            do {
                Vector3i floor = p.toVector3i().above(y);
                blocks.add(floor);
                floorBlocks.add(floor);
                insideAir.remove(floor);
            } while (y-- >= 0);
        }
    }

    private void buildColumn(int x, int z, int heigthMult, Direction facing, AlcoveFeatureConfig config, HashSet<Vector3i> blocks, HashMap<Vector2i, FloorMarker> floorPlan, HashSet<Vector3i> insideAir) {
        boolean isRotated = facing.getAxis() == Direction.Axis.X;
        int offsetX = x - MAX_SPREAD;
        int offsetZ = z - MAX_SPREAD;

        int y = getHeigth(offsetX, offsetZ, isRotated, heigthMult, config);
        if (y <= 0) return;


        int entranceOffsetX = !isRotated ? 4 * facing.getAxisDirection().getStep() : 0;
        int entranceOffsetZ = isRotated ? 4 * facing.getAxisDirection().getStep() : 0;

        int ceiling = getHeigth(offsetX - entranceOffsetX, offsetZ - entranceOffsetZ, isRotated, heigthMult * 0.8f, config);
        ceiling = Math.max(ceiling, 0);

        floorPlan.put(new Vector2i(x, z), ceiling > 0 ? FloorMarker.INSIDE : FloorMarker.OUTSIDE);

        while (y-- >= ceiling) {
            blocks.add(new Vector3i(x, y, z));
        }

        while (y-- >= 0) {
            insideAir.add(new Vector3i(x, y, z));
        }
    }

    private Vector3i getExtents() {
        return new Vector3i(MAX_SPREAD * 2, MAX_HEIGTH, MAX_SPREAD * 2);
    }

    private int getHeigth(float x, float z, boolean rotated, double heightMultiplier, AlcoveFeatureConfig config) {

        // lazy rotation
        double bias = rotated ? 0.02D : 0.05D;
        double xMul = rotated ? config.getxMul() : config.getzMul();
        double zMul = rotated ? config.getzMul() : config.getxMul();

        // visual graph: https://www.geogebra.org/3d/uhj3mrjf
        double fx = xMul * bias * x * x;
        double fz = zMul * (0.07D - bias) * z * z;
        return MathHelper.floor(Math.exp(-(fx + fz) * config.getShapeOffset()) * heightMultiplier);
    }

    private enum FloorMarker {
        INVALID,
        OUTSIDE,
        INSIDE,
        WALL;
    }
}
