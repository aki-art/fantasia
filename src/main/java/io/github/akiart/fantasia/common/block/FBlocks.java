package io.github.akiart.fantasia.common.block;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.blockType.AcidFluidBlock;
import io.github.akiart.fantasia.common.block.blockType.TestAcidLoggableBlock;
import io.github.akiart.fantasia.common.fluid.FFluids;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Fantasia.ID);

    // Fluids
    public static final RegistryObject<AcidFluidBlock> ACID = BlockRegistryUtil.register("acid",
            () -> new AcidFluidBlock(FFluids.ACID_SOURCE, Block.Properties
                    .of(FMaterial.ACID, MaterialColor.COLOR_LIGHT_GREEN)
                    .noCollission()
                    .speedFactor(0.7f)
                    .strength(100f)
                    .lightLevel(state -> 4)
                    .noDrops()));

    public static final RegistryObject<TestAcidLoggableBlock> TEST_ACID_LOGGABLE = BlockRegistryUtil.register("test_acid_loggable",
    TestAcidLoggableBlock::new);
}
