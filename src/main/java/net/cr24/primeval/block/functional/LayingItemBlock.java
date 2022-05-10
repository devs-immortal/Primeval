package net.cr24.primeval.block.functional;

import net.cr24.primeval.block.entity.AshPileBlockEntity;
import net.cr24.primeval.block.entity.LayingItemBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

public class LayingItemBlock extends BlockWithEntity {
    public LayingItemBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        if (!world.getBlockState(pos.down()).isOpaqueFullCube(world, pos)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof LayingItemBlockEntity) {
                dropStack(world, pos, ((LayingItemBlockEntity) blockEntity).getItem());
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            blockEntity.markRemoved();
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LayingItemBlockEntity) {
            dropStack(world, pos, ((LayingItemBlockEntity) blockEntity).getItem());
        }
        blockEntity.markRemoved();
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        } else if (!player.isSneaking()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            player.giveItemStack(((LayingItemBlockEntity) blockEntity).getItem());
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            blockEntity.markRemoved();
            return ActionResult.SUCCESS;
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
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
