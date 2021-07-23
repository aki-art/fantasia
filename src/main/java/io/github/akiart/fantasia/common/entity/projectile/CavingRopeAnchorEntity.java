package io.github.akiart.fantasia.common.entity.projectile;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CavingRopeAnchorEntity extends AbstractYeetableEntity {

    public static final String ID = "caving_rope_anchor2";
    private static final ResourceLocation texture = new ResourceLocation(Fantasia.ID, "textures/entity/caving_rope_anchor/caving_rope_anchor.png");

    public CavingRopeAnchorEntity(World world, LivingEntity entity, ItemStack item) {
        super(FEntities.CAVING_ROPE_ANCHOR.get(), world, entity, item);
    }

    public CavingRopeAnchorEntity(World world, LivingEntity entity) {
        super(FEntities.CAVING_ROPE_ANCHOR.get(), world, entity, new ItemStack(FItems.CAVING_ROPE_ANCHOR.get()));
    }

    public CavingRopeAnchorEntity(EntityType<? extends AbstractYeetableEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public CavingRopeAnchorEntity(EntityType<? extends AbstractYeetableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return texture;
    }
}
