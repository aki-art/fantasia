package io.github.akiart.fantasia.common.particles.particleTypes;

import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.particles.FParticleTypes;
import io.github.akiart.fantasia.util.Color;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AcidDripParticle extends SpriteTexturedParticle {
    private final Fluid type;

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float __) {
        return 240;
    }

    protected AcidDripParticle(ClientWorld world, double xo, double yo, double zo) {
        super(world, xo, yo, zo);
        setSize(0.01F, 0.01F);
        gravity = 0.06f;
        type = FFluids.ACID_SOURCE.get();
        lifetime = 40;
    }

    protected void preMoveUpdate() {
        if (lifetime-- <= 0) remove();
    }

    protected void postMoveUpdate() { }

    public void tick() {
        xo = x;
        yo = y;
        zo = z;

        preMoveUpdate();

        if (!removed) {
            yd -= gravity;
            move(xd, yd, zd);

            postMoveUpdate();

            if (!removed) {
                xd *= 0.98F;
                yd *= 0.98F;
                zd *= 0.98F;

                BlockPos blockpos = new BlockPos(x, y, z);
                FluidState fluidstate = level.getFluidState(blockpos);
                if (fluidstate.getType() == type && y < (double) ((float) blockpos.getY() + fluidstate.getHeight(level, blockpos))) {
                    remove();
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class Dripping extends AcidDripParticle {
        private final IParticleData fallingParticle;

        private Dripping(ClientWorld world, double xo, double yo, double zo, IParticleData fallingParticle) {
            super(world, xo, yo, zo);
            this.fallingParticle = fallingParticle;
            gravity *= 0.02F;
            lifetime = 40;
        }

        protected void preMoveUpdate() {
            if (lifetime-- <= 0) {
                remove();
                level.addParticle(fallingParticle, x, y, z, xd, yd, zd);
            }
        }

        protected void postMoveUpdate() {
            xd *= 0.02D;
            yd *= 0.02D;
            zd *= 0.02D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class DrippingAcidFactory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;
        private final static float r = (float) Color.getR(Constants.Colors.ACID_GREEN);
        private final static float g = (float) Color.getG(Constants.Colors.ACID_GREEN);
        private final static float b = (float) Color.getB(Constants.Colors.ACID_GREEN);

        public DrippingAcidFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Dripping particle = new Dripping(worldIn, x, y, z, FParticleTypes.DRIPPING_ACID.get());
            particle.setColor(r, g, b);
            particle.pickSprite(spriteSet);
            return particle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FallingAcidDripParticle extends AcidDripParticle {

        protected FallingAcidDripParticle(ClientWorld world, double xo, double yo, double zo, IParticleData fallingParticle) {
            super(world, xo, yo, zo);
            lifetime = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
        }

        protected void postMoveUpdate() {
            if (onGround) remove();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FallingAcidFactory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite sprite;
        private final static float r = (float) Color.getR(Constants.Colors.ACID_GREEN);
        private final static float g = (float) Color.getG(Constants.Colors.ACID_GREEN);
        private final static float b = (float) Color.getB(Constants.Colors.ACID_GREEN);

        public FallingAcidFactory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(BasicParticleType particleType, ClientWorld world, double xo, double yo, double zo, double xSpeed, double ySpeed, double zSpeed) {
            FallingAcidDripParticle particle = new FallingAcidDripParticle(world, xo, yo, zo, FParticleTypes.FALLING_ACID.get());
            particle.setColor(r, g, b);
            particle.pickSprite(sprite);
            return particle;
        }
    }
}
