package io.github.akiart.fantasia.mixin;

import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Allows entities to pathfind and swim in acid
@Mixin(SwimNodeProcessor.class)
public class SwimNodeProcessorMixin {

    @Inject(at = @At("RETURN"), method = "getBlockPathType(Lnet/minecraft/world/IBlockReader;III)Lnet/minecraft/pathfinding/PathNodeType;", cancellable = true)
    private void getBlockPathType(IBlockReader pLevel, int pX, int pY, int pZ, CallbackInfoReturnable<PathNodeType> callback) {
        if(pLevel.getFluidState(new BlockPos(pX, pY, pZ)).is(FTags.Fluids.ACID)) {
            callback.setReturnValue(PathNodeType.WATER);
        }
    }
}
