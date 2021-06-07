package io.github.akiart.fantasia.common.util;

import java.util.Arrays;
import java.util.HashSet;

import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public enum DirectionRestriction implements IStringSerializable {

	NONE("none", Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN),
	VERTICAL_ONLY("vertical_only", Direction.UP, Direction.DOWN),
	HORIZONAL_ONLY("horizontal_only", Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST),
	FLOOR_ONLY("floor_only", Direction.DOWN),
	NO_CEILING("no_ceiling", Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.DOWN),
	CEILING_ONGLY("ceiling_only", Direction.UP);

	HashSet<Direction> allowed;
	private String name;

	DirectionRestriction(String name, Direction... allowedConnections) {
		allowed = new HashSet<>(Arrays.asList(allowedConnections));
		this.name = name;
	}

	public boolean allowedDirection(Direction direction) {
		return allowed.contains(direction);
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
