package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.blockType.FChestBlock;
import io.github.akiart.fantasia.common.block.registrySet.CrystalRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.AbstractTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.BasicTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.ThinTreeRegistryObject;
import io.github.akiart.fantasia.common.item.registrySet.CrystalRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.StoneRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.tree.AbstractTreeRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.tree.BasicTreeRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.tree.ThinTreeRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class FItemModelProviderBase extends ItemModelProvider {

    public FItemModelProviderBase(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    protected ResourceLocation getBlockTexture(Block block) {
        ResourceLocation name = block.getRegistryName();
        return new ResourceLocation(name.getNamespace(), "block/" + name.getPath());
    }

    protected ResourceLocation getItemTexture(Item item) {
        ResourceLocation name = item.getRegistryName();
        return new ResourceLocation(name.getNamespace(), "item/" + name.getPath());
    }


    protected ResourceLocation getBlockLocation(String name) {
        return new ResourceLocation(Fantasia.ID, "block/" + name);
    }

    protected ResourceLocation getItemLocation(String name) {
        return new ResourceLocation(Fantasia.ID, "item/" + name);
    }

    protected String getName(ForgeRegistryEntry<?> entry) {
        return entry.getRegistryName().getPath();
    }

    protected ItemModelBuilder generate(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    protected ItemModelBuilder blockGenerated(BlockItem block) {
        ItemModelBuilder builder = withExistingParent(getName(block), "item/generated");
        builder = builder.texture("layer0", getBlockLocation(getName(block.getBlock())));
        return builder;
    }

    protected void fromBlock(Block block) {
        withExistingParent(getName(block), getBlockLocation(getName(block)));
    }

    protected void buttonInventory(Block block, ResourceLocation texture) {
        ResourceLocation parent = new ResourceLocation("block/button_inventory");
        withExistingParent(getName(block), parent).texture("texture", texture);
    }

    protected void fOreInventory(Block ore, Block baseBlock) {
        cubeAll(getName(ore), getBlockLocation(getName(baseBlock) + "_" + getName(ore)));
    }

    protected void stone(StoneRegistryItem items) {
        StoneRegistryObject blocks = items.getStoneRegistryObject();
        ResourceLocation texture = getBlockTexture(blocks.block.get());

        fromBlock(blocks.block.get());
        stairs(getName(items.stairs.get()), texture, texture, texture);
        wallInventory(getName(items.wall.get()), texture);
        slab(getName(items.slab.get()), texture, texture, texture);

        if (items.hadRedstoneComponents()) {
            buttonInventory(blocks.button.get(), texture);
            fromBlock(blocks.pressurePlate.get());
        }
    }

    protected void crystal(CrystalRegistryItem items) {
        CrystalRegistryObject blocks = items.getCrystal();

        fromBlock(blocks.block.get());
        fromBlock(blocks.polished.get());
        fromBlock(blocks.lens.get());
        fromBlock(blocks.waxedLens.get());
        miscItem(items.crystal);
        // blockGenerated(blocks.lantern.get());
    }

    protected <T extends AbstractTreeRegistryItem<? extends AbstractTreeRegistryObject>> void tree(T items) {

        AbstractTreeRegistryObject tree = items.getTree();

        ResourceLocation plankTex = getBlockTexture(tree.getPlanks().get());

        fromBlock(tree.getPlanks().get());

        miscItem(items.sign);
        miscItem(items.door);

        stairs(getName(items.stairs.get()), plankTex, plankTex, plankTex);
        slab(getName(items.slab.get()), plankTex, plankTex, plankTex);

        buttonInventory(tree.getButton().get(), plankTex);
        fromBlock(tree.getPressurePlate().get());

        fenceInventory(getName(items.fence.get()), plankTex);
        fenceGate(getName(items.fenceGate.get()), plankTex);

        if(tree instanceof BasicTreeRegistryObject) {
            BasicTreeRegistryObject basicTree = (BasicTreeRegistryObject)tree;
            BasicTreeRegistryItem basicItem = (BasicTreeRegistryItem)items;

            ResourceLocation logTex = getBlockTexture(basicTree.log.get());
            ResourceLocation lotTopTex = getBlockLocation(tree.getName() + "_log_top");
            ResourceLocation strippedTex = getBlockTexture(basicTree.strippedLog.get());
            ResourceLocation strippedTopTex = getBlockLocation(tree.getName() + "_stripped_log_top");

            blockGenerated(basicItem.sapling.get());

            cubeColumn(getName(basicItem.log.get()), logTex, lotTopTex);
            cubeColumn(getName(basicItem.strippedLog.get()), strippedTex, strippedTopTex);

            fromBlock(basicTree.wood.get());
            fromBlock(basicTree.strippedWood.get());
            fromBlock(basicTree.leaves.get());
        }
        else if(tree instanceof ThinTreeRegistryObject) {
            ThinTreeRegistryObject basicTree = (ThinTreeRegistryObject)tree;
            ThinTreeRegistryItem basicItem = (ThinTreeRegistryItem)items;

            ResourceLocation logTex = getBlockTexture(basicTree.log.get());
            ResourceLocation lotTopTex = getBlockLocation(tree.getName() + "_log_top");
            ResourceLocation strippedTex = getBlockTexture(basicTree.strippedLog.get());
            ResourceLocation strippedTopTex = getBlockLocation(tree.getName() + "_stripped_log_top");

            blockGenerated(basicItem.sapling.get());

            cubeColumn(getName(basicItem.log.get()), logTex, lotTopTex);
            cubeColumn(getName(basicItem.strippedLog.get()), strippedTex, strippedTopTex);

            fromBlock(basicTree.wood.get());
            fromBlock(basicTree.strippedWood.get());
            fromBlock(basicTree.leaves.get());
        }
    }

    protected void chest(FChestBlock block, ResourceLocation particle) {
        withExistingParent(getName(block), new ResourceLocation("builtin/entity")).texture("particle", particle);
    }

    public void miscItem(RegistryObject<? extends Item> item) {
        if(item.get() instanceof SpawnEggItem) {
            withExistingParent(getName(item.get()), new ResourceLocation("item/template_spawn_egg"));
        }
        else {
            generate(getName(item.get()), getItemTexture(item.get()));
        }
    }

    public void javelin(Item item, ResourceLocation particleTex) {
        javelin(item, particleTex, false);
    }

    public void javelin(Item item, ResourceLocation particleTex, boolean overlay) {

        getBuilder(getName(item) + "_throwing")
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", particleTex)
                .guiLight(BlockModel.GuiLight.FRONT)
                .transforms()
                .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).rotation(0, -90, 180).translation(8, -17, 9).scale(1, 1, 1).end()
                .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT).rotation(0, -90, 180).translation(8, -17, -7).scale(1, 1, 1).end()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).rotation(0, -90, 25).translation(-3, 17, 1).scale(1, 1, 1).end()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).rotation(0, 90, -25).translation(13, 17, 1).scale(1, 1, 1).end()
                .transform(ModelBuilder.Perspective.GUI).rotation(0, 0, 0).translation(0, 0, 0).scale(1, 1, 1).end()
                //.transform(ModelBuilder.Perspective.FIXED).rotation(0, 180, 0).translation(-2, 4, -5).scale(0.5f, 0.5f, 0.5f).end()
                .transform(ModelBuilder.Perspective.GROUND).rotation(0, 0, 0).translation(0, 0, 0).scale(2f, 2f, 2f).end().end();

        getBuilder(getName(item))
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", particleTex)
                .guiLight(BlockModel.GuiLight.FRONT)
                .transforms()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).rotation(0, 90, -25).translation(13, 17, 1).scale(1, 1, 1).end()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).rotation(0, -90, 25).translation(-3, 17, 1).scale(1, 1, 1).end()
                .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT).rotation(0, 60, 0).translation(11, 17, 12).scale(1, 1, 1).end()
                .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).rotation(0, 60, 0).translation(11, 17, -2).scale(1, 1, 1).end()
                .transform(ModelBuilder.Perspective.GUI).rotation(0, 0, 0).translation(0, 0, 0).scale(1, 1, 1).end()
                //.transform(ModelBuilder.Perspective.FIXED).rotation(0, 180, 0).translation(-2, 4, -5).scale(0.5f, 0.5f, 0.5f).end()
                .transform(ModelBuilder.Perspective.GROUND).rotation(0, 0, 0).translation(0, 0, 0).scale(1f, 1f, 1f).end().end()
                .override()
                .predicate(new ResourceLocation("throwing"), 1f)
                .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Fantasia.ID, "item/" + getName(item) + "_throwing"))).end();

        if (overlay) {
            generate(getName(item) + "_inventory", getItemTexture(item), getItemLocation(item.getRegistryName().getPath() + "_overlay"));
        } else {
            generate(getName(item) + "_inventory", getItemTexture(item));
        }
    }
}
