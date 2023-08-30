package net.cr24.primeval.block.functional;

import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class TimedTorchBlock extends Block {
    static final int TICKS = 6000;

    public static final IntProperty BURNOUT_STAGE;
    public static final DirectionProperty DIRECTION;
    protected static final VoxelShape[] SHAPES;
    private static final Direction[] SIDES = new Direction[] {Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public TimedTorchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(BURNOUT_STAGE, 0).with(DIRECTION, Direction.DOWN));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BURNOUT_STAGE, DIRECTION);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction shapeState = state.get(DIRECTION);
        switch (shapeState) {
            case DOWN: return SHAPES[0];
            case NORTH: return SHAPES[1];
            case EAST: return SHAPES[2];
            case SOUTH: return SHAPES[3];
            case WEST: return SHAPES[4];
        }
        return null;
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        int stage = state.get(BURNOUT_STAGE);
        if (stage == 1) {
            world.createAndScheduleBlockTick(pos, this, TICKS*stage);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int stage = state.get(BURNOUT_STAGE);
        world.setBlockState(pos, state.with(BURNOUT_STAGE, Math.min(stage+1, 5)));
        if (stage < 4) {
            world.createAndScheduleBlockTick(pos, PrimevalBlocks.CRUDE_TORCH, TICKS*stage);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        int burnState = 0;
        if (ctx.getStack().getItem() == PrimevalBlocks.LIT_CRUDE_TORCH) {
            burnState = 1;
        }
        Direction[] arranged = new Direction[SIDES.length+1];
        arranged[0] = ctx.getSide();
        for (int i = 1; i <= SIDES.length; i++) {
            arranged[i] = SIDES[i-1];
        }
        for (Direction direction1 : arranged) {
            if (direction1 == Direction.UP && world.getBlockState(blockPos.down()).isSideSolid(world, blockPos.down(), Direction.UP, SideShapeType.CENTER)) {
                return this.getDefaultState().with(DIRECTION, Direction.DOWN).with(BURNOUT_STAGE, burnState);
            } else if (direction1 != Direction.DOWN) {
                BlockPos placedOn = blockPos.offset(direction1.getOpposite());
                if (world.getBlockState(placedOn).isSideSolidFullSquare(world, placedOn, direction1)) {
                    return this.getDefaultState().with(DIRECTION, direction1.getOpposite()).with(BURNOUT_STAGE, burnState);
                }
            }
        }
        return null;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        Direction torchDirection = state.get(DIRECTION);
        BlockPos placedOn = pos.offset(torchDirection);
        if (torchDirection == Direction.DOWN && !world.getBlockState(placedOn).isSideSolid(world, placedOn, Direction.UP, SideShapeType.CENTER)) {
            return Blocks.AIR.getDefaultState();
        } else if (!world.getBlockState(placedOn).isSideSolidFullSquare(world, placedOn, torchDirection.getOpposite())) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int burnout = state.get(BURNOUT_STAGE);
        if (burnout != 0 && burnout != 5) {
            Direction direction = state.get(DIRECTION);
            double d = (double)pos.getX() + 0.5;
            double e = (double)pos.getY() + 0.85;
            double f = (double)pos.getZ() + 0.5;
            world.addParticle(ParticleTypes.SMOKE, d + direction.getOffsetX() * 0.27, e + direction.getOffsetY() * 0.125, f + direction.getOffsetZ() * 0.27, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.FLAME, d + direction.getOffsetX() * 0.27, e + direction.getOffsetY() * 0.125, f + direction.getOffsetZ() * 0.27, 0.0, 0.0, 0.0);
        }
    }

    public static int getLuminanceFromState(BlockState state) {
        int burnout = state.get(BURNOUT_STAGE);
        switch (burnout) {
            case 0: return 0;
            case 1: return 13;
            case 2: return 12;
            case 3: return 10;
            case 4: return 8;
            case 5: return 1;
        }
        return 0;
    }

    static {
        BURNOUT_STAGE = IntProperty.of("burnout_stage", 0, 5); // 0 = unlit    1,2,3,4 = burning    5 = extinguished
        DIRECTION = DirectionProperty.of("facing", facing -> facing != Direction.UP);
        SHAPES = new VoxelShape[] {
                Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 11.0D, 10.0D),  // ground shape
                Block.createCuboidShape(6.0D, 3.0D, 0.0D, 10.0D, 13.0D, 5.0D),
                Block.createCuboidShape(11.0D, 3.0D, 6.0D, 16.0D, 13.0D, 10.0D),
                Block.createCuboidShape(6.0D, 3.0D, 11.0D, 10.0D, 13.0D, 16.0D),
                Block.createCuboidShape(0.0D, 3.0D, 6.0D, 5.0D, 13.0D, 10.0D)
        };
    }
}
