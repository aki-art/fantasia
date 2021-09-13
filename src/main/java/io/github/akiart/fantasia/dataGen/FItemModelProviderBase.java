package io.github.akiart.fantasia.dataGen;


import io.github.akiart.fantasia.Fantasia;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class FItemModelProviderBase extends ItemModelProvider {
    public FItemModelProviderBase(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Fantasia.ID, existingFileHelper);
    }

    protected String getName(ForgeRegistryEntry<?> entry) {
        return entry.getRegistryName().getPath();
    }

    protected ResourceLocation getBlockLocation(String name) {
        return new ResourceLocation(Fantasia.ID, "block/" + name);
    }

    protected ResourceLocation getItemLocation(String name) {
        return new ResourceLocation(Fantasia.ID, "item/" + name);
    }

    // generate a simple item using a texture provided in the item folder
    protected ItemModelBuilder generate(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    protected ResourceLocation getItemTexture(Item item) {
        ResourceLocation name = item.getRegistryName();
        return new ResourceLocation(name.getNamespace(), "item/" + name.getPath());
    }

    protected ItemModelBuilder blockGenerated(BlockItem block) {
        ItemModelBuilder builder = withExistingParent(getName(block), "item/generated");
        builder = builder.texture("layer0", getBlockLocation(getName(block.getBlock())));
        return builder;
    }

    protected void coinItem(Item item) {
        ResourceLocation count = new ResourceLocation(Fantasia.ID, "count");

        generate(getName(item), getItemTexture(item))
                .override()
                .predicate(count, 0.33f)
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Fantasia.ID, "item/" + getName(item) + "_1"))).end()

                .override()
                .predicate(count, 0.66f)
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Fantasia.ID, "item/" + getName(item) + "_2"))).end()

                .override()
                .predicate(count, 1f)
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Fantasia.ID, "item/" + getName(item) + "_3"))).end();

        for (int i = 1; i <= 3; i++) {
            generate(getName(item) + "_" + i, new ResourceLocation(Fantasia.ID, "item/" + getName(item) + "_" + i));
        }
    }
}
