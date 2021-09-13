package io.github.akiart.fantasia.common.item.itemTypes;

import net.minecraft.item.ItemStack;

// used for items with a fake stack counter for stacks beyond 64 stack size
public interface ILargeStack {
    int getCount(ItemStack stack);
    void setCount(ItemStack stack, int count);
    int getMaxCount();
}
