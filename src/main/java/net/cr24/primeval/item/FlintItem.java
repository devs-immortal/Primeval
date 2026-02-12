package net.cr24.primeval.item;

import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import static net.cr24.primeval.item.tool.PrimevalHoeItem.hoeables;

public class FlintItem extends WeightedItem {
    public FlintItem(Weight weight, Size size, Item.Properties settings) {
        super(weight, size, settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        Block targetBlock = world.getBlockState(pos).getBlock();
        if (hoeables.containsKey(targetBlock) && world.getBlockState(pos.above()).isAir()) {
            world.setBlockAndUpdate(pos, hoeables.get(targetBlock).defaultBlockState());
            world.playSound(context.getPlayer(), pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!world.isClientSide() && context.getLevel().getRandom().nextFloat() < 0.2) {
                context.getItemInHand().shrink(1);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.useOn(context);
        }
    }
}
