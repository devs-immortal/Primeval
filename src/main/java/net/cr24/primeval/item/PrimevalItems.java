package net.cr24.primeval.item;

import net.cr24.primeval.PrimevalMain;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class PrimevalItems {

    static {
        final Item TESTITEM = registerItem("testitem", new WeightedItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(Size.MEDIUM.getStackSize()), Weight.NORMAL, Size.MEDIUM));

    }


    public static void init() {}


    private static Item registerItem(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(PrimevalMain.mod_id, id), item);
    }
    
}
