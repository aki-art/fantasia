package io.github.akiart.fantasia.common.block.trees;

import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

import java.util.HashMap;
import java.util.Map;

public class StripMap {
    public static void registerStripMaps() {
        Map<Block, Block> stripMap = new HashMap<>(AxeItem.STRIPABLES);
        BlockRegistryUtil.getTrees().forEach(t -> t.setStripMaps(stripMap));
        AxeItem.STRIPABLES = stripMap;
    }
}
