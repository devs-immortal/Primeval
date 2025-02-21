package net.cr24.primeval.block.functional;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.PrimevalSoundEvents;
import net.cr24.primeval.block.entity.QuernBlockEntity;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.recipe.QuernRecipe;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class QuernBlock extends BlockWithEntity {

    public static final MapCodec<QuernBlock> CODEC = createCodec(QuernBlock::new);

    public static final BooleanProperty WHEELED = BooleanProperty.of("wheeled");

    public QuernBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WHEELED, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof QuernBlockEntity) {
            dropStack(world, pos, ((QuernBlockEntity) blockEntity).inputItem);
            dropStack(world, pos, ((QuernBlockEntity) blockEntity).getWheelToDrop());
        }
        blockEntity.markRemoved();
        return state;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack itemStack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity ent = world.getBlockEntity(pos);
        if (ent instanceof QuernBlockEntity) {
            if (itemStack.isEmpty()) {
                if (player.isSneaking()) {
                    ItemStack storedItem = ((QuernBlockEntity) ent).tryRetrieveInputItem();
                    if (!storedItem.isEmpty()) {
                        player.giveItemStack(storedItem);
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.FAIL;
                    }
                } else {
                    boolean success = ((QuernBlockEntity) ent).tryTurnWheel(world, pos, 45);
                    if (success) {
                        if (!player.isCreative()) player.getHungerManager().addExhaustion(2.0f);
                        if (!world.isClient())
                            world.playSound(null, pos, PrimevalSoundEvents.QUERN_GRIND, SoundCategory.BLOCKS, 0.2f, 1f);
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.FAIL;
                    }
                }
            } else if (itemStack.isOf(PrimevalItems.QUERN_WHEEL)) {
                boolean success = ((QuernBlockEntity) ent).tryAddWheel(itemStack);
                if (success) {
                    if (!player.isCreative()) player.setStackInHand(hand, ItemStack.EMPTY);
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.FAIL;
                }
            } else if (world.getRecipeManager().getPropertySet(PrimevalRecipes.QUERN_GRINDING_INPUT).canUse(itemStack)) {
                boolean success = ((QuernBlockEntity) ent).tryPutInputItem(itemStack);
                if (success) {
                    player.setStackInHand(hand, ItemStack.EMPTY);
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.FAIL;
                }
            }
        }
        return ActionResult.PASS;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WHEELED);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new QuernBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        ServerRecipeManager.MatchGetter<SingleStackRecipeInput, QuernRecipe> matchGetter = world instanceof ServerWorld ? ServerRecipeManager.createCachedMatchGetter(PrimevalRecipes.QUERN_GRINDING) : null;
        return validateTicker(type, PrimevalBlocks.QUERN_BLOCK_ENTITY, (worldx, pos, statex, blockEntity) -> {
            QuernBlockEntity.tick(worldx, pos, statex, blockEntity, matchGetter);
        });
    }
}
