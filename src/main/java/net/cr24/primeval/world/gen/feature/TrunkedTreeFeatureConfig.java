package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record TrunkedTreeFeatureConfig(BlockStateProvider saplingState, IntProvider tickTries) implements FeatureConfiguration {

    public static final Codec<TrunkedTreeFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                BlockStateProvider.CODEC.fieldOf("sapling").forGetter(TrunkedTreeFeatureConfig::saplingState),
                IntProvider.CODEC.fieldOf("tick_tries").forGetter(TrunkedTreeFeatureConfig::tickTries)
        ).apply(instance, TrunkedTreeFeatureConfig::new);
    });
}
