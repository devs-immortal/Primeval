package net.cr24.primeval.world;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.world.gen.carver.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.carver.CaveCarverConfig;

public class PrimevalWorld {

    /* CARVERS */
    public static final CaveCarver CAVE_CARVER = Registry.register(Registry.CARVER, PrimevalMain.getId("cave"), new PrimevalCaveCarver(CaveCarverConfig.CAVE_CODEC));


    /* FEATURES */


    public static void init() {}
}
