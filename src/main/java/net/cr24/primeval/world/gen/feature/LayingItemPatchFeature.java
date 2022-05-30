package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.entity.LayingItemBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class LayingItemPatchFeature extends Feature<LayingItemPatchFeatureConfig> {
    public LayingItemPatchFeature(Codec<LayingItemPatchFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<LayingItemPatchFeatureConfig> context) {
        LayingItemPatchFeatureConfig randomPatchFeatureConfig = context.getConfig();
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int j = randomPatchFeatureConfig.xzSpread().get(random) + 1;
        int k = randomPatchFeatureConfig.ySpread().get(random) + 1;
        Item item1 = randomPatchFeatureConfig.itemSource().getItem();
        Item item2 = randomPatchFeatureConfig.secondaryItemSource().getItem();
        int t = randomPatchFeatureConfig.tries().get(random);
        for (int l = 0; l < t; ++l) {
            mutable.set(blockPos, random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j));
            if (!trySetBlock(structureWorldAccess, item1, item2, random, mutable)) continue;
            ++i;
        }
        return i > 0;
    }

    protected boolean trySetBlock(WorldAccess world, Item item1, Item item2, Random random, BlockPos.Mutable pos) {
        if (world.getBlockState(pos).isAir() && world.getBlockState(pos.down()).isOpaqueFullCube(world, pos)) {
            world.setBlockState(pos, PrimevalBlocks.LAYING_ITEM.getDefaultState(), 4);
            LayingItemBlockEntity ent = (LayingItemBlockEntity) world.getBlockEntity(pos);
            if (ent == null) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
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
