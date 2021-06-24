package io.github.akiart.fantasia.common.entity.item;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.block.FBlocks;
import io.github.akiart.fantasia.common.entity.projectile.IcicleEntity;
import io.github.akiart.fantasia.common.item.FItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.function.Supplier;

/* FIXME: this is an atrocious temporary hackjob, but since I want to add new boat types later and write my own boat entity,
    it would be a waste of time to rewrite this right now */

public class FBoatEntity extends BoatEntity {

    public static final Type FROZEN_ELM = new Type(FBlocks.FROZEN_ELM.planks, FItems.GOLD_JAVELIN, "frozen_elm");
    public static final Type FROZEN_SPRUCE = new Type(FBlocks.FROZEN_SPRUCE.planks, FItems.EDELSTONE_SPELEOTHEM,"frozen_spruce");
    Type currentType = FROZEN_ELM;

    public FBoatEntity(EntityType<? extends FBoatEntity> entity, World world) {
        super(entity, world);
    }

    public FBoatEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public Item getDropItem() {
        //return BoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
        return getActualType().getItem();
    }

    public Type getActualType() {
        return currentType;
    }

    public void setActualType(Type type) {
        currentType = type;
    }

    // FIXME hackity hax
    @Override
    @Nullable
    public ItemEntity spawnAtLocation(IItemProvider itemProvider) {
        if(itemProvider.asItem().getDefaultInstance().sameItem(Blocks.OAK_PLANKS.asItem().getDefaultInstance())) {
            return spawnAtLocation(currentType.getPlanks());
        }
        return this.spawnAtLocation(itemProvider, 0);
    }

    // FIXME hackity hax 2
    @Override
    public BoatEntity.Type getBoatType() {
        return BoatEntity.Type.OAK;
    }

    @Override
    public void setType(BoatEntity.Type type) {
        int id = 0;

        for(BoatEntity.Type t : BoatEntity.Type.values()) {
            if(t.equals(type)) break;
            id++;
        }

        if(Type.map.containsKey(id)) {
            setActualType(Type.getByIndex(id));
        }

        super.setType(BoatEntity.Type.OAK);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static class Type {
        private static final HashMap<Integer, Type> map = new HashMap<>();

        private final String name;
        private final Supplier<? extends Block> planks;
        private final Supplier<? extends Item> item;

        ResourceLocation texture;

        private Type(Supplier<? extends Block> block, Supplier<? extends Item> item, String name) {
            this.name = name;
            this.planks = block;
            this.item = item;
            this.texture = new ResourceLocation(Fantasia.ID, "textures/entity/boat/" + name + ".png");
            map.put(map.size(), this);
        }

        public static Type getByIndex(int id) {
            return map.getOrDefault(id, FBoatEntity.FROZEN_ELM);
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks.get();
        }

        public Item getItem() {
            return this.item.get();
        }

        public String toString() {
            return this.name;
        }

        public ResourceLocation getTextureLocation() {
            return this.texture;
        }
    }
}
