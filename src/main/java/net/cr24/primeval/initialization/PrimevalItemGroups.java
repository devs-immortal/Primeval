package net.cr24.primeval.initialization;

import net.cr24.primeval.Primeval;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
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
                addBlockSet(entries, COPPER_MALACHITE_ORE);
                addBlockSet(entries, COPPER_NATIVE_ORE);
                addBlockSet(entries, TIN_CASSITERITE_ORE);
                addBlockSet(entries, ZINC_SPHALERITE_ORE);
                addBlockSet(entries, GOLD_NATIVE_ORE);
                addBlockSet(entries, IRON_HEMATITE_ORE);
                addBlockSet(entries, LAZURITE_ORE);
                entries.add(FOSSIL);
                // Crafted Blocks
                entries.add(STRAW_BLOCK);
                entries.add(STRAW_STAIRS);
                entries.add(STRAW_SLAB);
                entries.add(STRAW_MESH);
                entries.add(STRAW_MAT);
                entries.add(TERRACOTTA);
                addBlockSet(entries, COLORED_TERRACOTTA);
                addBlockSet(entries, FIRED_CLAY_SHINGLE_BLOCKS);
                addBlockSetSet(entries, COLORED_FIRED_CLAY_SHINGLE_BLOCKS);
                addBlockSet(entries, FIRED_CLAY_BRICK_BLOCKS);
                addBlockSet(entries, FIRED_CLAY_TILES_BLOCKS);
                addBlockSet(entries, DRIED_BRICK_BLOCKS);
                addBlockSet(entries, MUD_BRICKS);
                addBlockSet(entries, CRUDE_BRICKS);
                addBlockSet(entries, STONE_BRICKS);
                addBlockSet(entries, SMOOTH_STONE);
                entries.add(STONE_INDENT);
                entries.add(STONE_PILLAR);
                addBlockSet(entries, STONE_PAVER);
                entries.add(DAUB);
                entries.add(FRAMED_DAUB);
                entries.add(FRAMED_PILLAR_DAUB);
                entries.add(FRAMED_CROSS_DAUB);
                entries.add(FRAMED_INVERTED_CROSS_DAUB);
                entries.add(FRAMED_X_DAUB);
                entries.add(FRAMED_PLUS_DAUB);
                entries.add(FRAMED_DIVIDED_DAUB);
                addBlockSet(entries, OAK_PLANK_BLOCKS);
                addBlockSet(entries, BIRCH_PLANK_BLOCKS);
                addBlockSet(entries, SPRUCE_PLANK_BLOCKS);
                addBlockSet(entries, WICKER);
                entries.add(WICKER_DOOR);
                entries.add(WICKER_TRAPDOOR);
                entries.add(WICKER_BARS);
                entries.add(ROPE);
                entries.add(ROPE_LADDER);
                entries.add(OAK_CRATE);
                entries.add(BIRCH_CRATE);
                entries.add(SPRUCE_CRATE);
                entries.add(LARGE_CLAY_POT);
                entries.add(LARGE_FIRED_CLAY_POT);
                entries.add(LARGE_DECORATIVE_FIRED_CLAY_POT);
                entries.add(WICKER_BASKET);
                entries.add(CRUDE_CRAFTING_BENCH);
            }));

    public static void init() {
    }

    // item group registry helper
    private static RegistryKey<ItemGroup> create(String id, ItemGroup.Builder itemGroup) {
        var key = RegistryKey.of(RegistryKeys.ITEM_GROUP, Primeval.identify(id));
        Registry.register(Registries.ITEM_GROUP, key, itemGroup.displayName(Text.translatable("itemGroup.primeval." + id)).build());
        return key;
    }

    private static <T extends Iterable<Block>> void addBlockSet(ItemGroup.Entries entries, T bs) {
        bs.iterator().forEachRemaining((b) -> entries.add(b.asItem()));
    }

    private static <T extends Iterable<BlockSet>> void addBlockSetSet(ItemGroup.Entries entries, T bs) {
        bs.iterator().forEachRemaining((b) -> addBlockSet(entries, b));
    }

}
