package io.github.akiart.fantasia.common.dispenser;

import io.github.akiart.fantasia.common.entity.projectile.IcicleEntity;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DispenseIcicleBehavior  extends ProjectileDispenseBehavior {
    @Override
    protected ProjectileEntity getProjectile(World world, IPosition position, ItemStack itemStack) {
        IcicleEntity entity = new IcicleEntity(world, position.x(), position.y(), position.z());
        entity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
        return entity;
    }
}
