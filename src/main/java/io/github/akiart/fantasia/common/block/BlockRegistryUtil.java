package io.github.akiart.fantasia.common.block;

import io.github.akiart.fantasia.common.fluid.FFluids;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistryUtil {

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return FBlocks.BLOCKS.register(name, supplier);
    }
}
