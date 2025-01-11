package net.cr24.primeval.item;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class PrimevalItemTags {

    public static final TagKey<Item> BURNABLE_SHORT = register("burnable_short");
    public static final TagKey<Item> BURNABLE_LONG = register("burnable_long");

    public static final TagKey<Item> BURNS_TO_ASH = register("burns_to_ash");

    public static final TagKey<Item> KNIVES = register("knives");
    public static final TagKey<Item> LOGS = register("logs");
    public static final TagKey<Item> ROCKS = register("rocks");
    public static final TagKey<Item> CAMPFIRE_KINDLING = register("campfire_kindling");


    public static final TagKey<Item> PLANKS = register("planks");
    public static final TagKey<Item> MORTAR = register("mortar");
    public static final TagKey<Item> SAPLINGS = register("saplings");
    public static final TagKey<Item> CRATES = register("crates");

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, PrimevalMain.getId(id));
    }
}
