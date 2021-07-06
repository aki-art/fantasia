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

public class FrostworkPickaxeProjectileEntity extends ThrowableEntity {

    private final static int EXPLOSION_DELAY = 8;
    public static final String ID = "frostwork_pickaxe_projectile";

    private int timer = 0;
    private int power = 5;
    private int progress = 0;

    public static final DataParameter<Integer> ID_POWER = EntityDataManager.defineId(FrostworkPickaxeProjectileEntity.class, DataSerializers.INT);
    public static final DataParameter<Integer> ID_PROGRESS = EntityDataManager.defineId(FrostworkPickaxeProjectileEntity.class, DataSerializers.INT);

    public FrostworkPickaxeProjectileEntity(World world) {
        super(FEntities.FROSTWORK_PICKAXE_PROJECTILE.get(), world);

        Fantasia.LOGGER.info("he spawn");
    }

    public FrostworkPickaxeProjectileEntity(EntityType<FrostworkPickaxeProjectileEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {

        Fantasia.LOGGER.info("he tick");
        if (progress >= power) {
            remove();
        } else if (timer++ > EXPLOSION_DELAY) {
            Fantasia.LOGGER.info("he boom");
            timer = 0;
            level.explode(getOwner(), getX(), getY(), getZ(), 5F, Explosion.Mode.BREAK);
            progress++;
        }

        super.tick();
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return this.power;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return this.progress;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(ID_POWER, 5);
        entityData.define(ID_PROGRESS, 0);
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        nbt.putInt("Power", getPower());
        nbt.putInt("Progress", getProgress());
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        setPower(nbt.getInt("Power"));
        setProgress(nbt.getInt("Progress"));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void checkInsideBlocks() {
        // don't care
    }

}
