package net.cr24.primeval.world.gen.trunker;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.TrunkBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class OakTrunker extends AbstractTrunker {

    public static final OakTrunker INSTANCE = new OakTrunker();

    private OakTrunker() {
        super(() -> PrimevalBlocks.OAK_TRUNK, () -> PrimevalBlocks.OAK_LEAVES);
    }

    @Override
    public void tickTrunk(BlockState state, World world, BlockPos pos, Random random, Direction[] directions) {
        int age = state.get(TrunkBlock.AGE);
        int size = state.get(TrunkBlock.SIZE);
        if (age == 0) {
            expandSize(state.with(TrunkBlock.UP, true), world, pos, 0);
        } else if (age < 4) {
            if (world.getBlockState(pos.down()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.down()).get(TrunkBlock.SIZE) < size) {
                expandSize(state.with(TrunkBlock.UP, true), world, pos, 0);
            }
        } else if (age < 8) {
            if (world.getBlockState(pos.down()).getBlock() instanceof TrunkBlock && world.getBlockState(pos.down()).get(TrunkBlock.SIZE) < size) {
                expandSize(state.with(TrunkBlock.UP, true), world, pos, 1);
            }
        } else if (age < 11)  {
            expandSize(state, world, pos, 2);
        }

        if (directions.length > 0 && age < 15) {
            // Expand into directions
            for (Direction d : directions) {
                if (age > 12 && random.nextBoolean()) continue;
                BlockPos newBranchPos = pos.offset(d);
                world.setBlockState(newBranchPos, logBlockState
                        .with(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()), true)
                        .with(TrunkBlock.AGE, Math.min(16, age + random.nextInt(2)+1))
                );
                world.setBlockState(pos, state.with(TrunkBlock.DIRECTION_MAP.get(d), true));
                if (age < 6) {
                    placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                } else if (age < 8) {
                    for (Direction d2 : TrunkBlock.XZ_DIRECTIONS) {
                        if (random.nextBoolean()) placeLeaves(world, newBranchPos.offset(d2), d2.getOpposite());
                    }
                    placeLeaves(world, newBranchPos.up(), Direction.DOWN);
                } else if (age < 10) {
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
        } else if (age > 10) {
            world.setBlockState(pos, state.with(TrunkBlock.GROWN, true));
            for (Direction d : TrunkBlock.XZ_DIRECTIONS) {
                placeLeaves(world, pos.offset(d), d.getOpposite());
            }
            placeLeaves(world, pos.up(), Direction.DOWN);
            if (random.nextBoolean()) placeLeaves(world, pos.down(), Direction.UP);
        }
    }

}
