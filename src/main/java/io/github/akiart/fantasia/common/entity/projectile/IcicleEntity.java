package io.github.akiart.fantasia.common.entity.projectile;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class IcicleEntity extends AbstractArrowEntity {

    private float breakChance = 0.5f;
    private final static SoundEvent FALL_SOUND = SoundEvents.GLASS_FALL;

    public IcicleEntity(EntityType<? extends IcicleEntity> entityType, World world) {
        super(entityType, world);
        setSoundEvent(FALL_SOUND);
    }

    public IcicleEntity(World world, double x, double y, double z) {
        super(FEntities.ICICLE.get(), x, y, z, world);
        setSoundEvent(FALL_SOUND);
    }

    public IcicleEntity(World world, LivingEntity spawner) {
        super(FEntities.ICICLE.get(), spawner, world);
        setSoundEvent(FALL_SOUND);
    }

    protected boolean dealtDamage;
    public static final DataParameter<Boolean> ID_FOIL = EntityDataManager.defineId(JavelinEntity.class, DataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_FOIL, false);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil() {
        return this.entityData.get(ID_FOIL);
    }

    @Nullable
    protected EntityRayTraceResult findHitEntity(Vector3d v1, Vector3d v2) {
        return this.dealtDamage ? null : super.findHitEntity(v1, v2);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    @Override
    public void tick() {
        if (inGroundTime > 4) {
            dealtDamage = true;
        }

        super.tick();
    }

    @Override
    protected void onHit(RayTraceResult rayTraceResult) {
        super.onHit(rayTraceResult);

        if (!level.isClientSide) {
            if (random.nextFloat() < breakChance) {
                level.broadcastEntityEvent(this, (byte) 3);
                destroy();
            }
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult target) {
        super.onHitEntity(target);
        Entity entity = target.getEntity();
        float damage = 3.0F;

        Entity owner = getOwner();
        DamageSource damagesource = DamageSource.trident(this, (owner == null ? this : owner));
        this.dealtDamage = true;

        if (entity.hurt(damagesource, damage)) {

            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;
                if (owner instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity1, owner);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)owner, livingentity1);
                }

                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(SoundEvents.GLASS_HIT, 1f, 1f);
    }

    public void playerTouch(PlayerEntity player) {
        Entity entity = getOwner();
        if (entity == null || entity.getUUID() == player.getUUID()) {
            super.playerTouch(player);
        }
    }

    protected void destroy() {
        playSound(SoundEvents.GLASS_BREAK, 1f, 1f);
        remove();
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.GLASS_BREAK;
    }

    @Override
    protected ItemStack getPickupItem() {
        return FItems.ICICLE.get().getDefaultInstance();
    }


    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
