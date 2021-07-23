package io.github.akiart.fantasia.common.entity.projectile;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.util.FDamageSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
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

public class JavelinEntity extends AbstractYeetableEntity {

    public static final DataParameter<String> ID_TYPE = EntityDataManager.defineId(JavelinEntity.class, DataSerializers.STRING);

    protected int damageLeft;
    protected ResourceLocation texture = new ResourceLocation(Fantasia.ID, "textures/entity/javelin/wooden_javelin.png");

    // Used by living entities
    public JavelinEntity(EntityType<? extends JavelinEntity> type, World world, LivingEntity entity, ItemStack item) {
        super(type, world, entity, item);
        damageLeft = getPierceLevel() + 1;

        if (item.getItem().getRegistryName() != null) {
            setJavelinType(item.getItem().getRegistryName().getPath());
        }
    }

    // Used by dispensers
    public JavelinEntity(World world, double x, double y, double z, ItemStack item) {
        super(FEntities.JAVELIN.get(), x, y, z, world);
        damageLeft = getPierceLevel() + 1;

        if (item.getItem().getRegistryName() != null) {
            setJavelinType(item.getItem().getRegistryName().getPath());
        }
    }

    public String getJavelinType() {
        return entityData.get(ID_TYPE);
    }

    public void setJavelinType(String type) {
        entityData.set(ID_TYPE, type);
        texture = new ResourceLocation(Fantasia.ID, "textures/entity/javelin/" + type + ".png");
    }

    public JavelinEntity(EntityType<? extends JavelinEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ID_TYPE, "wooden_javelin");
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult target) {
        --damageLeft;
        super.onHitEntity(target);
    }

    @Override
    protected SoundEvent getHitEntitySoundEvent() {
        return SoundEvents.TRIDENT_HIT;
    }

    @Override
    protected void onHitEntityPost() {
        if (damageLeft <= 0) {
            doneDealingDamage = true;
            setDeltaMovement(getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        setJavelinType(nbt.getString("JavelinType"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("JavelinType", getJavelinType());
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Fantasia.ID, "textures/entity/javelin/" + getJavelinType() + ".png");
    }
}