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
        Block nextBlock = world.getBlockState(pos.up()).getBlock();
        if (nextBlock instanceof TrunkBlock) {
            ((TrunkBlock) nextBlock).fell(world, pos.up(), world.getBlockState(pos.up()));
        }
    }

    public void fell(WorldAccess world, BlockPos pos, BlockState state) {
        world.breakBlock(pos, true);
        Block nextBlock = world.getBlockState(pos.up()).getBlock();
        if (nextBlock instanceof TrunkBlock) {
            ((TrunkBlock) nextBlock).fell(world, pos.up(), world.getBlockState(pos.up()));
        }
    }

    static {
        NATURAL = BooleanProperty.of("natural");;
    }
}
