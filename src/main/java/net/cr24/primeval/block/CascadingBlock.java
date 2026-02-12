package net.cr24.primeval.block;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

/*
 * A collapsible block that falls based
 * on the number of full face neighbors.
 *
 * When this block collapses, it triggers
 * neighboring CascadingBlocks up to a
 * depth of 4
 */
public class CascadingBlock extends SemiSupportedBlock {

    public CascadingBlock(float percentPerSide, Block fallBlock, Properties settings) {
        super(percentPerSide, fallBlock, settings);
    }

    public CascadingBlock(float percentPerSide, Properties settings) {
        super(percentPerSide, settings);
    }

    @Override
    protected boolean collapse(Level world, BlockPos pos, RandomSource random, int step, boolean force) {
        if ((step < 4) && (force || !this.supported(world, pos, random))) {
            List<BlockPos> allPositions = Arrays.asList(pos.above(), pos.below(), pos.north(), pos.east(), pos.south(), pos.west());
            Collections.shuffle(allPositions);
            for (BlockPos dest : allPositions) {
                Block bl = world.getBlockState(dest).getBlock();
                if (bl instanceof CollapsibleBlock) {
                    ((CollapsibleBlock) bl).collapse(world, dest, random, step + 1 + random.nextInt(1), true);
                }
            }

            if (isFree(world.getBlockState(pos.below()))) {
                world.playSound(null, pos, world.getBlockState(pos).getSoundType().getBreakSound(), SoundSource.BLOCKS, 0.5F, 0.6F + world.random.nextFloat() * 0.4F);

                return world.addFreshEntity(createFallingBlockEntity(world, pos, pos));
            } else {
                List<BlockPos> neighborPositions = Arrays.asList(pos.north(), pos.east(), pos.south(), pos.west());
                Collections.shuffle(neighborPositions);
                for (BlockPos dest : neighborPositions) {
                    if (isFree(world.getBlockState(dest)) && isFree(world.getBlockState(dest.below()))) {
                        world.playSound(null, pos, world.getBlockState(pos).getSoundType().getBreakSound(), SoundSource.BLOCKS, 0.5F, 0.6F + world.random.nextFloat() * 0.4F);
                        return world.addFreshEntity(createFallingBlockEntity(world, dest, pos));
                    }
                }
            }
        }
        return false;
    }
}
