package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.util.IntPoint2D;
import net.cr24.primeval.util.ShapesUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;


public class OreClusterFeature extends Feature<OreClusterFeatureConfig> {

    public OreClusterFeature(Codec<OreClusterFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreClusterFeatureConfig> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();
        RandomSource random = context.random();

        OreClusterFeatureConfig config = context.config();
        BlockStateProvider largeState = config.largeState();
        BlockStateProvider mediumState = config.mediumState();
        BlockStateProvider smallState = config.smallState();
        int radius = config.radius().sample(random);
        int height = config.height().sample(random);
        float density = config.density().sample(random);
        float richness = config.richness().sample(random);

        IntPoint2D[] circleCoords = ShapesUtil.getCoordinatesInCircle(radius);
        float oreRichness;
        for (IntPoint2D point : circleCoords) { // for each height step
            for (int y = 0; y < height; y++) { // for each coord in circle
                if (random.nextFloat() < density) { // density check
                    oreRichness = (richness + ((random.nextFloat()*2.0f)-1.0f)*richness);
                    if (oreRichness > 0.75) {
                        setBlockIfAble(structureWorldAccess, point.getOffsetBlockPos(blockPos).above(y), largeState, random);
                    } else if (oreRichness > 0.3) {
                        setBlockIfAble(structureWorldAccess, point.getOffsetBlockPos(blockPos).above(y), mediumState, random);
                    } else {
                        setBlockIfAble(structureWorldAccess, point.getOffsetBlockPos(blockPos).above(y), smallState, random);
                    }
                }
            }
        }

        return true;
    }

    private boolean setBlockIfAble(WorldGenLevel structureWorldAccess, BlockPos blockPos, BlockStateProvider stateProvider, RandomSource random) {
        if (structureWorldAccess.getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
            structureWorldAccess.setBlock(blockPos, stateProvider.getState(random, blockPos), 4);
            return true;
        } else {
            return false;
        }
    }
}
