package net.cr24.primeval.world.gen.trunker;

import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

import java.util.List;

public class SpruceTrunker extends AbstractTrunker {

    public static final SpruceTrunker INSTANCE = new SpruceTrunker();

    private SpruceTrunker() {
        super(() -> PrimevalBlocks.SPRUCE_TRUNK, () -> PrimevalBlocks.SPRUCE_LEAVES);
    }

    @Override
    public List<BlockPos> tickTrunk(BlockState state, WorldAccess world, BlockPos pos, Random random, Direction[] directions) {
        return List.of();
    }
}
