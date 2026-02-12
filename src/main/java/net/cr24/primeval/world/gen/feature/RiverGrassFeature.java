package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RiverGrassFeature extends Feature<NoneFeatureConfiguration> {
    public RiverGrassFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        boolean bl = false;
        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = structureWorldAccess.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
        BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
        if (structureWorldAccess.getBlockState(blockPos2).is(Blocks.WATER)) {
            BlockState blockState = PrimevalBlocks.RIVER_GRASS.defaultBlockState();
            if (blockState.canSurvive(structureWorldAccess, blockPos2)) {
                structureWorldAccess.setBlock(blockPos2, blockState, 2);
                bl = true;
            }
        }
        return bl;
    }
}
