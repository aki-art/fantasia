package io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave;

import net.minecraft.util.IStringSerializable;

public enum SpeleothemStage implements IStringSerializable {

	TINY("tiny"),
	SMALLBASE("small_base"),
	SMALLTIP("small_tip"),
	TIP("tip"),
	MIDDLE("middle"),
	MIDDLE_ALT("middle_alt"),
	BASE("base");

	private final String name;
	
	public boolean isMiddlePiece() {
		return this == MIDDLE || this == MIDDLE_ALT;
	}
	
	SpeleothemStage(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}
