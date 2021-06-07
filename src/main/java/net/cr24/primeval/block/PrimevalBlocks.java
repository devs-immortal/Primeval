package net.cr24.primeval.block;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.cr24.primeval.item.WeightedBlockItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.RecipeType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.cr24.primeval.PrimevalMain.PRIMEVAL_BLOCKS;

@SuppressWarnings("unused")
public class PrimevalBlocks {

    // Terrain blocks
    public static final Block DIRT = registerBlock("dirt", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.DIRT).breakByTool(FabricToolTags.SHOVELS), 0.15f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COBBLESTONE = registerBlock("cobblestone", new SemiSupportedBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).breakByTool(FabricToolTags.PICKAXES), 0.1f), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block STONE = registerBlock("stone", new CascadingBlock(FabricBlockSettings.copyOf(Blocks.STONE).breakByTool(FabricToolTags.PICKAXES), 0.35f, COBBLESTONE), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);

    // Crafted Blocks
    public static final Block STRAW_BLOCK = registerBlock("straw_block", new PillarBlock(FabricBlockSettings.of(Material.PLANT).strength(0.2F).sounds(BlockSoundGroup.GRASS)), Weight.LIGHT, Size.MEDIUM, PRIMEVAL_BLOCKS);

    // Technical Blocks or Blocks with other BlockItems than themselves
    public static final Block STRAW_PILE = registerBlockWithoutItem("straw", new LayeredBlock(FabricBlockSettings.of(Material.PLANT).strength(0.2F).sounds(BlockSoundGroup.GRASS)));


    public static void init() {}


    private static Block registerBlockWithoutItem(String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(PrimevalMain.mod_id, id), block);
    }


    private static Block registerBlock(String id, Block block, Weight weight, Size size, ItemGroup itemgroup) {
        Registry.register(Registry.ITEM, new Identifier(PrimevalMain.mod_id, id), new WeightedBlockItem(block, new FabricItemSettings().group(itemgroup).maxCount(size.getStackSize()), weight, size));
        return Registry.register(Registry.BLOCK, new Identifier(PrimevalMain.mod_id, id), block);
    }

}
