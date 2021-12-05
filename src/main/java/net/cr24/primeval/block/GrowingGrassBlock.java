package net.cr24.primeval.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class GrowingGrassBlock extends Block {
    public static final IntProperty GROWTH_STATE;
    protected static final VoxelShape[] SHAPES;

    protected GrowingGrassBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(GROWTH_STATE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{GROWTH_STATE});
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int shapeState = state.get(GROWTH_STATE);
        return SHAPES[shapeState];
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(GROWTH_STATE) < 4;
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
            world.setBlockState(pos, state.with(GROWTH_STATE, growth+1));
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

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlantOnTop(state, world.getBlockState(blockPos));
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return type == NavigationType.AIR && !this.collidable ? true : super.canPathfindThrough(state, world, pos, type);
    }

    static {
        GROWTH_STATE = IntProperty.of("growth", 0, 4);

        SHAPES = new VoxelShape[] {
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)

        };
    }
}
