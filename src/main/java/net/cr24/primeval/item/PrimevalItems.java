package net.cr24.primeval.item;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.block.PrimevalBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import static net.cr24.primeval.block.PrimevalBlocks.*;

@SuppressWarnings("unused")
public class PrimevalItems {

    /* Item Groups */
    public static final ItemGroup PRIMEVAL_ITEMS = FabricItemGroupBuilder.build(PrimevalMain.getId("items"), () -> new ItemStack(PrimevalItems.STRAW));
    public static final ItemGroup PRIMEVAL_BLOCKS = FabricItemGroupBuilder.build(PrimevalMain.getId("blocks"), () -> new ItemStack(PrimevalBlocks.DIRT));


    private static final FabricItemSettings groupItems = new FabricItemSettings().group(PRIMEVAL_ITEMS);
    /* Items */
    // Basic materials
    public static final Item STRAW = registerItem("straw", new WeightedBlockItem(STRAW_PILE, groupItems, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item STICK = registerItem("stick", new WeightedItem(groupItems, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item ROCK = registerItem("rock", new WeightedItem(groupItems, Weight.LIGHT, Size.SMALL));

    // Ore Items
    public static final Item RAW_IRON_SMALL = registerItem("raw_iron_small", new WeightedItem(groupItems, Weight.NORMAL, Size.SMALL));
    public static final Item RAW_IRON_MEDIUM = registerItem("raw_iron_medium", new WeightedItem(groupItems, Weight.NORMAL, Size.MEDIUM));
    public static final Item RAW_IRON_LARGE = registerItem("raw_iron_large", new WeightedItem(groupItems, Weight.NORMAL, Size.LARGE));

    // Utility items
    public static final Item FIRED_CLAY_JUG = registerItem("fired_clay_jug", new WeightedItem(groupItems, Weight.NORMAL, Size.MEDIUM, 1));


    public static void init() {}

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registry.ITEM, PrimevalMain.getId(id), item);
    }

}
