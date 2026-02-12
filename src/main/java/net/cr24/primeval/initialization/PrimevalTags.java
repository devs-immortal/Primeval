package net.cr24.primeval.initialization;

import net.cr24.primeval.Primeval;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class PrimevalTags {

    public static class Blocks {
        public static final TagKey<Block> CAMPFIRE_BASE = register("campfire_base");
        public static final TagKey<Block> COLLAPSING_NO_CRUSH = register("collapsing_no_crush");
        public static final TagKey<Block> FARMLAND = register("farmland");
        // Fence Gates
        // Fences
        public static final TagKey<Block> HEAVY_SOIL = register("heavy_soil");
        public static final TagKey<Block> LEAVES = register("leaves");
        public static final TagKey<Block> LIGHT_SOIL = register("light_soil");
        public static final TagKey<Block> LOGS = register("logs");
        public static final TagKey<Block> MEDIUM_SOIL = register("medium_soil");
        public static final TagKey<Block> ORE_REPLACEABLE = register("ore_replaceable");
        public static final TagKey<Block> ORE_SEMI_REPLACEABLE = register("ore_semi_replaceable");
        // Needs Diamond Tool
        // Needs Iron Tool
        // Needs Stone Tool
        public static final TagKey<Block> COPPER_ORES = register("ores_copper");
        public static final TagKey<Block> GOLD_ORES = register("ores_gold");
        public static final TagKey<Block> IRON_ORES = register("ores_iron");
        public static final TagKey<Block> LAZURITE_ORES = register("ores_lazurite");
        public static final TagKey<Block> TIN_ORES = register("ores_tin");
        public static final TagKey<Block> ZINC_ORES = register("ores_zinc");
        public static final TagKey<Block> SOIL = register("soil");
        public static final TagKey<Block> SPECIAL_PLANTABLE = register("special_plantable");
        public static final TagKey<Block> TREE_TRUNKS = register("tree_trunks");

        public static final TagKey<Block> MINEABLE_CHISEL = register("mineable/chisel");

        private static TagKey<Block> register(String id) {
            return TagKey.create(Registries.BLOCK, Primeval.identify(id));
        }
    }

    public static class Items {
        public static final TagKey<Item> FLINT_TOOL_MATERIALS = register("tool_material_flint");
        public static final TagKey<Item> COPPER_TOOL_MATERIALS = register("tool_material_copper");
        public static final TagKey<Item> BRONZE_TOOL_MATERIALS = register("tool_material_bronze");

        public static final TagKey<Item> BURNABLE_SHORT = register("burnable_short");
        public static final TagKey<Item> BURNABLE_LONG = register("burnable_long");

        public static final TagKey<Item> BURNS_TO_ASH = register("burns_to_ash");

        public static final TagKey<Item> CHISELS = register("chisels");
        public static final TagKey<Item> KNIVES = register("knives");
        public static final TagKey<Item> LOGS = register("logs");
        public static final TagKey<Item> ROCKS = register("rocks");
        public static final TagKey<Item> CAMPFIRE_KINDLING = register("campfire_kindling");


        public static final TagKey<Item> PLANKS = register("planks");
        public static final TagKey<Item> MORTAR = register("mortar");
        public static final TagKey<Item> SAPLINGS = register("saplings");
        public static final TagKey<Item> CRATES = register("crates");

        private static TagKey<Item> register(String id) {
            return TagKey.create(Registries.ITEM, Primeval.identify(id));
        }
    }

    public static class Fluids {
        public static final TagKey<Fluid> ALL_MOLD_FLUIDS = register("all_mold_fluids");
        public static final TagKey<Fluid> TOOL_MOLD_FLUIDS = register("tool_mold_fluids");

        private static TagKey<Fluid> register(String id) {
            return TagKey.create(Registries.FLUID, Primeval.identify(id));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_COPPER = register("has_copper");
        public static final TagKey<Biome> HAS_GOLD = register("has_gold");
        public static final TagKey<Biome> HAS_LAZURITE = register("has_lazurite");
        public static final TagKey<Biome> HAS_TIN = register("has_tin");
        public static final TagKey<Biome> HAS_ZINC = register("has_zinc");
        public static final TagKey<Biome> RAISED_ORES = register("raised_ores");

        private static TagKey<Biome> register(String id) {
            return TagKey.create(Registries.BIOME, Primeval.identify(id));
        }
    }
}
