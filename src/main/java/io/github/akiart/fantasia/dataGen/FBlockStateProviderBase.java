package io.github.akiart.fantasia.dataGen;

import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Direction.Axis;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class FBlockStateProviderBase extends BlockStateProvider {

    public FBlockStateProviderBase(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    protected String getName(ForgeRegistryEntry<?> entry) {
        return entry.getRegistryName().getPath();
    }

    protected void crossBlock(Block block) {
        ModelFile model = models().cross(getName(block), blockTexture(block));
        simpleBlock(block, model);
    }
}
