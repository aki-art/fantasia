package io.github.akiart.fantasia.common.entity.ai.behaviour;

import io.github.akiart.fantasia.Fantasia;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvents;

import java.util.Random;

public class EggLayerBehaviour extends Behaviour {

    private int nextEgg = 0;
    private final int minInterval;
    private final int maxInterval;
    private final IItemProvider egg;

    public EggLayerBehaviour(int minInterval, int maxInterval, IItemProvider egg) {
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
        this.egg = egg;
    }

    @Override
    public boolean canUse() {
        return entity.isAlive() && !entity.isBaby();
    }

    @Override
    public void enter() {
        this.nextEgg = getNextEgg(entity.getRandom());
    }

    @Override
    public void tick() {
        if(!entity.level.isClientSide())
            if(nextEgg-- < 0) layEgg();
    }

    private void layEgg() {
        Random random = entity.getRandom();
        entity.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        entity.spawnAtLocation(egg);
        nextEgg = getNextEgg(random);
    }

    private int getNextEgg(Random random) {
        return random.nextInt(maxInterval - minInterval) + minInterval;
    }
}
