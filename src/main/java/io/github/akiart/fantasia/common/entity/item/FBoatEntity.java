package io.github.akiart.fantasia.common.entity.item;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.entity.FEntities;
import io.github.akiart.fantasia.common.item.FItems;
import io.github.akiart.fantasia.common.item.itemType.FBoatItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

public class FBoatEntity extends BoatEntity {

    private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.defineId(FBoatEntity.class, DataSerializers.INT);

    public FBoatEntity(EntityType<? extends FBoatEntity> type, World world) {
        super(type, world);
    }

    public FBoatEntity(World world, double x, double y, double z) {
        this(FEntities.BOAT.get(), world);
        setPos(x, y, z);
        setDeltaMovement(Vector3d.ZERO);
        xo = x;
        yo = y;
        zo = z;
    }

    @Override
    public Item getDropItem() {
        switch (getFBoatType()) {
            case FROZEN_ELM:
                return FItems.FROZEN_ELM_BOAT.get();
            case ASPEN:
                return FItems.ASPEN_BOAT.get();
            default:
                return Items.OAK_BOAT;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(BOAT_TYPE, Type.FROZEN_ELM.ordinal());
    }

    public Type getFBoatType() {
        return Type.byId(this.entityData.get(BOAT_TYPE));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        compound.putString("Type", getFBoatType().getName());
    }

    public void setBoatType(Type type) {
        entityData.set(BOAT_TYPE, type.ordinal());
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
        if (compound.contains("Type", 8)) {
            setBoatType(Type.byName(compound.getString("Type")));
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // parallel enum to BoatEntity.Type
    public enum Type {
        FROZEN_ELM(FBlocks.FROZEN_ELM.getPlanks(),"elm"),
//        FROZEN_ELM(FBlocks.FROZEN_ELM.planks,"frozen_elm"),
//        FROZEN_SPRUCE(FBlocks.FROZEN_SPRUCE.planks,"frozen_spruce"),
        ASPEN(FBlocks.ASPEN.getPlanks(),"aspen");
//        DOGWOOD(FBlocks.FROZEN_ELM.planks, FItems.FROZEN_ELM_BOAT, "dogwood"),
//        BLACK_ELDER(FBlocks.FROZEN_ELM.planks, FItems.FROZEN_ELM_BOAT,  "black_elder"),
//        ROWAN(FBlocks.FROZEN_ELM.planks, FItems.FROZEN_ELM_BOAT,  "rowan"),
//        TALLOW_WOOD(FBlocks.FROZEN_ELM.planks, FItems.FROZEN_ELM_BOAT,  "tallow_wood"),
//        GRIMCAP(FBlocks.FROZEN_ELM.planks, FItems.FROZEN_ELM_BOAT,  "grimcap"),
//        GLOWFUNGAL(FBlocks.FROZEN_ELM.planks, FItems.FROZEN_ELM_BOAT,  "glowfungal"),
//        SILVER_PINE(FBlocks.FROZEN_ELM.planks, FItems.FROZEN_ELM_BOAT,  "silver_pine"),
//        GIANT_SEQOUIA(FBlocks.FROZEN_ELM.planks, FItems.FROZEN_ELM_BOAT,  "giant_seqouia");

        private final String name;
        private final Supplier<Block> planks;

        public ResourceLocation getTexture() {
            return texture;
        }

        private final ResourceLocation texture;

        Type(Supplier<Block> block, String name) {
            this.name = name;
            this.planks = block;
            this.texture = new ResourceLocation(Fantasia.ID, "textures/entity/boat/" + name + ".png");
        }

        /* Matching BoatEntity.Type methods START
         * do not rename these */

        public String getName() {
            return name;
        }

        public Block getPlanks() {
            return planks.get();
        }

        public String toString() {
            return name;
        }

        public static Type byId(int id) {
            Type[] types = values();
            if (id < 0 || id >= types.length) {
                id = 0;
            }

            return types[id];
        }

        public static Type byName(String p_184981_0_) {
            Type[] types = values();

            for (Type type : types) {
                if (type.getName().equals(p_184981_0_)) {
                    return type;
                }
            }

            return types[0];
        }

        /* Matching BoatEntity.Type methods END*/
    }
}
