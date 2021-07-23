package io.github.akiart.fantasia.common.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class ColumnBlockPlacer extends BlockPlacer {

    protected final int minSize;
    protected final int extraSize;

    public static final Codec<ColumnBlockPlacer> CODEC = RecordCodecBuilder.create((builder) -> builder
            .group(
                    Codec.INT.fieldOf("min_size")
                            .forGetter(placer -> placer.minSize),
                    Codec.INT.fieldOf("extra_size")
                            .forGetter(placer -> placer.extraSize)
            )
            .apply(builder, ColumnBlockPlacer::new));

    public ColumnBlockPlacer(int minSize, int extraSize) {
        this.minSize = minSize;
        this.extraSize = extraSize;
    }

    @Override
    public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
        BlockPos.Mutable blockpos$mutable = pos.mutable();
        int height = minSize + random.nextInt(random.nextInt(extraSize + 1) + 1);

        for (int i = 0; i < height; ++i) {
            world.setBlock(blockpos$mutable, state, 2);
            blockpos$mutable.move(Direction.DOWN);
        }
    }

    @Override
    protected BlockPlacerType<?> type() {
        return FBlockPlacerTypes.COLUMN_BLOCK_PLACER.get();
    }
}