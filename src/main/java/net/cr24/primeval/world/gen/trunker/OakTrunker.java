package net.cr24.primeval.world.gen.trunker;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.TrunkBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

import java.util.LinkedList;
import java.util.List;

public class OakTrunker extends AbstractTrunker {

    public static final OakTrunker INSTANCE = new OakTrunker();

    private OakTrunker() {
        super(() -> PrimevalBlocks.OAK_TRUNK, () -> PrimevalBlocks.OAK_LEAVES);
    }

    @Override
    public List<BlockPos> tickTrunk(BlockState state, WorldAccess world, BlockPos pos, Random random, Direction[] directions) {
        List<BlockPos> posList = new LinkedList<>();
        int age = state.get(TrunkBlock.AGE);
        int size = state.get(TrunkBlock.SIZE);
        boolean stillGrowing = false;
        if (age == 0) {
            stillGrowing = expandSize(state, world, pos, 0);
        } else if (age < 4) {
            if (world.getBlockState(pos.down()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.down()).get(TrunkBlock.SIZE) < size) {
                stillGrowing = expandSize(state, world, pos, 0);
            } else {
                stillGrowing = true;
            }
        } else if (age < 8) {
            if (world.getBlockState(pos.down()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.down()).get(TrunkBlock.SIZE) < size) {
                stillGrowing = expandSize(state, world, pos, 1);
            } else {
                stillGrowing = true;
            }
        } else if (age < 11)  {
            stillGrowing = expandSize(state, world, pos, 2);
        }
        if (stillGrowing) posList.add(pos);

        if (directions.length > 0 && age < 18) {
            // Expand into directions
            for (Direction d : directions) {
                if (age > 12 && random.nextBoolean()) continue;
                BlockPos newBranchPos = pos.offset(d);
                world.setBlockState(newBranchPos, logBlockState
                        .with(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                        .with(TrunkBlock.AGE, Math.min(18, age + random.nextInt(2)+1)),
                        3
                );
                posList.add(newBranchPos);
                world.setBlockState(pos, state.with(TrunkBlock.DIRECTION_MAP.get(d), true), 3);
                if (age < 6) {
                    placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                } else if (age < 9) {
                    for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                        if (random.nextBoolean()) placeLeaves(world, newBranchPos.offset(d2), d2.getOpposite());
                    }
                    placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                } else if (age < 12) {
                    for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                        if (random.nextInt(3) < 2) placeLeaves(world, newBranchPos.offset(d2), d2.getOpposite());
                    }
                    if (random.nextInt(4) == 0) placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                } else {
                    for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                        if (random.nextInt(3) < 2) placeLeaves(world, newBranchPos.offset(d2), d2.getOpposite());
                    }
                    if (random.nextBoolean()) placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                }
            }
        } else if (age > 12) {
            world.setBlockState(pos, state.with(TrunkBlock.GROWN, true), 3);
            for (Direction d : TrunkBlock.XZ_DIRECTIONS) {
                placeLeaves(world, pos.offset(d), d.getOpposite());
            }
            placeLeaves(world, pos.up(), Direction.DOWN);
            if (random.nextBoolean()) placeLeaves(world, pos.down(), Direction.UP);
        }
        return posList;
    }

}
