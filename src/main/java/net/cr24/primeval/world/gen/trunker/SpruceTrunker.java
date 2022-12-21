package net.cr24.primeval.world.gen.trunker;

import net.cr24.primeval.block.plant.LeafBlock;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.plant.TrunkBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

import java.util.LinkedList;
import java.util.List;

public class SpruceTrunker extends AbstractTrunker {

    public static final SpruceTrunker INSTANCE = new SpruceTrunker();

    private SpruceTrunker() {
        super(() -> PrimevalBlocks.SPRUCE_TRUNK, () -> PrimevalBlocks.SPRUCE_LEAVES);
    }

    @Override
    public List<BlockPos> growSapling(WorldAccess world, BlockPos pos, Random random) {
        world.setBlockState(pos, logBlockState.with(TrunkBlock.AGE, 0).with(TrunkBlock.UP, true).with(TrunkBlock.DOWN, true).with(TrunkBlock.SIZE, 2), 3);
        world.setBlockState(pos.up(), logBlockState.with(TrunkBlock.AGE, random.nextInt(2)+1).with(TrunkBlock.DOWN, true), 3);
        placeLeaves(world, pos.up(2), Direction.DOWN);
        List<BlockPos> posList = new LinkedList<BlockPos>();
        posList.add(pos);
        posList.add(pos.up());
        posList.add(pos.up(2));
        return posList;
    }

    @Override
    public List<BlockPos> tickTrunk(BlockState state, WorldAccess world, BlockPos pos, Random random, Direction[] directions) {
        List<BlockPos> posList = new LinkedList<>();
        int age = state.get(TrunkBlock.AGE);
        int size = state.get(TrunkBlock.SIZE);
        if (age > 17) { // Edge branches
            for (Direction d : directions) {
                BlockPos newBranchPos = pos.offset(d);
                if (age >= 23 || world.getBlockState(pos.up()).getBlock() instanceof LeafBlock) {
                    world.setBlockState(newBranchPos, logBlockState
                                    .with(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                                    .with(TrunkBlock.AGE, 24)
                                    .with(TrunkBlock.GROWN, true),
                            3
                    );
                } else {
                    world.setBlockState(newBranchPos, logBlockState
                                    .with(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                                    .with(TrunkBlock.AGE, age + 1),
                            3
                    );
                    posList.add(newBranchPos);
                }

                world.setBlockState(pos, world.getBlockState(pos).with(TrunkBlock.DIRECTION_MAP.get(d), true).with(TrunkBlock.GROWN, true), 3);
                for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                    placeLeaves(world, newBranchPos.offset(d2), d2.getOpposite());
                }
                if (random.nextBoolean()) placeLeaves(world, newBranchPos.up(), Direction.DOWN);

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

            if (world.getBlockState(pos.up()).getBlock() instanceof LeafBlock) {
                int growthAmount;
                if (age > 2 && (age) % 4 == 0) {
                    for (Direction d : TrunkBlock.XZ_DIRECTIONS) {
                        BlockPos newBranchPos = pos.offset(d);
                        world.setBlockState(pos, world.getBlockState(pos).with(TrunkBlock.DIRECTION_MAP.get(d), true), 3);

                        world.setBlockState(newBranchPos, logBlockState
                                        .with(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                                        .with(TrunkBlock.AGE, Math.min(24, 20+((age-2) / 4))),
                                3
                        );
                        for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                            placeLeaves(world, newBranchPos.offset(d2), d2.getOpposite());
                        }
                        if (random.nextBoolean()) placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                    }
                    growthAmount = 1 + random.nextInt(2);
                } else {
                    growthAmount = 1;
                }
                BlockPos newBranchPos = pos.up();
                world.setBlockState(newBranchPos, logBlockState
                                .with(TrunkBlock.DIRECTION_MAP.get(Direction.DOWN), true)
                                .with(TrunkBlock.AGE, Math.min(24, age + growthAmount)),
                        3
                );
                placeLeaves(world, pos.up(2), Direction.DOWN);
                posList.add(pos.up());
            }

            if (stillGrowing) {
                posList.add(pos);
            }
        }

        return posList;
    }
}
