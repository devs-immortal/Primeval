package net.cr24.primeval.block.functional;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import java.util.function.Supplier;

public class LogPileBlock extends Block implements Waterloggable {

    public static final BooleanProperty WATERLOGGED;
    public static final IntProperty AMOUNT;
    private final Supplier<Item> logItem;

    public LogPileBlock(Supplier<Item> logItem, Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(AMOUNT, 0));
        this.logItem = logItem;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack itemStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int amount = state.get(AMOUNT);
        if (amount < 7 && itemStack.isOf(logItem.get())) {
            world.setBlockState(pos, state.with(LogPileBlock.AMOUNT, state.get(LogPileBlock.AMOUNT)+1));
            if (!player.isInCreativeMode()) itemStack.decrement(1);
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 0.5f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            return ActionResult.SUCCESS;
        } else if (itemStack.isEmpty()) {
            player.giveItemStack(new ItemStack(logItem.get()));
            if (amount > 0) {
                world.setBlockState(pos, state.with(AMOUNT, amount-1));
            } else {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 0.3f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED, AMOUNT);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        AMOUNT = IntProperty.of("amount", 0, 7);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, (4.0D * Math.ceil((1+state.get(AMOUNT))/2.0d)), 15.0D);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, (4.0D * Math.ceil((1+state.get(AMOUNT))/2.0d)), 15.0D);
    }
}
