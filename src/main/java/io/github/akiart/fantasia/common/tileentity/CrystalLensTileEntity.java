package io.github.akiart.fantasia.common.tileentity;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class CrystalLensTileEntity extends TileEntity implements /*ITickableTileEntity,*/ IAnimatable {

	private final AnimationFactory factory = new AnimationFactory(this);
	private float prevAngle = 0;
	private float nextAngle = 90;
	private float duration = 300;
	
	public CrystalLensTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		//event.getController().transitionLengthTicks = 0;
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crystal_lens.test", true));

		return PlayState.STOP;
	}
	
	public void test() {
		Fantasia.LOGGER.info("test");
	}
	
	public CrystalLensTileEntity() {
		this(FTileEntityTypes.CRYSTAL_LENS.get());
	}
	
	public float getTestAngle(float elapsedTime, float dt) {
		if(elapsedTime > duration) {
			float angle = prevAngle;
			nextAngle = angle;
			prevAngle = nextAngle;
		}
		return MathHelper.lerp(prevAngle, nextAngle, (elapsedTime / duration) * dt);
	}
	
//	@Override
//	public void tick() {
//		this.world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
