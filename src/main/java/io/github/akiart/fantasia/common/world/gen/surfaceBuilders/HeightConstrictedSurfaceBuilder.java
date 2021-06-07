package io.github.akiart.fantasia.common.world.gen.surfaceBuilders;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class HeightConstrictedSurfaceBuilder extends SurfaceBuilder<HeightConstrictedSurfaceBuilderConfig> {

	public HeightConstrictedSurfaceBuilder(Codec<HeightConstrictedSurfaceBuilderConfig> codec) {
		super(codec);
	}

	@Override
	public void apply(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise,
			BlockState defaultBlock, BlockState defaultFluid, int sealevel, long seed,
			HeightConstrictedSurfaceBuilderConfig config) {
		
	      BlockState top = config.getTopMaterial();
	      BlockState under = config.getUnderMaterial();
	      ConfiguredSurfaceBuilder<?> thisBuilder = biomeIn.getGenerationSettings().getSurfaceBuilder().get();
	      
	      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
	      
	      int i = -1;
	      int j = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
	      int k = x & 15;
	      int l = z & 15;
	      
	      for(int y = startHeight; y >= 0; --y) {
	    	  
	          blockpos$mutable.set(k, y, l);
	          
	    	  if(!isMatchingBuilder(chunkIn, x, y, z, thisBuilder, blockpos$mutable))
	    		  continue;
	    	  
	          BlockState state = chunkIn.getBlockState(blockpos$mutable);
	          
	          if (state.isAir()) {
	              i = -1;
	           } else if (state.is(defaultBlock.getBlock())) {
	              if (i == -1) {
	                 if (j <= 0) {
	                    top = Blocks.AIR.defaultBlockState();
	                    under = defaultBlock;
	                 } else if (y >= sealevel - 4 && y <= sealevel + 1) {
	                    top =  config.getTopMaterial();
	                    under = config.getUnderMaterial();
	                 }

	                 if (y < sealevel && (top == null || top.isAir())) {
	                    if (biomeIn.getTemperature(blockpos$mutable.set(x, y, z)) < 0.15F) {
	                       top = Blocks.ICE.defaultBlockState();
	                    } else {
	                       top = defaultFluid;
	                    }

	                    blockpos$mutable.set(k, y, l);
	                 }

	                 i = j;
	                 if (y >= sealevel - 1) {
	                    chunkIn.setBlockState(blockpos$mutable, top, false);
	                 } else {
	                    chunkIn.setBlockState(blockpos$mutable, under, false);
	                 }
	              } else if (i > 0) {
	                 --i;
	                 chunkIn.setBlockState(blockpos$mutable, under, false);
	              }
	           }
	      }     
	}

	private boolean isMatchingBuilder(IChunk chunkIn, int x, int y, int z, ConfiguredSurfaceBuilder<?> thisBuilder,
			BlockPos.Mutable blockpos$mutable) {
		ConfiguredSurfaceBuilder<?> thatBuilder = chunkIn.getBiomes().getNoiseBiome(x, y, z).getGenerationSettings().getSurfaceBuilder().get();
		return thatBuilder.equals(thisBuilder);
	}
}
