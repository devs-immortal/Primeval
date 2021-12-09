package net.cr24.primeval.block;

import net.cr24.primeval.block.entity.PitKilnBlockEntity;
import net.cr24.primeval.item.PrimevalItemTags;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.recipe.PitKilnFiringRecipe;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class PitKilnBlock extends BlockWithEntity {

    public static final IntProperty BUILD_STEP = IntProperty.of("build_step", 0, 8);
    public static final BooleanProperty LIT = BooleanProperty.of("lit");
    protected static final VoxelShape[] SHAPES;

    protected PitKilnBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(BUILD_STEP, 0).with(LIT, false));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        breakBlockEntity(world, pos, state);
    }

    private void breakBlockEntity(World world, BlockPos pos, BlockState state) {
        dropStack(world, pos, new ItemStack(PrimevalItems.STRAW, 1+Math.min(state.get(BUILD_STEP), 4)));
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PitKilnBlockEntity) {
            for (ItemStack stack : ((PitKilnBlockEntity) blockEntity).getItems()) {
                dropStack(world, pos, stack);
            }
            for (ItemStack stack : ((PitKilnBlockEntity) blockEntity).getLogs()) {
                if (stack != null) dropStack(world, pos, stack);
            }
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        if (!isSoilSurrounded(world, pos)) { // if not surrounded properly
            breakBlockEntity(world, pos, state);
            world.breakBlock(pos, true);
        } else if (world.getBlockState(pos.up()).isOf(Blocks.FIRE)) { // if lit on fire
            BlockEntity blockEnt = world.getBlockEntity(pos);
            if (blockEnt instanceof PitKilnBlockEntity) {
                ((PitKilnBlockEntity) blockEnt).startFiring();
                world.setBlockState(pos, state.with(LIT, true));
            }
        } else { // if fire goes away/is not present
            BlockEntity blockEnt = world.getBlockEntity(pos);
            if (blockEnt instanceof PitKilnBlockEntity) {
                ((PitKilnBlockEntity) blockEnt).stopFiring();
                world.setBlockState(pos, state.with(LIT, false));
            }
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int shapeState = state.get(BUILD_STEP);
        if (shapeState < 5) {
            return LayeredBlock.LAYERS_TO_SHAPE[(Integer)state.get(BUILD_STEP)+1];
        } else {
            return SHAPES[shapeState-5];
        }
    }


    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(BUILD_STEP) < 5) {
            return LayeredBlock.LAYERS_TO_SHAPE[state.get(BUILD_STEP)];
        } else if (state.get(BUILD_STEP) < 7) {
            return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
        } else {
            return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        }
    }


    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PitKilnBlockEntity(pos, state);
    }


    public boolean hasSidedTransparency(BlockState state) {
        return false;
    }


    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        } else if (player.isSneaking()) {
            int stage = state.get(BUILD_STEP);
            if (stage == 0) {

                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PitKilnBlockEntity) {
                    Vec3d clicked = hit.getPos().subtract(hit.getBlockPos().getX(), 0, hit.getBlockPos().getZ());
                    int index = 0;
                    if (clicked.x > 0.5) {
                        index+=1;
                    }
                    if (clicked.z > 0.5) {
                        index+=2;
                    }
                    player.giveItemStack(((PitKilnBlockEntity) blockEntity).removeItem(index));
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.FAIL;
                }

            } else if (stage > 4) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PitKilnBlockEntity) {
                    world.setBlockState(pos, state.with(BUILD_STEP, stage-1));
                    player.giveItemStack(new ItemStack(((PitKilnBlockEntity) blockEntity).removeLog().getItem(), 1));
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.FAIL;
                }
            } else {
                world.setBlockState(pos, state.with(BUILD_STEP, stage-1));
                player.giveItemStack(new ItemStack(PrimevalItems.STRAW, 1));
                return ActionResult.SUCCESS;
            }
        } else {
            ItemStack itemStack = player.getStackInHand(hand);
            int stage = state.get(BUILD_STEP);
            if (itemStack.isOf(PrimevalItems.STRAW) && stage < 4) {
                world.setBlockState(pos, state.with(BUILD_STEP, stage+1));
                if (!player.isCreative()) {
                    player.getStackInHand(hand).decrement(1);
                }
                return ActionResult.SUCCESS;
            } else if (itemStack.isIn(PrimevalItemTags.LOGS) && stage > 3 && stage < 8) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PitKilnBlockEntity) {
                    ((PitKilnBlockEntity) blockEntity).addLog(new ItemStack(itemStack.getItem(), 1));
                    world.setBlockState(pos, state.with(BUILD_STEP, stage+1));
                    if (!player.isCreative()) {
                        player.getStackInHand(hand).decrement(1);
                    }
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.FAIL;
                }
            } else if (!itemStack.isEmpty() && stage == 0) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PitKilnBlockEntity) {
                    Vec3d clicked = hit.getPos().subtract(hit.getBlockPos().getX(), 0, hit.getBlockPos().getZ());
                    int index = 0;
                    if (clicked.x > 0.5) {
                        index+=1;
                    }
                    if (clicked.z > 0.5) {
                        index+=2;
                    }
                    if (((PitKilnBlockEntity) blockEntity).addItem(new ItemStack(itemStack.getItem(), 1), index)) {
                        if (!player.isCreative()) {
                            player.getStackInHand(hand).decrement(1);
                        }
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.FAIL;
                    }
                } else {
                    return ActionResult.FAIL;
                }
            } else {
                return ActionResult.PASS;
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BUILD_STEP, LIT);
    }

    public static boolean isSoilSurrounded(WorldAccess world, BlockPos block_pos) {
        for (BlockPos pos : new BlockPos[] {block_pos.north(), block_pos.east(), block_pos.south(), block_pos.west(), block_pos.down()}) {
            if (!world.getBlockState(pos).isIn(PrimevalBlockTags.SOIL)) return false;
        }
        return true;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, PrimevalBlocks.PIT_KILN_BLOCK_ENTITY, (world1, pos, state1, be) -> PitKilnBlockEntity.tick(world1, pos, state1, be));
    }

    static {
        SHAPES = new VoxelShape[] {
                VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 10.0D, 1.0D, 16.0D, 13.0D, 5.0D)),
                VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 10.0D, 1.0D, 16.0D, 13.0D, 5.0D), Block.createCuboidShape(0.0D, 10.0D, 11.0D, 16.0D, 13.0D, 15.0D)),
                VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 10.0D, 1.0D, 16.0D, 13.0D, 5.0D), Block.createCuboidShape(0.0D, 10.0D, 11.0D, 16.0D, 13.0D, 15.0D), Block.createCuboidShape(1.0D, 12.0D, 0.0D, 5.0D, 16.0D, 16.0D)),
                VoxelShapes.union(Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 10.0D, 1.0D, 16.0D, 13.0D, 5.0D), Block.createCuboidShape(0.0D, 10.0D, 11.0D, 16.0D, 13.0D, 15.0D), Block.createCuboidShape(1.0D, 12.0D, 0.0D, 5.0D, 16.0D, 16.0D), Block.createCuboidShape(11.0D, 12.0D, 0.0D, 15.0D, 16.0D, 16.0D))
        };
    }
}
