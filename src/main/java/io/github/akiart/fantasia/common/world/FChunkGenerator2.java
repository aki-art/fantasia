//package io.github.akiart.fantasia.common.world;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import io.github.akiart.fantasia.common.world.gen.surfaceBuilders.caveBiome.FSurfaceBuilder;
//import io.github.akiart.fantasia.lib.FastNoiseLite;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.util.SharedSeedRandom;
//import net.minecraft.util.math.ChunkPos;
//import net.minecraft.world.IBlockReader;
//import net.minecraft.world.IWorld;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.provider.BiomeProvider;
//import net.minecraft.world.chunk.IChunk;
//import net.minecraft.world.gen.*;
//import net.minecraft.world.gen.feature.structure.StructureManager;
//import net.minecraft.world.gen.settings.NoiseSettings;
//import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
//
//import java.util.Set;
//import java.util.function.Supplier;
//import java.util.stream.IntStream;
//
//public class FChunkGenerator2 extends ChunkGenerator {
//
//    private Supplier<DimensionSettings> settings;
//
//    private int terrainHeight = 135;
//    private int caveWorldCeiling = 118;
//    private int caveWorldFloor = 8;
//
//    private static final BlockState AIR = Blocks.AIR.defaultBlockState();
//    private static final BlockState RED = Blocks.RED_WOOL.defaultBlockState();
//    private static final BlockState ORANGE = Blocks.ORANGE_WOOL.defaultBlockState();
//    private static final BlockState YELLOW = Blocks.YELLOW_WOOL.defaultBlockState();
//    private static final BlockState STONE = Blocks.STONE.defaultBlockState();
//    protected final INoiseGenerator surfaceNoise;
//    private final OctavesNoiseGenerator mainPerlinNoise;
//    private final int chunkHeight;
//    private final int chunkWidth;
//    private final int chunkCountX;
//    private final int chunkCountY;
//    private final int chunkCountZ;
//    FastNoiseLite cavityNoise;
//    protected final SharedSeedRandom random;
//
//    public static final Codec<FChunkGenerator2> CODEC = RecordCodecBuilder
//            .create((config) -> config.group(
//                    BiomeProvider.CODEC.fieldOf("biome_source").forGetter((entry) -> entry.biomeSource),
//                    DimensionSettings.CODEC.fieldOf("settings").forGetter((entry) -> entry.settings))
//                    .apply(config, config.stable(FChunkGenerator2::new)));
//
//
//    public FChunkGenerator2(BiomeProvider biomeProvider, Supplier<DimensionSettings> settings) {
//        super(biomeProvider, biomeProvider, settings.get().structureSettings(), 0);
//
//        this.settings = settings;
//
//        random = new SharedSeedRandom(0);
//
//        NoiseSettings noiseSettings = settings.get().noiseSettings();
//
//        surfaceNoise = new PerlinNoiseGenerator(random, IntStream.rangeClosed(-3, 0));
//        mainPerlinNoise = new OctavesNoiseGenerator(random, IntStream.rangeClosed(-7, 0));
//
//        this.chunkHeight = noiseSettings.noiseSizeVertical() * 4;
//        this.chunkWidth = noiseSettings.noiseSizeHorizontal() * 4;
//        this.chunkCountX = 16 / this.chunkWidth;
//        this.chunkCountY = noiseSettings.height() / this.chunkHeight;
//        this.chunkCountZ = 16 / this.chunkWidth;
//
//        cavityNoise = new FastNoiseLite();
//        cavityNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
//        cavityNoise.SetFrequency(0.010f);
//        cavityNoise.SetFractalType(FastNoiseLite.FractalType.Ridged);
//        cavityNoise.SetFractalGain(-0.38f);
//        cavityNoise.SetFractalOctaves(4);
//    }
//
//    protected double getYOffset(double y) {
//        // Got this from https://mycurvefit.com/
//        return (-1.110223f * Math.exp(-16) + 0.005636364f * y + 0.00007272727f * y * y) - 0.9f;
//    }
//
//    protected boolean isAir(int x, int y, int z) {
//        float h = getCaveNoise(x, y, z);
//        if (y <= caveWorldFloor) {
//            h -= 16f * Math.exp(-0.4f * (y - 2f));
//        }
//        return h > getYOffset(y);
//    }
//
//    protected float getCaveNoise(int x, int y, int z) {
//        y *= 1.66f;
//        float val = cavityNoise.GetNoise(x, y, z);
//        val *= -1.34f;
//        return val;
//    }
//
//    // build surface to all biomes, not just the one sampled at world height like vanilla does
//    public void buildSurfaceAndBedrock(WorldGenRegion region, IChunk chunk) {
//
//        ChunkPos chunkpos = chunk.getPos();
//        int chunkX = chunkpos.getMinBlockX();
//        int chunkZ = chunkpos.getMinBlockZ();
//
//        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
//        sharedseedrandom.setBaseChunkSeed(chunkpos.x, chunkpos.z);
//
//        double noiseScale = 0.0625D;
//
//        for(int xOffset = 0; xOffset < 16; ++xOffset) {
//            for(int zOffset = 0; zOffset < 16; ++zOffset) {
//
//                int x = chunkX + xOffset;
//                int z = chunkZ + zOffset;
//                int y = chunk.getHeight(Heightmap.Type.WORLD_SURFACE_WG, xOffset, zOffset) + 1;
//
//                double noiseVal = surfaceNoise.getSurfaceNoiseValue(
//                        (double)x * noiseScale,
//                        (double)z * noiseScale,
//                        noiseScale,
//                        (double)xOffset * noiseScale) * 15.0D;
//
//                Set<Biome> biomes = ((FBiomeProvider)biomeSource).getAllVerticalBiomes((x << 2) + 2, (z << 2) + 2);
//                BlockState baseStone;
//                BlockState fluid = settings.get().getDefaultFluid();
//                for(Biome biome : biomes) {
//                    ConfiguredSurfaceBuilder<?> base = biome.getGenerationSettings().getSurfaceBuilder().get();
//
////                    if(base != null && base.surfaceBuilder instanceof FSurfaceBuilder) {
////                        baseStone = base.config().getUnderMaterial();
////                    } else baseStone = STONE;
//                    biome.buildSurfaceAt(sharedseedrandom, chunk, x, z, y, noiseVal, STONE, fluid, getSeaLevel(), region.getSeed());
//                }
//            }
//        }
//
//        //setBedrock(chunk, sharedseedrandom);
//    }
//
//    @Override
//    protected Codec<? extends ChunkGenerator> codec() {
//        return CODEC;
//    }
//
//    @Override
//    public ChunkGenerator withSeed(long seed) {
//        return null;
//    }
//
//    @Override
//    public void fillFromNoise(IWorld world, StructureManager structureManager, IChunk chunk) {
//
//    }
//
//    @Override
//    public int getBaseHeight(int p_222529_1_, int p_222529_2_, Heightmap.Type p_222529_3_) {
//        return 0;
//    }
//
//    @Override
//    public IBlockReader getBaseColumn(int p_230348_1_, int p_230348_2_) {
//        return null;
//    }
//}
