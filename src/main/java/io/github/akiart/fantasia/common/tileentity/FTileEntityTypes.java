package io.github.akiart.fantasia.common.tileentity;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.util.RotatableBoxVoxelShape;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class FTileEntityTypes {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister
		.create(ForgeRegistries.TILE_ENTITIES, Fantasia.ID);

	public static final RegistryObject<TileEntityType<CrystalLensTileEntity>> CRYSTAL_LENS = TILE_ENTITY_TYPES.register(
		"crystal_lens",
		() -> TileEntityType.Builder.of(CrystalLensTileEntity::new, FBlocks.TEST_CRYSTAL_LENS.get()).build(null));

    public static final RegistryObject<TileEntityType<FSignTileEntity>> SIGN = TILE_ENTITY_TYPES.register("sign",
			() -> TileEntityType.Builder.of(FSignTileEntity::new,
					FBlocks.FROZEN_ELM.getSign().get(),
					FBlocks.FROZEN_ELM.getWallSign().get(),
//					FBlocks.FROZEN_SPRUCE.sign.get(),
//					FBlocks.FROZEN_SPRUCE.wallSign.get(),
//					FBlocks.ELM.sign.get(),
//					FBlocks.ELM.wallSign.get(),
					FBlocks.sign.get(),
					FBlocks.wallSign.get()
			).build(null));

	public static final RegistryObject<TileEntityType<FChestTileEntity>> CHEST = TILE_ENTITY_TYPES.register("chest",
			() -> TileEntityType.Builder.of(FChestTileEntity::new,
					FBlocks.testChest.get(),
					FBlocks.FROZEN_ELM.getChest().get()
//					FBlocks.FROZEN_SPRUCE.chest.get(),
//					FBlocks.ELM.chest.get()
			).build(null));
}
