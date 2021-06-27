package io.github.akiart.fantasia.common.world;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.block.Blocks;
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Function;

public class FTeleporter implements ITeleporter {
    BlockPos pos;

    boolean test;

    public FTeleporter(BlockPos pos) {
        super();
        this.pos = pos;
        test = false;
    }

    @Override
    @Nullable
    public PortalInfo getPortalInfo(Entity entity, ServerWorld destinationWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo)
    {
        BlockPos.Mutable position = destinationWorld.getSharedSpawnPos().mutable();
        int y = destinationWorld.getChunk(position).getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, position.getX(), position.getZ());
        position.setY(y);
        makeTestPortal(destinationWorld, position);
        return new PortalInfo(Vector3d.atBottomCenterOf(position), entity.getDeltaMovement(), entity.yRot, entity.xRot);
    }

    private void makeTestPortal(ServerWorld destinationWorld, BlockPos.Mutable position) {
        BlockPos pos = position.immutable();
        int counter = 0;
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                if(counter++ > 9) {
                    Fantasia.LOGGER.info("WTF");
                    return;
                }
                destinationWorld.setBlock(pos.offset(x, 0, z), Blocks.OBSIDIAN.defaultBlockState(), Constants.BlockFlags.DEFAULT);
            }
        }

        test = true;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        entity.teleportToWithTicket(entity.getX(), entity.getY(), entity.getZ());
        entity = repositionEntity.apply(false);
        return entity;
    }
}
