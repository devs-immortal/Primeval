package net.cr24.primeval.data;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.tag.PrimevalBlockTags;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.tag.PrimevalItemTags;
import net.cr24.primeval.item.PrimevalItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class PrimevalItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public PrimevalItemTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, new PrimevalBlockTagProvider(dataGenerator));
    }

    @Override
    protected void generateTags() {
        copy(PrimevalBlockTags.STRAW_BLOCKS, PrimevalItemTags.STRAW_BLOCKS);
        copy(PrimevalBlockTags.SAPLINGS, PrimevalItemTags.SAPLINGS);
        copy(PrimevalBlockTags.LOGS, PrimevalItemTags.LOGS);
        copy(PrimevalBlockTags.PLANKS, PrimevalItemTags.PLANKS);
        copy(PrimevalBlockTags.CRATES, PrimevalItemTags.CRATES);
        copy(PrimevalBlockTags.OAK_PLANKS, PrimevalItemTags.OAK_PLANKS);
        copy(PrimevalBlockTags.BIRCH_PLANKS, PrimevalItemTags.BIRCH_PLANKS);
        copy(PrimevalBlockTags.SPRUCE_PLANKS, PrimevalItemTags.SPRUCE_PLANKS);
        getOrCreateTagBuilder(PrimevalItemTags.MORTAR).add(PrimevalItems.CLAY_BALL);
        getOrCreateTagBuilder(PrimevalItemTags.ROCKS).add(PrimevalItems.ROCK);
        getOrCreateTagBuilder(PrimevalItemTags.AXES).add(PrimevalItems.FLINT_AXE).add(PrimevalMain.getId("copper_axe"), PrimevalMain.getId("bronze_axe"));
        getOrCreateTagBuilder(PrimevalItemTags.CHISELS).add(PrimevalMain.getId("copper_chisel"), PrimevalMain.getId("bronze_chisel"));
        getOrCreateTagBuilder(PrimevalItemTags.KNIVES).add(PrimevalItems.FLINT_KNIFE).add(PrimevalMain.getId("copper_knife"), PrimevalMain.getId("bronze_knife"));
        getOrCreateTagBuilder(PrimevalItemTags.BURNABLE_LONG).addTag(PrimevalItemTags.LOGS).addTag(PrimevalItemTags.CRATES).add(PrimevalBlocks.STRAW_MESH.asItem());
        getOrCreateTagBuilder(PrimevalItemTags.BURNABLE_SHORT).addTag(PrimevalItemTags.SAPLINGS).addTag(PrimevalItemTags.OAK_PLANKS).addTag(PrimevalItemTags.BIRCH_PLANKS).addTag(PrimevalItemTags.SPRUCE_PLANKS).addTag(PrimevalItemTags.STRAW_BLOCKS).add(PrimevalItems.STRAW, PrimevalItems.STICK);
        getOrCreateTagBuilder(PrimevalItemTags.CAMPFIRE_KINDLING).add(PrimevalItems.STRAW);
    }
}
