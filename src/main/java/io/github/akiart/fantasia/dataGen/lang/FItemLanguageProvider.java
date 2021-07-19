package io.github.akiart.fantasia.dataGen.lang;

import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.potion.FPotions;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;

public class FItemLanguageProvider {

    FLanguageProvider provider;

    public FItemLanguageProvider(FLanguageProvider provider) {
        this.provider = provider;
    }

    void addTranslations() {
        boats();
        javelins();
        foods();
        spawnEggs();
        potions();

        provider.add(FItems.ACID_BUCKET.get(), "Acid Bucket");
        provider.add(FItems.FROSTWORK_PICKAXE.get(), "Frostwork Pickaxe");
        provider.add(FItems.PTARMIGAN_EGG.get(), "Ptarmigan Egg");
    }

    private void potions() {
        addPotion(FPotions.ACID_REPEL, "Acid Repel", false);
        addPotion(FPotions.FROST_RESIST, "Frost Resistance", false);
        addPotion(FPotions.DEFENDER, "The Defender", false);
    }

    private void addPotion(RegistryObject<Potion> potion, String name, boolean poison) {
        String item = poison ? "Poison" : "Potion";
        String potionName =  potion.get().getRegistryName().getPath();
        provider.add(Items.POTION.getDescriptionId() + ".effect." + potionName, item + " of " + name);
        provider.add(Items.SPLASH_POTION.getDescriptionId() + ".effect." + potionName, "Splash " + item + " of " + name);
        provider.add(Items.LINGERING_POTION.getDescriptionId() + ".effect." + potionName, "Lingering " + item + " of " + name);
        provider.add(Items.TIPPED_ARROW.getDescriptionId() + ".effect." + potionName, "Arrow of " + name);
    }

    private void spawnEggs() {
        provider.add(FItems.VALRAVN_SPAWN_EGG.get(), "Valravn Spawn Egg");
        provider.add(FItems.SABER_CAT_SPAWN_EGG.get(), "Saber Cat Spawn Egg");
        provider.add(FItems.PTARMIGAN_SPAWN_EGG.get(), "Ptarmigan Spawn Egg");
    }

    private void foods() {
        provider.add(FItems.PTARMIGAN_STEW.get(), "Ptarmigan Stew");
        provider.add(FItems.ROAST_PTARMIGAN.get(), "Roast Ptarmigan");
        provider.add(FItems.RAW_PTARMIGAN.get(), "Raw Ptarmigan");
        provider.add(FItems.SNOW_BERRY.get(), "Snowberry");
    }

    private void javelins() {
        provider.add(FItems.WOODEN_JAVELIN.get(), "Wooden Javelin");
        provider.add(FItems.STONE_JAVELIN.get(), "Stone Javelin");
        provider.add(FItems.GOLD_JAVELIN.get(), "Gold Javelin");
        provider.add(FItems.IRON_JAVELIN.get(), "Iron Javelin");
        provider.add(FItems.DIAMOND_JAVELIN.get(), "Diamond Javelin");
        provider.add(FItems.NETHERITE_JAVELIN.get(), "Netherite Javelin");
        provider.add(FItems.WOLFRAMITE_JAVELIN.get(), "Wolframite Javelin");
        provider.add(FItems.GHASTLY_JAVELIN.get(), "Ghastly Javelin");
        provider.add(FItems.FROSTWORK_BOLT.get(), "Frostwork Ballista Bolt");
        provider.add(FItems.TIPPED_SABER_TOOTH_JAVELIN.get(), "Sabertooth Javelin");
    }

    private void boats() {
        provider.add(FItems.ASPEN_BOAT.get(), "Aspen Boat");
        provider.add(FItems.FROZEN_ELM_BOAT.get(), "Frozen Elm Boat");
    }
}
