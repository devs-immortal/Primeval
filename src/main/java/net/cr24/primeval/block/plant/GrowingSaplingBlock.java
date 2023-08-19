package net.cr24.primeval.block.plant;

import net.cr24.primeval.block.PrimevalBlockTags;
import net.cr24.primeval.world.gen.trunker.AbstractTrunker;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class GrowingSaplingBlock extends PrimevalPlantBlock {

    private static final int GROW_HEIGHT = 4;
    private static final int GROW_RADIUS = 1;

    public final AbstractTrunker trunker;

    public GrowingSaplingBlock(Settings settings, AbstractTrunker trunker) {
        super(settings);
        this.trunker = trunker;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (inGrowableArea(world, pos)) {
            trunker.growSapling(world, pos, random);
        }
    }

    public boolean inGrowableArea(World world, BlockPos pos) {
        if (world.getBlockState(pos.down()).isIn(PrimevalBlockTags.SPECIAL_PLANTABLE)) return false;
        // If too dark
        if (world.getLightLevel(pos) < 11) return false;
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
