package io.github.akiart.fantasia.common.world.gen.layer;

import io.github.akiart.fantasia.common.world.biome.FBiomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum FShoreLayer implements ICastleTransformer  {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {

        if(!FLayerUtil.isOcean(center)) {
            if(FLayerUtil.hasNeighbour(south, south, east, north, FBiomes.FORLORN_SEA.ID))
                return FBiomes.BASALT_BEACH.ID;
        }

        return center;
    }
}
