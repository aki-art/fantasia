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

    public static final class EntityTypes {
        public static final ITag.INamedTag<EntityType<?>> ACID_IMMUNE = createEntityTypeTag("acid_immune");
        public static final ITag.INamedTag<EntityType<?>> FROSTWORK = createEntityTypeTag("frostwork");
        public static final ITag.INamedTag<EntityType<?>> VALRAVN_VICTIM_BLACKLIST = createEntityTypeTag("valravn_victim_blacklist");
        public static final ITag.INamedTag<EntityType<?>> VALRAVN_ENTITY_PICKUP_BLACKLIST = createEntityTypeTag("valravn_victim_blacklist");
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

    private static ITag.INamedTag<EntityType<?>> createEntityTypeTag(String name) {
        return EntityTypeTags.bind(new ResourceLocation(Fantasia.ID, name).toString());
    }
}
