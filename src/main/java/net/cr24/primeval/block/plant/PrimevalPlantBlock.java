package net.cr24.primeval.block.plant;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.function.Function;

public class PrimevalPlantBlock extends VegetationBlock {

    public static final MapCodec<PrimevalPlantBlock> CODEC = simpleCodec(PrimevalPlantBlock::new);

    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    public static final BooleanProperty CENTERED;
    public static Function<BlockState, OffsetType> CENTER_CHECK = (s) -> s.getBlock() instanceof PrimevalPlantBlock && s.getValue(PrimevalPlantBlock.CENTERED) ? BlockBehaviour.OffsetType.NONE : BlockBehaviour.OffsetType.XZ;

    public PrimevalPlantBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(CENTERED, false));
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();

        return this.defaultBlockState().setValue(CENTERED, world.getBlockState(pos.below()).is(PrimevalTags.Blocks.SPECIAL_PLANTABLE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(PrimevalTags.Blocks.HEAVY_SOIL) || floor.is(PrimevalTags.Blocks.MEDIUM_SOIL) || floor.is(PrimevalTags.Blocks.SPECIAL_PLANTABLE);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CENTERED);
    }

    static {
        CENTERED = BooleanProperty.create("centered");
    }
}
