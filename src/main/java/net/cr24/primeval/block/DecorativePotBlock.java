package net.cr24.primeval.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DecorativePotBlock extends Block {

    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(2.0, 0.0, 2.0, 14.0, 2.0, 14.0),
            Block.box(1.0, 2.0, 1.0, 15.0, 11.0, 15.0),
            Block.box(3.0, 11.0, 3.0, 13.0, 13.0, 13.0),
            Block.box(2.0, 13.0, 2.0, 14.0, 16.0, 14.0)
    );

    public DecorativePotBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
