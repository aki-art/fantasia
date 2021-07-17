package io.github.akiart.fantasia.common.potion;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Fantasia.ID);

    public static final RegistryObject<Effect> ACID_REPEL = EFFECTS.register("acid_repel", () -> new AcidRepelEffect(EffectType.BENEFICIAL, Constants.Colors.ACID_GREEN));
    public static final RegistryObject<Effect> FROST_RESISTANCE = EFFECTS.register("frost_resist", () -> new FrostResistEffect(EffectType.BENEFICIAL, Constants.Colors.FREEZY_BLUE));
}
