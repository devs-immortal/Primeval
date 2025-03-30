package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public record LayingItemPatchFeatureConfig(IntProvider tries, IntProvider xzSpread, IntProvider ySpread, ItemStack itemSource, ItemStack secondaryItemSource) implements FeatureConfig {

    public static final Codec<LayingItemPatchFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                IntProvider.VALUE_CODEC.fieldOf("tries").forGetter(LayingItemPatchFeatureConfig::tries),
                IntProvider.VALUE_CODEC.fieldOf("xzSpread").forGetter(LayingItemPatchFeatureConfig::xzSpread),
                IntProvider.VALUE_CODEC.fieldOf("y_spread").forGetter(LayingItemPatchFeatureConfig::ySpread),
                ItemStack.CODEC.fieldOf("item1").forGetter(LayingItemPatchFeatureConfig::itemSource),
                ItemStack.CODEC.fieldOf("item2").forGetter(LayingItemPatchFeatureConfig::secondaryItemSource)
        ).apply(instance, LayingItemPatchFeatureConfig::new);
    });

}
