package io.github.akiart.fantasia.common.entity.ai.brain;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.schedule.ScheduleBuilder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class FSchedules {

    private static final int MORNING = 5000;
    private static final int EVENING = 11000;

    public static final DeferredRegister<Schedule> SCHEDULES = DeferredRegister.create(ForgeRegistries.SCHEDULES, Fantasia.ID);

    public static final RegistryObject<Schedule> WILD_VALRAVN_SCHEDULE = SCHEDULES.register("wild_valravn",
            () -> new ScheduleBuilder(new Schedule())
                    .changeActivityAt(EVENING, FActivities.HUNT.get())
                    .changeActivityAt(MORNING, Activity.IDLE)
                    .build());
}
