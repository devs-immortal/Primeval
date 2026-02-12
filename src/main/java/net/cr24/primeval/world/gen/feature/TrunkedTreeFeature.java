package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.plant.GrowingSaplingBlock;
import net.cr24.primeval.block.plant.LeafBlock;
import net.cr24.primeval.block.plant.TrunkBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import java.util.ArrayList;
import java.util.List;
public class TrunkedTreeFeature extends Feature<TrunkedTreeFeatureConfig> {
    public TrunkedTreeFeature(Codec<TrunkedTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TrunkedTreeFeatureConfig> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();
        RandomSource random = context.random();

        TrunkedTreeFeatureConfig config = context.config();
        BlockState saplingState = config.saplingState().getState(random, blockPos);
        int tries = config.tickTries().sample(random);

        GrowingSaplingBlock saplingBlock = (GrowingSaplingBlock) saplingState.getBlock();

        structureWorldAccess.setBlock(blockPos, saplingState, 4);
        List<BlockPos> posList = saplingBlock.trunker.growSapling(structureWorldAccess, blockPos, random);
        int step = 0;
        while (!posList.isEmpty() && step < tries) {
            int randomIndex = random.nextInt(posList.size());
            BlockPos tickPos = posList.get(randomIndex);
            posList.remove(randomIndex);
            BlockState tickState = structureWorldAccess.getBlockState(tickPos);
            if (tickState.getBlock() instanceof TrunkBlock) {
                ArrayList<Direction> dirs = new ArrayList<>();
                for (Direction d : TrunkBlock.DIRECTION_MAP.keySet()) {
                    if (tickState.getValue(TrunkBlock.DIRECTION_MAP.get(d)) && structureWorldAccess.getBlockState(tickPos.relative(d, 1)).getBlock() instanceof LeafBlock) {
                        dirs.add(d);
                    }
                }
                List<BlockPos> newPos = ((TrunkBlock) tickState.getBlock()).trunker.tickTrunk(tickState, structureWorldAccess, tickPos, random, dirs.toArray(new Direction[dirs.size()]));
                posList.addAll(newPos);
            }
            step++;
        }

        return true;
    }
}
