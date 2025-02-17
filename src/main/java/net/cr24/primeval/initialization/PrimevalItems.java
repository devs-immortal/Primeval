package net.cr24.primeval.initialization;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.block.PrimevalFarmlandBlock;
import net.cr24.primeval.item.*;
import net.cr24.primeval.item.tool.*;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Consumer;

import static net.cr24.primeval.Primeval.identify;

public class PrimevalItems {

    private static Item.Settings SETTINGS_BASIC() { return new Item.Settings(); }

    // region CRAFTING MATERIALS
    // basic
    public static final Item STRAW = registerItem("straw", SETTINGS_BASIC(), (w, s, settings) -> new WeightedBlockItem(PrimevalBlocks.STRAW_PILE, w, s, settings), Weight.VERY_LIGHT, Size.SMALL);
    public static final Item STICK = registerItem("stick", SETTINGS_BASIC(), FirestarterItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item STRING = registerItem("string", SETTINGS_BASIC(), FirestarterItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item FLINT = registerItem("flint", SETTINGS_BASIC(), FlintItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item ROCK = registerItem("rock", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item STONE_BRICK = registerItem("stone_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item ASHES = registerItem("ashes", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item CRUSHED_TERRACOTTA = registerItem("crushed_terracotta", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CEMENT_MIX = registerItem("cement_mix", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CEMENT = registerItem("cement", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item BONE = registerItem("bone", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item BONEMEAL = registerItem("bonemeal", SETTINGS_BASIC(), (w, s, settings) -> new FertilizerItem(4, PrimevalFarmlandBlock.PrimevalFarmlandBlockFertilizerType.BONEMEAL, w, s, settings), Weight.VERY_LIGHT, Size.SMALL);
    public static final Item ANIMAL_FAT = registerItem("animal_fat", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item GUNPOWDER = registerItem("gunpowder", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item CHARRED_BONE = registerItem("charred_bone", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);

    public static final Item SANDY_CLAY_BALL = registerItem("sandy_clay_ball", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item SANDY_CLAY_BRICK = registerItem("sandy_clay_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item DRIED_BRICK = registerItem("dried_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item MUD_BALL = registerItem("mud_ball", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item MUD_BRICK = registerItem("mud_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    // clay items
    public static final Item CLAY_BALL = registerItem("clay_ball", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CLAY_BRICK = registerItem("clay_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item FIRED_CLAY_BRICK = registerItem("fired_clay_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CLAY_BOWL = registerItem("clay_bowl", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item FIRED_CLAY_BOWL = registerItem("fired_clay_bowl", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CLAY_TILE = registerItem("clay_tile", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item FIRED_CLAY_TILE = registerItem("fired_clay_tile", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CLAY_JUG = registerItem("clay_jug", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item CLAY_VESSEL = registerItem("clay_vessel", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);

    // logs
    public static final Item OAK_LOG = registerItem("oak_log", SETTINGS_BASIC(), (w, s, settings) -> new LogItem(PrimevalBlocks.OAK_LOG_BLOCK, PrimevalBlocks.OAK_LOG_PILE, w, s, settings), Weight.HEAVY, Size.LARGE);
    public static final Item BIRCH_LOG = registerItem("birch_log", SETTINGS_BASIC(), (w, s, settings) -> new LogItem(PrimevalBlocks.BIRCH_LOG_BLOCK, PrimevalBlocks.BIRCH_LOG_PILE, w, s, settings), Weight.HEAVY, Size.LARGE);
    public static final Item SPRUCE_LOG = registerItem("spruce_log", SETTINGS_BASIC(), (w, s, settings) -> new LogItem(PrimevalBlocks.SPRUCE_LOG_BLOCK, PrimevalBlocks.SPRUCE_LOG_PILE, w, s, settings), Weight.HEAVY, Size.LARGE);



    // endregion


    public static void init() {
    }

    @SafeVarargs
    private static <T extends Item> T registerItem(String id, Item.Settings settings, ItemFactory<T> factory, Weight w, Size s, Consumer<Block>... additionalActions) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Primeval.identify(id));
        return Registry.register(Registries.ITEM, identify(id), factory.create(w, s, settings.registryKey(itemKey)));
    }

    @FunctionalInterface
    public interface ItemFactory<T extends Item> {
        T create(Weight weight, Size size, Item.Settings settings);
    }


}
