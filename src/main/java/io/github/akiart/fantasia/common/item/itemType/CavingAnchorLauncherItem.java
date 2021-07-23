package io.github.akiart.fantasia.common.item.itemType;

import io.github.akiart.fantasia.common.entity.projectile.CavingRopeAnchorEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class CavingAnchorLauncherItem extends Item {

    public CavingAnchorLauncherItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAction getUseAnimation(ItemStack itemStack) {
        return UseAction.CROSSBOW;
    }

    public static boolean isCharged(ItemStack itemStack) {
        return true; //itemStack.hasTag() && itemStack.getTag().getBoolean("Charged");
    }

    public static void setCharged(ItemStack itemStack, boolean charged) {
        itemStack.getOrCreateTag().putBoolean("Charged", charged);
    }

    public static int getAmmunitionCount(ItemStack itemStack) {
        return itemStack.hasTag() ? itemStack.getTag().getInt("Ammunition") : 0;
    }

    //wtf i dont need to keep count
    public static void setAmmunitionCount(ItemStack itemStack, int count) {
        itemStack.getOrCreateTag().putInt("Ammunition", count);
    }

    public static void addAmmunition(ItemStack itemStack, int count) {
        setAmmunitionCount(itemStack, getAmmunitionCount(itemStack) + count);
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack launcher = player.getItemInHand(hand);

        if(isCharged(launcher)) {
            // shoot
            launchAnchor(world, player, hand, launcher); // the model is backwards

            setCharged(launcher, false);
            return ActionResult.consume(launcher);
        }
        else {
            // load
        }

        return ActionResult.fail(launcher);
    }

    private void launchAnchor(World world, PlayerEntity player, Hand hand, ItemStack launcher) {
        if(!world.isClientSide) {
            CavingRopeAnchorEntity anchor = new CavingRopeAnchorEntity(world, player);
            anchor.setShotFromCrossbow(true);

            Quaternion rotation = new Quaternion(new Vector3f(player.getUpVector(1.0F)), 0, true);
            Vector3f viewVector = new Vector3f(player.getViewVector(1.0F));
            viewVector.transform(rotation);
            anchor.shoot(viewVector.x(), viewVector.y(), viewVector.z(), 3.15f, 1f);

            launcher.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(hand));
            world.addFreshEntity(anchor);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1f, getRandomShotPitch());
        }
    }

    private static float getRandomShotPitch() {
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + 0.53f;
    }

}