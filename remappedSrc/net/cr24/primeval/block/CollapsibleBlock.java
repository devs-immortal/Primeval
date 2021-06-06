package net.cr24.primeval.block;

import com.google.common.collect.Lists;
import net.cr24.primeval.entity.CollapsingBlockEntity;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.*;

public class CollapsibleBlock extends FallingBlock {

    public static float stability;

    public CollapsibleBlock(Settings settings) {
        super(settings);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        collapse(world, pos, random);
    }

    protected boolean collapse(World world, BlockPos pos, Random random) {
        if (!this.supported(world, pos, random)) {
            if (canFallThrough(world.getBlockState(pos.down()))) {
                return world.spawnEntity(getFallingBlockEntity(world, pos, pos));
            } else {
                List<BlockPos> neighborPositions = Arrays.asList(pos.north(), pos.east(), pos.south(), pos.west());
                Collections.shuffle(neighborPositions);
                for (BlockPos dest : neighborPositions) {
                    if (canFallThrough(world.getBlockState(dest)) && canFallThrough(world.getBlockState(dest.down()))) {
                        return world.spawnEntity(getFallingBlockEntity(world, dest, pos));
                    }
                }
            }
        }
        return false;
    }

    protected boolean supported(World world, BlockPos pos, Random random) {
        return false;
    }

    public static boolean canFallThrough(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || !material.isSolid();
    }

    private FallingBlockEntity getFallingBlockEntity(World world, BlockPos pos, BlockPos origin) {
        if (pos.equals(origin)) {
            return new CollapsingBlockEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, world.getBlockState(origin), origin);
        } else {
            return new CollapsingBlockEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() - 0.5D, (double)pos.getZ() + 0.5D, world.getBlockState(origin), origin);
        }
    }
}
