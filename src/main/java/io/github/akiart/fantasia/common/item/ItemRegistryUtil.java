package io.github.akiart.fantasia.common.item;

import java.util.HashSet;
import java.util.function.Supplier;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer.ISTERs;
import io.github.akiart.fantasia.common.block.registrySet.CrystalRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.StoneSet;
import io.github.akiart.fantasia.common.block.registrySet.TreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.AbstractTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.BasicTreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.ThinTreeRegistryObject;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.item.FBoatEntity;
import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import io.github.akiart.fantasia.common.item.itemType.FBoatItem;
import io.github.akiart.fantasia.common.item.itemType.FSpawnEggItem;
import io.github.akiart.fantasia.common.item.itemType.JavelinItem;
import io.github.akiart.fantasia.common.item.registrySet.CrystalRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.StoneItemSet;
import io.github.akiart.fantasia.common.item.registrySet.StoneRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.TreeRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.tree.AbstractTreeRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.tree.BasicTreeRegistryItem;
import io.github.akiart.fantasia.common.item.registrySet.tree.ThinTreeRegistryItem;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.WoodType;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ItemRegistryUtil {

	public static HashSet<StoneRegistryItem> stones = new HashSet<>();
	public static HashSet<RegistryObject<BlockItem>> blockItems = new HashSet<>();
	public static HashSet<CrystalRegistryItem> crystals = new HashSet<>();
	public static HashSet<AbstractTreeRegistryItem<?>> trees = new HashSet<>();
	public static HashSet<RegistryObject<? extends Item>> simpleItems = new HashSet<>();

	public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
		RegistryObject<T> reg = FItems.ITEMS.register(name, item);
		simpleItems.add(reg);
		return reg;
	}

	public static RegistryObject<FSpawnEggItem> registerEgg(String name, EntityType<?> entity, int shellColor, int patternColor) {
		RegistryObject<FSpawnEggItem> reg = FItems.ITEMS.register(name, () -> new FSpawnEggItem(entity, shellColor, patternColor, new Item.Properties().tab(FItemGroup.FANTASIA)));
		simpleItems.add(reg);

		return reg;
	}

	public static RegistryObject<Item> registerFood(String id, Food food) {
		return register(id, () -> new Item(new Item.Properties()
				.tab(FItemGroup.FANTASIA)
				.food(Foods.COOKED_CHICKEN)));
	}

	public static RegistryObject<BlockItem> registerFromBlock(RegistryObject<? extends Block> parent) {
		return FItems.ITEMS.register(parent.getId().getPath(),
			() -> new BlockItem(parent.get(), new Item.Properties().tab(FItemGroup.FANTASIA)));
	}

	public static StoneRegistryItem registerStoneItems(StoneRegistryObject parent) {
		StoneRegistryItem obj = new StoneRegistryItem(parent);
		stones.add(obj);
		return obj;
	}

	public static CrystalRegistryItem registerCrystalItems(CrystalRegistryObject parent) {
		CrystalRegistryItem obj = new CrystalRegistryItem(parent);
		crystals.add(obj);
		return obj;
	}

	public static RegistryObject<FBoatItem> registerBoat(String name, FBoatEntity.Type type) {
		return ItemRegistryUtil.register(name, () ->
				new FBoatItem(type, new Item.Properties().stacksTo(1).tab(FItemGroup.FANTASIA)));
	}

	public static RegistryObject<JavelinItem> registerJavelin(String name, IItemTier tier, float damage, float attackSpeed) {
		return ItemRegistryUtil.register(name,
				() -> new JavelinItem(tier, damage , attackSpeed,
						new Item.Properties()
								.stacksTo(16)
								.tab(FItemGroup.FANTASIA)
								.setISTER(() -> () -> ISTERs.createJavelinISTER(name))));
	}

	public static BasicTreeRegistryItem registerTreeItems(BasicTreeRegistryObject parent, WoodType woodType) {
		BasicTreeRegistryItem obj = new BasicTreeRegistryItem(parent, woodType);
		trees.add(obj);
		return obj;
	}

	public static ThinTreeRegistryItem registerThinTreeItems(ThinTreeRegistryObject parent, WoodType woodType) {
		ThinTreeRegistryItem obj = new ThinTreeRegistryItem(parent, woodType);
		trees.add(obj);
		return obj;
	}


	public static StoneItemSet createStoneItemSet(StoneSet parent) {
		return new StoneItemSet(parent);
	}

	public static RegistryObject<BlockItem> registerSimpleBlockItem(RegistryObject<? extends Block> block) {
		RegistryObject<BlockItem> item = registerFromBlock(block);
		blockItems.add(item);
		return item;
	}
}
