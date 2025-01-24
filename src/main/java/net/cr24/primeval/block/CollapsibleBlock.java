package net.cr24.primeval.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.entity.CollapsingBlockEntity;
import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollapsibleBlock extends FallingBlock {

    public static final MapCodec<CollapsibleBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(
                Registries.BLOCK.getCodec().fieldOf("fallBlock").forGetter((block) -> block.fallBlock),
                createSettingsCodec()
        ).apply(instance, CollapsibleBlock::new);
    });

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return CODEC;
    }

    public Block fallBlock;

    public CollapsibleBlock(Block block, Settings settings) {
        super(settings);
        this.fallBlock = block;
    }

    public CollapsibleBlock(Settings settings) {
        super(settings);
    }


    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (!(neighborState.getBlock() instanceof FluidBlock)) {
            tickView.scheduleBlockTick(pos, this, this.getFallDelay());
        }
        return state;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        collapse(world, pos, random, 0, false);
    }

    protected boolean collapse(World world, BlockPos pos, Random random, int step, boolean force) {
        if (!this.supported(world, pos, random)) {
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

    protected boolean supported(World world, BlockPos pos, Random random) {
        return false;
    }

    public static boolean canFallThrough(BlockState state) {
        return state.isAir() || state.isSolid();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}

    protected FallingBlockEntity getFallingBlockEntity(World world, BlockPos pos, BlockPos origin) {
        BlockState fallingBlockState = fallBlock == null ? world.getBlockState(origin) : fallBlock.getDefaultState();
        if (pos.equals(origin)) {
            return new CollapsingBlockEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, fallingBlockState, origin, world.getBlockState(origin));
        } else {
            return new CollapsingBlockEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() - 0.5D, (double)pos.getZ() + 0.5D, fallingBlockState, origin, world.getBlockState(origin));
        }
    }
}
