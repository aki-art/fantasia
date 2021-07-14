package io.github.akiart.fantasia.common.entity.neutral.valravn.deletelater;

import net.minecraft.entity.ai.goal.GoalSelector;

public abstract class ValravnPhase {
    abstract void onEnter();
    abstract void onExit();
    abstract void onTick();
    abstract void addGoals(GoalSelector goalSelector, GoalSelector targetSelector);
    abstract void removeGoals(GoalSelector goalSelector, GoalSelector targetSelector);

    public class Wild extends ValravnPhase {

        @Override
        void onEnter() {

        }

        @Override
        void onExit() {

        }

        @Override
        void onTick() {

        }

        @Override
        void addGoals(GoalSelector goalSelector, GoalSelector targetSelector) {

        }

        @Override
        void removeGoals(GoalSelector goalSelector, GoalSelector targetSelector) {

        }
    }
}
