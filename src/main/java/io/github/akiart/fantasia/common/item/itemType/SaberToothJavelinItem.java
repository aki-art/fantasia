package io.github.akiart.fantasia.common.item.itemType;

import com.google.common.collect.Maps;
import io.github.akiart.fantasia.Config;
import io.github.akiart.fantasia.FTags;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import io.github.akiart.fantasia.common.entity.projectile.SaberToothJavelinEntity;
import io.github.akiart.fantasia.common.item.FItemTier;
import io.github.akiart.fantasia.util.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;

public class SaberToothJavelinItem extends JavelinItem {

    public SaberToothJavelinItem(float damage, float attackSpeed, Properties properties) {
        super(FItemTier.BONE, damage, attackSpeed, properties);
    }

    private static final HashMap<Potion, Integer> potionColorOverrides = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Potions.POISON, Constants.Colors.ACID_GREEN);
        map.put(Potions.HARMING, Constants.Colors.RED);
        map.put(Potions.WEAKNESS, Constants.Colors.INDIGO);
    });

    @Override
    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(getDefaultInstance(), Potions.EMPTY);
    }

    @Override
    public void fillItemCategory(ItemGroup itemGroup, NonNullList<ItemStack> stackList) {
        if (allowdedIn(itemGroup)) {
            stackList.add(setPotion(new ItemStack(this), Potions.EMPTY));
            for(Potion potion : Registry.POTION) {
                if (!potion.getEffects().isEmpty()) {
                    stackList.add(setPotion(new ItemStack(this), potion));
                }
            }
        }
    }

    // Apply potion effects on meelee hit
    @Override
    public boolean hurtEnemy(ItemStack item, LivingEntity enemy, LivingEntity source) {
        boolean success = false;
        for (EffectInstance effect : PotionUtils.getMobEffects(item)) {
            success = enemy.addEffect(new EffectInstance(effect.getEffect(), Math.max(effect.getDuration() / 8, 1), effect.getAmplifier(), effect.isAmbient(), effect.isVisible()));
        }

        if(success) {
            decreasePotionUses(item);
        }

        return super.hurtEnemy(item, enemy, source);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        // Clean potion effect off in a cauldron
        World level = context.getLevel();
        if (!level.isClientSide() && !PotionUtils.getPotion(context.getItemInHand()).getEffects().isEmpty()) {
            BlockState state = context.getLevel().getBlockState(context.getClickedPos());
            if (state.is(Blocks.CAULDRON)) {
                cleanInCauldron(context, level, state);
            }
        }

        return super.useOn(context);
    }

    private void cleanInCauldron(ItemUseContext context, World level, BlockState state) {
        int waterLevel = state.getValue(CauldronBlock.LEVEL);
        if (waterLevel > 0) {
            PlayerEntity player = context.getPlayer();
            if (player != null) {
                setPotion(context.getItemInHand(), Potions.EMPTY);
                player.awardStat(Stats.USE_CAULDRON);
                level.playSound(null, context.getClickedPos(), SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                ((CauldronBlock) state.getBlock()).setWaterLevel(level, context.getClickedPos(), state, waterLevel - 1);
            }
        }
    }

    public int getMaxPotionUses() {
        return Config.common.equipment.sabertoothJavelinUses.get();
    }

    public static int getColor(ItemStack stack) {
        return !stack.hasTag() ? -1 : stack.getTag().getInt("F_TipColor");
    }

    protected void setColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("F_TipColor", color | 0xFF000000);
    }

    public int getPotionUsesLeft(ItemStack stack) {
        return !stack.hasTag() ? 0 : stack.getTag().getInt("F_PotionUses");
    }

    public void decreasePotionUses(ItemStack stack) {
        setPotionUsesLeft(stack, getPotionUsesLeft(stack) - 1);
    }

    public void setPotionUsesLeft(ItemStack stack, int uses) {
        stack.getOrCreateTag().putInt("F_PotionUses", Math.max(0, uses));
        if (uses <= 0) {
            PotionUtils.setPotion(stack, Potions.EMPTY);
        }
    }

    private void setPotion(ItemStack stack, Potion potion, int uses) {
        PotionUtils.setPotion(stack, potion);
        setPotionUsesLeft(stack, uses);
        setColor(stack, potionColorOverrides.getOrDefault(potion, PotionUtils.getColor(potion)));
    }

    public ItemStack setPotion(ItemStack stack, Potion potion) {
        int uses = potion.getEffects().isEmpty() ? 0 : getMaxPotionUses();
        setPotion(stack, potion, uses);
        return stack;
    }

    public float getPotionUsesLeftForDisplay(ItemStack stack) {
        return getPotionUsesLeft(stack) / (float) getMaxPotionUses();
    }

    @Override
    protected JavelinEntity createJavelinEntity(ItemStack itemStack, World world, PlayerEntity player) {
        SaberToothJavelinEntity entity = new SaberToothJavelinEntity(FEntities.SABERTOOTH_JAVELIN.get(), world, player, itemStack);
        entity.setEffectsFromItem(itemStack);
        return entity;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        PotionUtils.addPotionTooltip(stack, tooltip, 0.125F);
        tooltip.add(new TranslationTextComponent("tooltip.item.sabertooth_javelin.uses_left", getPotionUsesLeft(stack), getMaxPotionUses()).withStyle(TextFormatting.DARK_GRAY));
        tooltip.add(StringTextComponent.EMPTY);
    }
}
