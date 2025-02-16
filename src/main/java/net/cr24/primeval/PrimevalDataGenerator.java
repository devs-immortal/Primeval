package net.cr24.primeval;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.tint.GrassTintSource;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

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

			registerFullBlockSetModels(blockStateModelGenerator, COPPER_MALACHITE_ORE);
			registerFullBlockSetModels(blockStateModelGenerator, COPPER_NATIVE_ORE);
			registerFullBlockSetModels(blockStateModelGenerator, TIN_CASSITERITE_ORE);
			registerFullBlockSetModels(blockStateModelGenerator, ZINC_SPHALERITE_ORE);
			registerFullBlockSetModels(blockStateModelGenerator, GOLD_NATIVE_ORE);
			registerFullBlockSetModels(blockStateModelGenerator, IRON_HEMATITE_ORE);
			registerFullBlockSetModels(blockStateModelGenerator, LAZURITE_ORE);

			blockStateModelGenerator.registerSimpleCubeAll(STRAW_MESH);
			registerCarpet(blockStateModelGenerator, STRAW_MESH, STRAW_MAT);
			blockStateModelGenerator.registerSimpleCubeAll(TERRACOTTA);
			registerFullBlockSetModels(blockStateModelGenerator, COLORED_TERRACOTTA);
			registerBlockSetModels(blockStateModelGenerator, FIRED_CLAY_SHINGLE_BLOCKS);
			registerColoredBlockSetSetModels(blockStateModelGenerator, COLORED_FIRED_CLAY_SHINGLE_BLOCKS);
			registerBlockSetModels(blockStateModelGenerator, FIRED_CLAY_BRICK_BLOCKS);
			registerBlockSetModels(blockStateModelGenerator, FIRED_CLAY_TILES_BLOCKS);
			registerBlockSetModels(blockStateModelGenerator, DRIED_BRICK_BLOCKS);
			registerBlockSetModels(blockStateModelGenerator, MUD_BRICKS);
			registerBlockSetModels(blockStateModelGenerator, CRUDE_BRICKS);
			registerBlockSetModels(blockStateModelGenerator, STONE_BRICKS);
			registerBlockSetModels(blockStateModelGenerator, SMOOTH_STONE);
			registerPillar(blockStateModelGenerator, STONE_INDENT, SMOOTH_STONE.block());
			blockStateModelGenerator.registerAxisRotated(STONE_PILLAR, TexturedModel.CUBE_COLUMN);
			registerBlockSetModels(blockStateModelGenerator, STONE_PAVER);
			blockStateModelGenerator.registerSimpleCubeAll(DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_PILLAR_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_CROSS_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_INVERTED_CROSS_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_X_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_PLUS_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_DIVIDED_DAUB);
			registerWoodBlockSetModels(blockStateModelGenerator, OAK_PLANK_BLOCKS);
			registerPillar(blockStateModelGenerator, OAK_PLANK_BLOCKS.panel(), OAK_PLANK_BLOCKS.block());
			registerWoodBlockSetModels(blockStateModelGenerator, BIRCH_PLANK_BLOCKS);
			registerPillar(blockStateModelGenerator, BIRCH_PLANK_BLOCKS.panel(), BIRCH_PLANK_BLOCKS.block());
			registerWoodBlockSetModels(blockStateModelGenerator, SPRUCE_PLANK_BLOCKS);
			registerPillar(blockStateModelGenerator, SPRUCE_PLANK_BLOCKS.panel(), SPRUCE_PLANK_BLOCKS.block());
			registerBlockSetModels(blockStateModelGenerator, WICKER);
			registerBars(blockStateModelGenerator, WICKER_BARS);
			blockStateModelGenerator.registerItemModel(ROPE.asItem());
			blockStateModelGenerator.registerAxisRotated(ROPE, ModelIds.getBlockModelId(ROPE));
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
		private static final TexturedModel.Factory TEXTURED_MODEL_PANEL = makeFactory((block) -> (new TextureMap()).put(TextureKey.SIDE, getSubId(block, "_0")).put(TextureKey.END, replaceInId(block, "panel", "planks")), Models.CUBE_COLUMN);

		private static Identifier replaceInId(Block block, String from, String to) {
			Identifier identifier = Registries.BLOCK.getId(block);
			return identifier.withPath((path) -> "block/" + path.replace(from, to));
		}


		private static <T extends Iterable<Block>> void registerFullBlockSetModels(BlockStateModelGenerator blockStateModelGenerator, T set) {
			set.iterator().forEachRemaining((b) -> blockStateModelGenerator.registerSimpleCubeAll(b));
		}

		private static void registerBlockSetModels(BlockStateModelGenerator blockStateModelGenerator, BlockSet set) {
			blockStateModelGenerator.registerCubeAllModelTexturePool(set.block())
					.stairs(set.stairs())
					.slab(set.slab());
		}

		private static void registerWoodBlockSetModels(BlockStateModelGenerator blockStateModelGenerator, WoodBlockSet set) {
			blockStateModelGenerator.registerCubeAllModelTexturePool(set.block())
					.stairs(set.stairs())
					.slab(set.slab())
					.fence(set.fence())
					.fenceGate(set.fenceGate());
		}

		private static void registerColoredBlockSetSetModels(BlockStateModelGenerator blockStateModelGenerator, ColoredBlockSetSet set) {
			set.iterator().forEachRemaining((bs) -> registerBlockSetModels(blockStateModelGenerator, bs));
		}

		private static void registerCarpet(BlockStateModelGenerator blockStateModelGenerator, Block wool, Block carpet) {
			Identifier identifier = TexturedModel.CARPET.get(wool).upload(carpet, blockStateModelGenerator.modelCollector);
			blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(carpet, identifier));
		}

		private void registerPillar(BlockStateModelGenerator blockStateModelGenerator, Block side, Block end) {
			TextureMap textureMap = TextureMap.sideEnd(TextureMap.getId(side), TextureMap.getId(end));
			Identifier identifier = Models.CUBE_COLUMN.upload(side, textureMap, blockStateModelGenerator.modelCollector);
			blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(side, identifier));
		}

		private void registerBars(BlockStateModelGenerator blockStateModelGenerator, Block bars) {
			Identifier identifier = ModelIds.getBlockSubModelId(bars, "_post_ends");
			Identifier identifier2 = ModelIds.getBlockSubModelId(bars, "_post");
			Identifier identifier3 = ModelIds.getBlockSubModelId(bars, "_cap");
			Identifier identifier4 = ModelIds.getBlockSubModelId(bars, "_cap_alt");
			Identifier identifier5 = ModelIds.getBlockSubModelId(bars, "_side");
			Identifier identifier6 = ModelIds.getBlockSubModelId(bars, "_side_alt");
			blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(bars).with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).with(When.create().set(Properties.NORTH, true).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)).with(When.create().set(Properties.NORTH, false).set(Properties.EAST, true).set(Properties.SOUTH, false).set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, true).set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4)).with(When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier6)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier6).put(VariantSettings.Y, VariantSettings.Rotation.R90)));
			blockStateModelGenerator.registerItemModel(bars);
		}
	}
}
