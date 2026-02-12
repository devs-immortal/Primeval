package net.cr24.primeval.block.plant;

import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.world.trunker.AbstractTrunker;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GrowingSaplingBlock extends PrimevalPlantBlock {

    private static final int GROW_HEIGHT = 4;
    private static final int GROW_RADIUS = 1;

    public final AbstractTrunker trunker;

    public GrowingSaplingBlock(AbstractTrunker trunker, Properties settings) {
        super(settings);
        this.trunker = trunker;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (inGrowableArea(world, pos)) {
            trunker.growSapling(world, pos, random);
        }
    }

    public boolean inGrowableArea(Level world, BlockPos pos) {
        if (world.getBlockState(pos.below()).is(PrimevalTags.Blocks.SPECIAL_PLANTABLE)) return false;
        // If too dark
        if (world.getMaxLocalRawBrightness(pos) < 11) return false;
        // If not in air bubble
        int maxX = pos.getX() + GROW_RADIUS;
        int maxY = pos.getY() + GROW_HEIGHT;
        int maxZ = pos.getZ() + GROW_RADIUS;
        for (int y = pos.getY()+1; y < maxY; y++) {
            for (int z = pos.getZ()-GROW_RADIUS; z < maxZ; z++) {
                for (int x = pos.getX()-GROW_RADIUS; x < maxX; x++) {
                    BlockPos checkpos = new BlockPos(x, y, z);
                    if (!world.getBlockState(checkpos).isAir()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
