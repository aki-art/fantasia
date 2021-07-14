package io.github.akiart.fantasia.common.entity.neutral.valravn.deletelater;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class FindBabyGoal extends Goal {
    private static final int CHECK_INTERVAL = 80;
    private static final int SEARCH_RANGE_Y = 64;
    private static final int SEARCH_RANGE_XZ = 16;

    private final EntityPredicate victimCondition;
    private final ValravnEntity entity;

    private int lastCheck = CHECK_INTERVAL;

    public FindBabyGoal(ValravnEntity entity) {

        this.entity = entity;

        victimCondition = (new EntityPredicate())
                .range(entity.getFollowDistance())
                .selector(this::isEntityAttackableBaby);
    }

    protected AxisAlignedBB getTargetSearchArea() {
        return entity.getBoundingBox().inflate(SEARCH_RANGE_XZ, SEARCH_RANGE_Y, SEARCH_RANGE_XZ);
    }

    @Override
    public void start() {
        lastCheck = CHECK_INTERVAL;
    }

    @Override
    public void stop() {
        Fantasia.LOGGER.info("exiting snatch goal");
    }

    @Override
    public boolean canUse() {
        if (lastCheck++ > CHECK_INTERVAL && !entity.hasChosenVictim()) {
            lastCheck = 0;

            LivingEntity baby = entity.level.getNearestLoadedEntity(AnimalEntity.class, victimCondition,
                    entity, entity.getX(), entity.getEyeY(), entity.getZ(), getTargetSearchArea());

            if (baby != null) {
                entity.setVictim(baby);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !entity.hasChosenVictim();
    }

    boolean isEntityAttackableBaby(LivingEntity entity) {
        return ValravnEntity.isEntityValidVictim(entity, false);
    }
}