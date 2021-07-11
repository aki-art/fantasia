package io.github.akiart.fantasia.common.block.blockType.plants;

import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class SnowBerryBushBottomBlock extends BushBlock implements IGrowable {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final int MAX_AGE = 3;
    private static final int TOP_APPEAR_AGE = 1;
    private static final int MIN_LIGHT_LEVEL = 9;
    private static final int NATURAL_GROWTH_CHANCE = 5;

    public SnowBerryBushBottomBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AGE, 0));
    }

    private void placeTopPiece(World world, BlockPos pos, int age) {
        world.setBlock(pos.above(), FBlocks.SNOWBERRY_BUSH_TOP.get().defaultBlockState().setValue(SnowBerryBushTopBlock.AGE, age), Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld world, BlockPos blockPos, Random random) {
        int age = blockState.getValue(AGE);
        if (canGrow(blockState, world, blockPos, random, age)) {
            ageUp(world, blockPos, blockState);
            ForgeHooks.onCropsGrowPost(world, blockPos, blockState);
        }
    }

    private boolean canGrow(BlockState blockState, ServerWorld world, BlockPos blockPos, Random random, int age) {
        boolean hasSpaceAbove = world.getBlockState(blockPos.above()).getMaterial() == Material.AIR;
        boolean isLightEnough = world.getRawBrightness(blockPos.above(), 0) >= MIN_LIGHT_LEVEL;
        return age < MAX_AGE && hasSpaceAbove && isLightEnough && ForgeHooks.onCropsGrowPre(world, blockPos, blockState, random.nextInt(NATURAL_GROWTH_CHANCE) == 0);
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        int age = blockState.getValue(AGE);
        boolean grown = isFullyGrown(blockState);

        // if player is trying to bone meal, let them
        if (!grown && player.getItemInHand(hand).getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        }
        // harvest is old enough
        if (age > 1) {
            return harvestBerries(blockState, world, blockPos);
        }

        return super.use(blockState, world, blockPos, player, hand, rayTraceResult);
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    private ActionResultType harvestBerries(BlockState state, World world, BlockPos pos) {
        popResource(world, pos, new ItemStack(FItems.SNOW_BERRY.get(), getBerryCount(world, state.getValue(AGE))));
        world.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
        setAge(world, state, pos, 1);
        return ActionResultType.sidedSuccess(world.isClientSide);
    }

    // give 0-5 berries depending on age
    private int getBerryCount(World world, int age) {
        return world.random.nextInt(4) + age - 1;
    }

    public ItemStack getCloneItemStack(IBlockReader blockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(FItems.SNOW_BERRY.get());
    }

    private boolean isFullyGrown(BlockState blockState) {
        return blockState.getValue(AGE) == MAX_AGE;
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return !isFullyGrown(blockState);
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader reader, BlockPos blockPos, BlockState blockState, boolean clientSide) {
        return blockState.getValue(AGE) < MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld serverWorld, Random random, BlockPos blockPos, BlockState blockState) {
        ageUp(serverWorld, blockPos, blockState);
    }

    public void ageUp(ServerWorld world, BlockPos pos, BlockState state) {
        if(state.is(this)) {
            int newAge = Math.min(MAX_AGE, state.getValue(AGE) + 1);
            setAge(world, state, pos, newAge);
        }
    }

    public void placeAt(IWorld world, BlockPos pos, int blockFlag, int age) {
        world.setBlock(pos, defaultBlockState().setValue(AGE, age), blockFlag);
        if(age > 1) {
            world.setBlock(pos.above(), defaultBlockState().setValue(SnowBerryBushTopBlock.AGE, age), blockFlag);
        }
    }

    private void setAge(World world, BlockState state, BlockPos pos, int age) {

        BlockState topPiece = world.getBlockState(pos.above());
        if (topPiece.is(FBlocks.SNOWBERRY_BUSH_TOP.get())) {
            if(age >= TOP_APPEAR_AGE ) {
                world.setBlock(pos.above(), topPiece.setValue(SnowBerryBushTopBlock.AGE, age), Constants.BlockFlags.BLOCK_UPDATE);
            }
            else {
                world.destroyBlock(pos.above(), false);
            }
        }
        else if(age >= TOP_APPEAR_AGE) {
            placeTopPiece(world, pos, age);
        }

        world.setBlock(pos, state.setValue(AGE, age), Constants.BlockFlags.BLOCK_UPDATE);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
