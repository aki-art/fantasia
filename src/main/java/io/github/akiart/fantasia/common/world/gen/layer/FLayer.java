package io.github.akiart.fantasia.common.world.gen.layer;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;

import java.util.HashMap;

public class FLayer {
    private final LazyArea area;

    public FLayer(IAreaFactory<LazyArea> lazyAreaFactoryIn) {
        this.area = lazyAreaFactoryIn.make();
    }

    private int getId(HashMap<Integer, Integer> map, int x, int z) {
        return map.get(area.get(x, z));
    }

    public Biome getBiome(Registry<Biome> biomeRegistry, int x, int z, HashMap<Integer, Integer> map) {
        int id = getId(map, x, z);
        Biome biome = biomeRegistry.get(BiomeRegistry.byId(id));

        if (biome == null) {
            return handleUnknownBiome(biomeRegistry, id);
        }

        return biome;
    }

    private Biome handleUnknownBiome(Registry<Biome> biomeRegistry, int id) {
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            throw Util.pauseInIde(new IllegalStateException("Unknown biome id: " + id));
        } else {
            Fantasia.LOGGER.warn("Unknown biome id: ", id);
            return biomeRegistry.get(BiomeRegistry.byId(0));
        }
    }
}
