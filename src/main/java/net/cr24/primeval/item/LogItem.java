package net.cr24.primeval.item;

import net.cr24.primeval.block.functional.LogPileBlock;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LogItem extends WeightedBlockItem {

    private final Block logPileBlock;

    public LogItem(Block block, Block logPileBlock, Weight weight, Size size, Settings settings) {
        super(block, weight, size, settings);
        this.logPileBlock = logPileBlock;
    }

    public ActionResult place(ItemPlacementContext context) {
        if (!context.canPlace()) {
            return ActionResult.FAIL;
        } else {
            ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
            if (itemPlacementContext == null) {
                return ActionResult.FAIL;
            } else {
                BlockState blockState = this.getPlacementState(itemPlacementContext);
                if (blockState == null) {
                    return ActionResult.FAIL;
                } else if (!this.place(itemPlacementContext, blockState)) {
                    return ActionResult.FAIL;
                }
                context.getWorld().playSound(null, context.getBlockPos(), SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 0.7f, context.getWorld().getRandom().nextFloat() * 0.4f + 0.8f);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    protected BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockState;
        if (context.getPlayer() != null && context.getPlayer().isSneaking()) {
            World world = context.getWorld();
            BlockPos pos = context.getBlockPos();
            blockState = logPileBlock.getDefaultState().with(LogPileBlock.WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
        } else {
            blockState = this.getBlock().getPlacementState(context);
        }
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }
}
