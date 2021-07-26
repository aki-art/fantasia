package io.github.akiart.fantasia.util;

import io.github.akiart.fantasia.common.block.FBlocks;
import net.minecraft.block.BlockState;

public class Constants {
    public static final int EMISSIVE_LIGHT = 15728880;

    // ARGB format
    public static class Colors {
        public static final int UNSET = -1;
        public static final int LIGHT_BROWN = 0xFF968860;
        public static final int CREAM = 0xFFe3decf;
        public static final int ALMOST_BLACK = 0xFF333132;
        public static final int RED = 0xFFed2449;
        public static final int GINGER = 0xFFe0b470;
        public static final int FREEZY_BLUE = 0xFFA8D9FF;
        public static final int ACID_GREEN = 0xFFBBFF29;
        public static final int BLACK = 0xFF000000;
        public static final int INDIGO = 0xFFbd91ff;
    }

    public static class BlockStates {
        public static final BlockState ACID = FBlocks.ACID.get().defaultBlockState();
        public static final BlockState ACID_ICE = FBlocks.ACID_ICE.get().defaultBlockState();
    }
}
