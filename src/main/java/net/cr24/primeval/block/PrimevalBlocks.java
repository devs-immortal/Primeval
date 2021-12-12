package net.cr24.primeval.block;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.block.entity.*;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.cr24.primeval.item.WeightedBlockItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import static net.cr24.primeval.item.PrimevalItems.PRIMEVAL_BLOCKS;

@SuppressWarnings("unused")
public class PrimevalBlocks {

    // Block Settings
    public static final FabricBlockSettings SETTINGS_SOIL = FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(2.1f, 2.0f).sounds(BlockSoundGroup.GRAVEL);
    public static final FabricBlockSettings SETTINGS_GRASSY = FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.TERRACOTTA_GREEN).strength(2.5f, 2.0f).sounds(BlockSoundGroup.GRASS);
    public static final FabricBlockSettings SETTINGS_SAND = FabricBlockSettings.of(Material.AGGREGATE, MapColor.PALE_YELLOW).strength(1.8f, 2.0f).sounds(BlockSoundGroup.SAND);
    public static final FabricBlockSettings SETTINGS_STONE = FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(4.5f, 6.0f).requiresTool();
    public static final FabricBlockSettings SETTINGS_LOG = FabricBlockSettings.of(Material.WOOD, MapColor.BROWN).strength(4.0f, 6.0f).sounds(BlockSoundGroup.WOOD).requiresTool();


    // Terrain blocks
    public static final Block DIRT = registerBlock("dirt", new SemiSupportedBlock(SETTINGS_SOIL, 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COARSE_DIRT = registerBlock("coarse_dirt", new SemiSupportedBlock(SETTINGS_SOIL, 0.3f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block GRASSY_DIRT = registerBlock("grassy_dirt", new GrassyDirtBlock(SETTINGS_GRASSY, 0.35f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block SAND = registerBlock("sand", new SemiSupportedBlock(SETTINGS_SAND, 0.1f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block CLAY_BLOCK = registerBlock("block_of_clay", new SemiSupportedBlock(SETTINGS_SOIL, 0.3f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COBBLESTONE = registerBlock("cobblestone", new SemiSupportedBlock(SETTINGS_STONE.strength(5.0f, 6.0f), 0.1f), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STONE = registerBlock("stone", new CascadingBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);


    // Plant blocks
    public static final Block OAK_LOG = registerBlock("oak_log", new TrunkBlock(SETTINGS_LOG), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block OAK_LEAVES = registerBlock("oak_leaves", new LeafBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block GRASS = registerBlock("grass", new GrowingGrassBlock(FabricBlockSettings.copyOf(Blocks.GRASS).ticksRandomly()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);


    // Ore blocks
    // IRON
    public static final Block IRON_HEMATITE_ORE_SMALL = registerBlock("iron_hematite_ore_small", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block IRON_HEMATITE_ORE_MEDIUM = registerBlock("iron_hematite_ore_medium", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block IRON_HEMATITE_ORE_LARGE = registerBlock("iron_hematite_ore_large", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    public static final Block COPPER_NATIVE_ORE_SMALL = registerBlock("copper_native_ore_small", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_NATIVE_ORE_MEDIUM = registerBlock("copper_native_ore_medium", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_NATIVE_ORE_LARGE = registerBlock("copper_native_ore_large", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    public static final Block COPPER_MALACHITE_ORE_SMALL = registerBlock("copper_malachite_ore_small", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_MALACHITE_ORE_MEDIUM = registerBlock("copper_malachite_ore_medium", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_MALACHITE_ORE_LARGE = registerBlock("copper_malachite_ore_large", new SemiSupportedBlock(SETTINGS_STONE, 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    // Crafted Blocks
    public static final Block STRAW_BLOCK = registerBlock("straw_block", new PillarBlock(FabricBlockSettings.of(Material.PLANT).strength(0.5F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);

    // Technical Blocks or Blocks with other BlockItems than themselves
    public static final Block STRAW_PILE = registerBlockWithoutItem("straw", new LayeredBlock(FabricBlockSettings.of(Material.PLANT).strength(0.5F).sounds(BlockSoundGroup.GRASS)));
    public static final Block ASH_PILE = registerBlockWithoutItem("ash_pile", new AshPileBlock(FabricBlockSettings.of(Material.AGGREGATE).strength(0.5F).sounds(BlockSoundGroup.SAND)));

    // Intractable Blocks
    public static final Block PIT_KILN = registerBlockWithoutItem("pit_kiln", new PitKilnBlock(FabricBlockSettings.of(Material.PLANT).strength(1.0F).sounds(BlockSoundGroup.GRASS)));

    public static void init() {}



    // Block entities
    public static final BlockEntityType<PitKilnBlockEntity> PIT_KILN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, PrimevalMain.getId("pit_kiln_block_entity"), FabricBlockEntityTypeBuilder.create(PitKilnBlockEntity::new, PIT_KILN).build());
    public static final BlockEntityType<AshPileBlockEntity> ASH_PILE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, PrimevalMain.getId("ash_pile_block_entity"), FabricBlockEntityTypeBuilder.create(AshPileBlockEntity::new, ASH_PILE).build());



    @Environment(EnvType.CLIENT)
    public static void initClient() {
        // Render Layers
        BlockRenderLayerMap.INSTANCE.putBlock(GRASSY_DIRT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(OAK_LEAVES, RenderLayer.getCutout());

        // Color registry on items
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x91BD59, GRASSY_DIRT.asItem());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x91BD59, GRASS.asItem());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x91BD59, OAK_LEAVES.asItem());

        // Block Renderers
        BlockEntityRendererRegistry.register(PIT_KILN_BLOCK_ENTITY, PitKilnBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ASH_PILE_BLOCK_ENTITY, AshPileBlockEntityRenderer::new);
    }


    private static Block registerBlockWithoutItem(String id, Block block) {
        return Registry.register(Registry.BLOCK, PrimevalMain.getId(id), block);
    }


    private static Block registerBlock(String id, Block block, Weight weight, Size size, ItemGroup itemgroup) {
        Registry.register(Registry.ITEM, PrimevalMain.getId(id), new WeightedBlockItem(block, new FabricItemSettings().group(itemgroup).maxCount(size.getStackSize()), weight, size));
        return Registry.register(Registry.BLOCK, PrimevalMain.getId(id), block);
    }

}
