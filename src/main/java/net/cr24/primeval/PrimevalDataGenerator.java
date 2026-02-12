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
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.*;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.cr24.primeval.initialization.PrimevalBlocks.*;
import static net.cr24.primeval.initialization.PrimevalItems.*;
import static net.cr24.primeval.initialization.PrimevalItems.RAW_COPPER_NATIVE_SMALL;
import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;
import static net.minecraft.client.data.models.model.TextureMapping.getItemTexture;
import static net.minecraft.client.data.models.model.TexturedModel.createDefault;

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
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, PrimevalFeatures::bootstrapConfiguredFeatures);
        registryBuilder.add(Registries.PLACED_FEATURE, PrimevalFeatures::bootstrapPlacedFeatures);
    }

    private static class ModelProvider extends FabricModelProvider {
        public ModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

            blockStateModelGenerator.createTrivialCube(DIRT);
            blockStateModelGenerator.createTrivialCube(COARSE_DIRT);
            blockStateModelGenerator.createTrivialCube(CLAY);
            blockStateModelGenerator.createRotatedMirroredVariantBlock(MUD);
            blockStateModelGenerator.createTrivialCube(DRY_DIRT);
            blockStateModelGenerator.createTrivialCube(SAND);
            blockStateModelGenerator.createTrivialCube(GRAVEL);
            blockStateModelGenerator.createTrivialCube(COBBLESTONE);
            blockStateModelGenerator.createRotatedMirroredVariantBlock(STONE);
            blockStateModelGenerator.createTrivialCube(SANDSTONE);

            blockStateModelGenerator.createAxisAlignedPillarBlock(OAK_LOG_BLOCK, TEXTURED_MODEL_LOG_COLUMN);
            blockStateModelGenerator.createTrivialBlock(OAK_LEAVES, TexturedModel.LEAVES);
            blockStateModelGenerator.createAxisAlignedPillarBlock(BIRCH_LOG_BLOCK, TEXTURED_MODEL_LOG_COLUMN);
            blockStateModelGenerator.createTrivialBlock(BIRCH_LEAVES, TexturedModel.LEAVES);
            blockStateModelGenerator.createAxisAlignedPillarBlock(SPRUCE_LOG_BLOCK, TEXTURED_MODEL_LOG_COLUMN);
            blockStateModelGenerator.createTrivialBlock(SPRUCE_LEAVES, TexturedModel.LEAVES);

            blockStateModelGenerator.createCrossBlockWithDefaultItem(OAK_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
            blockStateModelGenerator.createCrossBlockWithDefaultItem(BIRCH_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
            blockStateModelGenerator.createCrossBlockWithDefaultItem(SPRUCE_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);

            blockStateModelGenerator.registerSimpleTintedItemModel(GRASS, blockStateModelGenerator.createFlatItemModelWithBlockTexture(GRASS.asItem(), GRASS, "_4"), new GrassColorSource(0.7f, 1.0f));
            blockStateModelGenerator.registerSimpleTintedItemModel(BUSH, blockStateModelGenerator.createFlatItemModelWithBlockTexture(BUSH.asItem(), BUSH, "_big"), new GrassColorSource(0.7f, 1.0f));
            blockStateModelGenerator.createItemWithGrassTint(SPIKED_PLANT);
            blockStateModelGenerator.createItemWithGrassTint(LEAFY_PLANT);
            blockStateModelGenerator.registerSimpleFlatItemModel(SHRUB.asItem());
            blockStateModelGenerator.createMultiface(MOSS);

            blockStateModelGenerator.createCrossBlockWithDefaultItem(POPPY, BlockModelGenerators.PlantType.NOT_TINTED);
            blockStateModelGenerator.createCrossBlockWithDefaultItem(DANDELION, BlockModelGenerators.PlantType.NOT_TINTED);
            blockStateModelGenerator.createCrossBlockWithDefaultItem(OXEYE_DAISY, BlockModelGenerators.PlantType.NOT_TINTED);
            blockStateModelGenerator.createCrossBlockWithDefaultItem(CORNFLOWER, BlockModelGenerators.PlantType.NOT_TINTED);
            blockStateModelGenerator.createCrossBlockWithDefaultItem(LILY_OF_THE_VALLEY, BlockModelGenerators.PlantType.NOT_TINTED);

            blockStateModelGenerator.registerSimpleFlatItemModel(REEDS.asItem());
            blockStateModelGenerator.createCrossBlockWithDefaultItem(RIVER_GRASS, BlockModelGenerators.PlantType.NOT_TINTED);

            registerFullBlockSetModels(blockStateModelGenerator, COPPER_MALACHITE_ORE);
            registerFullBlockSetModels(blockStateModelGenerator, COPPER_NATIVE_ORE);
            registerFullBlockSetModels(blockStateModelGenerator, TIN_CASSITERITE_ORE);
            registerFullBlockSetModels(blockStateModelGenerator, ZINC_SPHALERITE_ORE);
            registerFullBlockSetModels(blockStateModelGenerator, GOLD_NATIVE_ORE);
            registerFullBlockSetModels(blockStateModelGenerator, IRON_HEMATITE_ORE);
            registerFullBlockSetModels(blockStateModelGenerator, LAZURITE_ORE);

            blockStateModelGenerator.createTrivialCube(STRAW_MESH);
            registerCarpet(blockStateModelGenerator, STRAW_MESH, STRAW_MAT);
            blockStateModelGenerator.createTrivialCube(TERRACOTTA);
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
            blockStateModelGenerator.createAxisAlignedPillarBlock(STONE_PILLAR, TexturedModel.COLUMN);
            registerBlockSetModels(blockStateModelGenerator, STONE_PAVER);
            blockStateModelGenerator.createTrivialCube(DAUB);
            blockStateModelGenerator.createTrivialCube(FRAMED_DAUB);
            blockStateModelGenerator.createAxisAlignedPillarBlock(FRAMED_PILLAR_DAUB, TexturedModel.COLUMN);
            blockStateModelGenerator.createTrivialCube(FRAMED_CROSS_DAUB);
            blockStateModelGenerator.createTrivialCube(FRAMED_INVERTED_CROSS_DAUB);
            blockStateModelGenerator.createTrivialCube(FRAMED_X_DAUB);
            blockStateModelGenerator.createTrivialCube(FRAMED_PLUS_DAUB);
            blockStateModelGenerator.createTrivialCube(FRAMED_DIVIDED_DAUB);
            registerWoodBlockSetModels(blockStateModelGenerator, OAK_PLANK_BLOCKS);
            registerPillar(blockStateModelGenerator, OAK_PLANK_BLOCKS.panel(), OAK_PLANK_BLOCKS.block());
            blockStateModelGenerator.createDoor(OAK_PLANK_BLOCKS.door());
            blockStateModelGenerator.createTrapdoor(OAK_PLANK_BLOCKS.trapdoor());
            registerWoodBlockSetModels(blockStateModelGenerator, BIRCH_PLANK_BLOCKS);
            registerPillar(blockStateModelGenerator, BIRCH_PLANK_BLOCKS.panel(), BIRCH_PLANK_BLOCKS.block());
            blockStateModelGenerator.createDoor(BIRCH_PLANK_BLOCKS.door());
            blockStateModelGenerator.createTrapdoor(BIRCH_PLANK_BLOCKS.trapdoor());
            registerWoodBlockSetModels(blockStateModelGenerator, SPRUCE_PLANK_BLOCKS);
            registerPillar(blockStateModelGenerator, SPRUCE_PLANK_BLOCKS.panel(), SPRUCE_PLANK_BLOCKS.block());
            blockStateModelGenerator.createDoor(SPRUCE_PLANK_BLOCKS.door());
            blockStateModelGenerator.createTrapdoor(SPRUCE_PLANK_BLOCKS.trapdoor());
            registerBlockSetModels(blockStateModelGenerator, WICKER);
            blockStateModelGenerator.createDoor(WICKER_DOOR);
            blockStateModelGenerator.createTrapdoor(WICKER_TRAPDOOR);
            registerBars(blockStateModelGenerator, WICKER_BARS);
            blockStateModelGenerator.registerSimpleFlatItemModel(ROPE.asItem());
            blockStateModelGenerator.createAxisAlignedPillarBlockCustomModel(ROPE, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(ROPE)));
            blockStateModelGenerator.createNonTemplateHorizontalBlock(ROPE_LADDER);
            blockStateModelGenerator.registerSimpleFlatItemModel(ROPE_LADDER);

            blockStateModelGenerator.createTrivialBlock(CRUDE_CRAFTING_BENCH, TexturedModel.CUBE_TOP_BOTTOM);
        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator) {
            // block stuff
            itemModelGenerator.itemModelOutput.accept(GRASSY_DIRT.asItem(),
                    ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(GRASSY_DIRT), new GrassColorSource(0.7f, 1.0f))
            );
            itemModelGenerator.itemModelOutput.accept(GRASSY_CLAY.asItem(),
                    ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(GRASSY_CLAY), new GrassColorSource(0.7f, 1.0f))
            );
            itemModelGenerator.itemModelOutput.accept(FOSSIL.asItem(),
                    ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(FOSSIL, "_2"))
            );
            itemModelGenerator.itemModelOutput.accept(OAK_PLANK_BLOCKS.logFence().asItem(),
                    ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(OAK_PLANK_BLOCKS.logFence(), "_inventory"))
            );
            itemModelGenerator.itemModelOutput.accept(BIRCH_PLANK_BLOCKS.logFence().asItem(),
                    ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(BIRCH_PLANK_BLOCKS.logFence(), "_inventory"))
            );
            itemModelGenerator.itemModelOutput.accept(SPRUCE_PLANK_BLOCKS.logFence().asItem(),
                    ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(SPRUCE_PLANK_BLOCKS.logFence(), "_inventory"))
            );
            itemModelGenerator.itemModelOutput.accept(STRAW,
                    ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(STRAW))
            );
            itemModelGenerator.itemModelOutput.accept(LIT_CRUDE_TORCH,
                    ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(CRUDE_TORCH.asItem(), "_lit"))
            );
            itemModelGenerator.itemModelOutput.accept(UNLIT_CRUDE_TORCH,
                    ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(CRUDE_TORCH.asItem(), ""))
            );

            // items
            itemModelGenerator.createFlatItemModel(STRAW, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.createFlatItemModel(LIT_CRUDE_TORCH, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.createFlatItemModel(UNLIT_CRUDE_TORCH, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(STICK, Items.STICK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(OAK_LOG, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BIRCH_LOG, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(SPRUCE_LOG, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(STRING, Items.STRING, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FLINT, Items.FLINT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ROCK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(STONE_BRICK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ASHES, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CRUSHED_TERRACOTTA, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CEMENT_MIX, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CEMENT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BONE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BONEMEAL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ANIMAL_FAT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(GUNPOWDER, Items.GUNPOWDER, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CHARRED_BONE, ModelTemplates.FLAT_ITEM);

            itemModelGenerator.generateFlatItem(SANDY_CLAY_BALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(SANDY_CLAY_BRICK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(DRIED_BRICK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(MUD_BALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(MUD_BRICK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CLAY_BALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CLAY_BRICK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FIRED_CLAY_BRICK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CLAY_BOWL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FIRED_CLAY_BOWL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CLAY_TILE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FIRED_CLAY_TILE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CLAY_JUG, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CLAY_VESSEL, ModelTemplates.FLAT_ITEM);
            // foods
            itemModelGenerator.generateFlatItem(PORKCHOP, Items.PORKCHOP, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(COOKED_PORKCHOP, Items.COOKED_PORKCHOP, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CARROT, Items.CARROT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(WHEAT, Items.WHEAT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CABBAGE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BEANS, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(POTATO, Items.POTATO, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(WHEAT_SEEDS, Items.WHEAT_SEEDS, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CABBAGE_SEEDS, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ROTTEN_FLESH, Items.ROTTEN_FLESH, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(SPIDER_EYE, Items.SPIDER_EYE, ModelTemplates.FLAT_ITEM);
            // ores
            itemModelGenerator.generateFlatItem(RAW_COPPER_MALACHITE_SMALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_COPPER_MALACHITE_MEDIUM, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_COPPER_MALACHITE_LARGE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_COPPER_NATIVE_SMALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_COPPER_NATIVE_MEDIUM, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_COPPER_NATIVE_LARGE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_TIN_CASSITERITE_SMALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_TIN_CASSITERITE_MEDIUM, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_TIN_CASSITERITE_LARGE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_ZINC_SPHALERITE_SMALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_ZINC_SPHALERITE_MEDIUM, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_ZINC_SPHALERITE_LARGE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_GOLD_NATIVE_SMALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_GOLD_NATIVE_MEDIUM, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_GOLD_NATIVE_LARGE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_IRON_HEMATITE_SMALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_IRON_HEMATITE_MEDIUM, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_IRON_HEMATITE_LARGE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_LAZURITE_SMALL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_LAZURITE_MEDIUM, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RAW_LAZURITE_LARGE, ModelTemplates.FLAT_ITEM);

            itemModelGenerator.generateFlatItem(FLINT_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateFlatItem(FLINT_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateFlatItem(FLINT_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateSpyglass(FLINT_SPEAR);
            registerToolSet(itemModelGenerator, COPPER_TOOLS);
            registerToolSet(itemModelGenerator, BRONZE_TOOLS);
            registerNormalItemSet(itemModelGenerator, COPPER_TOOL_PARTS);
            registerNormalItemSet(itemModelGenerator, BRONZE_TOOL_PARTS);
            itemModelGenerator.generateFlatItem(WOODEN_BUCKET, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(WOODEN_BUCKET_WATER, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FIRED_CLAY_JUG, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FIRED_CLAY_WATER_JUG, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(FIRED_CLAY_VESSEL, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(QUERN_WHEEL, ModelTemplates.FLAT_ITEM);

            itemModelGenerator.generateFlatItem(CLAY_INGOT_MOLD, ModelTemplates.FLAT_ITEM);
            registerFiredMold(itemModelGenerator, FIRED_CLAY_INGOT_MOLD, PrimevalFluids.ALL_MOLD_FLUIDS);
            itemModelGenerator.generateFlatItem(CLAY_AXE_HEAD_MOLD, ModelTemplates.FLAT_ITEM);
            registerFiredMold(itemModelGenerator, FIRED_CLAY_AXE_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
            itemModelGenerator.generateFlatItem(CLAY_CHISEL_HEAD_MOLD, ModelTemplates.FLAT_ITEM);
            registerFiredMold(itemModelGenerator, FIRED_CLAY_CHISEL_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
            itemModelGenerator.generateFlatItem(CLAY_KNIFE_BLADE_MOLD, ModelTemplates.FLAT_ITEM);
            registerFiredMold(itemModelGenerator, FIRED_CLAY_KNIFE_BLADE_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
            itemModelGenerator.generateFlatItem(CLAY_PICKAXE_HEAD_MOLD, ModelTemplates.FLAT_ITEM);
            registerFiredMold(itemModelGenerator, FIRED_CLAY_PICKAXE_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
            itemModelGenerator.generateFlatItem(CLAY_SHOVEL_HEAD_MOLD, ModelTemplates.FLAT_ITEM);
            registerFiredMold(itemModelGenerator, FIRED_CLAY_SHOVEL_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
            itemModelGenerator.generateFlatItem(CLAY_SWORD_BLADE_MOLD, ModelTemplates.FLAT_ITEM);
            registerFiredMold(itemModelGenerator, FIRED_CLAY_SWORD_BLADE_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
            itemModelGenerator.generateFlatItem(CLAY_HOE_HEAD_MOLD, ModelTemplates.FLAT_ITEM);
            registerFiredMold(itemModelGenerator, FIRED_CLAY_HOE_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);
            itemModelGenerator.generateFlatItem(CLAY_PROSPECTING_PICKAXE_HEAD_MOLD, ModelTemplates.FLAT_ITEM);
            registerFiredMold(itemModelGenerator, FIRED_CLAY_PROSPECTING_PICKAXE_HEAD_MOLD, PrimevalFluids.TOOL_MOLD_FLUIDS);

            itemModelGenerator.generateFlatItem(COPPER_INGOT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(COPPER_CHUNK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(TIN_INGOT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(TIN_CHUNK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ZINC_INGOT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ZINC_CHUNK, ModelTemplates.FLAT_ITEM);

            itemModelGenerator.generateFlatItem(BRONZE_INGOT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BRONZE_CHUNK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BRASS_INGOT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BRASS_CHUNK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(PEWTER_INGOT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(PEWTER_CHUNK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(GOLD_INGOT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(GOLD_CHUNK, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BOTCHED_ALLOY_INGOT, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BOTCHED_ALLOY_CHUNK, ModelTemplates.FLAT_ITEM);

            itemModelGenerator.generateFlatItem(WHITE_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(ORANGE_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(MAGENTA_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(LIGHT_BLUE_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(YELLOW_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(LIME_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(PINK_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(DARK_GRAY_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(LIGHT_GRAY_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(CYAN_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(PURPLE_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BLUE_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BROWN_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(GREEN_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(RED_DYE, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(BLACK_DYE, ModelTemplates.FLAT_ITEM);

            itemModelGenerator.generateFlatItem(COPPER_COIN, ModelTemplates.FLAT_ITEM);
            itemModelGenerator.generateFlatItem(GOLD_COIN, ModelTemplates.FLAT_ITEM);
        }

        @Override
        public String getName() {
            return "Primeval Model Provider";
        }

        private static final TexturedModel.Provider TEXTURED_MODEL_LOG_COLUMN = createDefault((block) -> (new TextureMapping()).put(TextureSlot.SIDE, getBlockTexture(block, "_0")).put(TextureSlot.END, getBlockTexture(block, "_top_0")), ModelTemplates.CUBE_COLUMN);
        private static final TexturedModel.Provider TEXTURED_MODEL_PANEL = createDefault((block) -> (new TextureMapping()).put(TextureSlot.SIDE, getBlockTexture(block, "_0")).put(TextureSlot.END, replaceInId(block, "panel", "planks")), ModelTemplates.CUBE_COLUMN);

        private static Identifier replaceInId(Block block, String from, String to) {
            Identifier identifier = BuiltInRegistries.BLOCK.getKey(block);
            return identifier.withPath((path) -> "block/" + path.replace(from, to));
        }


        private static <T extends Iterable<Block>> void registerFullBlockSetModels(BlockModelGenerators blockStateModelGenerator, T set) {
            set.iterator().forEachRemaining((b) -> blockStateModelGenerator.createTrivialCube(b));
        }

        private static void registerBlockSetModels(BlockModelGenerators blockStateModelGenerator, BlockSet set) {
            blockStateModelGenerator.family(set.block())
                    .stairs(set.stairs())
                    .slab(set.slab());
        }

        private static void registerWoodBlockSetModels(BlockModelGenerators blockStateModelGenerator, WoodBlockSet set) {
            blockStateModelGenerator.family(set.block())
                    .stairs(set.stairs())
                    .slab(set.slab())
                    .fence(set.fence())
                    .fenceGate(set.fenceGate());
        }

        private static void registerColoredBlockSetSetModels(BlockModelGenerators blockStateModelGenerator, ColoredBlockSetSet set) {
            set.iterator().forEachRemaining((bs) -> registerBlockSetModels(blockStateModelGenerator, bs));
        }

        private static void registerCarpet(BlockModelGenerators blockStateModelGenerator, Block wool, Block carpet) {
            MultiVariant weightedVariant = BlockModelGenerators.plainVariant(TexturedModel.CARPET.get(wool).create(carpet, blockStateModelGenerator.modelOutput));
            blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(carpet, weightedVariant));
        }

        private void registerPillar(BlockModelGenerators blockStateModelGenerator, Block side, Block end) {
            TextureMapping textureMap = TextureMapping.column(getBlockTexture(side), getBlockTexture(end));
            MultiVariant weightedVariant = BlockModelGenerators.plainVariant(ModelTemplates.CUBE_COLUMN.create(side, textureMap, blockStateModelGenerator.modelOutput));
            blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(side, weightedVariant));
        }

        private void registerBars(BlockModelGenerators blockStateModelGenerator, Block bars) {
            MultiVariant weightedVariant = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(bars, "_post_ends"));
            MultiVariant weightedVariant2 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(bars, "_post"));
            MultiVariant weightedVariant3 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(bars, "_cap"));
            MultiVariant weightedVariant4 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(bars, "_cap_alt"));
            MultiVariant weightedVariant5 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(bars, "_side"));
            MultiVariant weightedVariant6 = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(bars, "_side_alt"));
            blockStateModelGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(bars).with(weightedVariant).with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false), weightedVariant2).with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, true).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false), weightedVariant3).with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, true).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false), weightedVariant3.with(BlockModelGenerators.Y_ROT_90)).with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, true).term(BlockStateProperties.WEST, false), weightedVariant4).with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, true), weightedVariant4.with(BlockModelGenerators.Y_ROT_90)).with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, true), weightedVariant5).with(BlockModelGenerators.condition().term(BlockStateProperties.EAST, true), weightedVariant5.with(BlockModelGenerators.Y_ROT_90)).with(BlockModelGenerators.condition().term(BlockStateProperties.SOUTH, true), weightedVariant6).with(BlockModelGenerators.condition().term(BlockStateProperties.WEST, true), weightedVariant6.with(BlockModelGenerators.Y_ROT_90)));
            blockStateModelGenerator.registerSimpleFlatItemModel(bars);
        }

        private static void registerToolSet(ItemModelGenerators itemModelGenerator, ToolSet set) {
            itemModelGenerator.generateFlatItem(set.axe(), ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateFlatItem(set.chisel(), ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateFlatItem(set.knife(), ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateFlatItem(set.pickaxe(), ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateFlatItem(set.shovel(), ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateFlatItem(set.sword(), ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateFlatItem(set.hoe(), ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateFlatItem(set.prospecting_pickaxe(), ModelTemplates.FLAT_HANDHELD_ITEM);
            itemModelGenerator.generateSpyglass(set.spear());
        }

        private static <T extends Iterable<Item>> void registerNormalItemSet(ItemModelGenerators itemModelGenerator, T set) {
            set.iterator().forEachRemaining((b) -> itemModelGenerator.generateFlatItem(b, ModelTemplates.FLAT_ITEM));
        }

        public final void registerFiredMold(ItemModelGenerators itemModelGenerator, Item item, List<Fluid> validFluids) {
            Identifier id = ModelLocationUtils.getModelLocation(item);
            Identifier identifier2 = TextureMapping.getItemTexture(item);
            List<SelectItemModel.SwitchCase<ResourceKey<Fluid>>> list = new ArrayList(validFluids.size());

            for (var f : validFluids) {
                var filledId = id.withSuffix("_" + f.builtInRegistryHolder().unwrapKey().get().identifier().getPath());
                list.add(ItemModelUtils.when(f.builtInRegistryHolder().key(), ItemModelUtils.plainModel(filledId)));
                ModelTemplates.FLAT_ITEM.create(filledId, TextureMapping.layer0(filledId), itemModelGenerator.modelOutput);
            }

            ModelTemplates.FLAT_ITEM.create(id, TextureMapping.layer0(identifier2), itemModelGenerator.modelOutput);
            ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(id);
            itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.select(new FluidContentProperty(), unbaked2, list));
        }
    }

    private static class RecipeProvider extends FabricRecipeProvider {

        private static class PrimevalRecipeGenerator extends net.minecraft.data.recipes.RecipeProvider {

            protected PrimevalRecipeGenerator(HolderLookup.Provider registries, RecipeOutput exporter) {
                super(registries, exporter);
            }

            @Override
            public void buildRecipes() {
                // blocks
                this.shapeless(RecipeCategory.BUILDING_BLOCKS, COARSE_DIRT, 2).requires(DIRT).requires(GRAVEL).unlockedBy(getHasName(GRAVEL), this.has(GRAVEL)).save(this.output);
                this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, CLAY, CLAY_BALL);
                this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, MUD, MUD_BALL);
                this.shapeless(RecipeCategory.BUILDING_BLOCKS, DRY_DIRT, 2).requires(DIRT).requires(SAND).unlockedBy(getHasName(SAND), this.has(SAND)).save(this.output);
                this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, COBBLESTONE, ROCK);
                this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, SANDSTONE, SAND);

                this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, STRAW_BLOCK, STRAW);
                this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, STRAW_SLAB, Ingredient.of(STRAW_BLOCK)).unlockedBy(getHasName(STRAW_BLOCK), this.has(STRAW_BLOCK)).save(this.output);
                this.stairBuilder(STRAW_STAIRS, Ingredient.of(STRAW_BLOCK)).unlockedBy(getHasName(STRAW_BLOCK), this.has(STRAW_BLOCK)).save(this.output);
                this.threeByThreePacker(RecipeCategory.BUILDING_BLOCKS, STRAW_MESH, STRAW);
                this.carpet(STRAW_MAT, STRAW_MESH);

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


                this.shapeless(RecipeCategory.BUILDING_BLOCKS, DAUB, 4).requires(SAND).requires(CLAY_BALL).requires(STRAW).unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_DAUB, 5).define('D', DAUB).define('S', STICK).pattern("SDS").pattern("DDD").pattern("SDS").group("framed_daub").unlockedBy(getHasName(DAUB), this.has(DAUB)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_PILLAR_DAUB, 3).define('D', DAUB).define('S', STICK).pattern("SDS").pattern("SDS").pattern("SDS").group("framed_daub").unlockedBy(getHasName(DAUB), this.has(DAUB)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_CROSS_DAUB, 2).define('D', DAUB).define('S', STICK).pattern("DS").pattern("SD").group("framed_daub").unlockedBy(getHasName(DAUB), this.has(DAUB)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_INVERTED_CROSS_DAUB, 2).define('D', DAUB).define('S', STICK).pattern("SD").pattern("DS").group("framed_daub").unlockedBy(getHasName(DAUB), this.has(DAUB)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_X_DAUB, 4).define('D', DAUB).define('S', STICK).pattern("SDS").pattern("DSD").pattern("SDS").group("framed_daub").unlockedBy(getHasName(DAUB), this.has(DAUB)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_PLUS_DAUB, 2).define('D', DAUB).define('S', STICK).pattern("DSD").pattern("SSS").pattern("DSD").group("framed_daub").unlockedBy(getHasName(DAUB), this.has(DAUB)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, FRAMED_DIVIDED_DAUB, 2).define('D', DAUB).define('S', STICK).pattern(" S ").pattern("DSD").pattern(" S ").group("framed_daub").unlockedBy(getHasName(DAUB), this.has(DAUB)).save(this.output);

                this.offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, WICKER.block(), STICK, 2);
                offerWoodBlockSet(OAK_PLANK_BLOCKS, OAK_LOG);
                offerWoodBlockSet(BIRCH_PLANK_BLOCKS, BIRCH_LOG);
                offerWoodBlockSet(SPRUCE_PLANK_BLOCKS, SPRUCE_LOG);
                offerBlockSet(WICKER);
                this.doorBuilder(WICKER_DOOR, Ingredient.of(WICKER.block())).unlockedBy(getHasName(WICKER.block()), this.has(WICKER.block())).save(this.output);
                this.trapdoorBuilder(WICKER_TRAPDOOR, Ingredient.of(WICKER.block())).unlockedBy(getHasName(WICKER.block()), this.has(WICKER.block())).save(this.output);
                this.shaped(RecipeCategory.DECORATIONS, WICKER_BARS, 16).define('#', WICKER.block()).pattern("###").pattern("###").unlockedBy(getHasName(WICKER.block()), this.has(WICKER.block())).save(this.output);

                this.shaped(RecipeCategory.DECORATIONS, ROPE, 3).define('#', STRAW).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(STRAW), this.has(STRAW)).save(this.output);
                this.shaped(RecipeCategory.DECORATIONS, ROPE_LADDER, 6).define('#', ROPE).define('X', STICK).pattern("#X#").pattern("#X#").pattern("#X#").unlockedBy(getHasName(ROPE), this.has(ROPE)).save(this.output);

                offerCrateRecipe(OAK_CRATE, OAK_PLANK_BLOCKS);
                offerCrateRecipe(BIRCH_CRATE, BIRCH_PLANK_BLOCKS);
                offerCrateRecipe(SPRUCE_CRATE, SPRUCE_PLANK_BLOCKS);

                this.shaped(RecipeCategory.DECORATIONS, LARGE_CLAY_POT).define('B', CLAY).define('C', CLAY_BALL).pattern("C C").pattern("C C").pattern("CBC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shapeless(RecipeCategory.DECORATIONS, LARGE_FIRED_CLAY_POT).requires(LARGE_DECORATIVE_FIRED_CLAY_POT).unlockedBy(getHasName(LARGE_DECORATIVE_FIRED_CLAY_POT), this.has(LARGE_DECORATIVE_FIRED_CLAY_POT)).save(this.output);
                this.shapeless(RecipeCategory.DECORATIONS, LARGE_DECORATIVE_FIRED_CLAY_POT).requires(LARGE_FIRED_CLAY_POT).requires(DIRT).unlockedBy(getHasName(LARGE_FIRED_CLAY_POT), this.has(LARGE_FIRED_CLAY_POT)).save(this.output);
                this.shaped(RecipeCategory.DECORATIONS, WICKER_BASKET).define('W', WICKER.block()).pattern(" W ").pattern("W W").pattern("WWW").unlockedBy(getHasName(WICKER.block()), this.has(WICKER.block())).save(this.output);

                this.shaped(RecipeCategory.DECORATIONS, CRUDE_CRAFTING_BENCH).define('P', PrimevalTags.Items.PLANKS).define('S', STRAW).pattern("SS").pattern("PP").unlockedBy("has_planks", this.has(PrimevalTags.Items.PLANKS)).save(this.output);

                // items
                this.shapeless(RecipeCategory.MISC, STRAW, 4).requires(STRAW_BLOCK).unlockedBy(getHasName(STRAW_BLOCK), this.has(STRAW_BLOCK)).save(this.output);
                this.shapeless(RecipeCategory.MISC, ROCK, 4).requires(COBBLESTONE).unlockedBy(getHasName(COBBLESTONE), this.has(COBBLESTONE)).save(this.output);
                this.shapeless(RecipeCategory.MISC, STICK, 2).requires(REEDS).unlockedBy(getHasName(REEDS), this.has(REEDS)).save(this.output);

                this.shapeless(RecipeCategory.MISC, CEMENT_MIX, 4).requires(CRUSHED_TERRACOTTA).requires(ASHES).requires(ASHES).unlockedBy(getHasName(ASHES), this.has(ASHES)).save(this.output);
                this.shaped(RecipeCategory.MISC, CEMENT, 12).define('W', WOODEN_BUCKET_WATER).define('C', CEMENT_MIX).pattern("CCC").pattern("CWC").pattern("CCC").unlockedBy(getHasName(CEMENT_MIX), this.has(CEMENT_MIX)).save(this.output);

                this.shapeless(RecipeCategory.MISC, SANDY_CLAY_BALL, 2).requires(SAND).requires(CLAY_BALL).unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, SANDY_CLAY_BRICK, 2).define('C', SANDY_CLAY_BALL).pattern("CCC").unlockedBy(getHasName(SANDY_CLAY_BALL), this.has(SANDY_CLAY_BALL)).save(this.output);
                this.shapeless(RecipeCategory.MISC, MUD_BALL, 4).requires(MUD).unlockedBy(getHasName(MUD), this.has(MUD)).save(this.output);
                this.shaped(RecipeCategory.MISC, MUD_BRICK, 2).define('C', MUD_BALL).pattern("CCC").unlockedBy(getHasName(MUD_BALL), this.has(MUD_BALL)).save(this.output);

                this.shapeless(RecipeCategory.MISC, CLAY_BALL, 4).requires(CLAY).unlockedBy(getHasName(CLAY), this.has(CLAY)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_BRICK, 2).define('C', CLAY_BALL).pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_BOWL).define('C', CLAY_BALL).pattern("C C").pattern(" C ").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_TILE, 2).define('C', CLAY_BALL).pattern("CC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_JUG).define('C', CLAY_BALL).pattern("CC ").pattern("C C").pattern("CC ").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_VESSEL).define('C', CLAY_BALL).pattern("C C").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);

                this.shaped(RecipeCategory.TOOLS, FLINT_AXE).define('F', FLINT).define('S', STICK).define('T', STRAW).pattern("FT").pattern("FS").unlockedBy(getHasName(FLINT), this.has(FLINT)).save(this.output);
                this.shaped(RecipeCategory.TOOLS, FLINT_KNIFE).define('F', FLINT).define('S', STICK).define('T', STRAW).pattern("TF").pattern("S ").unlockedBy(getHasName(FLINT), this.has(FLINT)).save(this.output);
                this.shaped(RecipeCategory.TOOLS, FLINT_SHOVEL).define('F', FLINT).define('S', STICK).define('T', STRAW).pattern("TF").pattern(" S").unlockedBy(getHasName(FLINT), this.has(FLINT)).save(this.output);
                this.shaped(RecipeCategory.TOOLS, FLINT_SPEAR).define('F', FLINT).define('S', STICK).define('T', STRAW).pattern("  F").pattern(" ST").pattern("S  ").unlockedBy(getHasName(FLINT), this.has(FLINT)).save(this.output);
                offerToolSet(COPPER_TOOLS, COPPER_TOOL_PARTS);
                offerToolSet(BRONZE_TOOLS, BRONZE_TOOL_PARTS);

                this.shaped(RecipeCategory.TOOLS, WOODEN_BUCKET).define('P', PrimevalTags.Items.PLANKS).pattern("P P").pattern(" P ").unlockedBy("has_planks", this.has(PrimevalTags.Items.PLANKS)).save(this.output);
                this.shaped(RecipeCategory.MISC, QUERN_WHEEL).define('Q', SMOOTH_STONE.slab()).define('S', STICK).pattern("S").pattern("Q").unlockedBy(getHasName(SMOOTH_STONE.block()), this.has(SMOOTH_STONE.block())).save(this.output);

                this.shaped(RecipeCategory.MISC, CLAY_INGOT_MOLD).define('C', CLAY_BALL).pattern("CCC").pattern("   ").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_AXE_HEAD_MOLD).define('C', CLAY_BALL).pattern("  C").pattern(" CC").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_CHISEL_HEAD_MOLD).define('C', CLAY_BALL).pattern("CC ").pattern("C C").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_KNIFE_BLADE_MOLD).define('C', CLAY_BALL).pattern("CCC").pattern("C C").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_PICKAXE_HEAD_MOLD).define('C', CLAY_BALL).pattern("CCC").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_SHOVEL_HEAD_MOLD).define('C', CLAY_BALL).pattern("C C").pattern("CCC").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_SWORD_BLADE_MOLD).define('C', CLAY_BALL).pattern("C C").pattern("C C").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_HOE_HEAD_MOLD).define('C', CLAY_BALL).pattern("C  ").pattern("CCC").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);
                this.shaped(RecipeCategory.MISC, CLAY_PROSPECTING_PICKAXE_HEAD_MOLD).define('C', CLAY_BALL).pattern("  C").pattern("CC ").pattern("CCC").unlockedBy(getHasName(CLAY_BALL), this.has(CLAY_BALL)).save(this.output);

            }

            private void offerShapelessColoredBlockSet(ItemLike base, ColoredBlockSet set, String group) {
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.white(), 8).define('#', base).define('X', WHITE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.orange(), 8).define('#', base).define('X', ORANGE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.magenta(), 8).define('#', base).define('X', MAGENTA_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.lightBlue(), 8).define('#', base).define('X', LIGHT_BLUE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.yellow(), 8).define('#', base).define('X', YELLOW_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.lime(), 8).define('#', base).define('X', LIME_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.pink(), 8).define('#', base).define('X', PINK_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.darkGray(), 8).define('#', base).define('X', DARK_GRAY_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.lightGray(), 8).define('#', base).define('X', LIGHT_GRAY_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.cyan(), 8).define('#', base).define('X', CYAN_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.purple(), 8).define('#', base).define('X', PURPLE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.blue(), 8).define('#', base).define('X', BLUE_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.brown(), 8).define('#', base).define('X', BROWN_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.green(), 8).define('#', base).define('X', GREEN_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.red(), 8).define('#', base).define('X', RED_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.black(), 8).define('#', base).define('X', BLACK_DYE).pattern("###").pattern("#X#").pattern("###").group(group).unlockedBy(getHasName(base), this.has(base)).save(this.output);
            }

            private void offerBlockSet(BlockSet set) {
                var base = set.block();
                this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, set.slab(), Ingredient.of(base)).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.stairBuilder(set.stairs(), Ingredient.of(base)).unlockedBy(getHasName(base), this.has(base)).save(this.output);
            }

            private void offerWoodBlockSet(WoodBlockSet set, ItemLike log) {
                var base = set.block();
                this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, set.slab(), Ingredient.of(base)).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.stairBuilder(set.stairs(), Ingredient.of(base)).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.shaped(RecipeCategory.BUILDING_BLOCKS, set.panel(), 4).define('#', base).define('X', STICK).pattern("###").pattern("XXX").pattern("###").unlockedBy(getHasName(base), this.has(base)).save(this.output);
                offerFenceRecipe(RecipeCategory.DECORATIONS, set.fence(), set.block(), STICK, 3);
                offerFenceRecipe(RecipeCategory.DECORATIONS, set.logFence(), log, STICK, 3);
                offerFenceRecipe(RecipeCategory.DECORATIONS, set.fenceGate(), STICK, set.block(), 2);
                this.doorBuilder(set.door(), Ingredient.of(set.block())).unlockedBy(getHasName(base), this.has(base)).save(this.output);
                this.trapdoorBuilder(set.trapdoor(), Ingredient.of(set.block())).unlockedBy(getHasName(base), this.has(base)).save(this.output);
            }

            private void offerColoredBlockSetSet(ColoredBlockSetSet set) {
                for (BlockSet color : set) {
                    offerBlockSet(color);
                }
            }

            private void offer2x2CompactingRecipe(RecipeCategory category, ItemLike output, ItemLike input, int amount) {
                this.shaped(category, output, amount).define('#', input).pattern("##").pattern("##").unlockedBy(getHasName(input), this.has(input)).save(this.output);
            }

            private void offer2x2CrossRecipe(RecipeCategory category, ItemLike output, ItemLike input1, ItemLike input2, int amount) {
                this.shaped(category, output, amount).define('A', input1).define('B', input2).pattern("AB").pattern("BA").unlockedBy(getHasName(input1), this.has(input1)).save(this.output);
            }

            private void offer2x2CrossRecipe(RecipeCategory category, ItemLike output, ItemLike input1, TagKey<Item> input2, int amount) {
                this.shaped(category, output, amount).define('A', input1).define('B', input2).pattern("AB").pattern("BA").unlockedBy(getHasName(input1), this.has(input1)).save(this.output);
            }

            private void offerFenceRecipe(RecipeCategory category, ItemLike output, ItemLike inputSide, ItemLike inputMiddle, int amount) {
                this.shaped(category, output, amount).define('W', inputSide).define('#', inputMiddle).pattern("W#W").pattern("W#W").unlockedBy(getHasName(inputSide), this.has(inputSide)).save(this.output);
            }

            private void offerCrateRecipe(ItemLike crate, WoodBlockSet set) {
                this.shaped(RecipeCategory.DECORATIONS, crate, 1).define('W', set.block()).define('S', set.slab()).pattern("SSS").pattern("W W").pattern("WWW").unlockedBy(getHasName(set.block()), this.has(set.block())).save(this.output);
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
                this.shaped(RecipeCategory.COMBAT, toolSet.spear()).define('H', toolPartSet.sword_blade()).define('S', STICK).pattern("  H").pattern(" S ").pattern("S  ").unlockedBy(getHasName(toolPartSet.sword_blade()), this.has(toolPartSet.sword_blade())).save(this.output);

            }

            private void offerToolAssembly(RecipeCategory category, ItemLike tool, ItemLike head) {
                this.shapeless(category, tool).requires(STICK).requires(head).unlockedBy(getHasName(head), this.has(head)).save(this.output);
            }

        }

        public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected net.minecraft.data.recipes.RecipeProvider createRecipeProvider(HolderLookup.Provider wrapperLookup, RecipeOutput recipeExporter) {
            return new PrimevalRecipeGenerator(wrapperLookup, recipeExporter);
        }

        @Override
        public String getName() {
            return "Primeval Recipe Provider";
        }
    }

    private static class LootTableProvider extends FabricBlockLootTableProvider {

        protected LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            // terrain
            dropSelf(DIRT);
            dropSelf(COARSE_DIRT);
            add(CLAY, (block -> this.createSingleItemTable(CLAY_BALL, ConstantValue.exactly(4))));
            add(MUD, (block -> this.createSingleItemTable(MUD_BALL, ConstantValue.exactly(4))));
            dropSelf(DRY_DIRT);
            add(GRASSY_DIRT, (block -> this.createSingleItemTable(DIRT)));
            add(GRASSY_CLAY, (block -> this.createSingleItemTable(CLAY_BALL, ConstantValue.exactly(4))));
            dropSelf(SAND);
            dropSelf(GRAVEL);
            dropSelf(COBBLESTONE);
            add(STONE, (block -> this.stoneDrops(block, ROCK)));
            add(SANDSTONE, (block -> this.createSingleItemTable(SAND, UniformGenerator.between(2, 4))));
            add(DIRT_FARMLAND, (block -> this.createSingleItemTable(DIRT)));
            add(CLAY_FARMLAND, (block -> this.createSingleItemTable(CLAY_BALL, ConstantValue.exactly(4))));

            // plants
            add(OAK_LOG_BLOCK, (block -> this.createSingleItemTable(OAK_LOG)));
            add(BIRCH_LOG_BLOCK, (block -> this.createSingleItemTable(BIRCH_LOG)));
            add(SPRUCE_LOG_BLOCK, (block -> this.createSingleItemTable(SPRUCE_LOG)));
            add(OAK_LEAVES, (block -> this.leafDrops(OAK_SAPLING)));
            add(BIRCH_LEAVES, (block -> this.leafDrops(BIRCH_SAPLING)));
            add(SPRUCE_LEAVES, (block -> this.leafDrops(SPRUCE_SAPLING)));
            dropSelf(OAK_SAPLING);
            dropSelf(BIRCH_SAPLING);
            dropSelf(SPRUCE_SAPLING);
            add(GRASS, (block -> this.brushDrops()));
            add(BUSH, (block -> this.brushDrops()));
            add(SPIKED_PLANT, (block -> this.brushDrops()));
            add(LEAFY_PLANT, (block -> this.brushDrops()));
            add(SHRUB, (block -> this.dropsWithKnife(SHRUB)));
            //addDrop(MOSS, (block -> this.dropsWithKnife(MOSS)));
            // flowers
            add(POPPY, (block -> this.dropsWithKnife(POPPY)));
            add(DANDELION, (block -> this.dropsWithKnife(DANDELION)));
            add(OXEYE_DAISY, (block -> this.dropsWithKnife(OXEYE_DAISY)));
            add(CORNFLOWER, (block -> this.dropsWithKnife(CORNFLOWER)));
            add(LILY_OF_THE_VALLEY, (block -> this.dropsWithKnife(LILY_OF_THE_VALLEY)));
            // misc
            dropSelf(REEDS);
            //addDrop(RIVER_GRASS);

            // ores
            add(COPPER_MALACHITE_ORE.large(), (block -> this.oreSetDrops(COPPER_MALACHITE_ORE.large(), RAW_COPPER_MALACHITE_LARGE)));
            add(COPPER_MALACHITE_ORE.medium(), (block -> this.oreSetDrops(COPPER_MALACHITE_ORE.medium(), RAW_COPPER_MALACHITE_MEDIUM)));
            add(COPPER_MALACHITE_ORE.small(), (block -> this.oreSetDrops(COPPER_MALACHITE_ORE.small(), RAW_COPPER_MALACHITE_SMALL)));
            add(COPPER_NATIVE_ORE.large(), (block -> this.oreSetDrops(COPPER_NATIVE_ORE.large(), RAW_COPPER_NATIVE_LARGE)));
            add(COPPER_NATIVE_ORE.medium(), (block -> this.oreSetDrops(COPPER_NATIVE_ORE.medium(), RAW_COPPER_NATIVE_MEDIUM)));
            add(COPPER_NATIVE_ORE.small(), (block -> this.oreSetDrops(COPPER_NATIVE_ORE.small(), RAW_COPPER_NATIVE_SMALL)));
            add(TIN_CASSITERITE_ORE.large(), (block -> this.oreSetDrops(TIN_CASSITERITE_ORE.large(), RAW_TIN_CASSITERITE_LARGE)));
            add(TIN_CASSITERITE_ORE.medium(), (block -> this.oreSetDrops(TIN_CASSITERITE_ORE.medium(), RAW_TIN_CASSITERITE_MEDIUM)));
            add(TIN_CASSITERITE_ORE.small(), (block -> this.oreSetDrops(TIN_CASSITERITE_ORE.small(), RAW_TIN_CASSITERITE_SMALL)));
            add(GOLD_NATIVE_ORE.large(), (block -> this.oreSetDrops(GOLD_NATIVE_ORE.large(), RAW_GOLD_NATIVE_LARGE)));
            add(GOLD_NATIVE_ORE.medium(), (block -> this.oreSetDrops(GOLD_NATIVE_ORE.medium(), RAW_GOLD_NATIVE_MEDIUM)));
            add(GOLD_NATIVE_ORE.small(), (block -> this.oreSetDrops(GOLD_NATIVE_ORE.small(), RAW_GOLD_NATIVE_SMALL)));
            add(IRON_HEMATITE_ORE.large(), (block -> this.oreSetDrops(IRON_HEMATITE_ORE.large(), RAW_IRON_HEMATITE_LARGE)));
            add(IRON_HEMATITE_ORE.medium(), (block -> this.oreSetDrops(IRON_HEMATITE_ORE.medium(), RAW_IRON_HEMATITE_MEDIUM)));
            add(IRON_HEMATITE_ORE.small(), (block -> this.oreSetDrops(IRON_HEMATITE_ORE.small(), RAW_IRON_HEMATITE_SMALL)));
            add(LAZURITE_ORE.large(), (block -> this.oreSetDrops(LAZURITE_ORE.large(), RAW_LAZURITE_LARGE)));
            add(LAZURITE_ORE.medium(), (block -> this.oreSetDrops(LAZURITE_ORE.medium(), RAW_LAZURITE_MEDIUM)));
            add(LAZURITE_ORE.small(), (block -> this.oreSetDrops(LAZURITE_ORE.small(), RAW_LAZURITE_SMALL)));
            add(FOSSIL, (block -> this.fossilDrops(FOSSIL)));

            // crafted blocks
            dropSelf(STRAW_BLOCK);
            dropSelf(STRAW_STAIRS);
            add(STRAW_SLAB, this::createSlabItemTable);
            dropSelf(STRAW_MESH);
            dropSelf(STRAW_MAT);
            dropSelf(TERRACOTTA);
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
            dropSelf(STONE_INDENT);
            dropSelf(STONE_PILLAR);
            blockSetDrops(STONE_PAVER);
            dropSelf(DAUB);
            dropSelf(FRAMED_DAUB);
            dropSelf(FRAMED_PILLAR_DAUB);
            dropSelf(FRAMED_CROSS_DAUB);
            dropSelf(FRAMED_INVERTED_CROSS_DAUB);
            dropSelf(FRAMED_X_DAUB);
            dropSelf(FRAMED_PLUS_DAUB);
            dropSelf(FRAMED_DIVIDED_DAUB);
            woodBlockSetDrops(OAK_PLANK_BLOCKS);
            woodBlockSetDrops(BIRCH_PLANK_BLOCKS);
            woodBlockSetDrops(SPRUCE_PLANK_BLOCKS);
            blockSetDrops(WICKER);
            add(WICKER_DOOR, this::createDoorTable);
            dropSelf(WICKER_TRAPDOOR);
            dropSelf(WICKER_BARS);
            dropSelf(ROPE);
            dropSelf(ROPE_LADDER);

            // crops

            // technical blocks
            dropSelf(OAK_CRATE);
            dropSelf(BIRCH_CRATE);
            dropSelf(SPRUCE_CRATE);
            dropSelf(LARGE_CLAY_POT);
            dropSelf(LARGE_FIRED_CLAY_POT);
            dropSelf(LARGE_DECORATIVE_FIRED_CLAY_POT);
            dropSelf(WICKER_BASKET);
            dropSelf(CRUDE_CRAFTING_BENCH);
            dropSelf(QUERN);

        }

        public void coloredBlockSetDrops(ColoredBlockSet set) {
            for (Block b : set) {
                dropSelf(b);
            }
        }

        public void coloredBlockSetSetDrops(ColoredBlockSetSet set) {
            for (BlockSet bs : set) {
                blockSetDrops(bs);
            }
        }

        public void blockSetDrops(BlockSet set) {
            dropSelf(set.block());
            dropSelf(set.stairs());
            add(set.slab(), this::createSlabItemTable);
        }

        public void woodBlockSetDrops(WoodBlockSet set) {
            dropSelf(set.block());
            dropSelf(set.stairs());
            add(set.slab(), this::createSlabItemTable);
            dropSelf(set.panel());
            dropSelf(set.fence());
            dropSelf(set.logFence());
            dropSelf(set.fenceGate());
            add(set.door(), this::createDoorTable);
            dropSelf(set.trapdoor());

        }

        public LootTable.Builder oreSetDrops(Block block, Item raw) {
            return LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(raw))))
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(block, LootItem.lootTableItem(COBBLESTONE).when(LootItemRandomChanceCondition.randomChance(0.4F)))));
        }

        public LootTable.Builder fossilDrops(Block block) {
            return LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(BONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))));
        }

        public LootTable.Builder stoneDrops(Block stone, ItemLike rocks) {
            return createSelfDropDispatchTable(stone, createDropsWithChiselCondition(), LootItem.lootTableItem(rocks).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5))));
        }

        public LootTable.Builder leafDrops(Block sapling) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add((LootItem.lootTableItem(sapling).when(createDropsWithKnifeCondition()).when(LootItemRandomChanceCondition.randomChance(0.15F)))))
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(STICK, LootItem.lootTableItem(STICK).when(LootItemRandomChanceCondition.randomChance(0.05F)))));
        }

        public LootTable.Builder brushDrops() {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add((LootItem.lootTableItem(STRAW).when(this.createDropsWithKnifeCondition()))))
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(STRAW, LootItem.lootTableItem(STRAW).when(LootItemRandomChanceCondition.randomChance(0.1F)))));
        }

        public LootItemCondition.Builder createDropsWithChiselCondition() {
            return this.createToolTagCondition(PrimevalTags.Items.CHISELS);
        }

        public LootItemCondition.Builder createDropsWithKnifeCondition() {
            return this.createToolTagCondition(PrimevalTags.Items.KNIVES);
        }

        public LootTable.Builder dropsWithKnife(ItemLike item) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(this.createDropsWithKnifeCondition()).add(LootItem.lootTableItem(item)));
        }

        public LootItemCondition.Builder createToolTagCondition(TagKey<Item> tag) {
            return MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), tag));
        }

    }
}
