package io.github.akiart.fantasia.common.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.function.Supplier;

public class FArmorMaterial implements IArmorMaterial {
    private static final int[] DURABILITY_PER_SLOT = new int[]{13, 15, 16, 11};

    public static final FArmorMaterial DRUID = new FArmorMaterial(
            "druid",
            25,
            new int[] {2, 5, 6, 2},
            9,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            3f,
            0.1f,
            () -> Ingredient.of(ItemTags.LEAVES)
    );

    private final String name;
    private final int durabilityMultiplier;

    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairIngredient;

    public FArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairItem) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyValue<>(repairItem);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType pSlot) {
        return DURABILITY_PER_SLOT[pSlot.getIndex()] * durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType pSlot) {
        return slotProtections[pSlot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
