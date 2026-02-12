package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.plant.SpreadingMossBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import java.util.List;

public class MossFeature extends Feature<MultifaceGrowthConfiguration> {
    public MossFeature(Codec<MultifaceGrowthConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MultifaceGrowthConfiguration> context) {
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        RandomSource random = context.random();
        MultifaceGrowthConfiguration glowLichenFeatureConfig = context.config();
        if (!isAirOrWater(structureWorldAccess.getBlockState(blockPos))) {
            return false;
        }
        List<Direction> list = shuffleDirections(glowLichenFeatureConfig, random);
        if (generate(structureWorldAccess, blockPos, structureWorldAccess.getBlockState(blockPos), glowLichenFeatureConfig, random, list)) {
            return true;
        }
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        block0: for (Direction direction : list) {
            mutable.set(blockPos);
            List<Direction> list2 = shuffleDirections(glowLichenFeatureConfig, random, direction.getOpposite());
            for (int i = 0; i < glowLichenFeatureConfig.searchRange; ++i) {
                mutable.setWithOffset(blockPos, direction);
                BlockState blockState = structureWorldAccess.getBlockState(mutable);
                if (!isAirOrWater(blockState) && !blockState.is(PrimevalBlocks.MOSS)) continue block0;
                if (!generate(structureWorldAccess, mutable, blockState, glowLichenFeatureConfig, random, list2)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean generate(WorldGenLevel world, BlockPos pos, BlockState state, MultifaceGrowthConfiguration config, RandomSource random, List<Direction> directions) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (Direction direction : directions) {
            BlockState blockState = world.getBlockState(mutable.setWithOffset(pos, direction));
            if (!blockState.is(config.canBePlacedOn)) continue;
            SpreadingMossBlock mossBlock = (SpreadingMossBlock)PrimevalBlocks.MOSS;
            BlockState blockState2 = mossBlock.getStateForPlacement(state, world, pos, direction);
            if (blockState2 == null) {
                return false;
            }
            world.setBlock(pos, blockState2, Block.UPDATE_ALL);
            world.getChunk(pos).markPosForPostprocessing(pos);
            if (random.nextFloat() < config.chanceOfSpreading) {
                mossBlock.grower.spreadFromFaceTowardRandomDirection(blockState2, world, pos, direction, random, true);
            }
            return true;
        }
        return false;
    }

    public static List<Direction> shuffleDirections(MultifaceGrowthConfiguration config, RandomSource random) {
        return config.getShuffledDirections(random);
    }

    public static List<Direction> shuffleDirections(MultifaceGrowthConfiguration config, RandomSource random, Direction excluded) {
        return config.getShuffledDirectionsExcept(random, excluded);
    }

    private static boolean isAirOrWater(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }
}
