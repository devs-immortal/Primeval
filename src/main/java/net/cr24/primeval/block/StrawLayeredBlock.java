package net.cr24.primeval.block;

import net.cr24.primeval.block.functional.PitKilnBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;

public class StrawLayeredBlock extends LayeredBlock {
    public StrawLayeredBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (state.getValue(LAYERS) == 1 && PitKilnBlock.isSoilSurrounded(world, pos)) {
            world.setBlock(pos, PrimevalBlocks.PIT_KILN.defaultBlockState(), 0);
        } else {
            super.neighborChanged(state, world, pos, sourceBlock, wireOrientation, notify);
        }
    }
}
