package io.github.akiart.fantasia.dataGen.lang;

import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.block.registrySet.CrystalRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneSet;
import io.github.akiart.fantasia.common.block.registrySet.trees.AbstractTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.BasicTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.ThinTreeRegistryObject;

public class FBlockLanguageProvider {

    FLanguageProvider provider;

    public FBlockLanguageProvider(FLanguageProvider provider) {
        this.provider = provider;
    }

    protected void addTranslations() {
        trees();
        stones();
        crystals();
        plants();
        speleothems();

        provider.add(FBlocks.FANTASIA_PORTAL_BLOCK.get(), "Fantasia Portal");
        provider.add(FBlocks.ICICLE.get(), "Icicle");
        provider.add(FBlocks.ACID.get(), "Acid");
        provider.add(FBlocks.FROZEN_DIRT.get(), "Frozen Dirt");
    }

    private void speleothems() {
        provider.add(FBlocks.EDELSTONE_SPELEOTHEM.get(), "Edelstone Stalactite");
    }

    private void trees() {
        addTreeSet("Frozen Elm", FBlocks.FROZEN_ELM);
        addTreeSet("Aspen", FBlocks.ASPEN);
    }

    private void stones() {
        addStoneSet("Sanguite", FBlocks.SANGUITE);
        addStoneSet("Edelstone", FBlocks.EDELSTONE);
        addStoneSet("Marlstone", FBlocks.MARLSTONE);
        addStoneSet("Scalestone", FBlocks.SCALESTONE);
        addStoneSet("Pith", FBlocks.PITH);
        addStoneSet("Sut", FBlocks.SUT);
        addStoneSet("Mudstone", FBlocks.MUDSTONE);

        addStones("Obsidian Bricks", FBlocks.OBSIDIAN_BRICKS);
    }

    private void crystals() {
        addCrystalSet("Hiemsite", FBlocks.HIEMSITE);
        addCrystalSet("Gehennite", FBlocks.GEHENNITE);
    }

    private void plants() {
        provider.add(FBlocks.SNOWBERRY_BUSH_TOP.get(), "Snowberry Bush");
        provider.add(FBlocks.SNOWBERRY_BUSH_BOTTOM.get(), "Snowberry Bush");
    }

    private void addCrystalSet(String name, CrystalRegistryObject crystalSet) {
        provider.add(crystalSet.block.get(), name + " Block");
        provider.add(crystalSet.crystal.get(), name);
        provider.add(crystalSet.polished.get(), "Polised" + name + " Block");
        provider.add(crystalSet.lens.get(), name + " Lens");
        provider.add(crystalSet.waxedLens.get(), "Waxed " + name + " Lens");
    }

    private <T extends AbstractTreeRegistryObject> void addTreeSet(String name, T tree) {
        provider.add(tree.getPlanks().get(), name + " Planks");

        provider.add(tree.getLog().get(), name + " Log");
        provider.add(tree.getStrippedLog().get(), name + " Stripped Log");
        provider.add(tree.getWood().get(), name + " Wood");
        provider.add(tree.getStrippedWood().get(), name + " Stripped Wood");

        provider.add(tree.getButton().get(), name + " Button");
        provider.add(tree.getPressurePlate().get(), name + " Pressure Plate");

        provider.add(tree.getFence().get(), name + " Fence");
        provider.add(tree.getFenceGate().get(), name + " Fence Gate");
        provider.add(tree.getDoor().get(), name + " Door");
        provider.add(tree.getTrapDoor().get(), name + " Trapdoor");

        provider.add(tree.getStairs().get(), name + " Stairs");
        provider.add(tree.getSlab().get(), name + " Slab");

        provider.add(tree.getChest().get(), name + " Chest");
        provider.add(tree.getSign().get(), name + " Sign");
        // provider.add(tree.getWallSign().get(), name + " Sign");

        if(tree instanceof ThinTreeRegistryObject) {
            provider.add(((ThinTreeRegistryObject)tree).getSapling().get(), name + " Sapling");
            provider.add(((ThinTreeRegistryObject)tree).getLeaves().get(), name + " Leaves");
        }
        else if(tree instanceof BasicTreeRegistryObject) {
            provider.add(((BasicTreeRegistryObject)tree).getSapling().get(), name + " Sapling");
            provider.add(((BasicTreeRegistryObject)tree).getLeaves().get(), name + " Leaves");
        }
    }

    private void addStoneSet(String name, StoneSet set) {
        if(set.hasStoneVariant(StoneSet.Variant.RAW)) addStones(name, set.raw);
        if(set.hasStoneVariant(StoneSet.Variant.BRICKS)) addStones(name + " Bricks", set.bricks);
        if(set.hasStoneVariant(StoneSet.Variant.POLISHED)) addStones("Polished " + name, set.polished);
        if(set.hasStoneVariant(StoneSet.Variant.CRACKED_BRICKS)) addStones(name + " Cracked Bricks", set.cracked_bricks);
        if(set.hasStoneVariant(StoneSet.Variant.COBBLE)) addStones("Cobbled " + name, set.cobble);
        if(set.hasStoneVariant(StoneSet.Variant.CHISELED)) addStones("Chiseled " + name, set.chiseled);

    }
    private void addStones(String name, StoneRegistryObject stoneSet) {
        provider.add(stoneSet.block.get(), name);
        provider.add(stoneSet.stairs.get(), name + " Stairs");
        provider.add(stoneSet.slab.get(), name + " Slab");
        provider.add(stoneSet.wall.get(), name + " Wall");

        if(stoneSet.hasRedStoneComponents()) {
            provider.add(stoneSet.button.get(), name + " Button");
            provider.add(stoneSet.pressurePlate.get(), name + " Pressure Plate");
        }
    }
}
