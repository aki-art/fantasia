package io.github.akiart.fantasia.common.item.itemType;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.dispenser.DispenseJavelinBehavior;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.entity.projectile.JavelinEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class JavelinItem extends TieredItem {

    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public JavelinItem(IItemTier tier, float damage, float attackSpeed, Properties properties) {
        super(tier, properties);

        this.attackDamage = damage + tier.getAttackDamageBonus();

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();

        DispenserBlock.registerBehavior(this, new DispenseJavelinBehavior());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slot) {
        return slot == EquipmentSlotType.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public UseAction getUseAnimation(ItemStack item) {
        return UseAction.SPEAR;
    }

    public float getDamage() {
        return this.attackDamage;
    }

    @Override
    public int getUseDuration(ItemStack item) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, World world, LivingEntity userEntity, int duration) {
        if (userEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) userEntity;

            if (getUseDuration(itemStack) - duration >= 10) {

                if (!world.isClientSide) {
                    damageItem(itemStack, userEntity, player);
                    spawnJavelinEntity(itemStack, world, player);

                    if (!player.abilities.instabuild) {
                        player.inventory.removeItem(itemStack);
                    }
                }
            }
        }
    }

    private void damageItem(ItemStack itemStack, LivingEntity userEntity, PlayerEntity player) {
        itemStack.hurtAndBreak(1, player, (entity) -> {
            entity.broadcastBreakEvent(userEntity.getUsedItemHand());
        });
    }

    protected void spawnJavelinEntity(ItemStack itemStack, World world, PlayerEntity player) {
        JavelinEntity javelinEntity = new JavelinEntity(FEntities.JAVELIN.get(), world, player, itemStack);
        javelinEntity.shootFromRotation(player, player.xRot, player.yRot, 0.0F, 2.5F, 1.0F);

        if (player.abilities.instabuild) {
            javelinEntity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        }

        world.addFreshEntity(javelinEntity);
        javelinEntity.setJavelinType(this.getRegistryName().getPath());
        world.playSound(null, javelinEntity, SoundEvents.TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return ActionResult.consume(itemstack);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack item, LivingEntity enemy, LivingEntity source) {
        item.hurtAndBreak(1, source, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }
}
