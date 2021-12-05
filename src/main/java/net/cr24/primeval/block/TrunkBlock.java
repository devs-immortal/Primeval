package net.cr24.primeval.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class TrunkBlock extends PillarBlock {
    public static final BooleanProperty NATURAL;

    public TrunkBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(NATURAL, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{NATURAL});
    }

    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (!state.get(NATURAL)) return;
        BlockState nextBlockState = world.getBlockState(pos.up());
        if (nextBlockState.getBlock() instanceof TrunkBlock && nextBlockState.get(NATURAL)) {
            ((TrunkBlock) nextBlockState.getBlock()).fell(world, pos.up(), world.getBlockState(pos.up()));
        }
    }

    public void fell(WorldAccess world, BlockPos pos, BlockState state) {
        world.breakBlock(pos, true);
        for (BlockPos dest : new BlockPos[]{pos.up(), pos.north(), pos.east(), pos.south(), pos.west(),
                                            pos.up().north(), pos.up().east(), pos.up().south(), pos.up().west(),
                                            pos.north().east(), pos.east().south(), pos.south().west(), pos.west().north(),
                                            pos.up().north().east(), pos.up().east().south(), pos.up().south().west(), pos.up().west().north()
                                            }) {
            BlockState nextBlockState = world.getBlockState(dest);
            if (nextBlockState.getBlock() instanceof TrunkBlock && nextBlockState.get(NATURAL)) {
                ((TrunkBlock) nextBlockState.getBlock()).fell(world, dest, world.getBlockState(dest));
            }
        }
    }

    static {
        NATURAL = BooleanProperty.of("natural");;
    }
}
