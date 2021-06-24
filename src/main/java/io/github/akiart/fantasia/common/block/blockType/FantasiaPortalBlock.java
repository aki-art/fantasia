package io.github.akiart.fantasia.common.block.blockType;

import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.world.FTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FantasiaPortalBlock extends Block {

    public FantasiaPortalBlock(Properties properties) {
        super(properties);
    }

    private boolean canTeleport(Entity entity) {
        return !entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions();
    }

    private boolean checkCollision(BlockState state, World world, BlockPos pos, Entity entity) {

        VoxelShape entityShape = VoxelShapes.create(entity.getBoundingBox()
                .move(-pos.getX(), -pos.getY(), -pos.getZ()));

        return VoxelShapes.joinIsNotEmpty(entityShape, state.getShape(world, pos), IBooleanFunction.AND);
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {

        if (world instanceof ServerWorld && canTeleport(entity) && checkCollision(state, world, pos, entity)) {

            RegistryKey<World> registrykey = world.dimension() == Fantasia.FANTASIA_WORLD_KEY ?
                    World.OVERWORLD :
                    Fantasia.FANTASIA_WORLD_KEY;

            ServerWorld serverworld = ((ServerWorld) world).getServer().getLevel(registrykey);
            if (serverworld == null) {
                return;
            }

            // entity.handleInsidePortal(pos);
            entity.changeDimension(serverworld, new FTeleporter(pos));
        }
    }
}
