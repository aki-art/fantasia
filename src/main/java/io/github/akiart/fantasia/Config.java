package io.github.akiart.fantasia;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = Fantasia.ID)
public class Config {
    public static Common common;
    public static Client client;

    public static class Common {
        public DimensionSettings dimension = new DimensionSettings();
        public PortalSettings portal = new PortalSettings();
        public EquipmentSettings equipment = new EquipmentSettings();

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Dimension Settings");

            dimension.startInFantasia = builder
                    .translation(getKey("startInFantasia"))
                    .comment("If true, new worlds will spawn players in Fantasia.")
                    .define("startInFantasia", false);

            builder.pop();
            builder.push("Portal Settings");

            portal.enablePortals = builder
                    .translation(getKey("enablePortals"))
                    .comment("If true, players can activate Portals.\nAdd or remove possible destination dimensions via datapacks.")
                    .define("enablePortals", true);

            portal.areDialBlocksCraftable = builder
                    .translation(getKey("areDialBlocksCraftable"))
                    .comment("If true, players can craft the Carved Obsidian dial blocks.")
                    .define("areDialBlocksCraftable", true);

            builder.pop();
            builder.push("Equipment Settings");

            equipment.enableFrostworkPickGriefing = builder
                    .translation(getKey("enableFrostworkPickGriefing"))
                    .comment("If true, Frostwork Pickaxes can destroy blocks.")
                    .define("enableFrostworkPickGriefing", true);

            equipment.sabertoothJavelinUses = builder
                    .translation(getKey("sabertoothJavelinUses"))
                    .comment("Set default potion uses when applying a potion to a Sabertooth Javelin.")
                    .defineInRange("sabertoothJavelinUses", 16, 0, 1024);

//            equipment.saberToothInventoryVariants = builder
//                    .translation(getKey("saberToothInventoryVariants"))
//                    .comment("If true, all potion variants with their recipes will appear in creative inventory (and also JEI).")
//                    .define("saberToothInventoryVariants", true);

            equipment.frostworkAxeMaxBlocks = builder
                    .translation(getKey("frostworkAxeMaxBlocks"))
                    .comment("How many blocks can a Frostwork Axe break at once. Very large numbers may cause some lag. \n(No, you cannot cut down an entire Yggdrasil with this.)")
                    .defineInRange("frostworkAxeMaxBlocks", 64, 0, 1024);

            equipment.frostworkAxeSameOnly = builder
                    .translation(getKey("frostworkAxeSameOnly"))
                    .comment("If true, Frostwork Axes will only cut the exact same type of connected blocks down.")
                    .define("frostworkAxeSameOnly", false);

            builder.pop();
        }

        public static class DimensionSettings {
            public ForgeConfigSpec.BooleanValue startInFantasia;
        }

        public static class PortalSettings {
            public ForgeConfigSpec.BooleanValue enablePortals;
            public ForgeConfigSpec.BooleanValue areDialBlocksCraftable;
        }

        public static class EquipmentSettings {
            public ForgeConfigSpec.BooleanValue enableFrostworkPickGriefing;
            public ForgeConfigSpec.IntValue sabertoothJavelinUses;
            //public ForgeConfigSpec.BooleanValue saberToothInventoryVariants; client stuff
            public ForgeConfigSpec.IntValue frostworkAxeMaxBlocks;
            public ForgeConfigSpec.BooleanValue frostworkAxeSameOnly;
        }
    }

    public static class Client {

        RenderSettings renderSettings = new RenderSettings();

        public Client(ForgeConfigSpec.Builder builder) {

            builder.push("Rendering Settings");

            renderSettings.useFancyPortals = builder
                    .translation(getKey("useFancyPortals"))
                    .comment("If true, portals will have special effects.")
                    .define("useFancyPortals", true);

//            renderSettings.useShaderAmbient = builder
//                    .translation(getKey("useShaderAmbient"))
//                    .comment("If true, shaders will be used to tint lighting inside Fantasia for better atmosphere. (Disabled with Optifine)")
//                    .define("useShaderAmbient", true);

            builder.pop();
        }

        public static class RenderSettings {
            public ForgeConfigSpec.BooleanValue useShaderAmbient;
            public ForgeConfigSpec.BooleanValue useFancyPortals;
        }
    }

    private static String getKey(String name) {
        return Fantasia.ID + ".config." + name;
    }

    @SubscribeEvent
    public static void onConfigChanged(ModConfig.Reloading event) {
        if (event.getConfig().getModId().equals(Fantasia.ID)) {
            Fantasia.LOGGER.info("configs changed!");
            if(Config.common != null) {
                Fantasia.LOGGER.info("test is {}", Config.common.dimension);
            }
        }
    }
}