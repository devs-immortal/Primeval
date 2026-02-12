package net.cr24.primeval.block.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;

public class SpreadingMossBlock extends GlowLichenBlock {
    public SpreadingMossBlock(Properties settings) {
        super(settings);
    }
    public final MultifaceSpreader grower = new MultifaceSpreader(this);

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(12) == 0) this.grower.spreadFromRandomFaceTowardRandomDirection(state, world, pos, random);
        super.randomTick(state, world, pos, random);
    }
}
