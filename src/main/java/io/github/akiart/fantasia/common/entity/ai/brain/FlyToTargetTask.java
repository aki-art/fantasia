package io.github.akiart.fantasia.common.entity.ai.brain;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.WalkToTargetTask;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Optional;

public class FlyToTargetTask extends WalkToTargetTask {

    private boolean tryComputePath(MobEntity entity, WalkTarget flightTarget, long l) {
        BlockPos blockpos = flightTarget.getTarget().currentBlockPosition();
        path = entity.getNavigation().createPath(blockpos, 0);
        float speedModifier = flightTarget.getSpeedModifier();
        Brain<?> brain = entity.getBrain();

        if (reachedTarget(entity, flightTarget)) {
            brain.eraseMemory(FMemoryModuleTypes.CANT_REACH_FLIGHT_TARGET_SINCE.get());
        } else {

            boolean canProceed = path != null && path.canReach();
            if (canProceed) {
                brain.eraseMemory(FMemoryModuleTypes.CANT_REACH_FLIGHT_TARGET_SINCE.get());
            } else if (!brain.hasMemoryValue(FMemoryModuleTypes.CANT_REACH_FLIGHT_TARGET_SINCE.get())) {
                brain.setMemory(FMemoryModuleTypes.CANT_REACH_FLIGHT_TARGET_SINCE.get(), l);
            }

            if (path != null) return true;

            Vector3d targetPosition = RandomPositionGenerator.getAirPosTowards((CreatureEntity)entity, 10, 7, 4, Vector3d.atBottomCenterOf(blockpos), speedModifier);
            if (targetPosition != null) {
                path = entity.getNavigation().createPath(targetPosition.x, targetPosition.y, targetPosition.z, 0);
                return path != null;
            }
        }

        return false;
    }

    private boolean reachedTarget(MobEntity entity, WalkTarget target) {
        return target.getTarget().currentBlockPosition().distManhattan(entity.blockPosition()) <= target.getCloseEnoughDist();
    }
}
