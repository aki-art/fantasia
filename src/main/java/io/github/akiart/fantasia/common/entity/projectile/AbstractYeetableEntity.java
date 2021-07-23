package io.github.akiart.fantasia.common.entity.projectile;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.util.FDamageSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class AbstractYeetableEntity extends AbstractArrowEntity {

    protected ItemStack item;
    protected boolean doneDealingDamage = false;
    protected int clientSideReturnTimer;
    protected ResourceLocation texture = new ResourceLocation(Fantasia.ID, "textures/entity/javelin/wooden_javelin.png");
    public static final DataParameter<Byte> ID_LOYALTY = EntityDataManager.defineId(JavelinEntity.class, DataSerializers.BYTE);
    public static final DataParameter<Boolean> ID_FOIL = EntityDataManager.defineId(JavelinEntity.class, DataSerializers.BOOLEAN);

    // Used by living entities
    public AbstractYeetableEntity(EntityType<? extends AbstractYeetableEntity> type, World world, LivingEntity entity, ItemStack item) {
        super(type, entity, world);
        setDataFromItem(item);
    }

    // Used by dispensers
    public AbstractYeetableEntity(EntityType<? extends AbstractYeetableEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
        setDataFromItem(item);
    }

    // Used by renderers or when spawned from commands
    public AbstractYeetableEntity(EntityType<? extends AbstractYeetableEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void setDataFromItem(ItemStack item) {
        this.item = item.copy();
        entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(item));
        entityData.set(ID_FOIL, item.hasFoil());
    }

    @Override
    protected ItemStack getPickupItem() {
        return item;
    }

    @Override
    public void tick() {
        if (inGroundTime > 4) doneDealingDamage = true;

        Entity entity = getOwner();

        if ((doneDealingDamage || isNoPhysics()) && entity != null) {
            double loyalty = entityData.get(ID_LOYALTY);

            if (loyalty > 0 && !isAcceptibleReturnOwner()) {
                if (!level.isClientSide && pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    spawnAtLocation(getPickupItem(), 0.1F);
                }
                remove();
            } else if (loyalty > 0) {
                returnToOwner(entity, loyalty);
            }
        }
        super.tick();
    }

    protected void returnToOwner(Entity entity, double loyalty) {
        setNoPhysics(true);

        Vector3d vector3d = new Vector3d(entity.getX() - getX(), entity.getEyeY() - getY(), entity.getZ() - getZ());
        setPosRaw(getX(), getY() + vector3d.y * 0.015D * loyalty, getZ());

        if (level.isClientSide) yOld = getY();

        double speed = 0.05D * loyalty;

        setDeltaMovement(getDeltaMovement().scale(0.95D).add(vector3d.normalize().scale(speed)));

        if (clientSideReturnTimer == 0) {
            playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
        }

        ++clientSideReturnTimer;
    }

    protected boolean isAcceptibleReturnOwner() {
        Entity owner = getOwner();
        if (owner != null && owner.isAlive()) {
            return !((owner instanceof ServerPlayerEntity) && owner.isSpectator());
        }

        return false;
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult target) {
        Entity entity = target.getEntity();
        float damage = getDamage(entity);

        Entity owner = getOwner();
        DamageSource damagesource = FDamageSource.javelin(this, (owner == null ? this : owner));

        if (entity.hurt(damagesource, damage)) {
            if (entity.getType() == EntityType.ENDERMAN) return;

            if (entity instanceof LivingEntity) {
                LivingEntity hitEntity = (LivingEntity) entity;
                if (owner instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(hitEntity, owner);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, hitEntity);
                }

                doPostHurtEffects(hitEntity);
            }
        }

        onHitEntityPost();
        playSound(getHitEntitySoundEvent(), 1f, 1f);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ID_LOYALTY, (byte) 0);
        entityData.define(ID_FOIL, false);
    }


    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);

        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(this.item));
        this.entityData.set(ID_FOIL, nbt.getBoolean("F_Foil"));

        if (nbt.contains("F_Item", 10)) {
            this.item = ItemStack.of(nbt.getCompound("F_Item"));
        }

        this.doneDealingDamage = nbt.getBoolean("F_DealtDamage");
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put("F_Item", item.save(new CompoundNBT()));
        nbt.putBoolean("F_DealtDamage", doneDealingDamage);
        nbt.putBoolean("F_Foil", isFoil());
    }

    public void playerTouch(PlayerEntity player) {
        Entity entity = this.getOwner();
        if (entity == null || entity.getUUID() == player.getUUID()) {
            super.playerTouch(player);
        }
    }

    protected SoundEvent getHitEntitySoundEvent() {
        return SoundEvents.TRIDENT_HIT;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil() {
        return entityData.get(ID_FOIL);
    }


    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    protected void onHitEntityPost() {
        doneDealingDamage = true;
        setDeltaMovement(getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }

    protected float getDamage(Entity entity) {
        float damage = item == null ? 6 : item.getDamageValue();
        if (entity instanceof LivingEntity && item != null) {
            LivingEntity livingentity = (LivingEntity) entity;
            damage += EnchantmentHelper.getDamageBonus(item, livingentity.getMobType());
            return damage;
        }
        return damage;
    }

    @Nullable
    protected EntityRayTraceResult findHitEntity(Vector3d v1, Vector3d v2) {
        return doneDealingDamage ? null : super.findHitEntity(v1, v2);
    }

    @Override
    public void tickDespawn() {
        if (pickup != AbstractArrowEntity.PickupStatus.ALLOWED || entityData.get(ID_LOYALTY) <= 0) {
            super.tickDespawn();
        }
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    public ResourceLocation getResourceLocation() {
        return texture;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
