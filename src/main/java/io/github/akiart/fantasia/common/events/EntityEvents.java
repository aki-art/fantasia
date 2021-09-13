package io.github.akiart.fantasia.common.events;

import io.github.akiart.fantasia.common.capability.Capabilities;
import io.github.akiart.fantasia.common.capability.entity.DruidHoodPacifiable;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.potion.FEffects;
import io.github.akiart.fantasia.common.tags.FTags;
import io.github.akiart.fantasia.util.FDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvents {

    @SubscribeEvent
    public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        // Cancels animals targeting an entity if it's wearing a druid hood
        if (isAnimal(event.getEntity()) && isWearingDruidHood(event.getTarget())) {
            MobEntity entity = (MobEntity) event.getEntity();
            if(!isAngry(entity, event.getTarget())) {
                entity.target = null;
            }
        }
    }

    private static boolean isAngry(LivingEntity entity, LivingEntity at) {
        return (entity instanceof IAngerable && ((IAngerable)entity).isAngryAt(at)) || entity.lastHurtByPlayer == at;
    }

    private static boolean isWearingDruidHood(LivingEntity entity) {
        return entity != null && entity.hasItemInSlot(EquipmentSlotType.HEAD) && entity.getItemBySlot(EquipmentSlotType.HEAD).getItem() == FItems.DRUID_HOOD.get();
    }

    private static boolean isAnimal(Entity entity) {
        return entity instanceof MobEntity && FTags.EntityTypes.DRUID_HOOD_AFFECTED.contains(entity.getType());
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingAttackEvent event) {
        if(event.getSource() == FDamageSource.ACID) {
            if(isAcidImmune(event.getEntityLiving())) {
                event.setCanceled(true);
            }
        }

        if(event.getSource().getEntity() != null) {
            event.getEntityLiving().getCapability(DruidHoodPacifiable.INSTANCE).ifPresent(cap -> {
                Entity entity = event.getSource().getEntity();
                cap.addTraitor(entity.getUUID(), entity.tickCount);
            });
        }
    }

    private static boolean isAcidImmune(LivingEntity entity) {
        return FTags.EntityTypes.ACID_IMMUNE.contains(entity.getType()) || entity.hasEffect(FEffects.ACID_REPEL.get());
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isAffectedByFluids() && entity.getFluidHeight(FTags.Fluids.ACID) > 0) {
            jumpInAcid(entity);
        }
    }

    private static void jumpInAcid(LivingEntity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, (double) 0.04F * entity.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue(), 0.0D));
    }

    // FIXME
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        // Preventing fall damage when falling into acid
        LivingEntity entity = event.getEntityLiving();
        entity.getCapability(Capabilities.ACID).ifPresent(
                cap -> {
                    if (cap.isInAcid()) event.setResult(null);
                }
        );
    }

    @SubscribeEvent
    public static void onPotionApply(PotionEvent.PotionApplicableEvent event) {
        // Preventing poison damage is Poison Protection is active
        LivingEntity entity = event.getEntityLiving();
        if (event.getPotionEffect().getEffect() == Effects.POISON && hasPoisonProtection(entity)) {
            // calculating a chance to cancel based on enchantment level
            if (entity.getRandom().nextFloat() < event.getPotionEffect().getAmplifier() * 0.2f) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    private static boolean hasPoisonProtection(LivingEntity entity) {
        return false;// EnchantmentHelper.getEnchantmentLevel(FEnchantments.POISON_PROTECTION.get(), entity) > 0;
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        //LivingEntity entity = event.getEntityLiving();;

        // if in acid, get hurt by it
        //boolean isInAcid = entity.getFluidHeight(FTags.Fluids.ACID) > 0;


//        if(isInAcid/* && !entity.hasEffect(FEffects.ACID_REPEL.get())*/) {
//            if(entity.hurt(FDamageSource.ACID, 4f)) {
//                entity.playSound(SoundEvents.GENERIC_BURN, 1f, 1f);
//            }
//        }
    }

    //private static final Map<ServerWorld, ValravnSpawner> CUSTOM_SPAWNERS = new HashMap<>();

//    @SubscribeEvent
//    public static void worldLoad(WorldEvent.Load evt) {
//        if (!evt.getWorld().isClientSide() && evt.getWorld() instanceof ServerWorld) {
//            CUSTOM_SPAWNERS.put((ServerWorld) evt.getWorld(), new ValravnSpawner());
//        }
//    }
//
//    @SubscribeEvent
//    public static void worldUnload(WorldEvent.Unload evt) {
//        if (!evt.getWorld().isClientSide() && evt.getWorld() instanceof ServerWorld) {
//            CUSTOM_SPAWNERS.remove((ServerWorld) evt.getWorld());
//        }
//    }
//
//    @SubscribeEvent
//    public static void onServerTick(TickEvent.WorldTickEvent tick) {
//        if (!tick.world.isClientSide() && tick.world instanceof ServerWorld) {
//            ServerWorld serverWorld = (ServerWorld) tick.world;
//            ValravnSpawner spawner = CUSTOM_SPAWNERS.get(serverWorld);
//            if (spawner != null) {
//                spawner.tick(serverWorld, true, true);
//            }
//        }
//    }
}