package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class RiverGrassFeature extends Feature<DefaultFeatureConfig> {
    public RiverGrassFeature(Codec<DefaultFeatureConfig> codec) {
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
        if (structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER)) {
            BlockState blockState = PrimevalBlocks.RIVER_GRASS.getDefaultState();
            if (blockState.canPlaceAt(structureWorldAccess, blockPos2)) {
                structureWorldAccess.setBlockState(blockPos2, blockState, 2);
                bl = true;
            }
        }
        return bl;
    }
}
