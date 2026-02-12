package net.cr24.primeval.block.functional;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.block.entity.AshPileBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AshPileBlock extends BaseEntityBlock {

    public static final MapCodec<AshPileBlock> CODEC = simpleCodec(AshPileBlock::new);

    public AshPileBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        super.neighborChanged(state, world, pos, sourceBlock, wireOrientation, notify);
        if (!PitKilnBlock.isSoilSurrounded(world, pos)) { // if not surrounded properly
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AshPileBlockEntity) {
                for (ItemStack stack : ((AshPileBlockEntity) blockEntity).getItems()) {
                    popResource(world, pos, stack);
                }
                world.getBlockEntity(pos).setRemoved();
            }

            world.destroyBlock(pos, true);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AshPileBlockEntity) {
            for (ItemStack stack : ((AshPileBlockEntity) blockEntity).getItems()) {
                popResource(world, pos, stack);
            }
        }
        world.getBlockEntity(pos).setRemoved();
        return state;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
    }


    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AshPileBlockEntity(pos, state);
    }
}
