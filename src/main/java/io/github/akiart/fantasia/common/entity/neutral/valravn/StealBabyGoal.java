package io.github.akiart.fantasia.common.entity.neutral.valravn;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class StealBabyGoal extends Goal {

    ValravnEntity entity;
    LivingEntity target;
    Vector3d targetPos;
    int updateInterval;
    private int timeToRecalculatePath;
    private final double speedModifier;

    static final float BOARDING_DISTANCE = 1;

    public StealBabyGoal(ValravnEntity entity, double speedModifier) {
        this.entity = entity;
        this.speedModifier = speedModifier;
        setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

//    @Override
//    public void tick() {
//        if(target != null) targetPos = target.position();
//    }

    @Override
    public void start() {
        target = entity.getVictim();
        entity.getNavigation().moveTo(target, speedModifier);
    }

    @Override
    public boolean canUse() {
        return entity.getState() == ValravnEntity.State.SWOOPING && entity.getVictim() != null;
    }

    boolean isTargetOutOfReach(LivingEntity target) {
        return target == null || target.isDeadOrDying() || !entity.canAttack(target);
    }

    public void tick() {
        if (--timeToRecalculatePath <= 0) {
            timeToRecalculatePath = 10;
            entity.getNavigation().moveTo(target, speedModifier);
        }
    }

    // This is not ideal, selecting next goals should be handles from goal selector
    public boolean canContinueToUse() {

        if (isTargetOutOfReach(entity.getVictim())) {
            entity.setState(ValravnEntity.State.SEARCHING);
            return false;
        }

        if (isInRange()) {
            entity.pickUpVictim(target);
            return false;
        }

        if (entity.isAngry()) {
            entity.setState(ValravnEntity.State.COMBAT_WILD);
            return false;
        }

        return canUse();
    }

    private boolean isInRange() {
        return entity.distanceToSqr(target) < BOARDING_DISTANCE * BOARDING_DISTANCE;
    }
}
