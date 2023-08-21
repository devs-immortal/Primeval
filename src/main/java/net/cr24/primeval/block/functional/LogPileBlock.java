package net.cr24.primeval.block.functional;

import net.cr24.primeval.block.LayeredBlock;
import net.cr24.primeval.block.entity.LayingItemBlockEntity;
import net.cr24.primeval.block.entity.LogPileBlockEntity;
import net.cr24.primeval.block.entity.PrimevalCampfireBlockEntity;
import net.cr24.primeval.item.tool.PrimevalShovelItem;
import net.cr24.primeval.recipe.OpenFireRecipe;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.SimpleInventory;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class LogPileBlock extends BlockWithEntity implements Waterloggable {

    public static final BooleanProperty WATERLOGGED;
    public static final IntProperty AMOUNT;

    public LogPileBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(AMOUNT, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) return ActionResult.PASS;
        ItemStack itemStack = player.getStackInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (hand == Hand.MAIN_HAND && blockEntity instanceof LogPileBlockEntity) {
            ItemStack logItem = ((LogPileBlockEntity) blockEntity).getItem();
            int amount = state.get(AMOUNT);
            if (itemStack.isEmpty() && !world.isClient) {
                player.giveItemStack(logItem);
                if (amount > 0) {
                    world.setBlockState(pos, state.with(AMOUNT, amount-1));
                } else {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
                world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 0.3f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED, AMOUNT);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        AMOUNT = IntProperty.of("amount", 0, 7);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LogPileBlockEntity(pos, state);
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
