package net.cr24.primeval.block;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Arrays;

/*
 * A collapsible block that falls based
 * on the number of full face neighbors
 */
public class SemiSupportedBlock extends CollapsibleBlock {

    public float percentPerSide;

    public SemiSupportedBlock(float percentPerSide, Block fallBlock, Settings settings) {
        super(fallBlock, settings);
        this.percentPerSide = percentPerSide;
    }

    public SemiSupportedBlock(float percentPerSide, Settings settings) {
        super(settings);
        this.percentPerSide = percentPerSide;
    }

    protected boolean supported(World world, BlockPos pos, Random random) {
        float fallPercent = 1.0f;
        for (Direction sideDirection : Arrays.asList(Direction.UP, Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)) {
            if (world.getBlockState(pos.offset(sideDirection)).isSideSolidFullSquare(world, pos.offset(sideDirection), sideDirection.getOpposite())) {
                fallPercent -= percentPerSide;
            }
        }
        float rand = random.nextFloat();
        return rand > fallPercent || super.supported(world, pos, random);
    }
}
