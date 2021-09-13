package io.github.akiart.fantasia.dataGen;


import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public FEntityTypeTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Fantasia.ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        tag(FTags.EntityTypes.ACID_IMMUNE).add(
                EntityType.ARMOR_STAND,
                EntityType.IRON_GOLEM,
                EntityType.GUARDIAN,
                EntityType.ELDER_GUARDIAN,
                EntityType.STRIDER
                // FEntities.MUCKFISH.get()
        );

        tag(FTags.EntityTypes.ACID_IMMUNE).add(
                createOptionalTag("iceandfire", "hydra")
        );

        tag(FTags.EntityTypes.VALRAVN_VICTIM_BLACKLIST).add(
                createOptionalTag("iceandfire", "dragon")
        );

        tag(FTags.EntityTypes.DRUID_HOOD_AFFECTED).add(
                EntityType.BAT,
                EntityType.BEE,
                EntityType.CAT,
                EntityType.CAVE_SPIDER,
                EntityType.CHICKEN,
                EntityType.COD,
                EntityType.COW,
                EntityType.DOLPHIN,
                EntityType.DONKEY,
                EntityType.FOX,
                EntityType.HOGLIN,
                EntityType.HORSE,
                EntityType.LLAMA,
                EntityType.MOOSHROOM,
                EntityType.MULE,
                EntityType.OCELOT,
                EntityType.PANDA,
                EntityType.PARROT,
                EntityType.PIG,
                EntityType.POLAR_BEAR,
                EntityType.PUFFERFISH,
                EntityType.RABBIT,
                EntityType.SALMON,
                EntityType.SHEEP,
                EntityType.SILVERFISH,
                EntityType.SPIDER,
                EntityType.SQUID,
                EntityType.TRADER_LLAMA,
                EntityType.TROPICAL_FISH,
                EntityType.TURTLE,
                EntityType.WOLF
        );
    }

    private ITag.OptionalItemEntry createOptionalTag(String modid, String name) {
        return new ITag.OptionalItemEntry(new ResourceLocation(modid, name));
    }
}