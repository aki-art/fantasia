package io.github.akiart.fantasia.common.world.gen.layer;

import io.github.akiart.fantasia.common.world.biome.FBiomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum FIslandLayer implements IAreaTransformer0 {
    INSTANCE;

    public int applyPixel(INoiseRandom rand, int x, int y) {
        if (x == 0 && y == 0) {
            return FBiomes.ARCTIC_TUNDRA.ID;
        } else {
            return rand.nextRandom(5) == 0 ? FBiomes.ARCTIC_TUNDRA.ID : FBiomes.FORLORN_SEA.ID;
        }
    }
}
