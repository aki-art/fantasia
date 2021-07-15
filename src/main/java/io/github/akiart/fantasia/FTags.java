package io.github.akiart.fantasia;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;

// this needs a better place
public class FTags {
    public static final class Fluids {
        public static final ITag.INamedTag<Fluid> ACID = createFluidTag("acid");
    }

    public static final class Items {
        public static final ITag.INamedTag<Item> ACID_MELTABLE = createItemTag("acid_meltable");
    }


    private static ITag.INamedTag<Block> createBlockTag(String name) {
        return BlockTags.bind(new ResourceLocation(Fantasia.ID, name).toString());
    }

    private static ITag.INamedTag<Fluid> createFluidTag(String name) {
        return FluidTags.bind(new ResourceLocation(Fantasia.ID, name).toString());
    }

    private static ITag.INamedTag<Item> createItemTag(String name) {
        return ItemTags.bind(new ResourceLocation(Fantasia.ID, name).toString());
    }
}
