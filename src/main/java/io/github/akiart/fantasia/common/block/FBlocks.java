package io.github.akiart.fantasia.common.block;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.blockType.FChestBlock;
import io.github.akiart.fantasia.common.block.blockType.FSignBlock;
import io.github.akiart.fantasia.common.block.blockType.FWallSignBlock;
import io.github.akiart.fantasia.common.block.blockType.FantasiaPortalBlock;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.IcicleBlock;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemBlock;
import io.github.akiart.fantasia.common.block.blockType.crystalLens.HiemsiteLensBlock;
import io.github.akiart.fantasia.common.block.blockType.crystalLens.TestCrystalLensBlock;
import io.github.akiart.fantasia.common.block.registrySet.CrystalRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneSet;
import io.github.akiart.fantasia.common.block.registrySet.TreeRegistryObject;
import io.github.akiart.fantasia.common.block.trees.FTree;
import io.github.akiart.fantasia.common.util.DirectionRestriction;
import io.github.akiart.fantasia.common.world.gen.feature.FConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FBlocks {

    private static final float TEMP = 1f; // replace these before any release

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Fantasia.ID);

    // Stones

    public static final StoneRegistryObject OBSIDIAN_BRICKS = BlockRegistryUtil.registerStones("obsidian_bricks", TEMP,
            TEMP, MaterialColor.PODZOL, false);

    public static final StoneSet SANGUITE = BlockRegistryUtil.registerStoneSet("sanguite", TEMP, TEMP,
            MaterialColor.TERRACOTTA_RED, StoneSet.Variant.BRICKS | StoneSet.Variant.POLISHED);

    public static final StoneSet SCALESTONE = BlockRegistryUtil.registerStoneSet("scalestone", TEMP, TEMP,
            MaterialColor.TERRACOTTA_CYAN, StoneSet.Variant.NONE);

    public static final StoneSet MARLSTONE = BlockRegistryUtil.registerStoneSet("marlstone", TEMP, TEMP,
            MaterialColor.CLAY, StoneSet.Variant.COBBLE);

    public static final StoneSet EDELSTONE = BlockRegistryUtil.registerStoneSet("edelstone", TEMP, TEMP,
            MaterialColor.SNOW, StoneSet.Variant.BRICKS | StoneSet.Variant.CRACKED_BRICKS | StoneSet.Variant.POLISHED);

    public static final StoneSet MUDSTONE = BlockRegistryUtil.registerStoneSet("mudstone", TEMP, TEMP,
            MaterialColor.TERRACOTTA_GREEN,
            StoneSet.Variant.BRICKS | StoneSet.Variant.CRACKED_BRICKS | StoneSet.Variant.POLISHED);

    public static final StoneSet PITH = BlockRegistryUtil.registerStoneSet("pith", TEMP, TEMP, MaterialColor.SAND,
            StoneSet.Variant.BRICKS | StoneSet.Variant.POLISHED);

    public static final StoneSet SUT = BlockRegistryUtil.registerStoneSet("sut", TEMP, TEMP, MaterialColor.COLOR_BLACK,
            StoneSet.Variant.NONE);

    // Stone Speleothems

    public static final RegistryObject<SpeleothemBlock> EDELSTONE_SPELEOTHEM = BlockRegistryUtil
            .registerSpeleothem(EDELSTONE.raw.block);

    // Crystals

    public static final CrystalRegistryObject HIEMSITE = BlockRegistryUtil.createCrystal("hiemsite",
            DirectionRestriction.NONE, .33f, true, TEMP, MaterialColor.ICE,
            () -> new HiemsiteLensBlock(CrystalRegistryObject.getLensProperties()));

    public static final CrystalRegistryObject GEHENNITE = BlockRegistryUtil.createCrystal("gehennite",
            DirectionRestriction.NONE, .33f, true, TEMP, MaterialColor.ICE,
            () -> new HiemsiteLensBlock(CrystalRegistryObject.getLensProperties()));

    // Plants

    // Trees

    public static final TreeRegistryObject FROZEN_ELM = BlockRegistryUtil.createTree("frozen_elm",
            () -> new FTree(FConfiguredFeatures.FROZEN_ELM), MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.LAPIS,
            MaterialColor.ICE, FWoodType.FROZEN_ELM);

    public static final TreeRegistryObject FROZEN_SPRUCE = BlockRegistryUtil.createTree("frozen_spruce",
            () -> new FTree(FConfiguredFeatures.FROZEN_SPRUCE), MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.LAPIS,
            MaterialColor.ICE, FWoodType.FROZEN_SPRUCE);

    public static final TreeRegistryObject ELM = BlockRegistryUtil.createTree("elm",
            () -> new FTree(FConfiguredFeatures.FROZEN_SPRUCE), MaterialColor.WOOD, MaterialColor.COLOR_BROWN,
            MaterialColor.COLOR_GREEN, FWoodType.ELM);

    // XXX test, replace later
    public static final RegistryObject<TallFlowerBlock> SNOWBERRY_BUSH = BlockRegistryUtil.register("snowberry_bush",
            () -> new TallFlowerBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)));


    // Misc

    public static final RegistryObject<SpeleothemBlock> ICICLE = BlockRegistryUtil.createSpeleothem("icicle",
            () -> new IcicleBlock(AbstractBlock.Properties.copy(Blocks.ICE)));

    public static final RegistryObject<SnowyDirtBlock> FROZEN_DIRT = BlockRegistryUtil.register("frozen_dirt",
            () -> new SnowyDirtBlock(AbstractBlock.Properties.copy(Blocks.DIRT)));

    public static final RegistryObject<FantasiaPortalBlock> FANTASIA_PORTAL_BLOCK = BlockRegistryUtil.register("fantasia_portal_block",
            () -> new FantasiaPortalBlock(AbstractBlock.Properties.copy(Blocks.GLASS)
                    .noCollission()));


    // Test & Debug
    public static final RegistryObject<TestCrystalLensBlock> TEST_CRYSTAL_LENS = BlockRegistryUtil
            .register("test_crystal_lens", TestCrystalLensBlock::new);

    public static final RegistryObject<FSignBlock> sign = BlockRegistryUtil.register("test_sign", () -> new FSignBlock(AbstractBlock.Properties.copy(Blocks.OAK_SIGN), WoodType.ACACIA));
    public static final RegistryObject<FWallSignBlock> wallSign = BlockRegistryUtil.register("test_wall_sign", () -> new FWallSignBlock(AbstractBlock.Properties.copy(Blocks.OAK_SIGN), WoodType.ACACIA));

    public static final RegistryObject<FChestBlock> testChest = BlockRegistryUtil.register("test_chest", () -> new FChestBlock(AbstractBlock.Properties.copy(Blocks.CHEST), FWoodType.FROZEN_ELM));

}
