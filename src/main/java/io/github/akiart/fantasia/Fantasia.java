package io.github.akiart.fantasia;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Fantasia.ID)
public class Fantasia
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String ID = "fantasia";

    public Fantasia() {
    }
}
