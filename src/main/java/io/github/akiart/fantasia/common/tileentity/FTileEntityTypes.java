package io.github.akiart.fantasia.common.tileentity;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FTileEntityTypes {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister
		.create(ForgeRegistries.TILE_ENTITIES, Fantasia.ID);

	public static final RegistryObject<TileEntityType<CrystalLensTileEntity>> CRYSTAL_LENS = TILE_ENTITY_TYPES.register(
		"crystal_lens",
		() -> TileEntityType.Builder.of(CrystalLensTileEntity::new, FBlocks.TEST_CRYSTAL_LENS.get()).build(null));
}
