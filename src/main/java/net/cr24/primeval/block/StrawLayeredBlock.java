package net.cr24.primeval.block;

import net.cr24.primeval.block.functional.PitKilnBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class StrawLayeredBlock extends LayeredBlock {
    public StrawLayeredBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (state.get(LAYERS) == 1 && PitKilnBlock.isSoilSurrounded(world, pos)) {
            world.setBlockState(pos, PrimevalBlocks.PIT_KILN.getDefaultState(), 0);
        } else {
            super.neighborUpdate(state, world, pos, block, fromPos, notify);
        }
    }
}
