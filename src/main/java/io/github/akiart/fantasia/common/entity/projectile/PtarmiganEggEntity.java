package io.github.akiart.fantasia.common.entity.projectile;

import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.passive.PtarmiganEntity;
import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnMobPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.network.NetworkHooks;

public class PtarmiganEggEntity extends ProjectileItemEntity {
    private final static int HATCH_CHANCE = 8;
    public static final String ID = "ptarmigan_egg";

    public PtarmiganEggEntity(EntityType<? extends PtarmiganEggEntity> entityType, World world) {
        super(entityType, world);
    }

    public PtarmiganEggEntity(World world, PlayerEntity player) {
        super(FEntities.PTARMIGAN_EGG.get(), player, world);
    }

    public PtarmiganEggEntity(World world, double x, double y, double z) {
        super(FEntities.PTARMIGAN_EGG.get(), x, y, z, world);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte flag) {
        if (flag == 3) {
            for (int i = 0; i < 8; ++i) {
                this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public PtarmiganEntity spawnEntity() {
        PtarmiganEntity babyEntity = FEntities.PTARMIGAN.get().create(this.level);
        if (babyEntity == null) return null;
        babyEntity.setAge(-24000);
        babyEntity.setRandomColor();
        babyEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);

        return babyEntity;
    }

    @Override
    protected void onHit(RayTraceResult rayTraceResult) {
        super.onHit(rayTraceResult);
        if (!this.level.isClientSide) {
            if (this.random.nextInt(HATCH_CHANCE) == 0) {
                this.level.addFreshEntity(spawnEntity());
            }

            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove();
        }
    }

    protected Item getDefaultItem() {
        return Items.EGG;
    }
}
