package net.cr24.primeval.world.gen.trunker;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.TrunkBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public abstract class AbstractTrunker {

    protected BlockState logBlockState;
    protected BlockState leafBlockState;
    private final Supplier<Block> log;
    private final Supplier<Block> leaves;

    protected AbstractTrunker(Supplier<Block> log, Supplier<Block> leaves) {
        this.log = log;
        this.leaves = leaves;
    }

    public void build() {
        logBlockState = log.get().getDefaultState();
        leafBlockState = leaves.get().getDefaultState();
    }

    /*
     * Grows a sapling into a small trunk based tree
     * Should replace the sapling block at 'pos' or
     * set that block to air
     */
    public void growSapling(World world, BlockPos pos) {
        world.setBlockState(pos, logBlockState.with(TrunkBlock.AGE, 0).with(TrunkBlock.UP, true).with(TrunkBlock.DOWN, true).with(TrunkBlock.SIZE, 2));
        world.setBlockState(pos.up(), logBlockState.with(TrunkBlock.AGE, 1).with(TrunkBlock.DOWN, true));
        placeLeaves(world, pos.up(2), Direction.DOWN);
    }

    /*
     * Defines what a trunk should do when it is randomly
     * ticked, expanding the branches, etc
     */
    public abstract void tickTrunk(BlockState state, World world, BlockPos pos, Random random, Direction[] directions);

    protected void placeLeaves(World world, BlockPos pos, Direction growingFrom) {
        if (world.getBlockState(pos).isAir()) {
            world.setBlockState(pos, leafBlockState.with(LeavesBlock.DISTANCE, 1));
            world.setBlockState(pos.offset(growingFrom), world.getBlockState(pos.offset(growingFrom)).with(TrunkBlock.DIRECTION_MAP.get(growingFrom.getOpposite()), true));
        }
    }

    protected void expandSize(BlockState state, World world, BlockPos pos, int minSize) {
        int size = state.get(TrunkBlock.SIZE);
        if (size > minSize) {
            world.setBlockState(pos, state.with(TrunkBlock.SIZE, size - 1));
        } else {
            world.setBlockState(pos, state.with(TrunkBlock.GROWN, true));
        }
    }
}
