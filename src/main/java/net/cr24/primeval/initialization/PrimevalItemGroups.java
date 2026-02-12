package net.cr24.primeval.initialization;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.initialization.PrimevalBlocks.BlockSet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import static net.cr24.primeval.initialization.PrimevalBlocks.*;
import static net.cr24.primeval.initialization.PrimevalItems.*;
import static net.cr24.primeval.initialization.PrimevalItems.BRONZE_TOOL_PARTS;

public class PrimevalItemGroups {

    public static final ResourceKey<CreativeModeTab> BLOCKS = create("blocks", FabricItemGroup.builder()
            .icon(() -> new ItemStack(FIRED_CLAY_BRICK_BLOCKS.block()))
            .displayItems((context, entries) -> {
                // Terrain Blocks
                entries.accept(DIRT);
                entries.accept(COARSE_DIRT);
                entries.accept(CLAY);
                entries.accept(MUD);
                entries.accept(DRY_DIRT);
                entries.accept(GRASSY_DIRT);
                entries.accept(GRASSY_CLAY);
                entries.accept(SAND);
                entries.accept(GRAVEL);
                entries.accept(COBBLESTONE);
                entries.accept(STONE);
                entries.accept(SANDSTONE);
                // Plants
                entries.accept(OAK_SAPLING);
                entries.accept(BIRCH_SAPLING);
                entries.accept(SPRUCE_SAPLING);
                entries.accept(GRASS);
                entries.accept(BUSH);
                entries.accept(SPIKED_PLANT);
                entries.accept(LEAFY_PLANT);
                entries.accept(SHRUB);
                entries.accept(MOSS);
                // Flowers
                entries.accept(POPPY);
                entries.accept(DANDELION);
                entries.accept(OXEYE_DAISY);
                entries.accept(CORNFLOWER);
                entries.accept(LILY_OF_THE_VALLEY);
                // Water Plants
                entries.accept(REEDS);
                entries.accept(RIVER_GRASS);
                // Ores
                addBlockSet(entries, COPPER_MALACHITE_ORE);
                addBlockSet(entries, COPPER_NATIVE_ORE);
                addBlockSet(entries, TIN_CASSITERITE_ORE);
                addBlockSet(entries, ZINC_SPHALERITE_ORE);
                addBlockSet(entries, GOLD_NATIVE_ORE);
                addBlockSet(entries, IRON_HEMATITE_ORE);
                addBlockSet(entries, LAZURITE_ORE);
                entries.accept(FOSSIL);
                // Crafted Blocks
                entries.accept(STRAW_BLOCK);
                entries.accept(STRAW_STAIRS);
                entries.accept(STRAW_SLAB);
                entries.accept(STRAW_MESH);
                entries.accept(STRAW_MAT);
                entries.accept(TERRACOTTA);
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
                entries.accept(STONE_INDENT);
                entries.accept(STONE_PILLAR);
                addBlockSet(entries, STONE_PAVER);
                entries.accept(DAUB);
                entries.accept(FRAMED_DAUB);
                entries.accept(FRAMED_PILLAR_DAUB);
                entries.accept(FRAMED_CROSS_DAUB);
                entries.accept(FRAMED_INVERTED_CROSS_DAUB);
                entries.accept(FRAMED_X_DAUB);
                entries.accept(FRAMED_PLUS_DAUB);
                entries.accept(FRAMED_DIVIDED_DAUB);
                addBlockSet(entries, OAK_PLANK_BLOCKS);
                addBlockSet(entries, BIRCH_PLANK_BLOCKS);
                addBlockSet(entries, SPRUCE_PLANK_BLOCKS);
                addBlockSet(entries, WICKER);
                entries.accept(WICKER_DOOR);
                entries.accept(WICKER_TRAPDOOR);
                entries.accept(WICKER_BARS);
                entries.accept(ROPE);
                entries.accept(ROPE_LADDER);
                entries.accept(OAK_CRATE);
                entries.accept(BIRCH_CRATE);
                entries.accept(SPRUCE_CRATE);
                entries.accept(LARGE_CLAY_POT);
                entries.accept(LARGE_FIRED_CLAY_POT);
                entries.accept(LARGE_DECORATIVE_FIRED_CLAY_POT);
                entries.accept(WICKER_BASKET);
                entries.accept(LIT_CRUDE_TORCH);
                entries.accept(CRUDE_CRAFTING_BENCH);
                entries.accept(QUERN);
            })
    );

