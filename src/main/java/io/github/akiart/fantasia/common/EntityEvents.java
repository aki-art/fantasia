package io.github.akiart.fantasia.common;

import io.github.akiart.fantasia.FTags;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.enchantment.FEnchantments;
import io.github.akiart.fantasia.common.potion.FEffects;
import io.github.akiart.fantasia.common.world.spawner.ValravnSpawner;
import io.github.akiart.fantasia.util.FDamageSource;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvents {

    @SubscribeEvent
    public static void onPotionApply(PotionEvent.PotionApplicableEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if(event.getPotionEffect().getEffect() == Effects.POISON && hasPoisonProtection(entity)) {
            if(entity.getRandom().nextFloat() < event.getPotionEffect().getAmplifier() * 0.2f) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    private static boolean hasPoisonProtection(LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(FEnchantments.POISON_PROTECTION.get(), entity) > 0;
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.updateFluidHeightAndDoFluidPushing(FTags.Fluids.ACID, 0.014D);

        boolean isInAcid = entity.getFluidHeight(FTags.Fluids.ACID) > 0;
        if(isInAcid && !entity.hasEffect(FEffects.ACID_REPEL.get())) {
            if(entity.hurt(FDamageSource.ACID, 4f)) {
                entity.playSound(SoundEvents.GENERIC_BURN, 1f, 1f);
            }
        }
    }

    public static class CreateSpawnPosition extends WorldEvent {
        private final IServerWorldInfo settings;

        public CreateSpawnPosition(IWorld world, IServerWorldInfo settings) {
            super(world);
            this.settings = settings;
        }

        public IServerWorldInfo getSettings() {
            return settings;
        }
    }

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
            CUSTOM_SPAWNERS.remove((ServerWorld) evt.getWorld());
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.WorldTickEvent tick) {
        if (!tick.world.isClientSide() && tick.world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) tick.world;
            ValravnSpawner spawner = CUSTOM_SPAWNERS.get(serverWorld);
            if (spawner != null) {
                spawner.tick(serverWorld, true, true);
            }
        }
    }
}
