package net.cr24.primeval;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.item.PrimevalItems;
import net.fabricmc.api.ModInitializer;

public class PrimevalMain implements ModInitializer {

    public static String mod_id = "primeval";

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        System.out.println("Hello Fabric world!");

        PrimevalItems.init();
        PrimevalBlocks.init();
    }

}
