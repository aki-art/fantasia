package io.github.akiart.fantasia.common.block.blockType;

import io.github.akiart.fantasia.common.block.IAcidLoggable;
import io.github.akiart.fantasia.common.state.properties.FBlockStateProperties;
import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;

import javax.annotation.Nullable;

public class TestAcidLoggableBlock extends Block implements IAcidLoggable {
    public static final BooleanProperty ACIDLOGGED = FBlockStateProperties.ACIDLOGGED;

    public TestAcidLoggableBlock() {
        super(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noCollission());
        registerDefaultState(this.stateDefinition.any().setValue(ACIDLOGGED, false));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return defaultBlockState().setValue(ACIDLOGGED, fluidstate.is(FTags.Fluids.ACID) && fluidstate.getAmount() == 8);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ACIDLOGGED);
    }
}
