package net.cr24.primeval.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class FeatureUtil {

    public static BlockPos[] getCirclePositions(BlockPos center, double radius) {
        BlockPos[] positions = new BlockPos[(int) Math.ceil(Math.pow(radius*2+1, 2.0)*0.75)];
            // TODO hehe
        return positions;
    }

}
