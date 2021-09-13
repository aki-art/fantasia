package io.github.akiart.fantasia.util;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class FDamageSource extends DamageSource {

    public static final DamageSource ACID = new FDamageSource("acid", 3).bypassArmor();

    private final int varCount;

    public FDamageSource(String name, int variations) {
        super(name);
        this.varCount = variations;
    }

    @Override
    public ITextComponent getLocalizedDeathMessage(LivingEntity entity) {
        LivingEntity credit = entity.getKillCredit();
        String id = getId(entity.getRandom().nextInt(varCount));

        if(credit != null) {
            return new TranslationTextComponent(id + ".player", entity.getDisplayName(), credit.getDisplayName());
        }
        else {
            return new TranslationTextComponent(id, entity.getDisplayName());
        }
    }

    public String getId(int idx) {
        return "fantasia.death.attack." + getMsgId() + "_" + idx;
    }
}
