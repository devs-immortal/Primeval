package net.cr24.primeval.block.plant;

import net.cr24.primeval.block.PrimevalBlockTags;
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ReedsBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final IntProperty AGE;
    public static final BooleanProperty CAP;

    public ReedsBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
        this.setDefaultState(this.getDefaultState().with(AGE, 0));
        this.setDefaultState(this.getDefaultState().with(CAP, true));
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState upBlock = world.getBlockState(pos.up());
        if (upBlock.isAir() || upBlock.isOf(Blocks.WATER)) {
            world.setBlockState(pos.up(), getStateFor(world, pos.up()));
        }
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 4;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        return getStateFor(world, pos);
    }

    private BlockState getStateFor(WorldAccess world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        BlockState downBlock = world.getBlockState(pos.down());
        BlockState upBlock = world.getBlockState(pos.up());
        boolean capState = !upBlock.isOf(PrimevalBlocks.REEDS);
        if (fluidState.getFluid() == Fluids.WATER) {
            return this.getDefaultState().with(WATERLOGGED, true).with(CAP, capState);
        } else {
            if (downBlock.isOf(PrimevalBlocks.REEDS)) { // planting on another reed
                int downAge = downBlock.get(AGE);
                return this.getDefaultState().with(AGE, Math.min(downAge+1, 4)).with(CAP, capState);
            } else { // planted on soil
                return this.getDefaultState().with(AGE, 1).with(CAP, capState);
            }
        }
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState down = world.getBlockState(pos.down());
        return down.isIn(PrimevalBlockTags.HEAVY_SOIL) || down.isIn(PrimevalBlockTags.MEDIUM_SOIL) || down.isOf(PrimevalBlocks.REEDS);
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (canPlaceAt(state, world, pos)) {
            return getStateFor(world, pos);
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED, AGE, CAP);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        AGE = IntProperty.of("age", 0, 4);
        CAP = BooleanProperty.of("cap");
    }
}
