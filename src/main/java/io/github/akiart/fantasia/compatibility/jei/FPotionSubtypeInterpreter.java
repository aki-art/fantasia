package io.github.akiart.fantasia.compatibility.jei;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;

import java.util.Iterator;
import java.util.List;

public class FPotionSubtypeInterpreter implements ISubtypeInterpreter {
    public static final FPotionSubtypeInterpreter INSTANCE = new FPotionSubtypeInterpreter();

    private FPotionSubtypeInterpreter() {
    }

    public String apply(ItemStack itemStack) {
        if (!itemStack.hasTag()) {
            return "";
        } else {
            Potion potionType = PotionUtils.getPotion(itemStack);
            String potionTypeString = potionType.getName("");
            StringBuilder stringBuilder = new StringBuilder(potionTypeString);
            List<EffectInstance> effects = PotionUtils.getMobEffects(itemStack);
            Iterator var6 = effects.iterator();

            while(var6.hasNext()) {
                EffectInstance effect = (EffectInstance)var6.next();
                stringBuilder.append(";").append(effect);
            }

            return stringBuilder.toString();
        }
    }
}
