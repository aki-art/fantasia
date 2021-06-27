package io.github.akiart.fantasia.common.block;

import io.github.akiart.fantasia.Fantasia;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.block.WoodType;
import net.minecraft.util.ResourceLocation;

import java.util.Set;
import java.util.stream.Stream;

public class FWoodType {
    private static final Set<WoodType> VALUES = new ObjectArraySet<>();

    public static final WoodType FROZEN_ELM = create("frozen_elm");
    public static final WoodType FROZEN_SPRUCE = create("frozen_spruce");
    public static final WoodType ELM = create("elm");

    protected static WoodType create(String name) {
        WoodType type = WoodType.register(WoodType.create(new ResourceLocation(Fantasia.ID, name).toString()));
        VALUES.add(type);
        return type;
    }

    public static Stream<WoodType> values() {
        return VALUES.stream();
    }
}
