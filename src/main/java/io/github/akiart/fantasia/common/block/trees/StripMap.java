package io.github.akiart.fantasia.common.block.trees;

import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

import java.util.HashMap;
import java.util.Map;

public class StripMap {
    public static void registerStripMaps() {
        Map<Block, Block> stripMap = new HashMap<>(AxeItem.STRIPABLES);
        stripMap.put(FBlocks.FROZEN_ELM.log.get(), FBlocks.FROZEN_ELM.strippedLog.get());
        stripMap.put(FBlocks.FROZEN_ELM.wood.get(), FBlocks.FROZEN_ELM.strippedWood.get());
        stripMap.put(FBlocks.FROZEN_SPRUCE.log.get(), FBlocks.FROZEN_SPRUCE.strippedLog.get());
        stripMap.put(FBlocks.FROZEN_SPRUCE.wood.get(), FBlocks.FROZEN_SPRUCE.strippedWood.get());
        AxeItem.STRIPABLES = stripMap;
    }
}
