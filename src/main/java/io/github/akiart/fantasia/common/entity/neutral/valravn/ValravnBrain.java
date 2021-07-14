package io.github.akiart.fantasia.common.entity.neutral.valravn;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.github.akiart.fantasia.common.entity.ai.brain.FlyToTargetTask;
import io.github.akiart.fantasia.common.entity.ai.brain.PickRandomFlightTargetTask;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.LookTask;
import net.minecraft.entity.ai.brain.task.WalkToTargetTask;

public class ValravnBrain {

    protected static Brain<?> makeBrain(Brain<ValravnEntity2> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    private static void initCoreActivity(Brain<ValravnEntity2> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new LookTask(45, 90),
                new FlyToTargetTask()
        ));
    }

    private static void initIdleActivity(Brain<ValravnEntity2> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
                new PickRandomFlightTargetTask(60, 1f, 10, 10)
        ));
    }
}
