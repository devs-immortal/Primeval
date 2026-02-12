package net.cr24.primeval.block;

import net.cr24.primeval.item.tool.PrimevalHoeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PrimevalFarmlandBlock extends SemiSupportedBlock {

    public static final IntegerProperty MOISTURE;
    public static final IntegerProperty FERTILIZED;
    public static final EnumProperty<PrimevalFarmlandBlockFertilizerType> TYPE;
    public final Block turnsTo;
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    public PrimevalFarmlandBlock(float percentPerSide, Block resultBlock, Block[] sourceBlocks, Properties settings) {
        this(percentPerSide, resultBlock, resultBlock, sourceBlocks, settings);
    }

    public PrimevalFarmlandBlock(float percentPerSide, Block fallBlock, Block turnsTo, Block[] sourceBlocks, Properties settings) {
        super(percentPerSide, fallBlock, settings);
        this.turnsTo = turnsTo;
        for (Block b : sourceBlocks) {
            PrimevalHoeItem.hoeables.put(b, this);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (direction == Direction.UP) {
            if (!(neighborState.getBlock() instanceof FenceGateBlock) && neighborState.isSolid()) {
                return turnsTo.defaultBlockState();
            }
        }
        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        BlockPos[] adjacent = new BlockPos[] {
                pos.north(),
                pos.east(),
                pos.south(),
                pos.west()
        };
        int highest = 0;
        for (BlockPos b : adjacent) {
            if (world.getFluidState(b).is(FluidTags.WATER)) {
                highest = 4;
            } else {
                BlockState dest = world.getBlockState(b);
                if (dest.getBlock() instanceof PrimevalFarmlandBlock && highest < dest.getValue(MOISTURE)) {
                    highest = dest.getValue(PrimevalFarmlandBlock.MOISTURE) - 1;
                }
            }
        }
        world.setBlockAndUpdate(pos, state.setValue(MOISTURE, highest));
        if (!isWaterInRange(world, pos) && state.getValue(MOISTURE) == 0 && highest == 0) {
            world.setBlockAndUpdate(pos, turnsTo.defaultBlockState());
        }
    }

    private boolean isWaterInRange(Level world, BlockPos pos) {
        int maxRange = 4;

        int step = -maxRange;

        boolean found = false;
        while (!found && step <= maxRange) {
            int searchSize = maxRange - Math.abs(step);
            for (int y = -searchSize; y <= searchSize; y++) {
                if (world.getFluidState(pos.north(step).east(y)).is(FluidTags.WATER)) found = true;
            }
            step++;
        }
        return found;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE, FERTILIZED, TYPE);
    }

    static {
        MOISTURE = IntegerProperty.create("moisture", 0, 4);
        FERTILIZED = IntegerProperty.create("fertilized", 0, 15);
        TYPE = EnumProperty.create("fertilizer_type", PrimevalFarmlandBlockFertilizerType.class);
    }

    public enum PrimevalFarmlandBlockFertilizerType implements StringRepresentable {
        NONE("none"),
        BONEMEAL("bonemeal");

        private final String name;

        PrimevalFarmlandBlockFertilizerType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

}
