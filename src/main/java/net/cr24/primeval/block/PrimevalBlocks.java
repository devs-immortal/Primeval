package net.cr24.primeval.block;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.block.entity.*;
import net.cr24.primeval.block.functional.*;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.cr24.primeval.item.WeightedBlockItem;
import net.cr24.primeval.screen.PrimevalCraftingScreenHandler;
import net.cr24.primeval.world.gen.trunker.BirchTrunker;
import net.cr24.primeval.world.gen.trunker.OakTrunker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import static net.cr24.primeval.item.PrimevalItems.PRIMEVAL_BLOCKS;

@SuppressWarnings("unused")
public class PrimevalBlocks {

    // Block Settings
    private static final FabricBlockSettings SETTINGS_SOIL = FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(2.1f, 2.0f).sounds(BlockSoundGroup.GRAVEL);
    private static final FabricBlockSettings SETTINGS_TOUGH_SOIL = FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(3f, 2.0f).sounds(BlockSoundGroup.GRAVEL);
    private static final FabricBlockSettings SETTINGS_GRASSY = FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.TERRACOTTA_GREEN).strength(2.5f, 2.0f).sounds(BlockSoundGroup.GRASS);
    private static final FabricBlockSettings SETTINGS_SAND = FabricBlockSettings.of(Material.AGGREGATE, MapColor.PALE_YELLOW).strength(1.8f, 2.0f).sounds(BlockSoundGroup.SAND);
    private static final FabricBlockSettings SETTINGS_STONE = FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(4.5f, 6.0f).requiresTool();
    private static final FabricBlockSettings SETTINGS_PLANT = FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.GREEN).strength(0.05f, 0f).sounds(BlockSoundGroup.GRASS).noCollision();
    private static final FabricBlockSettings SETTINGS_LOG = FabricBlockSettings.of(Material.WOOD, MapColor.BROWN).strength(5.0f, 6.0f).sounds(BlockSoundGroup.WOOD).requiresTool();
    private static final FabricBlockSettings SETTINGS_TRUNK = FabricBlockSettings.of(Material.WOOD, MapColor.BROWN).strength(8.0f, 8.0f).sounds(BlockSoundGroup.WOOD).requiresTool();
    private static final FabricBlockSettings SETTINGS_FIRED_CLAY = FabricBlockSettings.of(Material.STONE, MapColor.ORANGE).strength(4.0f, 6.0f).sounds(BlockSoundGroup.STONE).requiresTool();
    private static final FabricBlockSettings SETTINGS_REFINED_WOOD = FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool();

    // Terrain blocks
    public static final Block DIRT = registerBlock("dirt", new SemiSupportedBlock(SETTINGS_SOIL, 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COARSE_DIRT = registerBlock("coarse_dirt", new SemiSupportedBlock(SETTINGS_SOIL, 0.3f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block MUD = registerBlock("mud", new MudBlock(SETTINGS_SOIL.velocityMultiplier(0.4f), 0.25f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block GRASSY_DIRT = registerBlock("grassy_dirt", new GrassyDirtBlock(SETTINGS_GRASSY.ticksRandomly(), 0.35f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block SAND = registerBlock("sand", new SemiSupportedBlock(SETTINGS_SAND, 0.1f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block GRAVEL = registerBlock("gravel", new SemiSupportedBlock(SETTINGS_SAND, 0.1f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block CLAY_BLOCK = registerBlock("block_of_clay", new SemiSupportedBlock(SETTINGS_SOIL, 0.3f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COBBLESTONE = registerBlock("cobblestone", new SemiSupportedBlock(SETTINGS_STONE.strength(5.0f, 6.0f), 0.1f), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STONE = registerBlock("stone", new CascadingBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);


    //// Plant blocks
    // Oak Trees
    public static final Block OAK_LOG = registerBlock("oak_log", new PillarBlock(SETTINGS_LOG), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block OAK_TRUNK = registerBlockWithoutItem("oak_trunk", new TrunkBlock(SETTINGS_TRUNK.nonOpaque(), OakTrunker.INSTANCE));
    public static final Block OAK_LEAVES = registerBlockWithoutItem("oak_leaves", new LeafBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));
    // Birch Trees
    public static final Block BIRCH_LOG = registerBlock("birch_log", new PillarBlock(SETTINGS_LOG), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block BIRCH_TRUNK = registerBlockWithoutItem("birch_trunk", new TrunkBlock(SETTINGS_TRUNK.nonOpaque(), BirchTrunker.INSTANCE));
    public static final Block BIRCH_LEAVES = registerBlockWithoutItem("birch_leaves", new LeafBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));
    // Saplings+
    public static final Block OAK_SAPLING = registerBlock("oak_sapling", new GrowingSaplingBlock(SETTINGS_PLANT.ticksRandomly(), OakTrunker.INSTANCE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block BIRCH_SAPLING = registerBlock("birch_sapling", new GrowingSaplingBlock(SETTINGS_PLANT.ticksRandomly(), BirchTrunker.INSTANCE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block GRASS = registerBlock("grass", new GrowingGrassBlock(SETTINGS_PLANT.ticksRandomly()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Block BUSH = registerBlock("bush", new PrimevalPlantBlock(SETTINGS_PLANT), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);


    // Ore blocks
    public static final Block COPPER_MALACHITE_ORE_SMALL = registerBlock("copper_malachite_ore_small", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_MALACHITE_ORE_MEDIUM = registerBlock("copper_malachite_ore_medium", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_MALACHITE_ORE_LARGE = registerBlock("copper_malachite_ore_large", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    public static final Block COPPER_NATIVE_ORE_SMALL = registerBlock("copper_native_ore_small", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_NATIVE_ORE_MEDIUM = registerBlock("copper_native_ore_medium", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_NATIVE_ORE_LARGE = registerBlock("copper_native_ore_large", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    public static final Block TIN_CASSITERITE_ORE_SMALL = registerBlock("tin_cassiterite_ore_small", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block TIN_CASSITERITE_ORE_MEDIUM = registerBlock("tin_cassiterite_ore_medium", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block TIN_CASSITERITE_ORE_LARGE = registerBlock("tin_cassiterite_ore_large", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    public static final Block ZINC_SPHALERITE_ORE_SMALL = registerBlock("zinc_sphalerite_ore_small", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block ZINC_SPHALERITE_ORE_MEDIUM = registerBlock("zinc_sphalerite_ore_medium", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block ZINC_SPHALERITE_ORE_LARGE = registerBlock("zinc_sphalerite_ore_large", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    public static final Block IRON_HEMATITE_ORE_SMALL = registerBlock("iron_hematite_ore_small", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block IRON_HEMATITE_ORE_MEDIUM = registerBlock("iron_hematite_ore_medium", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block IRON_HEMATITE_ORE_LARGE = registerBlock("iron_hematite_ore_large", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    // Crafted Blocks
    public static final Block STRAW_BLOCK = registerBlock("straw_block", new PillarBlock(FabricBlockSettings.of(Material.PLANT).strength(0.5F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STRAW_STAIRS = registerBlock("straw_stairs", new PrimevalStairsBlock(STRAW_BLOCK.getDefaultState(), FabricBlockSettings.of(Material.PLANT).strength(0.5F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STRAW_SLAB = registerBlock("straw_slab", new SlabBlock(FabricBlockSettings.of(Material.PLANT).strength(0.5F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STRAW_MESH = registerBlock("straw_mesh", new Block(FabricBlockSettings.of(Material.PLANT).strength(0.5F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STRAW_MAT = registerBlock("straw_mat", new CarpetBlock(FabricBlockSettings.of(Material.PLANT).strength(0.3F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block TERRACOTTA = registerBlock("terracotta", new Block(SETTINGS_FIRED_CLAY), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block[] FIRED_CLAY_SHINGLE_BLOCKS = registerBlockSet("fired_clay_shingles", SETTINGS_FIRED_CLAY, Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block[] FIRED_CLAY_BRICK_BLOCKS = registerBlockSet("fired_clay_bricks", SETTINGS_FIRED_CLAY, Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block[] FIRED_CLAY_TILES_BLOCKS = registerBlockSet("fired_clay_tiles", SETTINGS_FIRED_CLAY, Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block[] DRIED_BRICK_BLOCKS = registerBlockSet("dried_bricks", SETTINGS_FIRED_CLAY, Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block[] MUD_BRICKS = registerBlockSet("mud_bricks", SETTINGS_TOUGH_SOIL, Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block DAUB = registerBlock("daub", new Block(SETTINGS_REFINED_WOOD), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block[] OAK_PLANK_BLOCKS = registerBlockSet("oak_planks", SETTINGS_REFINED_WOOD, Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block[] BIRCH_PLANK_BLOCKS = registerBlockSet("birch_planks", SETTINGS_REFINED_WOOD, Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);

    // Technical Blocks or Blocks with other BlockItems than themselves
    public static final Block STRAW_PILE = registerBlockWithoutItem("straw", new StrawLayeredBlock(FabricBlockSettings.of(Material.PLANT).strength(0.5F).sounds(BlockSoundGroup.GRASS)));
    public static final Block ASH_PILE = registerBlockWithoutItem("ash_pile", new AshPileBlock(FabricBlockSettings.of(Material.AGGREGATE).strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block LAYING_ITEM = registerBlockWithoutItem("laying_item", new LayingItemBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().nonOpaque().breakInstantly()));

    // Intractable Blocks
    public static final Block PIT_KILN = registerBlockWithoutItem("pit_kiln", new PitKilnBlock(FabricBlockSettings.of(Material.PLANT).strength(1.0F).sounds(BlockSoundGroup.GRASS).nonOpaque()));
    public static final Block CRUDE_CRAFTING_BENCH = registerBlock("crude_crafting_bench", new PrimevalCraftingTableBlock(SETTINGS_REFINED_WOOD), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block CRUDE_TORCH = registerBlock("crude_torch", new TimedTorchBlock(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN).sounds(BlockSoundGroup.WOOD).breakInstantly().noCollision().luminance(state -> TimedTorchBlock.getLuminanceFromState(state)).ticksRandomly()), Weight.LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);
    public static final Item LIT_CRUDE_TORCH = Registry.register(Registry.ITEM, PrimevalMain.getId("crude_torch_lit"), new WeightedBlockItem(CRUDE_TORCH, new FabricItemSettings().maxCount(Size.SMALL.getStackSize()), Weight.LIGHT, Size.SMALL));

    // Fluid Blocks
    public static final Block MOLTEN_COPPER = registerMoltenFluid("molten_copper", PrimevalFluids.MOLTEN_COPPER);
    public static final Block MOLTEN_TIN = registerMoltenFluid("molten_tin", PrimevalFluids.MOLTEN_TIN);
    public static final Block MOLTEN_ZINC = registerMoltenFluid("molten_zinc", PrimevalFluids.MOLTEN_ZINC);

    public static final Block MOLTEN_BRONZE = registerMoltenFluid("molten_bronze", PrimevalFluids.MOLTEN_BRONZE);
    public static final Block MOLTEN_BRASS = registerMoltenFluid("molten_brass", PrimevalFluids.MOLTEN_BRASS);
    public static final Block MOLTEN_BOTCHED_ALLOY = registerMoltenFluid("molten_botched_alloy", PrimevalFluids.MOLTEN_BOTCHED_ALLOY);


    // Block entities
    public static final BlockEntityType<PitKilnBlockEntity> PIT_KILN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, PrimevalMain.getId("pit_kiln_block_entity"), FabricBlockEntityTypeBuilder.create(PitKilnBlockEntity::new, PIT_KILN).build());
    public static final BlockEntityType<AshPileBlockEntity> ASH_PILE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, PrimevalMain.getId("ash_pile_block_entity"), FabricBlockEntityTypeBuilder.create(AshPileBlockEntity::new, ASH_PILE).build());
    public static final BlockEntityType<LayingItemBlockEntity> LAYING_ITEM_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, PrimevalMain.getId("laying_item_block_entity"), FabricBlockEntityTypeBuilder.create(LayingItemBlockEntity::new, LAYING_ITEM).build());

    public static void init() {
        OakTrunker.INSTANCE.build();
        BirchTrunker.INSTANCE.build();

        registerFlammables();
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        // Render Layers
        BlockRenderLayerMap.INSTANCE.putBlock(GRASSY_DIRT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OAK_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OAK_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BIRCH_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BIRCH_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CRUDE_TORCH, RenderLayer.getCutout());

        // Color registry on items
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x91BD59, GRASSY_DIRT.asItem());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x91BD59, GRASS.asItem());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x91BD59, BUSH.asItem());

        // Block Renderers
        BlockEntityRendererRegistry.register(PIT_KILN_BLOCK_ENTITY, PitKilnBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ASH_PILE_BLOCK_ENTITY, AshPileBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(LAYING_ITEM_BLOCK_ENTITY, LayingItemBlockEntityRenderer::new);
    }


    private static void registerFlammables() {
        FlammableBlockRegistry.getDefaultInstance().add(OAK_LOG, 5, 10);
        FlammableBlockRegistry.getDefaultInstance().add(OAK_TRUNK, 1, 3);
        FlammableBlockRegistry.getDefaultInstance().add(OAK_LEAVES, 50, 60);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_LOG, 5, 10);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_TRUNK, 1, 3);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_LEAVES, 50, 60);

        FlammableBlockRegistry.getDefaultInstance().add(OAK_SAPLING, 50, 80);
        FlammableBlockRegistry.getDefaultInstance().add(BIRCH_SAPLING, 50, 80);

        FlammableBlockRegistry.getDefaultInstance().add(GRASS, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(BUSH, 60, 100);

        FlammableBlockRegistry.getDefaultInstance().add(STRAW_BLOCK, 10, 40);
        FlammableBlockRegistry.getDefaultInstance().add(STRAW_STAIRS, 10, 40);
        FlammableBlockRegistry.getDefaultInstance().add(STRAW_SLAB, 10, 40);

        FlammableBlockRegistry.getDefaultInstance().add(STRAW_MESH, 10, 40);
        FlammableBlockRegistry.getDefaultInstance().add(STRAW_MAT, 10, 40);
        FlammableBlockRegistry.getDefaultInstance().add(STRAW_PILE, 10, 40);

        FlammableBlockRegistry.getDefaultInstance().add(DAUB, 2, 6);

        for (Block b : OAK_PLANK_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(b, 5, 20);
        }
        for (Block b : BIRCH_PLANK_BLOCKS) {
            FlammableBlockRegistry.getDefaultInstance().add(b, 5, 20);
        }

        FlammableBlockRegistry.getDefaultInstance().add(CRUDE_CRAFTING_BENCH, 2, 6);
    }


    private static Block registerBlockWithoutItem(String id, Block block) {
        return Registry.register(Registry.BLOCK, PrimevalMain.getId(id), block);
    }


    private static Block registerBlock(String id, Block block, Weight weight, Size size, ItemGroup itemgroup) {
        Registry.register(Registry.ITEM, PrimevalMain.getId(id), new WeightedBlockItem(block, new FabricItemSettings().group(itemgroup).maxCount(size.getStackSize()), weight, size));
        return Registry.register(Registry.BLOCK, PrimevalMain.getId(id), block);
    }

    private static Block registerMoltenFluid(String id, FlowableFluid fluid) {
        return registerBlockWithoutItem(id, new UnprotectedFluidBlock(fluid, AbstractBlock.Settings.of(Material.LAVA).noCollision().ticksRandomly().strength(100.0F).luminance((state) -> 15).dropsNothing()));
    }

    private static Block[] registerBlockSet(String id, AbstractBlock.Settings settings, Weight weight, Size size, ItemGroup itemgroup) {
        return registerBlockSet(id, id+"_stairs", id+"_slab", settings, weight, size, itemgroup);
    }
    private static Block[] registerBlockSet(String base_id, String stairs_id, String slab_id, AbstractBlock.Settings settings, Weight weight, Size size, ItemGroup itemgroup) {
        Block[] blocks = new Block[3];
        blocks[0] = registerBlock(base_id, new Block(settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
        blocks[1] = registerBlock(stairs_id, new PrimevalStairsBlock(blocks[0].getDefaultState(), settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
        blocks[2] = registerBlock(slab_id, new SlabBlock(settings), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
        return blocks;
    }

}
