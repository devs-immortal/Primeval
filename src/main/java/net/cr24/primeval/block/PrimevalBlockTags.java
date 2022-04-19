package net.cr24.primeval.block;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class PrimevalBlockTags {

    public static final TagKey<Block> LIGHT_SOIL = register("light_soil");
    public static final TagKey<Block> MEDIUM_SOIL = register("medium_soil");
    public static final TagKey<Block> HEAVY_SOIL = register("heavy_soil");
    public static final TagKey<Block> SOIL = register("soil");

    public static final TagKey<Block> TREE_TRUNKS = register("tree_trunks");
    public static final TagKey<Block> LEAVES = register("leaves");
    public static final TagKey<Block> LOGS = register("logs");

    public static final TagKey<Block> COPPER_ORES = register("ores_copper");
    public static final TagKey<Block> IRON_ORES = register("ores_iron");
    public static final TagKey<Block> TIN_ORES = register("ores_tin");

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, PrimevalMain.getId(id));
    }
}
