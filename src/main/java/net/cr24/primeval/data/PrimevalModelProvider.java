package net.cr24.primeval.data;

import net.cr24.primeval.block.GrowingGrassBlock;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.TrunkBlock;
import net.cr24.primeval.block.functional.PitKilnBlock;
import net.cr24.primeval.block.functional.PrimevalCampfireBlock;
import net.cr24.primeval.block.functional.TimedTorchBlock;
import net.cr24.primeval.item.PrimevalItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.List;

public class PrimevalModelProvider extends FabricModelProvider {
    public PrimevalModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator modelGenerator) {
        registerParentedSingletonWithoutItem(PrimevalBlocks.LAYING_ITEM, Blocks.AIR, modelGenerator);

        registerFlower(PrimevalBlocks.OAK_SAPLING, Blocks.OAK_SAPLING, modelGenerator);
        registerParentedSingleton(PrimevalBlocks.OAK_LEAVES, Blocks.OAK_LEAVES, modelGenerator);
        registerTrunk(PrimevalBlocks.OAK_TRUNK, modelGenerator);
        registerLog(PrimevalBlocks.OAK_LOG, PrimevalBlocks.OAK_TRUNK, modelGenerator);
        registerCrate(PrimevalBlocks.OAK_CRATE, modelGenerator);
        registerWoodBlockSet(PrimevalBlocks.OAK_PLANK_BLOCKS, Blocks.OAK_PLANKS, Blocks.OAK_DOOR, Blocks.OAK_TRAPDOOR, true, modelGenerator);
        registerFlower(PrimevalBlocks.BIRCH_SAPLING, Blocks.BIRCH_SAPLING, modelGenerator);
        registerParentedSingleton(PrimevalBlocks.BIRCH_LEAVES, Blocks.BIRCH_LEAVES, modelGenerator);
        registerTrunk(PrimevalBlocks.BIRCH_TRUNK, modelGenerator);
        registerLog(PrimevalBlocks.BIRCH_LOG, PrimevalBlocks.BIRCH_TRUNK, modelGenerator);
        registerCrate(PrimevalBlocks.BIRCH_CRATE, modelGenerator);
        registerWoodBlockSet(PrimevalBlocks.BIRCH_PLANK_BLOCKS, Blocks.BIRCH_PLANKS, Blocks.BIRCH_DOOR, Blocks.BIRCH_TRAPDOOR, false, modelGenerator);
        registerFlower(PrimevalBlocks.SPRUCE_SAPLING, Blocks.SPRUCE_SAPLING, modelGenerator);
        registerParentedSingleton(PrimevalBlocks.SPRUCE_LEAVES, Blocks.SPRUCE_LEAVES, modelGenerator);
        registerTrunk(PrimevalBlocks.SPRUCE_TRUNK, modelGenerator);
        registerLog(PrimevalBlocks.SPRUCE_LOG, PrimevalBlocks.SPRUCE_TRUNK, modelGenerator);
        registerCrate(PrimevalBlocks.SPRUCE_CRATE, modelGenerator);
        registerWoodBlockSet(PrimevalBlocks.SPRUCE_PLANK_BLOCKS, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_DOOR, Blocks.SPRUCE_TRAPDOOR, false, modelGenerator);

        registerGrass(PrimevalBlocks.GRASS, Blocks.GRASS, modelGenerator);
        registerGrassyDirt(PrimevalBlocks.GRASSY_DIRT, modelGenerator);
        modelGenerator.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL, PrimevalBlocks.DIRT, PrimevalBlocks.COARSE_DIRT, PrimevalBlocks.SAND);
        registerSingleton(PrimevalBlocks.GRAVEL, modelGenerator);
        registerSingleton(PrimevalBlocks.CLAY_BLOCK, modelGenerator);
        modelGenerator.registerSingleton(PrimevalBlocks.COBBLESTONE, TexturedModel.CUBE_ALL);
        registerRandomMirroredInversion(PrimevalBlocks.STONE, modelGenerator);
        registerBlockSet(PrimevalBlocks.SMOOTH_STONE, modelGenerator);
        registerBlockSet(PrimevalBlocks.STONE_BRICKS, modelGenerator);
        registerSingleton(PrimevalBlocks.TERRACOTTA, modelGenerator);

        registerShrub(PrimevalBlocks.SHRUB, modelGenerator);
        registerBush(PrimevalBlocks.BUSH, modelGenerator);
        registerCampfire(PrimevalBlocks.CAMPFIRE, modelGenerator);
        registerTorch(PrimevalBlocks.CRUDE_TORCH, modelGenerator);
        registerPitKiln(PrimevalBlocks.PIT_KILN, modelGenerator);
        modelGenerator.registerSingleton(PrimevalBlocks.CRUDE_CRAFTING_BENCH, TexturedModel.CUBE_BOTTOM_TOP);
        registerRope(PrimevalBlocks.ROPE, modelGenerator);
        registerRopeLadder(PrimevalBlocks.ROPE_LADDER, modelGenerator);
        registerAshPile(PrimevalBlocks.ASH_PILE, modelGenerator);

        registerOreBlockSet(PrimevalBlocks.COPPER_MALACHITE_ORE, modelGenerator);
        registerOreBlockSet(PrimevalBlocks.COPPER_NATIVE_ORE, modelGenerator);
        registerOreBlockSet(PrimevalBlocks.IRON_HEMATITE_ORE, modelGenerator);
        registerOreBlockSet(PrimevalBlocks.TIN_CASSITERITE_ORE, modelGenerator);
        registerOreBlockSet(PrimevalBlocks.ZINC_SPHALERITE_ORE, modelGenerator);

        modelGenerator.registerSingleton(PrimevalBlocks.MOLTEN_BOTCHED_ALLOY, TexturedModel.PARTICLE);
        modelGenerator.registerSingleton(PrimevalBlocks.MOLTEN_BRASS, TexturedModel.PARTICLE);
        modelGenerator.registerSingleton(PrimevalBlocks.MOLTEN_BRONZE, TexturedModel.PARTICLE);
        modelGenerator.registerSingleton(PrimevalBlocks.MOLTEN_COPPER, TexturedModel.PARTICLE);
        modelGenerator.registerSingleton(PrimevalBlocks.MOLTEN_TIN, TexturedModel.PARTICLE);
        modelGenerator.registerSingleton(PrimevalBlocks.MOLTEN_ZINC, TexturedModel.PARTICLE);

        modelGenerator.registerWallPlant(PrimevalBlocks.MOSS);
        registerFlower(PrimevalBlocks.DANDELION, Blocks.DANDELION, modelGenerator);
        registerFlower(PrimevalBlocks.OXEYE_DAISY, Blocks.OXEYE_DAISY, modelGenerator);
        registerFlower(PrimevalBlocks.POPPY, Blocks.POPPY, modelGenerator);
        registerSingleton(PrimevalBlocks.WILD_CARROTS, modelGenerator);

        modelGenerator.registerSingleton(PrimevalBlocks.DAUB, TexturedModel.CUBE_ALL);
        modelGenerator.registerSingleton(PrimevalBlocks.FRAMED_DAUB, TexturedModel.CUBE_ALL);
        modelGenerator.registerSingleton(PrimevalBlocks.FRAMED_CROSS_DAUB, TexturedModel.CUBE_ALL);
        modelGenerator.registerSingleton(PrimevalBlocks.FRAMED_INVERTED_CROSS_DAUB, TexturedModel.CUBE_ALL);
        modelGenerator.registerSingleton(PrimevalBlocks.FRAMED_DIVIDED_DAUB, TexturedModel.CUBE_ALL);
        modelGenerator.registerAxisRotated(PrimevalBlocks.FRAMED_PILLAR_DAUB, TexturedModel.CUBE_COLUMN, TexturedModel.CUBE_COLUMN_HORIZONTAL);
        modelGenerator.registerSingleton(PrimevalBlocks.FRAMED_PLUS_DAUB, TexturedModel.CUBE_ALL);
        modelGenerator.registerSingleton(PrimevalBlocks.FRAMED_X_DAUB, TexturedModel.CUBE_ALL);

        registerRandomMirroredInversion(PrimevalBlocks.MUD, modelGenerator);
        registerBlockSet(PrimevalBlocks.MUD_BRICKS, modelGenerator);
        registerBlockSet(PrimevalBlocks.DRIED_BRICK_BLOCKS, modelGenerator);
        registerBlockSet(PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS, modelGenerator);
        registerBlockSet(PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS, modelGenerator);
        registerBlockSet(PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS, modelGenerator);

        registerSingleton(PrimevalBlocks.LARGE_FIRED_CLAY_POT, modelGenerator);
        registerSingleton(PrimevalBlocks.LARGE_DECORATIVE_FIRED_CLAY_POT, modelGenerator);
        registerSingleton(PrimevalBlocks.LARGE_CLAY_POT, modelGenerator);

        registerLayered(PrimevalBlocks.STRAW_PILE, PrimevalBlocks.STRAW_BLOCK, modelGenerator);
        modelGenerator.registerAxisRotated(PrimevalBlocks.STRAW_BLOCK, TexturedModel.CUBE_COLUMN);
        modelGenerator.registerSingleton(PrimevalBlocks.STRAW_MAT, TextureMap.wool(PrimevalBlocks.STRAW_MESH), Models.CARPET);
        registerSingleton(PrimevalBlocks.STRAW_MESH, modelGenerator);
        registerPillarSlab(PrimevalBlocks.STRAW_SLAB, PrimevalBlocks.STRAW_BLOCK, modelGenerator);
        registerPillarStairs(PrimevalBlocks.STRAW_STAIRS, PrimevalBlocks.STRAW_BLOCK, modelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator modelGenerator) {
        modelGenerator.register(PrimevalItems.ANIMAL_FAT, Models.GENERATED);
        modelGenerator.register(PrimevalItems.ASHES, Models.GENERATED);
        modelGenerator.register(PrimevalItems.BONE, Items.BONE, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CARROT, Items.CARROT, Models.GENERATED);
        modelGenerator.register(PrimevalItems.PORKCHOP, Items.PORKCHOP, Models.GENERATED);
        modelGenerator.register(PrimevalItems.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Models.GENERATED);
        modelGenerator.register(PrimevalItems.DRIED_BRICK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.FLINT, Items.FLINT, Models.GENERATED);
        modelGenerator.register(PrimevalItems.GUNPOWDER, Items.GUNPOWDER, Models.GENERATED);
        modelGenerator.register(PrimevalItems.MUD_BALL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.MUD_BRICK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.ROCK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.ROTTEN_FLESH, Items.ROTTEN_FLESH, Models.GENERATED);
        modelGenerator.register(PrimevalItems.SANDY_CLAY_BALL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.SANDY_CLAY_BRICK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.SPIDER_EYE, Items.SPIDER_EYE, Models.GENERATED);
        modelGenerator.register(PrimevalItems.STICK, Items.STICK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.STONE_BRICK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.STRAW, Models.GENERATED);
        modelGenerator.register(PrimevalItems.STRING, Items.STRING, Models.GENERATED);

        modelGenerator.register(PrimevalItems.CLAY_BALL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_BOWL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_BRICK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_TILE, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_VESSEL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_INGOT_MOLD, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_AXE_HEAD_MOLD, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_CHISEL_HEAD_MOLD, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_KNIFE_BLADE_MOLD, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_PICKAXE_HEAD_MOLD, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_SHOVEL_HEAD_MOLD, Models.GENERATED);
        modelGenerator.register(PrimevalItems.CLAY_SWORD_BLADE_MOLD, Models.GENERATED);

        modelGenerator.register(PrimevalItems.FIRED_CLAY_BOWL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_BRICK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_TILE, Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_VESSEL, Models.GENERATED);

        modelGenerator.register(PrimevalItems.FIRED_CLAY_INGOT_MOLD, "_copper", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_INGOT_MOLD, "_tin", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_INGOT_MOLD, "_zinc", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_INGOT_MOLD, "_bronze", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_INGOT_MOLD, "_brass", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_INGOT_MOLD, "_botched_alloy", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_AXE_HEAD_MOLD, "_copper", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_CHISEL_HEAD_MOLD, "_copper", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_KNIFE_BLADE_MOLD, "_copper", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_PICKAXE_HEAD_MOLD, "_copper", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_SHOVEL_HEAD_MOLD, "_copper", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_SWORD_BLADE_MOLD, "_copper", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_AXE_HEAD_MOLD, "_bronze", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_CHISEL_HEAD_MOLD, "_bronze", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_KNIFE_BLADE_MOLD, "_bronze", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_PICKAXE_HEAD_MOLD, "_bronze", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_SHOVEL_HEAD_MOLD, "_bronze", Models.GENERATED);
        modelGenerator.register(PrimevalItems.FIRED_CLAY_SWORD_BLADE_MOLD, "_bronze", Models.GENERATED);

        modelGenerator.register(PrimevalItems.RAW_COPPER_MALACHITE_LARGE, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_COPPER_MALACHITE_MEDIUM, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_COPPER_MALACHITE_SMALL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_COPPER_NATIVE_LARGE, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_COPPER_NATIVE_MEDIUM, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_COPPER_NATIVE_SMALL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_IRON_HEMATITE_LARGE, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_IRON_HEMATITE_MEDIUM, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_IRON_HEMATITE_SMALL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_TIN_CASSITERITE_LARGE, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_TIN_CASSITERITE_MEDIUM, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_TIN_CASSITERITE_SMALL, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_ZINC_SPHALERITE_LARGE, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_ZINC_SPHALERITE_MEDIUM, Models.GENERATED);
        modelGenerator.register(PrimevalItems.RAW_ZINC_SPHALERITE_SMALL, Models.GENERATED);

        modelGenerator.register(PrimevalItems.COPPER_CHUNK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.COPPER_INGOT, Models.GENERATED);
        modelGenerator.register(PrimevalItems.COPPER_COIN, Models.GENERATED);
        modelGenerator.register(PrimevalItems.TIN_CHUNK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.TIN_INGOT, Models.GENERATED);
        modelGenerator.register(PrimevalItems.ZINC_CHUNK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.ZINC_INGOT, Models.GENERATED);
        modelGenerator.register(PrimevalItems.BRONZE_CHUNK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.BRONZE_INGOT, Models.GENERATED);
        modelGenerator.register(PrimevalItems.BRASS_CHUNK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.BRASS_INGOT, Models.GENERATED);
        modelGenerator.register(PrimevalItems.BOTCHED_ALLOY_CHUNK, Models.GENERATED);
        modelGenerator.register(PrimevalItems.BOTCHED_ALLOY_INGOT, Models.GENERATED);

        modelGenerator.register(PrimevalItems.FLINT_AXE, Models.HANDHELD);
        modelGenerator.register(PrimevalItems.FLINT_KNIFE, Models.HANDHELD);
        modelGenerator.register(PrimevalItems.FLINT_SHOVEL, Models.HANDHELD);
        registerToolSet(PrimevalItems.BRONZE_TOOLS, modelGenerator);
        registerToolSet(PrimevalItems.COPPER_TOOLS, modelGenerator);
        registerToolPartSet(PrimevalItems.BRONZE_TOOL_PARTS, modelGenerator);
        registerToolPartSet(PrimevalItems.COPPER_TOOL_PARTS, modelGenerator);
    }

    public void registerToolSet(Item[] toolSet, ItemModelGenerator modelGenerator) {
        for (Item item : toolSet) modelGenerator.register(item, Models.HANDHELD);
    }

    public void registerToolPartSet(Item[] toolPartSet, ItemModelGenerator modelGenerator) {
        for (Item item : toolPartSet) modelGenerator.register(item, Models.GENERATED);
    }

    public void registerSingleton(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier model = ModelIds.getBlockModelId(block);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, model));
    }

    public void registerParentedSingleton(Block block, Block parent, BlockStateModelGenerator modelGenerator) {
        Identifier model = ModelIds.getBlockModelId(parent);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, model));
    }

    public void registerParentedSingletonWithoutItem(Block block, Block parent, BlockStateModelGenerator modelGenerator) {
        Identifier model = ModelIds.getBlockModelId(parent);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, model));
        modelGenerator.excludeFromSimpleItemModelGeneration(block);
    }

    public void registerRandomMirroredInversion(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier model = ModelIds.getBlockModelId(block);
        Identifier mirror = Models.CUBE_MIRRORED_ALL.upload(block, TextureMap.all(block), modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createBlockStateWithTwoModelAndRandomInversion(block, model, mirror));
    }

    public void registerLayered(Block layerBlock, Block fullBlock, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = ModelIds.getBlockModelId(fullBlock);
        modelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(layerBlock).coordinate(BlockStateVariantMap.create(Properties.LAYERS).register(height -> BlockStateVariant.create().put(VariantSettings.MODEL, height < 8 ? ModelIds.getBlockSubModelId(layerBlock, "_height" + height * 2) : identifier))));
    }

    public void registerBlockSet(PrimevalBlocks.BlockSet blockSet, BlockStateModelGenerator modelGenerator) {
        modelGenerator.registerSingleton(blockSet.block(), TexturedModel.CUBE_ALL);
        registerSlab(blockSet.slab(), blockSet.block(), modelGenerator);
        registerStairs(blockSet.stairs(), blockSet.block(), modelGenerator);
    }

    public void registerWoodBlockSet(PrimevalBlocks.WoodBlockSet woodBlockSet, Block texture, Block doorTexture, Block trapdoorTexture, boolean orientableTrapdoor, BlockStateModelGenerator modelGenerator) {
        registerParentedSingleton(woodBlockSet.block(), texture, modelGenerator);
        registerSlab(woodBlockSet.slab(), woodBlockSet.block(), modelGenerator);
        registerStairs(woodBlockSet.stairs(), woodBlockSet.block(), modelGenerator);
        registerTrapdoor(woodBlockSet.trapdoor(), trapdoorTexture, orientableTrapdoor, modelGenerator);
        registerDoor(woodBlockSet.door(), doorTexture, modelGenerator);
        registerLogFence(woodBlockSet.logFence(), woodBlockSet.fence(), modelGenerator);
        registerFence(woodBlockSet.fence(), texture, modelGenerator);
        registerFenceGate(woodBlockSet.fenceGate(), woodBlockSet.block(), modelGenerator);
    }

    public void registerOreBlockSet(PrimevalBlocks.OreBlockSet oreBlockSet, BlockStateModelGenerator modelGenerator) {
        for (Block block : oreBlockSet) modelGenerator.registerSingleton(block, TexturedModel.CUBE_ALL);
    }

    public void registerAshPile(Block block, BlockStateModelGenerator modelGenerator) {
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createBlockStateWithRandomHorizontalRotations(block, ModelIds.getBlockModelId(block)));
    }

    public void registerRope(Block block, BlockStateModelGenerator modelGenerator) {
        modelGenerator.registerAxisRotated(block, ModelIds.getBlockModelId(block));
        modelGenerator.registerItemModel(block.asItem());
    }

    public void registerRopeLadder(Block block, BlockStateModelGenerator modelGenerator) {
        modelGenerator.registerNorthDefaultHorizontalRotation(block);
        modelGenerator.registerItemModel(block);
    }

    public void registerFlower(Block block, Block parent, BlockStateModelGenerator modelGenerator) {
        Identifier model = ModelIds.getBlockModelId(parent);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, model));
        Models.GENERATED.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(parent), modelGenerator.modelCollector);
    }

    public void registerGrass(Block block, Block parent, BlockStateModelGenerator modelGenerator) {
        Identifier growth0 = ModelIds.getBlockSubModelId(block, "_growth0");
        Identifier growth1 = ModelIds.getBlockSubModelId(block, "_growth1");
        Identifier growth2 = ModelIds.getBlockSubModelId(block, "_growth2");
        Identifier growth3 = ModelIds.getBlockSubModelId(block, "_growth3");
        Identifier growth4 = ModelIds.getBlockSubModelId(block, "_growth4");
        modelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(GrowingGrassBlock.GROWTH_STATE)
                .register(0, BlockStateVariant.create().put(VariantSettings.MODEL, growth0))
                .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, growth1))
                .register(2, BlockStateVariant.create().put(VariantSettings.MODEL, growth2))
                .register(3, BlockStateVariant.create().put(VariantSettings.MODEL, growth3))
                .register(4, BlockStateVariant.create().put(VariantSettings.MODEL, growth4))
        ));
        Models.GENERATED.upload(ModelIds.getItemModelId(block.asItem()), TextureMap.layer0(parent), modelGenerator.modelCollector);
    }

    public void registerGrassyDirt(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = ModelIds.getBlockModelId(block);
        Identifier identifier1 = ModelIds.getBlockSubModelId(block, "_medium");
        Identifier identifier2 = ModelIds.getBlockSubModelId(block, "_dark");
        Identifier identifier3 = ModelIds.getBlockSubModelId(block, "_darkest");
        List<BlockStateVariant> list = List.of(new BlockStateVariant[]{BlockStateVariant.create().put(VariantSettings.MODEL, identifier), BlockStateVariant.create().put(VariantSettings.MODEL, identifier1).put(VariantSettings.Y, VariantSettings.Rotation.R90), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R180), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R270)});
        modelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.SNOWY)
                        .register(false, list)
                        .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(block, "_snowy")))
        ));
    }

    public void registerBush(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier big = ModelIds.getBlockSubModelId(block, "_big");
        Identifier small = ModelIds.getBlockSubModelId(block, "_small");
        modelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, big), BlockStateVariant.create().put(VariantSettings.MODEL, small)));
        modelGenerator.registerItemModel(block, "_big");
    }

    public void registerShrub(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier model = ModelIds.getBlockModelId(block);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, model));
        modelGenerator.registerItemModel(block.asItem());
    }

    public void registerCrate(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = TextureMap.getSubId(block, "_top_open");
        modelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.OPEN)
                .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP.upload(block, modelGenerator.modelCollector)))
                .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP.get(block).textures(textureMap -> textureMap.put(TextureKey.TOP, identifier)).upload(block, "_open", modelGenerator.modelCollector)))));
    }

    public void registerLogFence(Block logFence, Block fence, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = ModelIds.getBlockSubModelId(logFence, "_post");
        Identifier identifier2 = ModelIds.getBlockSubModelId(fence, "_side");
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(logFence, identifier, identifier2));
        Identifier identifier3 = ModelIds.getBlockSubModelId(logFence, "_inventory");
        modelGenerator.registerParentedItemModel(logFence, identifier3);
    }

    public void registerFence(Block fence, Block texture, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = Models.FENCE_POST.upload(fence, TextureMap.texture(texture), modelGenerator.modelCollector);
        Identifier identifier2 = Models.FENCE_SIDE.upload(fence, TextureMap.texture(texture), modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(fence, identifier, identifier2));
        Identifier identifier3 = Models.FENCE_INVENTORY.upload(fence, TextureMap.texture(texture), modelGenerator.modelCollector);
        modelGenerator.registerParentedItemModel(fence, identifier3);
    }

    public void registerFenceGate(Block fenceGate, Block texture, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = Models.TEMPLATE_FENCE_GATE_OPEN.upload(fenceGate, TextureMap.texture(texture), modelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_FENCE_GATE.upload(fenceGate, TextureMap.texture(texture), modelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(fenceGate, TextureMap.texture(texture), modelGenerator.modelCollector);
        Identifier identifier4 = Models.TEMPLATE_FENCE_GATE_WALL.upload(fenceGate, TextureMap.texture(texture), modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(fenceGate, identifier, identifier2, identifier3, identifier4));
    }

    public void registerSlab(Block slab, Block texture, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = Models.SLAB.upload(slab, TextureMap.all(texture), modelGenerator.modelCollector);
        Identifier identifier2 = Models.SLAB_TOP.upload(slab, TextureMap.all(texture), modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slab, identifier, identifier2, ModelIds.getBlockModelId(texture)));
        modelGenerator.registerParentedItemModel(slab, identifier);
    }

    public void registerStairs(Block stairs, Block texture, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = Models.INNER_STAIRS.upload(stairs, TextureMap.all(texture), modelGenerator.modelCollector);
        Identifier identifier2 = Models.STAIRS.upload(stairs, TextureMap.all(texture), modelGenerator.modelCollector);
        Identifier identifier3 = Models.OUTER_STAIRS.upload(stairs, TextureMap.all(texture), modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(stairs, identifier, identifier2, identifier3));
        modelGenerator.registerParentedItemModel(stairs, identifier2);
    }

    public void registerPillarSlab(Block slab, Block texture, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = Models.SLAB.upload(slab, TextureMap.sideTopBottom(texture), modelGenerator.modelCollector);
        Identifier identifier2 = Models.SLAB_TOP.upload(slab, TextureMap.sideTopBottom(texture), modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slab, identifier, identifier2, ModelIds.getBlockModelId(texture)));
        modelGenerator.registerParentedItemModel(slab, identifier);
    }

    public void registerPillarStairs(Block stairs, Block texture, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = Models.INNER_STAIRS.upload(stairs, TextureMap.sideTopBottom(texture), modelGenerator.modelCollector);
        Identifier identifier2 = Models.STAIRS.upload(stairs, TextureMap.sideTopBottom(texture), modelGenerator.modelCollector);
        Identifier identifier3 = Models.OUTER_STAIRS.upload(stairs, TextureMap.sideTopBottom(texture), modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(stairs, identifier, identifier2, identifier3));
        modelGenerator.registerParentedItemModel(stairs, identifier2);
    }

    public void registerDoor(Block door, Block texture, BlockStateModelGenerator modelGenerator) {
        TextureMap textureMap = TextureMap.topBottom(texture);
        Identifier identifier = Models.DOOR_BOTTOM_LEFT.upload(door, textureMap, modelGenerator.modelCollector);
        Identifier identifier2 = Models.DOOR_BOTTOM_LEFT_OPEN.upload(door, textureMap, modelGenerator.modelCollector);
        Identifier identifier3 = Models.DOOR_BOTTOM_RIGHT.upload(door, textureMap, modelGenerator.modelCollector);
        Identifier identifier4 = Models.DOOR_BOTTOM_RIGHT_OPEN.upload(door, textureMap, modelGenerator.modelCollector);
        Identifier identifier5 = Models.DOOR_TOP_LEFT.upload(door, textureMap, modelGenerator.modelCollector);
        Identifier identifier6 = Models.DOOR_TOP_LEFT_OPEN.upload(door, textureMap, modelGenerator.modelCollector);
        Identifier identifier7 = Models.DOOR_TOP_RIGHT.upload(door, textureMap, modelGenerator.modelCollector);
        Identifier identifier8 = Models.DOOR_TOP_RIGHT_OPEN.upload(door, textureMap, modelGenerator.modelCollector);
        modelGenerator.registerParentedItemModel(door, ModelIds.getItemModelId(texture.asItem()));
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createDoorBlockState(door, identifier, identifier2, identifier3, identifier4, identifier5, identifier6, identifier7, identifier8));
    }

    public void registerTrapdoor(Block trapdoor, Block texture, boolean orientable, BlockStateModelGenerator modelGenerator) {
        TextureMap textureMap = TextureMap.texture(texture);
        Identifier identifier = Models.TEMPLATE_TRAPDOOR_TOP.upload(trapdoor, textureMap, modelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_TRAPDOOR_BOTTOM.upload(trapdoor, textureMap, modelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_TRAPDOOR_OPEN.upload(trapdoor, textureMap, modelGenerator.modelCollector);
        modelGenerator.registerParentedItemModel(trapdoor, identifier2);
        if (orientable) {
            modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createOrientableTrapdoorBlockState(trapdoor, identifier, identifier2, identifier3));
        } else {
            modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createTrapdoorBlockState(trapdoor, identifier, identifier2, identifier3));
        }
    }

    public void registerLog(Block log, Block trunk, BlockStateModelGenerator modelGenerator) {
        Identifier identifier = ModelIds.getBlockSubModelId(trunk, "_0");
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(log, identifier, identifier));
        modelGenerator.registerItemModel(log.asItem());
    }

    public void registerTrunk(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier _0 = ModelIds.getBlockSubModelId(block, "_0");
        Identifier _1_core = ModelIds.getBlockSubModelId(block, "_1_core");
        Identifier _1_ns = ModelIds.getBlockSubModelId(block, "_1_ns");
        Identifier _1_ew = ModelIds.getBlockSubModelId(block, "_1_ew");
        Identifier _1_ud = ModelIds.getBlockSubModelId(block, "_1_ud");
        Identifier _2_core = ModelIds.getBlockSubModelId(block, "_2_core");
        Identifier _2_ns = ModelIds.getBlockSubModelId(block, "_2_ns");
        Identifier _2_ew = ModelIds.getBlockSubModelId(block, "_2_ew");
        Identifier _2_ud = ModelIds.getBlockSubModelId(block, "_2_ud");
        Identifier _3_core = ModelIds.getBlockSubModelId(block, "_3_core");
        Identifier _3_ns = ModelIds.getBlockSubModelId(block, "_3_ns");
        Identifier _3_ew = ModelIds.getBlockSubModelId(block, "_3_ew");
        Identifier _3_ud = ModelIds.getBlockSubModelId(block, "_3_ud");
        modelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
                .with(When.create().set(TrunkBlock.SIZE, 0), BlockStateVariant.create().put(VariantSettings.MODEL, _0))
                .with(When.create().set(TrunkBlock.SIZE, 1), BlockStateVariant.create().put(VariantSettings.MODEL, _1_core))
                .with(When.create().set(TrunkBlock.SIZE, 1).set(TrunkBlock.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, _1_ns))
                .with(When.create().set(TrunkBlock.SIZE, 1).set(TrunkBlock.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, _1_ew))
                .with(When.create().set(TrunkBlock.SIZE, 1).set(TrunkBlock.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, _1_ns).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .with(When.create().set(TrunkBlock.SIZE, 1).set(TrunkBlock.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, _1_ew).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .with(When.create().set(TrunkBlock.SIZE, 1).set(TrunkBlock.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, _1_ud))
                .with(When.create().set(TrunkBlock.SIZE, 1).set(TrunkBlock.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, _1_ud).put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .with(When.create().set(TrunkBlock.SIZE, 2), BlockStateVariant.create().put(VariantSettings.MODEL, _2_core))
                .with(When.create().set(TrunkBlock.SIZE, 2).set(TrunkBlock.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, _2_ns))
                .with(When.create().set(TrunkBlock.SIZE, 2).set(TrunkBlock.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, _2_ew))
                .with(When.create().set(TrunkBlock.SIZE, 2).set(TrunkBlock.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, _2_ns).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .with(When.create().set(TrunkBlock.SIZE, 2).set(TrunkBlock.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, _2_ew).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .with(When.create().set(TrunkBlock.SIZE, 2).set(TrunkBlock.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, _2_ud))
                .with(When.create().set(TrunkBlock.SIZE, 2).set(TrunkBlock.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, _2_ud).put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .with(When.create().set(TrunkBlock.SIZE, 3), BlockStateVariant.create().put(VariantSettings.MODEL, _3_core))
                .with(When.create().set(TrunkBlock.SIZE, 3).set(TrunkBlock.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, _3_ns))
                .with(When.create().set(TrunkBlock.SIZE, 3).set(TrunkBlock.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, _3_ew))
                .with(When.create().set(TrunkBlock.SIZE, 3).set(TrunkBlock.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, _3_ns).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .with(When.create().set(TrunkBlock.SIZE, 3).set(TrunkBlock.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, _3_ew).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
                .with(When.create().set(TrunkBlock.SIZE, 3).set(TrunkBlock.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, _3_ud))
                .with(When.create().set(TrunkBlock.SIZE, 3).set(TrunkBlock.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, _3_ud).put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true))
        );
    }

    public void registerCampfire(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier _0 = ModelIds.getBlockSubModelId(block, "_0");
        Identifier _1 = ModelIds.getBlockSubModelId(block, "_1");
        Identifier _2 = ModelIds.getBlockSubModelId(block, "_2");
        Identifier _3 = ModelIds.getBlockSubModelId(block, "_3");
        Identifier fire_0 = ModelIds.getBlockSubModelId(block, "fire_0");
        Identifier fire_1 = ModelIds.getBlockSubModelId(block, "fire_1");
        Identifier fire_2 = ModelIds.getBlockSubModelId(block, "fire_2");
        Identifier fire_3 = ModelIds.getBlockSubModelId(block, "fire_3");
        modelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
                .with(When.create().set(PrimevalCampfireBlock.KINDLING, 0), BlockStateVariant.create().put(VariantSettings.MODEL, _0))
                .with(When.create().set(PrimevalCampfireBlock.KINDLING, 1), BlockStateVariant.create().put(VariantSettings.MODEL, _1))
                .with(When.create().set(PrimevalCampfireBlock.KINDLING, 2), BlockStateVariant.create().put(VariantSettings.MODEL, _2))
                .with(When.create().set(PrimevalCampfireBlock.KINDLING, 3), BlockStateVariant.create().put(VariantSettings.MODEL, _3))
                .with(When.create().set(PrimevalCampfireBlock.FIRE_SCALE, 0).set(PrimevalCampfireBlock.LIT, true), BlockStateVariant.create().put(VariantSettings.MODEL, fire_0))
                .with(When.create().set(PrimevalCampfireBlock.FIRE_SCALE, 1).set(PrimevalCampfireBlock.LIT, true), BlockStateVariant.create().put(VariantSettings.MODEL, fire_1))
                .with(When.create().set(PrimevalCampfireBlock.FIRE_SCALE, 2).set(PrimevalCampfireBlock.LIT, true), BlockStateVariant.create().put(VariantSettings.MODEL, fire_2))
                .with(When.create().set(PrimevalCampfireBlock.FIRE_SCALE, 3).set(PrimevalCampfireBlock.LIT, true), BlockStateVariant.create().put(VariantSettings.MODEL, fire_3))
        );
    }

    public void registerTorch(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier unlit_ground = ModelIds.getBlockSubModelId(block, "_unlit_ground");
        Identifier unlit_wall = ModelIds.getBlockSubModelId(block, "_unlit_wall");
        Identifier ground = ModelIds.getBlockSubModelId(block, "_ground");
        Identifier wall = ModelIds.getBlockSubModelId(block, "_wall");
        Identifier burnt_ground = ModelIds.getBlockSubModelId(block, "_burnt_ground");
        Identifier burnt_wall = ModelIds.getBlockSubModelId(block, "_burnt_wall");
        modelGenerator.registerItemModel(block, "_unlit");
        modelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(TimedTorchBlock.BURNOUT_STAGE, TimedTorchBlock.DIRECTION)
                .register(0, Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, unlit_ground))
                .register(0, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, unlit_wall).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .register(0, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, unlit_wall).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .register(0, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, unlit_wall))
                .register(0, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, unlit_wall).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .register(1, Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, ground))
                .register(1, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .register(1, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .register(1, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, wall))
                .register(1, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .register(2, Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, ground))
                .register(2, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .register(2, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .register(2, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, wall))
                .register(2, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .register(3, Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, ground))
                .register(3, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .register(3, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .register(3, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, wall))
                .register(3, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .register(4, Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, ground))
                .register(4, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .register(4, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .register(4, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, wall))
                .register(4, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, wall).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .register(5, Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, burnt_ground))
                .register(5, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, burnt_wall).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .register(5, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, burnt_wall).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                .register(5, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, burnt_wall))
                .register(5, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, burnt_wall).put(VariantSettings.Y, VariantSettings.Rotation.R90))
        ));
    }

    public void registerPitKiln(Block block, BlockStateModelGenerator modelGenerator) {
        Identifier height2 = ModelIds.getBlockSubModelId(PrimevalBlocks.STRAW_PILE, "_height2");
        Identifier height4 = ModelIds.getBlockSubModelId(PrimevalBlocks.STRAW_PILE, "_height4");
        Identifier height6 = ModelIds.getBlockSubModelId(PrimevalBlocks.STRAW_PILE, "_height6");
        Identifier height8 = ModelIds.getBlockSubModelId(PrimevalBlocks.STRAW_PILE, "_height8");
        Identifier height10 = ModelIds.getBlockSubModelId(PrimevalBlocks.STRAW_PILE, "_height10");
        Identifier log1 = ModelIds.getBlockSubModelId(block, "_1log");
        Identifier log2 = ModelIds.getBlockSubModelId(block, "_2log");
        Identifier log3 = ModelIds.getBlockSubModelId(block, "_3log");
        Identifier log4 = ModelIds.getBlockSubModelId(block, "_4log");
        modelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
                .with(When.create().set(PitKilnBlock.BUILD_STEP, 0), BlockStateVariant.create().put(VariantSettings.MODEL, height2))
                .with(When.create().set(PitKilnBlock.BUILD_STEP, 1), BlockStateVariant.create().put(VariantSettings.MODEL, height4))
                .with(When.create().set(PitKilnBlock.BUILD_STEP, 2), BlockStateVariant.create().put(VariantSettings.MODEL, height6))
                .with(When.create().set(PitKilnBlock.BUILD_STEP, 3), BlockStateVariant.create().put(VariantSettings.MODEL, height8))
                .with(When.create().set(PitKilnBlock.BUILD_STEP, 4), BlockStateVariant.create().put(VariantSettings.MODEL, height10))
                .with(When.create().set(PitKilnBlock.BUILD_STEP, 5), BlockStateVariant.create().put(VariantSettings.MODEL, log1))
                .with(When.create().set(PitKilnBlock.BUILD_STEP, 6), BlockStateVariant.create().put(VariantSettings.MODEL, log2))
                .with(When.create().set(PitKilnBlock.BUILD_STEP, 7), BlockStateVariant.create().put(VariantSettings.MODEL, log3))
                .with(When.create().set(PitKilnBlock.BUILD_STEP, 8), BlockStateVariant.create().put(VariantSettings.MODEL, log4))
        );
    }
}