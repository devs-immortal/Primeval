package net.cr24.primeval;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.world.PrimevalWorld;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class PrimevalMain implements ModInitializer, ClientModInitializer {

    public static String mod_id = "primeval";

    /* Item Groups */
    public static final ItemGroup PRIMEVAL_ITEMS = FabricItemGroupBuilder.build(new Identifier(mod_id, "items"), () -> new ItemStack(PrimevalItems.STRAW));
    public static final ItemGroup PRIMEVAL_BLOCKS = FabricItemGroupBuilder.build(new Identifier(mod_id, "blocks"), () -> new ItemStack(PrimevalBlocks.DIRT));


    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        System.out.println("Hello Fabric world!");

        PrimevalItems.init();
        PrimevalBlocks.init();
        PrimevalWorld.init();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        System.out.println("Hello Fabric client!");
        PrimevalBlocks.initClient();
    }

    public static Identifier getId(String id) {
        return new Identifier(PrimevalMain.mod_id, id);
    }

}
