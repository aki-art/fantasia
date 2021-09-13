package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.tags.FTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FFluidTagsProvider extends FluidTagsProvider {

    public FFluidTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Fantasia.ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(FTags.Fluids.ACID).add(FFluids.ACID_SOURCE.get(), FFluids.ACID_FLOWING.get());
        //tag(FluidTags.LAVA).add(FFluids.HOT_MAGMA_SOURCE.get(), FFluids.HOT_MAGMA_FLOWING.get());
        //tag(FTags.Fluids.HOT_MAGMA).add(FFluids.HOT_MAGMA_SOURCE.get(), FFluids.HOT_MAGMA_FLOWING.get());
    }
}