package io.github.akiart.fantasia.common.enchantment;

import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.Map;
import java.util.Random;

public class PoisonThornsEnchantment extends Enchantment {

    protected PoisonThornsEnchantment(Rarity rarity, EquipmentSlotType... slots) {
        super(rarity, EnchantmentType.ARMOR, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.getItem() instanceof ArmorItem || super.canEnchant(itemStack);
    }

    @Override
    public void doPostHurt(LivingEntity target, Entity source, int amplifier) {
        Random random = target.getRandom();
        Map.Entry<EquipmentSlotType, ItemStack> randomPiece = EnchantmentHelper.getRandomItemWith(FEnchantments.POISON_THORNS.get(), target);
        if (source instanceof LivingEntity && shouldHit(amplifier, random)) {
            ((LivingEntity)source).addEffect(new EffectInstance(Effects.POISON, 10 + amplifier * 5, amplifier));

            if (randomPiece != null) {
                randomPiece.getValue().hurtAndBreak(2, target, (living) -> {
                    living.broadcastBreakEvent(randomPiece.getKey());
                });
            }
        }
    }

    @Override
    public boolean checkCompatibility(Enchantment otherEnchantment) {
        return !(otherEnchantment instanceof ThornsEnchantment);
    }

    public static boolean shouldHit(float level, Random random) {
        return level > 0 && random.nextFloat() < 0.15F * level;
    }

    public static int getDamage(int level, Random random) {
        return level > 10 ? level - 10 : 1 + random.nextInt(4);
    }
}
