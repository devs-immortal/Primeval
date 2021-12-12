package net.cr24.primeval.world.gen;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.world.PrimevalWorld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class NaturalTrunkPlacer extends TrunkPlacer {

    public static final Codec<NaturalTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return fillTrunkPlacerFields(instance).apply(instance, NaturalTrunkPlacer::new);
    });

    public NaturalTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return PrimevalWorld.NATURAL_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        setToDirt(world, replacer, random, startPos.down(), config);

        ArrayList<FoliagePlacer.TreeNode> leafNodes = new ArrayList<>();

        for (int branchCount = 0; branchCount < 3; branchCount++) { // for each branch

            int heightDisplacement = height - random.nextInt(this.firstRandomHeight);
            double offsetNW = (2d*random.nextDouble())-1d;
            double offsetES = (2d*random.nextDouble())-1d;
            double heightOffset = (((double)height)/3d) + random.nextInt(this.firstRandomHeight);
            double multiplier = (heightOffset*2d) + random.nextInt(this.secondRandomHeight);
            int offset = 0;
            int lastOffset;

            for (int y = 0; y < heightDisplacement; y++) { // for each y value in the height of the tree

                lastOffset = offset;
                offset = getTrunkOffset(heightOffset, multiplier, y);

                if (lastOffset == offset) {
                    this.getAndSetState(world, replacer, random, startPos.up(y).north((int) (offsetNW*lastOffset)).east((int) (offsetES*lastOffset)), config);
                } else {
                    for (int i = lastOffset; i < offset; i++) {
                        this.getAndSetState(world, replacer, random, startPos.up(y).north((int) (offsetNW*(i+1))).east((int) (offsetES*(i+1))), config);
                    }
                }
            }
            leafNodes.add(new FoliagePlacer.TreeNode(startPos.up(heightDisplacement-1).north((int) (offsetNW*getTrunkOffset(heightOffset, multiplier, heightDisplacement-1))).east((int) (offsetES*getTrunkOffset(heightOffset, multiplier, heightDisplacement-1))), 0, false));
        }

        for (int branchCount = 0; branchCount < 2; branchCount++) { // for each branch

            int heightDisplacement = height - random.nextInt(this.firstRandomHeight) - 2;
            double offsetNW = (2d*random.nextDouble())-1d;
            double offsetES = (2d*random.nextDouble())-1d;
            double heightOffset = (((double)height)/3d) + random.nextInt(this.firstRandomHeight);
            double multiplier = (heightOffset*1.5d) + random.nextInt(this.secondRandomHeight);
            int offset = 0;
            int lastOffset;

            for (int y = 0; y < heightDisplacement; y++) { // for each y value in the height of the tree

                lastOffset = offset;
                offset = getTrunkOffset(heightOffset, multiplier, y);

                if (lastOffset == offset) {
                    this.getAndSetState(world, replacer, random, startPos.up(y).north((int) (offsetNW*lastOffset)).east((int) (offsetES*lastOffset)), config);
                } else {
                    for (int i = lastOffset; i < offset; i++) {
                        this.getAndSetState(world, replacer, random, startPos.up(y).north((int) (offsetNW*(i+1))).east((int) (offsetES*(i+1))), config);
                    }
                }
            }
            leafNodes.add(new FoliagePlacer.TreeNode(startPos.up(heightDisplacement-1).north((int) (offsetNW*getTrunkOffset(heightOffset, multiplier, heightDisplacement-1))).east((int) (offsetES*getTrunkOffset(heightOffset, multiplier, heightDisplacement-1))), 0, false));
        }

        return leafNodes;
    }

    protected int getTrunkOffset(double heightOffset, double multiplier, double yHeight) {
        return (int) Math.round(Math.pow(10d, ((yHeight - heightOffset)/multiplier) ));
    }
}
