package io.github.akiart.fantasia.common.entity.ai.goal;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class RandomFlyingGoal extends Goal {

    protected CreatureEntity entity;
    protected final double speedModifier;
    protected int interval = 20;
    protected float maxAngleChange = (float)Math.PI / 8f;

    public RandomFlyingGoal(CreatureEntity entity, double speedModifier) {
        this.entity = entity;
        this.speedModifier = speedModifier;
        setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    protected Vector3d getPosition() {
        Vector3d viewVector = entity.getViewVector(0.0F);
        Vector3d pos = RandomPositionGenerator.getAboveLandPos(entity, 16, 7, viewVector, maxAngleChange, 2, 1);
        return pos != null ? pos : RandomPositionGenerator.getAirPos(entity, 16, 4, -2, viewVector, Math.PI / 2D);
    }

    @Override
    public void start() {
        Fantasia.LOGGER.info("entering randomflying goal");
        Vector3d targetPos = getPosition();
        if (targetPos != null) {
            entity.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, speedModifier);
        }
    }

    @Override
    public void stop() {
        Fantasia.LOGGER.info("exiting randomflying goal");
    }

    @Override
    public boolean canContinueToUse() {
        return entity.isPathFinding();
    }

    @Override
    public boolean canUse() {
        return !entity.isVehicle() && !entity.isPathFinding() && entity.getRandom().nextInt(interval) == 0;
    }
}
