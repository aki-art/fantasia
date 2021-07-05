package io.github.akiart.fantasia.common.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.NoiseSettings;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class FChunkGenerator2 extends ChunkGenerator {

    private Supplier<DimensionSettings> settings;

    private int terrainHeight = 135;
    private int caveWorldCeiling = 118;
    private int seed = 1337;

    private SurfaceGenerator surface;

    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
    private static final BlockState RED = Blocks.RED_WOOL.defaultBlockState();
    private static final BlockState ORANGE = Blocks.ORANGE_WOOL.defaultBlockState();
    private static final BlockState YELLOW = Blocks.YELLOW_WOOL.defaultBlockState();
    private static final BlockState STONE = Blocks.STONE.defaultBlockState();
    private final BlockState defaultBlock;
    private final BlockState defaultFluid;

    protected final INoiseGenerator surfaceNoise;
    private final OctavesNoiseGenerator mainPerlinNoise;
    private final int chunkHeight;
    private final int chunkWidth;
    private final int chunkCountX;
    private final int chunkCountY;
    private final int chunkCountZ;
    private final int sampleCount;
    protected final SharedSeedRandom random;

    public static final Codec<FChunkGenerator2> CODEC = RecordCodecBuilder
            .create((config) -> config.group(
                    BiomeProvider.CODEC.fieldOf("biome_source").forGetter((entry) -> entry.biomeSource),
                    DimensionSettings.CODEC.fieldOf("settings").forGetter((entry) -> entry.settings))
                    .apply(config, config.stable(FChunkGenerator2::new)));


    public FChunkGenerator2(BiomeProvider biomeProvider, Supplier<DimensionSettings> settings) {
        super(biomeProvider, biomeProvider, settings.get().structureSettings(), 0);

        this.settings = settings;

        random = new SharedSeedRandom(0);

        NoiseSettings noiseSettings = settings.get().noiseSettings();

        surfaceNoise = new PerlinNoiseGenerator(random, IntStream.rangeClosed(-3, 0));
        mainPerlinNoise = new OctavesNoiseGenerator(random, IntStream.rangeClosed(-7, 0));

        this.chunkHeight = 4;//noiseSettings.noiseSizeVertical() * 4;
        this.chunkWidth = 1; //noiseSettings.noiseSizeHorizontal() * 4;
        this.chunkCountX = 16 / this.chunkWidth;
        this.chunkCountY = noiseSettings.height() / this.chunkHeight;
        this.chunkCountZ = 16 / this.chunkWidth;
        sampleCount = chunkCountX * chunkCountY * chunkCountZ;

        this.defaultBlock = settings.get().getDefaultBlock();
        this.defaultFluid = settings.get().getDefaultFluid();

        this.surface = new SurfaceGenerator(biomeProvider, 0, chunkCountY, chunkHeight, caveWorldCeiling - 16, 255, 1.48f);
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return null;
    }

    @Override
    public void fillFromNoise(IWorld world, StructureManager structureManager, IChunk chunk) {
        ChunkPos chunkpos = chunk.getPos();
        int chunkX = chunkpos.getMinBlockX();
        int chunkZ = chunkpos.getMinBlockZ();
        Heightmap oceanHeightMap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap surfaceHeightMap = chunk.getOrCreateHeightmapUnprimed(Heightmap.Type.WORLD_SURFACE_WG);

//        float[][][] samples = new float[chunkCountX][chunkCountZ][chunkCountY]; // XZY format. would a 1D integer index be faster?
//        surface.fillSamples(samples, chunkX, chunkZ);
//
//        for (int xOffset = 0; xOffset < chunkCountX; xOffset++) {
//            for (int zOffset = 0; zOffset < chunkCountZ; zOffset++) {
//                BlockPos.Mutable pos = new BlockPos.Mutable(xOffset * 4, 0, zOffset * 4);
//                for (int y = chunkCountY - 1; y > 0; --y) {
//                    if(samples[xOffset][zOffset][y] > 0) {
//                        pos.setY(y * 4);
//                        chunk.setBlockState(pos, ORANGE, false);
//                    }
//                }
//            }
//        }
//
        for (int xOffset = 0; xOffset < 16; xOffset += chunkWidth) {
            for (int zOffset = 0; zOffset < 16; zOffset+= chunkWidth) {

                int x = chunkX + xOffset;
                int z = chunkZ + zOffset;

                float[] testColumn = surface.getNoiseColumn(x, z);

                BlockPos.Mutable pos = new BlockPos.Mutable(xOffset, 0, zOffset);

                double value = testColumn[chunkCountY];
                //surface.fillNoiseColumn(samples[xOffset + zOffset * chunkWidth], x, z);

                for (int y = surface.getMaxY(); y > surface.getMinY(); y--) {

                    double nextValue = testColumn[y + 1];

                    for (int ys = 0; ys < chunkHeight; ++ys) {
                        pos.setY(y * chunkHeight + ys);
                        double interpolated = MathHelper.clampedLerp(nextValue, value, ((float)ys / (float)chunkHeight));
                        if (interpolated > 0) {
                            chunk.setBlockState(pos, STONE, false);
                        }
                    }

                    value = nextValue;

                    oceanHeightMap.update(xOffset, y, zOffset, RED);
                    surfaceHeightMap.update(xOffset, y, zOffset, RED);
                }
            }
        }
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Type heightmapType) {
        return terrainHeight;
    }

    @Override
    public IBlockReader getBaseColumn(int x, int z) {
        return null; //WIP
    }

    // Build the surface of biomes
    @Override
    public void buildSurfaceAndBedrock(WorldGenRegion region, IChunk chunk) {
        ChunkPos chunkpos = chunk.getPos();
        int chunkX = chunkpos.getMinBlockX();
        int chunkZ = chunkpos.getMinBlockZ();

        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        sharedseedrandom.setBaseChunkSeed(chunkpos.x, chunkpos.z);

        double noiseScale = 0.0625D;

        for(int xOffset = 0; xOffset < 16; ++xOffset) {
            for(int zOffset = 0; zOffset < 16; ++zOffset) {

                int x = chunkX + xOffset;
                int z = chunkZ + zOffset;
                int y = chunk.getHeight(Heightmap.Type.WORLD_SURFACE_WG, xOffset, zOffset) + 1;

                double noiseVal = surfaceNoise.getSurfaceNoiseValue(
                        (double)x * noiseScale,
                        (double)z * noiseScale,
                        noiseScale,
                        (double)xOffset * noiseScale) * 15.0D;

                if(biomeSource instanceof FBiomeProvider) {
                    // build surface to all biomes, not just the one sampled at world height like vanilla does

                    Set<Biome> biomes = ((FBiomeProvider) biomeSource).getAllVerticalBiomes(x, z);
                    for (Biome biome : biomes) {
                        biome.buildSurfaceAt(sharedseedrandom, chunk, x, z, y, noiseVal, defaultBlock, defaultFluid, getSeaLevel(), region.getSeed());
                    }
                }
                else {
                    // if this generator is being used without the matching biome source, just sample the top biome instead
                    Biome biome = biomeSource.getNoiseBiome(x << 2, 63, z << 2);
                    biome.buildSurfaceAt(sharedseedrandom, chunk, x, z, y, noiseVal, defaultBlock, defaultFluid, getSeaLevel(), region.getSeed());
                }
            }
        }

        //setBedrock(chunk, sharedseedrandom);
    }

}
