package io.github.akiart.fantasia.common.world.gen.layer;

import io.github.akiart.fantasia.common.world.biome.FBiomes;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.AddIslandLayer;
import net.minecraft.world.gen.layer.RemoveTooMuchOceanLayer;
import net.minecraft.world.gen.layer.ZoomLayer;

import java.util.HashMap;
import java.util.function.LongFunction;

public class FLayerUtil {
    static HashMap<Integer, Integer> idMap;

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> processLayers(
            HashMap<Integer, Integer> biomeIDMap, LongFunction<C> lalc) {
        idMap = biomeIDMap;
        int biomeSize = 4;
        int riverSize = 5;

        // Place a few random tundras in a massive ocean
        IAreaFactory<T> baseLayer = FIslandLayer.INSTANCE.run(lalc.apply(1L));

        // Grow these areas
        baseLayer = ZoomLayer.FUZZY.run(lalc.apply(2L), baseLayer);

        // Add more layers, grow, add more, grow..
        baseLayer = AddIslandLayer.INSTANCE.run(lalc.apply(1L), baseLayer);
        baseLayer = ZoomLayer.NORMAL.run(lalc.apply(2001L), baseLayer);
        baseLayer = AddIslandLayer.INSTANCE.run(lalc.apply(2L), baseLayer);
        baseLayer = AddIslandLayer.INSTANCE.run(lalc.apply(50L), baseLayer);
        baseLayer = AddIslandLayer.INSTANCE.run(lalc.apply(70L), baseLayer);
        baseLayer = ZoomLayer.NORMAL.run(lalc.apply(2001L), baseLayer);

        // Shrink Oceans
        baseLayer = RemoveTooMuchOceanLayer.INSTANCE.run(lalc.apply(100L), baseLayer);

        baseLayer = AddIslandLayer.INSTANCE.run(lalc.apply(3L), baseLayer);

        // Time to grow those continents
        for (int i = 0; i < 8; ++i) {
            baseLayer = ZoomLayer.NORMAL.run(lalc.apply(1000 + i), baseLayer);
            if (i == 0) {
                baseLayer = AddIslandLayer.INSTANCE.run(lalc.apply(3L), baseLayer);
            }

            if (i == 1 || biomeSize == 1) {
               // baseLayer = FShoreLayer.INSTANCE.run(lalc.apply(1000L), baseLayer);
            }
        }

        return baseLayer;
    }

    protected static boolean isOcean(int biomeIn) {
        return biomeIn == idMap.get(FBiomes.FORLORN_SEA.ID);
    }

    protected static boolean hasNeighbour(int u, int d, int l, int r, int biome) {
        return u == biome || d == biome || l == biome || r == biome;
    }

    public static FLayer getLayer(long seed, HashMap<Integer, Integer> biomeIDMap) {
        IAreaFactory<LazyArea> iareafactory = processLayers(biomeIDMap, (seedModifier) -> new LazyAreaLayerContext(25, seed, seedModifier));
        return new FLayer(iareafactory);
    }
}
