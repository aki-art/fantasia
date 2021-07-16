package io.github.akiart.fantasia.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class PoisonProtectionEnchantment extends Enchantment {

    protected PoisonProtectionEnchantment(Rarity rarity, EquipmentSlotType... slots) {
        super(rarity, EnchantmentType.ARMOR, slots);
    }

    // EntityEvents#onPotionApply
}
