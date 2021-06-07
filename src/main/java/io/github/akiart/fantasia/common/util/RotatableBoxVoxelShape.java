package io.github.akiart.fantasia.common.util;

import net.minecraft.block.Block;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public class RotatableBoxVoxelShape {

	public final VoxelShape north;
	public final VoxelShape south;
	public final VoxelShape east;
	public final VoxelShape west;
	public final VoxelShape down;
	public final VoxelShape up;

	private RotatableBoxVoxelShape(double x1, double y1, double z1, double x2, double y2, double z2) {
		down = Block.box(x1, y1, z1, x2, y2, z2);
		up = Block.box(x1, 16D - y1, z1, x2, 16D - y2, z2);
		north = Block.box(z1, x1, 16D - y1, z2, x2, 16D - y2);
		south = Block.box(z1, x1, y1, z2, x2, y2);
		west = Block.box(16D - y1, x1, z1, 16D - y2, x2, z2);
		east = Block.box(y1, x1, z1, y2, x2, z2);
	}

	public static RotatableBoxVoxelShape create(double x1, double y1, double z1, double x2, double y2, double z2) {
		return new RotatableBoxVoxelShape(x1, y1, z1, x2, y2, z2);
	}

	public static RotatableBoxVoxelShape createXZSymmetric(double xInset, double zInset, double y1, double y2) {
		return new RotatableBoxVoxelShape(xInset, y1, zInset, 16D - xInset, y2, 16D - zInset);
	}

	public static RotatableBoxVoxelShape createXZSymmetric(double inset, double y1, double y2) {
		return new RotatableBoxVoxelShape(inset, y1, inset, 16D - inset, y2, 16D - inset);
	}

	public VoxelShape getForDirection(Direction direction) {
		switch (direction) {
		case EAST:
			return east;
		case WEST:
			return west;
		case NORTH:
			return north;
		case SOUTH:
		default:
			return south;
		}
	}

	public VoxelShape getForAxis(Direction.Axis axis) {
		switch (axis) {
		case X:
			return south;
		case Y:
			return down;
		case Z:
		default:
			return west;
		}
	}

	public VoxelShape get(AttachFace facing, Direction direction) {
		switch (facing) {
		case FLOOR:
			return down;
		case CEILING:
			return up;
		case WALL:
		default:
			return getForDirection(direction);
		}
	}
}