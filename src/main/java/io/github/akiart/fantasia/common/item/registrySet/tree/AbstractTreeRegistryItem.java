package io.github.akiart.fantasia.common.item.registrySet.tree;

import io.github.akiart.fantasia.client.renderer.tileentity.itemStackRenderer.ISTERs;
import io.github.akiart.fantasia.common.block.registrySet.TreeRegistryObject;
import io.github.akiart.fantasia.common.block.registrySet.trees.AbstractTreeRegistryObject;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.item.ItemRegistryUtil;
import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraftforge.fml.RegistryObject;

public abstract class AbstractTreeRegistryItem <T extends AbstractTreeRegistryObject> {
    T tree;

    public final RegistryObject<BlockItem> button;
    public final RegistryObject<BlockItem> chest;
    public final RegistryObject<BlockItem> door;
    public final RegistryObject<BlockItem> fence;
    public final RegistryObject<BlockItem> fenceGate;
    public final RegistryObject<BlockItem> planks;
    public final RegistryObject<BlockItem> pressurePlate;
    public final RegistryObject<SignItem> sign;
    public final RegistryObject<BlockItem> slab;
    public final RegistryObject<BlockItem> stairs;
    public final RegistryObject<BlockItem> trapDoor;

    public AbstractTreeRegistryItem(T tree, WoodType woodType) {

        this.tree = tree;

        button = tryRegisterFromBlock(tree.getButton());
        chest = FItems.ITEMS.register(getName() + "_chest",
                () -> new BlockItem(tree.getChest().get(),
                        new Item.Properties()
                                .tab(FItemGroup.FANTASIA)
                                .setISTER(() -> () -> ISTERs.createChestTileEntity(woodType))));

        door = tryRegisterFromBlock(tree.getDoor());
        fence = tryRegisterFromBlock(tree.getFence());
        fenceGate = tryRegisterFromBlock(tree.getFenceGate());
        planks = tryRegisterFromBlock(tree.getPlanks());
        pressurePlate = tryRegisterFromBlock(tree.getPressurePlate());
        sign = ItemRegistryUtil.register(getName() + "_sign", () -> new SignItem(new Item.Properties()
                .tab(FItemGroup.FANTASIA)
                .stacksTo(16),
                tree.getSign().get(),
                tree.getWallSign().get()));

        slab = tryRegisterFromBlock(tree.getSlab());
        stairs = tryRegisterFromBlock(tree.getStairs());
        trapDoor = tryRegisterFromBlock(tree.getTrapDoor());
    }

    public T getTree() {
        return tree;
    }

    RegistryObject<BlockItem> tryRegisterFromBlock(RegistryObject<? extends Block> block) {
        return block != null ? ItemRegistryUtil.registerFromBlock(block) : null;
    }

    public String getName() {
        return tree.getName();
    }
}
