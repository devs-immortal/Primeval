package net.cr24.primeval.world.gen.feature;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.block.plant.GrowingGrassBlock;
import net.cr24.primeval.block.PrimevalBlockTags;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.item.PrimevalItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PrimevalFeatures extends FabricDynamicRegistryProvider {


    public PrimevalFeatures(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        entries.addAll(wrapperLookup.getOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(wrapperLookup.getOrThrow(RegistryKeys.PLACED_FEATURE));
    }

    @Override
    public String getName() {
        return PrimevalMain.MODID + "features";
    }

    /* FEATURE TYPES */
    public static final OreClusterFeature ORE_CLUSTER_FEATURE = registerFeature(PrimevalMain.getId("ore_cluster"), new OreClusterFeature(OreClusterFeatureConfig.CODEC));
    public static final TrunkedTreeFeature TRUNKED_TREE_FEATURE = registerFeature(PrimevalMain.getId("trunked_tree"), new TrunkedTreeFeature(TrunkedTreeFeatureConfig.CODEC));
    public static final LayingItemPatchFeature LAYING_ITEM_PATCH_FEATURE = registerFeature(PrimevalMain.getId("laying_item_patch"), new LayingItemPatchFeature(LayingItemPatchFeatureConfig.CODEC));
    public static final MossFeature MOSS_FEATURE = registerFeature(PrimevalMain.getId("growing_moss"), new MossFeature(MultifaceGrowthFeatureConfig.CODEC));
    public static final RiverGrassFeature RIVER_GRASS_FEATURE = registerFeature(PrimevalMain.getId("river_grass"), new RiverGrassFeature(DefaultFeatureConfig.CODEC));
    public static final WaterReedsFeature WATER_REEDS_FEATURE = registerFeature(PrimevalMain.getId("water_reed"), new WaterReedsFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(Identifier id, F f) {
        return Registry.register(Registries.FEATURE, id, f);
    }


    /* CONFIGURED FEATURES */
    public static void bootstrapConfiguredFeatures(Registerable<ConfiguredFeature<?,?>> registerable) {
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
        registerConfiguredFeature(registerable, "patch_water_reed", PrimevalFeatures.WATER_REEDS_FEATURE, new DefaultFeatureConfig());
        registerConfiguredFeature(registerable, "river_grass", PrimevalFeatures.RIVER_GRASS_FEATURE, new DefaultFeatureConfig());

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

    public static void bootstrapPlacedFeatures(Registerable<PlacedFeature> registerable) {
        // ORES
        registerPlacedFeature(registerable, "ore_fossil", CountPlacementModifier.of(6), SquarePlacementModifier.of(), getHeightModifier(-32,80), BiomePlacementModifier.of());

        // BLOBS+
        registerPlacedFeature(registerable, "ore_dirt", CountPlacementModifier.of(7), SquarePlacementModifier.of(), getHeightModifier(0,160), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "ore_gravel", CountPlacementModifier.of(5), SquarePlacementModifier.of(), getHeightModifier(0,160), BiomePlacementModifier.of());

        // SURFACE DECORATION
        registerPlacedFeature(registerable, "patch_tall_grass_plains", NoiseThresholdCountPlacementModifier.of(-0.8, 3, 8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_medium_grass_oak_forest", NoiseThresholdCountPlacementModifier.of(-0.8, 1, 5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_short_grass_sparse", NoiseThresholdCountPlacementModifier.of(-0.8, 1, 3), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

        registerPlacedFeature(registerable, "patch_bush", NoiseThresholdCountPlacementModifier.of(-0.8, 4, 5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_spiked_plant", RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_leafy_plant", RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_shrub", RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

        registerPlacedFeature(registerable, "patch_reed", RarityFilterPlacementModifier.of(54), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_water_reed", RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "river_grass", SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, CountPlacementModifier.of(17), BiomePlacementModifier.of());

        registerPlacedFeature(registerable, "patch_poppy", RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_dandelion", RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_oxeye_daisy", RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_cornflower", RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_lily_of_the_valley", RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

        registerPlacedFeature(registerable, "patch_wild_carrots", RarityFilterPlacementModifier.of(55), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_wild_wheat", RarityFilterPlacementModifier.of(55), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_wild_cabbage", RarityFilterPlacementModifier.of(55), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_wild_beans", RarityFilterPlacementModifier.of(55), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "patch_wild_potato", RarityFilterPlacementModifier.of(55), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

        registerPlacedFeature(registerable, "moss_rare", CountPlacementModifier.of(UniformIntProvider.create(50, 100)), HeightRangePlacementModifier.uniform(YOffset.fixed(60), YOffset.getTop()), SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomePlacementModifier.of());

        // ITEM PATCHES
        registerPlacedFeature(registerable, "laying_item_patch_stick", RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "laying_item_patch_flint", RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "laying_item_patch_rock", RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

        registerPlacedFeature(registerable, "laying_item_patch_native_copper", getWeightedCountPlacementModifier(130, 1), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "laying_item_patch_malachite_copper", getWeightedCountPlacementModifier(210, 1), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "laying_item_patch_mixed_copper", getWeightedCountPlacementModifier(70, 1), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

        registerPlacedFeature(registerable, "laying_item_patch_cassiterite_tin", getWeightedCountPlacementModifier(160, 1), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

        registerPlacedFeature(registerable, "laying_item_patch_sphalerite_zinc", getWeightedCountPlacementModifier(220, 1), SquarePlacementModifier.of(), HeightmapPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG), BiomePlacementModifier.of());

        // TREES
        registerPlacedFeature(registerable, "trunked_tree_oak_plains", "trunked_tree_oak", getWeightedCountPlacementModifier(57, 2, 1), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(PrimevalBlocks.OAK_SAPLING.getDefaultState(), Vec3i.ZERO)), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "trunked_tree_oak_forest", "trunked_tree_oak", getWeightedCountPlacementModifier(5, 1, 2), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(PrimevalBlocks.OAK_SAPLING.getDefaultState(), Vec3i.ZERO)), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "trunked_tree_dense_oak_forest", "trunked_tree_oak", getCommonWeightedCountPlacementModifier(1, 3), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(PrimevalBlocks.OAK_SAPLING.getDefaultState(), Vec3i.ZERO)), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "trunked_tree_birch_forest", "trunked_tree_birch", getWeightedCountPlacementModifier(1, 2, 6), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(PrimevalBlocks.BIRCH_SAPLING.getDefaultState(), Vec3i.ZERO)), BiomePlacementModifier.of());
        registerPlacedFeature(registerable, "trunked_tree_taiga", "trunked_tree_spruce", getWeightedCountPlacementModifier(1, 2, 6), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(0), HeightmapPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(PrimevalBlocks.SPRUCE_SAPLING.getDefaultState(), Vec3i.ZERO)), BiomePlacementModifier.of());

    }


    // Registration Helpers

    private static RegistryKey<ConfiguredFeature<?,?>> configuredFeatureKey(String path) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, PrimevalMain.getId(path));
    }
    private static RegistryKey<PlacedFeature> placedFeatureKey(String path) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, PrimevalMain.getId(path));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void registerConfiguredFeature(Registerable<ConfiguredFeature<?,?>> registerable, String id, F f, FC fc) {
        registerable.register(configuredFeatureKey(id), new ConfiguredFeature<>(f, fc));
    }

    private static void registerPlacedFeature(Registerable<PlacedFeature> registerable, String id, PlacementModifier... modifiers) {
        RegistryEntryLookup<ConfiguredFeature<?,?>> lookup = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        registerable.register(placedFeatureKey(id), new PlacedFeature(lookup.getOptional(configuredFeatureKey(id)).get(), List.of(modifiers)));
    }

    private static void registerPlacedFeature(Registerable<PlacedFeature> registerable, String id, String configuredId, PlacementModifier... modifiers) {
        RegistryEntryLookup<ConfiguredFeature<?,?>> lookup = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        registerable.register(placedFeatureKey(id), new PlacedFeature(lookup.getOptional(configuredFeatureKey(configuredId)).get(), List.of(modifiers)));
    }


    // Placement Helpers

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

    private static CountPlacementModifier getWeightedCountPlacementModifier(int i, int j) {
        return CountPlacementModifier.of(new WeightedListIntProvider(
                DataPool.<IntProvider>builder()
                        .add(ConstantIntProvider.create(0), i)
                        .add(ConstantIntProvider.create(3), j)
                        .build())
        );
    }

    private static CountPlacementModifier getCommonWeightedCountPlacementModifier(int i, int j) {
        return CountPlacementModifier.of(new WeightedListIntProvider(
                DataPool.<IntProvider>builder()
                        .add(ConstantIntProvider.create(2), i)
                        .add(ConstantIntProvider.create(5), j)
                        .build())
        );
    }

    protected static class Configs {

        // Ores
        public static final OreClusterFeatureConfig NATIVE_COPPER_ORE_CLUSTER = new OreClusterFeatureConfig(
                PrimevalBlocks.COPPER_NATIVE_ORE,
                UniformIntProvider.create(6, 9),
                UniformIntProvider.create(4, 9),
                UniformFloatProvider.create(0.15f, 0.2f),
                UniformFloatProvider.create(0.25f, 0.4f)
        );
        public static final OreClusterFeatureConfig MALACHITE_COPPER_ORE_CLUSTER = new OreClusterFeatureConfig(
                PrimevalBlocks.COPPER_MALACHITE_ORE,
                UniformIntProvider.create(5, 8),
                UniformIntProvider.create(3, 5),
                UniformFloatProvider.create(0.15f, 0.35f),
                UniformFloatProvider.create(0.3f, 0.5f)
        );
        public static final OreClusterFeatureConfig CASSITERITE_TIN_ORE_CLUSTER = new OreClusterFeatureConfig(
                PrimevalBlocks.TIN_CASSITERITE_ORE,
                UniformIntProvider.create(5, 8),
                UniformIntProvider.create(3, 5),
                UniformFloatProvider.create(0.15f, 0.35f),
                UniformFloatProvider.create(0.3f, 0.5f)
        );
        public static final OreClusterFeatureConfig SPHALERITE_ZINC_ORE_CLUSTER = new OreClusterFeatureConfig(
                PrimevalBlocks.ZINC_SPHALERITE_ORE,
                UniformIntProvider.create(3, 6),
                UniformIntProvider.create(2, 3),
                UniformFloatProvider.create(0.2f, 0.4f),
                UniformFloatProvider.create(0.2f, 0.4f)
        );
        public static final OreClusterFeatureConfig LAZURITE_ORE_CLUSTER = new OreClusterFeatureConfig(
                SimpleBlockStateProvider.of(PrimevalBlocks.LAZURITE_ORE.large()),
                SimpleBlockStateProvider.of(PrimevalBlocks.LAZURITE_ORE.medium()),
                new WeightedBlockStateProvider(
                        DataPool.<BlockState>builder()
                            .add(PrimevalBlocks.LAZURITE_ORE.small().getDefaultState(), 12)
                            .add(PrimevalBlocks.GOLD_NATIVE_ORE.small().getDefaultState(), 1)
                ),
                UniformIntProvider.create(4, 6),
                UniformIntProvider.create(3, 6),
                UniformFloatProvider.create(0.1f, 0.3f),
                UniformFloatProvider.create(0.5f, 0.8f)
        );
        public static final OreFeatureConfig FOSSIL_ORE_BLOBS = new OreFeatureConfig(
                List.of(OreFeatureConfig.createTarget(new TagMatchRuleTest(PrimevalBlockTags.NATURAL_STONE), PrimevalBlocks.FOSSIL.getDefaultState())),
                3
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
        public static final RandomPatchFeatureConfig SPIKED_PLANT_PATCH = new RandomPatchFeatureConfig(
                12,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.SPIKED_PLANT.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig LEAFY_PLANT_PATCH = new RandomPatchFeatureConfig(
                12,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.LEAFY_PLANT.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig SHRUB_PATCH = new RandomPatchFeatureConfig(
                6,
                5,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.SHRUB.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig REED_PATCH = new RandomPatchFeatureConfig(
                20,
                6,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.REEDS.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig POPPY_PATCH = new RandomPatchFeatureConfig(
                6,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.POPPY.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig DANDELION_PATCH = new RandomPatchFeatureConfig(
                9,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.DANDELION.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig OXEYE_DAISY_PATCH = new RandomPatchFeatureConfig(
                6,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.OXEYE_DAISY.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig CORNFLOWER_PATCH = new RandomPatchFeatureConfig(
                6,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.CORNFLOWER.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig LILY_OF_THE_VALLEY_PATCH = new RandomPatchFeatureConfig(
                6,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.LILY_OF_THE_VALLEY.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig WILD_CARROTS_PATCH = new RandomPatchFeatureConfig(
                12,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.WILD_CARROTS.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig WILD_WHEAT_PATCH = new RandomPatchFeatureConfig(
                12,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.WILD_WHEAT.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig WILD_CABBAGE_PATCH = new RandomPatchFeatureConfig(
                12,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.WILD_CABBAGE.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig WILD_BEANS_PATCH = new RandomPatchFeatureConfig(
                12,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.WILD_BEANS.getDefaultState()))
        );
        public static final RandomPatchFeatureConfig WILD_POTATO_PATCH = new RandomPatchFeatureConfig(
                12,
                7,
                3,
                blockProviderFeature(SimpleBlockStateProvider.of(PrimevalBlocks.WILD_POTATOES.getDefaultState()))
        );
        public static final MultifaceGrowthFeatureConfig MOSS_RARE = new MultifaceGrowthFeatureConfig(
                (MultifaceGrowthBlock) PrimevalBlocks.MOSS,
                20,
                false,
                true,
                true,
                1.0f,
                RegistryEntryList.of(Block::getRegistryEntry, PrimevalBlocks.STONE, PrimevalBlocks.COBBLESTONE, PrimevalBlocks.GRAVEL, PrimevalBlocks.DIRT)
        );
        public static final LayingItemPatchFeatureConfig STICK_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformIntProvider.create(8, 12),
                ConstantIntProvider.create(7),
                ConstantIntProvider.create(3),
                new ItemStack(PrimevalItems.STICK),
                new ItemStack(PrimevalItems.STICK)
        );
        public static final LayingItemPatchFeatureConfig FLINT_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformIntProvider.create(3, 6),
                ConstantIntProvider.create(5),
                ConstantIntProvider.create(3),
                new ItemStack(PrimevalItems.FLINT),
                new ItemStack(PrimevalItems.FLINT)
        );
        public static final LayingItemPatchFeatureConfig ROCK_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformIntProvider.create(6, 12),
                ConstantIntProvider.create(7),
                ConstantIntProvider.create(3),
                new ItemStack(PrimevalItems.ROCK),
                new ItemStack(PrimevalItems.ROCK)
        );
        public static final LayingItemPatchFeatureConfig NATIVE_COPPER_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformIntProvider.create(23, 32),
                ConstantIntProvider.create(7),
                ConstantIntProvider.create(3),
                new ItemStack(PrimevalItems.RAW_COPPER_NATIVE_SMALL),
                new ItemStack(PrimevalItems.RAW_COPPER_NATIVE_MEDIUM)
        );
        public static final LayingItemPatchFeatureConfig MALACHITE_COPPER_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformIntProvider.create(13, 20),
                ConstantIntProvider.create(9),
                ConstantIntProvider.create(3),
                new ItemStack(PrimevalItems.RAW_COPPER_MALACHITE_SMALL),
                new ItemStack(PrimevalItems.RAW_COPPER_MALACHITE_MEDIUM)
        );
        public static final LayingItemPatchFeatureConfig MIXED_COPPER_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformIntProvider.create(5, 10),
                ConstantIntProvider.create(5),
                ConstantIntProvider.create(3),
                new ItemStack(PrimevalItems.RAW_COPPER_NATIVE_SMALL),
                new ItemStack(PrimevalItems.RAW_COPPER_MALACHITE_SMALL)
        );
        public static final LayingItemPatchFeatureConfig CASSITERITE_TIN_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformIntProvider.create(9, 13),
                ConstantIntProvider.create(7),
                ConstantIntProvider.create(3),
                new ItemStack(PrimevalItems.RAW_TIN_CASSITERITE_SMALL),
                new ItemStack(PrimevalItems.RAW_TIN_CASSITERITE_MEDIUM)
        );
        public static final LayingItemPatchFeatureConfig SPHALERITE_ZINC_ITEM_PATCH = new LayingItemPatchFeatureConfig(
                UniformIntProvider.create(9, 13),
                ConstantIntProvider.create(7),
                ConstantIntProvider.create(3),
                new ItemStack(PrimevalItems.RAW_ZINC_SPHALERITE_SMALL),
                new ItemStack(PrimevalItems.RAW_ZINC_SPHALERITE_MEDIUM)
        );

        // TREES
        public static final TrunkedTreeFeatureConfig OAK_TRUNKED_TREE = new TrunkedTreeFeatureConfig(
                SimpleBlockStateProvider.of(PrimevalBlocks.OAK_SAPLING),
                UniformIntProvider.create(180, 260)
        );
        public static final TrunkedTreeFeatureConfig BIRCH_TRUNKED_TREE = new TrunkedTreeFeatureConfig(
                SimpleBlockStateProvider.of(PrimevalBlocks.BIRCH_SAPLING),
                UniformIntProvider.create(110, 150)
        );
        public static final TrunkedTreeFeatureConfig SPRUCE_TRUNKED_TREE = new TrunkedTreeFeatureConfig(
                SimpleBlockStateProvider.of(PrimevalBlocks.SPRUCE_SAPLING),
                UniformIntProvider.create(120, 180)
        );
    }

    private static RegistryEntry<PlacedFeature> blockProviderFeature(BlockStateProvider b) {
        return PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(b));
    }

    private static BlockState getGrassAtState(int growth) {
        return PrimevalBlocks.GRASS.getDefaultState().with(GrowingGrassBlock.GROWTH_STATE, growth);
    }

}
