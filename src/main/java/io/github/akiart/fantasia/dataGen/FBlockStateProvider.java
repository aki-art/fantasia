package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FBlockStateProvider extends FBlockStateProviderBase {

    public FBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Fantasia.ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(FBlocks.ACID.get(), models().getBuilder(getName(FBlocks.ACID.get())).texture("particle", new ResourceLocation("block/lime_wool")));
        debugBlocks();
    }

    private void debugBlocks() {
        crossBlock(FBlocks.TEST_ACID_LOGGABLE.get());
    }
}