package io.github.akiart.fantasia.dataGen;

import com.google.common.collect.Sets;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.passive.PtarmiganEntity;
import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FEntityLootTables extends EntityLootTables {

    private final Set<EntityType<?>> knownEntities = new HashSet<>();

    @Override
    protected void add(EntityType<?> entity, LootTable.Builder builder) {
        super.add(entity, builder);
        knownEntities.add(entity);
    }

    @Override
    protected void addTables() {

        LootTable.Builder ptarmigan = LootTable.lootTable();
        addItem(ptarmigan, FItems.PTARMIGAN_EGG.get(), 1, 2, 1);

        add(FEntities.PTARMIGAN.get(),
                LootTable
                        .lootTable()
                        .withPool(LootPool
                                .lootPool()
                                .setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(Items.FEATHER)
                                        .apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
                                        .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))
                        .withPool(LootPool
                                .lootPool()
                                .setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(FItems.RAW_PTARMIGAN.get())
                                        .apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                        .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))));
    }

    LootTable.Builder addItem(LootTable.Builder builder, Item item, int min, int max, int lootMax) {
        return builder.withPool(LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(item)
                        .apply(SetCount.setCount(RandomValueRange.between(0f, 2f)))
                        .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0f, 1f)))));
    }

    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {

    }

    @Override
    public Set<EntityType<?>> getKnownEntities() {
        return knownEntities;
    }
}
