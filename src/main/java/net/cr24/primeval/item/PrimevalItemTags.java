package net.cr24.primeval.item;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class PrimevalItemTags {

    public static final TagKey<Item> KNIVES = register("knives");
    public static final TagKey<Item> LOGS = register("logs");
    public static final TagKey<Item> PLANKS = register("planks");

    private static TagKey<Item> register(String id) {
        return TagKey.of(Registry.ITEM_KEY, PrimevalMain.getId(id));
    }
}
