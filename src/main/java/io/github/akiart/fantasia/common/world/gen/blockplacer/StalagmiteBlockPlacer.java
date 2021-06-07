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

public class StalagmiteBlockPlacer extends BlockPlacer {

    private final int minSize;
    private final int extraSize;

    public static final Codec<StalagmiteBlockPlacer> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(Codec.INT.fieldOf("min_size").forGetter((placer) -> {
            return placer.minSize;
        }), Codec.INT.fieldOf("extra_size").forGetter((placer) -> {
            return placer.extraSize;
        })).apply(builder, StalagmiteBlockPlacer::new);
    });

    public StalagmiteBlockPlacer(int minSize, int extraSize) {
        this.minSize = minSize;
        this.extraSize = extraSize;
    }

    @Override
    public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
        BlockPos.Mutable blockpos$mutable = pos.mutable();
        int i = this.minSize + random.nextInt(random.nextInt(this.extraSize + 1) + 1);

        for (int j = 0; j < i; ++j) {
            world.setBlock(blockpos$mutable, state, 2);
            blockpos$mutable.move(Direction.UP);
        }
    }

    @Override
    protected BlockPlacerType<?> type() {
        return FBlockPlacerTypes.STALAGMITE_BLOCK_PLACER.get();
    }
}
