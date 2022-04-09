package net.cr24.primeval.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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

    public static final HashMap<Direction, BooleanProperty> DIRECTION_MAP;

    public TrunkBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(NORTH, false));
        this.setDefaultState(this.getDefaultState().with(EAST, false));
        this.setDefaultState(this.getDefaultState().with(SOUTH, false));
        this.setDefaultState(this.getDefaultState().with(WEST, false));
        this.setDefaultState(this.getDefaultState().with(UP, false));
        this.setDefaultState(this.getDefaultState().with(DOWN, false));
        this.setDefaultState(this.getDefaultState().with(SIZE, 3));
        this.setDefaultState(this.getDefaultState().with(GROWN, true));
        this.setDefaultState(this.getDefaultState().with(AGE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{NORTH, EAST, SOUTH, WEST, UP, DOWN, SIZE, GROWN, AGE});
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState state = this.getDefaultState();
        if (world.getBlockState(blockPos.down()).isIn(PrimevalBlockTags.HEAVY_SOIL) || world.getBlockState(blockPos.down()).isIn(PrimevalBlockTags.MEDIUM_SOIL)) {
            state = state.with(DOWN, true);
        }
        BlockState directionState;
        for (Direction d : DIRECTION_MAP.keySet()) {
            directionState = world.getBlockState(blockPos.offset(d));
            if (directionState.getBlock() instanceof TrunkBlock) {
                state = state.with(DIRECTION_MAP.get(d), true);
                world.setBlockState(blockPos.offset(d), directionState.with(DIRECTION_MAP.get(d.getOpposite()), true));
            }
        }
        return state;
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
        AGE = IntProperty.of("age", 0, 16);

        DIRECTION_MAP = new HashMap<>();
        DIRECTION_MAP.put(Direction.NORTH, NORTH);
        DIRECTION_MAP.put(Direction.EAST, EAST);
        DIRECTION_MAP.put(Direction.SOUTH, SOUTH);
        DIRECTION_MAP.put(Direction.WEST, WEST);
        DIRECTION_MAP.put(Direction.UP, UP);
        DIRECTION_MAP.put(Direction.DOWN, DOWN);
    }
}
