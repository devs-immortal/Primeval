package net.cr24.primeval.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.entity.CollapsingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * A block that will collapse and fall both:
 *   directly down
 *   1 block orthogonally adjacent to itself
 */
public class CollapsibleBlock extends FallingBlock {

    public static final MapCodec<CollapsibleBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fall_block").forGetter((block) -> block.fallBlock),
            propertiesCodec()
    ).apply(instance, CollapsibleBlock::new));

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    public Block fallBlock;

    public CollapsibleBlock(Block block, Properties settings) {
        super(settings);
        this.fallBlock = block;
    }

    public CollapsibleBlock(Properties settings) {
        super(settings);
        this.fallBlock = this;
    }

    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!(neighborState.getBlock() instanceof LiquidBlock)) {
            tickView.scheduleTick(pos, this, this.getDelayAfterPlace());
            return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
        }
        return state;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        collapse(world, pos, random, 0, false);
    }

    /*
     * Is attempted on a scheduled tick.
     * Controls logic for how this block is expected
     * fall based on blocks around it
     */
    protected boolean collapse(Level world, BlockPos pos, RandomSource random, int step, boolean force) {
        if (!this.supported(world, pos, random)) {
            if (isFree(world.getBlockState(pos.below()))) {
                world.playSound(null, pos, world.getBlockState(pos).getSoundType().getBreakSound(), SoundSource.BLOCKS, 0.5F, 0.6F + world.random.nextFloat() * 0.4F);
                return world.addFreshEntity(createFallingBlockEntity(world, pos, pos));
            } else {
                List<BlockPos> neighborPositions = Arrays.asList(pos.north(), pos.east(), pos.south(), pos.west());
                Collections.shuffle(neighborPositions);
                for (BlockPos dest : neighborPositions) {
                    if (isFree(world.getBlockState(dest)) && isFree(world.getBlockState(dest.below()))) {
                        world.playSound(null, pos, world.getBlockState(pos).getSoundType().getBreakSound(), SoundSource.BLOCKS, 0.5F, 0.6F + world.random.nextFloat() * 0.4F);
                        return world.addFreshEntity(createFallingBlockEntity(world, dest, pos));
                    }
                }
            }
        }
        return false;
    }

    protected boolean supported(Level world, BlockPos pos, RandomSource random) {
        return false;
    }

    public static boolean isFree(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || !state.isSolid();
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
    }

    @Override
    public int getDustColor(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getMapColor(world, pos).col;
    }

    protected FallingBlockEntity createFallingBlockEntity(Level world, BlockPos fallPos, BlockPos origin) {
        BlockState fallingBlockState = fallBlock == null ? world.getBlockState(origin) : fallBlock.defaultBlockState();
        if (fallPos.equals(origin)) {
            return new CollapsingBlockEntity(world, (double)fallPos.getX() + 0.5D, fallPos.getY(), (double)fallPos.getZ() + 0.5D, fallingBlockState, origin, world.getBlockState(origin));
        } else {
            return new CollapsingBlockEntity(world, (double)fallPos.getX() + 0.5D, (double)fallPos.getY() - 0.5D, (double)fallPos.getZ() + 0.5D, fallingBlockState, origin, world.getBlockState(origin));
        }
    }
}
