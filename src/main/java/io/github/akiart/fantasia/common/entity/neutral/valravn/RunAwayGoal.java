package io.github.akiart.fantasia.common.entity.neutral.valravn;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class RunAwayGoal extends Goal  {

    protected final double SLOW_RANGE_SQR = 49;
    protected final double preferredYLevel;

    protected ValravnEntity entity;
    protected double walkSpeedModifier;
    protected double sprintSpeedModifier;
    protected EntityPredicate avoidEntityTargeting;
    protected PlayerEntity player;
    protected Path path;
    protected final PathNavigator pathNav;

    public RunAwayGoal(ValravnEntity entity, double maxDistance, double walkSpeedModifier, double sprintSpeedModifier) {
        this.entity = entity;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.pathNav = entity.getNavigation();
        this.preferredYLevel = entity.level.getHeight() + 30;
        this.avoidEntityTargeting = new EntityPredicate()
                .allowUnseeable()
                .allowInvulnerable()
                .allowNonAttackable()
                .range(maxDistance)
                .selector(EntityPredicates.NO_SPECTATORS::test);

        setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public void start() {
        pathNav.moveTo(path, 1f);
    }

    @Override
    public void tick() {
        pathNav.setSpeedModifier(player.distanceToSqr(entity) < SLOW_RANGE_SQR ? sprintSpeedModifier : walkSpeedModifier);
    }

    @Override
    public void stop() {
        player = null;
    }

    @Override
    public boolean canUse() {
        if(!entity.isCarryingVictim()) return false;

        player = entity.level.getNearestPlayer(avoidEntityTargeting, entity);
        if(player == null) return false;

        Vector3d vector3d = Vector3d.atBottomCenterOf(player.blockPosition());
        Vector3d targetPos = getAirPosAvoid(entity, vector3d);
        if(targetPos == null) return false;

        //if(player.distanceToSqr(targetPos) < player.distanceToSqr(entity)) return false; // random pos somehow wants to move closer instead of away

        path = pathNav.createPath(targetPos.x, targetPos.y, targetPos.z, 0);

        return path != null;
    }

    public Vector3d getAirPosAvoid(CreatureEntity entity, Vector3d playerPos) {
        Vector3d vector3d = entity.position().subtract(playerPos);
        int yOffset = entity.getY() < preferredYLevel ? 4 : 0;
        return RandomPositionGenerator.generateRandomPos(entity, 36, 3, yOffset, vector3d, false, Math.PI / 10F, entity::getWalkTargetValue, true, 0, 0, false);
    }

    @Override
    public boolean canContinueToUse() {
        return entity.isCarryingVictim() && pathNav.isInProgress();
    }
}
