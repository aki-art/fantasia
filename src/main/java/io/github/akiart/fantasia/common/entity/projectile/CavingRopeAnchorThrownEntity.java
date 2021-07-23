package io.github.akiart.fantasia.common.entity.projectile;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class CavingRopeAnchorThrownEntity extends ProjectileItemEntity {
    public static final String ID = "caving_anchor_projectile";
    private final static SoundEvent FALL_SOUND = SoundEvents.LEASH_KNOT_PLACE;
    boolean hit = false; // serialize this

    @Nullable
    private BlockState lastState;

    public CavingRopeAnchorThrownEntity(EntityType<? extends CavingRopeAnchorThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public CavingRopeAnchorThrownEntity(World world, PlayerEntity player) {
        super(FEntities.CAVING_ANCHOR_PROJECTILE.get(), player, world);
    }

    public CavingRopeAnchorThrownEntity(World world, double x, double y, double z) {
        super(FEntities.CAVING_ANCHOR_PROJECTILE.get(), x, y, z, world);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHit(RayTraceResult rayTraceResult) {
        super.onHit(rayTraceResult);
        hit = true;
    }

    protected void onHitBlock(BlockRayTraceResult rayTraceResult) {
        super.onHitBlock(rayTraceResult);

        if(!level.isClientSide && !hit) {
            Direction direction = rayTraceResult.getDirection();
            Fantasia.LOGGER.info(direction.toString());
            Fantasia.LOGGER.info("laststate {}", lastState == null);
            lastState = level.getBlockState(rayTraceResult.getBlockPos());
            BlockPos arrivedAtPos = rayTraceResult.getBlockPos().relative(direction);
            if (lastState != null && lastState.entityCanStandOnFace(level, rayTraceResult.getBlockPos(), this, direction)) {

                Fantasia.LOGGER.info("-");
                level.setBlock(
                        arrivedAtPos,
                        FBlocks.CAVING_ROPE_ANCHOR.get().defaultBlockState(),
                        Constants.BlockFlags.DEFAULT);
                playSound(SoundEvents.LANTERN_PLACE, 1f, 1f);
            } else {
                spawnAtLocation(getDefaultItem());
            }

            remove(); // TODO: reel back to player if first
        }
    }



    @Override
    protected Item getDefaultItem() {
        return FItems.CAVING_ROPE_ANCHOR.get();
    }
}
