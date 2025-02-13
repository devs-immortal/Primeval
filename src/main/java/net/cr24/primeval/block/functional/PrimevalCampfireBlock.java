package net.cr24.primeval.block.functional;

import net.cr24.primeval.block.PrimevalBlockTags;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.entity.PrimevalCampfireBlockEntity;
import net.cr24.primeval.item.PrimevalItemTags;
import net.cr24.primeval.item.tool.PrimevalShovelItem;
import net.cr24.primeval.recipe.OpenFireRecipe;
import net.cr24.primeval.recipe.PrimevalRecipes;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
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
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        if (!canPlaceAt(state, world, pos)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PrimevalCampfireBlockEntity) {
                DefaultedList<ItemStack> items = ((PrimevalCampfireBlockEntity) blockEntity).getItemsBeingCooked();
                ItemScatterer.spawn(world, pos, items);
                blockEntity.markRemoved();
            }
            world.breakBlock(pos, true);
        }
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isIn(PrimevalBlockTags.CAMPFIRE_BASE);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof ItemEntity) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            boolean bl = false;
            if (blockEntity instanceof PrimevalCampfireBlockEntity) {
                ItemStack stack = ((ItemEntity) entity).getStack();
                if (stack.isIn(PrimevalItemTags.BURNABLE_LONG)) {
                    if (!world.isClient) bl = ((PrimevalCampfireBlockEntity) blockEntity).addFuel(state, world, pos, 1200);
                } else if (stack.isIn(PrimevalItemTags.BURNABLE_SHORT)) {
                    if (!world.isClient) bl = ((PrimevalCampfireBlockEntity) blockEntity).addFuel(state, world, pos, 200);
                } else if (state.get(LIT)) {
                    entity.setFireTicks(20);
                }
                if (bl) stack.decrement(1);
            }
        } else if (state.get(LIT) && !world.isClient) {
            entity.serverDamage(world.getDamageSources().inFire(), 1);
        }
    }


    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PrimevalCampfireBlockEntity) {
            DefaultedList<ItemStack> items = ((PrimevalCampfireBlockEntity) blockEntity).getItemsBeingCooked();
            ItemScatterer.spawn(world, pos, items);
            blockEntity.markRemoved();
        }
        return state;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
        Optional<OpenFireRecipe> optional;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PrimevalCampfireBlockEntity) {
            if (itemStack == ItemStack.EMPTY && !world.isClient) {
                boolean bl = false;
                List<ItemStack> cooked = ((PrimevalCampfireBlockEntity) blockEntity).retrieveCookedItems();
                for (ItemStack i : cooked) {
                    player.giveItemStack(i);
                    bl = true;
                }
                if (bl) {
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.5f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                    return ActionResult.SUCCESS;
                }
            } else if (itemStack.getItem() instanceof PrimevalShovelItem && !world.isClient && state.get(LIT)) {
                ((PrimevalCampfireBlockEntity) blockEntity).setLit(false);
                world.setBlockState(pos, state.with(LIT, false));
                return ActionResult.SUCCESS;
            } else if (state.get(LIT)) {
                optional = world.getRecipeManager().getFirstMatch(PrimevalRecipes.OPEN_FIRE, new SimpleInventory(itemStack), world); //TODO
                if (optional.isPresent()) {
                    OpenFireRecipe recipe = optional.get();
                    if (!world.isClient && ((PrimevalCampfireBlockEntity) blockEntity).addItem(player.isCreative() ? itemStack.copy() : itemStack, recipe.getCookTime())) {
                        world.playSound(null, pos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 0.7f, world.getRandom().nextFloat() * 0.4f + 0.8f);
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
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(LIT)) {
            return;
        }
        if (random.nextInt(6) == 0) {
            world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5f + random.nextFloat(), random.nextFloat() * 0.7f + 0.6f, false);
        }
        if (random.nextInt(7) == 0) {
            for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                world.addParticle(ParticleTypes.LAVA, (double)pos.getX() + 0.5, (double)pos.getY() + 0.3, (double)pos.getZ() + 0.5, random.nextFloat() / 3.0f, 5.0E-5, random.nextFloat() / 3.0f);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FIRE_SCALE, KINDLING, LIT);
    }

    public static int getLuminanceFromState(BlockState state) {
        if (!state.get(LIT)) return 0;
        int burnout = state.get(FIRE_SCALE);
        switch (burnout) {
            case 0: return 11;
            case 1: return 13;
            case 2: return 14;
            case 3: return 15;
        }
        return 0;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, PrimevalBlocks.CAMPFIRE_BLOCK_ENTITY, (world1, pos, state1, be) -> PrimevalCampfireBlockEntity.tick(world1, pos, state1, be));
    }
}
