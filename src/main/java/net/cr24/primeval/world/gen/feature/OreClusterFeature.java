package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.BlockPileFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Random;

public class OreClusterFeature extends Feature<OreClusterFeatureConfig> {

    public OreClusterFeature(Codec<OreClusterFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<OreClusterFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        OreClusterFeatureConfig oreClusterFeatureConfig = context.getConfig();
        for (int xOffset = 0; xOffset < 4 ; xOffset++) {
            setBlockIfAble(structureWorldAccess, blockPos.north(xOffset), oreClusterFeatureConfig.stateProvider_large, random);
        }
        for (int xOffset = 0; xOffset < 4 ; xOffset++) {
            setBlockIfAble(structureWorldAccess, blockPos.up().north(xOffset), oreClusterFeatureConfig.stateProvider_medium, random);
        }
        for (int xOffset = 0; xOffset < 4 ; xOffset++) {
            setBlockIfAble(structureWorldAccess, blockPos.up(2).north(xOffset), oreClusterFeatureConfig.stateProvider_small, random);
        }
        return false;
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
