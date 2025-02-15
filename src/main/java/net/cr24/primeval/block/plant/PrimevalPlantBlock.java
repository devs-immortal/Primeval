package net.cr24.primeval.block.plant;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Function;

public class PrimevalPlantBlock extends PlantBlock {

    public static final MapCodec<PrimevalPlantBlock> CODEC = createCodec(PrimevalPlantBlock::new);

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    public static final BooleanProperty CENTERED;
    public static Function<BlockState, OffsetType> CENTER_CHECK = (s) -> s.getBlock() instanceof PrimevalPlantBlock && s.get(PrimevalPlantBlock.CENTERED) ? AbstractBlock.OffsetType.NONE : AbstractBlock.OffsetType.XZ;

    public PrimevalPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(CENTERED, false));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        return this.getDefaultState().with(CENTERED, world.getBlockState(pos.down()).isIn(PrimevalTags.Blocks.SPECIAL_PLANTABLE));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(PrimevalTags.Blocks.HEAVY_SOIL) || floor.isIn(PrimevalTags.Blocks.MEDIUM_SOIL) || floor.isIn(PrimevalTags.Blocks.SPECIAL_PLANTABLE);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CENTERED);
    }

    static {
        CENTERED = BooleanProperty.of("centered");
    }
}
