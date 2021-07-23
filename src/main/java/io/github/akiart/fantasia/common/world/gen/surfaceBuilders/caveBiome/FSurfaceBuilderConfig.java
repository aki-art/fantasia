package io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;

public class FSurfaceBuilderConfig implements ISurfaceBuilderConfig {

    private final BlockState topMaterial;
    private final BlockState caveWallMaterial;
    private final BlockState underMaterial;
    private final BlockState airMaterial;
    private final BlockState borderMaterial;
    private final int borderThickness;

    public static final Codec<FSurfaceBuilderConfig> CODEC = RecordCodecBuilder
            .create((surfBuilderConfig) -> surfBuilderConfig
                    .group(
                            BlockState.CODEC
                                    .fieldOf("top_material")
                                    .forGetter(config -> config.topMaterial),
                            BlockState.CODEC
                                    .fieldOf("cave_wall_material")
                                    .forGetter(config -> config.caveWallMaterial),
                            BlockState.CODEC
                                    .fieldOf("air_material")
                                    .forGetter(config -> config.airMaterial),
                            BlockState.CODEC
                                    .fieldOf("under_material")
                                    .forGetter(config -> config.underMaterial),
                            BlockState.CODEC
                                    .fieldOf("border_material")
                                    .forGetter(config -> config.borderMaterial),
                            Codec.INT
                                    .fieldOf("border_thickness")
                                    .forGetter(config -> config.borderThickness))
                    // RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((source) -> source.biomeRegistry)
                    .apply(surfBuilderConfig, FSurfaceBuilderConfig::new));


    public FSurfaceBuilderConfig(BlockState topMaterial, BlockState caveWallMaterial, BlockState airMaterial, BlockState underMaterial, BlockState borderMaterial, int borderThickness) {
        this.topMaterial = topMaterial;
        this.caveWallMaterial = caveWallMaterial;
        this.airMaterial = airMaterial;
        this.underMaterial = underMaterial;
        this.borderMaterial = borderMaterial;
        this.borderThickness = borderThickness;
    }


    public BlockState getAirMaterial() {
        return airMaterial;
    }

    @Override
    public BlockState getTopMaterial() {
        return topMaterial;
    }

    @Override
    public BlockState getUnderMaterial() {
        return underMaterial;
    }


    public BlockState getCaveWallMaterial() {
        return caveWallMaterial;
    }

    public BlockState getBorderMaterial() {
        return borderMaterial;
    }

    public int getBorderThickness() {
        return borderThickness;
    }

}
