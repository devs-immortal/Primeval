package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.GrowingSaplingBlock;
import net.cr24.primeval.block.LeafBlock;
import net.cr24.primeval.block.TrunkBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.List;
public class TrunkedTreeFeature extends Feature<TrunkedTreeFeatureConfig> {
    public TrunkedTreeFeature(Codec<TrunkedTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<TrunkedTreeFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();

        TrunkedTreeFeatureConfig config = context.getConfig();
        BlockState saplingState = config.saplingState().getBlockState(random, blockPos);
        int tries = config.tickTries().get(random);

        GrowingSaplingBlock saplingBlock = (GrowingSaplingBlock) saplingState.getBlock();

        structureWorldAccess.setBlockState(blockPos, saplingState, 4);
        List<BlockPos> posList = saplingBlock.trunker.growSapling(structureWorldAccess, blockPos);
        int step = 0;
        while (!posList.isEmpty() && step < tries) {
            int randomIndex = random.nextInt(posList.size());
            BlockPos tickPos = posList.get(randomIndex);
            posList.remove(randomIndex);
            BlockState tickState = structureWorldAccess.getBlockState(tickPos);
            if (tickState.getBlock() instanceof TrunkBlock) {
                ArrayList<Direction> dirs = new ArrayList<>();
                for (Direction d : TrunkBlock.DIRECTION_MAP.keySet()) {
                    if (tickState.get(TrunkBlock.DIRECTION_MAP.get(d)) && structureWorldAccess.getBlockState(tickPos.offset(d, 1)).getBlock() instanceof LeafBlock) {
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
