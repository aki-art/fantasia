package io.github.akiart.fantasia.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class FDamageSource {
    public static final DamageSource ACID = new DamageSource("acid").bypassArmor();

    public static DamageSource javelin(Entity entity, @Nullable Entity owner) {
        return (new IndirectEntityDamageSource("javelin", entity, owner)).setProjectile();
    }
}
