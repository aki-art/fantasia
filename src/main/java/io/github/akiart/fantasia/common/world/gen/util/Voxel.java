package io.github.akiart.fantasia.common.world.gen.util;

import java.util.Objects;

public class Voxel {
    private int value;
    private int cachedValue;
    private boolean dirty = false;
    public static final Voxel EMPTY = new Voxel(-1);

    public Voxel(int value) {
        this.value = value;
    }

    public void setValue(int val) {
        setValue(val, false);
    }

    public void setValue(int val, boolean immediate) {
        if (immediate) {
            val = value;
            dirty = false;
        } else {
            cachedValue = val;
            dirty = true;
        }
    }

    public int get() {
        return value;
    }

    public int getCached() {
        return cachedValue;
    }

    public boolean isValid() {
        return value != -1;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty() {
        dirty = true;
    }

    public void apply() {
        if(dirty) {
            value = cachedValue;
            dirty = false;
        }
    }
}
