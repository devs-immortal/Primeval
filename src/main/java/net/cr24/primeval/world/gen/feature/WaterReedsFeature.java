package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.plant.ReedsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class WaterReedsFeature extends Feature<DefaultFeatureConfig> {
    public WaterReedsFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        boolean bl = false;
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
        BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
        if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER) && structureWorldAccess.getBlockState(blockPos2.up()).isAir()) {
            BlockState blockState = PrimevalBlocks.REEDS.getDefaultState();
            if (blockState.canPlaceAt(structureWorldAccess, blockPos2)) {
                structureWorldAccess.setBlockState(blockPos2, blockState.with(ReedsBlock.WATERLOGGED, true).with(ReedsBlock.CAP, false), 2);
                structureWorldAccess.setBlockState(blockPos2.up(), blockState.with(ReedsBlock.CAP, false).with(ReedsBlock.AGE, 1), 2);
                structureWorldAccess.setBlockState(blockPos2.up(2), blockState.with(ReedsBlock.CAP, true).with(ReedsBlock.AGE, 2), 2);
                bl = true;
            }
        }
        return bl;
    }
}
