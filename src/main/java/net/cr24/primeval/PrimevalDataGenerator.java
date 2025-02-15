package net.cr24.primeval;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.tint.GrassTintSource;

import static net.cr24.primeval.initialization.PrimevalBlocks.*;
import static net.minecraft.client.data.TextureMap.getSubId;
import static net.minecraft.client.data.TexturedModel.makeFactory;

public class PrimevalDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelProvider::new);
	}

	private static class ModelProvider extends FabricModelProvider {
		public ModelProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

			blockStateModelGenerator.registerSimpleCubeAll(DIRT);
			blockStateModelGenerator.registerSimpleCubeAll(COARSE_DIRT);
			blockStateModelGenerator.registerSimpleCubeAll(CLAY);
			blockStateModelGenerator.registerMirrorable(MUD);
			blockStateModelGenerator.registerSimpleCubeAll(DRY_DIRT);
			blockStateModelGenerator.registerSimpleCubeAll(SAND);
			blockStateModelGenerator.registerSimpleCubeAll(GRAVEL);
			blockStateModelGenerator.registerSimpleCubeAll(COBBLESTONE);
			blockStateModelGenerator.registerMirrorable(STONE);
			blockStateModelGenerator.registerSimpleCubeAll(SANDSTONE);

			blockStateModelGenerator.registerAxisRotated(OAK_LOG, TEXTURED_MODEL_LOG_COLUMN);
			blockStateModelGenerator.registerSingleton(OAK_LEAVES, TexturedModel.LEAVES);
			blockStateModelGenerator.registerAxisRotated(BIRCH_LOG, TEXTURED_MODEL_LOG_COLUMN);
			blockStateModelGenerator.registerSingleton(BIRCH_LEAVES, TexturedModel.LEAVES);
			blockStateModelGenerator.registerAxisRotated(SPRUCE_LOG, TEXTURED_MODEL_LOG_COLUMN);
			blockStateModelGenerator.registerSingleton(SPRUCE_LEAVES, TexturedModel.LEAVES);

			blockStateModelGenerator.registerTintableCross(OAK_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
			blockStateModelGenerator.registerTintableCross(BIRCH_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
			blockStateModelGenerator.registerTintableCross(SPRUCE_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);

			blockStateModelGenerator.registerTintedItemModel(GRASS, blockStateModelGenerator.uploadBlockItemModel(GRASS.asItem(), GRASS, "_4"), new GrassTintSource(0.7f, 1.0f));
			blockStateModelGenerator.registerTintedItemModel(BUSH, blockStateModelGenerator.uploadBlockItemModel(BUSH.asItem(), BUSH, "_big"), new GrassTintSource(0.7f, 1.0f));
			blockStateModelGenerator.registerGrassTinted(SPIKED_PLANT);
			blockStateModelGenerator.registerGrassTinted(LEAFY_PLANT);
			blockStateModelGenerator.registerItemModel(SHRUB.asItem());
			blockStateModelGenerator.registerMultifaceBlock(MOSS);

			blockStateModelGenerator.registerTintableCross(POPPY, BlockStateModelGenerator.CrossType.NOT_TINTED);
			blockStateModelGenerator.registerTintableCross(DANDELION, BlockStateModelGenerator.CrossType.NOT_TINTED);
			blockStateModelGenerator.registerTintableCross(OXEYE_DAISY, BlockStateModelGenerator.CrossType.NOT_TINTED);
			blockStateModelGenerator.registerTintableCross(CORNFLOWER, BlockStateModelGenerator.CrossType.NOT_TINTED);
			blockStateModelGenerator.registerTintableCross(LILY_OF_THE_VALLEY, BlockStateModelGenerator.CrossType.NOT_TINTED);

			blockStateModelGenerator.registerItemModel(REEDS.asItem());
			blockStateModelGenerator.registerTintableCross(RIVER_GRASS, BlockStateModelGenerator.CrossType.NOT_TINTED);

			registerOreBlockSetModels(blockStateModelGenerator, COPPER_MALACHITE_ORE);
			registerOreBlockSetModels(blockStateModelGenerator, COPPER_NATIVE_ORE);
			registerOreBlockSetModels(blockStateModelGenerator, TIN_CASSITERITE_ORE);
			registerOreBlockSetModels(blockStateModelGenerator, ZINC_SPHALERITE_ORE);
			registerOreBlockSetModels(blockStateModelGenerator, GOLD_NATIVE_ORE);
			registerOreBlockSetModels(blockStateModelGenerator, IRON_HEMATITE_ORE);
			registerOreBlockSetModels(blockStateModelGenerator, LAZURITE_ORE);
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			itemModelGenerator.output.accept(GRASSY_DIRT.asItem(),
					ItemModels.tinted(ModelIds.getBlockModelId(GRASSY_DIRT), new GrassTintSource(0.7f, 1.0f))
			);
			itemModelGenerator.output.accept(GRASSY_CLAY.asItem(),
					ItemModels.tinted(ModelIds.getBlockModelId(GRASSY_CLAY), new GrassTintSource(0.7f, 1.0f))
			);
			itemModelGenerator.output.accept(FOSSIL.asItem(),
					ItemModels.basic(ModelIds.getBlockSubModelId(FOSSIL, "_2"))
			);
		}

		@Override
		public String getName() {
			return "Primeval Model Provider";
		}

		private static final TexturedModel.Factory TEXTURED_MODEL_LOG_COLUMN = makeFactory((block) -> (new TextureMap()).put(TextureKey.SIDE, getSubId(block, "_0")).put(TextureKey.END, getSubId(block, "_top_0")), Models.CUBE_COLUMN);

		private static void registerOreBlockSetModels(BlockStateModelGenerator blockStateModelGenerator, OreBlockSet set) {
			blockStateModelGenerator.registerSimpleCubeAll(set.small());
			blockStateModelGenerator.registerSimpleCubeAll(set.medium());
			blockStateModelGenerator.registerSimpleCubeAll(set.large());
		}
	}
}
