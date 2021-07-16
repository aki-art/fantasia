package io.github.akiart.fantasia.common.enchantment;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FEnchantments {
    private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Fantasia.ID);

    public static final RegistryObject<Enchantment> POISON_TOUCH = ENCHANTMENTS.register("poison_touch", () -> new PoisonTouchEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> POISON_THORNS = ENCHANTMENTS.register("poison_thorns", () -> new PoisonThornsEnchantment(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS));
    public static final RegistryObject<Enchantment> POISON_PROTECTION = ENCHANTMENTS.register("poison_protection", () -> new PoisonProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ARMOR_SLOTS));
}
