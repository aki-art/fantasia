package io.github.akiart.fantasia.dataGen;

import io.github.akiart.fantasia.Fantasia;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Fantasia.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Generators {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator dataGen = event.getGenerator();
		ExistingFileHelper fileHelper = event.getExistingFileHelper();

		// Items & Blocks
		dataGen.addProvider(new FBlockStateProvider(dataGen, fileHelper));
		dataGen.addProvider(new FItemModelProvider(dataGen, fileHelper));

		// Tags
		dataGen.addProvider(new FBlockTagsProvider(dataGen, fileHelper));
		dataGen.addProvider(new FFluidTagsProvider(dataGen, fileHelper));
		dataGen.addProvider(new FEntityTypeTagsProvider(dataGen, fileHelper));
		// dataGen.addProvider(new ItemTagsProvider(dataGen), blocktags, fileHelper));

		// Recipes
		dataGen.addProvider(new FRecipeProvider(dataGen));

		// Loot tables
		dataGen.addProvider(new FLootTableProvider(dataGen));
		// dataGen.addProvider(new FFishingLootTableProvider(dataGen));
	}
}
