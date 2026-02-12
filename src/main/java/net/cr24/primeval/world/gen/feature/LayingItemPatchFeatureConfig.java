package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record LayingItemPatchFeatureConfig(IntProvider tries, IntProvider xzSpread, IntProvider ySpread, ItemStack itemSource, ItemStack secondaryItemSource) implements FeatureConfiguration {

    public static final Codec<LayingItemPatchFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                IntProvider.CODEC.fieldOf("tries").forGetter(LayingItemPatchFeatureConfig::tries),
                IntProvider.CODEC.fieldOf("xzSpread").forGetter(LayingItemPatchFeatureConfig::xzSpread),
                IntProvider.CODEC.fieldOf("y_spread").forGetter(LayingItemPatchFeatureConfig::ySpread),
                ItemStack.CODEC.fieldOf("item1").forGetter(LayingItemPatchFeatureConfig::itemSource),
                ItemStack.CODEC.fieldOf("item2").forGetter(LayingItemPatchFeatureConfig::secondaryItemSource)
        ).apply(instance, LayingItemPatchFeatureConfig::new);
    });

}
