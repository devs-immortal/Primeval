package net.cr24.primeval.block.plant;

import net.cr24.primeval.block.PrimevalBlockTags;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.function.Function;

public class PrimevalPlantBlock extends PlantBlock {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    public static final BooleanProperty CENTERED;
    public static Function<BlockState, OffsetType> CENTER_CHECK = (s) -> s.getBlock() instanceof PrimevalPlantBlock && s.get(PrimevalPlantBlock.CENTERED) ? AbstractBlock.OffsetType.NONE : AbstractBlock.OffsetType.XZ;

    public PrimevalPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(CENTERED, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        return this.getDefaultState().with(CENTERED, world.getBlockState(pos.down()).isIn(PrimevalBlockTags.SPECIAL_PLANTABLE));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(PrimevalBlockTags.HEAVY_SOIL) || floor.isIn(PrimevalBlockTags.MEDIUM_SOIL) || floor.isIn(PrimevalBlockTags.SPECIAL_PLANTABLE);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CENTERED);
    }

    @Override
    protected float getMaxHorizontalModelOffset() {
        Offsetter offsetter = this.offsetter;
        return offsetter != null ? offsetter.evaluate(this.asBlockState(), pos) : Vec3d.ZERO;
    }

    static {
        CENTERED = BooleanProperty.of("centered");
    }
}
