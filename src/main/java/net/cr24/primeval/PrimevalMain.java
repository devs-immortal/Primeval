package net.cr24.primeval;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItemGroups;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimevalMain implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "primeval";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
//		PrimevalDataComponentTypes.init();
//		PrimevalItems.init();
		PrimevalBlocks.init();
		PrimevalItemGroups.init();
//		PrimevalFluids.init();
//		PrimevalRecipes.init();
//		PrimevalWorld.init();
//		PrimevalStructures.init();
//		PrimevalVillagerTrades.init();
//		PrimevalSoundEvents.init();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
//		PrimevalItems.initClient();
		PrimevalBlocks.initClient();
//		PrimevalFluids.clientInit();
	}

	public static Identifier identify(String id) {
		return Identifier.of(MOD_ID, id);
	}
}