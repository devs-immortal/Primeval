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
        ActionResult actionResult;
        if (state.getBlock() instanceof LogPileBlock && state.get(LogPileBlock.AMOUNT) < 7) {
            world.setBlockState(pos, state.with(LogPileBlock.AMOUNT, state.get(LogPileBlock.AMOUNT)+1));
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) context.getStack().decrement(1);
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 0.3f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            actionResult = ActionResult.success(world.isClient);
        } else {
            actionResult = this.place(new ItemPlacementContext(context));
        }
        if (!actionResult.isAccepted() && this.isFood()) {
            ActionResult actionResult2 = this.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult();
            return actionResult2 == ActionResult.CONSUME ? ActionResult.CONSUME_PARTIAL : actionResult2;
        } else {
            return actionResult;
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
                } else {
                    BlockPos blockPos = itemPlacementContext.getBlockPos();
                    World world = itemPlacementContext.getWorld();
                    PlayerEntity playerEntity = itemPlacementContext.getPlayer();
                    ItemStack itemStack = itemPlacementContext.getStack();
                    BlockState blockState2 = world.getBlockState(blockPos);
                    if (blockState2.isOf(blockState.getBlock())) {
                        blockState2 = this.placeFromNbt(blockPos, world, itemStack, blockState2);
                        this.postPlacement(blockPos, world, playerEntity, itemStack, blockState2);
                        blockState2.getBlock().onPlaced(world, blockPos, blockState2, playerEntity, itemStack);
                        if (playerEntity instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
                        }
                    }

                    BlockSoundGroup blockSoundGroup = blockState2.getSoundGroup();
                    world.playSound(playerEntity, blockPos, this.getPlaceSound(blockState2), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));
                    if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }

                    return ActionResult.success(world.isClient);
                }
            }
        }
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

    private BlockState placeFromNbt(BlockPos pos, World world, ItemStack stack, BlockState state) {
        BlockState blockState = state;
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null) {
            NbtCompound nbtCompound2 = nbtCompound.getCompound("BlockStateTag");
            StateManager<Block, BlockState> stateManager = state.getBlock().getStateManager();
            Iterator var9 = nbtCompound2.getKeys().iterator();

            while(var9.hasNext()) {
                String string = (String)var9.next();
                Property<?> property = stateManager.getProperty(string);
                if (property != null) {
                    String string2 = nbtCompound2.get(string).asString();
                    blockState = with(blockState, property, string2);
                }
            }
        }

        if (blockState != state) {
            world.setBlockState(pos, blockState, 2);
        }

        if (blockState.getBlock() instanceof LogPileBlock) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof LogPileBlockEntity) {
                ItemStack s = stack.copy();
                s.setCount(1);
                ((LogPileBlockEntity) be).setItem(s);
            }
        }

        return blockState;
    }

    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return property.parse(name).map((value) -> state.with(property, value)).orElse(state);
    }

}
