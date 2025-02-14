package net.cr24.primeval;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;

public class PrimevalDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

	}

	private static class ModelProvider extends FabricModelProvider {
		public ModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
			blockStateModelGenerator.registerSimpleCubeAll(PrimevalBlocks.STONE);
		}


		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		}

		@Override
		public String getName() {
			return "FabricDocsReference Model Provider";
		}
	}
}
