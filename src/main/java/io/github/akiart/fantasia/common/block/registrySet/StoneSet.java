package io.github.akiart.fantasia.common.block.registrySet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import net.minecraft.block.material.MaterialColor;

public class StoneSet {

	public final StoneRegistryObject raw;
	public final StoneRegistryObject cobble;
	public final StoneRegistryObject bricks;
	public final StoneRegistryObject cracked_bricks;
	public final StoneRegistryObject polished;
	public final StoneRegistryObject chiseled;

	private final int stoneVariants;
	private final String NAME;

	public StoneSet(String name, float hardness, float resistance, MaterialColor color, int stoneFlag) {
		stoneVariants = stoneFlag | Variant.RAW;
		
		raw = BlockRegistryUtil.registerStones(name, hardness, resistance, color, true);

		chiseled = registerSet(Variant.CHISELED, "chiseled_" + name, false, hardness, resistance, color);
		bricks = registerSet(Variant.BRICKS, name + "_bricks", false, hardness, resistance, color);
		cobble = registerSet(Variant.COBBLE, "cobbled_" + name, false, hardness, resistance, color);
		cracked_bricks = registerSet(Variant.CRACKED_BRICKS, "cracked_" + name + "_bricks", false, hardness, resistance, color);
		polished = registerSet(Variant.POLISHED, "polished_" + name, false, hardness, resistance, color);

		this.NAME = name;
	}

	private StoneRegistryObject registerSet(int flag, String name, boolean redstone, float hardness, float resistance, MaterialColor color) {
		return hasFlag(stoneVariants, flag) ? BlockRegistryUtil.registerStones(name, hardness, resistance, color, false) : null;
	}

	public String getName() {
		return NAME;
	}

	public boolean hasStoneVariant(int variant) {
		return hasFlag(stoneVariants, variant);
	}

	private boolean hasFlag(int set, int flag) {
		return (set & flag) == flag;
	}

	public List<StoneRegistryObject> getItems() {
		List<StoneRegistryObject> result = Arrays.asList(cobble, polished, bricks, cracked_bricks, chiseled)
			.stream()
			.filter(b -> b != null)
			.collect(Collectors.toList());
		result.add(raw);
		
		return result;
	}
	
	public class Variant {
		public static final int NONE = 0, 
			RAW = 1 << 0, 
			COBBLE = 1 << 1, 
			POLISHED = 1 << 2, 
			BRICKS = 1 << 3,
			CRACKED_BRICKS = 1 << 4, 
			CHISELED = 1 << 5, 
			ALL = ~(-1 << 6), 
			STANDARD = RAW | COBBLE | BRICKS | POLISHED;
	}
}
