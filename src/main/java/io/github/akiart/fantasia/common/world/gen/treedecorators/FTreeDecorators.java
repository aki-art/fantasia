package io.github.akiart.fantasia.common.world.gen.treedecorators;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FTreeDecorators {
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister
		.create(ForgeRegistries.TREE_DECORATOR_TYPES, Fantasia.ID);
	public static final RegistryObject<TreeDecoratorType<IcicleTreeDecorator>> ICICLE_TREE_DECORATOR = TREE_DECORATOR_TYPES
		.register("icicle_tree_decorator", () -> new TreeDecoratorType<>(IcicleTreeDecorator.CODEC));

}
