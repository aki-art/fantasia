package io.github.akiart.fantasia.common.entity;


import io.github.akiart.fantasia.Fantasia;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

public abstract class FTameableEntity extends TameableEntity {

    protected FTameableEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    protected ActionResultType tameEntity(PlayerEntity player, ItemStack itemstack, float chance) {
        if (!player.abilities.instabuild) itemstack.shrink(1);

        if (!this.isSilent())
            level.playSound(null, getX(), getY(), getZ(), SoundEvents.PARROT_EAT, getSoundSource(), 1.0F, 1.0F + (random.nextFloat() - random.nextFloat()) * 0.2F);

        if (!level.isClientSide) {
            if (random.nextFloat() < chance && !ForgeEventFactory.onAnimalTame(this, player)) {
                tame(player);
                level.broadcastEntityEvent(this, (byte) 7);
            } else {
                level.broadcastEntityEvent(this, (byte) 6);
            }
        }

        return ActionResultType.sidedSuccess(level.isClientSide);
    }

}
