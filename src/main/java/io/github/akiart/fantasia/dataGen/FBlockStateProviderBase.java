package io.github.akiart.fantasia.dataGen;

import java.util.function.Supplier;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemBlock;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.SpeleothemStage;
import io.github.akiart.fantasia.common.block.blockType.crystalLens.AbstractCrystalPaneBlock;
import io.github.akiart.fantasia.common.block.blockType.crystalLens.AbstractFunctionalLensBlock;
import io.github.akiart.fantasia.common.block.registrySet.CrystalRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.TreeRegistryObject;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.StairsBlock;
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

	protected void tree(TreeRegistryObject blockSet) {
		ResourceLocation plankTex = blockTexture(blockSet.planks.get());
		ResourceLocation doorBottom = getLocation(blockSet.getName() + "_door_top");
		ResourceLocation doorTop = getLocation(blockSet.getName() + "_door_bottom");
		ResourceLocation trapdoor = getLocation(blockSet.getName() + "_trapdoor");
		ResourceLocation logTex = blockTexture(blockSet.log.get());
		ResourceLocation strippedLogTex = blockTexture(blockSet.strippedLog.get());

		simpleBlock(blockSet.planks.get());
		simpleBlock(blockSet.leaves.get());

		//simpleBlock(blockSet.sign.get(), models().getBuilder(getName(blockSet.sign.get())).texture("particle", plankTex));
		//simpleBlock(blockSet.wallSign.get(), models().getBuilder(getName(blockSet.wallSign.get())).texture("particle", plankTex));

		basicTileEntity(blockSet.sign.get(), plankTex);
		basicTileEntity(blockSet.wallSign.get(), plankTex);
		basicTileEntity(blockSet.chest.get(), plankTex);

		crossBlock(blockSet.sapling.get());

		logBlock(blockSet.log.get());
		logBlock(blockSet.strippedLog.get());
		axisBlock(blockSet.strippedWood.get(), strippedLogTex, strippedLogTex);
		axisBlock(blockSet.wood.get(), logTex, logTex);

		stairsBlock(blockSet.stairs.get(), plankTex);
		slabBlock(blockSet.slab.get(), blockSet.planks.getId(), plankTex);

		buttonBlock(blockSet.button.get(), plankTex);
		pressurePlateBlock(blockSet.pressurePlate.get(), plankTex);
		doorBlock(blockSet.door.get(), doorTop, doorBottom);
		trapdoorBlock(blockSet.trapDoor.get(), trapdoor, true);
		fenceBlock(blockSet.fence.get(), plankTex);
		fenceGateBlock(blockSet.fenceGate.get(), plankTex);
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
