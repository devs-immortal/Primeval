package net.cr24.primeval.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class OreClusterFeatureConfig implements FeatureConfig {
    public static final Codec<OreClusterFeatureConfig> CODEC;
    public final BlockStateProvider stateProvider_large; // Large Ore
    public final BlockStateProvider stateProvider_medium; // Medium Ore
    public final BlockStateProvider stateProvider_small; // Small Ore


    public OreClusterFeatureConfig(BlockStateProvider stateProvider_large, BlockStateProvider stateProvider_medium, BlockStateProvider stateProvider_small) {
        this.stateProvider_large = stateProvider_large;
        this.stateProvider_medium = stateProvider_medium;
        this.stateProvider_small = stateProvider_small;
    }


    static {
        CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(BlockStateProvider.TYPE_CODEC.fieldOf("large_ore_provider").forGetter((oreClusterFeatureConfig) -> {
                return oreClusterFeatureConfig.stateProvider_large;
            }), BlockStateProvider.TYPE_CODEC.fieldOf("medium_ore_provider").forGetter((oreClusterFeatureConfig) -> {
                return oreClusterFeatureConfig.stateProvider_medium;
            }), BlockStateProvider.TYPE_CODEC.fieldOf("small_ore_provider").forGetter((oreClusterFeatureConfig) -> {
                return oreClusterFeatureConfig.stateProvider_small;
            })).apply(instance, OreClusterFeatureConfig::new);
        });
    }
}
