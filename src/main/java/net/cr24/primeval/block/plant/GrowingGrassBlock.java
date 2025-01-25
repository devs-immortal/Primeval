package net.cr24.primeval.block.plant;

import net.cr24.primeval.block.PrimevalBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class GrowingGrassBlock extends Block {
    public static final IntProperty GROWTH_STATE;
    public static final BooleanProperty GROWING;
    protected static final VoxelShape[] SHAPES;

    public GrowingGrassBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(GROWTH_STATE, 0).with(GROWING, true));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{GROWTH_STATE, GROWING});
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int shapeState = state.get(GROWTH_STATE);
        return SHAPES[shapeState];
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(GROWING) && state.get(GROWTH_STATE) < 4;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        int growth = state.get(GROWTH_STATE);
        for (BlockPos dest : new BlockPos[]{pos.north(), pos.east(), pos.south(), pos.west()}) {
            BlockState destBlockState = world.getBlockState(dest);
            if (destBlockState.getBlock() instanceof GrowingGrassBlock && destBlockState.get(GROWTH_STATE) == growth+1) {
                return;
            }
        }
        if (growth < 4 && canGrow(growth+1, world.getBlockState(pos.down()))) {
            BlockState next = state.with(GROWTH_STATE, growth+1);
            if (random.nextInt(4-growth) <= 0) {
                next = next.with(GROWING, false);
            }
            world.setBlockState(pos, next);
        }
    }

    protected boolean canGrow(int growthState, BlockState floorBlock) {
        switch (growthState) {
            case 0:
                return floorBlock.isIn(PrimevalBlockTags.LIGHT_SOIL) || floorBlock.isIn(PrimevalBlockTags.MEDIUM_SOIL) || floorBlock.isIn(PrimevalBlockTags.HEAVY_SOIL);
            case 1: case 2:
                return floorBlock.isIn(PrimevalBlockTags.MEDIUM_SOIL) || floorBlock.isIn(PrimevalBlockTags.HEAVY_SOIL);
            default:
                return floorBlock.isIn(PrimevalBlockTags.HEAVY_SOIL);
        }
    }

    protected boolean canPlantOnTop(BlockState state, BlockState floorBlock) {
        return canGrow(state.get(GROWTH_STATE), floorBlock);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlantOnTop(state, world.getBlockState(blockPos));
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    public boolean canPathfindThrough(BlockState state, NavigationType type) {
        return type == NavigationType.AIR && !this.collidable || super.canPathfindThrough(state, type);
    }

    static {
        GROWTH_STATE = IntProperty.of("growth", 0, 4);
        GROWING = BooleanProperty.of("active");
        SHAPES = new VoxelShape[] {
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)

        };
    }
}
