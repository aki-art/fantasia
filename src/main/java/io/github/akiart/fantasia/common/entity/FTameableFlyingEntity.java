package io.github.akiart.fantasia.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class FTameableFlyingEntity extends FTameableEntity {
    protected FTameableFlyingEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean causeFallDamage(float f1, float f2) {
        return false;
    }

    protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState state, BlockPos pos) { }

    public boolean onClimbable() {
        return false;
    }

    public void travel(Vector3d vec) {
        if (this.isInWater()) {
            this.moveRelative(0.02F, vec);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
        } else if (this.isInLava()) {
            this.moveRelative(0.02F, vec);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
        } else {
            BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());

            float friction = 0.91F;
            if (this.onGround) {
                friction = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (friction * friction * friction);

            this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, vec);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(friction));
        }

        this.calculateEntityAnimation(this, false);
    }
}
