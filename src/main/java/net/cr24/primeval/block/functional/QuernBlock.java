package net.cr24.primeval.block.functional;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.PrimevalSoundEvents;
import net.cr24.primeval.block.entity.QuernBlockEntity;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.recipe.QuernRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class QuernBlock extends BaseEntityBlock {

    public static final MapCodec<QuernBlock> CODEC = simpleCodec(QuernBlock::new);

    public static final BooleanProperty WHEELED = BooleanProperty.create("wheeled");

    public QuernBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WHEELED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof QuernBlockEntity) {
            popResource(world, pos, ((QuernBlockEntity) blockEntity).inputItem);
            popResource(world, pos, ((QuernBlockEntity) blockEntity).getWheelToDrop());
        }
        blockEntity.setRemoved();
        return state;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity ent = world.getBlockEntity(pos);
        if (ent instanceof QuernBlockEntity) {
            if (itemStack.isEmpty()) {
                if (player.isShiftKeyDown()) {
                    ItemStack storedItem = ((QuernBlockEntity) ent).tryRetrieveInputItem();
                    if (!storedItem.isEmpty()) {
                        player.addItem(storedItem);
                        return InteractionResult.SUCCESS;
                    } else {
                        return InteractionResult.FAIL;
                    }
                } else {
                    boolean success = ((QuernBlockEntity) ent).tryTurnWheel(world, pos, 45);
                    if (success) {
                        if (!player.isCreative()) player.getFoodData().addExhaustion(2.0f);
                        if (!world.isClientSide())
                            world.playSound(null, pos, PrimevalSoundEvents.QUERN_GRIND, SoundSource.BLOCKS, 0.2f, 1f);
                        return InteractionResult.SUCCESS;
                    } else {
                        return InteractionResult.FAIL;
                    }
                }
            } else if (itemStack.is(PrimevalItems.QUERN_WHEEL)) {
                boolean success = ((QuernBlockEntity) ent).tryAddWheel(itemStack);
                if (success) {
                    if (!player.isCreative()) player.setItemInHand(hand, ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.FAIL;
                }
            } else if (world.recipeAccess().propertySet(PrimevalRecipes.QUERN_GRINDING_INPUT).test(itemStack)) {
                boolean success = ((QuernBlockEntity) ent).tryPutInputItem(itemStack);
                if (success) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                } else {
                    return InteractionResult.FAIL;
                }
            }
        }
        return InteractionResult.PASS;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WHEELED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new QuernBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        RecipeManager.CachedCheck<SingleRecipeInput, QuernRecipe> matchGetter = world instanceof ServerLevel ? RecipeManager.createCheck(PrimevalRecipes.QUERN_GRINDING) : null;
        return createTickerHelper(type, PrimevalBlocks.QUERN_BLOCK_ENTITY, (worldx, pos, statex, blockEntity) -> {
            QuernBlockEntity.tick(worldx, pos, statex, blockEntity, matchGetter);
        });
    }
}
