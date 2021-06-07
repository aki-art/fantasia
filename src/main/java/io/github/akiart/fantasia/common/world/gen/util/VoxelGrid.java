package io.github.akiart.fantasia.common.world.gen.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3i;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class VoxelGrid implements Iterable<Vector3i> {
    Extents extents;
    Vector3i offset;
    public static final int INVALID = Voxel.EMPTY.get();
    Map<Vector3i, Voxel> cells = Collections.emptyMap();

    @Override
    public Iterator<Vector3i> iterator() {
        return extents.iterator();
    }

    public VoxelGrid(Vector3i center, int size) {
        this.extents = new Extents(center, size, size, size); // working with cube for now
    }

    public VoxelGrid transform(Matrix4f transform) {
        // TODO
        return this;
    }

    public VoxelGrid applyToExtent(IVoxelTransform function, boolean immediate) {
        for (Vector3i pos : this) {
            set(pos, function.apply(pos), immediate);
        }

        return this;
    }

    public VoxelGrid offset(Direction direction, int n) {
        applyToExtent(pos -> get(pos.relative(direction.getOpposite(), n)), false);

        return this;
    }

    public VoxelGrid applyToActive(IVoxelTransform function) {
        for (Vector3i pos : cells.keySet()) {
            set(pos, function.apply(pos));
        }

        return this;
    }

    public VoxelGrid subtract(VoxelGrid other) {
        applyToActive(p -> other.cells.containsKey(p) ? INVALID : get(p));
        return this;
    }

    public VoxelGrid union(VoxelGrid other) {
        extents.swallow(other.extents);
        for (Vector3i pos : other.cells.keySet()) {
            if (!cells.containsKey(pos)) {
                set(pos, other.get(pos));
            }
        }
        return this;
    }

    public VoxelGrid intersect(VoxelGrid other) {
        extents.swallow(other.extents);
        for (Vector3i pos : cells.keySet()) {
            set(pos, other.cells.containsKey(pos) ? other.get(pos) : INVALID);
        }
        return this;
    }

    public VoxelGrid apply() {
        for (Vector3i pos : cells.keySet()) {
            Voxel voxel = cells.getOrDefault(pos, Voxel.EMPTY);
            voxel.apply();
            if (!voxel.isValid())
                cells.remove(pos);
        }

        return this;
    }

    public void set(Vector3i pos, int value) {
        set(pos, value, false);
    }

    public void set(Vector3i pos, int value, boolean immediate) {

        if (!extents.contains(pos)) return;

        if (value != INVALID) {
            cells.computeIfAbsent(pos, __ -> new Voxel(INVALID)).setValue(value, immediate);
        } else {
            cells.remove(pos);
        }
    }

    public int get(Vector3i pos) {
        return cells.getOrDefault(pos, Voxel.EMPTY).get();
    }

    public int getCached(Vector3i pos) {
        return cells.getOrDefault(pos, Voxel.EMPTY).getCached();
    }
}
