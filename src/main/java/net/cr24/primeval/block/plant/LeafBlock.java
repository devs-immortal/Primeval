package net.cr24.primeval.block.plant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class LeafBlock extends Block {

    public LeafBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        for (Direction d : TrunkBlock.DIRECTION_MAP.keySet()) {
            if (
                            world.getBlockState(pos.offset(d)).getBlock() instanceof TrunkBlock &&
                            world.getBlockState(pos.offset(d)).get(TrunkBlock.DIRECTION_MAP.get(d.getOpposite()))
            ) return;
        }
        world.breakBlock(pos, true);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        world.scheduleBlockTick(pos, this, 2);
    }
}
