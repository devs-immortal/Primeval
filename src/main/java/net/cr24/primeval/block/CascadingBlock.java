package net.cr24.primeval.block;

import net.minecraft.block.Block;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CascadingBlock extends SemiSupportedBlock {

    public CascadingBlock(Settings settings, float percentPerSide, Block fallBlock) {
        super(settings, percentPerSide, fallBlock);
    }

    public CascadingBlock(Settings settings, float percentPerSide) {
        super(settings, percentPerSide);
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
                return world.spawnEntity(getFallingBlockEntity(world, pos, pos));
            } else {
                List<BlockPos> neighborPositions = Arrays.asList(pos.north(), pos.east(), pos.south(), pos.west());
                Collections.shuffle(neighborPositions);
                for (BlockPos dest : neighborPositions) {
                    if (canFallThrough(world.getBlockState(dest)) && canFallThrough(world.getBlockState(dest.down()))) {
                        world.playSound(null, pos, world.getBlockState(pos).getSoundGroup().getBreakSound(), SoundCategory.BLOCKS, 0.5F, 0.6F + world.random.nextFloat() * 0.4F);
                        return world.spawnEntity(getFallingBlockEntity(world, dest, pos));
                    }
                }
            }
        }
        return false;
    }
}
