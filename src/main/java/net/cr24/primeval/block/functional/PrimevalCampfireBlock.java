package net.cr24.primeval.block.functional;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.block.entity.PrimevalCampfireBlockEntity;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.item.tool.PrimevalShovelItem;
import net.cr24.primeval.recipe.OpenFireRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrimevalCampfireBlock extends BaseEntityBlock {

    public static final MapCodec<PrimevalCampfireBlock> CODEC = simpleCodec(PrimevalCampfireBlock::new);

    public static final IntegerProperty FIRE_SCALE = IntegerProperty.create("fire_scale", 0, 3);
    public static final IntegerProperty KINDLING = IntegerProperty.create("kindling", 0, 3);
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

    public PrimevalCampfireBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FIRE_SCALE, 0).setValue(KINDLING, 0).setValue(LIT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (!canSurvive(state, world, pos)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PrimevalCampfireBlockEntity) {
                NonNullList<ItemStack> items = ((PrimevalCampfireBlockEntity) blockEntity).getItemsBeingCooked();
                Containers.dropContents(world, pos, items);
                blockEntity.setRemoved();
            }
            world.destroyBlock(pos, true);
        }
        super.neighborChanged(state, world, pos, sourceBlock, wireOrientation, notify);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).is(PrimevalTags.Blocks.CAMPFIRE_BASE);
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler, boolean bl2) {
        if (entity instanceof ItemEntity) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            boolean bl = false;
            if (blockEntity instanceof PrimevalCampfireBlockEntity) {
                ItemStack stack = ((ItemEntity) entity).getItem();
                if (stack.is(PrimevalTags.Items.BURNABLE_LONG)) {
                    if (!world.isClientSide()) bl = ((PrimevalCampfireBlockEntity) blockEntity).addFuel(state, world, pos, 1200);
                } else if (stack.is(PrimevalTags.Items.BURNABLE_SHORT)) {
                    if (!world.isClientSide()) bl = ((PrimevalCampfireBlockEntity) blockEntity).addFuel(state, world, pos, 200);
                } else if (state.getValue(LIT)) {
                    entity.setRemainingFireTicks(20);
                }
                if (bl) stack.shrink(1);
            }
        } else if (state.getValue(LIT) && !world.isClientSide()) {
            entity.hurt(world.damageSources().inFire(), 1);
        }
    }


    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PrimevalCampfireBlockEntity) {
            NonNullList<ItemStack> items = ((PrimevalCampfireBlockEntity) blockEntity).getItemsBeingCooked();
            Containers.dropContents(world, pos, items);
            blockEntity.setRemoved();
        }
        return state;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PrimevalCampfireBlockEntity) {
            if (itemStack == ItemStack.EMPTY && !world.isClientSide()) {
                boolean bl = false;
                List<ItemStack> cooked = ((PrimevalCampfireBlockEntity) blockEntity).retrieveCookedItems();
                for (ItemStack i : cooked) {
                    player.addItem(i);
                    bl = true;
                }
                if (bl) {
                    world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.5f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                    return InteractionResult.SUCCESS;
                }
            } else if (itemStack.getItem() instanceof PrimevalShovelItem && !world.isClientSide() && state.getValue(LIT)) {
                ((PrimevalCampfireBlockEntity) blockEntity).setLit(false);
                world.setBlockAndUpdate(pos, state.setValue(LIT, false));
                return InteractionResult.SUCCESS;
            } else if (state.getValue(LIT)) {
                if (world.recipeAccess().propertySet(PrimevalRecipes.OPEN_FIRE_INPUT).test(itemStack)) {
                    if (world instanceof ServerLevel) {
                        var recipe = ((ServerLevel) world).recipeAccess().getRecipeFor(PrimevalRecipes.OPEN_FIRE, new SingleRecipeInput(itemStack), world);
                        if (recipe.isPresent() && ((PrimevalCampfireBlockEntity) blockEntity).addItem(player.isCreative() ? itemStack.copy() : itemStack, recipe.get().value().getCookTime())) {
                            world.playSound(null, pos, SoundEvents.STONE_HIT, SoundSource.BLOCKS, 0.7f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                            return InteractionResult.SUCCESS;
                        }
                    }
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.PASS;
    }

    public static boolean tryLight(Level world, BlockPos pos, BlockState state) {
        if (!world.isClientSide() && state.getValue(KINDLING) > 0) {
            world.setBlockAndUpdate(pos, state.setValue(LIT, true));
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PrimevalCampfireBlockEntity) {
                ((PrimevalCampfireBlockEntity) blockEntity).setLit(true);
            }
        }
        return false;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PrimevalCampfireBlockEntity(pos, state);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!state.getValue(LIT)) {
            return;
        }
        if (random.nextInt(6) == 0) {
            world.playLocalSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5f + random.nextFloat(), random.nextFloat() * 0.7f + 0.6f, false);
        }
        if (random.nextInt(7) == 0) {
            for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                world.addParticle(ParticleTypes.LAVA, (double)pos.getX() + 0.5, (double)pos.getY() + 0.3, (double)pos.getZ() + 0.5, random.nextFloat() / 3.0f, 5.0E-5, random.nextFloat() / 3.0f);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FIRE_SCALE, KINDLING, LIT);
    }

    public static int getLuminanceFromState(BlockState state) {
        if (!state.getValue(LIT)) return 0;
        int burnout = state.getValue(FIRE_SCALE);
        switch (burnout) {
            case 0: return 11;
            case 1: return 13;
            case 2: return 14;
            case 3: return 15;
        }
        return 0;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        RecipeManager.CachedCheck<SingleRecipeInput, OpenFireRecipe> matchGetter = RecipeManager.createCheck(PrimevalRecipes.OPEN_FIRE);
        return createTickerHelper(type, PrimevalBlocks.CAMPFIRE_BLOCK_ENTITY, (worldx, pos, statex, blockEntity) -> PrimevalCampfireBlockEntity.tick(world, pos, statex, blockEntity, matchGetter));
    }
}