    public static final ResourceKey<CreativeModeTab> ITEMS = create("items", FabricItemGroup.builder()
            .icon(() -> new ItemStack(STRAW))
            .displayItems((context, entries) -> {
                entries.accept(STRAW);
                entries.accept(STICK);
                entries.accept(OAK_LOG);
                entries.accept(BIRCH_LOG);
                entries.accept(SPRUCE_LOG);
                entries.accept(STRING);
                entries.accept(FLINT);
                entries.accept(ROCK);
                entries.accept(STONE_BRICK);
                entries.accept(ASHES);
                entries.accept(CRUSHED_TERRACOTTA);
                entries.accept(CEMENT_MIX);
                entries.accept(CEMENT);
                entries.accept(BONE);
                entries.accept(BONEMEAL);
                entries.accept(ANIMAL_FAT);
                entries.accept(GUNPOWDER);
                entries.accept(CHARRED_BONE);

                entries.accept(SANDY_CLAY_BALL);
                entries.accept(SANDY_CLAY_BRICK);
                entries.accept(DRIED_BRICK);

                entries.accept(RAW_COPPER_MALACHITE_SMALL);
                entries.accept(RAW_COPPER_MALACHITE_MEDIUM);
                entries.accept(RAW_COPPER_MALACHITE_LARGE);
                entries.accept(RAW_COPPER_NATIVE_SMALL);
                entries.accept(RAW_COPPER_NATIVE_MEDIUM);
                entries.accept(RAW_COPPER_NATIVE_LARGE);
                entries.accept(RAW_TIN_CASSITERITE_SMALL);
                entries.accept(RAW_TIN_CASSITERITE_MEDIUM);
                entries.accept(RAW_TIN_CASSITERITE_LARGE);
                entries.accept(RAW_ZINC_SPHALERITE_SMALL);
                entries.accept(RAW_ZINC_SPHALERITE_MEDIUM);
                entries.accept(RAW_ZINC_SPHALERITE_LARGE);
                entries.accept(RAW_GOLD_NATIVE_SMALL);
                entries.accept(RAW_GOLD_NATIVE_MEDIUM);
                entries.accept(RAW_GOLD_NATIVE_LARGE);
                entries.accept(RAW_IRON_HEMATITE_SMALL);
                entries.accept(RAW_IRON_HEMATITE_MEDIUM);
                entries.accept(RAW_IRON_HEMATITE_LARGE);
                entries.accept(RAW_LAZURITE_SMALL);
                entries.accept(RAW_LAZURITE_MEDIUM);
                entries.accept(RAW_LAZURITE_LARGE);

                entries.accept(MUD_BALL);
                entries.accept(MUD_BRICK);
                entries.accept(CLAY_BALL);
                entries.accept(CLAY_BRICK);
                entries.accept(FIRED_CLAY_BRICK);
                entries.accept(CLAY_TILE);
                entries.accept(FIRED_CLAY_TILE);
                entries.accept(CLAY_BOWL);
                entries.accept(FIRED_CLAY_BOWL);
                entries.accept(CLAY_JUG);
                entries.accept(FIRED_CLAY_JUG);
                entries.accept(CLAY_VESSEL);
                entries.accept(FIRED_CLAY_VESSEL);

                entries.accept(CLAY_INGOT_MOLD);
                entries.accept(FIRED_CLAY_INGOT_MOLD);
                entries.accept(CLAY_AXE_HEAD_MOLD);
                entries.accept(FIRED_CLAY_AXE_HEAD_MOLD);
                entries.accept(CLAY_CHISEL_HEAD_MOLD);
                entries.accept(FIRED_CLAY_CHISEL_HEAD_MOLD);
                entries.accept(CLAY_KNIFE_BLADE_MOLD);
                entries.accept(FIRED_CLAY_KNIFE_BLADE_MOLD);
                entries.accept(CLAY_PICKAXE_HEAD_MOLD);
                entries.accept(FIRED_CLAY_PICKAXE_HEAD_MOLD);
                entries.accept(CLAY_SHOVEL_HEAD_MOLD);
                entries.accept(FIRED_CLAY_SHOVEL_HEAD_MOLD);
                entries.accept(CLAY_SWORD_BLADE_MOLD);
                entries.accept(FIRED_CLAY_SWORD_BLADE_MOLD);
                entries.accept(CLAY_HOE_HEAD_MOLD);
                entries.accept(FIRED_CLAY_HOE_HEAD_MOLD);
                entries.accept(CLAY_PROSPECTING_PICKAXE_HEAD_MOLD);
                entries.accept(FIRED_CLAY_PROSPECTING_PICKAXE_HEAD_MOLD);

                entries.accept(COPPER_INGOT);
                entries.accept(COPPER_CHUNK);
                entries.accept(TIN_INGOT);
                entries.accept(TIN_CHUNK);
                entries.accept(ZINC_INGOT);
                entries.accept(ZINC_CHUNK);

                entries.accept(BRONZE_INGOT);
                entries.accept(BRONZE_CHUNK);
                entries.accept(BRASS_INGOT);
                entries.accept(BRASS_CHUNK);
                entries.accept(PEWTER_INGOT);
                entries.accept(PEWTER_CHUNK);
                entries.accept(GOLD_INGOT);
                entries.accept(GOLD_CHUNK);
                entries.accept(BOTCHED_ALLOY_INGOT);
                entries.accept(BOTCHED_ALLOY_CHUNK);

                entries.accept(WHITE_DYE);
                entries.accept(ORANGE_DYE);
                entries.accept(MAGENTA_DYE);
                entries.accept(LIGHT_BLUE_DYE);
                entries.accept(YELLOW_DYE);
                entries.accept(LIME_DYE);
                entries.accept(PINK_DYE);
                entries.accept(DARK_GRAY_DYE);
                entries.accept(LIGHT_GRAY_DYE);
                entries.accept(CYAN_DYE);
                entries.accept(PURPLE_DYE);
                entries.accept(BLUE_DYE);
                entries.accept(BROWN_DYE);
                entries.accept(GREEN_DYE);
                entries.accept(RED_DYE);
                entries.accept(BLACK_DYE);
            })
    );

