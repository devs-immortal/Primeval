package net.cr24.primeval.block.plant;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.block.MultifaceGrower;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class SpreadingMossBlock extends GlowLichenBlock {
    public SpreadingMossBlock(Settings settings) {
        super(settings);
    }
    public final MultifaceGrower grower = new MultifaceGrower(this);

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(12) == 0) this.grower.grow(state, world, pos, random);
        super.randomTick(state, world, pos, random);
    }
}
