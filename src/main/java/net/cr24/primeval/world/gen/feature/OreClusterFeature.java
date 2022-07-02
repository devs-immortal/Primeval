package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.util.IntPoint2D;
import net.cr24.primeval.util.ShapesUtil;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;


public class OreClusterFeature extends Feature<OreClusterFeatureConfig> {

    public OreClusterFeature(Codec<OreClusterFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<OreClusterFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();

        OreClusterFeatureConfig config = context.getConfig();
        BlockStateProvider largeState = config.largeState();
        BlockStateProvider mediumState = config.mediumState();
        BlockStateProvider smallState = config.smallState();
        int radius = config.radius().get(random);
        int height = config.height().get(random);
        float density = config.density().get(random);
        float richness = config.richness().get(random);

        IntPoint2D[] circleCoords = ShapesUtil.getCoordinatesInCircle(radius);
        float oreRichness;
        for (IntPoint2D point : circleCoords) { // for each height step
            for (int y = 0; y < height; y++) { // for each coord in circle
                if (random.nextFloat() < density) { // density check
                    oreRichness = (richness + ((random.nextFloat()*2.0f)-1.0f)*richness);
                    if (oreRichness > 0.75) {
                        setBlockIfAble(structureWorldAccess, point.getOffsetBlockPos(blockPos).up(y), largeState, random);
                    } else if (oreRichness > 0.3) {
                        setBlockIfAble(structureWorldAccess, point.getOffsetBlockPos(blockPos).up(y), mediumState, random);
                    } else {
                        setBlockIfAble(structureWorldAccess, point.getOffsetBlockPos(blockPos).up(y), smallState, random);
                    }
                }
            }
        }

        return true;
    }

    private boolean setBlockIfAble(StructureWorldAccess structureWorldAccess, BlockPos blockPos, BlockStateProvider stateProvider, Random random) {
        if (structureWorldAccess.getBlockState(blockPos).isIn(BlockTags.BASE_STONE_OVERWORLD)) {
            structureWorldAccess.setBlockState(blockPos, stateProvider.getBlockState(random, blockPos), 4);
            return true;
        } else {
            return false;
        }
    }
}
