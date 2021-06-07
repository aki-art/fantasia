package io.github.akiart.fantasia.common.world.gen.util;

import net.minecraft.dispenser.IPosition;
import net.minecraft.util.math.vector.Vector3i;

import javax.annotation.concurrent.Immutable;

@Immutable
public class Vector2i
{
    private final int x;
    private final int z;

    public Vector2i(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Vector2i(Vector3i vec) {
        this.x = vec.getX();
        this.z = vec.getZ();
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public Vector3i toVector3i() {
        return new Vector3i(x, 0, z);
    }
}
