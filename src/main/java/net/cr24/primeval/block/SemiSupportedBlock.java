package net.cr24.primeval.block;

import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

/*
 * A collapsible block that falls based
 * on the number of full face neighbors
 */
public class SemiSupportedBlock extends CollapsibleBlock {

    public float percentPerSide;

    public SemiSupportedBlock(float percentPerSide, Block fallBlock, Properties settings) {
        super(fallBlock, settings);
        this.percentPerSide = percentPerSide;
    }

    public SemiSupportedBlock(float percentPerSide, Properties settings) {
        super(settings);
        this.percentPerSide = percentPerSide;
    }

    protected boolean supported(Level world, BlockPos pos, RandomSource random) {
        float fallPercent = 1.0f;
        for (Direction sideDirection : Arrays.asList(Direction.UP, Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)) {
            if (world.getBlockState(pos.relative(sideDirection)).isFaceSturdy(world, pos.relative(sideDirection), sideDirection.getOpposite())) {
                fallPercent -= percentPerSide;
            }
        }
        float rand = random.nextFloat();
        return rand > fallPercent || super.supported(world, pos, random);
    }
}
