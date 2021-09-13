package io.github.akiart.fantasia.client.particle.particleTypes;

import io.github.akiart.fantasia.common.tags.FTags;
import io.github.akiart.fantasia.util.Color;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AcidBubbleParticle extends SpriteTexturedParticle {

    private static final float ACID_R = (float)Color.getR(Constants.Colors.ACID_BLUE_TINT);
    private static final float ACID_G = (float)Color.getG(Constants.Colors.ACID_BLUE_TINT);
    private static final float ACID_B = (float)Color.getB(Constants.Colors.ACID_BLUE_TINT);

    boolean hasReachedSurface = false;
    boolean markedToPop = false;
    int popFrame = 0;
    IAnimatedSprite sprites;

    protected AcidBubbleParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, IAnimatedSprite sprites) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.sprites = sprites;

        scale(1F);
        lifetime = 400;

        xd = motionX;
        yd = motionY;
        zd = motionZ;

        hasPhysics = false;

        setColor(ACID_R, ACID_G, ACID_B);

        //setPos(x - 0.5f + Math.random() / 0.5f, y, z - 0.5f + Math.random() / 0.5f);
        float speed = 0.005f;
        //yo *= 0.4f;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    private static float HORIZONTAL_ACCELERATION = 1.02f;

    @Override
    public void tick() {

        xo = x;
        yo = y;
        zo = z;


        if (lifetime-- <= 0) {
            remove();
            return;
        }

        move(xd, yd, zd);

        xd *= 0.95f;
        zd *= 0.95f;

        BlockPos blockpos = new BlockPos(x, y, z);
        FluidState state = level.getFluidState(blockpos);
        FluidState stateAbove = level.getFluidState(blockpos.above());

        if (!hasReachedSurface && stateAbove.isEmpty()) {
            double h = blockpos.getY() + state.getHeight(level, blockpos);
            if(y >= h){
                y = h;
                yd *= 0.1f;
                hasReachedSurface = true;
            }
            else {
                yd *= 1.02f;
            }
        }
        else if(hasReachedSurface) {
            xd *= 0.8f;
            zd *= 0.8f;
            yd *= 0.7f;
            if(markedToPop) {
                setSprite(sprites.get(popFrame++, 4));
                if(popFrame >= 5) remove();
            }
            else markedToPop = random.nextBoolean();
        }
        else {
            yd *= 1.02f;
        }


    }

    //
    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            AcidBubbleParticle particle = new AcidBubbleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            //particle.pickSprite(this.spriteSet);
            particle.setSpriteFromAge(spriteSet);
            return particle;
        }
    }
}
