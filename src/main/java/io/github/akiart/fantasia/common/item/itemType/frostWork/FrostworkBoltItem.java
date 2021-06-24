package io.github.akiart.fantasia.common.item.itemType.frostWork;

import io.github.akiart.fantasia.common.item.FItemTier;
import io.github.akiart.fantasia.common.item.itemType.JavelinItem;

public class FrostworkBoltItem extends JavelinItem {
    public FrostworkBoltItem(float damage, float attackSpeed, Properties properties) {
        super(FItemTier.WOLFRAMITE, damage, attackSpeed, properties);
    }
}
