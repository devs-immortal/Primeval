package net.cr24.primeval.item.tool;

import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.item.WeightedItem;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WoodenBucketItem extends WeightedItem {

    public WoodenBucketItem(Weight weight, Size size, Item.Properties settings) {
        super(weight, size, 4, settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!world.mayInteract(user, blockPos)) {
                    return InteractionResult.PASS;
                }

                if (world.getFluidState(blockPos).is(FluidTags.WATER)) {
                    world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.BUCKET_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    world.gameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                    return InteractionResult.SUCCESS.heldItemTransformedTo(this.fill(itemStack, user, new ItemStack(PrimevalItems.WOODEN_BUCKET_WATER)));
                }
            }
        }
        return InteractionResult.PASS;
    }

    protected ItemStack fill(ItemStack stack, Player player, ItemStack outputStack) {
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.createFilledResult(stack, player, outputStack);
    }

    @Override
    public Weight getWeight() {
        return this.weight;
    }

    @Override
    public Size getSize() {
        return this.size;
    }
}
