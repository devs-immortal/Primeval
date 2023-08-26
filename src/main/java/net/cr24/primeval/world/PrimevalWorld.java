package net.cr24.primeval.world;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.List;

import static net.cr24.primeval.world.gen.feature.PrimevalFeatures.*;

public class PrimevalWorld {

    /* BIOMES */
    public static final BiomeEffects UNIVERSAL_EFFECTS = new BiomeEffects.Builder().skyColor(7907327).fogColor(12638463).grassColor(9551193).waterColor(5404090).waterFogColor(329011).build();

    public static final List<RegistryEntry<PlacedFeature>> UNDERGROUND_FEATURES = List.of(
            DIRT_ORE_BLOBS, GRAVEL_ORE_BLOBS, FOSSIL_ORE_BLOBS
    );

    // INLAND
    public static final RegistryEntry<Biome> PLAINS = registerBiome(getBiomeKey("inland/plains"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of(PLAINS_OAK_TRUNKED_TREE,
                            MOSS_RARE, RIVER_GRASS,
                            STICK_ITEM_PATCH, FLINT_ITEM_PATCH, ROCK_ITEM_PATCH,
                            NATIVE_COPPER_ITEM_PATCH, MALACHITE_COPPER_ITEM_PATCH, MIXED_COPPER_ITEM_PATCH, SPHALERITE_ZINC_ITEM_PATCH,
                            PLAINS_GRASS_PATCH, BUSH_PATCH, SHRUB_PATCH, POPPY_PATCH, DANDELION_PATCH, OXEYE_DAISY_PATCH,
                            WILD_CARROTS_PATCH, WILD_WHEAT_PATCH, WATER_REED_PATCH
                    )
            )
    ));
    public static final RegistryEntry<Biome> OAK_FOREST = registerBiome(getBiomeKey("inland/oak_forest"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of(FOREST_OAK_TRUNKED_TREE,
                            MOSS_RARE, RIVER_GRASS,
                            STICK_ITEM_PATCH, FLINT_ITEM_PATCH, ROCK_ITEM_PATCH,
                            NATIVE_COPPER_ITEM_PATCH, MALACHITE_COPPER_ITEM_PATCH, MIXED_COPPER_ITEM_PATCH, CASSITERITE_TIN_ITEM_PATCH,
                            OAK_FOREST_GRASS_PATCH, SPIKED_PLANT_PATCH, POPPY_PATCH,
                            WILD_BEANS_PATCH, WILD_POTATO_PATCH, WATER_REED_PATCH
                    )
            )
    ));
    public static final RegistryEntry<Biome> DENSE_OAK_FOREST = registerBiome(getBiomeKey("inland/dense_oak_forest"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of(FOREST_DENSE_OAK_TRUNKED_TREE,
                            MOSS_RARE, RIVER_GRASS,
                            STICK_ITEM_PATCH, FLINT_ITEM_PATCH, ROCK_ITEM_PATCH,
                            NATIVE_COPPER_ITEM_PATCH, MALACHITE_COPPER_ITEM_PATCH, MIXED_COPPER_ITEM_PATCH, CASSITERITE_TIN_ITEM_PATCH,
                            SPARSE_GRASS_PATCH, SPIKED_PLANT_PATCH, SHRUB_PATCH, POPPY_PATCH,
                            WILD_BEANS_PATCH, WILD_POTATO_PATCH, WATER_REED_PATCH
                    )
            )
    ));
    public static final RegistryEntry<Biome> BIRCH_FOREST = registerBiome(getBiomeKey("inland/birch_forest"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of(FOREST_BIRCH_TRUNKED_TREE,
                            MOSS_RARE, RIVER_GRASS,
                            STICK_ITEM_PATCH, FLINT_ITEM_PATCH, ROCK_ITEM_PATCH,
                            NATIVE_COPPER_ITEM_PATCH, MALACHITE_COPPER_ITEM_PATCH, MIXED_COPPER_ITEM_PATCH, CASSITERITE_TIN_ITEM_PATCH,
                            PLAINS_GRASS_PATCH, DANDELION_PATCH, OXEYE_DAISY_PATCH,
                            WILD_CABBAGE_PATCH, REED_PATCH, WATER_REED_PATCH
                    )
            )
    ));
    public static final RegistryEntry<Biome> TAIGA = registerBiome(getBiomeKey("inland/taiga"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of(FOREST_SPRUCE_TRUNKED_TREE,
                            MOSS_RARE, RIVER_GRASS,
                            STICK_ITEM_PATCH, FLINT_ITEM_PATCH, ROCK_ITEM_PATCH,
                            NATIVE_COPPER_ITEM_PATCH, MIXED_COPPER_ITEM_PATCH, CASSITERITE_TIN_ITEM_PATCH, SPHALERITE_ZINC_ITEM_PATCH,
                            SPARSE_GRASS_PATCH, LEAFY_PLANT_PATCH, SHRUB_PATCH, DANDELION_PATCH, LILY_OF_THE_VALLEY_PATCH,
                            WILD_CARROTS_PATCH, WILD_BEANS_PATCH
                    )
            )
    ));
    public static final RegistryEntry<Biome> ROCKY_OUTCROP = registerBiome(getBiomeKey("inland/rocky_outcrop"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of(MOSS_RARE,
                            FLINT_ITEM_PATCH, ROCK_ITEM_PATCH,
                            NATIVE_COPPER_ITEM_PATCH, MALACHITE_COPPER_ITEM_PATCH, MIXED_COPPER_ITEM_PATCH, CASSITERITE_TIN_ITEM_PATCH, SPHALERITE_ZINC_ITEM_PATCH,
                            SPARSE_GRASS_PATCH, LEAFY_PLANT_PATCH, LILY_OF_THE_VALLEY_PATCH,
                            WILD_WHEAT_PATCH
                    )
            )
    ));
    public static final RegistryEntry<Biome> CRUMBLING_CLIFFS = registerBiome(getBiomeKey("inland/crumbling_cliffs"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of(FLINT_ITEM_PATCH, ROCK_ITEM_PATCH,
                            NATIVE_COPPER_ITEM_PATCH, MALACHITE_COPPER_ITEM_PATCH, MIXED_COPPER_ITEM_PATCH, CASSITERITE_TIN_ITEM_PATCH, SPHALERITE_ZINC_ITEM_PATCH
                    )
            )
    ));

    // COASTAL
    public static final RegistryEntry<Biome> SANDY_SHORE = registerBiome(getBiomeKey("coastal/sandy_shore"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of(STICK_ITEM_PATCH, ROCK_ITEM_PATCH,
                            SPARSE_GRASS_PATCH, CORNFLOWER_PATCH
                    )
            )
    ));
    public static final RegistryEntry<Biome> ROCKY_SHORE = registerBiome(getBiomeKey("coastal/rocky_shore"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of(STICK_ITEM_PATCH, FLINT_ITEM_PATCH, ROCK_ITEM_PATCH,
                            WILD_CARROTS_PATCH
                    )
            )
    ));

    // ETC
    public static final RegistryEntry<Biome> OCEAN = registerBiome(getBiomeKey("ocean"), createBiome(
            buildGeneratorSettings(
                    UNDERGROUND_FEATURES,
                    List.of()
            )
    ));

    public static void init() {}

    private static Biome createBiome(GenerationSettings g) {
        return new Biome.Builder()
                .effects(UNIVERSAL_EFFECTS)
                .generationSettings(g)
                .spawnSettings(
                        new SpawnSettings.Builder()
                                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 50, 3, 4))
                                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CREEPER, 75, 1, 3))
                                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 100, 2, 3))
                                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 200, 4, 5))
                                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.PIG, 20, 5, 10))
                        .build()
                )
                .precipitation(Biome.Precipitation.RAIN)
                .temperature(0.8f)
                .downfall(0.4f)
                .build();
    }

    private static RegistryKey<Biome> getBiomeKey(String id) {
        return RegistryKey.of(Registry.BIOME_KEY, PrimevalMain.getId(id));
    }

    private static RegistryEntry<Biome> registerBiome(RegistryKey<Biome> key, Biome b) {
        return BuiltinRegistries.add(BuiltinRegistries.BIOME, key, b);
    }

    public static GenerationSettings buildGeneratorSettings(List<RegistryEntry<PlacedFeature>> undergroundOres, List<RegistryEntry<PlacedFeature>> vegetalDecoration) {
        GenerationSettings.Builder gen = new GenerationSettings.Builder();
        for (RegistryEntry<PlacedFeature> f : undergroundOres) {
            gen.feature(GenerationStep.Feature.UNDERGROUND_ORES, f);
        }
        for (RegistryEntry<PlacedFeature> f : vegetalDecoration) {
            gen.feature(GenerationStep.Feature.VEGETAL_DECORATION, f);
        }
        return gen.build();
    }
}
