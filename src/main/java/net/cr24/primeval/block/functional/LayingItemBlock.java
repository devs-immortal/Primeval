package net.cr24.primeval.block.functional;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.block.entity.LayingItemBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class LayingItemBlock extends BlockWithEntity {

    public static final MapCodec<LayingItemBlock> CODEC = createCodec(LayingItemBlock::new);

    public LayingItemBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);
        if (!world.getBlockState(pos.down()).isOpaqueFullCube()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof LayingItemBlockEntity) {
                dropStack(world, pos, ((LayingItemBlockEntity) blockEntity).getItem());
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            blockEntity.markRemoved();
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LayingItemBlockEntity) {
            dropStack(world, pos, ((LayingItemBlockEntity) blockEntity).getItem());
        }
        blockEntity.markRemoved();
        return state;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        } else if (!player.isSneaking()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            player.giveItemStack(((LayingItemBlockEntity) blockEntity).getItem());
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            if (!world.isClient) world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.5f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            return ActionResult.SUCCESS;
        } else {
            return super.onUse(state, world, pos, player, hit);
        }
    }

    @Override
    protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LayingItemBlockEntity(pos, state);
    }

}
