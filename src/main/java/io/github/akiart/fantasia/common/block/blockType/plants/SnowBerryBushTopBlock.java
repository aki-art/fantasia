package io.github.akiart.fantasia.common.block.blockType.plants;

import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class SnowBerryBushTopBlock extends BushBlock implements IGrowable {

    // Starts from one because 0 age top should not exist
    public static final IntegerProperty AGE = IntegerProperty.create("age", 1, 3);

    public SnowBerryBushTopBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AGE, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public ItemStack getCloneItemStack(IBlockReader blockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(FItems.SNOW_BERRY.get());
    }

    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClientSide) {
            BlockPos belowPos = pos.below();
            BlockState belowState = world.getBlockState(belowPos);
            if (belowState.is(FBlocks.SNOWBERRY_BUSH_BOTTOM.get())) {
                if (player.isCreative()) {
                    world.setBlock(belowPos, Blocks.AIR.defaultBlockState(), Constants.BlockFlags.DEFAULT | Constants.BlockFlags.NO_NEIGHBOR_DROPS);
                    world.levelEvent(player, Constants.WorldEvents.BREAK_BLOCK_EFFECTS, belowPos, Block.getId(belowState));
                }
                else {
                    world.destroyBlock(pos.below(), true);
                }
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.is(FBlocks.SNOWBERRY_BUSH_BOTTOM.get());
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }


    // pass usage to the bottom block, handle things from there
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        BlockState bottomState = world.getBlockState(pos.below());
        if (bottomState.is(FBlocks.SNOWBERRY_BUSH_BOTTOM.get())) {
            return bottomState.getBlock().use(bottomState, world, pos.below(), player, hand, rayTraceResult);
        }
        return super.use(state, world, pos, player, hand, rayTraceResult);
    }

    @Override
    public boolean canSurvive(BlockState thisState, IWorldReader world, BlockPos pos) {

        if (thisState.getBlock() != this) {
            // Forge: "This function is called during world gen and placement, before this block is set,
            // so if we are not 'here' then assume it's the pre-check."
            return super.canSurvive(thisState, world, pos);
        }

        BlockState bottomState = world.getBlockState(pos.below());
        return bottomState.is(FBlocks.SNOWBERRY_BUSH_BOTTOM.get()) && bottomState.getValue(SnowBerryBushBottomBlock.AGE) > 0;
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader world, BlockPos pos, BlockState state, boolean clientSide) {
        return state.getValue(AGE) < SnowBerryBushBottomBlock.MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos belowPos = pos.below();
        BlockState bottomState = world.getBlockState(belowPos);
        if (bottomState.is(FBlocks.SNOWBERRY_BUSH_BOTTOM.get())) {
            IGrowable bottomGrowable = (IGrowable)bottomState.getBlock();
            if(bottomGrowable.isValidBonemealTarget(world, belowPos, bottomState, false)) {
                bottomGrowable.performBonemeal(world, random, belowPos, bottomState);
            }
        }
    }
}
