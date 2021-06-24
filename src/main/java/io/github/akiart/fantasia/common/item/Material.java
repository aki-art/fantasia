package io.github.akiart.fantasia.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;

import java.util.HashMap;
import java.util.Map;

public class Material {

    public static final Material IRON = new Material("iron", ItemTier.IRON);
    public static final Material GHASTLY = new Material("ghastly", FItemTier.GHASTLY);
    public static final Material NETHERITE = new Material("netherite", ItemTier.NETHERITE);
    public static final Map<String, Material> materials = new HashMap<>();

    IItemTier tier;
    String name;

    public Material(String name, IItemTier tier) {
        this.name = name;
        this.tier = tier;
        materials.put(name, this);
    }

    public static Material getByName(String name) {
        return materials.get(name);
    }

    public String getName() {
        return name;
    }

    public static Material getMaterialForItemTier(IItemTier tier) {
        for(Material mat : materials.values()) {
            if(mat.tier.equals(tier)) {
                return mat;
            }
        }

        return null;
    }
}
