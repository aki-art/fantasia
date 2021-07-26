package io.github.akiart.fantasia.common.block.blockType;

import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.enchantment.FEnchantments;
import io.github.akiart.fantasia.common.potion.FEffects;
import io.github.akiart.fantasia.util.FDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class AcidIceBlock extends BreakableBlock {
    public AcidIceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerDestroy(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileEntity, ItemStack itemStack) {
        super.playerDestroy(world, player, pos, state, tileEntity, itemStack);

        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0) {
            if (world.dimensionType().ultraWarm()) {
                world.removeBlock(pos, false);
                return;
            }

            Material material = world.getBlockState(pos.below()).getMaterial();
            if (material.blocksMotion() || material.isLiquid()) {
                world.setBlockAndUpdate(pos, FBlocks.ACID.get().defaultBlockState());
            }
        }
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity) {
        if (canBeHurtByAcid(entity)) {
            entity.hurt(FDamageSource.ACID, 1.0F);
        }

        super.stepOn(world, pos, entity);
    }

    private boolean canBeHurtByAcid(Entity entity) {
        return entity instanceof LivingEntity &&
                !((LivingEntity) entity).hasEffect(FEffects.ACID_REPEL.get()) &&
                entity.getDeltaMovement().length() > 0;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBrightness(LightType.BLOCK, pos) > 11 - state.getLightBlock(world, pos)) {
            melt(world, pos);
        }

    }

    protected void melt(World world, BlockPos pos) {
        if (world.dimensionType().ultraWarm()) {
            world.removeBlock(pos, false);
        } else {
            world.setBlockAndUpdate(pos, FBlocks.ACID.get().defaultBlockState());
            world.neighborChanged(pos, FBlocks.ACID.get(), pos);
        }
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.NORMAL;
    }

}
