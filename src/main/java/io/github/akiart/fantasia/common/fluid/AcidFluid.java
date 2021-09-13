package io.github.akiart.fantasia.common.fluid;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.client.particle.FParticleTypes;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.Nullable;
import java.util.Random;

public class AcidFluid extends ForgeFlowingFluid {
    private static final double R = (double) (Constants.Colors.ACID_GREEN >> 16 & 255) / 255.0D;
    private static final double G = (double) (Constants.Colors.ACID_GREEN >> 8 & 255) / 255.0D;
    private static final double B = (double) (Constants.Colors.ACID_GREEN & 255) / 255.0D;

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
    public Fluid getFlowing() {
        return FFluids.ACID_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return FFluids.ACID_SOURCE.get();
    }

    @Override
    public boolean isSame(Fluid pFluid) {
        return pFluid == FFluids.ACID_SOURCE.get() || pFluid == FFluids.ACID_FLOWING.get();
    }

    @Override
    public Vector3d getFlow(IBlockReader pBlockReader, BlockPos pPos, FluidState pFluidState) {
        return Fluids.WATER.getFlow(pBlockReader, pPos, pFluidState);
    }

    @Override
    public int getTickDelay(IWorldReader world) {
        return 5;
    }

    public static ForgeFlowingFluid.Properties getProperties() {
        ResourceLocation still = new ResourceLocation(Fantasia.ID, "block/fluid/acid_still");
        ResourceLocation flowing = new ResourceLocation(Fantasia.ID, "block/fluid/acid_still");

        return new ForgeFlowingFluid.Properties(FFluids.ACID_SOURCE, FFluids.ACID_FLOWING,
                FluidAttributes
                        .builder(still, flowing)
                        .luminosity(4)
                        .viscosity(5000)
                        .density(1)
        )
                .bucket(FItems.ACID_BUCKET)
                .tickRate(5)
                .block(FBlocks.ACID);
    }

    public static class Flowing extends AcidFluid {
        public Flowing(Properties properties) {
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

    public static class Source extends AcidFluid {
        public Source(Properties properties) {
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
        //return FParticleTypes.DRIPPING_ACID.get();
        return ParticleTypes.DRIPPING_WATER;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(World world, BlockPos pos, FluidState state, Random random) {

        if (!world.isClientSide()) return;

        BlockPos blockpos = pos.above();
        if (!world.getBlockState(blockpos).isAir()) {
            if (!world.getBlockState(blockpos).isSolidRender(world, blockpos) && random.nextInt(20) == 0) {

                double x = (double) pos.getX() + random.nextDouble();
                double y = pos.getY();
                double z = (double) pos.getZ() + random.nextDouble();

                double xs = (random.nextDouble() - .5d) * .2d;
                double ys = random.nextDouble() * .2d;
                double zs = (random.nextDouble() - .5d) * .2d;

                world.addParticle(FParticleTypes.ACID_BUBBLE.get(), x, y, z, xs, ys, zs);
            }

            if (random.nextInt(200) == 0) {
                //world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 1.5F + random.nextFloat() * 0.15F, false);
            }
        }
        else {
            // only play sound at surface
            if (random.nextInt(200) == 0) {
                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 0.5F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }
//            if(random.nextInt(1000) == 0) {
//
//                double x = (double) pos.getX() + random.nextDouble();
//                double y = pos.getY();
//                double z = (double) pos.getZ() + random.nextDouble();
//
//                double xs = (random.nextDouble() - .5d) * .2d;
//                double ys = random.nextDouble() * .2d;
//                double zs = (random.nextDouble() - .5d) * .2d;
//
//                world.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, xs, ys, zs);
//
//                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GENERIC_BURN, SoundCategory.BLOCKS, 0.1F + random.nextFloat() * 0.1F, 1.7F + random.nextFloat() * 0.15F, false);
//            }
        }
    }
}
