package io.github.akiart.fantasia.common.entity.projectile;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.item.itemType.SaberToothJavelinItem;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SaberToothJavelinEntity extends JavelinEntity {
    private static final DataParameter<Integer> ID_EFFECT_COLOR = EntityDataManager.defineId(SaberToothJavelinEntity.class, DataSerializers.INT);

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
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);

        boolean success = false;

        for(EffectInstance effectinstance : PotionUtils.getMobEffects(item)) {
            success = entity.addEffect(new EffectInstance(effectinstance.getEffect(), Math.max(effectinstance.getDuration() / 8, 1), effectinstance.getAmplifier(), effectinstance.isAmbient(), effectinstance.isVisible()));
        }

        if (success) {
            SaberToothJavelinItem javelin = (SaberToothJavelinItem) item.getItem();
            javelin.decreasePotionUses(item);
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_EFFECT_COLOR, -1);
    }

    public void setEffectsFromItem(ItemStack stack) {
        setColor(SaberToothJavelinItem.getColor(stack));
    }

    private void setColor(int color) {
        entityData.set(ID_EFFECT_COLOR, color);
        //Fantasia.LOGGER.info("SET COLOR:" + String.format("0x%08X", color));
    }

    public int getColor() {
        return entityData.get(ID_EFFECT_COLOR);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            makeParticle(inGround && inGroundTime % 5 == 0 ? 1 : 2);
        }
    }

    private void makeParticle(int count) {
        Fantasia.LOGGER.info("PARTICLE" + count);
        int color = getColor();
        if (color != Constants.Colors.UNSET && count > 0) {
            double r = (double)(color >> 16 & 255) / 255.0D;
            double g = (double)(color >> 8 & 255) / 255.0D;
            double b = (double)(color & 255) / 255.0D;

            for(int j = 0; j < count; ++j) {
                level.addParticle(ParticleTypes.ENTITY_EFFECT, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), r, g, b);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte flag) {
        if (flag == 0) {
            int color = getColor();
            if (color != -1) {
                double r = (double)(color >> 16 & 255) / 255.0D;
                double g = (double)(color >> 8 & 255) / 255.0D;
                double b = (double)(color >> 0 & 255) / 255.0D;

                for(int i = 0; i < 20; ++i) {
                    level.addParticle(ParticleTypes.ENTITY_EFFECT, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), r, g, b);
                }
            }
        } else {
            super.handleEntityEvent(flag);
        }
    }
}

