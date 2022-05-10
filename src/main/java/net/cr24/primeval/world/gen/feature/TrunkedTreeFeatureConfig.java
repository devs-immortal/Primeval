package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record TrunkedTreeFeatureConfig(BlockStateProvider saplingState, IntProvider tickTries) implements FeatureConfig {

    public static final Codec<TrunkedTreeFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                BlockStateProvider.TYPE_CODEC.fieldOf("sapling").forGetter(TrunkedTreeFeatureConfig::saplingState),
                IntProvider.VALUE_CODEC.fieldOf("tick_tries").forGetter(TrunkedTreeFeatureConfig::tickTries)
        ).apply(instance, TrunkedTreeFeatureConfig::new);
    });
}
