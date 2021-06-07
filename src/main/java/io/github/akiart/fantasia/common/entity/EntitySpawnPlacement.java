package io.github.akiart.fantasia.common.entity;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Fantasia.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitySpawnPlacement {
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> evt) {
        //evt.getRegistry().registerAll(ALL.toArray(new EntityType<?>[0]));

        EntitySpawnPlacementRegistry.register(FEntities.PTARMIGAN.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EntitySpawnPlacement::canAnimalSpawn);

    }

    public static boolean canAnimalSpawn(EntityType<? extends AnimalEntity> animal, IWorld worldIn, SpawnReason
            reason, BlockPos pos, Random random) {
        BlockState target = worldIn.getBlockState(pos.below());
        boolean isLightEnough = worldIn.getRawBrightness(pos, 0) > 8;
        boolean canSpawnOnBlock = target.is(Blocks.GRASS_BLOCK) || target.is(FBlocks.FROZEN_DIRT.get());
        return true; //canSpawnOnBlock && isLightEnough;
    }
}
