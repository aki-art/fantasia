package io.github.akiart.fantasia.common.entity.ai.brain;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;

public class PickRandomFlightTargetTask extends Task<CreatureEntity> {

    private final int maxHorizontalDistance;
    private final int maxVerticalDistance;
    private final float speed;
    protected float maxAngleChange = (float)Math.PI / 8f;

    public PickRandomFlightTargetTask(int duration, float speed, int maxHorizontalDistance, int maxVerticalDistance) {
        super(ImmutableMap.of(FMemoryModuleTypes.FLIGHT_TARGET.get(), MemoryModuleStatus.VALUE_ABSENT), duration);
        this.speed = speed;
        this.maxHorizontalDistance = maxHorizontalDistance;
        this.maxVerticalDistance = maxVerticalDistance;
    }

    protected void start(ServerWorld world, CreatureEntity entity, long l) {
        Vector3d viewVector = entity.getViewVector(0.0F);
        Optional<Vector3d> optional = Optional.ofNullable(RandomPositionGenerator.getAboveLandPos(entity, maxHorizontalDistance, maxVerticalDistance, viewVector, maxAngleChange, 2, 1));
       entity.getBrain().setMemory(FMemoryModuleTypes.FLIGHT_TARGET.get(), optional.map((target) -> new WalkTarget(target, speed, 0)));
    }
}
