package net.cr24.primeval;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.cr24.primeval.recipe.rei.PrimevalREIIntegration;
import net.cr24.primeval.world.PrimevalWorld;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class PrimevalMain implements ModInitializer, ClientModInitializer {

    private static final String MODID = "primeval";

    @Override
    public void onInitialize() {
        PrimevalItems.init();
        PrimevalBlocks.init();
        PrimevalFluids.init();
        PrimevalRecipes.init();
        PrimevalWorld.init();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        PrimevalBlocks.initClient();
        PrimevalFluids.clientInit();
    }

    public static Identifier getId(String id) {
        return new Identifier(MODID, id);
    }

}
