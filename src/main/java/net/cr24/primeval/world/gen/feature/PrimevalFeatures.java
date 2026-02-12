package net.cr24.primeval.world.gen.feature;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.block.plant.GrowingGrassBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PrimevalFeatures extends FabricDynamicRegistryProvider {


    public PrimevalFeatures(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider wrapperLookup, Entries entries) {
        entries.addAll(wrapperLookup.lookupOrThrow(Registries.CONFIGURED_FEATURE));
        entries.addAll(wrapperLookup.lookupOrThrow(Registries.PLACED_FEATURE));
    }

    @Override
    public String getName() {
        return Primeval.MOD_ID + "features";
    }

    public static void init() {
    }

    /* FEATURE TYPES */
    public static final TrunkedTreeFeature TRUNKED_TREE_FEATURE = registerFeature(Primeval.identify("trunked_tree"), new TrunkedTreeFeature(TrunkedTreeFeatureConfig.CODEC));
    public static final LayingItemPatchFeature LAYING_ITEM_PATCH_FEATURE = registerFeature(Primeval.identify("laying_item_patch"), new LayingItemPatchFeature(LayingItemPatchFeatureConfig.CODEC));
    public static final MossFeature MOSS_FEATURE = registerFeature(Primeval.identify("growing_moss"), new MossFeature(MultifaceGrowthConfiguration.CODEC));
    public static final RiverGrassFeature RIVER_GRASS_FEATURE = registerFeature(Primeval.identify("river_grass"), new RiverGrassFeature(NoneFeatureConfiguration.CODEC));
    public static final WaterReedsFeature WATER_REEDS_FEATURE = registerFeature(Primeval.identify("water_reed"), new WaterReedsFeature(NoneFeatureConfiguration.CODEC));

    private static <C extends FeatureConfiguration, F extends Feature<C>> F registerFeature(Identifier id, F f) {
        return Registry.register(BuiltInRegistries.FEATURE, id, f);
    }


    /* CONFIGURED FEATURES */
    public static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?,?>> registerable) {
        // ORES
        registerConfiguredFeature(registerable, "ore_fossil", Feature.ORE, Configs.FOSSIL_ORE_BLOBS);

        // BLOBS+
        registerConfiguredFeature(registerable, "ore_dirt", Feature.ORE, Configs.DIRT_ORE_BLOBS);
        registerConfiguredFeature(registerable, "ore_gravel", Feature.ORE, Configs.GRAVEL_ORE_BLOBS);

        // SURFACE DECORATION
        registerConfiguredFeature(registerable, "patch_short_grass", Feature.RANDOM_PATCH, Configs.SHORT_GRASS_PATCH);
        registerConfiguredFeature(registerable, "patch_medium_grass", Feature.RANDOM_PATCH, Configs.MEDIUM_GRASS_PATCH);
        registerConfiguredFeature(registerable, "patch_tall_grass", Feature.RANDOM_PATCH, Configs.TALL_GRASS_PATCH);

        registerConfiguredFeature(registerable, "patch_bush", Feature.RANDOM_PATCH, Configs.BUSH_PATCH);
        registerConfiguredFeature(registerable, "patch_spiked_plant", Feature.RANDOM_PATCH, Configs.SPIKED_PLANT_PATCH);
        registerConfiguredFeature(registerable, "patch_leafy_plant", Feature.RANDOM_PATCH, Configs.LEAFY_PLANT_PATCH);
        registerConfiguredFeature(registerable, "patch_shrub", Feature.RANDOM_PATCH, Configs.SHRUB_PATCH);

        registerConfiguredFeature(registerable, "patch_reed", Feature.RANDOM_PATCH, Configs.REED_PATCH);
        registerConfiguredFeature(registerable, "patch_water_reed", PrimevalFeatures.WATER_REEDS_FEATURE, new NoneFeatureConfiguration());
        registerConfiguredFeature(registerable, "river_grass", PrimevalFeatures.RIVER_GRASS_FEATURE, new NoneFeatureConfiguration());

        registerConfiguredFeature(registerable, "patch_poppy", Feature.RANDOM_PATCH, Configs.POPPY_PATCH);
        registerConfiguredFeature(registerable, "patch_dandelion", Feature.RANDOM_PATCH, Configs.DANDELION_PATCH);
        registerConfiguredFeature(registerable, "patch_oxeye_daisy", Feature.RANDOM_PATCH, Configs.OXEYE_DAISY_PATCH);
        registerConfiguredFeature(registerable, "patch_cornflower", Feature.RANDOM_PATCH, Configs.CORNFLOWER_PATCH);
        registerConfiguredFeature(registerable, "patch_lily_of_the_valley", Feature.RANDOM_PATCH, Configs.LILY_OF_THE_VALLEY_PATCH);

        registerConfiguredFeature(registerable, "patch_wild_carrots", Feature.RANDOM_PATCH, Configs.WILD_CARROTS_PATCH);
        registerConfiguredFeature(registerable, "patch_wild_wheat", Feature.RANDOM_PATCH, Configs.WILD_WHEAT_PATCH);
        registerConfiguredFeature(registerable, "patch_wild_cabbage", Feature.RANDOM_PATCH, Configs.WILD_CABBAGE_PATCH);
        registerConfiguredFeature(registerable, "patch_wild_beans", Feature.RANDOM_PATCH, Configs.WILD_BEANS_PATCH);
        registerConfiguredFeature(registerable, "patch_wild_potato", Feature.RANDOM_PATCH, Configs.WILD_POTATO_PATCH);

        registerConfiguredFeature(registerable, "moss_rare", PrimevalFeatures.MOSS_FEATURE, Configs.MOSS_RARE);

        // ITEM PATCHES
        registerConfiguredFeature(registerable, "laying_item_patch_stick", LAYING_ITEM_PATCH_FEATURE, Configs.STICK_ITEM_PATCH);
        registerConfiguredFeature(registerable, "laying_item_patch_flint", LAYING_ITEM_PATCH_FEATURE, Configs.FLINT_ITEM_PATCH);
        registerConfiguredFeature(registerable, "laying_item_patch_rock", LAYING_ITEM_PATCH_FEATURE, Configs.ROCK_ITEM_PATCH);

        registerConfiguredFeature(registerable, "laying_item_patch_native_copper", LAYING_ITEM_PATCH_FEATURE, Configs.NATIVE_COPPER_ITEM_PATCH);
        registerConfiguredFeature(registerable, "laying_item_patch_malachite_copper", LAYING_ITEM_PATCH_FEATURE, Configs.MALACHITE_COPPER_ITEM_PATCH);
        registerConfiguredFeature(registerable, "laying_item_patch_mixed_copper", LAYING_ITEM_PATCH_FEATURE, Configs.MIXED_COPPER_ITEM_PATCH);

        registerConfiguredFeature(registerable, "laying_item_patch_cassiterite_tin", LAYING_ITEM_PATCH_FEATURE, Configs.CASSITERITE_TIN_ITEM_PATCH);

        registerConfiguredFeature(registerable, "laying_item_patch_sphalerite_zinc", LAYING_ITEM_PATCH_FEATURE, Configs.SPHALERITE_ZINC_ITEM_PATCH);

        // TREES
        registerConfiguredFeature(registerable, "trunked_tree_oak", TRUNKED_TREE_FEATURE, Configs.OAK_TRUNKED_TREE);
        registerConfiguredFeature(registerable, "trunked_tree_birch", TRUNKED_TREE_FEATURE, Configs.BIRCH_TRUNKED_TREE);
        registerConfiguredFeature(registerable, "trunked_tree_spruce", TRUNKED_TREE_FEATURE, Configs.SPRUCE_TRUNKED_TREE);

    }

    public static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> registerable) {
        // ORES
        registerPlacedFeature(registerable, "ore_fossil", CountPlacement.of(6), InSquarePlacement.spread(), getHeightModifier(-32,80), BiomeFilter.biome());

        // BLOBS+
        registerPlacedFeature(registerable, "ore_dirt", CountPlacement.of(7), InSquarePlacement.spread(), getHeightModifier(0,160), BiomeFilter.biome());
        registerPlacedFeature(registerable, "ore_gravel", CountPlacement.of(5), InSquarePlacement.spread(), getHeightModifier(0,160), BiomeFilter.biome());

        // SURFACE DECORATION
        registerPlacedFeature(registerable, "patch_tall_grass_plains", "patch_tall_grass", NoiseThresholdCountPlacement.of(-0.8, 3, 8), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_medium_grass_oak_forest", "patch_medium_grass", NoiseThresholdCountPlacement.of(-0.8, 1, 5), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_short_grass_sparse", "patch_short_grass", NoiseThresholdCountPlacement.of(-0.8, 1, 3), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());

        registerPlacedFeature(registerable, "patch_bush", NoiseThresholdCountPlacement.of(-0.8, 4, 5), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_spiked_plant", RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_leafy_plant", RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_shrub", RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());

        registerPlacedFeature(registerable, "patch_reed", RarityFilter.onAverageOnceEvery(54), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_water_reed", RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "river_grass", InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(17), BiomeFilter.biome());

        registerPlacedFeature(registerable, "patch_poppy", RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_dandelion", RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_oxeye_daisy", RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_cornflower", RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_lily_of_the_valley", RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());

        registerPlacedFeature(registerable, "patch_wild_carrots", RarityFilter.onAverageOnceEvery(55), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_wild_wheat", RarityFilter.onAverageOnceEvery(55), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_wild_cabbage", RarityFilter.onAverageOnceEvery(55), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_wild_beans", RarityFilter.onAverageOnceEvery(55), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "patch_wild_potato", RarityFilter.onAverageOnceEvery(55), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());

        registerPlacedFeature(registerable, "moss_rare", CountPlacement.of(UniformInt.of(50, 100)), HeightRangePlacement.uniform(VerticalAnchor.absolute(60), VerticalAnchor.top()), InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.biome());

        // ITEM PATCHES
        registerPlacedFeature(registerable, "laying_item_patch_stick", RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "laying_item_patch_flint", RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "laying_item_patch_rock", RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());

        registerPlacedFeature(registerable, "laying_item_patch_native_copper", getWeightedCountPlacementModifier(65, 1), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "laying_item_patch_malachite_copper", getWeightedCountPlacementModifier(105, 1), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());
        registerPlacedFeature(registerable, "laying_item_patch_mixed_copper", getWeightedCountPlacementModifier(35, 1), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());

        registerPlacedFeature(registerable, "laying_item_patch_cassiterite_tin", getWeightedCountPlacementModifier(80, 1), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());

        registerPlacedFeature(registerable, "laying_item_patch_sphalerite_zinc", getWeightedCountPlacementModifier(110, 1), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), BiomeFilter.biome());

        // TREES
        registerPlacedFeature(registerable, "trunked_tree_oak_plains", "trunked_tree_oak", getWeightedCountPlacementModifier(57, 2, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(PrimevalBlocks.OAK_SAPLING.defaultBlockState(), Vec3i.ZERO)), BiomeFilter.biome());
        registerPlacedFeature(registerable, "trunked_tree_oak_forest", "trunked_tree_oak", getWeightedCountPlacementModifier(5, 1, 2), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(PrimevalBlocks.OAK_SAPLING.defaultBlockState(), Vec3i.ZERO)), BiomeFilter.biome());
        registerPlacedFeature(registerable, "trunked_tree_dense_oak_forest", "trunked_tree_oak", getCommonWeightedCountPlacementModifier(1, 3), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(PrimevalBlocks.OAK_SAPLING.defaultBlockState(), Vec3i.ZERO)), BiomeFilter.biome());
        registerPlacedFeature(registerable, "trunked_tree_birch_forest", "trunked_tree_birch", getWeightedCountPlacementModifier(1, 2, 6), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(PrimevalBlocks.BIRCH_SAPLING.defaultBlockState(), Vec3i.ZERO)), BiomeFilter.biome());
        registerPlacedFeature(registerable, "trunked_tree_taiga", "trunked_tree_spruce", getWeightedCountPlacementModifier(1, 2, 6), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(PrimevalBlocks.SPRUCE_SAPLING.defaultBlockState(), Vec3i.ZERO)), BiomeFilter.biome());

    }


    // Registration Helpers

    private static ResourceKey<ConfiguredFeature<?,?>> configuredFeatureKey(String path) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Primeval.identify(path));
    }
    private static ResourceKey<PlacedFeature> placedFeatureKey(String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Primeval.identify(path));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> registerConfiguredFeature(BootstrapContext<ConfiguredFeature<?,?>> registerable, String id, F f, FC fc) {
        return registerable.register(configuredFeatureKey(id), new ConfiguredFeature<>(f, fc));
    }

    private static void registerPlacedFeature(BootstrapContext<PlacedFeature> registerable, String id, PlacementModifier... modifiers) {
        HolderGetter<ConfiguredFeature<?,?>> lookup = registerable.lookup(Registries.CONFIGURED_FEATURE);
        registerable.register(placedFeatureKey(id), new PlacedFeature(lookup.get(configuredFeatureKey(id)).get(), List.of(modifiers)));
    }

    private static void registerPlacedFeature(BootstrapContext<PlacedFeature> registerable, String id, String configuredId, PlacementModifier... modifiers) {
        HolderGetter<ConfiguredFeature<?,?>> lookup = registerable.lookup(Registries.CONFIGURED_FEATURE);
        registerable.register(placedFeatureKey(id), new PlacedFeature(lookup.get(configuredFeatureKey(configuredId)).get(), List.of(modifiers)));
    }


    // Placement Helpers

    private static HeightRangePlacement getHeightModifier(int down, int up) {
        return HeightRangePlacement.uniform(VerticalAnchor.absolute(down),VerticalAnchor.absolute(up));
    }

    private static CountPlacement getWeightedCountPlacementModifier(int i, int j, int k) {
        return CountPlacement.of(new WeightedListInt(
                WeightedList.<IntProvider>builder()
                        .add(ConstantInt.of(0), i)
                        .add(ConstantInt.of(1), j)
                        .add(ConstantInt.of(2), k)
                        .build())
        );
    }

    private static CountPlacement getWeightedCountPlacementModifier(int i, int j) {
        return CountPlacement.of(new WeightedListInt(
                WeightedList.<IntProvider>builder()
                        .add(ConstantInt.of(0), i)
                        .add(ConstantInt.of(3), j)
                        .build())
        );
    }

    private static CountPlacement getCommonWeightedCountPlacementModifier(int i, int j) {
        return CountPlacement.of(new WeightedListInt(
                WeightedList.<IntProvider>builder()
                        .add(ConstantInt.of(2), i)
                        .add(ConstantInt.of(5), j)
                        .build())
        );
    }

    public static class Configs {

        // Ores
        public static final OreClusterFeatureConfig NATIVE_COPPER_ORE_CLUSTER = new OreClusterFeatureConfig(
                PrimevalBlocks.COPPER_NATIVE_ORE,
                UniformInt.of(6, 9),
                UniformInt.of(4, 9),
                UniformFloat.of(0.15f, 0.2f),
                UniformFloat.of(0.25f, 0.4f)
        );
        public static final OreClusterFeatureConfig MALACHITE_COPPER_ORE_CLUSTER = new OreClusterFeatureConfig(
                PrimevalBlocks.COPPER_MALACHITE_ORE,
                UniformInt.of(5, 8),
                UniformInt.of(3, 5),
                UniformFloat.of(0.15f, 0.35f),
                UniformFloat.of(0.3f, 0.5f)
        );
        public static final OreClusterFeatureConfig CASSITERITE_TIN_ORE_CLUSTER = new OreClusterFeatureConfig(
                PrimevalBlocks.TIN_CASSITERITE_ORE,
                UniformInt.of(5, 8),
                UniformInt.of(3, 5),
                UniformFloat.of(0.15f, 0.35f),
                UniformFloat.of(0.3f, 0.5f)
        );
        public static final OreClusterFeatureConfig SPHALERITE_ZINC_ORE_CLUSTER = new OreClusterFeatureConfig(
                PrimevalBlocks.ZINC_SPHALERITE_ORE,
                UniformInt.of(3, 6),
                UniformInt.of(2, 3),
                UniformFloat.of(0.2f, 0.4f),
                UniformFloat.of(0.2f, 0.4f)
        );
        public static final OreClusterFeatureConfig LAZURITE_ORE_CLUSTER = new OreClusterFeatureConfig(
                SimpleStateProvider.simple(PrimevalBlocks.LAZURITE_ORE.large()),
                SimpleStateProvider.simple(PrimevalBlocks.LAZURITE_ORE.medium()),
                new WeightedStateProvider(
                        WeightedList.<BlockState>builder()
                            .add(PrimevalBlocks.LAZURITE_ORE.small().defaultBlockState(), 12)
                            .add(PrimevalBlocks.GOLD_NATIVE_ORE.small().defaultBlockState(), 1)
                ),
                UniformInt.of(4, 6),
                UniformInt.of(3, 6),
                UniformFloat.of(0.1f, 0.3f),
                UniformFloat.of(0.5f, 0.8f)
        );
        public static final OreConfiguration FOSSIL_ORE_BLOBS = new OreConfiguration(
                List.of(OreConfiguration.target(new TagMatchTest(PrimevalTags.Blocks.ORE_REPLACEABLE), PrimevalBlocks.FOSSIL.defaultBlockState())),
                3
        );
        public static final OreConfiguration DIRT_ORE_BLOBS = new OreConfiguration(
                List.of(OreConfiguration.target(new TagMatchTest(PrimevalTags.Blocks.ORE_REPLACEABLE), PrimevalBlocks.DIRT.defaultBlockState())),
                33
        );
        public static final OreConfiguration GRAVEL_ORE_BLOBS = new OreConfiguration(
                List.of(OreConfiguration.target(new TagMatchTest(PrimevalTags.Blocks.ORE_REPLACEABLE), PrimevalBlocks.GRAVEL.defaultBlockState())),
                45
        );

        // SURFACE DECO
        public static final RandomPatchConfiguration SHORT_GRASS_PATCH = new RandomPatchConfiguration(
                32,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(getGrassAtState(0)))
        );
        public static final RandomPatchConfiguration MEDIUM_GRASS_PATCH = new RandomPatchConfiguration(
                32,
                7,
                3,
                blockProviderFeature(new WeightedStateProvider(
                        WeightedList.<BlockState>builder()
                                .add(getGrassAtState(0), 3)
                                .add(getGrassAtState(1), 2)
                                .add(getGrassAtState(2), 1)
                ))
        );
        public static final RandomPatchConfiguration TALL_GRASS_PATCH = new RandomPatchConfiguration(
                32,
                7,
                3,
                blockProviderFeature(new WeightedStateProvider(
                        WeightedList.<BlockState>builder()
                                .add(getGrassAtState(0), 3)
                                .add(getGrassAtState(1), 2)
                                .add(getGrassAtState(2), 2)
                                .add(getGrassAtState(3), 1)
                                .add(getGrassAtState(4), 1)
                ))
        );
        public static final RandomPatchConfiguration BUSH_PATCH = new RandomPatchConfiguration(
                12,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.BUSH.defaultBlockState()))
        );
        public static final RandomPatchConfiguration SPIKED_PLANT_PATCH = new RandomPatchConfiguration(
                12,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.SPIKED_PLANT.defaultBlockState()))
        );
        public static final RandomPatchConfiguration LEAFY_PLANT_PATCH = new RandomPatchConfiguration(
                12,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.LEAFY_PLANT.defaultBlockState()))
        );
        public static final RandomPatchConfiguration SHRUB_PATCH = new RandomPatchConfiguration(
                6,
                5,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.SHRUB.defaultBlockState()))
        );
        public static final RandomPatchConfiguration REED_PATCH = new RandomPatchConfiguration(
                20,
                6,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.REEDS.defaultBlockState()))
        );
        public static final RandomPatchConfiguration POPPY_PATCH = new RandomPatchConfiguration(
                6,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.POPPY.defaultBlockState()))
        );
        public static final RandomPatchConfiguration DANDELION_PATCH = new RandomPatchConfiguration(
                9,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.DANDELION.defaultBlockState()))
        );
        public static final RandomPatchConfiguration OXEYE_DAISY_PATCH = new RandomPatchConfiguration(
                6,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.OXEYE_DAISY.defaultBlockState()))
        );
        public static final RandomPatchConfiguration CORNFLOWER_PATCH = new RandomPatchConfiguration(
                6,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.CORNFLOWER.defaultBlockState()))
        );
        public static final RandomPatchConfiguration LILY_OF_THE_VALLEY_PATCH = new RandomPatchConfiguration(
                6,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.LILY_OF_THE_VALLEY.defaultBlockState()))
        );
        public static final RandomPatchConfiguration WILD_CARROTS_PATCH = new RandomPatchConfiguration(
                12,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.WILD_CARROTS.defaultBlockState()))
        );
        public static final RandomPatchConfiguration WILD_WHEAT_PATCH = new RandomPatchConfiguration(
                12,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.WILD_WHEAT.defaultBlockState()))
        );
        public static final RandomPatchConfiguration WILD_CABBAGE_PATCH = new RandomPatchConfiguration(
                12,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.WILD_CABBAGE.defaultBlockState()))
        );
        public static final RandomPatchConfiguration WILD_BEANS_PATCH = new RandomPatchConfiguration(
                12,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.WILD_BEANS.defaultBlockState()))
        );
        public static final RandomPatchConfiguration WILD_POTATO_PATCH = new RandomPatchConfiguration(
                12,
                7,
                3,
                blockProviderFeature(SimpleStateProvider.simple(PrimevalBlocks.WILD_POTATOES.defaultBlockState()))
        );
        public static final MultifaceGrowthConfiguration MOSS_RARE = new MultifaceGrowthConfiguration(
                (MultifaceSpreadeableBlock) PrimevalBlocks.MOSS,
                20,
                false,
                true,
                true,
                1.0f,
                HolderSet.direct(Block::builtInRegistryHolder, PrimevalBlocks.STONE, PrimevalBlocks.COBBLESTONE, PrimevalBlocks.GRAVEL, PrimevalBlocks.DIRT)
        );
        public static final LayingItemPatchFeatureConfig STICK_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformInt.of(8, 12),
                ConstantInt.of(7),
                ConstantInt.of(3),
                new ItemStack(PrimevalItems.STICK),
                new ItemStack(PrimevalItems.STICK)
        );
        public static final LayingItemPatchFeatureConfig FLINT_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformInt.of(3, 6),
                ConstantInt.of(5),
                ConstantInt.of(3),
                new ItemStack(PrimevalItems.FLINT),
                new ItemStack(PrimevalItems.FLINT)
        );
        public static final LayingItemPatchFeatureConfig ROCK_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformInt.of(6, 12),
                ConstantInt.of(7),
                ConstantInt.of(3),
                new ItemStack(PrimevalItems.ROCK),
                new ItemStack(PrimevalItems.ROCK)
        );
        public static final LayingItemPatchFeatureConfig NATIVE_COPPER_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformInt.of(23, 32),
                ConstantInt.of(7),
                ConstantInt.of(3),
                new ItemStack(PrimevalItems.RAW_COPPER_NATIVE_SMALL),
                new ItemStack(PrimevalItems.RAW_COPPER_NATIVE_MEDIUM)
        );
        public static final LayingItemPatchFeatureConfig MALACHITE_COPPER_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformInt.of(13, 20),
                ConstantInt.of(9),
                ConstantInt.of(3),
                new ItemStack(PrimevalItems.RAW_COPPER_MALACHITE_SMALL),
                new ItemStack(PrimevalItems.RAW_COPPER_MALACHITE_MEDIUM)
        );
        public static final LayingItemPatchFeatureConfig MIXED_COPPER_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformInt.of(5, 10),
                ConstantInt.of(5),
                ConstantInt.of(3),
                new ItemStack(PrimevalItems.RAW_COPPER_NATIVE_SMALL),
                new ItemStack(PrimevalItems.RAW_COPPER_MALACHITE_SMALL)
        );
        public static final LayingItemPatchFeatureConfig CASSITERITE_TIN_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformInt.of(9, 13),
                ConstantInt.of(7),
                ConstantInt.of(3),
                new ItemStack(PrimevalItems.RAW_TIN_CASSITERITE_SMALL),
                new ItemStack(PrimevalItems.RAW_TIN_CASSITERITE_MEDIUM)
        );
        public static final LayingItemPatchFeatureConfig SPHALERITE_ZINC_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformInt.of(9, 13),
                ConstantInt.of(7),
                ConstantInt.of(3),
                new ItemStack(PrimevalItems.RAW_ZINC_SPHALERITE_SMALL),
                new ItemStack(PrimevalItems.RAW_ZINC_SPHALERITE_MEDIUM)
        );

        // TREES
        public static final TrunkedTreeFeatureConfig OAK_TRUNKED_TREE = new TrunkedTreeFeatureConfig(
                SimpleStateProvider.simple(PrimevalBlocks.OAK_SAPLING),
                UniformInt.of(180, 260)
        );
        public static final TrunkedTreeFeatureConfig BIRCH_TRUNKED_TREE = new TrunkedTreeFeatureConfig(
                SimpleStateProvider.simple(PrimevalBlocks.BIRCH_SAPLING),
                UniformInt.of(110, 150)
        );
        public static final TrunkedTreeFeatureConfig SPRUCE_TRUNKED_TREE = new TrunkedTreeFeatureConfig(
                SimpleStateProvider.simple(PrimevalBlocks.SPRUCE_SAPLING),
                UniformInt.of(120, 180)
        );
    }

    private static Holder<PlacedFeature> blockProviderFeature(BlockStateProvider b) {
        return PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(b));
    }

    private static BlockState getGrassAtState(int growth) {
        return PrimevalBlocks.GRASS.defaultBlockState().setValue(GrowingGrassBlock.GROWTH_STATE, growth);
    }

}
