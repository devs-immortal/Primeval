package net.cr24.primeval.block;

import net.minecraft.block.*;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class PrimevalFarmlandBlock extends SemiSupportedBlock {

    public static final IntProperty MOISTURE;
    public static final IntProperty FERTILIZED;
    public static final EnumProperty<PrimevalFarmlandBlockFertilizerType> TYPE;
    public final Block turnsTo;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    public PrimevalFarmlandBlock(float percentPerSide, Block resultBlock, Block[] sourceBlocks, Settings settings) {
        this(percentPerSide, resultBlock, resultBlock, sourceBlocks, settings);
    }

    public PrimevalFarmlandBlock(float percentPerSide, Block fallBlock, Block turnsTo, Block[] sourceBlocks, Settings settings) {
        super(percentPerSide, fallBlock, settings);
        this.turnsTo = turnsTo;
        for (Block b : sourceBlocks) {
            //PrimevalHoeItem.hoeables.put(b, this); //TODO
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == Direction.UP) {
            if (!(neighborState.getBlock() instanceof FenceGateBlock) && neighborState.isSolid()) {
                return turnsTo.getDefaultState();
            }
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        BlockPos[] adjacent = new BlockPos[] {
                pos.north(),
                pos.east(),
                pos.south(),
                pos.west()
        };
        int highest = 0;
        for (BlockPos b : adjacent) {
            if (world.getFluidState(b).isIn(FluidTags.WATER)) {
                highest = 4;
            } else {
                BlockState dest = world.getBlockState(b);
                if (dest.getBlock() instanceof PrimevalFarmlandBlock && highest < dest.get(MOISTURE)) {
                    highest = dest.get(PrimevalFarmlandBlock.MOISTURE) - 1;
                }
            }
        }
        world.setBlockState(pos, state.with(MOISTURE, highest));
        if (!isWaterInRange(world, pos) && state.get(MOISTURE) == 0 && highest == 0) {
            world.setBlockState(pos, turnsTo.getDefaultState());
        }
    }

    private boolean isWaterInRange(World world, BlockPos pos) {
        int maxRange = 4;

        int step = -maxRange;

        boolean found = false;
        while (!found && step <= maxRange) {
            int searchSize = maxRange - Math.abs(step);
            for (int y = -searchSize; y <= searchSize; y++) {
                if (world.getFluidState(pos.north(step).east(y)).isIn(FluidTags.WATER)) found = true;
            }
            step++;
        }
        return found;
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
        builder.add(MOISTURE, FERTILIZED, TYPE);
    }

    static {
        MOISTURE = IntProperty.of("moisture", 0, 4);
        FERTILIZED = IntProperty.of("fertilized", 0, 15);
        TYPE = EnumProperty.of("fertilizer_type", PrimevalFarmlandBlockFertilizerType.class);
    }

    public enum PrimevalFarmlandBlockFertilizerType implements StringIdentifiable {
        NONE("none"),
        BONEMEAL("bonemeal");

        private final String name;

        private PrimevalFarmlandBlockFertilizerType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

}
