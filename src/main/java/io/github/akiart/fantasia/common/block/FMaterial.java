package io.github.akiart.fantasia.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class FMaterial {
    public static final Material ACID = create(
            MaterialColor.COLOR_LIGHT_GREEN,
            true,
            false,
            false,
            true,
            true,
            true,
            null);

   private static Material create(MaterialColor color, boolean liquid, boolean solid, boolean blocksMotion, boolean solidBlocking, boolean flammable, boolean replaceable, PushReaction pushReaction) {
       return  new Material(color, liquid, solid, blocksMotion, solidBlocking, flammable, replaceable, pushReaction);
   }
}
