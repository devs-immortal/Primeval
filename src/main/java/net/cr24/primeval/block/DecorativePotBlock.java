package net.cr24.primeval.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class DecorativePotBlock extends Block {

    protected static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 2.0, 14.0),
            Block.createCuboidShape(1.0, 2.0, 1.0, 15.0, 11.0, 15.0),
            Block.createCuboidShape(3.0, 11.0, 3.0, 13.0, 13.0, 13.0),
            Block.createCuboidShape(2.0, 13.0, 2.0, 14.0, 16.0, 14.0)
    );

    public DecorativePotBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
