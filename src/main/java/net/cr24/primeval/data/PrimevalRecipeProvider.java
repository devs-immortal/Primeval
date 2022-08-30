package net.cr24.primeval.data;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.tag.PrimevalItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;

import java.util.function.Consumer;

public class PrimevalRecipeProvider extends FabricRecipeProvider {
    public PrimevalRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerCrateRecipe(PrimevalBlocks.OAK_CRATE, PrimevalBlocks.OAK_PLANK_BLOCKS.block(), PrimevalBlocks.OAK_PLANK_BLOCKS.slab(), exporter);
        offerCrateRecipe(PrimevalBlocks.BIRCH_CRATE, PrimevalBlocks.BIRCH_PLANK_BLOCKS.block(), PrimevalBlocks.BIRCH_PLANK_BLOCKS.slab(), exporter);
        offerCrateRecipe(PrimevalBlocks.SPRUCE_CRATE, PrimevalBlocks.SPRUCE_PLANK_BLOCKS.block(), PrimevalBlocks.SPRUCE_PLANK_BLOCKS.slab(), exporter);
        offerWoodBlockSetRecipes(PrimevalBlocks.OAK_PLANK_BLOCKS, exporter);
        offerWoodBlockSetRecipes(PrimevalBlocks.BIRCH_PLANK_BLOCKS, exporter);
        offerWoodBlockSetRecipes(PrimevalBlocks.SPRUCE_PLANK_BLOCKS, exporter);

        offerSimpleCompactingRecipe(PrimevalBlocks.COBBLESTONE, PrimevalItems.ROCK, exporter);
        offerSimpleCompactingRecipe(PrimevalBlocks.CLAY_BLOCK, PrimevalItems.CLAY_BALL, exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_BOWL).input('#', PrimevalItems.CLAY_BALL).pattern("# #").pattern(" # ").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_BRICK, 2).input('#', PrimevalItems.CLAY_BALL).pattern("###").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_TILE, 1).input('#', PrimevalItems.CLAY_BALL).pattern("##").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_VESSEL, 1).input('#', PrimevalItems.CLAY_BALL).pattern("# #").pattern("###").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_AXE_HEAD_MOLD).input('#', PrimevalItems.CLAY_BALL).pattern("#  ").pattern("## ").pattern("###").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_CHISEL_HEAD_MOLD).input('#', PrimevalItems.CLAY_BALL).pattern("# #").pattern("# #").pattern("###").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_INGOT_MOLD).input('#', PrimevalItems.CLAY_BALL).pattern("###").pattern("   ").pattern("###").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_KNIFE_BLADE_MOLD).input('#', PrimevalItems.CLAY_BALL).pattern("###").pattern("# #").pattern("###").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_PICKAXE_HEAD_MOLD).input('#', PrimevalItems.CLAY_BALL).pattern("   ").pattern("###").pattern("###").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_SHOVEL_HEAD_MOLD).input('#', PrimevalItems.CLAY_BALL).pattern("# #").pattern("###").pattern("###").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.CLAY_SWORD_BLADE_MOLD).input('#', PrimevalItems.CLAY_BALL).pattern("# #").pattern("# #").pattern("###").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(PrimevalBlocks.CRUDE_CRAFTING_BENCH).input('S', PrimevalBlocks.STRAW_BLOCK).input('#', PrimevalItemTags.PLANKS).pattern("SS").pattern("##").criterion(hasItem(PrimevalItems.STRAW), conditionsFromItem(PrimevalItems.STRAW)).offerTo(exporter);
