package net.cr24.primeval.block.functional;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class SuspendedLadderBlock extends LadderBlock {
    public SuspendedLadderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockState upState = world.getBlockState(pos.up());
        if (upState.getBlock() instanceof SuspendedLadderBlock) {
            if (upState.get(FACING) == direction) return true;
        }
        return super.canPlaceAt(state, world, pos);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

//    @Override
//    @Nullable
//    public BlockState getPlacementState(ItemPlacementContext ctx) {
//        World world = ctx.getWorld();
//        BlockPos blockPos = ctx.getBlockPos();
//        BlockState destinationState = world.getBlockState(blockPos);
//        if (destinationState.getBlock() instanceof SuspendedLadderBlock) {
//            if (canPlaceAt(getDefaultState().with(FACING, destinationState.get(FACING)), world, blockPos.down())) {
//                world.setBlockState(blockPos.down(), getDefaultState().with(FACING, destinationState.get(FACING)));
//                return null;
//            }
//        }
//        return super.getPlacementState(ctx);
//    }
}
