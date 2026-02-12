package net.cr24.primeval.block.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LeafBlock extends Block {

    public LeafBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        for (Direction d : TrunkBlock.DIRECTION_MAP.keySet()) {
            if (
                            world.getBlockState(pos.relative(d)).getBlock() instanceof TrunkBlock &&
                            world.getBlockState(pos.relative(d)).getValue(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()))
            ) return;
        }
        world.destroyBlock(pos, true);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        world.scheduleTick(pos, this, 2);
    }
}
