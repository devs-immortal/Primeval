package net.cr24.primeval.block.functional;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.entity.PrimevalCampfireBlockEntity;
import net.cr24.primeval.item.PrimevalItemTags;
import net.cr24.primeval.item.PrimevalShovelItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class PrimevalCampfireBlock extends BlockWithEntity {

    public static final IntProperty FIRE_SCALE = IntProperty.of("fire_scale", 0, 3);
    public static final IntProperty KINDLING = IntProperty.of("kindling", 0, 3);
    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

    public PrimevalCampfireBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FIRE_SCALE, 0).with(KINDLING, 0).with(LIT, false));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient) {
            return;
        }
        if (entity instanceof ItemEntity) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            boolean bl = false;
            if (blockEntity instanceof PrimevalCampfireBlockEntity) {
                ItemStack stack = ((ItemEntity) entity).getStack();
                if (stack.isIn(PrimevalItemTags.BURNABLE_LONG)) {
                    bl = ((PrimevalCampfireBlockEntity) blockEntity).addFuel(state, world, pos, 2400);
                } else if (stack.isIn(PrimevalItemTags.BURNABLE_SHORT)) {
                    bl = ((PrimevalCampfireBlockEntity) blockEntity).addFuel(state, world, pos, 300);
                }
            }
            if (bl || state.get(LIT)) entity.damage(DamageSource.IN_FIRE, 10);
        } else if (state.get(LIT)) {
            entity.damage(DamageSource.IN_FIRE, 1);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PrimevalCampfireBlockEntity) {
            DefaultedList<ItemStack> items = ((PrimevalCampfireBlockEntity) blockEntity).getItemsBeingCooked();
            ItemScatterer.spawn(world, pos, items);
            blockEntity.markRemoved();
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        Optional<CampfireCookingRecipe> optional;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PrimevalCampfireBlockEntity) {
            if (itemStack == ItemStack.EMPTY && !world.isClient) {
                List<ItemStack> cooked = ((PrimevalCampfireBlockEntity) blockEntity).retrieveCookedItems();
                for (ItemStack i : cooked) {
                    player.giveItemStack(i);
                }
                return ActionResult.SUCCESS;
            } else if (itemStack.getItem() instanceof PrimevalShovelItem && !world.isClient && state.get(LIT)) {
                ((PrimevalCampfireBlockEntity) blockEntity).setLit(false);
                world.setBlockState(pos, state.with(LIT, false));
                return ActionResult.SUCCESS;
            } else if (state.get(LIT)) {
                optional = world.getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, new SimpleInventory(itemStack), world);
                if (optional.isPresent()) {
                    CampfireCookingRecipe recipe = optional.get();
                    if (!world.isClient && ((PrimevalCampfireBlockEntity) blockEntity).addItem(itemStack, recipe.getCookTime())) {
                        return ActionResult.SUCCESS;
                    }
                    return ActionResult.CONSUME;
                }
            }
        }
        return ActionResult.PASS;
    }

    public static boolean tryLight(World world, BlockPos pos, BlockState state) {
        if (!world.isClient && state.get(KINDLING) > 0) {
            world.setBlockState(pos, state.with(LIT, true));
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PrimevalCampfireBlockEntity) {
                ((PrimevalCampfireBlockEntity) blockEntity).setLit(true);
            }
        }
        return false;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PrimevalCampfireBlockEntity(pos, state);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FIRE_SCALE, KINDLING, LIT);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, PrimevalBlocks.CAMPFIRE_BLOCK_ENTITY, (world1, pos, state1, be) -> PrimevalCampfireBlockEntity.tick(world1, pos, state1, be));
    }
}
