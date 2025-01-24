package net.cr24.primeval.block;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.block.entity.*;
import net.cr24.primeval.block.functional.*;
import net.cr24.primeval.block.plant.*;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.cr24.primeval.item.WeightedBlockItem;
import net.cr24.primeval.screen.Primeval3x5ContainerScreen;
import net.cr24.primeval.screen.PrimevalContainerScreenHandler;
import net.cr24.primeval.world.gen.trunker.BirchTrunker;
import net.cr24.primeval.world.gen.trunker.OakTrunker;
import net.cr24.primeval.world.gen.trunker.SpruceTrunker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

import static net.cr24.primeval.block.plant.PrimevalPlantBlock.CENTER_CHECK;
import static net.cr24.primeval.item.PrimevalItems.PRIMEVAL_BLOCKS;
import static net.cr24.primeval.item.tool.PrimevalHoeItem.hoeables;

@SuppressWarnings("unused")
public class PrimevalBlocks {

    // Block Settings
    private static AbstractBlock.Settings SETTINGS_SOIL() { return AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(2.1f, 2.0f).sounds(BlockSoundGroup.GRAVEL); }
    private static AbstractBlock.Settings SETTINGS_TOUGH_SOIL() { return AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(3f, 2.0f).sounds(BlockSoundGroup.GRAVEL);}
    private static AbstractBlock.Settings SETTINGS_GRASSY() { return AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GREEN).strength(2.5f, 2.0f).sounds(BlockSoundGroup.GRASS);}
    private static AbstractBlock.Settings SETTINGS_SAND() { return AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(1.8f, 2.0f).sounds(BlockSoundGroup.SAND);}
    private static AbstractBlock.Settings SETTINGS_STONE() { return AbstractBlock.Settings.create().mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 6.0f).requiresTool();}
    private static AbstractBlock.Settings SETTINGS_PLANT() { return AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.05f, 0f).sounds(BlockSoundGroup.GRASS).noCollision(); }
    private static AbstractBlock.Settings SETTINGS_CROP() { return AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.05f, 0f).sounds(BlockSoundGroup.GRASS).noCollision().ticksRandomly(); }
    private static AbstractBlock.Settings SETTINGS_LOG() { return AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(5.0f, 6.0f).sounds(BlockSoundGroup.WOOD).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_TRUNK() { return AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(8.0f, 8.0f).sounds(BlockSoundGroup.WOOD).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_FIRED_CLAY() { return AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).strength(4.0f, 6.0f).sounds(BlockSoundGroup.STONE).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_REFINED_WOOD() { return AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool(); }

    // Terrain blocks
    public static final Block DIRT = registerBlock("dirt", new SemiSupportedBlock(SETTINGS_SOIL(), 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COARSE_DIRT = registerBlock("coarse_dirt", new SemiSupportedBlock(SETTINGS_SOIL(), 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block CLAY_BLOCK = registerBlock("block_of_clay", new SemiSupportedBlock(SETTINGS_SOIL(), 0.3f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block MUD = registerBlock("mud", new MudBlock(SETTINGS_SOIL().velocityMultiplier(0.4f), 0.25f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block DRY_DIRT = registerBlock("dry_dirt", new SemiSupportedBlock(SETTINGS_TOUGH_SOIL().sounds(BlockSoundGroup.DRIPSTONE_BLOCK), 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block GRASSY_DIRT = registerBlock("grassy_dirt", new GrassySoilBlock(SETTINGS_GRASSY().ticksRandomly(), 0.35f, PrimevalBlocks.DIRT, new Block[]{DIRT}), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block GRASSY_CLAY = registerBlock("grassy_clay", new GrassySoilBlock(SETTINGS_GRASSY().ticksRandomly(), 0.45f, PrimevalBlocks.CLAY_BLOCK, new Block[]{CLAY_BLOCK}), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block SAND = registerBlock("sand", new SemiSupportedBlock(SETTINGS_SAND(), 0.1f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block GRAVEL = registerBlock("gravel", new SemiSupportedBlock(SETTINGS_SAND(), 0.1f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COBBLESTONE = registerBlock("cobblestone", new SemiSupportedBlock(SETTINGS_STONE().strength(5.0f, 6.0f), 0.1f), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STONE = registerBlock("stone", new CascadingBlock(SETTINGS_STONE(), 0.35f, COBBLESTONE), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block SANDSTONE = registerBlock("sandstone", new CascadingBlock(SETTINGS_STONE(), 0.3f), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block DIRT_FARMLAND = registerBlockWithoutItem("farmland_dirt", new PrimevalFarmlandBlock(SETTINGS_SOIL().ticksRandomly(), 0.2f, DIRT, new Block[]{DIRT, GRASSY_DIRT}));
    public static final Block CLAY_FARMLAND = registerBlockWithoutItem("farmland_clay", new PrimevalFarmlandBlock(SETTINGS_SOIL().ticksRandomly(), 0.3f, CLAY_BLOCK, new Block[]{CLAY_BLOCK}));

    //// Plant blocks
    // Oak Trees
    public static final Block OAK_LOG = registerBlockWithoutItem("oak_log", new PillarBlock(SETTINGS_LOG()));
    public static final Block OAK_TRUNK = registerBlockWithoutItem("oak_trunk", new TrunkBlock(SETTINGS_TRUNK().nonOpaque(), OakTrunker.INSTANCE));
    public static final Block OAK_LEAVES = registerBlockWithoutItem("oak_leaves", new LeafBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    // Birch Trees
    public static final Block BIRCH_LOG = registerBlockWithoutItem("birch_log", new PillarBlock(SETTINGS_LOG()));
    public static final Block BIRCH_TRUNK = registerBlockWithoutItem("birch_trunk", new TrunkBlock(SETTINGS_TRUNK().nonOpaque(), BirchTrunker.INSTANCE));
    public static final Block BIRCH_LEAVES = registerBlockWithoutItem("birch_leaves", new LeafBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    // Spruce Trees
    public static final Block SPRUCE_LOG = registerBlockWithoutItem("spruce_log", new PillarBlock(SETTINGS_LOG()));
    public static final Block SPRUCE_TRUNK = registerBlockWithoutItem("spruce_trunk", new TrunkBlock(SETTINGS_TRUNK().nonOpaque(), SpruceTrunker.INSTANCE));
    public static final Block SPRUCE_LEAVES = registerBlockWithoutItem("spruce_leaves", new LeafBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    // Saplings+
    public static final Block OAK_SAPLING = registerBlock("oak_sapling", new GrowingSaplingBlock(SETTINGS_PLANT().ticksRandomly(), OakTrunker.INSTANCE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block BIRCH_SAPLING = registerBlock("birch_sapling", new GrowingSaplingBlock(SETTINGS_PLANT().ticksRandomly(), BirchTrunker.INSTANCE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block SPRUCE_SAPLING = registerBlock("spruce_sapling", new GrowingSaplingBlock(SETTINGS_PLANT().ticksRandomly(), SpruceTrunker.INSTANCE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block GRASS = registerBlock("grass", new GrowingGrassBlock(SETTINGS_PLANT().ticksRandomly()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block BUSH = registerBlock("bush", new PrimevalPlantBlock(SETTINGS_PLANT()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block SPIKED_PLANT = registerBlock("plant_0", new PrimevalPlantBlock(SETTINGS_PLANT()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block LEAFY_PLANT = registerBlock("plant_1", new PrimevalPlantBlock(SETTINGS_PLANT()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block SHRUB = registerBlock("shrub", new PrimevalPlantBlock(SETTINGS_PLANT()), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block MOSS = registerBlock("moss", new SpreadingMossBlock(SETTINGS_PLANT().ticksRandomly()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    // Flowers
    public static final Block POPPY = registerBlock("poppy", new PrimevalPlantBlock(SETTINGS_PLANT()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block DANDELION = registerBlock("dandelion", new PrimevalPlantBlock(SETTINGS_PLANT()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block OXEYE_DAISY = registerBlock("oxeye_daisy", new PrimevalPlantBlock(SETTINGS_PLANT()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block CORNFLOWER = registerBlock("cornflower", new PrimevalPlantBlock(SETTINGS_PLANT()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block LILY_OF_THE_VALLEY = registerBlock("lily_of_the_valley", new PrimevalPlantBlock(SETTINGS_PLANT()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    // Misc
    public static final Block REEDS = registerBlock("reeds", new ReedsBlock(SETTINGS_PLANT().ticksRandomly()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block RIVER_GRASS = registerBlock("river_grass", new PrimevalWaterPlantBlock(SETTINGS_PLANT().offset(AbstractBlock.OffsetType.XZ)), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);


    // Ore blocks
    public static final OreBlockSet COPPER_MALACHITE_ORE = registerOreBlockSet("copper_malachite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final OreBlockSet COPPER_NATIVE_ORE = registerOreBlockSet("copper_native_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final OreBlockSet TIN_CASSITERITE_ORE = registerOreBlockSet("tin_cassiterite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final OreBlockSet ZINC_SPHALERITE_ORE = registerOreBlockSet("zinc_sphalerite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final OreBlockSet GOLD_NATIVE_ORE = registerOreBlockSet("gold_native_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final OreBlockSet IRON_HEMATITE_ORE = registerOreBlockSet("iron_hematite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final OreBlockSet LAZURITE_ORE = registerOreBlockSet("lazurite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block FOSSIL = registerBlock("fossil", new SemiSupportedBlock(SETTINGS_STONE(), 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);


    // Crafted Blocks
    public static final Block STRAW_BLOCK = registerBlock("straw_block", new PillarBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STRAW_STAIRS = registerBlock("straw_stairs", new PrimevalStairsBlock(STRAW_BLOCK.getDefaultState(), AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STRAW_SLAB = registerBlock("straw_slab", new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STRAW_MESH = registerBlock("straw_mesh", new Block(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STRAW_MAT = registerBlock("straw_mat", new CarpetBlock(AbstractBlock.Settings.create().strength(0.3F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block TERRACOTTA = registerBlock("terracotta", new Block(SETTINGS_FIRED_CLAY()), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final ColoredBlockSet COLORED_TERRACOTTA = registerColoredBlockSet("terracotta", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet FIRED_CLAY_SHINGLE_BLOCKS = registerBlockSet("fired_clay_shingles", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final ColoredBlockSetSet COLORED_FIRED_CLAY_SHINGLE_BLOCKS = registerColoredBlockSetSet("fired_clay_shingles", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet FIRED_CLAY_BRICK_BLOCKS = registerBlockSet("fired_clay_bricks", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet FIRED_CLAY_TILES_BLOCKS = registerBlockSet("fired_clay_tiles", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet DRIED_BRICK_BLOCKS = registerBlockSet("dried_bricks", SETTINGS_FIRED_CLAY(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet MUD_BRICKS = registerBlockSet("mud_bricks", SETTINGS_TOUGH_SOIL(), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet CRUDE_BRICKS = registerBlockSet("crude_bricks", SETTINGS_STONE(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet STONE_BRICKS = registerBlockSet("stone_bricks", SETTINGS_STONE(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet SMOOTH_STONE = registerBlockSet("smooth_stone", SETTINGS_STONE(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STONE_INDENT = registerBlock("stone_indent", new Block(SETTINGS_STONE()), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STONE_PILLAR = registerBlock("stone_pillar", new PillarBlock(SETTINGS_STONE()), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet STONE_PAVER = registerBlockSet("stone_paver", SETTINGS_STONE(), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block DAUB = registerBlock("daub", new Block(SETTINGS_REFINED_WOOD()), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block FRAMED_DAUB = registerBlock("framed_daub", new Block(SETTINGS_REFINED_WOOD()), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block FRAMED_PILLAR_DAUB = registerBlock("framed_pillar_daub", new PillarBlock(SETTINGS_REFINED_WOOD()), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block FRAMED_CROSS_DAUB = registerBlock("framed_cross_daub", new Block(SETTINGS_REFINED_WOOD()), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block FRAMED_INVERTED_CROSS_DAUB = registerBlock("framed_inverted_cross_daub", new Block(SETTINGS_REFINED_WOOD()), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block FRAMED_X_DAUB = registerBlock("framed_x_daub", new Block(SETTINGS_REFINED_WOOD()), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block FRAMED_PLUS_DAUB = registerBlock("framed_plus_daub", new Block(SETTINGS_REFINED_WOOD()), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block FRAMED_DIVIDED_DAUB = registerBlock("framed_divided_daub", new Block(SETTINGS_REFINED_WOOD()), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final WoodBlockSet OAK_PLANK_BLOCKS = registerWoodBlockSet("oak", AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool(), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final WoodBlockSet BIRCH_PLANK_BLOCKS = registerWoodBlockSet("birch", AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool(), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final WoodBlockSet SPRUCE_PLANK_BLOCKS = registerWoodBlockSet("spruce", AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool(), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final BlockSet WICKER = registerBlockSet("wicker", SETTINGS_REFINED_WOOD(), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block WICKER_DOOR = registerBlock("wicker_door", new PrimevalDoorBlock(SETTINGS_REFINED_WOOD().nonOpaque()), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block WICKER_TRAPDOOR = registerBlock("wicker_trapdoor", new PrimevalTrapdoorBlock(SETTINGS_REFINED_WOOD().nonOpaque()), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block WICKER_BARS = registerBlock("wicker_bars", new PaneBlock(SETTINGS_REFINED_WOOD().nonOpaque()), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block ROPE = registerBlock("rope", new ChainBlock(AbstractBlock.Settings.create().strength(0.2F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block ROPE_LADDER = registerBlock("rope_ladder", new SuspendedLadderBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(0.3F).sounds(BlockSoundGroup.WOOD).nonOpaque()), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block OAK_LOG_PILE = registerBlockWithoutItem("oak_log_pile", new LogPileBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(1.0f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque().requiresTool()));
    public static final Block BIRCH_LOG_PILE = registerBlockWithoutItem("birch_log_pile", new LogPileBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(1.0f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque().requiresTool()));
    public static final Block SPRUCE_LOG_PILE = registerBlockWithoutItem("spruce_log_pile", new LogPileBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(1.0f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque().requiresTool()));


    // Crops
    public static final Block CARROT_CROP = registerBlockWithoutItem("crop_carrot", new PrimevalCropBlock(SETTINGS_CROP(), 3, new double[]{4, 6, 10, 14} ));
    public static final Block WILD_CARROTS = registerBlockWithoutItem("wild_carrots", new PrimevalPlantBlock(SETTINGS_PLANT()));
    public static final Block WHEAT_CROP = registerBlockWithoutItem("crop_wheat", new PrimevalCropBlock(SETTINGS_CROP(), 7, new double[]{3, 5, 7, 9, 12, 14, 15, 16} ));
    public static final Block WILD_WHEAT = registerBlockWithoutItem("wild_wheat", new PrimevalPlantBlock(SETTINGS_PLANT()));
    public static final Block CABBAGE_CROP = registerBlockWithoutItem("crop_cabbage", new PrimevalCropBlock(SETTINGS_CROP(), 3, new double[]{4, 7, 9, 11} ));
    public static final Block WILD_CABBAGE = registerBlockWithoutItem("wild_cabbage", new PrimevalPlantBlock(SETTINGS_PLANT()));
    public static final Block BEANS_CROP = registerBlockWithoutItem("crop_beans", new PrimevalCropBlock(SETTINGS_CROP(), 3, new double[]{5, 8, 11, 14} ));
    public static final Block WILD_BEANS = registerBlockWithoutItem("wild_beans", new PrimevalPlantBlock(SETTINGS_PLANT()));
    public static final Block POTATO_CROP = registerBlockWithoutItem("crop_potato", new PrimevalCropBlock(SETTINGS_CROP(), 3, new double[]{5, 8, 11, 16} ));
    public static final Block WILD_POTATOES = registerBlockWithoutItem("wild_potatoes", new PrimevalPlantBlock(SETTINGS_PLANT()));


    // Technical Blocks or Blocks with other BlockItems than themselves
    public static final Block STRAW_PILE = registerBlockWithoutItem("straw", new StrawLayeredBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.GRASS)));
    public static final Block ASH_PILE = registerBlockWithoutItem("ash_pile", new AshPileBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block LAYING_ITEM = registerBlockWithoutItem("laying_item", new LayingItemBlock(AbstractBlock.Settings.create().noCollision().nonOpaque().breakInstantly()));
    public static final Block OAK_CRATE = registerBlock("oak_crate", new CrateBlock(SETTINGS_REFINED_WOOD()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block BIRCH_CRATE = registerBlock("birch_crate", new CrateBlock(SETTINGS_REFINED_WOOD()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block SPRUCE_CRATE = registerBlock("spruce_crate", new CrateBlock(SETTINGS_REFINED_WOOD()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    public static final Block LARGE_CLAY_POT = registerBlock("large_clay_pot", new DecorativePotBlock(SETTINGS_SOIL().nonOpaque()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block LARGE_FIRED_CLAY_POT = registerBlock("fired_large_clay_pot", new StoragePotBlock(SETTINGS_FIRED_CLAY().nonOpaque()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block LARGE_DECORATIVE_FIRED_CLAY_POT = registerBlock("fired_large_decorative_clay_pot", new DecorativePotBlock(SETTINGS_FIRED_CLAY().nonOpaque()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block WICKER_BASKET = registerBlock("wicker_basket", new WickerBasketBlock(SETTINGS_REFINED_WOOD().nonOpaque()), Weight.NORMAL, Size.LARGE, PRIMEVAL_BLOCKS);


    // Intractable Blocks
    public static final Block PIT_KILN = registerBlockWithoutItem("pit_kiln", new PitKilnBlock(AbstractBlock.Settings.create().strength(1.0F).sounds(BlockSoundGroup.GRASS).nonOpaque()));
    public static final Block CRUDE_CRAFTING_BENCH = registerBlock("crude_crafting_bench", new PrimevalCraftingTableBlock(SETTINGS_REFINED_WOOD()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block CRUDE_TORCH = registerBlock("crude_torch", new TimedTorchBlock(AbstractBlock.Settings.create().notSolid().sounds(BlockSoundGroup.WOOD).breakInstantly().noCollision().luminance(TimedTorchBlock::getLuminanceFromState).ticksRandomly()), Weight.LIGHT, Size.SMALL, PRIMEVAL_BLOCKS); //todo
    public static final Item LIT_CRUDE_TORCH = Registry.register(Registries.ITEM, PrimevalMain.getId("crude_torch_lit"), new WeightedBlockItem(CRUDE_TORCH, new Item.Settings().maxCount(Size.SMALL.getStackSize()), Weight.LIGHT, Size.SMALL));
    public static final Block CAMPFIRE = registerBlock("campfire", new PrimevalCampfireBlock(SETTINGS_STONE().luminance(PrimevalCampfireBlock::getLuminanceFromState).nonOpaque()), Weight.HEAVY, Size.LARGE, null);
    public static final Block QUERN = registerBlock("quern", new QuernBlock(SETTINGS_STONE().nonOpaque()), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    // Fluid Blocks
    public static final Block MOLTEN_COPPER = registerMoltenFluid("molten_copper", PrimevalFluids.MOLTEN_COPPER);
    public static final Block MOLTEN_TIN = registerMoltenFluid("molten_tin", PrimevalFluids.MOLTEN_TIN);
    public static final Block MOLTEN_ZINC = registerMoltenFluid("molten_zinc", PrimevalFluids.MOLTEN_ZINC);

    public static final Block MOLTEN_BRONZE = registerMoltenFluid("molten_bronze", PrimevalFluids.MOLTEN_BRONZE);
    public static final Block MOLTEN_BRASS = registerMoltenFluid("molten_brass", PrimevalFluids.MOLTEN_BRASS);
    public static final Block MOLTEN_PEWTER = registerMoltenFluid("molten_pewter", PrimevalFluids.MOLTEN_PEWTER);
    public static final Block MOLTEN_GOLD = registerMoltenFluid("molten_gold", PrimevalFluids.MOLTEN_GOLD);
    public static final Block MOLTEN_BOTCHED_ALLOY = registerMoltenFluid("molten_botched_alloy", PrimevalFluids.MOLTEN_BOTCHED_ALLOY);


    // Block entities
    public static final BlockEntityType<PitKilnBlockEntity> PIT_KILN_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("pit_kiln_block_entity"), FabricBlockEntityTypeBuilder.create(PitKilnBlockEntity::new, PIT_KILN).build());
    public static final BlockEntityType<AshPileBlockEntity> ASH_PILE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("ash_pile_block_entity"), FabricBlockEntityTypeBuilder.create(AshPileBlockEntity::new, ASH_PILE).build());
    public static final BlockEntityType<LayingItemBlockEntity> LAYING_ITEM_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("laying_item_block_entity"), FabricBlockEntityTypeBuilder.create(LayingItemBlockEntity::new, LAYING_ITEM).build());
    public static final BlockEntityType<LogPileBlockEntity> LOG_PILE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("log_pile_block_entity"), FabricBlockEntityTypeBuilder.create(LogPileBlockEntity::new, OAK_LOG_PILE, BIRCH_LOG_PILE, SPRUCE_LOG_PILE).build());
    public static final BlockEntityType<CrateBlockEntity> CRATE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("crate_block_entity"), FabricBlockEntityTypeBuilder.create(CrateBlockEntity::new, OAK_CRATE, BIRCH_CRATE, SPRUCE_CRATE).build());
    public static final BlockEntityType<StoragePotBlockEntity> LARGE_POT_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("large_pot_block_entity"), FabricBlockEntityTypeBuilder.create(StoragePotBlockEntity::new, LARGE_FIRED_CLAY_POT).build());
    public static final BlockEntityType<WickerBasketBlockEntity> WICKER_BASKET_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("wicker_basket_block_entity"), FabricBlockEntityTypeBuilder.create(WickerBasketBlockEntity::new, WICKER_BASKET).build());
    public static final BlockEntityType<PrimevalCampfireBlockEntity> CAMPFIRE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("campfire_block_entity"), FabricBlockEntityTypeBuilder.create(PrimevalCampfireBlockEntity::new, CAMPFIRE).build());
    public static final BlockEntityType<QuernBlockEntity> QUERN_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PrimevalMain.getId("quern_block_entity"), FabricBlockEntityTypeBuilder.create(QuernBlockEntity::new, QUERN).build());

    // Screen Handlers
    public static final ScreenHandlerType<PrimevalContainerScreenHandler> CRATE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(PrimevalMain.getId("crate_screen_handler"), PrimevalContainerScreenHandler::new);


    public static void init() {
        OakTrunker.INSTANCE.build();
        BirchTrunker.INSTANCE.build();
        SpruceTrunker.INSTANCE.build();

        registerFlammables();
        registerHoeables();
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        // Render Layers
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                GRASSY_DIRT, GRASSY_CLAY,
                GRASS, BUSH, SPIKED_PLANT, LEAFY_PLANT, SHRUB,
                POPPY, DANDELION, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY,
                MOSS,
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
                CRUDE_TORCH,
                CAMPFIRE,
                ROPE,
                ROPE_LADDER,
                OAK_PLANK_BLOCKS.door,
                OAK_PLANK_BLOCKS.trapdoor,
                WICKER_DOOR,
                WICKER_TRAPDOOR,
                WICKER_BARS
        );

        // Color registry on items
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x91BD59,
                GRASSY_DIRT.asItem(),
                GRASSY_CLAY.asItem(),
                GRASS.asItem(),
                BUSH.asItem(),
                SPIKED_PLANT.asItem(),
                LEAFY_PLANT.asItem());

        // Block Renderers
        BlockEntityRendererRegistry.register(PIT_KILN_BLOCK_ENTITY, PitKilnBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ASH_PILE_BLOCK_ENTITY, AshPileBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(LAYING_ITEM_BLOCK_ENTITY, LayingItemBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(CAMPFIRE_BLOCK_ENTITY, PrimevalCampfireBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(QUERN_BLOCK_ENTITY, QuernBlockEntityRenderer::new);

        ScreenRegistry.register(CRATE_SCREEN_HANDLER, Primeval3x5ContainerScreen::new);
    }


    private static void registerFlammables() {
        FlammableBlockRegistry.getDefaultInstance().add(OAK_LOG, 5, 10);
        FlammableBlockRegistry.getDefaultInstance().add(OAK_TRUNK, 1, 3);
        FlammableBlockRegistry.getDefaultInstance().add(OAK_LEAVES, 50, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_LOG, 5, 10);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_TRUNK, 1, 3);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_LEAVES, 50, 60);
        FlammableBlockRegistry.getDefaultInstance().add(SPRUCE_LOG, 5, 10);
        FlammableBlockRegistry.getDefaultInstance().add(SPRUCE_TRUNK, 1, 3);
        FlammableBlockRegistry.getDefaultInstance().add(SPRUCE_LEAVES, 50, 60);
        FlammableBlockRegistry.getDefaultInstance().add(OAK_LOG_PILE, 5, 10);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_LOG_PILE, 5, 10);
        FlammableBlockRegistry.getDefaultInstance().add(SPRUCE_LOG_PILE, 5, 10);

        FlammableBlockRegistry.getDefaultInstance().add(OAK_SAPLING, 50, 80);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_SAPLING, 50, 80);
        FlammableBlockRegistry.getDefaultInstance().add(SPRUCE_SAPLING, 50, 80);

        FlammableBlockRegistry.getDefaultInstance().add(GRASS, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(BUSH, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(SPIKED_PLANT, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(LEAFY_PLANT, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(POPPY, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(DANDELION, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(OXEYE_DAISY, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(CORNFLOWER, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(LILY_OF_THE_VALLEY, 60, 100);

        FlammableBlockRegistry.getDefaultInstance().add(STRAW_BLOCK, 10, 40);
        FlammableBlockRegistry.getDefaultInstance().add(STRAW_STAIRS, 10, 40);
        FlammableBlockRegistry.getDefaultInstance().add(STRAW_SLAB, 10, 40);

        FlammableBlockRegistry.getDefaultInstance().add(STRAW_MESH, 10, 40);
        FlammableBlockRegistry.getDefaultInstance().add(STRAW_MAT, 10, 40);
        FlammableBlockRegistry.getDefaultInstance().add(STRAW_PILE, 10, 40);

        FlammableBlockRegistry.getDefaultInstance().add(DAUB, 2, 6);
        FlammableBlockRegistry.getDefaultInstance().add(FRAMED_DAUB, 2, 6);
        FlammableBlockRegistry.getDefaultInstance().add(FRAMED_PILLAR_DAUB, 2, 6);
        FlammableBlockRegistry.getDefaultInstance().add(FRAMED_CROSS_DAUB, 2, 6);
        FlammableBlockRegistry.getDefaultInstance().add(FRAMED_INVERTED_CROSS_DAUB, 2, 6);
        FlammableBlockRegistry.getDefaultInstance().add(FRAMED_X_DAUB, 2, 6);
        FlammableBlockRegistry.getDefaultInstance().add(FRAMED_PLUS_DAUB, 2, 6);
        FlammableBlockRegistry.getDefaultInstance().add(FRAMED_DIVIDED_DAUB, 2, 6);

        for (Block b : OAK_PLANK_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(b, 5, 20);
        }
        for (Block b : BIRCH_PLANK_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(b, 5, 20);
        }
        for (Block b : SPRUCE_PLANK_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(b, 5, 20);
        }

        for (Block b : WICKER) {
            FlammableBlockRegistry.getDefaultInstance().add(b, 10, 40);
        }
        FlammableBlockRegistry.getDefaultInstance().add(WICKER_DOOR, 10, 40);
        FlammableBlockRegistry.getDefaultInstance().add(WICKER_TRAPDOOR, 10, 40);

        FlammableBlockRegistry.getDefaultInstance().add(CRUDE_CRAFTING_BENCH, 2, 6);
    }

    private static void registerHoeables() {
        hoeables.put(COARSE_DIRT, DIRT);
    }

    private static Block registerBlockWithoutItem(String id, Block block) {
        return Registry.register(Registries.BLOCK, PrimevalMain.getId(id), block);
    }


    private static <V extends Block> V registerBlock(String id, V block, Weight weight, Size size, ItemGroup itemgroup) {
        if (itemgroup == null) {
            Registry.register(Registries.ITEM, PrimevalMain.getId(id), new WeightedBlockItem(block, new Item.Settings().maxCount(size.getStackSize()), weight, size));
        } else {
            Registry.register(Registries.ITEM, PrimevalMain.getId(id), new WeightedBlockItem(block, new Item.Settings().group(itemgroup).maxCount(size.getStackSize()), weight, size));
        }
        return Registry.register(Registries.BLOCK, PrimevalMain.getId(id), block);
    }

    private static Block registerMoltenFluid(String id, FlowableFluid fluid) {
        return registerBlockWithoutItem(id, new UnprotectedFluidBlock(fluid, AbstractBlock.Settings.create().noCollision().ticksRandomly().strength(100.0F).luminance((state) -> 15).dropsNothing()));
    }

    private static ColoredBlockSet registerColoredBlockSet(String baseId, AbstractBlock.Settings settings, Weight weight, Size size, ItemGroup itemgroup) {
        return new ColoredBlockSet(
                registerBlock(baseId + "_white", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_orange", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_magenta", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_light_blue", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_yellow", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_lime", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_pink", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_dark_gray", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_light_gray", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_cyan", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_purple", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_blue", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_brown", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_green", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_red", new Block(settings), weight, size, itemgroup),
                registerBlock(baseId + "_black", new Block(settings), weight, size, itemgroup)
        );
    }
    private static ColoredBlockSetSet registerColoredBlockSetSet(String baseId, AbstractBlock.Settings settings, Weight weight, Size size, ItemGroup itemgroup) {
        return new ColoredBlockSetSet(
                registerBlockSet(baseId + "_white", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_orange", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_magenta", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_light_blue", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_yellow", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_lime", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_pink", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_dark_gray", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_light_gray", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_cyan", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_purple", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_blue", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_brown", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_green", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_red", settings, weight, size, itemgroup),
                registerBlockSet(baseId + "_black", settings, weight, size, itemgroup)
        );
    }

    private static BlockSet registerBlockSet(String id, AbstractBlock.Settings settings, Weight weight, Size size, ItemGroup itemgroup) {
        return registerBlockSet(id, id+"_stairs", id+"_slab", settings, weight, size, itemgroup);
    }
    private static BlockSet registerBlockSet(String base_id, String stairs_id, String slab_id, AbstractBlock.Settings settings, Weight weight, Size size, ItemGroup itemgroup) {
        Block base = registerBlock(base_id, new Block(settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
        return new BlockSet(
                base,
                registerBlock(stairs_id, new PrimevalStairsBlock(base.getDefaultState(), settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS),
                registerBlock(slab_id, new SlabBlock(settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS)
        );
    }

    private static WoodBlockSet registerWoodBlockSet(String id, AbstractBlock.Settings settings, Weight weight, Size size, ItemGroup itemgroup) {
        return registerWoodBlockSet(id+"_planks", id+"_stairs", id+"_slab", id+"_panel", id+"_fence", id+"_log_fence", id+"_fence_gate", id+"_door", id+"_trapdoor", settings, weight, size, itemgroup);
    }
    private static WoodBlockSet registerWoodBlockSet(String block_id, String stairs_id, String slab_id, String panel_id, String fence_id, String log_fence_id, String fence_gate_id, String door_id, String trapdoor_id, AbstractBlock.Settings settings, Weight weight, Size size, ItemGroup itemgroup) {
        Block base = registerBlock(block_id, new Block(settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);

        return new WoodBlockSet(
                base,
                registerBlock(stairs_id, new PrimevalStairsBlock(base.getDefaultState(), settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS),
                registerBlock(slab_id, new SlabBlock(settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS),
                registerBlock(panel_id, new Block(settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS),
                registerBlock(fence_id, new FenceBlock(settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS),
                registerBlock(log_fence_id, new FenceBlock(settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS),
                registerBlock(fence_gate_id, new FenceGateBlock(settings, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS),
                registerBlock(door_id, new PrimevalDoorBlock(settings.nonOpaque()), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS),
                registerBlock(trapdoor_id, new PrimevalTrapdoorBlock(settings.nonOpaque()), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS)
        );
    }

    private static OreBlockSet registerOreBlockSet(String ore_id, AbstractBlock.Settings settings, Weight weight, Size size, ItemGroup itemgroup) {
        return new OreBlockSet(
                registerBlock(ore_id + "_small", new SemiSupportedBlock(settings, 0.35f, COBBLESTONE), weight, size, itemgroup),
                registerBlock(ore_id + "_medium", new SemiSupportedBlock(settings, 0.35f, COBBLESTONE), weight, size, itemgroup),
                registerBlock(ore_id + "_large", new SemiSupportedBlock(settings, 0.35f, COBBLESTONE), weight, size, itemgroup)
        );
    }

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
    public record OreBlockSet(Block small, Block medium, Block large) {
        public @NotNull Iterator<Block> iterator() {
            return Arrays.stream(new Block[]{small, medium, large}).iterator();
        }
    }
}
