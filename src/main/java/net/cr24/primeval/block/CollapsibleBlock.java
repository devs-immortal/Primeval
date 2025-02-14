package net.cr24.primeval.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.PrimevalMain;
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
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * A block that will collapse and fall both:
 *   directly down
 *   1 block orthogonally adjacent to itself
 */
public class CollapsibleBlock extends FallingBlock {

    public static final MapCodec<CollapsibleBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Registries.BLOCK.getCodec().fieldOf("fall_block").forGetter((block) -> block.fallBlock),
            createSettingsCodec()
    ).apply(instance, CollapsibleBlock::new));

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
        this.fallBlock = this;
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (!(neighborState.getBlock() instanceof FluidBlock)) {
            tickView.scheduleBlockTick(pos, this, this.getFallDelay());
            return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
        }
        return state;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        collapse(world, pos, random, 0, false);
    }

    /*
     * Is attempted on a scheduled tick.
     * Controls logic for how this block is expected
     * fall based on blocks around it
     */
    protected boolean collapse(World world, BlockPos pos, Random random, int step, boolean force) {
        if (!this.supported(world, pos, random)) {
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

    protected boolean supported(World world, BlockPos pos, Random random) {
        return false;
    }

    public static boolean canFallThrough(BlockState state) {
        return state.isAir() || state.isIn(BlockTags.FIRE) || state.isLiquid() || !state.isSolid();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    }

    protected FallingBlockEntity createFallingBlockEntity(World world, BlockPos fallPos, BlockPos origin) {
        BlockState fallingBlockState = fallBlock == null ? world.getBlockState(origin) : fallBlock.getDefaultState();
        if (fallPos.equals(origin)) {
            return new CollapsingBlockEntity(world, (double)fallPos.getX() + 0.5D, fallPos.getY(), (double)fallPos.getZ() + 0.5D, fallingBlockState, origin, world.getBlockState(origin));
        } else {
            return new CollapsingBlockEntity(world, (double)fallPos.getX() + 0.5D, (double)fallPos.getY() - 0.5D, (double)fallPos.getZ() + 0.5D, fallingBlockState, origin, world.getBlockState(origin));
        }
    }
}
