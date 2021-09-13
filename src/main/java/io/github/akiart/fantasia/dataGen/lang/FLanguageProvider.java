package io.github.akiart.fantasia.dataGen.lang;

import io.github.akiart.fantasia.util.FDamageSource;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.data.LanguageProvider;

public class FLanguageProvider extends LanguageProvider {
    public FLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        addDeathMessage(FDamageSource.ACID,
                "%1$s tripped.",
                "%1$s has melted in acid.",
                "%1$s mistook acid for water.");

        addCreditedDeathMessage(FDamageSource.ACID,
                "%1$s fell into acid while avoiding %2$s.",
                "%1$s has melted in acid while while fighting %2$s.",
                "%1$s wanted a break from a fight with %2$s and took a bath in acid.");
    }

    private void addDeathMessage(DamageSource source, String... message) {
        for(int i = 0; i < message.length; i++) {
            add("fantasia.death.attack." + source.getMsgId() + "_" + i, message[i]);
        }
    }

    private void addCreditedDeathMessage(DamageSource source, String... message) {
        for(int i = 0; i < message.length; i++) {
            add("fantasia.death.attack." + source.getMsgId() + "_" + i + ".player", message[i]);
        }
    }
}
