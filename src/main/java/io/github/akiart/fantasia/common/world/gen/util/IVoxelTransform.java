package io.github.akiart.fantasia.common.world.gen.util;

import net.minecraft.util.math.vector.Vector3i;

@FunctionalInterface
public interface IVoxelTransform {
    public int apply(Vector3i position);
}
