package net.cr24.primeval.block.functional;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TimedTorchBlock extends Block {
    static final int TICKS = 6000;

    public static final IntegerProperty BURNOUT_STAGE;
    public static final EnumProperty<Direction> DIRECTION;
    protected static final VoxelShape[] SHAPES;
    private static final Direction[] SIDES = new Direction[] {Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public TimedTorchBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(BURNOUT_STAGE, 0).setValue(DIRECTION, Direction.DOWN));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BURNOUT_STAGE, DIRECTION);
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction shapeState = state.getValue(DIRECTION);
        switch (shapeState) {
            case DOWN: return SHAPES[0];
            case NORTH: return SHAPES[1];
            case EAST: return SHAPES[2];
            case SOUTH: return SHAPES[3];
            case WEST: return SHAPES[4];
        }
        return null;
    }

    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        int stage = state.getValue(BURNOUT_STAGE);
        if (stage != 0 && stage < 5) {
            world.scheduleTick(pos, PrimevalBlocks.CRUDE_TORCH, TICKS*stage);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int stage = state.getValue(BURNOUT_STAGE);
        world.setBlockAndUpdate(pos, state.setValue(BURNOUT_STAGE, Math.min(stage+1, 5)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        int burnState = 0;
        if (ctx.getItemInHand().getItem() == PrimevalItems.LIT_CRUDE_TORCH) {
            burnState = 1;
        }
        Direction[] arranged = new Direction[SIDES.length+1];
        arranged[0] = ctx.getClickedFace();
        for (int i = 1; i <= SIDES.length; i++) {
            arranged[i] = SIDES[i-1];
        }
        for (Direction direction1 : arranged) {
            if (direction1 == Direction.UP && world.getBlockState(blockPos.below()).isFaceSturdy(world, blockPos.below(), Direction.UP, SupportType.CENTER)) {
                return this.defaultBlockState().setValue(DIRECTION, Direction.DOWN).setValue(BURNOUT_STAGE, burnState);
            } else if (direction1 != Direction.DOWN) {
                BlockPos placedOn = blockPos.relative(direction1.getOpposite());
                if (world.getBlockState(placedOn).isFaceSturdy(world, placedOn, direction1)) {
                    return this.defaultBlockState().setValue(DIRECTION, direction1.getOpposite()).setValue(BURNOUT_STAGE, burnState);
                }
            }
        }
        return null;
    }

    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        Direction torchDirection = state.getValue(DIRECTION);
        BlockPos placedOn = pos.relative(torchDirection);
        if (torchDirection == Direction.DOWN) {
            if (!world.getBlockState(placedOn).isFaceSturdy(world, placedOn, Direction.UP, SupportType.CENTER))
                return Blocks.AIR.defaultBlockState();
        } else if (!world.getBlockState(placedOn).isFaceSturdy(world, placedOn, torchDirection.getOpposite())) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        int burnout = state.getValue(BURNOUT_STAGE);
        if (burnout != 0 && burnout != 5) {
            Direction direction = state.getValue(DIRECTION);
            double d = (double)pos.getX() + 0.5;
            double e = (double)pos.getY() + 0.85;
            double f = (double)pos.getZ() + 0.5;
            world.addParticle(ParticleTypes.SMOKE, d + direction.getStepX() * 0.27, e + direction.getStepY() * 0.125, f + direction.getStepZ() * 0.27, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.FLAME, d + direction.getStepX() * 0.27, e + direction.getStepY() * 0.125, f + direction.getStepZ() * 0.27, 0.0, 0.0, 0.0);
        }
    }

    public static int getLuminanceFromState(BlockState state) {
        int burnout = state.getValue(BURNOUT_STAGE);
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
        BURNOUT_STAGE = IntegerProperty.create("burnout_stage", 0, 5); // 0 = unlit    1,2,3,4 = burning    5 = extinguished
        DIRECTION = EnumProperty.create("facing", Direction.class, new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.DOWN});
        SHAPES = new VoxelShape[] {
                Block.box(6.0D, 0.0D, 6.0D, 10.0D, 11.0D, 10.0D),  // ground shape
                Block.box(6.0D, 3.0D, 0.0D, 10.0D, 13.0D, 5.0D),
                Block.box(11.0D, 3.0D, 6.0D, 16.0D, 13.0D, 10.0D),
                Block.box(6.0D, 3.0D, 11.0D, 10.0D, 13.0D, 16.0D),
                Block.box(0.0D, 3.0D, 6.0D, 5.0D, 13.0D, 10.0D)
        };
    }
}
