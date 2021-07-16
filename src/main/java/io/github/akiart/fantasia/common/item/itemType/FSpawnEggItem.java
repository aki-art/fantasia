package io.github.akiart.fantasia.common.item.itemType;

import io.github.akiart.fantasia.common.dispenser.DispenseEntityBehavior;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;

public class FSpawnEggItem extends SpawnEggItem {
    public FSpawnEggItem(EntityType<?> entityType, int shellColor, int patternColor, Properties properties) {
        super(entityType, shellColor, patternColor, properties);
        DispenserBlock.registerBehavior(this, new DispenseEntityBehavior());
    }
}
