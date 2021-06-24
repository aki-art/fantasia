package io.github.akiart.fantasia.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public class FItemTier implements IItemTier {

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyValue<Ingredient> repairIngredient;

    public static final FItemTier WOLFRAMITE = new FItemTier(2, 340, 6.0f, 2.5f, 30, () -> Ingredient.of(Items.APPLE));
    public static final FItemTier GHASTLY = new FItemTier(2, 1561, 6.0f, 2f, 30, () -> Ingredient.of(Items.APPLE));

    private FItemTier(int level, int uses, float speed, float damage, int enchantment, Supplier<Ingredient> repair) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantment;
        this.repairIngredient = new LazyValue<>(repair);
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }
}
