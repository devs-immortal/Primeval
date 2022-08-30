package net.cr24.primeval;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.data.*;
import net.cr24.primeval.entity.PrimevalVillagerTrades;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.cr24.primeval.world.PrimevalWorld;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.util.Identifier;

public class PrimevalMain implements ModInitializer, ClientModInitializer, DataGeneratorEntrypoint {

    private static final String MODID = "primeval";

    @Override
    public void onInitialize() {
        PrimevalItems.init();
        PrimevalBlocks.init();
        PrimevalFluids.init();
        PrimevalRecipes.init();
        PrimevalWorld.init();
        PrimevalVillagerTrades.init();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        PrimevalItems.initClient();
        PrimevalBlocks.initClient();
        PrimevalFluids.clientInit();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        dataGenerator.addProvider(new PrimevalModelProvider(dataGenerator));
        dataGenerator.addProvider(new PrimevalBlockLootTableProvider(dataGenerator));
        dataGenerator.addProvider(new PrimevalRecipeProvider(dataGenerator));
        dataGenerator.addProvider(new PrimevalBlockTagProvider(dataGenerator));
        dataGenerator.addProvider(new PrimevalItemTagProvider(dataGenerator));
    }

    public static Identifier getId(String id) {
        return new Identifier(MODID, id);
    }
}
