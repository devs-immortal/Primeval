package net.cr24.primeval.block.plant;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class ReedsBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED;
    public static final IntegerProperty AGE;
    public static final BooleanProperty CAP;

    public ReedsBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
        this.registerDefaultState(this.defaultBlockState().setValue(CAP, true));
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockState upBlock = world.getBlockState(pos.above());
        if (upBlock.isAir() || upBlock.is(Blocks.WATER)) {
            world.setBlockAndUpdate(pos.above(), getStateFor(world, pos.above()));
        }
    }

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 4;
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();

        return getStateFor(world, pos);
    }

    private BlockState getStateFor(LevelReader world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        BlockState downBlock = world.getBlockState(pos.below());
        BlockState upBlock = world.getBlockState(pos.above());
        boolean capState = !upBlock.is(PrimevalBlocks.REEDS);
        if (fluidState.getType() == Fluids.WATER) {
            return this.defaultBlockState().setValue(WATERLOGGED, true).setValue(CAP, capState);
        } else {
            if (downBlock.is(PrimevalBlocks.REEDS)) { // planting on another reed
                int downAge = downBlock.getValue(AGE);
                return this.defaultBlockState().setValue(AGE, Math.min(downAge+1, 4)).setValue(CAP, capState);
            } else { // planted on soil
                return this.defaultBlockState().setValue(AGE, 1).setValue(CAP, capState);
            }
        }
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState down = world.getBlockState(pos.below());
        return down.is(PrimevalTags.Blocks.HEAVY_SOIL) || down.is(PrimevalTags.Blocks.MEDIUM_SOIL) || down.is(PrimevalBlocks.REEDS);
    }

    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        if (canSurvive(state, world, pos)) {
            return getStateFor(world, pos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED, AGE, CAP);
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        AGE = IntegerProperty.create("age", 0, 4);
        CAP = BooleanProperty.create("cap");
    }
}
