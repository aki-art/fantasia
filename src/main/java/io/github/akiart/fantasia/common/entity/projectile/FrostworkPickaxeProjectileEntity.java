package io.github.akiart.fantasia.common.entity.projectile;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

// This is an invisible entity which controls the explosions released from the
// Frostwork Pickaxe.

public class FrostworkPickaxeProjectileEntity extends ThrowableEntity {

    private static final int EXPLOSION_DELAY = 6;
    private static final int FIRST_EXPLOSION_START = -4;
    public static final String ID = "frostwork_pickaxe_projectile";

    private int timer;
    private int power = 5;
    private int explosionCounter = 0;

    public static final DataParameter<Integer> ID_POWER = EntityDataManager.defineId(FrostworkPickaxeProjectileEntity.class, DataSerializers.INT);
    public static final DataParameter<Integer> ID_PROGRESS = EntityDataManager.defineId(FrostworkPickaxeProjectileEntity.class, DataSerializers.INT);

    public FrostworkPickaxeProjectileEntity(World world) {
        this(FEntities.FROSTWORK_PICKAXE_PROJECTILE.get(), world);
    }

    public FrostworkPickaxeProjectileEntity(EntityType<FrostworkPickaxeProjectileEntity> type, World world) {
        super(type, world);
        timer = FIRST_EXPLOSION_START;
        noPhysics = true;
        setNoGravity(true);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public void tick() {
        if (explosionCounter >= power) {
            remove();
        } else if (timer++ > EXPLOSION_DELAY) {
            timer = 0;
            level.explode(getOwner(), getX(), getY(), getZ(), 5F, Explosion.Mode.BREAK);
            explosionCounter++;
        }

        super.tick();
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return this.power;
    }

    public void setExplosionCounter(int explosionCounter) {
        this.explosionCounter = explosionCounter;
    }

    public int getExplosionCounter() {
        return this.explosionCounter;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(ID_POWER, 5);
        entityData.define(ID_PROGRESS, 0);
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        nbt.putInt("Power", getPower());
        nbt.putInt("Progress", getExplosionCounter());
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        setPower(nbt.getInt("Power"));
        setExplosionCounter(nbt.getInt("Progress"));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
