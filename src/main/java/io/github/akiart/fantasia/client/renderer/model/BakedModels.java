package io.github.akiart.fantasia.client.renderer.model;

import io.github.akiart.fantasia.Fantasia;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BakedModels {
    static ModelResourceLocation missing =  new ModelResourceLocation("builtin/missing", "missing");
    static IBakedModel missingModel;
    private static final HashMap<ModelResourceLocation, IBakedModel> modelCache = new HashMap<>();

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        missingModel = event.getModelRegistry().get(missing);
        addJavelins(event);
        Fantasia.LOGGER.info("finished adding baked models, all {} of them.", modelCache.size() );
    }

    private static void addJavelins(ModelBakeEvent event) {
        addJavelin("wooden_javelin", event);
        addJavelin("stone_javelin", event);
        addJavelin("gold_javelin", event);
        addJavelin("iron_javelin", event);
        addJavelin("gold_javelin", event);
        addJavelin("diamond_javelin", event);
        addJavelin("netherite_javelin", event);
        addJavelin("wolframite_javelin", event);
        addJavelin("ghastly_javelin", event);
        addJavelin("frostwork_bolt", event);
    }

    private static void addJavelin(String name, ModelBakeEvent event) {
        ModelResourceLocation loc = new ModelResourceLocation("fantasia:gold_javelin_inventory#inventory");
        modelCache.put(loc, event.getModelRegistry().getOrDefault(loc, missingModel));
    }

    public static IBakedModel get(ModelResourceLocation loc) {
        return modelCache.getOrDefault(loc, missingModel);
    }
}
