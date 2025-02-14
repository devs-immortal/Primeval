package net.cr24.primeval.block;

import net.minecraft.block.Block;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * A collapsible block that falls based
 * on the number of full face neighbors.
 *
 * When this block collapses, it triggers
 * neighboring CascadingBlocks up to a
 * depth of 4
 */
public class CascadingBlock extends SemiSupportedBlock {

    public CascadingBlock(float percentPerSide, Block fallBlock, Settings settings) {
        super(percentPerSide, fallBlock, settings);
    }

    public CascadingBlock(float percentPerSide, Settings settings) {
        super(percentPerSide, settings);
    }

    @Override
    protected boolean collapse(World world, BlockPos pos, Random random, int step, boolean force) {
        if ((step < 4) && (force || !this.supported(world, pos, random))) {
            List<BlockPos> allPositions = Arrays.asList(pos.up(), pos.down(), pos.north(), pos.east(), pos.south(), pos.west());
            Collections.shuffle(allPositions);
            for (BlockPos dest : allPositions) {
                Block bl = world.getBlockState(dest).getBlock();
                if (bl instanceof CollapsibleBlock) {
                    ((CollapsibleBlock) bl).collapse(world, dest, random, step + 1 + random.nextInt(1), true);
                }
            }

            if (canFallThrough(world.getBlockState(pos.down()))) {
                world.playSound(null, pos, world.getBlockState(pos).getSoundGroup().getBreakSound(), SoundCategory.BLOCKS, 0.5F, 0.6F + world.random.nextFloat() * 0.4F);

                return world.spawnEntity(createFallingBlockEntity(world, pos, pos));
            } else {
                List<BlockPos> neighborPositions = Arrays.asList(pos.north(), pos.east(), pos.south(), pos.west());
                Collections.shuffle(neighborPositions);
                for (BlockPos dest : neighborPositions) {
                    if (canFallThrough(world.getBlockState(dest)) && canFallThrough(world.getBlockState(dest.down()))) {
                        world.playSound(null, pos, world.getBlockState(pos).getSoundGroup().getBreakSound(), SoundCategory.BLOCKS, 0.5F, 0.6F + world.random.nextFloat() * 0.4F);
                        return world.spawnEntity(createFallingBlockEntity(world, dest, pos));
                    }
                }
            }
        }
        return false;
    }
}
