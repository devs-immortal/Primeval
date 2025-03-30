package net.cr24.primeval;

import net.cr24.primeval.fluid.PrimevalFluids;
import net.cr24.primeval.initialization.*;
import net.cr24.primeval.item.property.FluidContentProperty;
import net.cr24.primeval.screen.PrimevalScreens;
import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.cr24.primeval.world.gen.feature.PrimevalFeatures;
import net.cr24.primeval.world.gen.structure.PrimevalStructures;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.render.item.property.select.CustomModelDataStringProperty;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Primeval implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "primeval";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PrimevalDataComponentTypes.init();
		PrimevalScreens.init();
		PrimevalItems.init();
		PrimevalTypes.init();
		PrimevalBlocks.init();
		PrimevalItemGroups.init();
		PrimevalFluids.init();
		PrimevalRecipes.init();
//		PrimevalWorld.init();
		PrimevalFeatures.init();
		PrimevalStructures.init();
//		PrimevalVillagerTrades.init();
		PrimevalSoundEvents.init();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		PrimevalBlocks.initClient();
		PrimevalFluids.initClient();
		SelectProperties.ID_MAPPER.put(identify("fluid_contents"), FluidContentProperty.TYPE);
	}

	public static Identifier identify(String id) {
		return Identifier.of(MOD_ID, id);
	}
}