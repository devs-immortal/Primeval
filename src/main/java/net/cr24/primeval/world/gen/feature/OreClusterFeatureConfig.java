package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.DiskFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record OreClusterFeatureConfig(BlockStateProvider largeState,
                                      BlockStateProvider mediumState,
                                      BlockStateProvider smallState,
                                      IntProvider radius,
                                      IntProvider height,
                                      FloatProvider density,
                                      FloatProvider richness) implements FeatureConfig {
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
                BlockStateProvider.TYPE_CODEC.fieldOf("large_ore").forGetter(OreClusterFeatureConfig::largeState),
                BlockStateProvider.TYPE_CODEC.fieldOf("medium_ore").forGetter(OreClusterFeatureConfig::mediumState),
                BlockStateProvider.TYPE_CODEC.fieldOf("small_ore").forGetter(OreClusterFeatureConfig::smallState),
                IntProvider.createValidatingCodec(0, 16).fieldOf("radius").forGetter(OreClusterFeatureConfig::radius),
                IntProvider.createValidatingCodec(0, 16).fieldOf("height").forGetter(OreClusterFeatureConfig::height),
                FloatProvider.createValidatedCodec(0.0f, 1.0f).fieldOf("density").forGetter(OreClusterFeatureConfig::density),
                FloatProvider.createValidatedCodec(0.0f, 1.0f).fieldOf("richness").forGetter(OreClusterFeatureConfig::richness)
        ).apply(instance, OreClusterFeatureConfig::new);
    });

}
