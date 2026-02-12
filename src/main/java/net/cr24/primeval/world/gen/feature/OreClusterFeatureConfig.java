package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

public record OreClusterFeatureConfig(BlockStateProvider largeState,
                                      BlockStateProvider mediumState,
                                      BlockStateProvider smallState,
                                      IntProvider radius,
                                      IntProvider height,
                                      FloatProvider density,
                                      FloatProvider richness) implements FeatureConfiguration {

    /*
     * Ore Cluster Feature Configuration:
     * large_ore    :   Blockstate for the large ore
     * medium_ore   :   Blockstate for the medium ore
     * small_ore    :   Blockstate for the small ore
     * radius       :   Radius of the ore cluster
     * height       :   Height of the ore cluster
     * density      :   Density of the ore block, IE. how many ores are generated, 0.0 = no ores, 1.0 = only ores
     * richness     :   Changes the ratio of large, medium and small ores, 0.0 = mostly small, 1.0 = mostly large
     */
    public static final Codec<OreClusterFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                BlockStateProvider.CODEC.fieldOf("large_ore").forGetter(OreClusterFeatureConfig::largeState),
                BlockStateProvider.CODEC.fieldOf("medium_ore").forGetter(OreClusterFeatureConfig::mediumState),
                BlockStateProvider.CODEC.fieldOf("small_ore").forGetter(OreClusterFeatureConfig::smallState),
                IntProvider.codec(0, 16).fieldOf("radius").forGetter(OreClusterFeatureConfig::radius),
                IntProvider.codec(0, 16).fieldOf("height").forGetter(OreClusterFeatureConfig::height),
                FloatProvider.codec(0.0f, 1.0f).fieldOf("density").forGetter(OreClusterFeatureConfig::density),
                FloatProvider.codec(0.0f, 1.0f).fieldOf("richness").forGetter(OreClusterFeatureConfig::richness)
        ).apply(instance, OreClusterFeatureConfig::new);
    });

    public OreClusterFeatureConfig(PrimevalBlocks.OreBlockSet ore, IntProvider radius, IntProvider height, FloatProvider density, FloatProvider richness) {
        this(SimpleStateProvider.simple(ore.large()),
                SimpleStateProvider.simple(ore.medium()),
                SimpleStateProvider.simple(ore.small()),
                radius, height, density, richness);
    }
}