    public static final ResourceKey<CreativeModeTab> TOOLS = create("tools", FabricItemGroup.builder()
            .icon(() -> new ItemStack(FLINT_AXE))
            .displayItems((context, entries) -> {
                entries.accept(FLINT_AXE);
                entries.accept(FLINT_KNIFE);
                entries.accept(FLINT_SHOVEL);
                entries.accept(FLINT_SPEAR);
                addItemSet(entries, COPPER_TOOLS);
                addItemSet(entries, BRONZE_TOOLS);
                addItemSet(entries, COPPER_TOOL_PARTS);
                addItemSet(entries, BRONZE_TOOL_PARTS);

                entries.accept(WOODEN_BUCKET);
                entries.accept(WOODEN_BUCKET_WATER);
                entries.accept(FIRED_CLAY_JUG);
                entries.accept(FIRED_CLAY_WATER_JUG);
                entries.accept(FIRED_CLAY_VESSEL);
                entries.accept(QUERN_WHEEL);

                entries.accept(COPPER_COIN);
                entries.accept(GOLD_COIN);
            })
    );

    public static final ResourceKey<CreativeModeTab> FOODS = create("foods", FabricItemGroup.builder()
            .icon(() -> new ItemStack(COOKED_PORKCHOP))
            .displayItems((context, entries) -> {
                entries.accept(PORKCHOP);
                entries.accept(COOKED_PORKCHOP);
                entries.accept(CARROT);
                entries.accept(WHEAT);
                entries.accept(CABBAGE);
                entries.accept(BEANS);
                entries.accept(POTATO);
                entries.accept(WHEAT_SEEDS);
                entries.accept(CABBAGE_SEEDS);
                entries.accept(ROTTEN_FLESH);
                entries.accept(SPIDER_EYE);
            })
    );




    public static void init() {
    }

    // item group registry helper
    private static ResourceKey<CreativeModeTab> create(String id, CreativeModeTab.Builder itemGroup) {
        var key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Primeval.identify(id));
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, itemGroup.title(Component.translatable("itemGroup.primeval." + id)).build());
        return key;
    }

    private static <T extends Iterable<Block>> void addBlockSet(CreativeModeTab.Output entries, T bs) {
        bs.iterator().forEachRemaining((b) -> entries.accept(b.asItem()));
    }

    private static <T extends Iterable<BlockSet>> void addBlockSetSet(CreativeModeTab.Output entries, T bs) {
        bs.iterator().forEachRemaining((b) -> addBlockSet(entries, b));
    }

    private static <T extends Iterable<Item>> void addItemSet(CreativeModeTab.Output entries, T bs) {
        bs.iterator().forEachRemaining((b) -> entries.accept(b));
    }

}
