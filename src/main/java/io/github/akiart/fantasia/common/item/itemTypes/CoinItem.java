package io.github.akiart.fantasia.common.item.itemTypes;

import io.github.akiart.fantasia.common.item.itemGroup.FItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;

public class CoinItem extends Item implements ILargeStack {
    public CoinItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .tab(FItemGroup.FANTASIA));
    }

    @Override
    public int getCount(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt("count") : 0;
    }

    @Override
    public void setCount(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt("count", value);
    }

    @Override
    public int getMaxCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if(context.getLevel().isClientSide() && context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.TARGET)) {
            ItemStack stack = context.getItemInHand();
            setCount(stack, getCount(stack) + 10);
            context.getPlayer().sendMessage(new StringTextComponent("Count: " + getCount(stack)), context.getPlayer().getUUID());
        }

        return super.useOn(context);
    }
}
