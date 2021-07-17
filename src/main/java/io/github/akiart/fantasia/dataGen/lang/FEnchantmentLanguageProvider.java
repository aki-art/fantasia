package io.github.akiart.fantasia.dataGen.lang;

import io.github.akiart.fantasia.common.enchantment.FEnchantments;

public class FEnchantmentLanguageProvider {
    FLanguageProvider provider;

    public FEnchantmentLanguageProvider(FLanguageProvider provider) {
        this.provider = provider;
    }

    protected void addTranslations() {
        provider.add(FEnchantments.POISON_PROTECTION.get(), "Poison Protection");
        provider.add(FEnchantments.POISON_THORNS.get(), "Poison Thorns");
        provider.add(FEnchantments.POISON_TOUCH.get(), "Poison Touch");
    }
}
