package io.github.akiart.fantasia.common.item.itemType.frostWork;

import com.google.common.collect.ImmutableList;
import io.github.akiart.fantasia.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FrostworkAxeItem extends AxeItem {

    public FrostworkAxeItem(IItemTier itemTier, int baseDamage, float attackSpeed, Properties properties) {
        super(itemTier, baseDamage, attackSpeed, properties);
    }

    public static int getCharge(ItemStack itemStack) {
        return itemStack.hasTag() ? itemStack.getTag().getInt("F_ChargeCount") : 0;
    }

    public static void setCharge(ItemStack itemStack, int count) {
        itemStack.getOrCreateTag().putInt("F_ChargeCount", count);
    }

    public static void setTargetedBlock(ItemStack itemStack, BlockPos pos) {
        itemStack.getOrCreateTag().putLong("F_Target", pos.asLong());
    }

    public static BlockPos getTargetedBlock(ItemStack itemStack) {
        return itemStack.hasTag() ? BlockPos.of(itemStack.getOrCreateTag().getLong("F_Target")) : null;
    }

    public static void queueBrokenBlocks(ItemStack itemStack, List<BlockPos> positions) {
        itemStack.getOrCreateTag().putLongArray("F_Queue", positions.stream().map(BlockPos::asLong).collect(Collectors.toList()));
    }

    public static long[] getQueuedBlocks(ItemStack itemStack) {
        return itemStack.hasTag() ? itemStack.getOrCreateTag().getLongArray("F_Queue") : null;
    }

    public static List<BlockPos> getQueuedBlockPositions(ItemStack itemStack) {
        long[] blocks = getQueuedBlocks(itemStack);
        return blocks == null ? new ArrayList<>() : Arrays.stream(blocks).mapToObj(BlockPos::of).collect(Collectors.toList());
    }

    @Override
    public UseAction getUseAnimation(ItemStack item) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack item) {
        return Integer.MAX_VALUE;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ActionResultType result = super.useOn(context);
        if(result == ActionResultType.PASS && context.getPlayer() != null) {
            BlockPos target = context.getClickedPos();
            if(context.getLevel().getBlockState(target).getHarvestTool() == ToolType.AXE) {
                context.getPlayer().startUsingItem(context.getHand());
                ItemStack stack = context.getItemInHand();
                setTargetedBlock(stack, target);
                queueBrokenBlocks(stack, ImmutableList.of(target));
            }
        }
        return result;
    }

    private BlockPos findConnectedBlock(World world, List<BlockPos> positions) {
        for(BlockPos pos : positions) {
            for(Direction direction : Direction.values()) {
                BlockPos offset = pos.relative(direction);
                if(!positions.contains(offset) && isConnectibleBlock(world, pos, offset)) {
                    return offset;
                }
            }
        }

        return null;
    }

    private boolean isConnectibleBlock(World world, BlockPos aPos, BlockPos bPos) {
        if(Config.common.equipment.frostworkAxeSameOnly.get()) {
            return world.getBlockState(aPos).getBlock().is(world.getBlockState(bPos).getBlock());
        }
        else {
            return world.getBlockState(bPos).getHarvestTool() == ToolType.AXE;
        }
    }

    @Override
    public void onUseTick(World world, LivingEntity entity, ItemStack itemStack, int  duration) {
        BlockPos target = getTargetedBlock(itemStack);
        if(target == null) return;

        int charge = getCharge(itemStack);

        if(charge > 40) { //getDestroySpeed(itemStack, Blocks.OAK_LOG.defaultBlockState())) {
            List<BlockPos> positions = getQueuedBlockPositions(itemStack);
            BlockPos newPos = findConnectedBlock(world, positions);
            if(newPos != null) {
                positions.add(newPos);
                queueBrokenBlocks(itemStack, positions);
                setCharge(itemStack, 0);
                world.playLocalSound(newPos.getX(), newPos.getY(), newPos.getZ(), SoundEvents.WOOD_BREAK, SoundCategory.BLOCKS, 1f, 1f, true);
            }
        }

        setCharge(itemStack, charge + 1);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, World world, LivingEntity userEntity, int duration) {
        List<BlockPos> positions = getQueuedBlockPositions(itemStack);
        for(BlockPos pos : positions) {
            world.destroyBlock(pos, true, userEntity);
        }

        queueBrokenBlocks(itemStack, new ArrayList<>());
        itemStack.removeTagKey("F_Target");
    }
}
