package net.cr24.primeval.item;

import net.cr24.primeval.PrimevalMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.cr24.primeval.PrimevalMain.PRIMEVAL_ITEMS;
import static net.cr24.primeval.block.PrimevalBlocks.*;

@SuppressWarnings("unused")
public class PrimevalItems {

    /* Items */
    // Basic materials
    public static final Item STRAW = registerItem("straw", new WeightedBlockItem(STRAW_PILE, new FabricItemSettings().group(PRIMEVAL_ITEMS), Weight.VERY_LIGHT, Size.SMALL));
    public static final Item STICK = registerItem("stick", new WeightedItem(new FabricItemSettings().group(PRIMEVAL_ITEMS), Weight.VERY_LIGHT, Size.SMALL));
    public static final Item ROCK = registerItem("rock", new WeightedItem(new FabricItemSettings().group(PRIMEVAL_ITEMS), Weight.LIGHT, Size.SMALL));

    // Utility items
    public static final Item FIRED_CLAY_JUG = registerItem("fired_clay_jug", new WeightedItem(new FabricItemSettings().group(PRIMEVAL_ITEMS), Weight.NORMAL, Size.MEDIUM, 1));


    public static void init() {}

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registry.ITEM, PrimevalMain.getId(id), item);
    }

}
