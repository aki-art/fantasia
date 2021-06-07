package io.github.akiart.fantasia.common.item.registrySet;

import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneSet;
import io.github.akiart.fantasia.common.item.ItemRegistryUtil;

public class StoneItemSet {
	
	private final StoneSet set;
	public final StoneRegistryItem raw;
	public final StoneRegistryItem cobble;
	public final StoneRegistryItem bricks;
	public final StoneRegistryItem cracked_bricks;
	public final StoneRegistryItem polished;
	public final StoneRegistryItem chiseled;
	
	public StoneItemSet(StoneSet set) {
		this.set = set;
		raw = registerItem(set.raw);
		cobble = registerItem(set.cobble);
		bricks = registerItem(set.bricks);
		cracked_bricks = registerItem(set.cracked_bricks);
		polished = registerItem(set.polished);
		chiseled = registerItem(set.chiseled);
	}
	
	private StoneRegistryItem registerItem(StoneRegistryObject stoneRegistryObject) {
		return stoneRegistryObject != null ? ItemRegistryUtil.registerStoneItems(stoneRegistryObject) : null;
	}
	
	public String getName() {
		return set.getName();
	}
	
	public boolean hasVariant(int variant) {
		return set.hasStoneVariant(variant);
	}

	public StoneSet getBlockSet() {
		return set;
	}
}
