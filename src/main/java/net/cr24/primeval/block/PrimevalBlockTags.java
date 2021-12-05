package net.cr24.primeval.block;

import net.cr24.primeval.PrimevalMain;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;

public class PrimevalBlockTags {

    public static final Tag<Block> LIGHT_SOIL = TagFactory.BLOCK.create(PrimevalMain.getId("light_soil"));
    public static final Tag<Block> MEDIUM_SOIL = TagFactory.BLOCK.create(PrimevalMain.getId("medium_soil"));
    public static final Tag<Block> HEAVY_SOIL = TagFactory.BLOCK.create(PrimevalMain.getId("heavy_soil"));


}
