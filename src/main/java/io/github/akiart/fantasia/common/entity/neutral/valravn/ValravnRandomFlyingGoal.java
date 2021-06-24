package io.github.akiart.fantasia.common.entity.neutral.valravn;

import io.github.akiart.fantasia.common.entity.ai.goal.RandomFlyingGoal;

public class ValravnRandomFlyingGoal extends RandomFlyingGoal {

    public ValravnRandomFlyingGoal(ValravnEntity entity, double speedModifier) {
        super(entity, speedModifier);
    }

    @Override
    public boolean canUse() {
        return !((ValravnEntity)entity).hasChosenVictim() && super.canUse();
    }
}
