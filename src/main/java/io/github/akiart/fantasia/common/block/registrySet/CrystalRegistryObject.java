package io.github.akiart.fantasia.common.block.registrySet;

import java.util.function.Supplier;

import io.github.akiart.fantasia.common.block.BlockRegistryUtil;
import io.github.akiart.fantasia.common.block.blockType.biomeDecoration.cave.CrystalColumnBlock;
import io.github.akiart.fantasia.common.block.blockType.crystalLens.AbstractFunctionalLensBlock;
import io.github.akiart.fantasia.common.block.blockType.crystalLens.WaxedLensBlock;
import io.github.akiart.fantasia.common.sound.FSoundType;
import io.github.akiart.fantasia.common.util.DirectionRestriction;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;

public class CrystalRegistryObject {

	private final String name;

	public final RegistryObject<Block> block;
	public final RegistryObject<Block> polished;
	public final RegistryObject<? extends AbstractFunctionalLensBlock> lens;
	public final RegistryObject<WaxedLensBlock> waxedLens;
//	public final RegistryObject<Block> LANTERN;
	public final RegistryObject<CrystalColumnBlock> crystal;

	public static AbstractBlock.Properties getLensProperties() {
		return AbstractBlock.Properties.of(Material.DECORATION)
			.strength(0.3f)
			.noOcclusion()
			.isValidSpawn(BlockRegistryUtil::neverAllowSpawn)
			.isRedstoneConductor(BlockRegistryUtil::notSolid)
			.isSuffocating(BlockRegistryUtil::notSolid)
			.isViewBlocking(BlockRegistryUtil::notSolid)
			.randomTicks()
			.sound(SoundType.GLASS);
	}

	protected AbstractBlock.Properties makeGlowy(AbstractBlock.Properties property) {
		return property.hasPostProcess(CrystalRegistryObject::needsPostProcessing)
			.emissiveRendering(CrystalRegistryObject::needsPostProcessing)
			.lightLevel((state) -> {
				return 2;
			});
	}

	protected AbstractBlock.Properties getCrystalBlockProperties(boolean glowy, MaterialColor color,
		float hardnessAndResistance) {
		AbstractBlock.Properties properties = AbstractBlock.Properties.of(Material.METAL, color)
			.requiresCorrectToolForDrops()
			.strength(hardnessAndResistance)
			.sound(FSoundType.CRYSTAL);

		return glowy ? makeGlowy(properties) : properties;
	}

	protected AbstractBlock.Properties getCrystalProperties(boolean glowy, MaterialColor color,
		float hardnessAndResistance) {
		AbstractBlock.Properties properties = AbstractBlock.Properties.of(Material.METAL, color)
			.strength(hardnessAndResistance / 2f)
			.noOcclusion()
			.isValidSpawn(BlockRegistryUtil::neverAllowSpawn)
			.isRedstoneConductor(BlockRegistryUtil::notSolid)
			.isSuffocating(BlockRegistryUtil::notSolid)
			.sound(FSoundType.CRYSTAL);

		return glowy ? makeGlowy(properties) : properties;
	}

	public CrystalRegistryObject(String name, DirectionRestriction restriction, float altMiddleChance, boolean glow,
		float hardnessAndResistance, MaterialColor color,
		Supplier<? extends AbstractFunctionalLensBlock> lensProvider) {

		this.name = name;

		block = BlockRegistryUtil.register(name,
			() -> new Block(getCrystalBlockProperties(glow, color, hardnessAndResistance)));

		polished = BlockRegistryUtil.register("polished_" + name,
			() -> new Block(AbstractBlock.Properties.copy(block.get())));

		lens = BlockRegistryUtil.register(name + "_lens", lensProvider);

		waxedLens = BlockRegistryUtil.register(name + "_waxed_lens",
			() -> new WaxedLensBlock(lens, AbstractBlock.Properties.copy(lens.get())));

		crystal = BlockRegistryUtil.createSpeleothem(name + "_crystal", () -> new CrystalColumnBlock(restriction,
			altMiddleChance, getCrystalProperties(glow, color, hardnessAndResistance)));

//		LANTERN = BlockRegistryUtil
//			.register(
//				name + "_lantern", () -> new CrystalLanternBlock(AbstractBlock.Properties
//					.create(Material.IRON)
//					.setRequiresTool()
//					.hardnessAndResistance(hardnessAndResistance)
//					.sound(SoundType.LANTERN)
//					.setLightLevel((state) -> {
//						return 15;
//					})
//					.notSolid()));
	}

	private static boolean needsPostProcessing(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

	public String getName() {
		return name;
	}
}
