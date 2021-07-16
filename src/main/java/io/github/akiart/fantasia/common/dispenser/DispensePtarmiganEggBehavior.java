package io.github.akiart.fantasia.common.dispenser;

import io.github.akiart.fantasia.common.entity.projectile.PtarmiganEggEntity;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class DispensePtarmiganEggBehavior extends ProjectileDispenseBehavior {
    @Override
    protected ProjectileEntity getProjectile(World world, IPosition position, ItemStack itemStack) {
        return Util.make(new PtarmiganEggEntity(world, position.x(), position.y(), position.z()), (entity) -> {
            entity.setItem(itemStack);
        });
    }
}
