package io.github.akiart.fantasia.common.sound;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
		Fantasia.ID);

	public static final RegistryObject<SoundEvent> CRYSTAL_MINING = register("crystal_mining");
	public static final RegistryObject<SoundEvent> CRYSTAL_PLACE = register("crystal_place");
	public static final RegistryObject<SoundEvent> CRYSTAL_HIT = register("crystal_hit");

	private static RegistryObject<SoundEvent> register(String name) {
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Fantasia.ID, name)));
	}
}
