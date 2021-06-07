package io.github.akiart.fantasia.common.sound;

import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;

public class FSoundType {
	
	public static final ForgeSoundType CRYSTAL = new ForgeSoundType(1f, 1f, 
		FSounds.CRYSTAL_MINING,
		() -> SoundEvents.GLASS_STEP, 
		FSounds.CRYSTAL_PLACE, 
		FSounds.CRYSTAL_HIT,
		() -> SoundEvents.GLASS_FALL);
}
