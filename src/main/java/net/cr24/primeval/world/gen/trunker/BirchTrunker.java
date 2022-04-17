package net.cr24.primeval.world.gen.trunker;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.TrunkBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class BirchTrunker extends AbstractTrunker {

    public static final BirchTrunker INSTANCE = new BirchTrunker();

    private BirchTrunker() {
        super(() -> PrimevalBlocks.BIRCH_TRUNK, () -> PrimevalBlocks.BIRCH_LEAVES);
    }

    @Override
    public void tickTrunk(BlockState state, World world, BlockPos pos, Random random, Direction[] directions) {
        int age = state.get(TrunkBlock.AGE);
        int size = state.get(TrunkBlock.SIZE);
        if (age == 0) {
            expandSize(state, world, pos, 0);
        } else if (age < 4) {
            if (world.getBlockState(pos.down()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.down()).get(TrunkBlock.SIZE) < size) {
                expandSize(state, world, pos, 0);
            }
        } else if (age < 11) {
            if (world.getBlockState(pos.down()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.down()).get(TrunkBlock.SIZE) < size) {
                expandSize(state, world, pos, 1);
            }
        } else if (age < 16) {
            if (world.getBlockState(pos.down()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.down()).get(TrunkBlock.SIZE) < size) {
                expandSize(state, world, pos, 2);
            }
        }

        if (directions.length > 0 && age < 19) {
            if (age < 6) {
                for (Direction d : directions) {
                    BlockPos newBranchPos = pos.offset(d);
                    world.setBlockState(newBranchPos, logBlockState
                            .with(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                            .with(TrunkBlock.AGE, Math.min(20, age + random.nextInt(2) + 1))
                    );
                    placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                }
            } else if (age < 11) {
                for (Direction d : directions) {
                    int ageOffset = 1;
                    if (d != Direction.UP) ageOffset += 9;
                    BlockPos newBranchPos = pos.offset(d);
                    world.setBlockState(newBranchPos, logBlockState
                            .with(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                            .with(TrunkBlock.AGE, Math.min(20, age + random.nextInt(2) + ageOffset))
                    );
                    placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                    if (random.nextInt(3) == 0) {
                        Direction rd = TrunkBlock.XZ_DIRECTIONS[random.nextInt(4)];
                        placeLeaves(world, newBranchPos.offset(rd), rd.getOpposite());
                    }
                }
            } else if (age < 15) {
                for (Direction d : directions) {
                    int ageOffset = 1;
                    if (d != Direction.UP) ageOffset += 2;
                    BlockPos newBranchPos = pos.offset(d);
                    world.setBlockState(newBranchPos, logBlockState
                            .with(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                            .with(TrunkBlock.AGE, age + random.nextInt(2) + ageOffset)
                    );
                    for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                        if (random.nextInt(4) == 0) placeLeaves(world, newBranchPos.offset(d2), d2.getOpposite());
                    }
                    placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                }
            } else {
                for (Direction d : directions) {
                    BlockPos newBranchPos = pos.offset(d);
                    world.setBlockState(newBranchPos, logBlockState
                            .with(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                            .with(TrunkBlock.AGE, Math.min(20, age + random.nextInt(3) + 1))
                    );
                    placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                }
            }
        } else if (age > 15) {
            world.setBlockState(pos, state.with(TrunkBlock.GROWN, true));
            for (Direction d : TrunkBlock.XZ_DIRECTIONS) {
                placeLeaves(world, pos.offset(d), d.getOpposite());
            }
            if (random.nextBoolean()) placeLeaves(world, pos.up(), Direction.DOWN);
        } else if (age > 8) {
            world.setBlockState(pos, state.with(TrunkBlock.GROWN, true));
            for (Direction d : TrunkBlock.XZ_DIRECTIONS) {
                if (random.nextBoolean()) placeLeaves(world, pos.offset(d), d.getOpposite());
            }
        }
    }
}
