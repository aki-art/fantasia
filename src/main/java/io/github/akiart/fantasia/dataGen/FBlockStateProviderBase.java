package io.github.akiart.fantasia.dataGen;

import java.util.function.Supplier;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.blockType.ThinLogBlock;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemBlock;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemStage;
import io.github.akiart.fantasia.common.block.blockType.crystalLens.AbstractCrystalPaneBlock;
import io.github.akiart.fantasia.common.block.blockType.crystalLens.AbstractFunctionalLensBlock;
import io.github.akiart.fantasia.common.block.blockType.plants.SnowBerryBushBottomBlock;
import io.github.akiart.fantasia.common.block.blockType.plants.SnowBerryBushTopBlock;
import io.github.akiart.fantasia.common.block.registrySet.CrystalRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.AbstractTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.BasicTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.ThinTreeRegistryObject;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Direction.Axis;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class FBlockStateProviderBase extends BlockStateProvider {

	public FBlockStateProviderBase(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
		super(gen, modid, exFileHelper);
	}

	protected String getName(ForgeRegistryEntry<?> entry) {
		return entry.getRegistryName().getPath();
	}

	protected ResourceLocation getVanillaLocation(String name) {
		return new ResourceLocation("block/" + name);
	}

	protected ResourceLocation getLocation(String name) {
		return new ResourceLocation(Fantasia.ID, "block/" + name);
	}

	protected void snowyBlock(SnowyDirtBlock block) {
		getVariantBuilder(block).forAllStates(state -> {

			boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);

			ResourceLocation all = blockTexture(block);
			ModelFile model;

			if (snowy) {
				ResourceLocation side = getLocation("snowy_" + getName(block));
				ResourceLocation top = new ResourceLocation("block/snow");
				model = models().cubeBottomTop(getName(block), side, all, top);
			} else {
				model = models().cubeAll("snowy_" + getName(block), all);
			}

			return ConfiguredModel.builder().modelFile(model).build();
		});
	}

	protected <T extends DoublePlantBlock> void doublePlantBlock(T block) {
		getVariantBuilder(block).forAllStates(state -> {

			// String name = getName(block);
			String name = getName(block)
				+ (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? "_top" : "_bottom");
			ModelFile model = models().cross(name, getLocation(name));

			return ConfiguredModel.builder().modelFile(model).build();
		});
	}

	protected void thinLogBlock(Supplier<ThinLogBlock> thinLogBlock, ResourceLocation texture) {
		ThinLogBlock block = thinLogBlock.get();
		ModelFile post = models().singleTexture(getName(block), getLocation("sixway_post"), texture);
		ModelFile branch = models().singleTexture(getName(block), getLocation("sixway_side"), texture);
		MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
				.part().modelFile(post).addModel().end()
				.part().modelFile(branch).uvLock(true).addModel().condition(ThinLogBlock.UP, true).end()
				.part().modelFile(branch).rotationX(180).uvLock(true).addModel().condition(ThinLogBlock.DOWN, true).end()
				.part().modelFile(branch).rotationX(90).uvLock(true).addModel().condition(ThinLogBlock.NORTH, true).end()
				.part().modelFile(branch).rotationX(90).rotationY(90).uvLock(true).addModel().condition(ThinLogBlock.EAST, true).end()
				.part().modelFile(branch).rotationX(90).rotationY(180).uvLock(true).addModel().condition(ThinLogBlock.SOUTH, true).end()
				.part().modelFile(branch).rotationX(90).rotationY(270).uvLock(true).addModel().condition(ThinLogBlock.WEST, true).end();
	}

	protected void stones(StoneRegistryObject blockSet) {
		ResourceLocation texture = blockTexture(blockSet.block.get());

		simpleBlock(blockSet.block.get());
		stairsBlock(blockSet.stairs.get(), texture);
		wallBlock(blockSet.wall.get(), texture);
		slabBlock(blockSet.slab.get(), blockSet.block.getId(), texture);

		if (blockSet.hasRedStoneComponents()) {
			buttonBlock(blockSet.button.get(), texture);
			pressurePlateBlock(blockSet.pressurePlate.get(), texture);
		}
	}

	protected void berryBush(SnowBerryBushBottomBlock bottom, SnowBerryBushTopBlock top) {
		String bottomName = getName(bottom);

		getVariantBuilder(bottom).forAllStates(state -> {
			String name = bottomName + "_" + state.getValue(SnowBerryBushBottomBlock.AGE);
			ModelFile model = models().cross(name, getLocation(name));
			return ConfiguredModel.builder().modelFile(model).build();
		});

		String topName = getName(top);

		getVariantBuilder(top).forAllStates(state -> {
			String name = topName + "_" + state.getValue(SnowBerryBushTopBlock.AGE);
			ModelFile model = models().cross(name, getLocation(name));
			return ConfiguredModel.builder().modelFile(model).build();
		});
	}

	private void basicTree(BasicTreeRegistryObject blockSet) {
		ResourceLocation logTex = blockTexture(blockSet.log.get());
		ResourceLocation strippedLogTex = blockTexture(blockSet.strippedLog.get());

		simpleBlock(blockSet.leaves.get());
		crossBlock(blockSet.sapling.get());
		logBlock(blockSet.log.get());
		logBlock(blockSet.strippedLog.get());
		axisBlock(blockSet.strippedWood.get(), strippedLogTex, strippedLogTex);
		axisBlock(blockSet.wood.get(), logTex, logTex);
	}

	private void thinTree(ThinTreeRegistryObject blockSet) {
		ResourceLocation logTex = blockTexture(blockSet.log.get());
		ResourceLocation strippedLogTex = blockTexture(blockSet.strippedLog.get());

		simpleBlock(blockSet.leaves.get());
		crossBlock(blockSet.sapling.get());
		thinLogBlock(blockSet.log, logTex);
		thinLogBlock(blockSet.strippedLog, strippedLogTex);
		//thinLogBlock(blockSet.strippedWood.get(), strippedLogTex, strippedLogTex);
		//thinLogBlock(blockSet.wood.get(), logTex, logTex);
	}

	protected <T extends AbstractTreeRegistryObject> void tree(T blockSet) {
		ResourceLocation plankTex = blockTexture(blockSet.getPlanks().get());

		simpleBlock(blockSet.getPlanks().get());

		basicTileEntity(blockSet.getSign().get(), plankTex);
		basicTileEntity(blockSet.getWallSign().get(), plankTex);
		basicTileEntity(blockSet.getChest().get(), plankTex);

		stairsBlock(blockSet.getStairs().get(), plankTex);
		slabBlock(blockSet.getSlab().get(), blockSet.getPlanks().getId(), plankTex);

		buttonBlock(blockSet.getButton().get(), plankTex);
		pressurePlateBlock(blockSet.getPressurePlate().get(), plankTex);
		doorBlock(blockSet.getDoor().get(), getLocation(blockSet.getName() + "_door_bottom"), getLocation(blockSet.getName() + "_door_top"));
		trapdoorBlock(blockSet.getTrapDoor().get(), getLocation(blockSet.getName() + "_trapdoor"), true);
		fenceBlock(blockSet.getFence().get(), plankTex);
		fenceGateBlock(blockSet.getFenceGate().get(), plankTex);

		if(blockSet instanceof BasicTreeRegistryObject) {
			basicTree((BasicTreeRegistryObject) blockSet);
		}
		else if(blockSet instanceof ThinTreeRegistryObject) {
			thinTree((ThinTreeRegistryObject) blockSet);
		}
	}

	protected void basicTileEntity(Block block, ResourceLocation particleTex) {
		simpleBlock(block, models().getBuilder(getName(block)).texture("particle", particleTex));
	}


	protected <T extends AbstractButtonBlock> void buttonBlock(T button, ResourceLocation texture) {

		String name = getName(button);
		ModelFile buttonModel = models().singleTexture(name, getVanillaLocation("button"), texture);
		ModelFile pressedButtonModel = models().singleTexture(name + "_pressed", getVanillaLocation("button_pressed"),
			texture);

		getVariantBuilder(button).forAllStates(state -> {

			ModelFile model = state.getValue(AbstractButtonBlock.POWERED) ? buttonModel : pressedButtonModel;

			AttachFace face = state.getValue(HorizontalFaceBlock.FACE);
			Direction facing = state.getValue(HorizontalFaceBlock.FACING);
			boolean onWall = face == AttachFace.WALL;
			boolean onCeiling = face == AttachFace.CEILING;

			int rotX = onCeiling ? 180 : onWall ? 90 : 0;
			int rotY = onCeiling ? (int) facing.toYRot() : (int) facing.getOpposite().toYRot();

			return ConfiguredModel.builder().uvLock(onWall).rotationX(rotX).rotationY(rotY).modelFile(model).build();
		});
	}

	protected void crystal(CrystalRegistryObject blockSet) {
		simpleBlock(blockSet.block.get());
		simpleBlock(blockSet.polished.get());

		ResourceLocation lensTexture = getLocation(getName(blockSet.lens.get()));
		crystalLensBlock2(lensTexture, blockSet.lens.get());
		crystalLensBlock2(lensTexture, blockSet.waxedLens.get());

//		ModelFile lanternModel = models().singleTexture(
//			blockSet.getName() + "_lantern", 
//		    getLocation("crystal_lantern"), 
//		    "texture", 
//		    getLocation(blockSet.getName() + "_lantern_crystal"));

		// getVariantBuilder(blockSet.LANTERN.get()).partialState().setModels(new
		// ConfiguredModel(lanternModel));

		speleothemBlock(blockSet.crystal);
	}

	protected void crystalLensBlock2(ResourceLocation texture, AbstractCrystalPaneBlock block) {

		getVariantBuilder(block).forAllStates(state -> {

			Direction facing = state.getValue(AbstractFunctionalLensBlock.FACING);

			boolean onCeiling = facing == Direction.DOWN;
			boolean onWall = facing.getAxis() != Axis.Y;

			int rx = onCeiling ? 180 : onWall ? 90 : 0;
			int ry = onCeiling ? (int) facing.toYRot() : (int) facing.getOpposite().toYRot();

			ModelFile model = models().withExistingParent(getName(block), getLocation("crystal_lens"))
				.texture("texture", texture)
				.texture("edge", texture + "_edge");

			return ConfiguredModel.builder().modelFile(model).rotationX(rx).rotationY(ry).build();
		});
	}

	protected void crystalLensBlock(AbstractFunctionalLensBlock block) {

		getVariantBuilder(block).forAllStates(state -> {

			AttachFace face = state.getValue(HorizontalFaceBlock.FACE);
			Direction facing = state.getValue(HorizontalFaceBlock.FACING);
			boolean onWall = face == AttachFace.WALL;
			boolean onCeiling = face == AttachFace.CEILING;

			int rx = onCeiling ? 180 : onWall ? 90 : 0;
			int ry = onCeiling ? (int) facing.toYRot() : (int) facing.getOpposite().toYRot();

			ModelFile model = models().withExistingParent(getName(block), getLocation("crystal_lens"))
				.texture("texture", getLocation(getName(block)))
				.texture("edge", getLocation(getName(block) + "_edge"));

			return ConfiguredModel.builder().modelFile(model).rotationX(rx).rotationY(ry).build();
		});
	}

	protected <T extends AbstractPressurePlateBlock> void pressurePlateBlock(T pressurePlate,
		ResourceLocation texture) {

		String name = getName(pressurePlate);

		ModelFile upModel = models().singleTexture(name, getVanillaLocation("pressure_plate_up"), texture);
		ModelFile downModel = models().singleTexture(name + "_down", getVanillaLocation("pressure_plate_down"),
			texture);

		getVariantBuilder(pressurePlate).forAllStates(state -> {
			ModelFile model = state.getValue(AbstractButtonBlock.POWERED) ? upModel : downModel;
			return ConfiguredModel.builder().modelFile(model).build();
		});
	}
	
	protected void crossBlock(Block block) {
		ModelFile model = models().cross(getName(block), blockTexture(block));
		simpleBlock(block, model);
	}
	
	protected <T extends SpeleothemBlock> void speleothemBlock(Supplier<T> block) {
		getVariantBuilder(block.get()).forAllStates(state -> {

			SpeleothemStage stage = state.getValue(SpeleothemBlock.STAGE);

			String suffix = (stage == SpeleothemStage.MIDDLE_ALT && !block.get().hasAltMiddle())
				? SpeleothemStage.MIDDLE.getSerializedName()
				: stage.getSerializedName();
			String name = getName(block.get()) + "_" + suffix;

			AttachFace face = state.getValue(HorizontalFaceBlock.FACE);
			Direction facing = state.getValue(HorizontalFaceBlock.FACING);
			boolean onWall = face == AttachFace.WALL;
			boolean onCeiling = face == AttachFace.CEILING;

			int rx = onCeiling ? 180 : onWall ? 90 : 0;
			int ry = onCeiling ? (int) facing.toYRot() : (int) facing.getOpposite().toYRot();

			ModelFile model = models().cross(name, getLocation(name));

			return ConfiguredModel.builder().modelFile(model).rotationX(rx).rotationY(ry).build();
		});
	}
}
