package net.cr24.primeval.block.functional;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.block.LayeredBlock;
import net.cr24.primeval.block.entity.PitKilnBlockEntity;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.recipe.AlloyingRecipe;
import net.cr24.primeval.recipe.MeltingRecipe;
import net.cr24.primeval.recipe.PitKilnFiringRecipe;
import net.cr24.primeval.util.FluidInput;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PitKilnBlock extends BaseEntityBlock {

    public static final MapCodec<PitKilnBlock> CODEC = simpleCodec(PitKilnBlock::new);

    public static final IntegerProperty BUILD_STEP = IntegerProperty.create("build_step", 0, 8);
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public PitKilnBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(BUILD_STEP, 0).setValue(LIT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);
        breakBlockEntity(world, pos, state);
        return state;
    }

    private void breakBlockEntity(Level world, BlockPos pos, BlockState state) {
        popResource(world, pos, new ItemStack(PrimevalItems.STRAW, 1+Math.min(state.getValue(BUILD_STEP), 4)));
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PitKilnBlockEntity) {
            for (ItemStack stack : ((PitKilnBlockEntity) blockEntity).getItems()) {
                popResource(world, pos, stack);
            }
            for (ItemStack stack : ((PitKilnBlockEntity) blockEntity).getLogs()) {
                if (stack != null) popResource(world, pos, stack);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        super.neighborChanged(state, world, pos, sourceBlock, wireOrientation, notify);
        if (!isSoilSurrounded(world, pos)) { // if not surrounded properly
            breakBlockEntity(world, pos, state);
            world.destroyBlock(pos, true);
        } else if (world.getBlockState(pos.above()).is(Blocks.FIRE)) { // if lit on fire
            BlockEntity blockEnt = world.getBlockEntity(pos);
            if (blockEnt instanceof PitKilnBlockEntity) {
                ((PitKilnBlockEntity) blockEnt).startFiring();
                world.setBlockAndUpdate(pos, state.setValue(LIT, true));
            }
        } else { // if fire goes away/is not present
            BlockEntity blockEnt = world.getBlockEntity(pos);
            if (blockEnt instanceof PitKilnBlockEntity) {
                ((PitKilnBlockEntity) blockEnt).stopFiring();
                world.setBlockAndUpdate(pos, state.setValue(LIT, false));
            }
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        int shapeState = state.getValue(BUILD_STEP);
        if (shapeState < 5) {
            return LayeredBlock.LAYERS_TO_SHAPE[(Integer)state.getValue(BUILD_STEP)+1];
        } else if (state.getValue(BUILD_STEP) < 7) {
            return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
        } else {
            return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        }
    }


    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(BUILD_STEP) < 5) {
            return LayeredBlock.LAYERS_TO_SHAPE[state.getValue(BUILD_STEP)];
        } else if (state.getValue(BUILD_STEP) < 7) {
            return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
        } else {
            return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        }
    }


    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PitKilnBlockEntity(pos, state);
    }


    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }


    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isShiftKeyDown()) {
            int stage = state.getValue(BUILD_STEP);
            if (stage == 0) {

                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PitKilnBlockEntity) {
                    Vec3 clicked = hit.getLocation().subtract(hit.getBlockPos().getX(), 0, hit.getBlockPos().getZ());
                    int index = 0;
                    if (clicked.x > 0.5) {
                        index+=1;
                    }
                    if (clicked.z > 0.5) {
                        index+=2;
                    }
                    player.addItem(((PitKilnBlockEntity) blockEntity).removeItem(index));
                    if (world.isClientSide()) world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.5f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.FAIL;
                }

            } else if (stage > 4) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PitKilnBlockEntity) {
                    world.setBlockAndUpdate(pos, state.setValue(BUILD_STEP, stage-1));
                    player.addItem(new ItemStack(((PitKilnBlockEntity) blockEntity).removeLog().getItem(), 1));
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.FAIL;
                }
            } else {
                world.setBlockAndUpdate(pos, state.setValue(BUILD_STEP, stage-1));
                player.addItem(new ItemStack(PrimevalItems.STRAW, 1));
                return InteractionResult.SUCCESS;
            }
        } else {
            ItemStack itemStack = player.getItemInHand(hand);
            int stage = state.getValue(BUILD_STEP);
            if (itemStack.is(PrimevalItems.STRAW) && stage < 4) {
                world.setBlockAndUpdate(pos, state.setValue(BUILD_STEP, stage+1));
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                if (world.isClientSide()) world.playSound(null, pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 0.3f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                return InteractionResult.SUCCESS;
            } else if (itemStack.is(PrimevalTags.Items.LOGS) && stage > 3 && stage < 8) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PitKilnBlockEntity) {
                    ((PitKilnBlockEntity) blockEntity).addLog(new ItemStack(itemStack.getItem(), 1));
                    world.setBlockAndUpdate(pos, state.setValue(BUILD_STEP, stage+1));
                    if (!player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.FAIL;
                }
            } else if (!itemStack.isEmpty() && stage == 0) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof PitKilnBlockEntity) {
                    Vec3 clicked = hit.getLocation().subtract(hit.getBlockPos().getX(), 0, hit.getBlockPos().getZ());
                    int index = 0;
                    if (clicked.x > 0.5) {
                        index+=1;
                    }
                    if (clicked.z > 0.5) {
                        index+=2;
                    }
                    ItemStack newStack = itemStack.copy();
                    newStack.setCount(1);
                    if (((PitKilnBlockEntity) blockEntity).addItem(newStack, index)) {
                        if (!player.isCreative()) {
                            player.getItemInHand(hand).shrink(1);
                        }
                        return InteractionResult.SUCCESS;
                    } else {
                        return InteractionResult.FAIL;
                    }
                } else {
                    return InteractionResult.FAIL;
                }
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BUILD_STEP, LIT);
    }

    public static boolean isSoilSurrounded(LevelAccessor world, BlockPos block_pos) {
        for (BlockPos pos : new BlockPos[] {block_pos.north(), block_pos.east(), block_pos.south(), block_pos.west(), block_pos.below()}) {
            if (!world.getBlockState(pos).is(PrimevalTags.Blocks.SOIL)) return false;
        }
        return true;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (world instanceof ServerLevel) {
            RecipeManager.CachedCheck<SingleRecipeInput, PitKilnFiringRecipe> matchGetter = RecipeManager.createCheck(PrimevalRecipes.PIT_KILN_FIRING);
            RecipeManager.CachedCheck<SingleRecipeInput, MeltingRecipe> vesselMatchGetter = RecipeManager.createCheck(PrimevalRecipes.MELTING);
            RecipeManager.CachedCheck<FluidInput, AlloyingRecipe> alloyMatchGetter = RecipeManager.createCheck(PrimevalRecipes.ALLOYING);
            return createTickerHelper(type, PrimevalBlocks.PIT_KILN_BLOCK_ENTITY, (worldx, pos, statex, blockEntity) -> {
                PitKilnBlockEntity.serverTick((ServerLevel) world, pos, statex, blockEntity, matchGetter, vesselMatchGetter, alloyMatchGetter);
            });
        }
        return null;
    }

}
