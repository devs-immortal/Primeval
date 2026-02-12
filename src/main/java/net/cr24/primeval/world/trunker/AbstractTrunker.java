package net.cr24.primeval.world.trunker;

import net.cr24.primeval.block.plant.TrunkBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
        logBlockState = log.get().defaultBlockState();
        leafBlockState = leaves.get().defaultBlockState();
    }

    /*
     * Grows a sapling into a small trunk based tree
     * Should replace the sapling block at 'pos' or
     * set that block to air
     */
    public List<BlockPos> growSapling(LevelAccessor world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, logBlockState.setValue(TrunkBlock.AGE, 0).setValue(TrunkBlock.UP, true).setValue(TrunkBlock.DOWN, true).setValue(TrunkBlock.SIZE, 2), 3);
        world.setBlock(pos.above(), logBlockState.setValue(TrunkBlock.AGE, 1).setValue(TrunkBlock.DOWN, true), 3);
        placeLeaves(world, pos.above(2), Direction.DOWN);
        List<BlockPos> posList = new LinkedList<BlockPos>();
        posList.add(pos);
        posList.add(pos.above());
        posList.add(pos.above(2));
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
    public abstract List<BlockPos> tickTrunk(BlockState state, LevelAccessor world, BlockPos pos, RandomSource random, Direction[] directions);

    protected void placeLeaves(LevelAccessor world, BlockPos pos, Direction growingFrom) {
        if (world.getBlockState(pos).isAir() && world.getBlockState(pos.relative(growingFrom)).getBlock() instanceof TrunkBlock) {
            world.setBlock(pos, leafBlockState, 3);
            world.setBlock(pos.relative(growingFrom), world.getBlockState(pos.relative(growingFrom)).setValue(TrunkBlock.DIRECTION_MAP.get(growingFrom.getOpposite()), true), 4);
        }
    }

    /*
     * Expand the thickness of a trunk block
     *
     * Returns true if trunk can still grow, ie not fully grown
     */
    protected boolean expandSize(BlockState state, LevelAccessor world, BlockPos pos, int minSize) {
        int size = state.getValue(TrunkBlock.SIZE);
        if (size > minSize) {
            world.setBlock(pos, state.setValue(TrunkBlock.SIZE, size - 1), 3);
            return true;
        } else {
            world.setBlock(pos, state.setValue(TrunkBlock.GROWN, true), 3);
            return false;
        }
    }
}
