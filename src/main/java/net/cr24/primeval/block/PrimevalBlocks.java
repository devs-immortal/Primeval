package net.cr24.primeval.block;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.cr24.primeval.item.WeightedBlockItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import static net.cr24.primeval.item.PrimevalItems.PRIMEVAL_BLOCKS;

@SuppressWarnings("unused")
public class PrimevalBlocks {

    // Terrain blocks
    public static final Block DIRT = registerBlock("dirt", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.DIRT), 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COARSE_DIRT = registerBlock("coarse_dirt", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.DIRT), 0.3f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block GRASSY_DIRT = registerBlock("grassy_dirt", new GrassyDirtBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK), 0.35f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block SAND = registerBlock("sand", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.SAND), 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COBBLESTONE = registerBlock("cobblestone", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE), 0.1f), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STONE = registerBlock("stone", new CascadingBlock(FabricBlockSettings.copyOf(Blocks.STONE), 0.35f, COBBLESTONE), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);


    // Plant blocks
    public static final Block OAK_LOG = registerBlock("oak_log", new TrunkBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block OAK_LEAVES = registerBlock("oak_leaves", new LeafBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block GRASS = registerBlock("grass", new GrowingGrassBlock(FabricBlockSettings.copyOf(Blocks.GRASS).ticksRandomly()), Weight.VERY_LIGHT, Size.SMALL, PRIMEVAL_BLOCKS);


    // Ore blocks
    // IRON
    public static final Block IRON_HEMATITE_ORE_SMALL = registerBlock("iron_hematite_ore_small", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.STONE), 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block IRON_HEMATITE_ORE_MEDIUM = registerBlock("iron_hematite_ore_medium", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.STONE), 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block IRON_HEMATITE_ORE_LARGE = registerBlock("iron_hematite_ore_large", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.STONE), 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);

    public static final Block COPPER_NATIVE_ORE_SMALL = registerBlock("copper_native_ore_small", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.STONE), 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_NATIVE_ORE_MEDIUM = registerBlock("copper_native_ore_medium", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.STONE), 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);
    public static final Block COPPER_NATIVE_ORE_LARGE = registerBlock("copper_native_ore_large", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.STONE), 0.35f, COBBLESTONE), Weight.HEAVY, Size.LARGE, PRIMEVAL_BLOCKS);


    // Crafted Blocks
    public static final Block STRAW_BLOCK = registerBlock("straw_block", new PillarBlock(FabricBlockSettings.of(Material.PLANT).strength(0.2F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);

    // Technical Blocks or Blocks with other BlockItems than themselves
    public static final Block STRAW_PILE = registerBlockWithoutItem("straw", new LayeredBlock(FabricBlockSettings.of(Material.PLANT).strength(0.2F).sounds(BlockSoundGroup.GRASS)));

    // Intractable Blocks
    public static final Block PIT_KILN = registerBlockWithoutItem("pit_kiln", new PitKilnBlock(FabricBlockSettings.of(Material.PLANT).strength(0.4F).sounds(BlockSoundGroup.GRASS)));

    public static void init() {}

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
    }


    private static Block registerBlockWithoutItem(String id, Block block) {
        return Registry.register(Registry.BLOCK, PrimevalMain.getId(id), block);
    }


    private static Block registerBlock(String id, Block block, Weight weight, Size size, ItemGroup itemgroup) {
        Registry.register(Registry.ITEM, PrimevalMain.getId(id), new WeightedBlockItem(block, new FabricItemSettings().group(itemgroup).maxCount(size.getStackSize()), weight, size));
        return Registry.register(Registry.BLOCK, PrimevalMain.getId(id), block);
    }

}
