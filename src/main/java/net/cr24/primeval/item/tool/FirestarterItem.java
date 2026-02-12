package net.cr24.primeval.item.tool;

import net.cr24.primeval.block.entity.PrimevalCampfireBlockEntity;
import net.cr24.primeval.block.functional.PrimevalCampfireBlock;
import net.cr24.primeval.block.functional.TimedTorchBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.item.*;
import net.cr24.primeval.util.PrimevalUtil;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class FirestarterItem extends WeightedItem {
    public FirestarterItem(Weight weight, Size size, Item.Properties settings) {
        super(weight, size, settings);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!(user instanceof Player)) {
            return stack;
        }
        ItemStack offHand = user.getItemInHand(InteractionHand.OFF_HAND);
        BlockHitResult result = (BlockHitResult) user.pick(4.5, 0, false);
        if (result.getType() == HitResult.Type.BLOCK && offHand.getItem() == PrimevalItems.STICK) {
            BlockPos pos = result.getBlockPos();
            BlockState existingState = world.getBlockState(pos);
            if (existingState.getBlock() == PrimevalBlocks.CRUDE_TORCH) {
                int burnoutStage = existingState.getValue(TimedTorchBlock.BURNOUT_STAGE);
                if (burnoutStage != 5) {
                    world.setBlockAndUpdate(pos, existingState.setValue(TimedTorchBlock.BURNOUT_STAGE, 1));
                }
            } else if (existingState.getBlock() == PrimevalBlocks.CAMPFIRE) {
                PrimevalCampfireBlock.tryLight(world, pos, existingState);
            } else {
                BlockPos pos2 = pos.relative(result.getDirection());

                if (world.getBlockState(pos2.below()).is(PrimevalTags.Blocks.CAMPFIRE_BASE) && PrimevalUtil.itemEntitiesInBlock(world, pos2, PrimevalTags.Items.LOGS, PrimevalTags.Items.ROCKS, PrimevalTags.Items.ROCKS, PrimevalTags.Items.CAMPFIRE_KINDLING)) {
                    world.setBlockAndUpdate(pos2, PrimevalBlocks.CAMPFIRE.defaultBlockState().setValue(PrimevalCampfireBlock.LIT, true));
                    BlockEntity blockEntity = world.getBlockEntity(pos2);
                    if (!world.isClientSide() && blockEntity instanceof PrimevalCampfireBlockEntity) {
                        ((PrimevalCampfireBlockEntity) blockEntity).addFuel(world.getBlockState(pos2), world, pos2, 1200);
                        ((PrimevalCampfireBlockEntity) blockEntity).setLit(true);
                    }
                } else if (BaseFireBlock.canBePlacedAt(world, pos2, result.getDirection())) {
                    setFire(world, pos2);
                }
            }
            if (!world.isClientSide()) world.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.5f, world.getRandom().nextFloat() * 0.4f + 0.8f);
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack offHand = user.getItemInHand(InteractionHand.OFF_HAND);
        if (hand == InteractionHand.MAIN_HAND && offHand.getItem() == PrimevalItems.STICK) {
            return ItemUtils.startUsingInstantly(world, user, hand);
        }
        return InteractionResult.FAIL;
    }

    private void setFire(Level world, BlockPos pos) {
        BlockState blockState2 = BaseFireBlock.getState(world, pos);
        world.setBlock(pos, blockState2, Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
    }

}
