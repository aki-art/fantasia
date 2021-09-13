package io.github.akiart.fantasia.common.block.blockType;

import io.github.akiart.fantasia.common.capability.Capabilities;
import io.github.akiart.fantasia.common.tags.FTags;
import io.github.akiart.fantasia.util.FDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class AcidFluidBlock extends FlowingFluidBlock {

    public AcidFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

    @Override
    public boolean isPathfindable(BlockState pState, IBlockReader pLevel, BlockPos pPos, PathType pType) {
        return false;
    }

    @Override
    public void entityInside(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {

        // update acid capability
        pEntity.getCapability(Capabilities.ACID).ifPresent(cap -> {
            cap.setIsInAcid(true);
        });

        // acid damage
        if (pEntity.hurt(FDamageSource.ACID, 2f)) {
            pEntity.playSound(SoundEvents.GENERIC_BURN, 1f, 1f);
        }
    }
}
