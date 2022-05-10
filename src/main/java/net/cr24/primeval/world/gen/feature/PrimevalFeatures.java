package net.cr24.primeval.world.gen.feature;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.block.GrowingGrassBlock;
import net.cr24.primeval.block.PrimevalBlockTags;
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.blockpredicate.WouldSurviveBlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class PrimevalFeatures {

    /* FEATURE TYPES */
    public static final OreClusterFeature ORE_CLUSTER_FEATURE = registerFeature(PrimevalMain.getId("ore_cluster"), new OreClusterFeature(OreClusterFeatureConfig.CODEC));
    public static final TrunkedTreeFeature TRUNKED_TREE_FEATURE = registerFeature(PrimevalMain.getId("trunked_tree"), new TrunkedTreeFeature(TrunkedTreeFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(Identifier id, F f) {
        return Registry.register(Registry.FEATURE, id, f);
    }

    /* CONFIGURED FEATURES */
    // ORES
    private static final RegistryEntry<ConfiguredFeature<OreClusterFeatureConfig, ?>> CONFIGURED_NATIVE_COPPER_CLUSTER = register("ore_copper_native", ORE_CLUSTER_FEATURE, Configs.NATIVE_COPPER_ORE_CLUSTER);
    public static final RegistryEntry<PlacedFeature> NATIVE_COPPER_ORE_CLUSTER = register("ore_copper_native", CONFIGURED_NATIVE_COPPER_CLUSTER, RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), getHeightModifier(5,80), BiomePlacementModifier.of());

    private static final RegistryEntry<ConfiguredFeature<OreClusterFeatureConfig, ?>> CONFIGURED_MALACHITE_COPPER_CLUSTER = register("ore_copper_malachite", ORE_CLUSTER_FEATURE, Configs.MALACHITE_COPPER_ORE_CLUSTER);
    public static final RegistryEntry<PlacedFeature> MALACHITE_COPPER_ORE_CLUSTER = register("ore_copper_malachite", CONFIGURED_MALACHITE_COPPER_CLUSTER, RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), getHeightModifier(-20,120), BiomePlacementModifier.of());

    private static final RegistryEntry<ConfiguredFeature<OreClusterFeatureConfig, ?>> CONFIGURED_CASSITERITE_TIN_CLUSTER = register("ore_tin_cassiterite", ORE_CLUSTER_FEATURE, Configs.CASSITERITE_TIN_ORE_CLUSTER);
    public static final RegistryEntry<PlacedFeature> CASSITERITE_TIN_ORE_CLUSTER = register("ore_tin_cassiterite", CONFIGURED_CASSITERITE_TIN_CLUSTER, RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), getHeightModifier(30,100), BiomePlacementModifier.of());

    // BLOBS +
    private static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> CONFIGURED_DIRT_ORE_BLOBS = register("ore_dirt", Feature.ORE, Configs.DIRT_ORE_BLOBS);
    public static final RegistryEntry<PlacedFeature> DIRT_ORE_BLOBS = register("ore_dirt", CONFIGURED_DIRT_ORE_BLOBS, CountPlacementModifier.of(7), SquarePlacementModifier.of(), getHeightModifier(0,160), BiomePlacementModifier.of());

    private static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> CONFIGURED_GRAVEL_ORE_BLOBS = register("ore_gravel", Feature.ORE, Configs.GRAVEL_ORE_BLOBS);
    public static final RegistryEntry<PlacedFeature> GRAVEL_ORE_BLOBS = register("ore_gravel", CONFIGURED_GRAVEL_ORE_BLOBS, CountPlacementModifier.of(5), SquarePlacementModifier.of(), getHeightModifier(0,160), BiomePlacementModifier.of());

    // SURFACE DECORATION
    private static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> CONFIGURED_SHORT_GRASS_PATCH = register("patch_short_grass", Feature.RANDOM_PATCH, Configs.SHORT_GRASS_PATCH);
    private static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> CONFIGURED_MEDIUM_GRASS_PATCH = register("patch_medium_grass", Feature.RANDOM_PATCH, Configs.MEDIUM_GRASS_PATCH);
    private static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> CONFIGURED_TALL_GRASS_PATCH = register("patch_tall_grass", Feature.RANDOM_PATCH, Configs.TALL_GRASS_PATCH);

    public static final RegistryEntry<PlacedFeature> PLAINS_GRASS_PATCH = register("patch_medium_grass_plains", CONFIGURED_MEDIUM_GRASS_PATCH, NoiseThresholdCountPlacementModifier.of(-0.8, 3, 8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

    private static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> CONFIGURED_BUSH_PATCH = register("patch_bush", Feature.RANDOM_PATCH, Configs.BUSH_PATCH);
    public static final RegistryEntry<PlacedFeature> BUSH_PATCH = register("patch_bush", CONFIGURED_BUSH_PATCH, NoiseThresholdCountPlacementModifier.of(-0.8, 4, 5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());


    // TREES AND STUFF
    private static final RegistryEntry<ConfiguredFeature<TrunkedTreeFeatureConfig, ?>> CONFIGURED_OAK_TRUNKED_TREE = register("trunked_tree_oak", TRUNKED_TREE_FEATURE, Configs.OAK_TRUNKED_TREE);

    public static final RegistryEntry<PlacedFeature> PLAINS_OAK_TRUNKED_TREE = register("trunked_tree_oak_plains", CONFIGURED_OAK_TRUNKED_TREE, getWeightedCountPlacementModifier(57, 2, 1), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(PrimevalBlocks.OAK_SAPLING.getDefaultState(), Vec3i.ZERO)), BiomePlacementModifier.of());


    protected static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> register(String id, F feature, FC featureConfig) {
        return register(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, featureConfig));
    }

    @SuppressWarnings("unchecked")
    private static <V extends T, T> RegistryEntry<V> register(Registry<T> registry, String id, V value) {
        return (RegistryEntry<V>) BuiltinRegistries.add(registry, PrimevalMain.getId(id), value);
    }

    static RegistryEntry<PlacedFeature> register(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, PrimevalMain.getId(id), new PlacedFeature(RegistryEntry.upcast(feature), List.of(modifiers)));
    }

    private static HeightRangePlacementModifier getHeightModifier(int down, int up) {
        return HeightRangePlacementModifier.uniform(YOffset.fixed(down),YOffset.fixed(up));
    }

    private static CountPlacementModifier getWeightedCountPlacementModifier(int i, int j, int k) {
        return CountPlacementModifier.of(new WeightedListIntProvider(
                DataPool.<IntProvider>builder()
                        .add(ConstantIntProvider.create(0), i)
                        .add(ConstantIntProvider.create(1), j)
                        .add(ConstantIntProvider.create(2), k)
                .build())
        );
    }

    protected static class Configs {

        // Ores
        public static final OreClusterFeatureConfig NATIVE_COPPER_ORE_CLUSTER = new OreClusterFeatureConfig(
                SimpleBlockStateProvider.of(PrimevalBlocks.COPPER_NATIVE_ORE_LARGE),
                SimpleBlockStateProvider.of(PrimevalBlocks.COPPER_NATIVE_ORE_MEDIUM),
                SimpleBlockStateProvider.of(PrimevalBlocks.COPPER_NATIVE_ORE_SMALL),
                UniformIntProvider.create(6, 9),
                UniformIntProvider.create(4, 9),
                UniformFloatProvider.create(0.15f, 0.2f),
                UniformFloatProvider.create(0.25f, 0.4f)
        );
        public static final OreClusterFeatureConfig MALACHITE_COPPER_ORE_CLUSTER = new OreClusterFeatureConfig(
                SimpleBlockStateProvider.of(PrimevalBlocks.COPPER_MALACHITE_ORE_LARGE),
                SimpleBlockStateProvider.of(PrimevalBlocks.COPPER_MALACHITE_ORE_MEDIUM),
                SimpleBlockStateProvider.of(PrimevalBlocks.COPPER_MALACHITE_ORE_SMALL),
                UniformIntProvider.create(5, 8),
                UniformIntProvider.create(3, 5),
                UniformFloatProvider.create(0.15f, 0.35f),
                UniformFloatProvider.create(0.3f, 0.5f)
        );
        public static final OreClusterFeatureConfig CASSITERITE_TIN_ORE_CLUSTER = new OreClusterFeatureConfig(
                SimpleBlockStateProvider.of(PrimevalBlocks.TIN_CASSITERITE_ORE_LARGE),
                SimpleBlockStateProvider.of(PrimevalBlocks.TIN_CASSITERITE_ORE_MEDIUM),
                SimpleBlockStateProvider.of(PrimevalBlocks.TIN_CASSITERITE_ORE_SMALL),
                UniformIntProvider.create(5, 8),
                UniformIntProvider.create(3, 5),
                UniformFloatProvider.create(0.15f, 0.35f),
                UniformFloatProvider.create(0.3f, 0.5f)
        );
        public static final OreFeatureConfig DIRT_ORE_BLOBS = new OreFeatureConfig(
                List.of(OreFeatureConfig.createTarget(new TagMatchRuleTest(PrimevalBlockTags.NATURAL_STONE), PrimevalBlocks.DIRT.getDefaultState())),
                33
        );
        public static final OreFeatureConfig GRAVEL_ORE_BLOBS = new OreFeatureConfig(
                List.of(OreFeatureConfig.createTarget(new TagMatchRuleTest(PrimevalBlockTags.NATURAL_STONE), PrimevalBlocks.GRAVEL.getDefaultState())),
                45
        );

        // SURFACE DECO
        public static final RandomPatchFeatureConfig SHORT_GRASS_PATCH = new RandomPatchFeatureConfig(
                32,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(getGrassAtState(0)))
        );
        public static final RandomPatchFeatureConfig MEDIUM_GRASS_PATCH = new RandomPatchFeatureConfig(
                32,
                7,
                3,
                blockProviderFeature(new WeightedBlockStateProvider(
                        DataPool.<BlockState>builder()
                                .add(getGrassAtState(0), 3)
                                .add(getGrassAtState(1), 2)
                                .add(getGrassAtState(2), 1)
                ))
        );
        public static final RandomPatchFeatureConfig TALL_GRASS_PATCH = new RandomPatchFeatureConfig(
                32,
                7,
                3,
                blockProviderFeature(new WeightedBlockStateProvider(
                        DataPool.<BlockState>builder()
                                .add(getGrassAtState(0), 3)
                                .add(getGrassAtState(1), 2)
                                .add(getGrassAtState(2), 2)
                                .add(getGrassAtState(3), 1)
                                .add(getGrassAtState(4), 1)
                ))
        );
        public static final RandomPatchFeatureConfig BUSH_PATCH = new RandomPatchFeatureConfig(
                12,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.BUSH.getDefaultState()))
        );

        // TREES
        public static final TrunkedTreeFeatureConfig OAK_TRUNKED_TREE = new TrunkedTreeFeatureConfig(
                SimpleBlockStateProvider.of(PrimevalBlocks.OAK_SAPLING),
                UniformIntProvider.create(70, 120)
        );
    }

    private static RegistryEntry<PlacedFeature> blockProviderFeature(BlockStateProvider b) {
        return PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(b));
    }

    private static BlockState getGrassAtState(int growth) {
        return PrimevalBlocks.GRASS.getDefaultState().with(GrowingGrassBlock.GROWTH_STATE, growth);
    }

}
