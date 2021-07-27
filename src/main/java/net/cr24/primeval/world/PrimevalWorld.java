package net.cr24.primeval.world;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.world.gen.carver.*;
import net.cr24.primeval.world.gen.feature.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.carver.CaveCarverConfig;
import net.minecraft.world.gen.feature.Feature;

public class PrimevalWorld {

    /* CARVERS */
    public static final CaveCarver CAVE_CARVER = Registry.register(Registry.CARVER, PrimevalMain.getId("cave"), new PrimevalCaveCarver(CaveCarverConfig.CAVE_CODEC));


    /* FEATURES */
    public static final Feature ORE_CLUSTER_FEATURE = Registry.register(Registry.FEATURE, PrimevalMain.getId("ore_cluster"), new OreClusterFeature(OreClusterFeatureConfig.CODEC));

    public static void init() {}
}
