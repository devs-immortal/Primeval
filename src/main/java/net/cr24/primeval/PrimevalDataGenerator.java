package net.cr24.primeval;

import net.cr24.primeval.initialization.PrimevalTags;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.tint.GrassTintSource;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static net.cr24.primeval.initialization.PrimevalBlocks.*;
import static net.cr24.primeval.initialization.PrimevalItems.*;
import static net.minecraft.client.data.TextureMap.getSubId;
import static net.minecraft.client.data.TexturedModel.makeFactory;

public class PrimevalDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelProvider::new);
		pack.addProvider(RecipeProvider::new);
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

			blockStateModelGenerator.registerAxisRotated(OAK_LOG_BLOCK, TEXTURED_MODEL_LOG_COLUMN);
			blockStateModelGenerator.registerSingleton(OAK_LEAVES, TexturedModel.LEAVES);
			blockStateModelGenerator.registerAxisRotated(BIRCH_LOG_BLOCK, TEXTURED_MODEL_LOG_COLUMN);
			blockStateModelGenerator.registerSingleton(BIRCH_LEAVES, TexturedModel.LEAVES);
			blockStateModelGenerator.registerAxisRotated(SPRUCE_LOG_BLOCK, TEXTURED_MODEL_LOG_COLUMN);
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
			blockStateModelGenerator.registerAxisRotated(FRAMED_PILLAR_DAUB, TexturedModel.CUBE_COLUMN);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_CROSS_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_INVERTED_CROSS_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_X_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_PLUS_DAUB);
			blockStateModelGenerator.registerSimpleCubeAll(FRAMED_DIVIDED_DAUB);
			registerWoodBlockSetModels(blockStateModelGenerator, OAK_PLANK_BLOCKS);
			registerPillar(blockStateModelGenerator, OAK_PLANK_BLOCKS.panel(), OAK_PLANK_BLOCKS.block());
			blockStateModelGenerator.registerDoor(OAK_PLANK_BLOCKS.door());
			blockStateModelGenerator.registerTrapdoor(OAK_PLANK_BLOCKS.trapdoor());
			registerWoodBlockSetModels(blockStateModelGenerator, BIRCH_PLANK_BLOCKS);
			registerPillar(blockStateModelGenerator, BIRCH_PLANK_BLOCKS.panel(), BIRCH_PLANK_BLOCKS.block());
			blockStateModelGenerator.registerDoor(BIRCH_PLANK_BLOCKS.door());
			blockStateModelGenerator.registerTrapdoor(BIRCH_PLANK_BLOCKS.trapdoor());
			registerWoodBlockSetModels(blockStateModelGenerator, SPRUCE_PLANK_BLOCKS);
			registerPillar(blockStateModelGenerator, SPRUCE_PLANK_BLOCKS.panel(), SPRUCE_PLANK_BLOCKS.block());
			blockStateModelGenerator.registerDoor(SPRUCE_PLANK_BLOCKS.door());
			blockStateModelGenerator.registerTrapdoor(SPRUCE_PLANK_BLOCKS.trapdoor());
			registerBlockSetModels(blockStateModelGenerator, WICKER);
			blockStateModelGenerator.registerDoor(WICKER_DOOR);
			blockStateModelGenerator.registerTrapdoor(WICKER_TRAPDOOR);
			registerBars(blockStateModelGenerator, WICKER_BARS);
			blockStateModelGenerator.registerItemModel(ROPE.asItem());
			blockStateModelGenerator.registerAxisRotated(ROPE, ModelIds.getBlockModelId(ROPE));
			blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ROPE_LADDER);
			blockStateModelGenerator.registerItemModel(ROPE_LADDER);

			blockStateModelGenerator.registerSingleton(CRUDE_CRAFTING_BENCH, TexturedModel.CUBE_BOTTOM_TOP);
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			// block stuff
			itemModelGenerator.output.accept(GRASSY_DIRT.asItem(),
					ItemModels.tinted(ModelIds.getBlockModelId(GRASSY_DIRT), new GrassTintSource(0.7f, 1.0f))
			);
			itemModelGenerator.output.accept(GRASSY_CLAY.asItem(),
					ItemModels.tinted(ModelIds.getBlockModelId(GRASSY_CLAY), new GrassTintSource(0.7f, 1.0f))
			);
			itemModelGenerator.output.accept(FOSSIL.asItem(),
					ItemModels.basic(ModelIds.getBlockSubModelId(FOSSIL, "_2"))
			);
			itemModelGenerator.output.accept(OAK_PLANK_BLOCKS.logFence().asItem(),
					ItemModels.basic(ModelIds.getBlockSubModelId(OAK_PLANK_BLOCKS.logFence(), "_inventory"))
			);
			itemModelGenerator.output.accept(BIRCH_PLANK_BLOCKS.logFence().asItem(),
					ItemModels.basic(ModelIds.getBlockSubModelId(BIRCH_PLANK_BLOCKS.logFence(), "_inventory"))
			);
			itemModelGenerator.output.accept(SPRUCE_PLANK_BLOCKS.logFence().asItem(),
					ItemModels.basic(ModelIds.getBlockSubModelId(SPRUCE_PLANK_BLOCKS.logFence(), "_inventory"))
			);
			itemModelGenerator.output.accept(STRAW,
					ItemModels.basic(ModelIds.getItemModelId(STRAW))
			);

			// items
			itemModelGenerator.upload(STRAW, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(STICK, Items.STICK, Models.GENERATED);
			itemModelGenerator.register(OAK_LOG, Models.GENERATED);
			itemModelGenerator.register(BIRCH_LOG, Models.GENERATED);
			itemModelGenerator.register(SPRUCE_LOG, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(STRING, Items.STRING, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(FLINT, Items.FLINT, Models.GENERATED);
			itemModelGenerator.register(ROCK, Models.GENERATED);
			itemModelGenerator.register(STONE_BRICK, Models.GENERATED);
			itemModelGenerator.register(ASHES, Models.GENERATED);
			itemModelGenerator.register(CRUSHED_TERRACOTTA, Models.GENERATED);
			itemModelGenerator.register(CEMENT_MIX, Models.GENERATED);
			itemModelGenerator.register(CEMENT, Models.GENERATED);
			itemModelGenerator.register(BONE, Models.GENERATED);
			itemModelGenerator.register(BONEMEAL, Models.GENERATED);
			itemModelGenerator.register(ANIMAL_FAT, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(GUNPOWDER, Items.GUNPOWDER, Models.GENERATED);
			itemModelGenerator.register(CHARRED_BONE, Models.GENERATED);

			itemModelGenerator.register(SANDY_CLAY_BALL, Models.GENERATED);
			itemModelGenerator.register(SANDY_CLAY_BRICK, Models.GENERATED);
			itemModelGenerator.register(DRIED_BRICK, Models.GENERATED);
			itemModelGenerator.register(MUD_BALL, Models.GENERATED);
			itemModelGenerator.register(MUD_BRICK, Models.GENERATED);
			itemModelGenerator.register(CLAY_BALL, Models.GENERATED);
			itemModelGenerator.register(CLAY_BRICK, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_BRICK, Models.GENERATED);
			itemModelGenerator.register(CLAY_BOWL, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_BOWL, Models.GENERATED);
			itemModelGenerator.register(CLAY_TILE, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_TILE, Models.GENERATED);
			itemModelGenerator.register(CLAY_JUG, Models.GENERATED);
			itemModelGenerator.register(CLAY_VESSEL, Models.GENERATED);
			// foods
			itemModelGenerator.registerWithTextureSource(PORKCHOP, Items.PORKCHOP, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(CARROT, Items.CARROT, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(WHEAT, Items.WHEAT, Models.GENERATED);
			itemModelGenerator.register(CABBAGE, Models.GENERATED);
			itemModelGenerator.register(BEANS, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(POTATO, Items.POTATO, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(WHEAT_SEEDS, Items.WHEAT_SEEDS, Models.GENERATED);
			itemModelGenerator.register(CABBAGE_SEEDS, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(ROTTEN_FLESH, Items.ROTTEN_FLESH, Models.GENERATED);
			itemModelGenerator.registerWithTextureSource(SPIDER_EYE, Items.SPIDER_EYE, Models.GENERATED);
			// ores
			itemModelGenerator.register(RAW_COPPER_MALACHITE_SMALL, Models.GENERATED);
			itemModelGenerator.register(RAW_COPPER_MALACHITE_MEDIUM, Models.GENERATED);
			itemModelGenerator.register(RAW_COPPER_MALACHITE_LARGE, Models.GENERATED);
			itemModelGenerator.register(RAW_COPPER_NATIVE_SMALL, Models.GENERATED);
			itemModelGenerator.register(RAW_COPPER_NATIVE_MEDIUM, Models.GENERATED);
			itemModelGenerator.register(RAW_COPPER_NATIVE_LARGE, Models.GENERATED);
			itemModelGenerator.register(RAW_TIN_CASSITERITE_SMALL, Models.GENERATED);
			itemModelGenerator.register(RAW_TIN_CASSITERITE_MEDIUM, Models.GENERATED);
			itemModelGenerator.register(RAW_TIN_CASSITERITE_LARGE, Models.GENERATED);
			itemModelGenerator.register(RAW_ZINC_SPHALERITE_SMALL, Models.GENERATED);
			itemModelGenerator.register(RAW_ZINC_SPHALERITE_MEDIUM, Models.GENERATED);
			itemModelGenerator.register(RAW_ZINC_SPHALERITE_LARGE, Models.GENERATED);
			itemModelGenerator.register(RAW_GOLD_NATIVE_SMALL, Models.GENERATED);
			itemModelGenerator.register(RAW_GOLD_NATIVE_MEDIUM, Models.GENERATED);
			itemModelGenerator.register(RAW_GOLD_NATIVE_LARGE, Models.GENERATED);
			itemModelGenerator.register(RAW_IRON_HEMATITE_SMALL, Models.GENERATED);
			itemModelGenerator.register(RAW_IRON_HEMATITE_MEDIUM, Models.GENERATED);
			itemModelGenerator.register(RAW_IRON_HEMATITE_LARGE, Models.GENERATED);
			itemModelGenerator.register(RAW_LAZURITE_SMALL, Models.GENERATED);
			itemModelGenerator.register(RAW_LAZURITE_MEDIUM, Models.GENERATED);
			itemModelGenerator.register(RAW_LAZURITE_LARGE, Models.GENERATED);

			itemModelGenerator.register(FLINT_AXE, Models.HANDHELD);
			itemModelGenerator.register(FLINT_KNIFE, Models.HANDHELD);
			itemModelGenerator.register(FLINT_SHOVEL, Models.HANDHELD);
			itemModelGenerator.registerWithInHandModel(FLINT_SPEAR);
			registerToolSet(itemModelGenerator, COPPER_TOOLS);
			registerToolSet(itemModelGenerator, BRONZE_TOOLS);
			registerNormalItemSet(itemModelGenerator, COPPER_TOOL_PARTS);
			registerNormalItemSet(itemModelGenerator, BRONZE_TOOL_PARTS);
			itemModelGenerator.register(WOODEN_BUCKET, Models.GENERATED);
			itemModelGenerator.register(WOODEN_BUCKET_WATER, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_JUG, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_WATER_JUG, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_VESSEL, Models.GENERATED);
			itemModelGenerator.register(QUERN_WHEEL, Models.GENERATED);

			itemModelGenerator.register(CLAY_INGOT_MOLD, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_INGOT_MOLD, Models.GENERATED);
			itemModelGenerator.register(CLAY_AXE_HEAD_MOLD, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_AXE_HEAD_MOLD, Models.GENERATED);
			itemModelGenerator.register(CLAY_CHISEL_HEAD_MOLD, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_CHISEL_HEAD_MOLD, Models.GENERATED);
			itemModelGenerator.register(CLAY_KNIFE_BLADE_MOLD, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_KNIFE_BLADE_MOLD, Models.GENERATED);
			itemModelGenerator.register(CLAY_PICKAXE_HEAD_MOLD, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_PICKAXE_HEAD_MOLD, Models.GENERATED);
			itemModelGenerator.register(CLAY_SHOVEL_HEAD_MOLD, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_SHOVEL_HEAD_MOLD, Models.GENERATED);
			itemModelGenerator.register(CLAY_SWORD_BLADE_MOLD, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_SWORD_BLADE_MOLD, Models.GENERATED);
			itemModelGenerator.register(CLAY_HOE_HEAD_MOLD, Models.GENERATED);
			itemModelGenerator.register(FIRED_CLAY_HOE_HEAD_MOLD, Models.GENERATED);

			itemModelGenerator.register(COPPER_INGOT, Models.GENERATED);
			itemModelGenerator.register(COPPER_CHUNK, Models.GENERATED);
			itemModelGenerator.register(TIN_INGOT, Models.GENERATED);
			itemModelGenerator.register(TIN_CHUNK, Models.GENERATED);
			itemModelGenerator.register(ZINC_INGOT, Models.GENERATED);
			itemModelGenerator.register(ZINC_CHUNK, Models.GENERATED);

			itemModelGenerator.register(BRONZE_INGOT, Models.GENERATED);
			itemModelGenerator.register(BRONZE_CHUNK, Models.GENERATED);
			itemModelGenerator.register(BRASS_INGOT, Models.GENERATED);
			itemModelGenerator.register(BRASS_CHUNK, Models.GENERATED);
			itemModelGenerator.register(PEWTER_INGOT, Models.GENERATED);
			itemModelGenerator.register(PEWTER_CHUNK, Models.GENERATED);
			itemModelGenerator.register(GOLD_INGOT, Models.GENERATED);
			itemModelGenerator.register(GOLD_CHUNK, Models.GENERATED);
			itemModelGenerator.register(BOTCHED_ALLOY_INGOT, Models.GENERATED);
			itemModelGenerator.register(BOTCHED_ALLOY_CHUNK, Models.GENERATED);

			itemModelGenerator.register(WHITE_DYE, Models.GENERATED);
			itemModelGenerator.register(ORANGE_DYE, Models.GENERATED);
			itemModelGenerator.register(MAGENTA_DYE, Models.GENERATED);
			itemModelGenerator.register(LIGHT_BLUE_DYE, Models.GENERATED);
			itemModelGenerator.register(YELLOW_DYE, Models.GENERATED);
			itemModelGenerator.register(LIME_DYE, Models.GENERATED);
			itemModelGenerator.register(PINK_DYE, Models.GENERATED);
			itemModelGenerator.register(DARK_GRAY_DYE, Models.GENERATED);
			itemModelGenerator.register(LIGHT_GRAY_DYE, Models.GENERATED);
			itemModelGenerator.register(CYAN_DYE, Models.GENERATED);
			itemModelGenerator.register(PURPLE_DYE, Models.GENERATED);
			itemModelGenerator.register(BLUE_DYE, Models.GENERATED);
			itemModelGenerator.register(BROWN_DYE, Models.GENERATED);
			itemModelGenerator.register(GREEN_DYE, Models.GENERATED);
			itemModelGenerator.register(RED_DYE, Models.GENERATED);
			itemModelGenerator.register(BLACK_DYE, Models.GENERATED);

			itemModelGenerator.register(COPPER_COIN, Models.GENERATED);
			itemModelGenerator.register(GOLD_COIN, Models.GENERATED);
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

		private static void registerToolSet(ItemModelGenerator itemModelGenerator, ToolSet set) {
			itemModelGenerator.register(set.axe(), Models.HANDHELD);
			itemModelGenerator.register(set.chisel(), Models.HANDHELD);
			itemModelGenerator.register(set.knife(), Models.HANDHELD);
			itemModelGenerator.register(set.pickaxe(), Models.HANDHELD);
			itemModelGenerator.register(set.shovel(), Models.HANDHELD);
			itemModelGenerator.register(set.sword(), Models.HANDHELD);
			itemModelGenerator.register(set.hoe(), Models.HANDHELD);
			itemModelGenerator.registerWithInHandModel(set.spear());
		}

		private static <T extends Iterable<Item>> void registerNormalItemSet(ItemModelGenerator itemModelGenerator, T set) {
			set.iterator().forEachRemaining((b) -> itemModelGenerator.register(b, Models.GENERATED));
		}
	}

	private static class RecipeProvider extends FabricRecipeProvider {

		private static class PrimevalRecipeGenerator extends RecipeGenerator {

			protected PrimevalRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
				super(registries, exporter);
			}

			@Override
			public void generate() {
				// blocks
				this.createShapeless(RecipeCategory.BUILDING_BLOCKS, COARSE_DIRT, 2).input(DIRT).input(GRAVEL).criterion(hasItem(GRAVEL), this.conditionsFromItem(GRAVEL)).offerTo(this.exporter);
				this.offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, CLAY, CLAY_BALL);
				this.offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, MUD, MUD_BALL);
				this.createShapeless(RecipeCategory.BUILDING_BLOCKS, DRY_DIRT, 2).input(DIRT).input(SAND).criterion(hasItem(SAND), this.conditionsFromItem(SAND)).offerTo(this.exporter);
				this.offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, COBBLESTONE, ROCK);
				this.offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, SANDSTONE, SAND);

				this.offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, STRAW_BLOCK, STRAW);
				this.createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, STRAW_SLAB, Ingredient.ofItem(STRAW_BLOCK)).criterion(hasItem(STRAW_BLOCK), this.conditionsFromItem(STRAW_BLOCK)).offerTo(this.exporter);
				this.createStairsRecipe(STRAW_STAIRS, Ingredient.ofItem(STRAW_BLOCK)).criterion(hasItem(STRAW_BLOCK), this.conditionsFromItem(STRAW_BLOCK)).offerTo(this.exporter);
				this.offerCompactingRecipe(RecipeCategory.BUILDING_BLOCKS, STRAW_MESH, STRAW);
				this.offerCarpetRecipe(STRAW_MAT, STRAW_MESH);

				offerShapelessColoredBlockSet(TERRACOTTA, COLORED_TERRACOTTA, "colored_terracotta");

				this.offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, FIRED_CLAY_SHINGLE_BLOCKS.block(), FIRED_CLAY_TILE, 4);
				offerBlockSet(FIRED_CLAY_SHINGLE_BLOCKS);
				offerShapelessColoredBlockSet(FIRED_CLAY_SHINGLE_BLOCKS.block(), new ColoredBlockSet(
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.white().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.orange().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.magenta().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.lightBlue().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.yellow().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.lime().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.pink().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.darkGray().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.lightGray().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.cyan().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.purple().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.blue().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.brown().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.green().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.red().block(),
						COLORED_FIRED_CLAY_SHINGLE_BLOCKS.black().block()
				), "colored_terracotta");
				offerColoredBlockSetSet(COLORED_FIRED_CLAY_SHINGLE_BLOCKS);

				offer2x2CrossRecipe(RecipeCategory.BUILDING_BLOCKS, FIRED_CLAY_BRICK_BLOCKS.block(), FIRED_CLAY_BRICK, PrimevalTags.Items.MORTAR, 2);
				offerBlockSet(FIRED_CLAY_BRICK_BLOCKS);
				offer2x2CrossRecipe(RecipeCategory.BUILDING_BLOCKS, FIRED_CLAY_TILES_BLOCKS.block(), FIRED_CLAY_TILE, PrimevalTags.Items.MORTAR, 2);
				offerBlockSet(FIRED_CLAY_TILES_BLOCKS);
				offer2x2CrossRecipe(RecipeCategory.BUILDING_BLOCKS, DRIED_BRICK_BLOCKS.block(), DRIED_BRICK, PrimevalTags.Items.MORTAR, 2);
				offerBlockSet(DRIED_BRICK_BLOCKS);

				this.offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, MUD_BRICKS.block(), MUD_BRICK, 2);
				offerBlockSet(MUD_BRICKS);

				offer2x2CrossRecipe(RecipeCategory.BUILDING_BLOCKS, CRUDE_BRICKS.block(), ROCK, PrimevalTags.Items.MORTAR, 2);
				offerBlockSet(CRUDE_BRICKS);
				offer2x2CrossRecipe(RecipeCategory.BUILDING_BLOCKS, STONE_BRICKS.block(), STONE_BRICK, PrimevalTags.Items.MORTAR, 2);
				offerBlockSet(STONE_BRICKS);
				offerBlockSet(SMOOTH_STONE);
				offer2x2CrossRecipe(RecipeCategory.BUILDING_BLOCKS, STONE_PAVER.block(), STONE_BRICK, DIRT, 2);
				offerBlockSet(STONE_PAVER);


				this.createShapeless(RecipeCategory.BUILDING_BLOCKS, DAUB, 4).input(SAND).input(CLAY_BALL).input(STICK).input(STRAW).criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_DAUB, 5).input('D', DAUB).input('S', STICK).pattern("SDS").pattern("DDD").pattern("SDS").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_PILLAR_DAUB, 3).input('D', DAUB).input('S', STICK).pattern("SDS").pattern("SDS").pattern("SDS").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_CROSS_DAUB, 2).input('D', DAUB).input('S', STICK).pattern("DS").pattern("SD").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_INVERTED_CROSS_DAUB, 2).input('D', DAUB).input('S', STICK).pattern("SD").pattern("DS").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_X_DAUB, 4).input('D', DAUB).input('S', STICK).pattern("SDS").pattern("DSD").pattern("SDS").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_PLUS_DAUB, 2).input('D', DAUB).input('S', STICK).pattern("DSD").pattern("SSS").pattern("DSD").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_DIVIDED_DAUB, 2).input('D', DAUB).input('S', STICK).pattern(" S ").pattern("DSD").pattern(" S ").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);

			}

			private void offerShapelessColoredBlockSet(ItemConvertible base, ColoredBlockSet set, String group) {
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.white(), 8).input('#', base).input('X', WHITE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.orange(), 8).input('#', base).input('X', ORANGE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.magenta(), 8).input('#', base).input('X', MAGENTA_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.lightBlue(), 8).input('#', base).input('X', LIGHT_BLUE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.yellow(), 8).input('#', base).input('X', YELLOW_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.lime(), 8).input('#', base).input('X', LIME_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.pink(), 8).input('#', base).input('X', PINK_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.darkGray(), 8).input('#', base).input('X', DARK_GRAY_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.lightGray(), 8).input('#', base).input('X', LIGHT_GRAY_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.cyan(), 8).input('#', base).input('X', CYAN_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.purple(), 8).input('#', base).input('X', PURPLE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.blue(), 8).input('#', base).input('X', BLUE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.brown(), 8).input('#', base).input('X', BROWN_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.green(), 8).input('#', base).input('X', GREEN_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.red(), 8).input('#', base).input('X', RED_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.black(), 8).input('#', base).input('X', BLACK_DYE).pattern("###").pattern("#X#").pattern("###").group(group).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
			}

			private void offerBlockSet(BlockSet set) {
				var base = set.block();
				this.createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, set.slab(), Ingredient.ofItem(base)).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createStairsRecipe(set.stairs(), Ingredient.ofItem(base)).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
			}

			private void offerColoredBlockSetSet(ColoredBlockSetSet set) {
				for (BlockSet color : set) {
					offerBlockSet(color);
				}
			}

			private void offer2x2CompactingRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible input, int amount) {
				this.createShaped(category, output, amount).input('#', input).pattern("##").pattern("##").criterion(hasItem(input), this.conditionsFromItem(input)).offerTo(this.exporter);
			}

			private void offer2x2CrossRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible input1, ItemConvertible input2, int amount) {
				this.createShaped(category, output, amount).input('A', input1).input('B', input2).pattern("AB").pattern("BA").criterion(hasItem(input1), this.conditionsFromItem(input1)).offerTo(this.exporter);
			}

			private void offer2x2CrossRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible input1, TagKey<Item> input2, int amount) {
				this.createShaped(category, output, amount).input('A', input1).input('B', input2).pattern("AB").pattern("BA").criterion(hasItem(input1), this.conditionsFromItem(input1)).offerTo(this.exporter);
			}

		}

		public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
			return new PrimevalRecipeGenerator(wrapperLookup, recipeExporter);
		}

		@Override
		public String getName() {
			return "Primeval Recipe Provider";
		}
	}
}
