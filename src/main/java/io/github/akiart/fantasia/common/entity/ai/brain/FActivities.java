package io.github.akiart.fantasia.common.entity.ai.brain;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FActivities {
    public static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister.create(ForgeRegistries.ACTIVITIES, Fantasia.ID);

    public static final RegistryObject<Activity> HUNT = ACTIVITIES.register("hunt", () -> new Activity("hunt"));
    public static final RegistryObject<Activity> FLEE = ACTIVITIES.register("flee", () -> new Activity("flee"));
    public static final RegistryObject<Activity> FOLLOW = ACTIVITIES.register("follow", () -> new Activity("follow"));
    public static final RegistryObject<Activity> KNIGHT = ACTIVITIES.register("knight", () -> new Activity("knight"));
}
