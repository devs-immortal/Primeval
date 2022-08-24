package net.cr24.primeval.block;

import net.cr24.primeval.tag.PrimevalBlockTags;
import net.cr24.primeval.world.gen.trunker.AbstractTrunker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
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
    public static final IntProperty SIZE;
    public static final BooleanProperty GROWN;
    public static final IntProperty AGE;

    public final AbstractTrunker trunker;
    public static final HashMap<Direction, BooleanProperty> DIRECTION_MAP;
    public static final Direction[] XZ_DIRECTIONS;

    public TrunkBlock(Settings settings, AbstractTrunker trunker) {
        super(settings);
        this.trunker = trunker;
        this.setDefaultState(this.getDefaultState().with(NORTH, false));
        this.setDefaultState(this.getDefaultState().with(EAST, false));
        this.setDefaultState(this.getDefaultState().with(SOUTH, false));
        this.setDefaultState(this.getDefaultState().with(WEST, false));
        this.setDefaultState(this.getDefaultState().with(UP, false));
        this.setDefaultState(this.getDefaultState().with(DOWN, false));
        this.setDefaultState(this.getDefaultState().with(SIZE, 3));
        this.setDefaultState(this.getDefaultState().with(GROWN, false));
        this.setDefaultState(this.getDefaultState().with(AGE, 0));
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState floor = world.getBlockState(pos.down());
        if (state.get(AGE) == 0 && floor.isIn(PrimevalBlockTags.HEAVY_SOIL) || floor.isIn(PrimevalBlockTags.MEDIUM_SOIL)) return;
        for (Direction d : DIRECTION_MAP.keySet()) {
            if (
                    state.get(DIRECTION_MAP.get(d)) &&
                            world.getBlockState(pos.offset(d)).getBlock() instanceof TrunkBlock &&
                            world.getBlockState(pos.offset(d)).get(AGE) < state.get(AGE)
            ) return;
        }
        world.breakBlock(pos, true);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        world.createAndScheduleBlockTick(pos, this, 2);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ArrayList<Direction> dirs = new ArrayList<>();
        for (Direction d : DIRECTION_MAP.keySet()) {
            if (state.get(DIRECTION_MAP.get(d)) && world.getBlockState(pos.offset(d, 1)).getBlock() instanceof LeafBlock) {
                dirs.add(d);
            }
        }
        trunker.tickTrunk(state, world, pos, random, dirs.toArray(new Direction[dirs.size()]));
    }

    public boolean hasRandomTicks(BlockState state) {
        return !state.get(GROWN);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{NORTH, EAST, SOUTH, WEST, UP, DOWN, SIZE, GROWN, AGE});
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(SIZE, 0);
    }

    static {
        NORTH = BooleanProperty.of("north");
        EAST = BooleanProperty.of("east");
        SOUTH = BooleanProperty.of("south");
        WEST = BooleanProperty.of("west");
        UP = BooleanProperty.of("up");
        DOWN = BooleanProperty.of("down");
        SIZE = IntProperty.of("size", 0, 3);
        GROWN = BooleanProperty.of("grown");
        AGE = IntProperty.of("age", 0, 24);

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
