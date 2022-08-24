package net.cr24.primeval.tag;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class PrimevalBlockTags {

    public static final TagKey<Block> COLLAPSING_NO_CRUSH = register("collapsing_no_crush");
    public static final TagKey<Block> SPECIAL_PLANTABLE = register("special_plantable");

    public static final TagKey<Block> LIGHT_SOIL = register("light_soil");
    public static final TagKey<Block> MEDIUM_SOIL = register("medium_soil");
    public static final TagKey<Block> HEAVY_SOIL = register("heavy_soil");
    public static final TagKey<Block> SOIL = register("soil");
    public static final TagKey<Block> NATURAL_STONE = register("natural_stone");
    public static final TagKey<Block> CAMPFIRE_BASE = register("campfire_base");

    public static final TagKey<Block> SAPLINGS = register("saplings");
    public static final TagKey<Block> TREE_TRUNKS = register("tree_trunks");
    public static final TagKey<Block> LEAVES = register("leaves");
    public static final TagKey<Block> LOGS = register("logs");
    public static final TagKey<Block> PLANKS = register("planks");
    public static final TagKey<Block> OAK_PLANKS = register("oak_planks");
    public static final TagKey<Block> BIRCH_PLANKS = register("birch_planks");
    public static final TagKey<Block> SPRUCE_PLANKS = register("spruce_planks");
    public static final TagKey<Block> CRATES = register("crates");
    public static final TagKey<Block> FENCE_GATES = register("fence_gates");
    public static final TagKey<Block> FENCES = register("fences");

    public static final TagKey<Block> STRAW_BLOCKS = register("straw_blocks");
    public static final TagKey<Block> DAUB_BLOCKS = register("daub_blocks");
    public static final TagKey<Block> MUD_BRICKS = register("mud_bricks");
    public static final TagKey<Block> STONE_BRICKS = register("stone_bricks");
    public static final TagKey<Block> SMOOTH_STONE_BLOCKS = register("smooth_stone_blocks");
    public static final TagKey<Block> FIRED_SHINGLE_BLOCKS = register("fired_shingle_blocks");
    public static final TagKey<Block> FIRED_BRICKS = register("fired_bricks");
    public static final TagKey<Block> FIRED_TILE_BLOCKS = register("fired_tile_blocks");
    public static final TagKey<Block> DRIED_BRICKS = register("dried_bricks");

    public static final TagKey<Block> COPPER_ORES = register("copper_ores");
    public static final TagKey<Block> IRON_ORES = register("iron_ores");
    public static final TagKey<Block> TIN_ORES = register("tin_ores");
    public static final TagKey<Block> ZINC_ORES = register("zinc_ores");

    public static final TagKey<Block> CHISEL_MINEABLE = register("mineable/chisel");
    public static final TagKey<Block> AXE_MINEABLE = register("mineable/axe");
    public static final TagKey<Block> HOE_MINEABLE = register("mineable/hoe");
    public static final TagKey<Block> PICKAXE_MINEABLE = register("mineable/pickaxe");
    public static final TagKey<Block> SHOVEL_MINEABLE = register("mineable/shovel");

    public static final TagKey<Block> NEEDS_DIAMOND_TOOL = register("needs_diamond_tool");
    public static final TagKey<Block> NEEDS_IRON_TOOL = register("needs_iron_tool");
    public static final TagKey<Block> NEEDS_STONE_TOOL = register("needs_stone_tool");

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, PrimevalMain.getId(id));
    }
}
