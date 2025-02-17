package net.cr24.primeval.item.tool;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.item.*;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FirestarterItem extends WeightedItem {
    public FirestarterItem(Weight weight, Size size, Settings settings) {
        super(weight, size, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!(user instanceof PlayerEntity)) {
            return stack;
        }
        ItemStack offHand = user.getStackInHand(Hand.OFF_HAND);
        BlockHitResult result = (BlockHitResult) user.raycast(4.5, 0, false);
        if (result.getType() == HitResult.Type.BLOCK && offHand.getItem() == PrimevalItems.STICK) {
            BlockPos pos = result.getBlockPos();
            BlockState existingState = world.getBlockState(pos);
//            if (existingState.getBlock() == PrimevalBlocks.CRUDE_TORCH) {
//                int burnoutStage = existingState.get(TimedTorchBlock.BURNOUT_STAGE);
//                if (burnoutStage != 5) {
//                    world.setBlockState(pos, existingState.with(TimedTorchBlock.BURNOUT_STAGE, 1));
//                }
//            } else if (existingState.getBlock() == PrimevalBlocks.CAMPFIRE) {
//                PrimevalCampfireBlock.tryLight(world, pos, existingState);
//            } else {
                BlockPos pos2 = pos.offset(result.getSide());

//                if (world.getBlockState(pos2.down()).isIn(PrimevalTags.Blocks.CAMPFIRE_BASE) && PrimevalUtil.itemEntitiesInBlock(world, pos2, PrimevalTags.Items.LOGS, PrimevalTags.Items.ROCKS, PrimevalTags.Items.ROCKS, PrimevalTags.Items.CAMPFIRE_KINDLING)) {
//                    world.setBlockState(pos2, PrimevalBlocks.CAMPFIRE.getDefaultState().with(PrimevalCampfireBlock.LIT, true));
//                    BlockEntity blockEntity = world.getBlockEntity(pos2);
//                    if (!world.isClient && blockEntity instanceof PrimevalCampfireBlockEntity) {
//                        ((PrimevalCampfireBlockEntity) blockEntity).addFuel(world.getBlockState(pos2), world, pos2, 1200);
//                        ((PrimevalCampfireBlockEntity) blockEntity).setLit(true);
//                    }
//                } else if (AbstractFireBlock.canPlaceAt(world, pos2, result.getSide())) {
                    setFire(world, (PlayerEntity) user, pos2);
//                }
//            }
            if (!world.isClient) world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 0.5f, world.getRandom().nextFloat() * 0.4f + 0.8f);
        }
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack offHand = user.getStackInHand(Hand.OFF_HAND);
        if (hand == Hand.MAIN_HAND && offHand.getItem() == PrimevalItems.STICK) {
            return ItemUsage.consumeHeldItem(world, user, hand);
        }
        return ActionResult.FAIL;
    }

    private void setFire(World world, PlayerEntity player, BlockPos pos) {
        BlockState blockState2 = AbstractFireBlock.getState(world, pos);
        world.setBlockState(pos, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
    }

}
