package net.cr24.primeval.block.plant;

import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GrowingGrassBlock extends Block {
    public static final IntegerProperty GROWTH_STATE;
    public static final BooleanProperty GROWING;
    protected static final VoxelShape[] SHAPES;

    public GrowingGrassBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(GROWTH_STATE, 0).setValue(GROWING, true));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(GROWTH_STATE, GROWING);
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        int shapeState = state.getValue(GROWTH_STATE);
        return SHAPES[shapeState];
    }

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(GROWING) && state.getValue(GROWTH_STATE) < 4;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        int growth = state.getValue(GROWTH_STATE);
        for (BlockPos dest : new BlockPos[]{pos.north(), pos.east(), pos.south(), pos.west()}) {
            BlockState destBlockState = world.getBlockState(dest);
            if (destBlockState.getBlock() instanceof GrowingGrassBlock && destBlockState.getValue(GROWTH_STATE) == growth+1) {
                return;
            }
        }
        if (growth < 4 && canGrow(growth+1, world.getBlockState(pos.below()))) {
            BlockState next = state.setValue(GROWTH_STATE, growth+1);
            if (random.nextInt(4-growth) <= 0) {
                next = next.setValue(GROWING, false);
            }
            world.setBlockAndUpdate(pos, next);
        }
    }

    protected boolean canGrow(int growthState, BlockState floorBlock) {
        switch (growthState) {
            case 0:
                return floorBlock.is(PrimevalTags.Blocks.LIGHT_SOIL) || floorBlock.is(PrimevalTags.Blocks.MEDIUM_SOIL) || floorBlock.is(PrimevalTags.Blocks.HEAVY_SOIL);
            case 1: case 2:
                return floorBlock.is(PrimevalTags.Blocks.MEDIUM_SOIL) || floorBlock.is(PrimevalTags.Blocks.HEAVY_SOIL);
            default:
                return floorBlock.is(PrimevalTags.Blocks.HEAVY_SOIL);
        }
    }

    protected boolean canPlantOnTop(BlockState state, BlockState floorBlock) {
        return canGrow(state.getValue(GROWTH_STATE), floorBlock);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        return this.canPlantOnTop(state, world.getBlockState(blockPos));
    }

    public boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return type == PathComputationType.AIR && !this.hasCollision || super.isPathfindable(state, type);
    }

    static {
        GROWTH_STATE = IntegerProperty.create("growth", 0, 4);
        GROWING = BooleanProperty.create("active");
        SHAPES = new VoxelShape[] {
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)

        };
    }
}
