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

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, PrimevalMain.getId(id));
    }
}
