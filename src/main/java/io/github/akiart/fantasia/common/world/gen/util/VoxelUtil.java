package io.github.akiart.fantasia.common.world.gen.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.CubeCoordinateIterator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;

import java.util.HashSet;
import java.util.Set;

// LOTS OF GARBAGE HERE, NEEDS CLEANING
public class VoxelUtil {

    public static void smooth(HashSet<Vector3i> positions, Vector3i from, Vector3i size) {
        CubeCoordinateIterator it = new CubeCoordinateIterator(from.getX(), from.getY(), from.getZ(), size.getX(), size.getY(), size.getZ());

        while (it.advance()) {
            Vector3i pos = new Vector3i(it.nextX(), it.nextY(), it.nextZ());
            int neighbours = getNeighbourCount(positions, pos);
            boolean isFilled = positions.contains(pos);

            if (isFilled && neighbours < 3)
                positions.remove(pos);
            else if (!isFilled && neighbours > 3)
                positions.add(pos);
        }
    }

    public static void smooth(HashSet<Vector3i> positions, Vector3i from, Vector3i size, int nTimes) {
        for (int i = 0; i < nTimes; i++) {
            smooth(positions, from, size);
        }
    }

    public static int getNeighbourCount(HashSet<? extends Vector3i> positions, Vector3i pos) {
        int count = 0;
        for (Direction dir : Direction.values())
            if (positions.contains(pos.relative(dir, 1))) count++;

        return count;
    }

    public static Set<Vector3i> getFilledCircle(float radius) {
        float d = radius * radius;
        Set<Vector3i> result = new HashSet<>();
        for(int x = MathHelper.floor(-radius); x < MathHelper.ceil(radius); x++) {
            for(int y = MathHelper.floor(-radius); y < MathHelper.ceil(radius); y++) {
                if(x * x + y * y <= d) {
                    result.add(new Vector3i(x, 0, y));
                }
            }
        }

        return result;
    }

    public static Set<Vector3i> getCircle(float radius, Vector3i center) {
        int i = 0;
        float num3 = 1f - radius;
        Set<Vector3i> result = new HashSet<>();
        while (radius >= i)
        {
            result.add(new Vector3i(radius + center.getX(), center.getY(), i + center.getZ()));
            result.add(new Vector3i(i + center.getX(), center.getY(), radius + center.getZ()));
            result.add(new Vector3i(-radius + center.getX(), center.getY(), i + center.getZ()));
            result.add(new Vector3i(-i + center.getX(), center.getY(), radius + center.getZ()));
            result.add(new Vector3i(-radius + center.getX(), center.getY(), -i + center.getZ()));
            result.add(new Vector3i(-i + center.getX(), center.getY(), -radius + center.getZ()));
            result.add(new Vector3i(radius + center.getX(), center.getY(), -i + center.getZ()));
            result.add(new Vector3i(i + center.getX(), center.getY(), -radius + center.getZ()));

            ++i;

            if (num3 < 0)
            {
                num3 += 2 * i + 1;
            } else
            {
                --radius;
                num3 += 2 * (i - radius) + 1;
            }
        }
        return result;
    }


    public static HashSet<Vector3i> projectDown(HashSet<Vector3i> positions, Vector3i from, Vector3i to) {
        HashSet<Vector3i> result = new HashSet<>();

        for(int x = from.getX(); x < to.getX(); x++) {
            for(int z = from.getZ(); z < to.getZ(); z++) {
                projectColumn(positions, result, x, z, from.getY(), to.getY());
            }
        }

        return result;
    }

    private static void projectColumn(HashSet<Vector3i> positions, HashSet<Vector3i> result, int x, int z, int from, int to) {
        for(int y = from; y < to; y++) {
            Vector3i pos = new Vector3i(x, y, z);
            if(positions.contains(pos)) {
                result.add(new Vector3i(x, 0, z));
                return;
            }
        }
    }
}
