package net.cr24.primeval.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.function.Supplier;

public class LogPileBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED;
    public static final IntegerProperty AMOUNT;
    private final Supplier<Item> logItem;

    public LogPileBlock(Supplier<Item> logItem, Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(AMOUNT, 0));
        this.logItem = logItem;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int amount = state.getValue(AMOUNT);
        if (amount < 7 && itemStack.is(logItem.get())) {
            world.setBlockAndUpdate(pos, state.setValue(LogPileBlock.AMOUNT, state.getValue(LogPileBlock.AMOUNT)+1));
            if (!player.hasInfiniteMaterials()) itemStack.shrink(1);
            world.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.5f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            return InteractionResult.SUCCESS;
        } else if (itemStack.isEmpty()) {
            player.addItem(new ItemStack(logItem.get()));
            if (amount > 0) {
                world.setBlockAndUpdate(pos, state.setValue(AMOUNT, amount-1));
            } else {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
            world.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 0.3f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return state;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED, AMOUNT);
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        AMOUNT = IntegerProperty.create("amount", 0, 7);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, (4.0D * Math.ceil((1+state.getValue(AMOUNT))/2.0d)), 15.0D);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 15.0D, (4.0D * Math.ceil((1+state.getValue(AMOUNT))/2.0d)), 15.0D);
    }
}
