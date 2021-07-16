package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.FTags;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FEntities;
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

        tag(EntityTypeTags.IMPACT_PROJECTILES).add(
                FEntities.JAVELIN.get(),
                FEntities.ICICLE.get(),
                FEntities.PTARMIGAN_EGG.get()
        );

        tag(FTags.EntityTypes.VALRAVN_VICTIM_BLACKLIST).add(
                createOptionalTag("iceandfire", "dragon")
        );

        tag(FTags.EntityTypes.VALRAVN_ENTITY_PICKUP_BLACKLIST).add(
                FEntities.VALRAVN2.get()
        );

        tag(FTags.EntityTypes.FROSTWORK);

        tag(FTags.EntityTypes.ACID_IMMUNE).add(
                new ITag.TagEntry(FTags.EntityTypes.FROSTWORK.getName())
        );

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
    }

    private ITag.OptionalItemEntry createOptionalTag(String modid, String name) {
        return new ITag.OptionalItemEntry(new ResourceLocation(modid, name));
    }
}
