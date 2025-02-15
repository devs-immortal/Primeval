package net.cr24.primeval.initialization;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.world.trunker.BirchTrunker;
import net.cr24.primeval.world.trunker.OakTrunker;
import net.cr24.primeval.world.trunker.SpruceTrunker;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import static net.cr24.primeval.initialization.PrimevalBlocks.*;

public class PrimevalItemGroups {

    public static final RegistryKey<ItemGroup> BLOCKS = create("blocks", FabricItemGroup.builder()
            .icon(() -> new ItemStack(DIRT))
            .entries((context, entries) -> {
                // Terrain Blocks
                entries.add(DIRT);
                entries.add(COARSE_DIRT);
                entries.add(CLAY);
                entries.add(MUD);
                entries.add(DRY_DIRT);
                entries.add(GRASSY_DIRT);
                entries.add(GRASSY_CLAY);
                entries.add(SAND);
                entries.add(GRAVEL);
                entries.add(COBBLESTONE);
                entries.add(STONE);
                entries.add(SANDSTONE);
                // Plants
                entries.add(OAK_SAPLING);
                entries.add(BIRCH_SAPLING);
                entries.add(SPRUCE_SAPLING);
                entries.add(GRASS);
                entries.add(BUSH);
                entries.add(SPIKED_PLANT);
                entries.add(LEAFY_PLANT);
                entries.add(SHRUB);
                entries.add(MOSS);
                // Flowers
                entries.add(POPPY);
                entries.add(DANDELION);
                entries.add(OXEYE_DAISY);
                entries.add(CORNFLOWER);
                entries.add(LILY_OF_THE_VALLEY);
                // Water Plants
                entries.add(REEDS);
                entries.add(RIVER_GRASS);
                // Ores
                entries.add(COPPER_MALACHITE_ORE.small());
                entries.add(COPPER_MALACHITE_ORE.medium());
                entries.add(COPPER_MALACHITE_ORE.large());
                entries.add(COPPER_NATIVE_ORE.small());
                entries.add(COPPER_NATIVE_ORE.medium());
                entries.add(COPPER_NATIVE_ORE.large());
                entries.add(TIN_CASSITERITE_ORE.small());
                entries.add(TIN_CASSITERITE_ORE.medium());
                entries.add(TIN_CASSITERITE_ORE.large());
                entries.add(ZINC_SPHALERITE_ORE.small());
                entries.add(ZINC_SPHALERITE_ORE.medium());
                entries.add(ZINC_SPHALERITE_ORE.large());
                entries.add(GOLD_NATIVE_ORE.small());
                entries.add(GOLD_NATIVE_ORE.medium());
                entries.add(GOLD_NATIVE_ORE.large());
                entries.add(IRON_HEMATITE_ORE.small());
                entries.add(IRON_HEMATITE_ORE.medium());
                entries.add(IRON_HEMATITE_ORE.large());
                entries.add(LAZURITE_ORE.small());
                entries.add(LAZURITE_ORE.medium());
                entries.add(LAZURITE_ORE.large());
                entries.add(FOSSIL);

            }));

    public static void init() {
    }

    // item group registry helper
    private static RegistryKey<ItemGroup> create(String id, ItemGroup.Builder itemGroup) {
        var key = RegistryKey.of(RegistryKeys.ITEM_GROUP, PrimevalMain.identify(id));
        Registry.register(Registries.ITEM_GROUP, key, itemGroup.displayName(Text.translatable("itemGroup.primeval." + id)).build());
        return key;
    }

}
