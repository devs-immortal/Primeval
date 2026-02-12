package net.cr24.primeval.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class SuspendedLadderBlock extends LadderBlock {
    public SuspendedLadderBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockState upState = world.getBlockState(pos.above());
        if (upState.getBlock() instanceof SuspendedLadderBlock) {
            if (upState.getValue(FACING) == direction) return true;
        }
        return super.canSurvive(state, world, pos);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof SuspendedLadderBlock) {
            for (int i = 1; i <= 20; i++) {
                if (world.getBlockState(pos.below(i)).isAir() && this.canSurvive(state, world, pos.below(i))) {
                    world.playLocalSound(pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                    stack.consume(1, player);
                    world.setBlockAndUpdate(pos.below(i), state);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

}
