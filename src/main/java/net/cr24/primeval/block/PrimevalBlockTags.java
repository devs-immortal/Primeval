package net.cr24.primeval.block;

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

    public static final TagKey<Block> TREE_TRUNKS = register("tree_trunks");
    public static final TagKey<Block> LEAVES = register("leaves");
    public static final TagKey<Block> LOGS = register("logs");

    public static final TagKey<Block> COPPER_ORES = register("ores_copper");
    public static final TagKey<Block> IRON_ORES = register("ores_iron");
    public static final TagKey<Block> TIN_ORES = register("ores_tin");
    public static final TagKey<Block> GOLD_ORES = register("ores_gold");

    public static final TagKey<Block> MINEABLE_CHISEL = register("mineable/chisel");

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, PrimevalMain.getId(id));
    }
}
