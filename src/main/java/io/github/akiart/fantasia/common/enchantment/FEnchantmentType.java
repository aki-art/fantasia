package io.github.akiart.fantasia.common.enchantment;

import io.github.akiart.fantasia.common.item.itemType.JavelinItem;
import net.minecraft.enchantment.EnchantmentType;

public class FEnchantmentType {
    EnchantmentType JAVELIN = EnchantmentType.create("javelin", item -> item instanceof JavelinItem);
}
