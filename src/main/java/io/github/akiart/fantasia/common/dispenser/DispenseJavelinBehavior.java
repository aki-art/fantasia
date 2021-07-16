package io.github.akiart.fantasia.common.dispenser;

import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DispenseJavelinBehavior extends ProjectileDispenseBehavior {
    @Override
    protected ProjectileEntity getProjectile(World world, IPosition position, ItemStack itemStack) {
        JavelinEntity entity = new JavelinEntity(world, position.x(), position.y(), position.z(), itemStack);
        entity.pickup = AbstractArrowEntity.PickupStatus.ALLOWED;
        return entity;
    }
}
