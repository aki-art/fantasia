package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.loaders.DynamicBucketModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FItemModelProvider extends FItemModelProviderBase {

    public FItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        coinItem(FItems.COIN.get());

        // Forge bucket model
        withExistingParent(getName(FItems.ACID_BUCKET.get()), new ResourceLocation("forge", "item/bucket"))
                .customLoader(DynamicBucketModelBuilder::begin)
                .fluid(FFluids.ACID_SOURCE.get())
                .applyFluidLuminosity(true);

        blockGenerated(FItems.TEST_ACID_LOGGABLE.get());

    }
}
