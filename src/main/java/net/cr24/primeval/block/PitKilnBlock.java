package net.cr24.primeval.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class PitKilnBlock extends BlockWithEntity {

    public static final IntProperty BUILD_STEP = IntProperty.of("build_step", 0, 8);
    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    protected static final VoxelShape[] SHAPES;

    protected PitKilnBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(BUILD_STEP, 0).with(LIT, false));
    }


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int shapeState = state.get(BUILD_STEP);
        if (shapeState < 5) {
            return LayeredBlock.LAYERS_TO_SHAPE[(Integer)state.get(BUILD_STEP)+1];
        } else {
            return SHAPES[shapeState-5];
        }
    }


    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(BUILD_STEP) < 5) {
            return LayeredBlock.LAYERS_TO_SHAPE[state.get(BUILD_STEP)];
        } else if (state.get(BUILD_STEP) < 7) {
            return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
        } else {
            return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        }
    }


    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    public boolean hasSidedTransparency(BlockState state) {
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BUILD_STEP, LIT);
    }

    static {
        SHAPES = new VoxelShape[] {
                VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 10.0D, 1.0D, 16.0D, 13.0D, 5.0D)),
                VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 10.0D, 1.0D, 16.0D, 13.0D, 5.0D), Block.createCuboidShape(0.0D, 10.0D, 11.0D, 16.0D, 13.0D, 15.0D)),
                VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 10.0D, 1.0D, 16.0D, 13.0D, 5.0D), Block.createCuboidShape(0.0D, 10.0D, 11.0D, 16.0D, 13.0D, 15.0D), Block.createCuboidShape(1.0D, 12.0D, 0.0D, 5.0D, 16.0D, 16.0D)),
                VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 10.0D, 1.0D, 16.0D, 13.0D, 5.0D), Block.createCuboidShape(0.0D, 10.0D, 11.0D, 16.0D, 13.0D, 15.0D), Block.createCuboidShape(1.0D, 12.0D, 0.0D, 5.0D, 16.0D, 16.0D), Block.createCuboidShape(11.0D, 12.0D, 0.0D, 15.0D, 16.0D, 16.0D))
        };
    }
}
