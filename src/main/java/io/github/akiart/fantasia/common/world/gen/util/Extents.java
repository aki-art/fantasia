package io.github.akiart.fantasia.common.world.gen.util;

import com.google.common.base.MoreObjects;
import net.minecraft.util.math.CubeCoordinateIterator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class Extents implements Iterable<Vector3i> {

    private static final int MAX_SIZE = 999999; // trying to avoid frying thing
    public static final Logger LOGGER = LogManager.getLogger();

    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;

    private Vector3i center;

    public Extents(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;

        validate();
    }

    public Extents(Vector3i center, int xSpread, int ySpread, int zSpread) {
        this(center.getX() - xSpread,
                center.getY() - ySpread,
                center.getZ() - zSpread,
                center.getX() + xSpread,
                center.getY() + ySpread,
                center.getZ() + zSpread);
    }

    public Extents(Vector3i from, Vector3i to) {
        this(from.getX(),
                from.getY(),
                from.getZ(),
                to.getX(),
                to.getY(),
                to.getZ());
    }

    // Returns 0.5 coordinate if the center is halfway a block
    public Vector3f getCenter() {
        // TODO
        return null;
    }

    public boolean contains(Vector3i position) {
        return x1 <= position.getX() && x1 >= position.getX() &&
                y1 <= position.getY() && y2 >= position.getY() &&
                z1 <= position.getZ() && z2 >= position.getZ();
    }

    public void growToContain(Vector3i target) {
        x1 = Math.min(x1, target.getX());
        x2 = Math.max(x2, target.getX());
        y1 = Math.min(y1, target.getY());
        y2 = Math.max(y2, target.getY());
        z1 = Math.min(z1, target.getZ());
        z2 = Math.max(z2, target.getZ());

        constrainToWorld(true);
    }

    public void transform(Matrix4f transform) {
        // TODO
    }

    public boolean validate() {
        if (x1 < x2 && y1 < y2 && z1 < z2 && getSize() < MAX_SIZE)
            return true;

        LOGGER.warn("Extents is invalid: " + this.toString());
        return false;
    }

    public void constrainToWorld(boolean horizontalCheck) {
        y1 = MathHelper.clamp(y1, 0, y2);
        y2 = MathHelper.clamp(y2, y1, 255);

        if (!horizontalCheck) return;

        x1 = MathHelper.clamp(x1, Integer.MIN_VALUE, x2);
        x2 = MathHelper.clamp(x2, x2, Integer.MAX_VALUE);
        z1 = MathHelper.clamp(z1, Integer.MIN_VALUE, z2);
        z2 = MathHelper.clamp(z2, z2, Integer.MAX_VALUE);
    }

    public int getXSize() {
        return x2 - x1;
    }

    public int getYSize() {
        return y2 - y1;
    }

    public int getZSize() {
        return z2 - z1;
    }

    public int getSize() {
        return getXSize() * getYSize() * getZSize();
    }

    public void set(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;

        center = new Vector3i(x1 + getXSize() / 2f, y1 + getYSize() / 2f, z1 + getZSize() / 2f);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x1", this.x1)
                .add("y1", this.y1)
                .add("z1", this.z1)
                .add("x2", this.x2)
                .add("y2", this.y2)
                .add("z2", this.z2)
                .toString();
    }

    @Override
    public Iterator<Vector3i> iterator() {
        return new Iterator<Vector3i>() {
            private final CubeCoordinateIterator iter = new CubeCoordinateIterator(x1, y1, z1, x2, y2, z2);

            @Override
            public boolean hasNext() {
                return iter.advance();
            }

            @Override
            public Vector3i next() {
                return new Vector3i(iter.nextX(), iter.nextY(), iter.nextZ());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Cannot modify extents per coordinate, use Extents#set() instead.");
            }
        };
    }

    public void swallow(Extents extents) {
        x1 = Math.max(x1, extents.x1);
        x2 = Math.max(x2, extents.x2);
        y1 = Math.max(y1, extents.y1);
        y2 = Math.max(y2, extents.y2);
        z1 = Math.max(z1, extents.z1);
        z2 = Math.max(z2, extents.z2);
    }
}
