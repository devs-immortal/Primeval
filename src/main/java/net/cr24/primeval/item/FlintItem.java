package net.cr24.primeval.item;

import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.block.Block;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.cr24.primeval.item.tool.PrimevalHoeItem.hoeables;

public class FlintItem extends WeightedItem {
    public FlintItem(Weight weight, Size size, Settings settings) {
        super(weight, size, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        Block targetBlock = world.getBlockState(pos).getBlock();
        if (hoeables.containsKey(targetBlock) && world.getBlockState(pos.up()).isAir()) {
            world.setBlockState(pos, hoeables.get(targetBlock).getDefaultState());
            world.playSound(context.getPlayer(), pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!world.isClient() && context.getWorld().getRandom().nextFloat() < 0.2) {
                context.getStack().decrement(1);
                return ActionResult.CONSUME;
            }
            return ActionResult.SUCCESS;
        } else {
            return super.useOnBlock(context);
        }
    }
}
