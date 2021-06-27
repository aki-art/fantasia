package io.github.akiart.fantasia.common.entity.projectile;

import com.google.common.collect.Maps;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.item.FBoatEntity;
import io.github.akiart.fantasia.common.item.itemType.JavelinItem;
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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.*;

public class JavelinEntity extends AbstractArrowEntity {
    public static final DataParameter<Byte> ID_LOYALTY = EntityDataManager.defineId(JavelinEntity.class, DataSerializers.BYTE);
    public static final DataParameter<Boolean> ID_FOIL = EntityDataManager.defineId(JavelinEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<String> ID_TYPE = EntityDataManager.defineId(JavelinEntity.class, DataSerializers.STRING);

    protected ItemStack item;
    protected boolean dealtDamage;
    protected int clientSideReturnTimer;
    protected ResourceLocation texture = new ResourceLocation(Fantasia.ID, "textures/entity/javelin/wooden_javelin.png");

    public JavelinEntity(EntityType<? extends JavelinEntity> type, World world, LivingEntity entity, ItemStack item) {
        super(type, entity, world);
        this.item = item.copy();
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(item));
        this.entityData.set(ID_FOIL, item.hasFoil());

        if (item.getItem().getRegistryName() != null) {
            setJavelinType(item.getItem().getRegistryName().getPath());
        }
    }

    public String getJavelinType() {
        return entityData.get(ID_TYPE);
    }

    public void setJavelinType(String type) {
        entityData.set(ID_TYPE, type);
        this.texture = new ResourceLocation(Fantasia.ID, "textures/entity/javelin/" + type + ".png");
    }

    public JavelinEntity(EntityType<? extends JavelinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        if (inGroundTime > 4) {
            dealtDamage = true;
        }

        Entity entity = getOwner();
        if ((this.dealtDamage || isNoPhysics()) && entity != null) {
            double loyalty = entityData.get(ID_LOYALTY);

            if (loyalty > 0 && !this.isAcceptibleReturnOwner()) {
                if (!level.isClientSide && pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    spawnAtLocation(getPickupItem(), 0.1F);
                }
                remove();
            } else if (loyalty > 0) {
                setNoPhysics(true);

                Vector3d vector3d = new Vector3d(entity.getX() - getX(), entity.getEyeY() - getY(), entity.getZ() - getZ());
                setPosRaw(this.getX(), getY() + vector3d.y * 0.015D * (double) loyalty, getZ());
                if (level.isClientSide) {
                    yOld = this.getY();
                }

                double speed = 0.05D * loyalty;
                setDeltaMovement(getDeltaMovement().scale(0.95D).add(vector3d.normalize().scale(speed)));
                if (clientSideReturnTimer == 0) {
                    playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++clientSideReturnTimer;
            }
        }

        super.tick();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Nullable
    protected EntityRayTraceResult findHitEntity(Vector3d v1, Vector3d v2) {
        return this.dealtDamage ? null : super.findHitEntity(v1, v2);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_LOYALTY, (byte) 0);
        this.entityData.define(ID_FOIL, false);
        this.entityData.define(ID_TYPE, "wooden_javelin");
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil() {
        return entityData.get(ID_FOIL);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult target) {
        Entity entity = target.getEntity();
        float damage = 8.0F;

        if (entity instanceof LivingEntity && item != null) {
            LivingEntity livingentity = (LivingEntity) entity;
            damage += EnchantmentHelper.getDamageBonus(item, livingentity.getMobType());
        }

        Entity owner = this.getOwner();
        DamageSource damagesource = DamageSource.trident(this, (owner == null ? this : owner));
        this.dealtDamage = true;

        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(damagesource, damage)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity) entity;
                if (owner instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity1, owner);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, livingentity1);
                }

                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;

        this.playSound(soundevent, f1, 1.0F);
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    public void playerTouch(PlayerEntity player) {
        Entity entity = this.getOwner();
        if (entity == null || entity.getUUID() == player.getUUID()) {
            super.playerTouch(player);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);

        if (nbt.contains("Javelin", 10)) {
            this.item = ItemStack.of(nbt.getCompound("Javelin"));
        }

        setJavelinType(nbt.getString("JavelinType"));

        this.dealtDamage = nbt.getBoolean("DealtDamage");
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(this.item));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put("Javelin", this.item.save(new CompoundNBT()));
        nbt.putString("JavelinType", getJavelinType());
        nbt.putBoolean("DealtDamage", this.dealtDamage);
    }

    public void tickDespawn() {
        int loyalty = this.entityData.get(ID_LOYALTY);
        if (this.pickup != AbstractArrowEntity.PickupStatus.ALLOWED || loyalty <= 0) {
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

    @Override
    public ItemStack getPickupItem() {
        return item;
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Fantasia.ID, "textures/entity/javelin/" + getJavelinType() + ".png");
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static class Type {
        public static final Type WOODEN = new Type("wooden_javelin", -2f, 3, 4, null);
        public static final Type STONE = new Type("stone_javelin", -2.2f, 4, 5, null);
        public static final Type GOLD = new Type("gold_javelin", -2.2f, 4, 5, null);
        public static final Type IRON = new Type("iron_javelin", -2.2f, 4, 5, null);
        public static final Type DIAMOND = new Type("diamond_javelin", -2.2f, 4, 5, null);
        public static final Type NETHERITE = new Type("netherite_javelin", -2.2f, 4, 5, null);
        public static final Type WOLFRAMITE = new Type("wolframite_javelin", -2.2f, 4, 5, null);
        public static final Type GHASTLY = new Type("ghastly_javelin", -2.2f, 4, 5, null);
        public static final Type FROSTWORK = new Type("frostwork_javelin", -2.2f, 4, 5, null);
        public static final Type FROSTWORK_BOLT = new Type("frostwork_bolt", -2.2f, 4, 5, null);
        public static final Type SABER_TOOTH_JAVELIN = new Type("saber_tooth_javelin", -2.2f, 4, 5, null);

        static final List<Type> types = new ArrayList<>();
        private final String name;
        private final String textureName;
        private final float meeleeDamage;
        private final float meeleeAttackSpeed;
        private final float projectileDamage;
        private final IItemProvider repairMaterial;
        private final static Type DEFAULT = GOLD;

        Type(String name, String textureName, float meeleeDamage, float meeleeAttackSpeed, float projectileDamage, IItemProvider repairMaterial) {
            this.name = name;
            this.textureName = textureName;
            this.meeleeDamage = meeleeDamage;
            this.meeleeAttackSpeed = meeleeAttackSpeed;
            this.projectileDamage = projectileDamage;
            this.repairMaterial = repairMaterial;
            types.add(this);
        }

        Type(String name, float meeleeDamage, float meeleeAttackSpeed, float projectileDamage, IItemProvider repairMaterial) {
            this(name, name, meeleeDamage, meeleeAttackSpeed, projectileDamage, repairMaterial);
        }

        public static Type byName(String p_184981_0_) {
            for (Type type : types) {
                if (type.getName().equals(p_184981_0_)) {
                    return type;
                }
            }

            return DEFAULT;
        }

        public String getName() {
            return name;
        }

        public String getTextureName() {
            return textureName;
        }

        public float getMeeleeDamage() {
            return meeleeDamage;
        }

        public float getMeeleeAttackSpeed() {
            return meeleeAttackSpeed;
        }

        public float getProjectileDamage() {
            return projectileDamage;
        }

        public IItemProvider getRepairMaterial() {
            return repairMaterial;
        }
    }
}
