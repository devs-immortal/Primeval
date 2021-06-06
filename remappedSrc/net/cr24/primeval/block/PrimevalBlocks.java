package net.cr24.primeval.block;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.cr24.primeval.item.WeightedBlockItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.FabricItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PrimevalBlocks {

    public static final Block TESTDIRT = new CollapsibleBlock(FabricBlockSettings.copyOf(Blocks.DIRT).breakByTool(FabricItemTags.SHOVELS));
    public static final Block TESTCOBBLE = new CollapsibleBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE).breakByTool(FabricItemTags.PICKAXES));


    public static void registerBlocks() {
        registerBlock("dirt", TESTDIRT, Weight.NORMAL, Size.MEDIUM, ItemGroup.BUILDING_BLOCKS);
        registerBlock("cobble", TESTCOBBLE, Weight.NORMAL, Size.MEDIUM, ItemGroup.BUILDING_BLOCKS);

    }

    private static Block registerBlockWithoutItem(String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(PrimevalMain.mod_id, id), block);
    }

    private static Block registerBlock(String id, Block block, Weight weight, Size size, ItemGroup itemgroup) {
        Registry.register(Registry.ITEM, new Identifier(PrimevalMain.mod_id, id), new WeightedBlockItem(block, new FabricItemSettings().group(itemgroup).maxCount(size.getStackSize()), weight, size));
        return Registry.register(Registry.BLOCK, new Identifier(PrimevalMain.mod_id, id), block);
    }

}
