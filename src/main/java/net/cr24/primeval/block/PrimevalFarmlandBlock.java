package net.cr24.primeval.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class PrimevalFarmlandBlock extends SemiSupportedBlock {

    public static final IntProperty MOISTURE = Properties.MOISTURE;
    public static final IntProperty FERTILIZED;
    public final Block turnsTo;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    public PrimevalFarmlandBlock(Settings settings, float percentPerSide, Block resultBlock) {
        this(settings, percentPerSide, resultBlock, resultBlock);
    }

    public PrimevalFarmlandBlock(Settings settings, float percentPerSide, Block fallBlock, Block turnsTo) {
        super(settings, percentPerSide, fallBlock);
        this.turnsTo = turnsTo;
    }




    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE, FERTILIZED);
    }

    static {
        FERTILIZED = IntProperty.of("fertilized", 0, 15);
    }

}
