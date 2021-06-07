package io.github.akiart.fantasia.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CrystalPileFeatureConfig implements IFeatureConfig {

    public final BlockState crystalBlock;
    public final BlockState crystal;
    private final FeatureSpread radius;
    private final FeatureSpread maxPillarHeight;
    private final FeatureSpread smallCrystalMaxLength;
    private final FeatureSpread extraSmallCrystals;
    public final float smallCrystalChance;
    public final float extraSmallCrystalChance;

    public CrystalPileFeatureConfig(BlockState crystal_block, BlockState crystal, FeatureSpread radius,
                                    FeatureSpread height, FeatureSpread smallCrystalMaxLength, FeatureSpread extraSmallCrystals,float smallCrystalChance, float extraSmallCrystalChance) {
        this.crystalBlock = crystal_block;
        this.smallCrystalChance = smallCrystalChance;
        this.crystal = crystal;
        this.radius = radius;
        this.maxPillarHeight = height;
        this.smallCrystalMaxLength = smallCrystalMaxLength;
        this.extraSmallCrystals = extraSmallCrystals;
        this.extraSmallCrystalChance = extraSmallCrystalChance;

    }

    public static final Codec<CrystalPileFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder
                .group(
                        BlockState.CODEC.fieldOf("block").forGetter((config) -> {
                            return config.crystalBlock;
                        }),
                        BlockState.CODEC.fieldOf("crystal").forGetter((config) -> {
                            return config.crystal;
                        }),
                        FeatureSpread.CODEC.fieldOf("radius").forGetter((config) -> {
                            return config.radius;
                        }),
                        FeatureSpread.CODEC.fieldOf("pillar_max_height").forGetter((config) -> {
                            return config.radius;
                        }),
                        FeatureSpread.CODEC.fieldOf("small_crystal_length").forGetter((config) -> {
                            return config.smallCrystalMaxLength;
                        }),
                        FeatureSpread.CODEC.fieldOf("extra_ground_crystals_area").forGetter((config) -> {
                            return config.extraSmallCrystals;
                        }),
                        Codec.floatRange(0.0F, 1.0F).fieldOf("small_crystal_probability").forGetter((config) -> {
                            return config.smallCrystalChance;
                        }),
                        Codec.floatRange(0.0F, 1.0F).fieldOf("extra_small_crystal_probability").forGetter((config) -> {
                            return config.extraSmallCrystalChance;
                        }))
                .apply(builder, CrystalPileFeatureConfig::new);
    });

    public FeatureSpread getSmallCrystalMaxLength() {
        return smallCrystalMaxLength;
    }

    public FeatureSpread getExtraSmallCrystalsSpread() {
        return extraSmallCrystals;
    }

    public float getExtraSmallCrystalChance() {
        return extraSmallCrystalChance;
    }

    public FeatureSpread getMaxPillarHeight() {
        return this.maxPillarHeight;
    }

    public FeatureSpread getRadius() {
        return this.radius;
    }

    public FeatureSpread getSmallCrystalLength() {
        return this.smallCrystalMaxLength;
    }

    public BlockState getCrystalBlock() {
        return crystalBlock;
    }

    public BlockState getCrystal() {
        return crystal;
    }

    public float getSmallCrystalChance() {
        return smallCrystalChance;
    }
}
