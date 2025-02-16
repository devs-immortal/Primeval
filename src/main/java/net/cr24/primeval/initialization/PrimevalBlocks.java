package net.cr24.primeval.initialization;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.block.*;
import net.cr24.primeval.block.entity.*;
import net.cr24.primeval.block.functional.*;
import net.cr24.primeval.block.plant.*;
import net.cr24.primeval.item.WeightedBlockItem;
import net.cr24.primeval.util.*;
import net.cr24.primeval.world.trunker.BirchTrunker;
import net.cr24.primeval.world.trunker.OakTrunker;
import net.cr24.primeval.world.trunker.SpruceTrunker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static net.cr24.primeval.Primeval.identify;

/*
 * This class stores all blocks and fluids in the mod.
 */
public class PrimevalBlocks {

    // region SETTINGS
    private static AbstractBlock.Settings SETTINGS_SOIL() { return AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(2.1f, 2.0f).sounds(BlockSoundGroup.GRAVEL); }
    private static AbstractBlock.Settings SETTINGS_TOUGH_SOIL() { return AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(3f, 2.0f).sounds(BlockSoundGroup.GRAVEL);}
    private static AbstractBlock.Settings SETTINGS_GRASSY() { return AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GREEN).strength(2.5f, 2.0f).sounds(BlockSoundGroup.GRASS);}
    private static AbstractBlock.Settings SETTINGS_SAND() { return AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(1.8f, 2.0f).sounds(BlockSoundGroup.SAND);}
    private static AbstractBlock.Settings SETTINGS_STONE() { return AbstractBlock.Settings.create().mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 6.0f).requiresTool();}
    private static AbstractBlock.Settings SETTINGS_PLANT() { return AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.05f, 0f).sounds(BlockSoundGroup.GRASS).replaceable().noCollision(); }
    private static AbstractBlock.Settings SETTINGS_CROP() { return AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.05f, 0f).sounds(BlockSoundGroup.GRASS).noCollision().ticksRandomly(); }
    private static AbstractBlock.Settings SETTINGS_LOG() { return AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(5.0f, 6.0f).sounds(BlockSoundGroup.WOOD).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_TRUNK() { return AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(8.0f, 8.0f).sounds(BlockSoundGroup.WOOD).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_FIRED_CLAY() { return AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).strength(4.0f, 6.0f).sounds(BlockSoundGroup.STONE).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_REFINED_WOOD() { return AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_STRAW() { return AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(0.5f).sounds(BlockSoundGroup.GRASS); }
    private static AbstractBlock.Settings SETTINGS_LOGPILE() { return AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(1.0f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque().requiresTool(); }

    // endregion


    // region TERRAIN BLOCKS

    public static final Block DIRT = registerBlock("dirt", SETTINGS_SOIL(), (settings) -> new SemiSupportedBlock(0.2f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block COARSE_DIRT = registerBlock("coarse_dirt", SETTINGS_SOIL(), (settings) -> new SemiSupportedBlock(0.2f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block CLAY = registerBlock("clay", SETTINGS_SOIL(), (settings) -> new SemiSupportedBlock(0.3f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block MUD = registerBlock("mud", SETTINGS_SOIL().velocityMultiplier(0.4f), (settings) -> new MuckBlock(0.25f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block DRY_DIRT = registerBlock("dry_dirt", SETTINGS_TOUGH_SOIL(), (settings) -> new SemiSupportedBlock(0.2f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block GRASSY_DIRT = registerBlock("grassy_dirt", SETTINGS_GRASSY().ticksRandomly(), (settings) -> new GrassySoilBlock(0.35f, PrimevalBlocks.DIRT, new Block[]{DIRT}, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block GRASSY_CLAY = registerBlock("grassy_clay", SETTINGS_GRASSY().ticksRandomly(), (settings) -> new GrassySoilBlock(0.45f, PrimevalBlocks.CLAY, new Block[]{CLAY}, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block SAND = registerBlock("sand", SETTINGS_SAND(), (settings) -> new SemiSupportedBlock(0.1f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block GRAVEL = registerBlock("gravel", SETTINGS_SAND(), (settings) -> new SemiSupportedBlock(0.1f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block COBBLESTONE = registerBlock("cobblestone", SETTINGS_STONE().strength(5.0f, 6.0f), (settings) -> new SemiSupportedBlock(0.1f, settings), Weight.HEAVY, Size.MEDIUM);
    public static final Block STONE = registerBlock("stone", SETTINGS_STONE(), (settings) -> new CascadingBlock(0.35f, COBBLESTONE, settings), Weight.HEAVY, Size.MEDIUM);
    public static final Block SANDSTONE = registerBlock("sandstone", SETTINGS_STONE(), (settings) -> new CascadingBlock(0.3f, settings), Weight.HEAVY, Size.MEDIUM);
    public static final Block DIRT_FARMLAND = registerBlockWithoutItem("farmland_dirt", SETTINGS_SOIL().ticksRandomly(), (settings) -> new PrimevalFarmlandBlock(0.2f, DIRT, new Block[]{DIRT, GRASSY_DIRT}, settings));
    public static final Block CLAY_FARMLAND = registerBlockWithoutItem("farmland_clay", SETTINGS_SOIL().ticksRandomly(), (settings) -> new PrimevalFarmlandBlock(0.3f, CLAY, new Block[]{CLAY, GRASSY_CLAY}, settings));

    // endregion

    // region PLANT BLOCKS

    // Oak Trees
    public static final Block OAK_LOG = registerBlockWithoutItem("oak_log", SETTINGS_LOG(), PillarBlock::new);
    public static final Block OAK_TRUNK = registerBlockWithoutItem("oak_trunk", SETTINGS_TRUNK().nonOpaque(), (settings) -> new TrunkBlock(OakTrunker.INSTANCE, settings));
    public static final Block OAK_LEAVES = registerBlockWithoutItem("oak_leaves", AbstractBlock.Settings.copy(Blocks.OAK_LEAVES), LeafBlock::new);
    // Birch Trees
    public static final Block BIRCH_LOG = registerBlockWithoutItem("birch_log", SETTINGS_LOG(), PillarBlock::new);
    public static final Block BIRCH_TRUNK = registerBlockWithoutItem("birch_trunk", SETTINGS_TRUNK().nonOpaque(), (settings) -> new TrunkBlock(BirchTrunker.INSTANCE, settings));
    public static final Block BIRCH_LEAVES = registerBlockWithoutItem("birch_leaves", AbstractBlock.Settings.copy(Blocks.OAK_LEAVES), LeafBlock::new);
    // Spruce Trees
    public static final Block SPRUCE_LOG = registerBlockWithoutItem("spruce_log", SETTINGS_LOG(), PillarBlock::new);
    public static final Block SPRUCE_TRUNK = registerBlockWithoutItem("spruce_trunk", SETTINGS_TRUNK().nonOpaque(), (settings) -> new TrunkBlock(SpruceTrunker.INSTANCE, settings));
    public static final Block SPRUCE_LEAVES = registerBlockWithoutItem("spruce_leaves", AbstractBlock.Settings.copy(Blocks.OAK_LEAVES), LeafBlock::new);
    // Saplings+
    public static final Block OAK_SAPLING = registerBlock("oak_sapling", SETTINGS_PLANT().ticksRandomly(), (settings) -> new GrowingSaplingBlock(OakTrunker.INSTANCE, settings), Weight.HEAVY, Size.LARGE);
    public static final Block BIRCH_SAPLING = registerBlock("birch_sapling", SETTINGS_PLANT().ticksRandomly(), (settings) -> new GrowingSaplingBlock(BirchTrunker.INSTANCE, settings), Weight.HEAVY, Size.LARGE);
    public static final Block SPRUCE_SAPLING = registerBlock("spruce_sapling", SETTINGS_PLANT().ticksRandomly(), (settings) -> new GrowingSaplingBlock(SpruceTrunker.INSTANCE, settings), Weight.HEAVY, Size.LARGE);
    public static final Block GRASS = registerBlock("grass", SETTINGS_PLANT().ticksRandomly(), GrowingGrassBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block BUSH = registerBlock("bush", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block SPIKED_PLANT = registerBlock("plant_0", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block LEAFY_PLANT = registerBlock("plant_1", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block SHRUB = registerBlock("shrub", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.LIGHT, Size.MEDIUM);
    public static final Block MOSS = registerBlock("moss", SETTINGS_PLANT().ticksRandomly(), SpreadingMossBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    // Flowers
    public static final Block POPPY = registerBlock("poppy", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block DANDELION = registerBlock("dandelion", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block OXEYE_DAISY = registerBlock("oxeye_daisy", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block CORNFLOWER = registerBlock("cornflower", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block LILY_OF_THE_VALLEY = registerBlock("lily_of_the_valley", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    // Misc
    public static final Block REEDS = registerBlock("reeds", SETTINGS_PLANT().ticksRandomly().pistonBehavior(PistonBehavior.DESTROY), ReedsBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block RIVER_GRASS = registerBlock("river_grass", SETTINGS_PLANT().offset(AbstractBlock.OffsetType.XZ), PrimevalWaterPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);

    // endregion

    // region ORES

    public static final OreBlockSet COPPER_MALACHITE_ORE = registerOreBlockSet("copper_malachite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet COPPER_NATIVE_ORE = registerOreBlockSet("copper_native_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet TIN_CASSITERITE_ORE = registerOreBlockSet("tin_cassiterite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet ZINC_SPHALERITE_ORE = registerOreBlockSet("zinc_sphalerite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet GOLD_NATIVE_ORE = registerOreBlockSet("gold_native_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet IRON_HEMATITE_ORE = registerOreBlockSet("iron_hematite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet LAZURITE_ORE = registerOreBlockSet("lazurite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final Block FOSSIL = registerBlock("fossil", SETTINGS_STONE(), (settings) -> new SemiSupportedBlock(0.35f, COBBLESTONE, settings), Weight.HEAVY, Size.LARGE);

    // endregion
    
    // region CRAFTED BLOCKS

    public static final Block STRAW_BLOCK = registerBlock("straw_block", SETTINGS_STRAW(), PillarBlock::new, Weight.LIGHT, Size.MEDIUM);
    public static final Block STRAW_STAIRS = registerBlock("straw_stairs", SETTINGS_STRAW(), (settings) -> new StairsBlock(STRAW_BLOCK.getDefaultState(), settings), Weight.LIGHT, Size.MEDIUM);
    public static final Block STRAW_SLAB = registerBlock("straw_slab", SETTINGS_STRAW(), SlabBlock::new, Weight.LIGHT, Size.MEDIUM);
    public static final Block STRAW_MESH = registerBlock("straw_mesh", SETTINGS_STRAW(), Block::new, Weight.LIGHT, Size.MEDIUM);
    public static final Block STRAW_MAT = registerBlock("straw_mat", SETTINGS_STRAW().strength(0.3f), CarpetBlock::new, Weight.LIGHT, Size.MEDIUM);
    public static final Block TERRACOTTA = registerBlock("terracotta", SETTINGS_FIRED_CLAY(), Block::new, Weight.HEAVY, Size.MEDIUM);
    public static final ColoredBlockSet COLORED_TERRACOTTA = registerColoredBlockSet("terracotta", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM);
    public static final BlockSet FIRED_CLAY_SHINGLE_BLOCKS = registerBlockSet("fired_clay_shingles", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM);
    public static final ColoredBlockSetSet COLORED_FIRED_CLAY_SHINGLE_BLOCKS = registerColoredBlockSetSet("fired_clay_shingles", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM);
    public static final BlockSet FIRED_CLAY_BRICK_BLOCKS = registerBlockSet("fired_clay_bricks", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM);
    public static final BlockSet FIRED_CLAY_TILES_BLOCKS = registerBlockSet("fired_clay_tiles", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM);
    public static final BlockSet DRIED_BRICK_BLOCKS = registerBlockSet("dried_bricks", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM);
    public static final BlockSet MUD_BRICKS = registerBlockSet("mud_bricks", SETTINGS_TOUGH_SOIL(), Weight.NORMAL, Size.MEDIUM);
    public static final BlockSet CRUDE_BRICKS = registerBlockSet("crude_bricks", SETTINGS_STONE(), Weight.HEAVY, Size.MEDIUM);
    public static final BlockSet STONE_BRICKS = registerBlockSet("stone_bricks", SETTINGS_STONE(), Weight.HEAVY, Size.MEDIUM);
    public static final BlockSet SMOOTH_STONE = registerBlockSet("smooth_stone", SETTINGS_STONE(), Weight.HEAVY, Size.MEDIUM);
    public static final Block STONE_INDENT = registerBlock("stone_indent", SETTINGS_STONE(), Block::new, Weight.HEAVY, Size.MEDIUM);
    public static final Block STONE_PILLAR = registerBlock("stone_pillar", SETTINGS_STONE(), PillarBlock::new, Weight.HEAVY, Size.MEDIUM);
    public static final BlockSet STONE_PAVER = registerBlockSet("stone_paver", SETTINGS_STONE(), Weight.HEAVY, Size.MEDIUM);
    public static final Block DAUB = registerBlock("daub", SETTINGS_REFINED_WOOD(), Block::new, Weight.NORMAL, Size.MEDIUM);
    public static final Block FRAMED_DAUB = registerBlock("framed_daub", SETTINGS_REFINED_WOOD(), Block::new, Weight.NORMAL, Size.MEDIUM);
    public static final Block FRAMED_PILLAR_DAUB = registerBlock("framed_pillar_daub", SETTINGS_REFINED_WOOD(), PillarBlock::new, Weight.NORMAL, Size.MEDIUM);
    public static final Block FRAMED_CROSS_DAUB = registerBlock("framed_cross_daub", SETTINGS_REFINED_WOOD(), Block::new, Weight.NORMAL, Size.MEDIUM);
    public static final Block FRAMED_INVERTED_CROSS_DAUB = registerBlock("framed_inverted_cross_daub", SETTINGS_REFINED_WOOD(), Block::new, Weight.NORMAL, Size.MEDIUM);
    public static final Block FRAMED_X_DAUB = registerBlock("framed_x_daub", SETTINGS_REFINED_WOOD(), Block::new, Weight.NORMAL, Size.MEDIUM);
    public static final Block FRAMED_PLUS_DAUB = registerBlock("framed_plus_daub", SETTINGS_REFINED_WOOD(), Block::new, Weight.NORMAL, Size.MEDIUM);
    public static final Block FRAMED_DIVIDED_DAUB = registerBlock("framed_divided_daub", SETTINGS_REFINED_WOOD(), Block::new, Weight.NORMAL, Size.MEDIUM);
    public static final WoodBlockSet OAK_PLANK_BLOCKS = registerWoodBlockSet("oak", AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool(), PrimevalTypes.Wood.OAK, PrimevalTypes.BlockSet.OAK, Weight.NORMAL, Size.MEDIUM);
    public static final WoodBlockSet BIRCH_PLANK_BLOCKS = registerWoodBlockSet("birch", AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool(), PrimevalTypes.Wood.BIRCH, PrimevalTypes.BlockSet.BIRCH, Weight.NORMAL, Size.MEDIUM);
    public static final WoodBlockSet SPRUCE_PLANK_BLOCKS = registerWoodBlockSet("spruce", AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool(), PrimevalTypes.Wood.SPRUCE, PrimevalTypes.BlockSet.SPRUCE, Weight.NORMAL, Size.MEDIUM);
    public static final BlockSet WICKER = registerBlockSet("wicker", SETTINGS_REFINED_WOOD(), Weight.LIGHT, Size.MEDIUM);
    public static final Block WICKER_DOOR = registerBlock("wicker_door", SETTINGS_REFINED_WOOD().nonOpaque(), (settings) -> new DoorBlock(PrimevalTypes.BlockSet.WICKER, settings), Weight.LIGHT, Size.MEDIUM);
    public static final Block WICKER_TRAPDOOR = registerBlock("wicker_trapdoor", SETTINGS_REFINED_WOOD().nonOpaque(), (settings) -> new TrapdoorBlock(PrimevalTypes.BlockSet.WICKER, settings), Weight.LIGHT, Size.MEDIUM);
    public static final Block WICKER_BARS = registerBlock("wicker_bars", SETTINGS_REFINED_WOOD().nonOpaque(), PaneBlock::new, Weight.LIGHT, Size.MEDIUM);
    public static final Block ROPE = registerBlock("rope", AbstractBlock.Settings.create().strength(0.2F).sounds(BlockSoundGroup.GRASS), ChainBlock::new, Weight.LIGHT, Size.SMALL);
    public static final Block ROPE_LADDER = registerBlock("rope_ladder", AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(0.3F).sounds(BlockSoundGroup.WOOD).nonOpaque(), SuspendedLadderBlock::new, Weight.NORMAL, Size.MEDIUM);
    public static final Block OAK_LOG_PILE = registerBlockWithoutItem("oak_log_pile", SETTINGS_LOGPILE(), LogPileBlock::new);
    public static final Block BIRCH_LOG_PILE = registerBlockWithoutItem("birch_log_pile", SETTINGS_LOGPILE(), LogPileBlock::new);
    public static final Block SPRUCE_LOG_PILE = registerBlockWithoutItem("spruce_log_pile", SETTINGS_LOGPILE(), LogPileBlock::new);
    
    // endregion

    // region CROPS

    public static final Block CARROT_CROP = registerBlockWithoutItem("crop_carrot", SETTINGS_CROP(), (settings) -> new PrimevalCropBlock(3, List.of(4, 6, 10, 14), settings));
    public static final Block WILD_CARROTS = registerBlockWithoutItem("wild_carrots", SETTINGS_PLANT(), PrimevalPlantBlock::new);
    public static final Block WHEAT_CROP = registerBlockWithoutItem("crop_wheat", SETTINGS_CROP(), (settings) -> new PrimevalCropBlock(7, List.of(3, 5, 7, 9, 12, 14, 15, 16), settings));
    public static final Block WILD_WHEAT = registerBlockWithoutItem("wild_wheat", SETTINGS_PLANT(), PrimevalPlantBlock::new);
    public static final Block CABBAGE_CROP = registerBlockWithoutItem("crop_cabbage", SETTINGS_CROP(), (settings) -> new PrimevalCropBlock( 3, List.of(4, 7, 9, 11), settings));
    public static final Block WILD_CABBAGE = registerBlockWithoutItem("wild_cabbage", SETTINGS_PLANT(), PrimevalPlantBlock::new);
    public static final Block BEANS_CROP = registerBlockWithoutItem("crop_beans", SETTINGS_CROP(), (settings) -> new PrimevalCropBlock(3,List.of(5, 8, 11, 14), settings));
    public static final Block WILD_BEANS = registerBlockWithoutItem("wild_beans", SETTINGS_PLANT(), PrimevalPlantBlock::new);
    public static final Block POTATO_CROP = registerBlockWithoutItem("crop_potato", SETTINGS_CROP(), (settings) -> new PrimevalCropBlock(3, List.of(5, 8, 11, 16), settings));
    public static final Block WILD_POTATOES = registerBlockWithoutItem("wild_potatoes", SETTINGS_PLANT(), PrimevalPlantBlock::new);

    // endregion

    // region TECHNICAL BLOCKS

    public static final Block STRAW_PILE = registerBlockWithoutItem("straw", SETTINGS_STRAW(), StrawLayeredBlock::new);
    public static final Block ASH_PILE = registerBlockWithoutItem("ash_pile", AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND), AshPileBlock::new);
    public static final Block LAYING_ITEM = registerBlockWithoutItem("laying_item", AbstractBlock.Settings.create().noCollision().nonOpaque().breakInstantly(), LayingItemBlock::new);
//    public static final Block OAK_CRATE = registerBlock("oak_crate", new CrateBlock(SETTINGS_REFINED_WOOD()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
//    public static final Block BIRCH_CRATE = registerBlock("birch_crate", new CrateBlock(SETTINGS_REFINED_WOOD()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
//    public static final Block SPRUCE_CRATE = registerBlock("spruce_crate", new CrateBlock(SETTINGS_REFINED_WOOD()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
//
//    public static final Block LARGE_CLAY_POT = registerBlock("large_clay_pot", new DecorativePotBlock(SETTINGS_SOIL().nonOpaque()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
//    public static final Block LARGE_FIRED_CLAY_POT = registerBlock("fired_large_clay_pot", new StoragePotBlock(SETTINGS_FIRED_CLAY().nonOpaque()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
//    public static final Block LARGE_DECORATIVE_FIRED_CLAY_POT = registerBlock("fired_large_decorative_clay_pot", new DecorativePotBlock(SETTINGS_FIRED_CLAY().nonOpaque()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
//    public static final Block WICKER_BASKET = registerBlock("wicker_basket", new WickerBasketBlock(SETTINGS_REFINED_WOOD().nonOpaque()), Weight.NORMAL, Size.LARGE, PRIMEVAL_BLOCKS);

    // endregion





    // region BLOCK ENTITIES

//    public static final BlockEntityType<PitKilnBlockEntity> PIT_KILN_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("pit_kiln_block_entity"), FabricBlockEntityTypeBuilder.create(PitKilnBlockEntity::new, PIT_KILN).build());
    public static final BlockEntityType<AshPileBlockEntity> ASH_PILE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Primeval.identify("ash_pile_block_entity"), FabricBlockEntityTypeBuilder.create(AshPileBlockEntity::new, ASH_PILE).build());
    public static final BlockEntityType<LayingItemBlockEntity> LAYING_ITEM_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Primeval.identify("laying_item_block_entity"), FabricBlockEntityTypeBuilder.create(LayingItemBlockEntity::new, LAYING_ITEM).build());
    public static final BlockEntityType<LogPileBlockEntity> LOG_PILE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Primeval.identify("log_pile_block_entity"), FabricBlockEntityTypeBuilder.create(LogPileBlockEntity::new, OAK_LOG_PILE, BIRCH_LOG_PILE, SPRUCE_LOG_PILE).build());
//    public static final BlockEntityType<CrateBlockEntity> CRATE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("crate_block_entity"), FabricBlockEntityTypeBuilder.create(CrateBlockEntity::new, OAK_CRATE, BIRCH_CRATE, SPRUCE_CRATE).build());
//    public static final BlockEntityType<StoragePotBlockEntity> LARGE_POT_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("large_pot_block_entity"), FabricBlockEntityTypeBuilder.create(StoragePotBlockEntity::new, LARGE_FIRED_CLAY_POT).build());
//    public static final BlockEntityType<WickerBasketBlockEntity> WICKER_BASKET_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("wicker_basket_block_entity"), FabricBlockEntityTypeBuilder.create(WickerBasketBlockEntity::new, WICKER_BASKET).build());
//    public static final BlockEntityType<PrimevalCampfireBlockEntity> CAMPFIRE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("campfire_block_entity"), FabricBlockEntityTypeBuilder.create(PrimevalCampfireBlockEntity::new, CAMPFIRE).build());
//    public static final BlockEntityType<QuernBlockEntity> QUERN_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("quern_block_entity"), FabricBlockEntityTypeBuilder.create(QuernBlockEntity::new, QUERN).build());

    // endregion

    public static void init() {
        OakTrunker.INSTANCE.build();
        BirchTrunker.INSTANCE.build();
        SpruceTrunker.INSTANCE.build();
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        // Render Layers
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                GRASSY_DIRT, GRASSY_CLAY,
                GRASS, BUSH, SPIKED_PLANT, LEAFY_PLANT, SHRUB,
                POPPY, DANDELION, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY,
                MOSS,
                REEDS, RIVER_GRASS,
                /* Crop */
                CARROT_CROP, WILD_CARROTS,
                WHEAT_CROP, WILD_WHEAT,
                CABBAGE_CROP, WILD_CABBAGE,
                BEANS_CROP, WILD_BEANS,
                POTATO_CROP, WILD_POTATOES,
                REEDS, RIVER_GRASS,
                /* Tree */
                OAK_SAPLING,
                OAK_LEAVES,
                BIRCH_SAPLING,
                BIRCH_LEAVES,
                SPRUCE_SAPLING,
                SPRUCE_LEAVES,
                /* Misc */
                //CRUDE_TORCH,
                //CAMPFIRE,
                ROPE,
                ROPE_LADDER,
                OAK_PLANK_BLOCKS.door(),
                OAK_PLANK_BLOCKS.trapdoor(),
                WICKER_DOOR,
                WICKER_TRAPDOOR,
                WICKER_BARS
        );

        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.7D, 1.0D)),
                GRASSY_DIRT, GRASSY_CLAY,
                GRASS, BUSH, SPIKED_PLANT, LEAFY_PLANT, SHRUB
        );
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getColor(0.5D, 1.0D)),
                OAK_LEAVES
        );
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos)+2621440 : FoliageColors.getColor(0.5D, 1.0D)),
                BIRCH_LEAVES
        );
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos)-4082973 : FoliageColors.getColor(0.5D, 1.0D)),
                SPRUCE_LEAVES
        );

        // Block Renderers
//        BlockEntityRendererRegistry.register(PIT_KILN_BLOCK_ENTITY, PitKilnBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ASH_PILE_BLOCK_ENTITY, AshPileBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(LAYING_ITEM_BLOCK_ENTITY, LayingItemBlockEntityRenderer::new);
//        BlockEntityRendererRegistry.register(CAMPFIRE_BLOCK_ENTITY, PrimevalCampfireBlockEntityRenderer::new);
//        BlockEntityRendererRegistry.register(QUERN_BLOCK_ENTITY, QuernBlockEntityRenderer::new);

//        ScreenRegistry.register(CRATE_SCREEN_HANDLER, Primeval3x5ContainerScreen::new);
    }

    // region HELPER FUNCTIONS

    private static RegistryKey<Block> blockKey(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, identify(id));
    }

    @SafeVarargs
    private static <T extends Block> T registerBlock(String id, AbstractBlock.Settings settings, BlockFactory<T> factory, Weight w, Size s, Consumer<Block>... additionalActions) {
        RegistryKey<Block> blockKey = blockKey(id);
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
        var registeredBlock = Registry.register(Registries.BLOCK, blockKey, factory.create(settings.registryKey(blockKey)));
        Registry.register(Registries.ITEM, identify(id), new WeightedBlockItem(registeredBlock, w, s, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey()));
        for (var action : additionalActions) {
            action.accept(registeredBlock);
        }
        return registeredBlock;
    }

    @SafeVarargs
    private static <T extends Block> T registerBlockWithoutItem(String id, AbstractBlock.Settings settings, BlockFactory<T> factory, Consumer<Block>... additionalActions) {
        RegistryKey<Block> blockKey = blockKey(id);
        var registeredBlock = Registry.register(Registries.BLOCK, blockKey, factory.create(settings.registryKey(blockKey)));
        for (var action : additionalActions) {
            action.accept(registeredBlock);
        }
        return registeredBlock;
    }

    @FunctionalInterface
    public interface BlockFactory<T extends Block> {
        T create(AbstractBlock.Settings settings);
    }

    // records

    private static ColoredBlockSet registerColoredBlockSet(String baseId, AbstractBlock.Settings settings, Weight weight, Size size) {
        return new ColoredBlockSet(
                registerBlock(baseId + "_white", settings, Block::new, weight, size),
                registerBlock(baseId + "_orange", settings, Block::new, weight, size),
                registerBlock(baseId + "_magenta", settings, Block::new, weight, size),
                registerBlock(baseId + "_light_blue", settings, Block::new, weight, size),
                registerBlock(baseId + "_yellow", settings, Block::new, weight, size),
                registerBlock(baseId + "_lime", settings, Block::new, weight, size),
                registerBlock(baseId + "_pink", settings, Block::new, weight, size),
                registerBlock(baseId + "_dark_gray", settings, Block::new, weight, size),
                registerBlock(baseId + "_light_gray", settings, Block::new, weight, size),
                registerBlock(baseId + "_cyan", settings, Block::new, weight, size),
                registerBlock(baseId + "_purple", settings, Block::new, weight, size),
                registerBlock(baseId + "_blue", settings, Block::new, weight, size),
                registerBlock(baseId + "_brown", settings, Block::new, weight, size),
                registerBlock(baseId + "_green", settings, Block::new, weight, size),
                registerBlock(baseId + "_red", settings, Block::new, weight, size),
                registerBlock(baseId + "_black", settings, Block::new, weight, size)
        );
    }
    private static ColoredBlockSetSet registerColoredBlockSetSet(String baseId, AbstractBlock.Settings settings, Weight weight, Size size) {
        return new ColoredBlockSetSet(
                registerBlockSet(baseId + "_white", settings, weight, size),
                registerBlockSet(baseId + "_orange", settings, weight, size),
                registerBlockSet(baseId + "_magenta", settings, weight, size),
                registerBlockSet(baseId + "_light_blue", settings, weight, size),
                registerBlockSet(baseId + "_yellow", settings, weight, size),
                registerBlockSet(baseId + "_lime", settings, weight, size),
                registerBlockSet(baseId + "_pink", settings, weight, size),
                registerBlockSet(baseId + "_dark_gray", settings, weight, size),
                registerBlockSet(baseId + "_light_gray", settings, weight, size),
                registerBlockSet(baseId + "_cyan", settings, weight, size),
                registerBlockSet(baseId + "_purple", settings, weight, size),
                registerBlockSet(baseId + "_blue", settings, weight, size),
                registerBlockSet(baseId + "_brown", settings, weight, size),
                registerBlockSet(baseId + "_green", settings, weight, size),
                registerBlockSet(baseId + "_red", settings, weight, size),
                registerBlockSet(baseId + "_black", settings, weight, size)
        );
    }

    private static BlockSet registerBlockSet(String id, AbstractBlock.Settings settings, Weight weight, Size size) {
        return registerBlockSet(id, id+"_stairs", id+"_slab", settings, weight, size);
    }
    private static BlockSet registerBlockSet(String base_id, String stairs_id, String slab_id, AbstractBlock.Settings settings, Weight weight, Size size) {
        Block base = registerBlock(base_id, settings, Block::new, Weight.LIGHT, Size.MEDIUM);
        return new BlockSet(
                base,
                registerBlock(stairs_id, settings, (s) -> new StairsBlock(base.getDefaultState(), s), Weight.LIGHT, Size.MEDIUM),
                registerBlock(slab_id, settings, SlabBlock::new, Weight.LIGHT, Size.MEDIUM)
        );
    }

    private static WoodBlockSet registerWoodBlockSet(String id, AbstractBlock.Settings settings, WoodType woodType, BlockSetType blockSetType, Weight weight, Size size) {
        return registerWoodBlockSet(id+"_planks", id+"_stairs", id+"_slab", id+"_panel", id+"_fence", id+"_log_fence", id+"_fence_gate", id+"_door", id+"_trapdoor", settings, woodType, blockSetType, weight, size);
    }
    private static WoodBlockSet registerWoodBlockSet(String block_id, String stairs_id, String slab_id, String panel_id, String fence_id, String log_fence_id, String fence_gate_id, String door_id, String trapdoor_id, AbstractBlock.Settings settings, WoodType woodType, BlockSetType blockSetType, Weight weight, Size size) {
        Block base = registerBlock(block_id, settings, Block::new, Weight.LIGHT, Size.MEDIUM);

        return new WoodBlockSet(
                base,
                registerBlock(stairs_id, settings, (s) -> new StairsBlock(base.getDefaultState(), s), Weight.LIGHT, Size.MEDIUM),
                registerBlock(slab_id, settings, SlabBlock::new, Weight.LIGHT, Size.MEDIUM),
                registerBlock(panel_id, settings, Block::new, Weight.LIGHT, Size.MEDIUM),
                registerBlock(fence_id, settings, FenceBlock::new, Weight.LIGHT, Size.MEDIUM),
                registerBlock(log_fence_id, settings, FenceBlock::new, Weight.LIGHT, Size.MEDIUM),
                registerBlock(fence_gate_id, settings, (s) -> new FenceGateBlock(woodType, s), Weight.LIGHT, Size.MEDIUM),
                registerBlock(door_id, settings, (s) -> new DoorBlock(blockSetType, s), Weight.LIGHT, Size.MEDIUM),
                registerBlock(trapdoor_id, settings, (s) -> new TrapdoorBlock(blockSetType, s.nonOpaque()), Weight.LIGHT, Size.MEDIUM)
        );
    }

    private static OreBlockSet registerOreBlockSet(String ore_id, AbstractBlock.Settings s, Weight weight, Size size) {
        return new OreBlockSet(
                registerBlock(ore_id + "_small", s, (settings) -> new SemiSupportedBlock(0.35f, COBBLESTONE, settings), weight, size),
                registerBlock(ore_id + "_medium", s, (settings) -> new SemiSupportedBlock(0.35f, COBBLESTONE, settings), weight, size),
                registerBlock(ore_id + "_large", s, (settings) -> new SemiSupportedBlock(0.35f, COBBLESTONE, settings), weight, size)
        );
    }

    // Block Sets

    public record BlockSet(Block block, StairsBlock stairs, SlabBlock slab) implements Iterable<Block> {
        public @NotNull Iterator<Block> iterator() {
            return Arrays.stream(new Block[]{block, stairs, slab}).iterator();
        }
    }

    public record ColoredBlockSet(Block white, Block orange, Block magenta, Block lightBlue, Block yellow, Block lime, Block pink, Block darkGray, Block lightGray, Block cyan, Block purple, Block blue, Block brown, Block green, Block red, Block black) implements Iterable<Block> {
        public @NotNull Iterator<Block> iterator() {
            return Arrays.stream(new Block[]{white, orange, magenta, lightBlue, yellow, lime, pink, darkGray, lightGray, cyan, purple, blue, brown, green, red, black}).iterator();
        }
    }
    public record ColoredBlockSetSet(BlockSet white, BlockSet orange, BlockSet magenta, BlockSet lightBlue, BlockSet yellow, BlockSet lime, BlockSet pink, BlockSet darkGray, BlockSet lightGray, BlockSet cyan, BlockSet purple, BlockSet blue, BlockSet brown, BlockSet green, BlockSet red, BlockSet black) implements Iterable<BlockSet> {
        public @NotNull Iterator<BlockSet> iterator() {
            return Arrays.stream(new BlockSet[]{white, orange, magenta, lightBlue, yellow, lime, pink, darkGray, lightGray, cyan, purple, blue, brown, green, red, black}).iterator();
        }
    }
    public record WoodBlockSet(Block block, StairsBlock stairs, SlabBlock slab, Block panel, FenceBlock fence, FenceBlock logFence, FenceGateBlock fenceGate, DoorBlock door, TrapdoorBlock trapdoor) implements Iterable<Block> {
        public @NotNull Iterator<Block> iterator() {
            return Arrays.stream(new Block[]{block, stairs, slab, panel, fence, logFence, fenceGate, door, trapdoor}).iterator();
        }
    }
    public record OreBlockSet(Block small, Block medium, Block large) implements Iterable<Block> {
        public @NotNull Iterator<Block> iterator() {
            return Arrays.stream(new Block[]{small, medium, large}).iterator();
        }
    }

    // endregion

}
