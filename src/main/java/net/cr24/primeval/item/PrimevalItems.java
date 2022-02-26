package net.cr24.primeval.item;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.block.PrimevalBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import static net.cr24.primeval.block.PrimevalBlocks.*;

@SuppressWarnings("unused")
public class PrimevalItems {

    /* Item Groups */
    public static final ItemGroup PRIMEVAL_ITEMS = FabricItemGroupBuilder.build(PrimevalMain.getId("items"), () -> new ItemStack(PrimevalItems.STRAW));
    public static final ItemGroup PRIMEVAL_BLOCKS = FabricItemGroupBuilder.build(PrimevalMain.getId("blocks"), () -> new ItemStack(PrimevalBlocks.DIRT));
    public static final ItemGroup PRIMEVAL_TOOLS = FabricItemGroupBuilder.build(PrimevalMain.getId("tools"), () -> new ItemStack(PrimevalItems.FLINT_AXE));

    private static final Item.Settings GROUP_ITEMS = new Item.Settings().group(PRIMEVAL_ITEMS);
    private static final Item.Settings GROUP_TOOLS = new Item.Settings().group(PRIMEVAL_TOOLS);


    /* Items */
    // Crafting materials
    public static final Item STRAW = registerItem("straw", new WeightedBlockItem(STRAW_PILE, GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item STICK = registerItem("stick", new WeightedItem(GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item ROCK = registerItem("rock", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    public static final Item FLINT = registerItem("flint", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    public static final Item ASHES = registerItem("ashes", new WeightedItem(GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));

    public static final Item SANDY_CLAY_BALL = registerItem("sandy_clay_ball", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item SANDY_CLAY_BRICK = registerItem("sandy_clay_brick", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item DRIED_BRICK = registerItem("dried_brick", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));

    public static final Item CLAY_BALL = registerItem("clay_ball", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item CLAY_BRICK = registerItem("clay_brick", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item FIRED_CLAY_BRICK = registerItem("fired_clay_brick", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item CLAY_BOWL = registerItem("clay_bowl", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item FIRED_CLAY_BOWL = registerItem("fired_clay_bowl", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item CLAY_TILE = registerItem("clay_tile", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item FIRED_CLAY_TILE = registerItem("fired_clay_tile", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));

    // Ore Items
    public static final Item RAW_IRON_HEMATITE_SMALL = registerItem("raw_iron_hematite_small", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item RAW_IRON_HEMATITE_MEDIUM = registerItem("raw_iron_hematite_medium", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item RAW_IRON_HEMATITE_LARGE = registerItem("raw_iron_hematite_large", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.LARGE));

    public static final Item RAW_COPPER_NATIVE_SMALL = registerItem("raw_copper_native_small", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item RAW_COPPER_NATIVE_MEDIUM = registerItem("raw_copper_native_medium", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item RAW_COPPER_NATIVE_LARGE = registerItem("raw_copper_native_large", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.LARGE));

    public static final Item RAW_COPPER_MALACHITE_SMALL = registerItem("raw_copper_malachite_small", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item RAW_COPPER_MALACHITE_MEDIUM = registerItem("raw_copper_malachite_medium", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item RAW_COPPER_MALACHITE_LARGE = registerItem("raw_copper_malachite_large", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.LARGE));

    // Tools
    public static final Item FLINT_AXE = registerItem("flint_axe", new PrimevalAxeItem(PrimevalToolMaterials.FLINT, PrimevalToolMaterials.FLINT.getAttackDamage(), -3.0f, GROUP_TOOLS, Weight.HEAVY, Size.LARGE));
    public static final Item FLINT_SHOVEL = registerItem("flint_shovel", new PrimevalShovelItem(PrimevalToolMaterials.FLINT, PrimevalToolMaterials.FLINT.getAttackDamage(), -3.0f, GROUP_TOOLS, Weight.HEAVY, Size.LARGE));

    // Utility items
    public static final Item FIRED_CLAY_JUG = registerItem("fired_clay_jug", new JugItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM, 1));
    public static final Item FIRED_CLAY_VESSEL = registerItem("fired_clay_vessel", new VesselItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));

    public static void init() {}

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registry.ITEM, PrimevalMain.getId(id), item);
    }
}
