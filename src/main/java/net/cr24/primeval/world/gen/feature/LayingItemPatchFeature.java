package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.entity.LayingItemBlockEntity;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;


public class LayingItemPatchFeature extends Feature<LayingItemPatchFeatureConfig> {
    public LayingItemPatchFeature(Codec<LayingItemPatchFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<LayingItemPatchFeatureConfig> context) {
        LayingItemPatchFeatureConfig randomPatchFeatureConfig = context.config();
        RandomSource random = context.random();
        BlockPos blockPos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();
        int i = 0;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int j = randomPatchFeatureConfig.xzSpread().sample(random) + 1;
        int k = randomPatchFeatureConfig.ySpread().sample(random) + 1;
        Item item1 = randomPatchFeatureConfig.itemSource().getItem();
        Item item2 = randomPatchFeatureConfig.secondaryItemSource().getItem();
        int t = randomPatchFeatureConfig.tries().sample(random);
        for (int l = 0; l < t; ++l) {
            mutable.setWithOffset(blockPos, random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j));
            if (!trySetBlock(structureWorldAccess, item1, item2, random, mutable)) continue;
            ++i;
        }
        return i > 0;
    }

    protected boolean trySetBlock(LevelAccessor world, Item item1, Item item2, RandomSource random, BlockPos.MutableBlockPos pos) {
        if (world.getBlockState(pos).isAir() && world.getBlockState(pos.below()).isSolidRender()) {
            world.setBlock(pos, PrimevalBlocks.LAYING_ITEM.defaultBlockState(), 4);
            LayingItemBlockEntity ent = (LayingItemBlockEntity) world.getBlockEntity(pos);
            if (ent == null) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
                return false;
            }
            if (random.nextInt(5) < 2) {
                ent.setItem(new ItemStack(item2));
            } else {
                ent.setItem(new ItemStack(item1));
            }
            return true;
        }
        return false;
    }
}
