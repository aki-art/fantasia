package io.github.akiart.fantasia.common.util;

import java.util.*;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Util;

import javax.annotation.Nullable;

public enum DirectionRestriction implements IStringSerializable {

	NONE("none", Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN),
	VERTICAL_ONLY("vertical_only", Direction.UP, Direction.DOWN),
	HORIZONAL_ONLY("horizontal_only", Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST),
	FLOOR_ONLY("floor_only", Direction.DOWN),
	NO_CEILING("no_ceiling", Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.DOWN),
	CEILING_ONGLY("ceiling_only", Direction.UP);

	List<Direction> allowed;
	private String name;

	public static final Codec<DirectionRestriction> CODEC = IStringSerializable.fromEnum(DirectionRestriction::values, DirectionRestriction::byName);

	private static final Map<String, DirectionRestriction> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(DirectionRestriction::getName, restriction -> restriction));

	DirectionRestriction(String name, Direction... allowedConnections) {
		allowed = Arrays.asList(allowedConnections);
		this.name = name;
	}

	@Nullable
	public static DirectionRestriction byName(String name) {
		return BY_NAME.get(name.toLowerCase(Locale.ROOT));
	}

	public String getName() {
		return this.name;
	}

	public boolean allowedDirection(Direction direction) {
		return allowed.contains(direction);
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	public Direction getRandomDirection(Random random) {
		return allowed.get(random.nextInt(allowed.size())).getOpposite();
	}
}
