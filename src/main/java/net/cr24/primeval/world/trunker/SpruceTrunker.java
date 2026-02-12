package net.cr24.primeval.world.trunker;

import net.cr24.primeval.block.plant.LeafBlock;
import net.cr24.primeval.block.plant.TrunkBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import java.util.LinkedList;
import java.util.List;

public class SpruceTrunker extends AbstractTrunker {

    public static final SpruceTrunker INSTANCE = new SpruceTrunker();

    private SpruceTrunker() {
        super(() -> PrimevalBlocks.SPRUCE_TRUNK, () -> PrimevalBlocks.SPRUCE_LEAVES);
    }

    @Override
    public List<BlockPos> growSapling(LevelAccessor world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, logBlockState.setValue(TrunkBlock.AGE, 0).setValue(TrunkBlock.UP, true).setValue(TrunkBlock.DOWN, true).setValue(TrunkBlock.SIZE, 2), 3);
        world.setBlock(pos.above(), logBlockState.setValue(TrunkBlock.AGE, random.nextInt(2)+1).setValue(TrunkBlock.DOWN, true), 3);
        placeLeaves(world, pos.above(2), Direction.DOWN);
        List<BlockPos> posList = new LinkedList<BlockPos>();
        posList.add(pos);
        posList.add(pos.above());
        posList.add(pos.above(2));
        return posList;
    }

    @Override
    public List<BlockPos> tickTrunk(BlockState state, LevelAccessor world, BlockPos pos, RandomSource random, Direction[] directions) {
        List<BlockPos> posList = new LinkedList<>();
        int age = state.getValue(TrunkBlock.AGE);
        int size = state.getValue(TrunkBlock.SIZE);
        if (age > 17) { // Edge branches
            for (Direction d : directions) {
                BlockPos newBranchPos = pos.relative(d);
                if (age >= 23 || world.getBlockState(pos.above()).getBlock() instanceof LeafBlock) {
                    world.setBlock(newBranchPos, logBlockState
                                    .setValue(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                                    .setValue(TrunkBlock.AGE, 24)
                                    .setValue(TrunkBlock.GROWN, true),
                            3
                    );
                } else {
                    world.setBlock(newBranchPos, logBlockState
                                    .setValue(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                                    .setValue(TrunkBlock.AGE, age + 1),
                            3
                    );
                    posList.add(newBranchPos);
                }

                world.setBlock(pos, world.getBlockState(pos).setValue(TrunkBlock.DIRECTION_MAP.get(d), true).setValue(TrunkBlock.GROWN, true), 3);
                for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                    placeLeaves(world, newBranchPos.relative(d2), d2.getOpposite());
                }
                if (random.nextBoolean()) placeLeaves(world, newBranchPos.above(), Direction.DOWN);

            }
        } else {
            boolean stillGrowing;
            if (age > 15) {
                stillGrowing = expandSize(state, world, pos, 2);
            } else if (age > 8) {
                stillGrowing = expandSize(state, world, pos, 1);
            } else {
                stillGrowing = expandSize(state, world, pos, 0);
            }

            if (world.getBlockState(pos.above()).getBlock() instanceof LeafBlock) {
                int growthAmount;
                if (age > 2 && (age) % 4 == 0) {
                    for (Direction d : TrunkBlock.XZ_DIRECTIONS) {
                        BlockPos newBranchPos = pos.relative(d);
                        world.setBlock(pos, world.getBlockState(pos).setValue(TrunkBlock.DIRECTION_MAP.get(d), true), 3);

                        world.setBlock(newBranchPos, logBlockState
                                        .setValue(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                                        .setValue(TrunkBlock.AGE, Math.min(24, 20+((age-2) / 4))),
                                3
                        );
                        for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                            placeLeaves(world, newBranchPos.relative(d2), d2.getOpposite());
                        }
                        if (random.nextBoolean()) placeLeaves(world, newBranchPos.above(), Direction.DOWN);
                    }
                    growthAmount = 1 + random.nextInt(2);
                } else {
                    growthAmount = 1;
                }
                BlockPos newBranchPos = pos.above();
                world.setBlock(newBranchPos, logBlockState
                                .setValue(TrunkBlock.DIRECTION_MAP.get(Direction.DOWN), true)
                                .setValue(TrunkBlock.AGE, Math.min(24, age + growthAmount)),
                        3
                );
                placeLeaves(world, pos.above(2), Direction.DOWN);
                posList.add(pos.above());
            }

            if (stillGrowing) {
                posList.add(pos);
            }
        }

        return posList;
    }
}
