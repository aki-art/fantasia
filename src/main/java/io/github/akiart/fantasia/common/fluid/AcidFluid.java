package io.github.akiart.fantasia.common.fluid;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.particles.FParticleTypes;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.Random;

public class AcidFluid extends ForgeFlowingFluid {

    private static final int LIME_GREEN = 0xFFCBFF5C;
    private static final double R =  (double)(LIME_GREEN >> 16 & 255) / 255.0D;
    private static final double G =  (double)(LIME_GREEN >> 8 & 255) / 255.0D;
    private static final double B =  (double)(LIME_GREEN & 255) / 255.0D;

    protected AcidFluid(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSource(FluidState state) {
        return true;
    }

    @Override
    public int getAmount(FluidState state) {
        return 0;
    }

    @Override
    public Vector3d getFlow(IBlockReader reader, BlockPos pos, FluidState state) {
        return Vector3d.ZERO; // water handles this
    }

    public static ForgeFlowingFluid.Properties getProperties() {
        return new ForgeFlowingFluid.Properties(FFluids.ACID_SOURCE, FFluids.ACID_FLOWING,
                FluidAttributes
                        .builder(new ResourceLocation(Fantasia.ID, "block/acid_still"), new ResourceLocation( "minecraft:block/water_flow"))
                        //.overlay(new ResourceLocation("minecraft:block/water_overlay"))
                        //.color(LIME_GREEN)
                        .luminosity(4))
                .bucket(FItems.ACID_BUCKET)
                .tickRate(5)
                .block(FBlocks.ACID);
    }

    public static class Flowing extends AcidFluid
    {
        public Flowing(Properties properties)
        {
            super(properties);
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends AcidFluid
    {
        public Source(Properties properties)
        {
            super(properties);
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    @Override
    public IParticleData getDripParticle() {
        return FParticleTypes.DRIPPING_ACID.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(World world, BlockPos pos, FluidState state, Random random) {

        if(!world.isClientSide()) return;

        BlockPos blockpos = pos.above();
        if (world.getBlockState(blockpos).isAir() && !world.getBlockState(blockpos).isSolidRender(world, blockpos)) {
            if (random.nextInt(100) == 0) {

                double x = (double)pos.getX() + random.nextDouble();
                double y = (double)pos.getY() + 1.0D;
                double z = (double)pos.getZ() + random.nextDouble();

                world.addParticle(ParticleTypes.BUBBLE, x, y, z, R, G, B);
                world.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.5F + random.nextFloat() * 0.15F, false);
            }

            if (random.nextInt(200) == 0) {
                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 1.5F + random.nextFloat() * 0.15F, false);
            }
        }
    }
}
