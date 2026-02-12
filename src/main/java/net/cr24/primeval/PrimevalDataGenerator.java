package net.cr24.primeval;

import net.cr24.primeval.fluid.PrimevalFluids;
import net.cr24.primeval.initialization.PrimevalBlocks.BlockSet;
import net.cr24.primeval.initialization.PrimevalBlocks.ColoredBlockSet;
import net.cr24.primeval.initialization.PrimevalBlocks.ColoredBlockSetSet;
import net.cr24.primeval.initialization.PrimevalBlocks.WoodBlockSet;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalItems.ToolPartSet;
import net.cr24.primeval.initialization.PrimevalItems.ToolSet;
import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.item.property.FluidContentProperty;
import net.cr24.primeval.world.gen.feature.PrimevalFeatures;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.tint.GrassTintSource;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.data.loottable.LootTableGenerator;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.cr24.primeval.initialization.PrimevalBlocks.*;
import static net.cr24.primeval.initialization.PrimevalItems.*;
import static net.cr24.primeval.initialization.PrimevalItems.RAW_COPPER_NATIVE_SMALL;
import static net.minecraft.client.data.TextureMap.getSubId;
import static net.minecraft.client.data.TexturedModel.makeFactory;

public class PrimevalDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelProvider::new);
		pack.addProvider(RecipeProvider::new);
		pack.addProvider(LootTableProvider::new);
		pack.addProvider(PrimevalFeatures::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, PrimevalFeatures::bootstrapConfiguredFeatures);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, PrimevalFeatures::bootstrapPlacedFeatures);
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
			blockStateModelGenerator.registerAxisRotated(ROPE, BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockModelId(ROPE)));
			blockStateModelGenerator.registerNorthDefaultHorizontalRotatable(ROPE_LADDER);
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
			itemModelGenerator.output.accept(LIT_CRUDE_TORCH,
					ItemModels.basic(ModelIds.getItemSubModelId(CRUDE_TORCH.asItem(), "_lit"))
			);
			itemModelGenerator.output.accept(UNLIT_CRUDE_TORCH,
					ItemModels.basic(ModelIds.getItemSubModelId(CRUDE_TORCH.asItem(), ""))
			);

			// items
			itemModelGenerator.upload(STRAW, Models.GENERATED);
			itemModelGenerator.upload(LIT_CRUDE_TORCH, Models.GENERATED);
			itemModelGenerator.upload(UNLIT_CRUDE_TORCH, Models.GENERATED);
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
			registerFiredMold(itemModelGenerator, FIRED_CLAY_INGOT_MOLD, PrimevalFluids.ALL_MOLD_FLUIDS);
			itemModelGenerator.register(CLAY_AXE_HEAD_MOLD, Models.GENERATED);
			registerFiredMold(itemModelGenerator, FIRED_CLAY_AXE_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
			itemModelGenerator.register(CLAY_CHISEL_HEAD_MOLD, Models.GENERATED);
			registerFiredMold(itemModelGenerator, FIRED_CLAY_CHISEL_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
			itemModelGenerator.register(CLAY_KNIFE_BLADE_MOLD, Models.GENERATED);
			registerFiredMold(itemModelGenerator, FIRED_CLAY_KNIFE_BLADE_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
			itemModelGenerator.register(CLAY_PICKAXE_HEAD_MOLD, Models.GENERATED);
			registerFiredMold(itemModelGenerator, FIRED_CLAY_PICKAXE_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
			itemModelGenerator.register(CLAY_SHOVEL_HEAD_MOLD, Models.GENERATED);
			registerFiredMold(itemModelGenerator, FIRED_CLAY_SHOVEL_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
			itemModelGenerator.register(CLAY_SWORD_BLADE_MOLD, Models.GENERATED);
			registerFiredMold(itemModelGenerator, FIRED_CLAY_SWORD_BLADE_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
			itemModelGenerator.register(CLAY_HOE_HEAD_MOLD, Models.GENERATED);
			registerFiredMold(itemModelGenerator, FIRED_CLAY_HOE_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
			itemModelGenerator.register(CLAY_PROSPECTING_PICKAXE_HEAD_MOLD, Models.GENERATED);
			registerFiredMold(itemModelGenerator, FIRED_CLAY_PROSPECTING_PICKAXE_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);

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
            WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(TexturedModel.CARPET.get(wool).upload(carpet, blockStateModelGenerator.modelCollector));
			blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(carpet, weightedVariant));
		}

		private void registerPillar(BlockStateModelGenerator blockStateModelGenerator, Block side, Block end) {
			TextureMap textureMap = TextureMap.sideEnd(TextureMap.getId(side), TextureMap.getId(end));
            WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(Models.CUBE_COLUMN.upload(side, textureMap, blockStateModelGenerator.modelCollector));
			blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(side, weightedVariant));
		}

		private void registerBars(BlockStateModelGenerator blockStateModelGenerator, Block bars) {
            WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockSubModelId(bars, "_post_ends"));
            WeightedVariant weightedVariant2 = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockSubModelId(bars, "_post"));
            WeightedVariant weightedVariant3 = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockSubModelId(bars, "_cap"));
            WeightedVariant weightedVariant4 = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockSubModelId(bars, "_cap_alt"));
            WeightedVariant weightedVariant5 = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockSubModelId(bars, "_side"));
            WeightedVariant weightedVariant6 = BlockStateModelGenerator.createWeightedVariant(ModelIds.getBlockSubModelId(bars, "_side_alt"));
			blockStateModelGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(bars).with(weightedVariant).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, false).put(Properties.SOUTH, false).put(Properties.WEST, false), weightedVariant2).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.NORTH, true).put(Properties.EAST, false).put(Properties.SOUTH, false).put(Properties.WEST, false), weightedVariant3).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, true).put(Properties.SOUTH, false).put(Properties.WEST, false), weightedVariant3.apply(BlockStateModelGenerator.ROTATE_Y_90)).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, false).put(Properties.SOUTH, true).put(Properties.WEST, false), weightedVariant4).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, false).put(Properties.SOUTH, false).put(Properties.WEST, true), weightedVariant4.apply(BlockStateModelGenerator.ROTATE_Y_90)).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.NORTH, true), weightedVariant5).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.EAST, true), weightedVariant5.apply(BlockStateModelGenerator.ROTATE_Y_90)).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.SOUTH, true), weightedVariant6).with(BlockStateModelGenerator.createMultipartConditionBuilder().put(Properties.WEST, true), weightedVariant6.apply(BlockStateModelGenerator.ROTATE_Y_90)));
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
			itemModelGenerator.register(set.prospecting_pickaxe(), Models.HANDHELD);
			itemModelGenerator.registerWithInHandModel(set.spear());
		}

		private static <T extends Iterable<Item>> void registerNormalItemSet(ItemModelGenerator itemModelGenerator, T set) {
			set.iterator().forEachRemaining((b) -> itemModelGenerator.register(b, Models.GENERATED));
		}

		public final void registerFiredMold(ItemModelGenerator itemModelGenerator, Item item, List<Fluid> validFluids) {
			Identifier id = ModelIds.getItemModelId(item);
			Identifier identifier2 = TextureMap.getId(item);
			List<SelectItemModel.SwitchCase<RegistryKey<Fluid>>> list = new ArrayList(validFluids.size());

			for (var f : validFluids) {
				var filledId = id.withSuffixedPath("_" + f.getRegistryEntry().getKey().get().getValue().getPath());
				list.add(ItemModels.switchCase(f.getRegistryEntry().registryKey(), ItemModels.basic(filledId)));
				Models.GENERATED.upload(filledId, TextureMap.layer0(filledId), itemModelGenerator.modelCollector);
			}

			Models.GENERATED.upload(id, TextureMap.layer0(identifier2), itemModelGenerator.modelCollector);
			ItemModel.Unbaked unbaked2 = ItemModels.basic(id);
			itemModelGenerator.output.accept(item, ItemModels.select(new FluidContentProperty(), unbaked2, list));
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


				this.createShapeless(RecipeCategory.BUILDING_BLOCKS, DAUB, 4).input(SAND).input(CLAY_BALL).input(STRAW).criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_DAUB, 5).input('D', DAUB).input('S', STICK).pattern("SDS").pattern("DDD").pattern("SDS").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_PILLAR_DAUB, 3).input('D', DAUB).input('S', STICK).pattern("SDS").pattern("SDS").pattern("SDS").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_CROSS_DAUB, 2).input('D', DAUB).input('S', STICK).pattern("DS").pattern("SD").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_INVERTED_CROSS_DAUB, 2).input('D', DAUB).input('S', STICK).pattern("SD").pattern("DS").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_X_DAUB, 4).input('D', DAUB).input('S', STICK).pattern("SDS").pattern("DSD").pattern("SDS").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_PLUS_DAUB, 2).input('D', DAUB).input('S', STICK).pattern("DSD").pattern("SSS").pattern("DSD").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_DIVIDED_DAUB, 2).input('D', DAUB).input('S', STICK).pattern(" S ").pattern("DSD").pattern(" S ").group("framed_daub").criterion(hasItem(DAUB), this.conditionsFromItem(DAUB)).offerTo(this.exporter);

				this.offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, WICKER.block(), STICK, 2);
				offerWoodBlockSet(OAK_PLANK_BLOCKS, OAK_LOG);
				offerWoodBlockSet(BIRCH_PLANK_BLOCKS, BIRCH_LOG);
				offerWoodBlockSet(SPRUCE_PLANK_BLOCKS, SPRUCE_LOG);
				offerBlockSet(WICKER);
				this.createDoorRecipe(WICKER_DOOR, Ingredient.ofItem(WICKER.block())).criterion(hasItem(WICKER.block()), this.conditionsFromItem(WICKER.block())).offerTo(this.exporter);
				this.createTrapdoorRecipe(WICKER_TRAPDOOR, Ingredient.ofItem(WICKER.block())).criterion(hasItem(WICKER.block()), this.conditionsFromItem(WICKER.block())).offerTo(this.exporter);
				this.createShaped(RecipeCategory.DECORATIONS, WICKER_BARS, 16).input('#', WICKER.block()).pattern("###").pattern("###").criterion(hasItem(WICKER.block()), this.conditionsFromItem(WICKER.block())).offerTo(this.exporter);

				this.createShaped(RecipeCategory.DECORATIONS, ROPE, 3).input('#', STRAW).pattern("#").pattern("#").pattern("#").criterion(hasItem(STRAW), this.conditionsFromItem(STRAW)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.DECORATIONS, ROPE_LADDER, 6).input('#', ROPE).input('X', STICK).pattern("#X#").pattern("#X#").pattern("#X#").criterion(hasItem(ROPE), this.conditionsFromItem(ROPE)).offerTo(this.exporter);

				offerCrateRecipe(OAK_CRATE, OAK_PLANK_BLOCKS);
				offerCrateRecipe(BIRCH_CRATE, BIRCH_PLANK_BLOCKS);
				offerCrateRecipe(SPRUCE_CRATE, SPRUCE_PLANK_BLOCKS);

				this.createShaped(RecipeCategory.DECORATIONS, LARGE_CLAY_POT).input('B', CLAY).input('C', CLAY_BALL).pattern("C C").pattern("C C").pattern("CBC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShapeless(RecipeCategory.DECORATIONS, LARGE_FIRED_CLAY_POT).input(LARGE_DECORATIVE_FIRED_CLAY_POT).criterion(hasItem(LARGE_DECORATIVE_FIRED_CLAY_POT), this.conditionsFromItem(LARGE_DECORATIVE_FIRED_CLAY_POT)).offerTo(this.exporter);
				this.createShapeless(RecipeCategory.DECORATIONS, LARGE_DECORATIVE_FIRED_CLAY_POT).input(LARGE_FIRED_CLAY_POT).input(DIRT).criterion(hasItem(LARGE_FIRED_CLAY_POT), this.conditionsFromItem(LARGE_FIRED_CLAY_POT)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.DECORATIONS, WICKER_BASKET).input('W', WICKER.block()).pattern(" W ").pattern("W W").pattern("WWW").criterion(hasItem(WICKER.block()), this.conditionsFromItem(WICKER.block())).offerTo(this.exporter);

				this.createShaped(RecipeCategory.DECORATIONS, CRUDE_CRAFTING_BENCH).input('P', PrimevalTags.Items.PLANKS).input('S', STRAW).pattern("SS").pattern("PP").criterion("has_planks", this.conditionsFromTag(PrimevalTags.Items.PLANKS)).offerTo(this.exporter);

				// items
				this.createShapeless(RecipeCategory.MISC, STRAW, 4).input(STRAW_BLOCK).criterion(hasItem(STRAW_BLOCK), this.conditionsFromItem(STRAW_BLOCK)).offerTo(this.exporter);
				this.createShapeless(RecipeCategory.MISC, ROCK, 4).input(COBBLESTONE).criterion(hasItem(COBBLESTONE), this.conditionsFromItem(COBBLESTONE)).offerTo(this.exporter);
				this.createShapeless(RecipeCategory.MISC, STICK, 2).input(REEDS).criterion(hasItem(REEDS), this.conditionsFromItem(REEDS)).offerTo(this.exporter);

				this.createShapeless(RecipeCategory.MISC, CEMENT_MIX, 4).input(CRUSHED_TERRACOTTA).input(ASHES).input(ASHES).criterion(hasItem(ASHES), this.conditionsFromItem(ASHES)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CEMENT, 12).input('W', WOODEN_BUCKET_WATER).input('C', CEMENT_MIX).pattern("CCC").pattern("CWC").pattern("CCC").criterion(hasItem(CEMENT_MIX), this.conditionsFromItem(CEMENT_MIX)).offerTo(this.exporter);

				this.createShapeless(RecipeCategory.MISC, SANDY_CLAY_BALL, 2).input(SAND).input(CLAY_BALL).criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, SANDY_CLAY_BRICK, 2).input('C', SANDY_CLAY_BALL).pattern("CCC").criterion(hasItem(SANDY_CLAY_BALL), this.conditionsFromItem(SANDY_CLAY_BALL)).offerTo(this.exporter);
				this.createShapeless(RecipeCategory.MISC, MUD_BALL, 4).input(MUD).criterion(hasItem(MUD), this.conditionsFromItem(MUD)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, MUD_BRICK, 2).input('C', MUD_BALL).pattern("CCC").criterion(hasItem(MUD_BALL), this.conditionsFromItem(MUD_BALL)).offerTo(this.exporter);

				this.createShapeless(RecipeCategory.MISC, CLAY_BALL, 4).input(CLAY).criterion(hasItem(CLAY), this.conditionsFromItem(CLAY)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_BRICK, 2).input('C', CLAY_BALL).pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_BOWL).input('C', CLAY_BALL).pattern("C C").pattern(" C ").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_TILE, 2).input('C', CLAY_BALL).pattern("CC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_JUG).input('C', CLAY_BALL).pattern("CC ").pattern("C C").pattern("CC ").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_VESSEL).input('C', CLAY_BALL).pattern("C C").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);

				this.createShaped(RecipeCategory.TOOLS, FLINT_AXE).input('F', FLINT).input('S', STICK).input('T', STRAW).pattern("FT").pattern("FS").criterion(hasItem(FLINT), this.conditionsFromItem(FLINT)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.TOOLS, FLINT_KNIFE).input('F', FLINT).input('S', STICK).input('T', STRAW).pattern("TF").pattern("S ").criterion(hasItem(FLINT), this.conditionsFromItem(FLINT)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.TOOLS, FLINT_SHOVEL).input('F', FLINT).input('S', STICK).input('T', STRAW).pattern("TF").pattern(" S").criterion(hasItem(FLINT), this.conditionsFromItem(FLINT)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.TOOLS, FLINT_SPEAR).input('F', FLINT).input('S', STICK).input('T', STRAW).pattern("  F").pattern(" ST").pattern("S  ").criterion(hasItem(FLINT), this.conditionsFromItem(FLINT)).offerTo(this.exporter);
				offerToolSet(COPPER_TOOLS, COPPER_TOOL_PARTS);
				offerToolSet(BRONZE_TOOLS, BRONZE_TOOL_PARTS);

				this.createShaped(RecipeCategory.TOOLS, WOODEN_BUCKET).input('P', PrimevalTags.Items.PLANKS).pattern("P P").pattern(" P ").criterion("has_planks", this.conditionsFromTag(PrimevalTags.Items.PLANKS)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, QUERN_WHEEL).input('Q', SMOOTH_STONE.slab()).input('S', STICK).pattern("S").pattern("Q").criterion(hasItem(SMOOTH_STONE.block()), this.conditionsFromItem(SMOOTH_STONE.block())).offerTo(this.exporter);

				this.createShaped(RecipeCategory.MISC, CLAY_INGOT_MOLD).input('C', CLAY_BALL).pattern("CCC").pattern("   ").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_AXE_HEAD_MOLD).input('C', CLAY_BALL).pattern("  C").pattern(" CC").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_CHISEL_HEAD_MOLD).input('C', CLAY_BALL).pattern("CC ").pattern("C C").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_KNIFE_BLADE_MOLD).input('C', CLAY_BALL).pattern("CCC").pattern("C C").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_PICKAXE_HEAD_MOLD).input('C', CLAY_BALL).pattern("CCC").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_SHOVEL_HEAD_MOLD).input('C', CLAY_BALL).pattern("C C").pattern("CCC").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_SWORD_BLADE_MOLD).input('C', CLAY_BALL).pattern("C C").pattern("C C").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_HOE_HEAD_MOLD).input('C', CLAY_BALL).pattern("C  ").pattern("CCC").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.MISC, CLAY_PROSPECTING_PICKAXE_HEAD_MOLD).input('C', CLAY_BALL).pattern("  C").pattern("CC ").pattern("CCC").criterion(hasItem(CLAY_BALL), this.conditionsFromItem(CLAY_BALL)).offerTo(this.exporter);

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

			private void offerWoodBlockSet(WoodBlockSet set, ItemConvertible log) {
				var base = set.block();
				this.createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, set.slab(), Ingredient.ofItem(base)).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createStairsRecipe(set.stairs(), Ingredient.ofItem(base)).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createShaped(RecipeCategory.BUILDING_BLOCKS, set.panel(), 4).input('#', base).input('X', STICK).pattern("###").pattern("XXX").pattern("###").criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				offerFenceRecipe(RecipeCategory.DECORATIONS, set.fence(), set.block(), STICK, 3);
				offerFenceRecipe(RecipeCategory.DECORATIONS, set.logFence(), log, STICK, 3);
				offerFenceRecipe(RecipeCategory.DECORATIONS, set.fenceGate(), STICK, set.block(), 2);
				this.createDoorRecipe(set.door(), Ingredient.ofItem(set.block())).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
				this.createTrapdoorRecipe(set.trapdoor(), Ingredient.ofItem(set.block())).criterion(hasItem(base), this.conditionsFromItem(base)).offerTo(this.exporter);
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

			private void offerFenceRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible inputSide, ItemConvertible inputMiddle, int amount) {
				this.createShaped(category, output, amount).input('W', inputSide).input('#', inputMiddle).pattern("W#W").pattern("W#W").criterion(hasItem(inputSide), this.conditionsFromItem(inputSide)).offerTo(this.exporter);
			}

			private void offerCrateRecipe(ItemConvertible crate, WoodBlockSet set) {
				this.createShaped(RecipeCategory.DECORATIONS, crate, 1).input('W', set.block()).input('S', set.slab()).pattern("SSS").pattern("W W").pattern("WWW").criterion(hasItem(set.block()), this.conditionsFromItem(set.block())).offerTo(this.exporter);
			}

			private void offerToolSet(ToolSet toolSet, ToolPartSet toolPartSet) {
				offerToolAssembly(RecipeCategory.TOOLS, toolSet.axe(), toolPartSet.axe_head());
				offerToolAssembly(RecipeCategory.TOOLS, toolSet.chisel(), toolPartSet.chisel_head());
				offerToolAssembly(RecipeCategory.TOOLS, toolSet.knife(), toolPartSet.knife_blade());
				offerToolAssembly(RecipeCategory.TOOLS, toolSet.pickaxe(), toolPartSet.pickaxe_head());
				offerToolAssembly(RecipeCategory.TOOLS, toolSet.shovel(), toolPartSet.shovel_head());
				offerToolAssembly(RecipeCategory.COMBAT, toolSet.sword(), toolPartSet.sword_blade());
				offerToolAssembly(RecipeCategory.TOOLS, toolSet.hoe(), toolPartSet.hoe_head());
				offerToolAssembly(RecipeCategory.TOOLS, toolSet.prospecting_pickaxe(), toolPartSet.prospecting_pickaxe_head());
				this.createShaped(RecipeCategory.COMBAT, toolSet.spear()).input('H', toolPartSet.sword_blade()).input('S', STICK).pattern("  H").pattern(" S ").pattern("S  ").criterion(hasItem(toolPartSet.sword_blade()), this.conditionsFromItem(toolPartSet.sword_blade())).offerTo(this.exporter);

			}

			private void offerToolAssembly(RecipeCategory category, ItemConvertible tool, ItemConvertible head) {
				this.createShapeless(category, tool).input(STICK).input(head).criterion(hasItem(head), this.conditionsFromItem(head)).offerTo(this.exporter);
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

	private static class LootTableProvider extends FabricBlockLootTableProvider {

		protected LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generate() {
			// terrain
			addDrop(DIRT);
			addDrop(COARSE_DIRT);
			addDrop(CLAY, (block -> this.drops(CLAY_BALL, ConstantLootNumberProvider.create(4))));
			addDrop(MUD, (block -> this.drops(MUD_BALL, ConstantLootNumberProvider.create(4))));
			addDrop(DRY_DIRT);
			addDrop(GRASSY_DIRT, (block -> this.drops(DIRT)));
			addDrop(GRASSY_CLAY, (block -> this.drops(CLAY_BALL, ConstantLootNumberProvider.create(4))));
			addDrop(SAND);
			addDrop(GRAVEL);
			addDrop(COBBLESTONE);
			addDrop(STONE, (block -> this.stoneDrops(block, ROCK)));
			addDrop(SANDSTONE, (block -> this.drops(SAND, UniformLootNumberProvider.create(2, 4))));
			addDrop(DIRT_FARMLAND, (block -> this.drops(DIRT)));
			addDrop(CLAY_FARMLAND, (block -> this.drops(CLAY_BALL, ConstantLootNumberProvider.create(4))));

			// plants
			addDrop(OAK_LOG_BLOCK, (block -> this.drops(OAK_LOG)));
			addDrop(BIRCH_LOG_BLOCK, (block -> this.drops(BIRCH_LOG)));
			addDrop(SPRUCE_LOG_BLOCK, (block -> this.drops(SPRUCE_LOG)));
			addDrop(OAK_LEAVES, (block -> this.leafDrops(OAK_SAPLING)));
			addDrop(BIRCH_LEAVES, (block -> this.leafDrops(BIRCH_SAPLING)));
			addDrop(SPRUCE_LEAVES, (block -> this.leafDrops(SPRUCE_SAPLING)));
			addDrop(OAK_SAPLING);
			addDrop(BIRCH_SAPLING);
			addDrop(SPRUCE_SAPLING);
			addDrop(GRASS, (block -> this.brushDrops()));
			addDrop(BUSH, (block -> this.brushDrops()));
			addDrop(SPIKED_PLANT, (block -> this.brushDrops()));
			addDrop(LEAFY_PLANT, (block -> this.brushDrops()));
			addDrop(SHRUB, (block -> this.dropsWithKnife(SHRUB)));
			//addDrop(MOSS, (block -> this.dropsWithKnife(MOSS)));
			// flowers
			addDrop(POPPY, (block -> this.dropsWithKnife(POPPY)));
			addDrop(DANDELION, (block -> this.dropsWithKnife(DANDELION)));
			addDrop(OXEYE_DAISY, (block -> this.dropsWithKnife(OXEYE_DAISY)));
			addDrop(CORNFLOWER, (block -> this.dropsWithKnife(CORNFLOWER)));
			addDrop(LILY_OF_THE_VALLEY, (block -> this.dropsWithKnife(LILY_OF_THE_VALLEY)));
			// misc
			addDrop(REEDS);
			//addDrop(RIVER_GRASS);

			// ores
			addDrop(COPPER_MALACHITE_ORE.large(), (block -> this.oreSetDrops(COPPER_MALACHITE_ORE.large(), RAW_COPPER_MALACHITE_LARGE)));
			addDrop(COPPER_MALACHITE_ORE.medium(), (block -> this.oreSetDrops(COPPER_MALACHITE_ORE.medium(), RAW_COPPER_MALACHITE_MEDIUM)));
			addDrop(COPPER_MALACHITE_ORE.small(), (block -> this.oreSetDrops(COPPER_MALACHITE_ORE.small(), RAW_COPPER_MALACHITE_SMALL)));
			addDrop(COPPER_NATIVE_ORE.large(), (block -> this.oreSetDrops(COPPER_NATIVE_ORE.large(), RAW_COPPER_NATIVE_LARGE)));
			addDrop(COPPER_NATIVE_ORE.medium(), (block -> this.oreSetDrops(COPPER_NATIVE_ORE.medium(), RAW_COPPER_NATIVE_MEDIUM)));
			addDrop(COPPER_NATIVE_ORE.small(), (block -> this.oreSetDrops(COPPER_NATIVE_ORE.small(), RAW_COPPER_NATIVE_SMALL)));
			addDrop(TIN_CASSITERITE_ORE.large(), (block -> this.oreSetDrops(TIN_CASSITERITE_ORE.large(), RAW_TIN_CASSITERITE_LARGE)));
			addDrop(TIN_CASSITERITE_ORE.medium(), (block -> this.oreSetDrops(TIN_CASSITERITE_ORE.medium(), RAW_TIN_CASSITERITE_MEDIUM)));
			addDrop(TIN_CASSITERITE_ORE.small(), (block -> this.oreSetDrops(TIN_CASSITERITE_ORE.small(), RAW_TIN_CASSITERITE_SMALL)));
			addDrop(GOLD_NATIVE_ORE.large(), (block -> this.oreSetDrops(GOLD_NATIVE_ORE.large(), RAW_GOLD_NATIVE_LARGE)));
			addDrop(GOLD_NATIVE_ORE.medium(), (block -> this.oreSetDrops(GOLD_NATIVE_ORE.medium(), RAW_GOLD_NATIVE_MEDIUM)));
			addDrop(GOLD_NATIVE_ORE.small(), (block -> this.oreSetDrops(GOLD_NATIVE_ORE.small(), RAW_GOLD_NATIVE_SMALL)));
			addDrop(IRON_HEMATITE_ORE.large(), (block -> this.oreSetDrops(IRON_HEMATITE_ORE.large(), RAW_IRON_HEMATITE_LARGE)));
			addDrop(IRON_HEMATITE_ORE.medium(), (block -> this.oreSetDrops(IRON_HEMATITE_ORE.medium(), RAW_IRON_HEMATITE_MEDIUM)));
			addDrop(IRON_HEMATITE_ORE.small(), (block -> this.oreSetDrops(IRON_HEMATITE_ORE.small(), RAW_IRON_HEMATITE_SMALL)));
			addDrop(LAZURITE_ORE.large(), (block -> this.oreSetDrops(LAZURITE_ORE.large(), RAW_LAZURITE_LARGE)));
			addDrop(LAZURITE_ORE.medium(), (block -> this.oreSetDrops(LAZURITE_ORE.medium(), RAW_LAZURITE_MEDIUM)));
			addDrop(LAZURITE_ORE.small(), (block -> this.oreSetDrops(LAZURITE_ORE.small(), RAW_LAZURITE_SMALL)));
			addDrop(FOSSIL, (block -> this.fossilDrops(FOSSIL)));

			// crafted blocks
			addDrop(STRAW_BLOCK);
			addDrop(STRAW_STAIRS);
			addDrop(STRAW_SLAB, this::slabDrops);
			addDrop(STRAW_MESH);
			addDrop(STRAW_MAT);
			addDrop(TERRACOTTA);
			coloredBlockSetDrops(COLORED_TERRACOTTA);
			blockSetDrops(FIRED_CLAY_SHINGLE_BLOCKS);
			coloredBlockSetSetDrops(COLORED_FIRED_CLAY_SHINGLE_BLOCKS);
			blockSetDrops(FIRED_CLAY_BRICK_BLOCKS);
			blockSetDrops(FIRED_CLAY_TILES_BLOCKS);
			blockSetDrops(DRIED_BRICK_BLOCKS);
			blockSetDrops(MUD_BRICKS);
			blockSetDrops(CRUDE_BRICKS);
			blockSetDrops(STONE_BRICKS);
			blockSetDrops(SMOOTH_STONE);
			addDrop(STONE_INDENT);
			addDrop(STONE_PILLAR);
			blockSetDrops(STONE_PAVER);
			addDrop(DAUB);
			addDrop(FRAMED_DAUB);
			addDrop(FRAMED_PILLAR_DAUB);
			addDrop(FRAMED_CROSS_DAUB);
			addDrop(FRAMED_INVERTED_CROSS_DAUB);
			addDrop(FRAMED_X_DAUB);
			addDrop(FRAMED_PLUS_DAUB);
			addDrop(FRAMED_DIVIDED_DAUB);
			woodBlockSetDrops(OAK_PLANK_BLOCKS);
			woodBlockSetDrops(BIRCH_PLANK_BLOCKS);
			woodBlockSetDrops(SPRUCE_PLANK_BLOCKS);
			blockSetDrops(WICKER);
			addDrop(WICKER_DOOR, this::doorDrops);
			addDrop(WICKER_TRAPDOOR);
			addDrop(WICKER_BARS);
			addDrop(ROPE);
			addDrop(ROPE_LADDER);

			// crops

			// technical blocks
			addDrop(OAK_CRATE);
			addDrop(BIRCH_CRATE);
			addDrop(SPRUCE_CRATE);
			addDrop(LARGE_CLAY_POT);
			addDrop(LARGE_FIRED_CLAY_POT);
			addDrop(LARGE_DECORATIVE_FIRED_CLAY_POT);
			addDrop(WICKER_BASKET);
			addDrop(CRUDE_CRAFTING_BENCH);
			addDrop(QUERN);

		}

		public void coloredBlockSetDrops(ColoredBlockSet set) {
			for (Block b : set) {
				addDrop(b);
			}
		}

		public void coloredBlockSetSetDrops(ColoredBlockSetSet set) {
			for (BlockSet bs : set) {
				blockSetDrops(bs);
			}
		}

		public void blockSetDrops(BlockSet set) {
			addDrop(set.block());
			addDrop(set.stairs());
			addDrop(set.slab(), this::slabDrops);
		}

		public void woodBlockSetDrops(WoodBlockSet set) {
			addDrop(set.block());
			addDrop(set.stairs());
			addDrop(set.slab(), this::slabDrops);
			addDrop(set.panel());
			addDrop(set.fence());
			addDrop(set.logFence());
			addDrop(set.fenceGate());
			addDrop(set.door(), this::doorDrops);
			addDrop(set.trapdoor());

		}

		public LootTable.Builder oreSetDrops(Block block, Item raw) {
			return LootTable.builder().pool(this.addSurvivesExplosionCondition(block, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(raw))))
					.pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(this.applyExplosionDecay(block, ItemEntry.builder(COBBLESTONE).conditionally(RandomChanceLootCondition.builder(0.4F)))));
		}

		public LootTable.Builder fossilDrops(Block block) {
			return LootTable.builder().pool(this.addSurvivesExplosionCondition(block, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(BONE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 4.0F))))));
		}

		public LootTable.Builder stoneDrops(Block stone, ItemConvertible rocks) {
			return drops(stone, createDropsWithChiselCondition(), ItemEntry.builder(rocks).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 5))));
		}

		public LootTable.Builder leafDrops(Block sapling) {
			return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with((ItemEntry.builder(sapling).conditionally(createDropsWithKnifeCondition()).conditionally(RandomChanceLootCondition.builder(0.15F)))))
					.pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(this.applyExplosionDecay(STICK, ItemEntry.builder(STICK).conditionally(RandomChanceLootCondition.builder(0.05F)))));
		}

		public LootTable.Builder brushDrops() {
			return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with((ItemEntry.builder(STRAW).conditionally(this.createDropsWithKnifeCondition()))))
					.pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(this.applyExplosionDecay(STRAW, ItemEntry.builder(STRAW).conditionally(RandomChanceLootCondition.builder(0.1F)))));
		}

		public LootCondition.Builder createDropsWithChiselCondition() {
			return this.createToolTagCondition(PrimevalTags.Items.CHISELS);
		}

		public LootCondition.Builder createDropsWithKnifeCondition() {
			return this.createToolTagCondition(PrimevalTags.Items.KNIVES);
		}

		public LootTable.Builder dropsWithKnife(ItemConvertible item) {
			return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(this.createDropsWithKnifeCondition()).with(ItemEntry.builder(item)));
		}

		public LootCondition.Builder createToolTagCondition(TagKey<Item> tag) {
			return MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(this.registries.getOrThrow(RegistryKeys.ITEM), tag));
		}

	}
}
