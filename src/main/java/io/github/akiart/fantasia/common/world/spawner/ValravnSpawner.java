package io.github.akiart.fantasia.common.world.spawner;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;

import java.util.Iterator;
import java.util.List;

public class ValravnSpawner implements ISpecialSpawner  {

    @Override
    public int tick(ServerWorld world, boolean spawnEnemies, boolean spawnFriendlies) {
        if (!spawnEnemies) {
            return 0;
        }
        else {
            for(PlayerEntity playerentity : world.players()) {
                AxisAlignedBB axisalignedbb = AxisAlignedBB.unitCubeFromLowerCorner(playerentity.position()).inflate(30, 30, 30);
                List<MobEntity> list = world.getLoadedEntitiesOfClass(AnimalEntity.class, axisalignedbb);
                for(MobEntity entity : list) {
                    if (entity.isBaby()) {
                       // Fantasia.LOGGER.info("bebe spotted!!");
                    }
                }

            }
        }

        return 0;
    }
}
