package io.github.akiart.fantasia.common.particles.particleTypes;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowParticle extends SpriteTexturedParticle {

	protected SnowParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY,
		double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);

	    this.scale(0.2F);
	    this.lifetime = 20;
	    
	    this.setPos(x - 0.5f +  Math.random() / 0.5f, y , z - 0.5f +  Math.random() / 0.5f);
		float speed = 0.05f;
		this.setPower((float) Math.random() * speed);
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.lifetime-- <= 0) {
			this.remove();
		} else {
			this.move(this.xd, this.yd, this.zd);
			
			this.xd *= (double) 1.02F;
			this.yd *= (double) 1.02F;
			this.zd *= (double) 1.02F;
			
//			if (this.onGround) {
//				if (Math.random() < 0.5D) {
//					this.setExpired();
//				}
//			}

//			BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
//			
//			double d0 = Math.max(
//				this.world.getBlockState(blockpos)
//					.getCollisionShapeUncached(this.world, blockpos)
//					.max(Direction.Axis.Y, this.posX - (double) blockpos.getX(), this.posZ - (double) blockpos.getZ()),
//				(double) this.world.getFluidState(blockpos).getActualHeight(this.world, blockpos));
//			
//			if (d0 > 0.0D && this.posY < (double) blockpos.getY() + d0) {
//				this.setExpired();
//			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z,
			double xSpeed, double ySpeed, double zSpeed) {
			SnowParticle snowParticle = new SnowParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			snowParticle.pickSprite(this.spriteSet);
			return snowParticle;
		}
	}
}
