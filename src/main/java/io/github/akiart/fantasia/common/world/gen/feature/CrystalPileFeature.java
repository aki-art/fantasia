package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Constants.BlockFlags;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// TODO: needs a massive rewrite
public class CrystalPileFeature extends Feature<CrystalPileFeatureConfig> {

    public CrystalPileFeature(Codec<CrystalPileFeatureConfig> codec) {
        super(codec);
    }

    private Direction primaryFacing;
    private List<BlockPos> blocks;
    private List<BlockPos> topBlocks;
    private int widestSpread = 0;

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, CrystalPileFeatureConfig config) {

        blocks = new ArrayList<BlockPos>();
        primaryFacing = Direction.UP; //getGrowthDirection(reader, pos);

        generateBase(reader, generator, rand, pos, config);

        if (blocks.isEmpty()) {
            Fantasia.LOGGER.info("Blocks is empty.");
            return false;
        }

        updateTopBlocks(reader);

        generateBlockPillars(reader, rand, widestSpread, pos, config);
        updateTopBlocks(reader);

        growThinCrystals(reader, rand, config);
        sprinkleTinyCrystalsNearby(reader, pos, rand, config);

        return true;

    }

    private void updateTopBlocks(ISeedReader reader) {
        topBlocks = blocks.stream()
                .filter(b -> canOccupy(reader, b.relative(primaryFacing)))
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean generateBase(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, CrystalPileFeatureConfig config) {

        int x = config.getRadius().sample(rand);
        int z = config.getRadius().sample(rand);
        int y = config.getRadius().sample(rand);

        widestSpread = Math.max(z, Math.max(x, y));

        for (BlockPos blockPos : BlockPos.withinManhattan(pos, x, y, z)) {
            if (blockPos.closerThan(pos, rand.nextFloat() * widestSpread)) {
                replaceAndCache(reader, config, blockPos);
            }
        }

        return true;
    }

    private void replaceAndCache(ISeedReader reader, CrystalPileFeatureConfig config, BlockPos blockPos) {
        if (this.tryReplace(reader, blockPos, config)) {
            blocks.add(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ())); // otherwise only the reference gets passed and that's worthless
        }
    }

    private Direction getGrowthDirection(ISeedReader reader, BlockPos origin) {

        int max = 0;
        Direction result = Direction.WEST;

        for (Direction direction : Direction.values()) {
            int openFaceCount = 0;

            int openSpaceCount = (int) get3x3SampleBox(origin).stream().filter(p -> canOccupy(reader, p.relative(direction))).count();
            if (openSpaceCount > max) {
                max = openSpaceCount;
                result = direction;
            }
//			for(BlockPos pos : get3x3SampleBox(origin)) {
//				if(canOccupy(reader, pos.offset(direction))) {
//					if(++openFaceCount > max) {
//						max = openFaceCount;
//						result = direction;
//						continue;
//					}
//				}
//			}
        }

        Fantasia.LOGGER.info(result.getOpposite().getName());
        return result.getOpposite();
    }

    private List<BlockPos> get3x3SampleBox(BlockPos origin) {
        return BlockPos.betweenClosedStream(origin.offset(-1, -1, -1), origin.offset(1, 1, 1)).collect(Collectors.toList());
    }

    private Direction getGrowthDirection2(BlockPos origin, ISeedReader world) {
        for (Direction direction : Direction.values()) {
            if (isClearInDirection(direction, origin, world)) {
                return direction;
            }
        }

        Fantasia.LOGGER.info("No direction found");
        return null;
    }

    private boolean isClearInDirection(Direction direction, BlockPos origin, ISeedReader world) {
        BlockPos firstPos = origin.relative(direction);
        return true;

    }

    private void generateBlockPillars(ISeedReader reader, Random rand, int radius, BlockPos origin, CrystalPileFeatureConfig config) {

        int maxPillarHeight = config.getMaxPillarHeight().sample(rand);

        for (BlockPos pos : topBlocks) {
            placeBlockPillar(reader, rand, radius, origin, config, maxPillarHeight, pos);
        }
    }

    private void placeBlockPillar(ISeedReader reader, Random rand, int featureRadius, BlockPos featureOrigin, CrystalPileFeatureConfig config, int maxHeight, BlockPos startingPos) {
        int distanceFromOrigin = startingPos.distManhattan(featureOrigin);
        float heightModifier = 1 - (float) distanceFromOrigin / (float) featureRadius;

        float pillarHeight = heightModifier * rand.nextFloat() * maxHeight / 2f;
        pillarHeight += (maxHeight / 2f) * heightModifier;

        BlockPos currentPos = startingPos;

        for (int y = 1; y < pillarHeight; y++) {
            currentPos = startingPos.relative(primaryFacing, y);

            if (tryGrowBlock(reader, currentPos, config)) {
                blocks.add(currentPos);
            } else break;
        }
    }

    private void growThinCrystals(ISeedReader reader, Random rand, CrystalPileFeatureConfig config) {
        for (BlockPos pos : topBlocks) {
            if (rand.nextFloat() < config.getSmallCrystalChance()) {
                int length = config.getSmallCrystalLength().sample(rand);
                placeThinCrystalColumn(reader, config, pos, length);
            }
        }
    }

    private void placeThinCrystalColumn(ISeedReader reader, CrystalPileFeatureConfig config, BlockPos pos, int length) {
        for (int i = 1; i <= length; i++) {
            reader.setBlock(pos.relative(primaryFacing, i), config.crystal, BlockFlags.NO_RERENDER);
        }
    }

    private void sprinkleTinyCrystalsNearby(ISeedReader reader, BlockPos origin, Random rand, CrystalPileFeatureConfig config) {

        int radius = config.getExtraSmallCrystalsSpread().sample(rand);

        for (BlockPos blockPos : BlockPos.withinManhattan(origin, radius, 2, radius)) {
            if (shouldPlaceTinyCrystal(reader, origin, rand, config, radius, blockPos)) {
                reader.setBlock(blockPos, config.crystal, BlockFlags.NO_RERENDER);
            }
        }
    }

    private boolean shouldPlaceTinyCrystal(ISeedReader reader, BlockPos origin, Random rand, CrystalPileFeatureConfig config, int r, BlockPos blockPos) {
        return rand.nextFloat() < config.getExtraSmallCrystalChance() && blockPos.closerThan(origin, r) && canOccupy(reader, blockPos);
    }

    private boolean tryGrowBlock(ISeedReader world, BlockPos pos, CrystalPileFeatureConfig config) {
        if (canOccupy(world, pos)) {
            world.setBlock(pos, config.crystalBlock, BlockFlags.NO_RERENDER);
            return true;
        }

        return false;
    }

    private boolean tryReplace(ISeedReader world, BlockPos pos, CrystalPileFeatureConfig config) {
        if (canReplace(world, pos)) {
            BlockState state = primaryFacing != Direction.UP ? FBlocks.GEHENNITE.block.get().defaultBlockState() : config.crystalBlock;
            world.setBlock(pos, state, BlockFlags.NO_RERENDER);
            return true;
        }

        return false;
    }

    private boolean canOccupy(ISeedReader reader, BlockPos pos) {
        return reader.isEmptyBlock(pos) || reader.getBlockState(pos).is(Blocks.SNOW);
    }

    private boolean canReplace(IWorld world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return (Tags.Blocks.DIRT.contains(block) ||
                Tags.Blocks.STONE.contains(block) ||
                block.defaultBlockState().getMaterial() == Material.STONE ||
                block.is(FBlocks.ICICLE.get()));
    }
}
