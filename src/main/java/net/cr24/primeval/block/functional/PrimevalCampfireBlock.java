package net.cr24.primeval.block.functional;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.entity.PitKilnBlockEntity;
import net.cr24.primeval.block.entity.PrimevalCampfireBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.WeightedPressurePlateBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class PrimevalCampfireBlock extends BlockWithEntity {

    public PrimevalCampfireBlock(Settings settings) {
        super(settings);
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
            if (itemStack == ItemStack.EMPTY) {
                List<ItemStack> cooked = ((PrimevalCampfireBlockEntity) blockEntity).retrieveCookedItems();
                for (ItemStack i : cooked) {
                    player.giveItemStack(i);
                }
            } else {
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

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PrimevalCampfireBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, PrimevalBlocks.CAMPFIRE_BLOCK_ENTITY, (world1, pos, state1, be) -> PrimevalCampfireBlockEntity.tick(world1, pos, state1, be));
    }
}
