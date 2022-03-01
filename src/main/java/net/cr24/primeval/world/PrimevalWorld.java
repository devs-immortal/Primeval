package net.cr24.primeval.world;

import com.mojang.serialization.Codec;
import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.mixin.world.gen.trunk.TrunkPlacerTypeMixin;
import net.cr24.primeval.world.gen.NaturalTrunkPlacer;
import net.cr24.primeval.world.gen.feature.*;
import net.fabricmc.fabric.impl.registry.sync.FabricRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class PrimevalWorld {

    /* FEATURES */
    public static final Feature ORE_CLUSTER_FEATURE = Registry.register(Registry.FEATURE, PrimevalMain.getId("ore_cluster"), new OreClusterFeature(OreClusterFeatureConfig.CODEC));

    public static final TrunkPlacerType<NaturalTrunkPlacer> NATURAL_TRUNK_PLACER = TrunkPlacerTypeMixin.callRegister("natural_trunk_placer", NaturalTrunkPlacer.CODEC);

    public static void init() {}
}
