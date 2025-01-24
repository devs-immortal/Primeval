package net.cr24.primeval.item;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.block.entity.LogPileBlockEntity;
import net.cr24.primeval.block.functional.LogPileBlock;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class LogItem extends WeightedBlockItem {

    private final Block logPileBlock;

    public LogItem(Block block, Block logPileBlock, Settings settings, Weight weight, Size size) {
        super(block, settings, weight, size);
        this.logPileBlock = logPileBlock;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof LogPileBlock && state.get(LogPileBlock.AMOUNT) < 7) {
            world.setBlockState(pos, state.with(LogPileBlock.AMOUNT, state.get(LogPileBlock.AMOUNT)+1));
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) context.getStack().decrement(1);
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 0.3f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            return ActionResult.SUCCESS;
        } else {
            return this.place(new ItemPlacementContext(context));
        }
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
