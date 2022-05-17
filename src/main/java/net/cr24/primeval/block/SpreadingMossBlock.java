package net.cr24.primeval.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class SpreadingMossBlock extends GlowLichenBlock {
    public SpreadingMossBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(12) == 0) trySpreadRandomly(state, world, pos, random);
        super.randomTick(state, world, pos, random);
    }
}
