package net.cr24.primeval.block;

import net.cr24.primeval.block.entity.AshPileBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AshPileBlock extends BlockWithEntity implements Clearable {

    protected AshPileBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        if (!PitKilnBlock.isSoilSurrounded(world, pos)) { // if not surrounded properly
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AshPileBlockEntity) {
                for (ItemStack stack : ((AshPileBlockEntity) blockEntity).getItems()) {
                    dropStack(world, pos, stack);
                }
                world.getBlockEntity(pos).markRemoved();
            }

            world.breakBlock(pos, true);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AshPileBlockEntity) {
            for (ItemStack stack : ((AshPileBlockEntity) blockEntity).getItems()) {
                dropStack(world, pos, stack);
            }
        }
        world.getBlockEntity(pos).markRemoved();
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    }


    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AshPileBlockEntity(pos, state);
    }

    @Override
    public void clear() {

    }
}
