package net.cr24.primeval.item;

import net.cr24.primeval.PrimevalMain;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class PrimevalItemTags {

    public static final Tag<Item> LOGS = TagFactory.ITEM.create(PrimevalMain.getId("logs"));
}
