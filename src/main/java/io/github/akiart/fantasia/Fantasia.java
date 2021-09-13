package io.github.akiart.fantasia;

import io.github.akiart.fantasia.client.particle.FParticleTypes;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.capability.Capabilities;
import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.item.itemGroup.FItemModelProperties;
import io.github.akiart.fantasia.common.potion.FEffects;
import io.github.akiart.fantasia.common.potion.FPotions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Fantasia.ID)
public class Fantasia
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String ID = "fantasia";

    public Fantasia() {
        GeckoLib.initialize();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::clientSetup);
        bus.addListener(this::commonSetup);

        initRegistries(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void initRegistries(IEventBus bus) {

        // Game Objects
        FItems.ITEMS.register(bus);
        FBlocks.BLOCKS.register(bus);
        FFluids.FLUIDS.register(bus);
        FEffects.EFFECTS.register(bus);
        FPotions.POTIONS.register(bus);

        FParticleTypes.PARTICLES.register(bus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        Capabilities.registerCapabilities();
    }

    public void clientSetup(FMLClientSetupEvent event) {
        FItemModelProperties.setItemModelProperties();
    }
}
