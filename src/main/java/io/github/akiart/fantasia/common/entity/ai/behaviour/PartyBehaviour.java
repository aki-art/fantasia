package io.github.akiart.fantasia.common.entity.ai.behaviour;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.lib.GeckoLibExtension.IBasicAnimatable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class PartyBehaviour extends Behaviour {

    BlockPos jukeBox;

    public PartyBehaviour() {
    }

    @Override
    public boolean canUse() {
        return entity.isAlive() && jukeBoxInRange();
    }

    private boolean jukeBoxInRange() {
        return this.jukeBox != null && jukeBox.closerThan(entity.position(), 3.46D);
    }

    private boolean isMusicPlaying() {
        BlockState juke = entity.level.getBlockState(jukeBox);
        return juke.is(Blocks.JUKEBOX) && juke.getValue(JukeboxBlock.HAS_RECORD);
    }

    public void setJuke(BlockPos juke) {
        this.jukeBox = juke;
        resume();
    }

    public void tick() {
        if(!jukeBoxInRange() || !isMusicPlaying()) {
            stop();
        }
    }
}
