package io.github.akiart.fantasia.common.enchantment;

import io.github.akiart.fantasia.common.item.itemType.JavelinItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class PoisonTouchEnchantment extends Enchantment {
    protected PoisonTouchEnchantment(Rarity rarity, EquipmentSlotType... slotTypes) {
        super(rarity, EnchantmentType.WEAPON, slotTypes);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return item instanceof AxeItem || item instanceof JavelinItem || super.canEnchant(itemStack);
    }

    @Override
    public void doPostAttack(LivingEntity livingEntity, Entity source, int amplifier) {
        if (source instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) source;
            int duration = 20 + livingEntity.getRandom().nextInt(10 * amplifier);
            target.addEffect(new EffectInstance(Effects.POISON, duration, amplifier));
        }
    }
}
