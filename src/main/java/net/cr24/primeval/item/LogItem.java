package net.cr24.primeval.item;

import net.cr24.primeval.block.functional.LogPileBlock;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class LogItem extends WeightedBlockItem {

    private final Block logPileBlock;

    public LogItem(Block block, Block logPileBlock, Weight weight, Size size, net.minecraft.world.item.Item.Properties settings) {
        super(block, weight, size, settings);
        this.logPileBlock = logPileBlock;
    }

    public InteractionResult place(BlockPlaceContext context) {
        if (!context.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext itemPlacementContext = this.updatePlacementContext(context);
            if (itemPlacementContext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockState = this.getPlacementState(itemPlacementContext);
                if (blockState == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(itemPlacementContext, blockState)) {
                    return InteractionResult.FAIL;
                }
                context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.7f, context.getLevel().getRandom().nextFloat() * 0.4f + 0.8f);
            }
        }
        if (!context.getPlayer().hasInfiniteMaterials())
            context.getItemInHand().shrink(1);
        return InteractionResult.SUCCESS;
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockState;
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            Level world = context.getLevel();
            BlockPos pos = context.getClickedPos();
            blockState = logPileBlock.defaultBlockState().setValue(LogPileBlock.WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
        } else {
            blockState = this.getBlock().getStateForPlacement(context);
        }
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }
}
