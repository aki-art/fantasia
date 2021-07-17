package io.github.akiart.fantasia.common.potion;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Fantasia.ID);

    public static RegistryObject<Potion> ACID_REPEL = POTIONS.register("acid_repel", () -> new Potion(new EffectInstance(FEffects.ACID_REPEL.get(), 1800)));
    public static RegistryObject<Potion> LONG_ACID_REPEL = POTIONS.register("long_acid_repel", () -> new Potion("acid_repel", new EffectInstance(FEffects.ACID_REPEL.get(), 4000)));
    public static RegistryObject<Potion> STRONG_ACID_REPEL = POTIONS.register("strong_acid_repel", () -> new Potion("acid_repel", new EffectInstance(FEffects.ACID_REPEL.get(), 1800, 2)));

    public static RegistryObject<Potion> FROST_RESIST = POTIONS.register("frost_resist", () -> new Potion(new EffectInstance(FEffects.FROST_RESISTANCE.get(), 1800)));
    public static RegistryObject<Potion> LONG_FROST_RESIST = POTIONS.register("long_frost_resist", () -> new Potion("frost_resist", new EffectInstance(FEffects.FROST_RESISTANCE.get(), 4000)));
    public static RegistryObject<Potion> STRONG_FROST_RESIST = POTIONS.register("strong_frost_resist", () -> new Potion("frost_resist", new EffectInstance(FEffects.FROST_RESISTANCE.get(), 1800, 2)));

    public static RegistryObject<Potion> DEFENDER = POTIONS.register("defender", () -> new Potion(
            new EffectInstance(FEffects.ACID_REPEL.get(), 1800, 1),
            new EffectInstance(FEffects.FROST_RESISTANCE.get(), 1800, 1),
            new EffectInstance(Effects.FIRE_RESISTANCE, 1800, 1),
            new EffectInstance(Effects.DAMAGE_RESISTANCE, 1800, 1)
    ));
}
