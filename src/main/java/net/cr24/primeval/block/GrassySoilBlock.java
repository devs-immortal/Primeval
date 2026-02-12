package net.cr24.primeval.block;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.lighting.LightEngine;
import java.util.HashMap;

public class GrassySoilBlock extends SemiSupportedBlock {
    public static final BooleanProperty SNOWY;

    // Set of source -> grassy version
    public static HashMap<Block, Block> grassSpreadable = new HashMap<>();

    public GrassySoilBlock(float percentPerSide, Block fallBlock, Block[] sourceBlocks, Properties settings) {
        super(percentPerSide, fallBlock, settings);
        for (Block b : sourceBlocks) {
            grassSpreadable.put(b, this);
        }
    }

    private static boolean grassCanSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(Blocks.SNOW) && blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(state, blockState, Direction.UP, blockState.getLightBlock());
            return i < 15;
        }
    }

    private static boolean canSpread(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return grassCanSurvive(state, world, pos) && !world.getFluidState(blockPos).is(FluidTags.WATER);
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!grassCanSurvive(state, world, pos)) {
            world.setBlockAndUpdate(pos, this.fallBlock.defaultBlockState());
        } else {
            if (world.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockState = this.defaultBlockState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    Block block = world.getBlockState(blockPos).getBlock();
                    if (grassSpreadable.containsKey(block) && canSpread(blockState, world, blockPos)) {
                        world.setBlockAndUpdate(blockPos, grassSpreadable.get(block).defaultBlockState().setValue(SNOWY, world.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                        if (random.nextInt(3) == 0 && world.getBlockState(pos.above()).isAir()) {
                            world.setBlockAndUpdate(pos.above(), PrimevalBlocks.GRASS.defaultBlockState());
                        }
                    }
                }
            }

        }
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return direction == Direction.UP ? state.setValue(SNOWY, isSnow(neighborState)) : super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos().above());
        return this.defaultBlockState().setValue(SNOWY, isSnow(blockState));
    }

    private static boolean isSnow(BlockState state) {
        return state.is(BlockTags.SNOW);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SNOWY);
    }

    static {
        SNOWY = BlockStateProperties.SNOWY;
    }
}
