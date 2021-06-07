package io.github.akiart.fantasia.common.world.gen.treedecorators;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;

import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.common.util.Constants.BlockFlags;

public class IcicleTreeDecorator extends TreeDecorator {
	private float probability;
	private final float DOUBLE_ICICLE_CHANCE = 0.2f;
	private final float TRIPLE_ICICLE_CHANCE = 0.01f;
	private final BlockState ICICLE = FBlocks.ICICLE.get().defaultBlockState();

	public static final Codec<IcicleTreeDecorator> CODEC = Codec.floatRange(0.0F, 1.0F)
		.fieldOf("probability")
		.xmap(IcicleTreeDecorator::new, (decorator) -> {
			return decorator.probability;
		})
		.codec();

	public IcicleTreeDecorator(float probability) {
		this.probability = probability;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return FTreeDecorators.ICICLE_TREE_DECORATOR.get();
	}

	private void placeDecoration(IWorldWriter worldWriter, BlockPos pos, BlockState state, Set<BlockPos> decors,
		MutableBoundingBox boundingBox) {
		worldWriter.setBlock(pos, state, BlockFlags.NOTIFY_NEIGHBORS | BlockFlags.BLOCK_UPDATE);
		decors.add(pos);
		boundingBox.expand(new MutableBoundingBox(pos, pos));
	}

	@Override
	public void place(ISeedReader world, Random rand, List<BlockPos> logs, List<BlockPos> leaves,
		Set<BlockPos> decors, MutableBoundingBox boundingBox) {

		HashSet<BlockPos> possiblePositions = new HashSet<BlockPos>();

		for (BlockPos leaf : leaves) {
			BlockPos belowLeaf = leaf.below();
			if (world.isEmptyBlock(belowLeaf)) {
				possiblePositions.add(belowLeaf);
			}
		}

		for (BlockPos target : possiblePositions) {
			if(rand.nextFloat() < probability) {
				float r = rand.nextFloat();
				int length = r < DOUBLE_ICICLE_CHANCE ? (r < TRIPLE_ICICLE_CHANCE ? 3 : 2) : 1;
				for (int i = 0; i < length; i++) {
					placeDecoration(world, target.below(i), ICICLE, decors, boundingBox);
				}
			}
		}
	}
}