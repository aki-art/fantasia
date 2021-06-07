package io.github.akiart.fantasia.common.tileentity.model;

import javax.annotation.Nullable;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.tileentity.CrystalLensTileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrystalLensModel extends AnimatedGeoModel<CrystalLensTileEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(CrystalLensTileEntity animatable) {
		return new ResourceLocation(Fantasia.ID, "animations/crystal_lens.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(CrystalLensTileEntity object) {
		return new ResourceLocation(Fantasia.ID, "geo/crystal_lens.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalLensTileEntity object) {
		return new ResourceLocation(Fantasia.ID, "textures/entity/crystal_lens/hiemsite.png");
	}

//	@Override
//	public void setLivingAnimations(CrystalLensTileEntity entity, Integer uniqueID, @Nullable AnimationEvent predicate) {
//
//		super.setLivingAnimations(entity, uniqueID, predicate);
//		
//		IBone bone = getAnimationProcessor().getBone("outer_frame");
//		//bone.setRotationZ(bone.getRotationZ() + 0.01f); // random test animation
//		bone.setRotationZ(entity.getTestAngle(0, 0));
//	}
}