//        ShapedRecipeJsonBuilder.create(PrimevalBlocks.CRUDE_TORCH, 3).input('S', PrimevalItems.STRAW).input('#', PrimevalItems.STICK).pattern("S").pattern("#").criterion(hasItem(PrimevalItems.STRAW), conditionsFromItem(PrimevalItems.STRAW)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(PrimevalBlocks.DAUB, 4).input(PrimevalBlocks.SAND).input(PrimevalItems.CLAY_BALL).input(PrimevalItems.STICK).input(PrimevalItems.STRAW).criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).criterion(hasItem(PrimevalItems.STRAW), conditionsFromItem(PrimevalItems.STRAW)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.FRAMED_CROSS_DAUB, 2).input('D', PrimevalBlocks.DAUB).input('S', PrimevalItems.STICK).pattern("DS").pattern("SD").criterion(hasItem(PrimevalBlocks.DAUB), conditionsFromItem(PrimevalBlocks.DAUB)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.FRAMED_DAUB, 5).input('D', PrimevalBlocks.DAUB).input('S', PrimevalItems.STICK).pattern("SDS").pattern("DSD").pattern("SDS").criterion(hasItem(PrimevalBlocks.DAUB), conditionsFromItem(PrimevalBlocks.DAUB)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.FRAMED_DIVIDED_DAUB, 2).input('D', PrimevalBlocks.DAUB).input('S', PrimevalItems.STICK).pattern(" S ").pattern("DSD").pattern(" S ").criterion(hasItem(PrimevalBlocks.DAUB), conditionsFromItem(PrimevalBlocks.DAUB)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.FRAMED_INVERTED_CROSS_DAUB, 2).input('D', PrimevalBlocks.DAUB).input('S', PrimevalItems.STICK).pattern("SD").pattern("DS").criterion(hasItem(PrimevalBlocks.DAUB), conditionsFromItem(PrimevalBlocks.DAUB)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.FRAMED_PILLAR_DAUB, 3).input('D', PrimevalBlocks.DAUB).input('S', PrimevalItems.STICK).pattern("SDS").pattern("SDS").pattern("SDS").criterion(hasItem(PrimevalBlocks.DAUB), conditionsFromItem(PrimevalBlocks.DAUB)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.FRAMED_PLUS_DAUB, 4).input('D', PrimevalBlocks.DAUB).input('S', PrimevalItems.STICK).pattern("DSD").pattern("SSS").pattern("DSD").criterion(hasItem(PrimevalBlocks.DAUB), conditionsFromItem(PrimevalBlocks.DAUB)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.FRAMED_X_DAUB, 4).input('D', PrimevalBlocks.DAUB).input('S', PrimevalItems.STICK).pattern("SDS").pattern("DSD").pattern("SDS").criterion(hasItem(PrimevalBlocks.DAUB), conditionsFromItem(PrimevalBlocks.DAUB)).offerTo(exporter);

        offerMortarCompactingRecipe(PrimevalBlocks.DRIED_BRICK_BLOCKS.block(), PrimevalItems.DRIED_BRICK, exporter);
        offerSlabRecipe(PrimevalBlocks.DRIED_BRICK_BLOCKS.slab(), PrimevalBlocks.DRIED_BRICK_BLOCKS.block(), exporter);
        offerStairsRecipe(PrimevalBlocks.DRIED_BRICK_BLOCKS.stairs(), PrimevalBlocks.DRIED_BRICK_BLOCKS.block(), exporter);
        offerMortarCompactingRecipe(PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.block(), PrimevalItems.FIRED_CLAY_BRICK, exporter);
        offerSlabRecipe(PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.slab(), PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.block(), exporter);
        offerStairsRecipe(PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.stairs(), PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.block(), exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.block(), 4).input('#', PrimevalItems.FIRED_CLAY_TILE).pattern("##").pattern("##").criterion(hasItem(PrimevalItems.FIRED_CLAY_TILE), conditionsFromItem(PrimevalItems.FIRED_CLAY_TILE)).offerTo(exporter);
        offerSlabRecipe(PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.slab(), PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.block(), exporter);
        offerStairsRecipe(PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.stairs(), PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.block(), exporter);
        offerMortarCompactingRecipe(PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.block(), PrimevalItems.FIRED_CLAY_TILE, exporter);
        offerSlabRecipe(PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.slab(), PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.block(), exporter);
        offerStairsRecipe(PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.stairs(), PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.block(), exporter);
        ShapelessRecipeJsonBuilder.create(PrimevalBlocks.LARGE_FIRED_CLAY_POT).input(PrimevalBlocks.LARGE_DECORATIVE_FIRED_CLAY_POT).criterion(hasItem(PrimevalBlocks.LARGE_DECORATIVE_FIRED_CLAY_POT), conditionsFromItem(PrimevalBlocks.LARGE_DECORATIVE_FIRED_CLAY_POT)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(PrimevalBlocks.LARGE_DECORATIVE_FIRED_CLAY_POT).input(PrimevalBlocks.LARGE_FIRED_CLAY_POT).input(PrimevalBlocks.DIRT).criterion(hasItem(PrimevalBlocks.LARGE_FIRED_CLAY_POT), conditionsFromItem(PrimevalBlocks.LARGE_FIRED_CLAY_POT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.LARGE_CLAY_POT).input('B', PrimevalBlocks.CLAY_BLOCK).input('C', PrimevalItems.CLAY_BALL).pattern("C C").pattern("C C").pattern("CBC").criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(PrimevalItems.SANDY_CLAY_BALL, 2).input(PrimevalItems.CLAY_BALL).input(PrimevalBlocks.SAND).criterion(hasItem(PrimevalItems.CLAY_BALL), conditionsFromItem(PrimevalItems.CLAY_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.SANDY_CLAY_BRICK, 2).input('#', PrimevalItems.SANDY_CLAY_BALL).pattern("###").criterion(hasItem(PrimevalItems.SANDY_CLAY_BALL), conditionsFromItem(PrimevalItems.SANDY_CLAY_BALL)).offerTo(exporter);

        offerSlabRecipe(PrimevalBlocks.SMOOTH_STONE.slab(), PrimevalBlocks.SMOOTH_STONE.block(), exporter);
        offerStairsRecipe(PrimevalBlocks.SMOOTH_STONE.stairs(), PrimevalBlocks.SMOOTH_STONE.block(), exporter);
        offerMortarCompactingRecipe(PrimevalBlocks.STONE_BRICKS.block(), PrimevalItems.STONE_BRICK, exporter);
        offerSlabRecipe(PrimevalBlocks.STONE_BRICKS.slab(), PrimevalBlocks.STONE_BRICKS.block(), exporter);
        offerStairsRecipe(PrimevalBlocks.STONE_BRICKS.stairs(), PrimevalBlocks.STONE_BRICKS.block(), exporter);
        offerSimpleCompactingRecipe(PrimevalBlocks.STRAW_BLOCK, PrimevalItems.STRAW, exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.STRAW_MESH).input('#', PrimevalItems.STRAW).pattern("###").pattern("###").pattern("###").criterion(hasItem(PrimevalItems.STRAW), conditionsFromItem(PrimevalItems.STRAW)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.STRAW_MAT, 3).input('#', PrimevalBlocks.STRAW_MESH).pattern("##").criterion(hasItem(PrimevalItems.STRAW), conditionsFromItem(PrimevalItems.STRAW)).offerTo(exporter);
        offerSlabRecipe(PrimevalBlocks.STRAW_SLAB, PrimevalBlocks.STRAW_BLOCK, exporter);
        offerStairsRecipe(PrimevalBlocks.STRAW_STAIRS, PrimevalBlocks.STRAW_BLOCK, exporter);
        offerSimpleCompactingRecipe(PrimevalBlocks.MUD, PrimevalItems.MUD_BALL, exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.MUD_BRICK, 2).input('#', PrimevalItems.MUD_BALL).pattern("###").criterion(hasItem(PrimevalItems.MUD_BALL), conditionsFromItem(PrimevalItems.MUD_BALL)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.MUD_BRICKS.block(), 4).input('#', PrimevalItems.MUD_BRICK).pattern("##").pattern("##").criterion(hasItem(PrimevalItems.MUD_BRICK), conditionsFromItem(PrimevalItems.MUD_BRICK)).offerTo(exporter);
        offerSlabRecipe(PrimevalBlocks.MUD_BRICKS.slab(), PrimevalBlocks.MUD_BRICKS.block(), exporter);
        offerStairsRecipe(PrimevalBlocks.MUD_BRICKS.stairs(), PrimevalBlocks.MUD_BRICKS.block(), exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.ROPE, 2).input('#', PrimevalItems.STRAW).pattern("#").pattern("#").pattern("#").criterion(hasItem(PrimevalItems.STRAW), conditionsFromItem(PrimevalItems.STRAW)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalBlocks.ROPE_LADDER, 2).input('#', PrimevalItems.STRAW).input('S', PrimevalItems.STICK).pattern("#S#").pattern("#S#").pattern("#S#").criterion(hasItem(PrimevalItems.STRAW), conditionsFromItem(PrimevalItems.STRAW)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(PrimevalItems.FLINT_AXE).input('#', PrimevalItems.FLINT).input('S', PrimevalItems.STRAW).input('/', PrimevalItems.STICK).pattern("#S").pattern("#/").criterion(hasItem(PrimevalItems.FLINT), conditionsFromItem(PrimevalItems.FLINT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.FLINT_KNIFE).input('#', PrimevalItems.FLINT).input('S', PrimevalItems.STRAW).input('/', PrimevalItems.STICK).pattern("S#").pattern("/ ").criterion(hasItem(PrimevalItems.FLINT), conditionsFromItem(PrimevalItems.FLINT)).offerTo(exporter);
        ShapedRecipeJsonBuilder.create(PrimevalItems.FLINT_SHOVEL).input('#', PrimevalItems.FLINT).input('S', PrimevalItems.STRAW).input('/', PrimevalItems.STICK).pattern("S#").pattern(" /").criterion(hasItem(PrimevalItems.FLINT), conditionsFromItem(PrimevalItems.FLINT)).offerTo(exporter);

        offerMetalToolRecipe(PrimevalItems.COPPER_TOOLS[0], PrimevalItems.COPPER_TOOL_PARTS[0], exporter);
        offerMetalToolRecipe(PrimevalItems.COPPER_TOOLS[1], PrimevalItems.COPPER_TOOL_PARTS[1], exporter);
        offerMetalToolRecipe(PrimevalItems.COPPER_TOOLS[2], PrimevalItems.COPPER_TOOL_PARTS[2], exporter);
        offerMetalToolRecipe(PrimevalItems.COPPER_TOOLS[3], PrimevalItems.COPPER_TOOL_PARTS[3], exporter);
        offerMetalToolRecipe(PrimevalItems.COPPER_TOOLS[4], PrimevalItems.COPPER_TOOL_PARTS[4], exporter);
        offerMetalToolRecipe(PrimevalItems.COPPER_TOOLS[5], PrimevalItems.COPPER_TOOL_PARTS[5], exporter);

        offerMetalToolRecipe(PrimevalItems.BRONZE_TOOLS[0], PrimevalItems.BRONZE_TOOL_PARTS[0], exporter);
        offerMetalToolRecipe(PrimevalItems.BRONZE_TOOLS[1], PrimevalItems.BRONZE_TOOL_PARTS[1], exporter);
        offerMetalToolRecipe(PrimevalItems.BRONZE_TOOLS[2], PrimevalItems.BRONZE_TOOL_PARTS[2], exporter);
        offerMetalToolRecipe(PrimevalItems.BRONZE_TOOLS[3], PrimevalItems.BRONZE_TOOL_PARTS[3], exporter);
        offerMetalToolRecipe(PrimevalItems.BRONZE_TOOLS[4], PrimevalItems.BRONZE_TOOL_PARTS[4], exporter);
        offerMetalToolRecipe(PrimevalItems.BRONZE_TOOLS[5], PrimevalItems.BRONZE_TOOL_PARTS[5], exporter);
    }

    public static void offerSimpleCompactingRecipe(ItemConvertible output, ItemConvertible input, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output).input('#', input).pattern("##").pattern("##").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(input, 4).input(output).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerMortarCompactingRecipe(ItemConvertible output, ItemConvertible input, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output, 2).input('#', input).input('S', PrimevalItemTags.MORTAR).pattern("S#").pattern("#S").criterion(hasItem(input), conditionsFromItem(input)).criterion("has_mortar", conditionsFromTag(PrimevalItemTags.MORTAR)).offerTo(exporter);
    }

    public static void offerSlabRecipe(ItemConvertible output, ItemConvertible input, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output, 6).input('#', input).pattern("###").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerStairsRecipe(ItemConvertible output, ItemConvertible input, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output, 8).input('#', input).pattern("#  ").pattern("## ").pattern("###").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerDoorRecipe(ItemConvertible output, ItemConvertible input, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output, 3).input('#', input).pattern("##").pattern("##").pattern("##").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerTrapdoorRecipe(ItemConvertible output, ItemConvertible input, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output, 2).input('#', input).pattern("###").pattern("###").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerFenceRecipe(ItemConvertible output, ItemConvertible input, ItemConvertible input2, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output, 6).input('W', input).input('#', input2).pattern("W#W").pattern("W#W").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerFenceGateRecipe(ItemConvertible output, ItemConvertible input, ItemConvertible input2, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output).input('#', input2).input('W', input).pattern("#W#").pattern("#W#").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerCrateRecipe(ItemConvertible output, ItemConvertible input, ItemConvertible slab, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output).input('#', input).input('S', slab).pattern("SSS").pattern("# #").pattern("###").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerWoodBlockSetRecipes(PrimevalBlocks.WoodBlockSet woodBlockSet, Consumer<RecipeJsonProvider> exporter) {
        offerDoorRecipe(woodBlockSet.door(), woodBlockSet.block(), exporter);
        offerTrapdoorRecipe(woodBlockSet.trapdoor(), woodBlockSet.block(), exporter);
        offerFenceRecipe(woodBlockSet.fence(), woodBlockSet.block(), PrimevalItems.STICK, exporter);
        offerFenceGateRecipe(woodBlockSet.fenceGate(), woodBlockSet.block(), PrimevalItems.STICK, exporter);
        offerFenceRecipe(woodBlockSet.logFence(), woodBlockSet.block(), woodBlockSet.slab(), exporter);
        offerSlabRecipe(woodBlockSet.slab(), woodBlockSet.block(), exporter);
        offerStairsRecipe(woodBlockSet.stairs(), woodBlockSet.block(), exporter);
    }

    public static void offerMetalToolRecipe(ItemConvertible output, ItemConvertible input, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(output).input('#', input).input('S', PrimevalItems.STICK).pattern("#").pattern("S").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

}
