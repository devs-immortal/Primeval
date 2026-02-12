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

public class BirchTrunker extends AbstractTrunker {

    public static final BirchTrunker INSTANCE = new BirchTrunker();

    private BirchTrunker() {
        super(() -> PrimevalBlocks.BIRCH_TRUNK, () -> PrimevalBlocks.BIRCH_LEAVES);
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
        } else if (age < 11) {
            if (world.getBlockState(pos.below()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.below()).getValue(TrunkBlock.SIZE) < size) {
                stillGrowing = expandSize(state, world, pos, 1);
            } else {
                stillGrowing = true;
            }
        } else if (age < 16) {
            if (world.getBlockState(pos.below()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.below()).getValue(TrunkBlock.SIZE) < size) {
                stillGrowing = expandSize(state, world, pos, 2);
            } else {
                stillGrowing = true;
            }
        }
        if (stillGrowing) posList.add(pos);

        if (directions.length > 0 && age < 19) {
            if (age < 6) {
                for (Direction d : directions) {
                    BlockPos newBranchPos = pos.relative(d);
                    world.setBlock(newBranchPos, logBlockState
                            .setValue(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                            .setValue(TrunkBlock.AGE, Math.min(20, age + random.nextInt(2) + 1)),
                            3
                    );
                    posList.add(newBranchPos);
                    placeLeaves(world, newBranchPos.above(), Direction.DOWN);
                }
            } else if (age < 11) {
                for (Direction d : directions) {
                    int ageOffset = 1;
                    if (d != Direction.UP) ageOffset += 9;
                    BlockPos newBranchPos = pos.relative(d);
                    world.setBlock(newBranchPos, logBlockState
                            .setValue(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                            .setValue(TrunkBlock.AGE, Math.min(20, age + random.nextInt(2) + ageOffset)),
                            3
                    );
                    posList.add(newBranchPos);
                    placeLeaves(world, newBranchPos.above(), Direction.DOWN);
                    if (random.nextInt(3) == 0) {
                        Direction rd = TrunkBlock.XZ_DIRECTIONS[random.nextInt(4)];
                        placeLeaves(world, newBranchPos.relative(rd), rd.getOpposite());
                    }
                }
            } else if (age < 15) {
                for (Direction d : directions) {
                    int ageOffset = 1;
                    if (d != Direction.UP) ageOffset += 2;
                    BlockPos newBranchPos = pos.relative(d);
                    world.setBlock(newBranchPos, logBlockState
                            .setValue(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                            .setValue(TrunkBlock.AGE, age + random.nextInt(2) + ageOffset),
                            3
                    );
                    posList.add(newBranchPos);
                    for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                        if (random.nextInt(4) == 0) placeLeaves(world, newBranchPos.relative(d2), d2.getOpposite());
                    }
                    placeLeaves(world, newBranchPos.above(), Direction.DOWN);
                }
            } else {
                for (Direction d : directions) {
                    BlockPos newBranchPos = pos.relative(d);
                    world.setBlock(newBranchPos, logBlockState
                            .setValue(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                            .setValue(TrunkBlock.AGE, Math.min(20, age + random.nextInt(3) + 1)),
                            3
                    );
                    posList.add(newBranchPos);
                    placeLeaves(world, newBranchPos.above(), Direction.DOWN);
                }
            }
        } else if (age > 15) {
            world.setBlock(pos, state.setValue(TrunkBlock.GROWN, true), 3);
            for (Direction d : TrunkBlock.XZ_DIRECTIONS) {
                placeLeaves(world, pos.relative(d), d.getOpposite());
            }
            if (random.nextBoolean()) placeLeaves(world, pos.above(), Direction.DOWN);
        } else if (age > 8) {
            world.setBlock(pos, state.setValue(TrunkBlock.GROWN, true), 3);
            for (Direction d : TrunkBlock.XZ_DIRECTIONS) {
                if (random.nextBoolean()) placeLeaves(world, pos.relative(d), d.getOpposite());
            }
        }
        return posList;
    }
}
