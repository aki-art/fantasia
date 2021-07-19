package io.github.akiart.fantasia.common.entity.projectile;

import com.google.common.collect.Sets;
import io.github.akiart.fantasia.common.item.itemType.SaberToothJavelinItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Set;

public class SaberToothJavelinEntity extends JavelinEntity {
    private static final DataParameter<Integer> ID_EFFECT_COLOR = EntityDataManager.defineId(SaberToothJavelinEntity.class, DataSerializers.INT);
    private Potion potion = Potions.EMPTY;
    private final Set<EffectInstance> effects = Sets.newHashSet();
    private boolean fixedColor;
    private static int EMPTY_COLOR = -1;

    public SaberToothJavelinEntity(EntityType<? extends JavelinEntity> type, World world, LivingEntity entity, ItemStack item) {
        super(type, world, entity, item);
    }

    public SaberToothJavelinEntity(World world, double x, double y, double z, ItemStack item) {
        super(world, x, y, z, item);
    }

    public SaberToothJavelinEntity(EntityType<? extends JavelinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult target) {
        super.onHitEntity(target);
        ((SaberToothJavelinItem)item.getItem()).decreasePotionUses(item);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_EFFECT_COLOR, -1);
    }

    public void setEffectsFromItem(ItemStack stack) {
        if (stack.getItem() instanceof SaberToothJavelinItem) {
            potion = PotionUtils.getPotion(stack);
            Collection<EffectInstance> inEffects = PotionUtils.getCustomEffects(stack);

            if (!inEffects.isEmpty()) {
                for(EffectInstance effect : inEffects) {
                    this.effects.add(new EffectInstance(effect));
                }
            }

            int color = getCustomColor(stack);
            if (color == EMPTY_COLOR) {
                updateColor();
            } else {
                setFixedColor(color);
            }
        }
    }

    public static int getCustomColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.contains("CustomPotionColor", 99) ? compoundnbt.getInt("CustomPotionColor") : EMPTY_COLOR;
    }

    private void setFixedColor(int color) {
        fixedColor = true;
        entityData.set(ID_EFFECT_COLOR, color);
    }

    private void updateColor() {
        fixedColor = false;
        if (potion == Potions.EMPTY && effects.isEmpty()) {
            entityData.set(ID_EFFECT_COLOR, EMPTY_COLOR);
        } else {
            entityData.set(ID_EFFECT_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(potion, effects)));
        }
    }
}
