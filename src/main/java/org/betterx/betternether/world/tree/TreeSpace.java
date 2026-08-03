package org.betterx.betternether.world.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

import org.joml.Vector3f;

/**
 * Where a tree is being grown and how much room it has.
 *
 * <h2>Why the room matters</h2>
 * On this game version feature reads and writes are not restricted to a Mojang write zone, so tree
 * geometry is intentionally left unbounded.
 *
 * @param origin the block the tree grows from - tree-local {@code (0, 0, 0)}
 */
public record TreeSpace(BlockPos origin) {
    public static TreeSpace of(LevelAccessor level, BlockPos origin) {
        return new TreeSpace(origin);
    }

    /**
     * An unbounded space, for tests and for growth in a live level.
     */
    public static TreeSpace unbounded(BlockPos origin) {
        return new TreeSpace(origin);
    }

    /**
     * {@code radius} shrunk to what fits around a tree-local point, or a negative number when not even
     * {@code minRadius} fits.
     */
    public float fitRadius(Vector3f localCentre, float radius, float minRadius) {
        return radius;
    }

    /**
     * {@code end} pulled back along the segment until a branch of the given radius fits. Direction is
     * preserved, so a radial fan of branches keeps its angles.
     */
    public Vector3f fitSegment(Vector3f start, Vector3f end, float radius) {
        return new Vector3f(end);
    }

    public boolean canWrite(int localX, int localZ) {
        return true;
    }
}
