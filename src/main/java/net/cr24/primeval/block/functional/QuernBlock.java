package net.cr24.primeval.block.functional;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.entity.QuernBlockEntity;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.cr24.primeval.recipe.QuernRecipe;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
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

import java.util.Optional;

public class QuernBlock extends BlockWithEntity {

    public static final BooleanProperty WHEELED = BooleanProperty.of("wheeled");

    public QuernBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WHEELED, false));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof QuernBlockEntity) {
            dropStack(world, pos, ((QuernBlockEntity) blockEntity).inputItem);
            dropStack(world, pos, ((QuernBlockEntity) blockEntity).getWheelToDrop());
        }
        blockEntity.markRemoved();
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity ent = world.getBlockEntity(pos);
        if (ent instanceof QuernBlockEntity) {

            ItemStack handItem = player.getStackInHand(hand);

            if (handItem.isEmpty()) {
                if (player.isSneaking()) {
                    ItemStack storedItem = ((QuernBlockEntity) ent).tryRetrieveInputItem();
                    if (!storedItem.isEmpty()) {
                        player.giveItemStack(storedItem);
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.FAIL;
                    }
                } else {
                    boolean success = ((QuernBlockEntity) ent).tryTurnWheel(world, pos, 30);
                    if (success) {
                        if (!player.isCreative()) player.getHungerManager().addExhaustion(2.0f);
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.FAIL;
                    }
                }
            } else if (handItem.isOf(PrimevalItems.QUERN_WHEEL)) {
                boolean success = ((QuernBlockEntity) ent).tryAddWheel(handItem);
                if (success) {
                    if (!player.isCreative()) player.setStackInHand(hand, ItemStack.EMPTY);
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.FAIL;
                }
            } else {
                Optional<QuernRecipe> recipe = world.getRecipeManager().getFirstMatch(PrimevalRecipes.QUERN_GRINDING, new SimpleInventory(player.getStackInHand(hand)), world);
                if (recipe.isPresent()) {
                    boolean success = ((QuernBlockEntity) ent).tryPutInputItem(handItem);
                    if (success) {
                        player.setStackInHand(hand, ItemStack.EMPTY);
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.FAIL;
                    }
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
        return checkType(type, PrimevalBlocks.QUERN_BLOCK_ENTITY, (world1, pos, state1, be) -> QuernBlockEntity.tick(world1, pos, state1, be));
    }
}
