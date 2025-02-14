package net.cr24.primeval.block.functional;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.block.entity.AshPileBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class AshPileBlock extends BlockWithEntity {

    public static final MapCodec<AshPileBlock> CODEC = createCodec(AshPileBlock::new);

    public AshPileBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);
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
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AshPileBlockEntity) {
            for (ItemStack stack : ((AshPileBlockEntity) blockEntity).getItems()) {
                dropStack(world, pos, stack);
            }
        }
        world.getBlockEntity(pos).markRemoved();
        return state;
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
}
