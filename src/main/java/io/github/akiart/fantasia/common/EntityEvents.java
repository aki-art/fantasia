package io.github.akiart.fantasia.common;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.world.spawner.ValravnSpawner;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvents {

//    @SubscribeEvent
//    public static void onPlayerPostTick(TickEvent.PlayerTickEvent player)
//    {
//    }

    private static final Map<ServerWorld, ValravnSpawner> CUSTOM_SPAWNERS = new HashMap<>();

    @SubscribeEvent
    public static void worldLoad(WorldEvent.Load evt) {
        if (!evt.getWorld().isClientSide() && evt.getWorld() instanceof ServerWorld) {
            CUSTOM_SPAWNERS.put((ServerWorld) evt.getWorld(), new ValravnSpawner());
        }
    }

    @SubscribeEvent
    public static void worldUnload(WorldEvent.Unload evt) {
        if (!evt.getWorld().isClientSide() && evt.getWorld() instanceof ServerWorld) {
            CUSTOM_SPAWNERS.remove((ServerWorld)evt.getWorld());
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.WorldTickEvent tick){
        if(!tick.world.isClientSide() && tick.world instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)tick.world;
            ValravnSpawner spawner = CUSTOM_SPAWNERS.get(serverWorld);
            if (spawner != null) {
                spawner.tick(serverWorld, true, true);
            }
        }
    }
}
