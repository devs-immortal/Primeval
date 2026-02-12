package net.cr24.primeval.world.trunker;

import net.cr24.primeval.block.plant.TrunkBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import java.util.LinkedList;
import java.util.List;

public class OakTrunker extends AbstractTrunker {

    public static final OakTrunker INSTANCE = new OakTrunker();

    private OakTrunker() {
        super(() -> PrimevalBlocks.OAK_TRUNK, () -> PrimevalBlocks.OAK_LEAVES);
    }

    @Override
    public List<BlockPos> tickTrunk(BlockState state, LevelAccessor world, BlockPos pos, RandomSource random, Direction[] directions) {
        List<BlockPos> posList = new LinkedList<>();
        int age = state.getValue(TrunkBlock.AGE);
        int size = state.getValue(TrunkBlock.SIZE);
        boolean stillGrowing = false;
        if (age == 0) {
            stillGrowing = expandSize(state, world, pos, 0);
        } else if (age < 4) {
            if (world.getBlockState(pos.below()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.below()).getValue(TrunkBlock.SIZE) < size) {
                stillGrowing = expandSize(state, world, pos, 0);
            } else {
                stillGrowing = true;
            }
        } else if (age < 8) {
            if (world.getBlockState(pos.below()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.below()).getValue(TrunkBlock.SIZE) < size) {
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
                BlockPos newBranchPos = pos.relative(d);
                world.setBlock(newBranchPos, logBlockState
                        .setValue(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                        .setValue(TrunkBlock.AGE, Math.min(18, age + random.nextInt(2)+1)),
                        3
                );
                posList.add(newBranchPos);
                world.setBlock(pos, state.setValue(TrunkBlock.DIRECTION_MAP.get(d), true), 3);
                if (age < 6) {
                    placeLeaves(world, newBranchPos.above(), Direction.DOWN);
                } else if (age < 9) {
                    for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                        if (random.nextBoolean()) placeLeaves(world, newBranchPos.relative(d2), d2.getOpposite());
                    }
                    placeLeaves(world, newBranchPos.above(), Direction.DOWN);
                } else if (age < 12) {
                    for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                        if (random.nextInt(3) < 2) placeLeaves(world, newBranchPos.relative(d2), d2.getOpposite());
                    }
                    if (random.nextInt(4) == 0) placeLeaves(world, newBranchPos.above(), Direction.DOWN);
                } else {
                    for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                        if (random.nextInt(3) < 2) placeLeaves(world, newBranchPos.relative(d2), d2.getOpposite());
                    }
                    if (random.nextBoolean()) placeLeaves(world, newBranchPos.above(), Direction.DOWN);
                }
            }
        } else if (age > 12) {
            world.setBlock(pos, state.setValue(TrunkBlock.GROWN, true), 3);
            for (Direction d : TrunkBlock.XZ_DIRECTIONS) {
                placeLeaves(world, pos.relative(d), d.getOpposite());
            }
            placeLeaves(world, pos.above(), Direction.DOWN);
            if (random.nextBoolean()) placeLeaves(world, pos.below(), Direction.UP);
        }
        return posList;
    }

}
