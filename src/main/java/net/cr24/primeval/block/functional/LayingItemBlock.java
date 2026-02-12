package net.cr24.primeval.block.functional;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.block.entity.LayingItemBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LayingItemBlock extends BaseEntityBlock {

    public static final MapCodec<LayingItemBlock> CODEC = simpleCodec(LayingItemBlock::new);

    public LayingItemBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        super.neighborChanged(state, world, pos, sourceBlock, wireOrientation, notify);
        if (!world.getBlockState(pos.below()).isSolidRender()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof LayingItemBlockEntity) {
                popResource(world, pos, ((LayingItemBlockEntity) blockEntity).getItem());
            }
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            blockEntity.setRemoved();
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LayingItemBlockEntity) {
            popResource(world, pos, ((LayingItemBlockEntity) blockEntity).getItem());
        }
        blockEntity.setRemoved();
        return state;
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.spawnAfterBreak(state, world, pos, tool, dropExperience);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LayingItemBlockEntity) {
            popResource(world, pos, ((LayingItemBlockEntity) blockEntity).getItem());
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else if (!player.isShiftKeyDown()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            player.addItem(((LayingItemBlockEntity) blockEntity).getItem());
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            if (!world.isClientSide()) world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.5f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, world, pos, player, hit);
        }
    }

    @Override
    protected void spawnDestroyParticles(Level world, Player player, BlockPos pos, BlockState state) {
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LayingItemBlockEntity(pos, state);
    }

}
