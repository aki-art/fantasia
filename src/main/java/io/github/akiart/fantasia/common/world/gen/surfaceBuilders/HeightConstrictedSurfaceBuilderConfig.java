package io.github.akiart.fantasia.common.world.gen.surfaceBuilders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;

public class HeightConstrictedSurfaceBuilderConfig implements ISurfaceBuilderConfig {

	private final BlockState topMaterial;
	private final BlockState underMaterial;

	// TODO: make this a depth from surface rather than a set Y range
	public static final Codec<HeightConstrictedSurfaceBuilderConfig> CODEC = RecordCodecBuilder
			.create((surfBuilderConfig) -> {
				return surfBuilderConfig
						.group(
						       BlockState.CODEC 
							       .fieldOf("top_material")
							       .forGetter((config) -> { return config.topMaterial;}), 
						       BlockState.CODEC
						       	   .fieldOf("under_material")
						       	   .forGetter((config) -> { return config.underMaterial;}))
						       // RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((source) -> source.biomeRegistry)
						.apply(surfBuilderConfig, HeightConstrictedSurfaceBuilderConfig::new);
			});
	
	
	public HeightConstrictedSurfaceBuilderConfig(BlockState topMaterial, BlockState underMaterial) {
		this.topMaterial = topMaterial;
		this.underMaterial = underMaterial;
	}

	@Override
	public BlockState getTopMaterial() {
	      return topMaterial;
	}

	@Override
	public BlockState getUnderMaterial() {
	      return underMaterial;
	}
}
