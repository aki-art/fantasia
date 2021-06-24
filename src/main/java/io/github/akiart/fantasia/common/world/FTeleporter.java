package io.github.akiart.fantasia.common.world;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Function;

public class FTeleporter implements ITeleporter {
    BlockPos pos;

    public FTeleporter(BlockPos pos) {
        super();
        this.pos = pos;
    }

    @Override
    @Nullable
    public PortalInfo getPortalInfo(Entity entity, ServerWorld destinationWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo)
    {
        BlockPos position = destinationWorld.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, destinationWorld.getSharedSpawnPos());
        Fantasia.LOGGER.info("teleporting to {}", position.toString());
        return new PortalInfo(Vector3d.atBottomCenterOf(position), entity.getDeltaMovement(), entity.yRot, entity.xRot);
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        entity.teleportToWithTicket(entity.getX(), entity.getY(), entity.getZ());
        return entity;
    }
}
