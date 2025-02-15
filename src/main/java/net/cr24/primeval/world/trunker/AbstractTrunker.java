package net.cr24.primeval.world.trunker;

import net.cr24.primeval.block.plant.TrunkBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

import java.util.LinkedList;
import java.util.List;
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
    public List<BlockPos> growSapling(WorldAccess world, BlockPos pos, Random random) {
        world.setBlockState(pos, logBlockState.with(TrunkBlock.AGE, 0).with(TrunkBlock.UP, true).with(TrunkBlock.DOWN, true).with(TrunkBlock.SIZE, 2), 3);
        world.setBlockState(pos.up(), logBlockState.with(TrunkBlock.AGE, 1).with(TrunkBlock.DOWN, true), 3);
        placeLeaves(world, pos.up(2), Direction.DOWN);
        List<BlockPos> posList = new LinkedList<BlockPos>();
        posList.add(pos);
        posList.add(pos.up());
        posList.add(pos.up(2));
        return posList;
    }

    /*
     * Defines what a trunk should do when it is randomly
     * ticked, expanding the branches, etc
     *
     * Returns a list of newly created branch block positions
     * that can still be ticked, including the currently
     * ticked position, if it is still tickable
     *
     * state        : Blockstate of ticking block
     * world        : World containing this tree
     * pos          : Position of block
     * random       : Random instance from world
     * directions   : Connected leaves to this block
     */
    public abstract List<BlockPos> tickTrunk(BlockState state, WorldAccess world, BlockPos pos, Random random, Direction[] directions);

    protected void placeLeaves(WorldAccess world, BlockPos pos, Direction growingFrom) {
        if (world.getBlockState(pos).isAir()) {
            world.setBlockState(pos, leafBlockState, 3);
            world.setBlockState(pos.offset(growingFrom), world.getBlockState(pos.offset(growingFrom)).with(TrunkBlock.DIRECTION_MAP.get(growingFrom.getOpposite()), true), 4);
        }
    }

    /*
     * Expand the thickness of a trunk block
     *
     * Returns true if trunk can still grow, ie not fully grown
     */
    protected boolean expandSize(BlockState state, WorldAccess world, BlockPos pos, int minSize) {
        int size = state.get(TrunkBlock.SIZE);
        if (size > minSize) {
            world.setBlockState(pos, state.with(TrunkBlock.SIZE, size - 1), 3);
            return true;
        } else {
            world.setBlockState(pos, state.with(TrunkBlock.GROWN, true), 3);
            return false;
        }
    }
}
