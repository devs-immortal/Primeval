package net.cr24.primeval.block.plant;

import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.world.trunker.AbstractTrunker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class TrunkBlock extends Block {
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;
    public static final BooleanProperty UP;
    public static final BooleanProperty DOWN;
    public static final IntegerProperty SIZE;
    public static final BooleanProperty GROWN;
    public static final IntegerProperty AGE;

    public final AbstractTrunker trunker;
    public static final HashMap<Direction, BooleanProperty> DIRECTION_MAP;
    public static final Direction[] XZ_DIRECTIONS;

    public TrunkBlock(AbstractTrunker trunker, Properties settings) {
        super(settings);
        this.trunker = trunker;
        this.registerDefaultState(this.defaultBlockState().setValue(NORTH, false));
        this.registerDefaultState(this.defaultBlockState().setValue(EAST, false));
        this.registerDefaultState(this.defaultBlockState().setValue(SOUTH, false));
        this.registerDefaultState(this.defaultBlockState().setValue(WEST, false));
        this.registerDefaultState(this.defaultBlockState().setValue(UP, false));
        this.registerDefaultState(this.defaultBlockState().setValue(DOWN, false));
        this.registerDefaultState(this.defaultBlockState().setValue(SIZE, 3));
        this.registerDefaultState(this.defaultBlockState().setValue(GROWN, false));
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockState floor = world.getBlockState(pos.below());
        if (state.getValue(AGE) == 0 && floor.is(PrimevalTags.Blocks.HEAVY_SOIL) || floor.is(PrimevalTags.Blocks.MEDIUM_SOIL)) return;
        for (Direction d : DIRECTION_MAP.keySet()) {
            if (
                    state.getValue(DIRECTION_MAP.get(d)) &&
                            world.getBlockState(pos.relative(d)).getBlock() instanceof TrunkBlock &&
                            world.getBlockState(pos.relative(d)).getValue(AGE) < state.getValue(AGE)
            ) return;
        }
        world.destroyBlock(pos, true);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        world.scheduleTick(pos, this, 2);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        ArrayList<Direction> dirs = new ArrayList<>();
        for (Direction d : DIRECTION_MAP.keySet()) {
            if (state.getValue(DIRECTION_MAP.get(d)) && world.getBlockState(pos.relative(d, 1)).getBlock() instanceof LeafBlock) {
                dirs.add(d);
            }
        }
        trunker.tickTrunk(state, world, pos, random, dirs.toArray(new Direction[dirs.size()]));
    }

    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(GROWN);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, SIZE, GROWN, AGE);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(SIZE, 0);
    }

    static {
        NORTH = BooleanProperty.create("north");
        EAST = BooleanProperty.create("east");
        SOUTH = BooleanProperty.create("south");
        WEST = BooleanProperty.create("west");
        UP = BooleanProperty.create("up");
        DOWN = BooleanProperty.create("down");
        SIZE = IntegerProperty.create("size", 0, 3);
        GROWN = BooleanProperty.create("grown");
        AGE = IntegerProperty.create("age", 0, 24);

        DIRECTION_MAP = new HashMap<>();
        DIRECTION_MAP.put(Direction.NORTH, NORTH);
        DIRECTION_MAP.put(Direction.EAST, EAST);
        DIRECTION_MAP.put(Direction.SOUTH, SOUTH);
        DIRECTION_MAP.put(Direction.WEST, WEST);
        DIRECTION_MAP.put(Direction.UP, UP);
        DIRECTION_MAP.put(Direction.DOWN, DOWN);

        XZ_DIRECTIONS = new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    }
}
