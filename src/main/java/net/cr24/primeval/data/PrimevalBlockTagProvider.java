package net.cr24.primeval.data;

import net.cr24.primeval.tag.PrimevalBlockTags;
import net.cr24.primeval.block.PrimevalBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;

public class PrimevalBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public PrimevalBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        //primeval
        getOrCreateTagBuilder(PrimevalBlockTags.STRAW_BLOCKS).add(PrimevalBlocks.STRAW_BLOCK, PrimevalBlocks.STRAW_STAIRS, PrimevalBlocks.STRAW_SLAB, PrimevalBlocks.STRAW_MESH, PrimevalBlocks.STRAW_MAT);
        getOrCreateTagBuilder(PrimevalBlockTags.DAUB_BLOCKS).add(PrimevalBlocks.DAUB, PrimevalBlocks.FRAMED_DAUB, PrimevalBlocks.FRAMED_PILLAR_DAUB, PrimevalBlocks.FRAMED_CROSS_DAUB, PrimevalBlocks.FRAMED_INVERTED_CROSS_DAUB, PrimevalBlocks.FRAMED_X_DAUB, PrimevalBlocks.FRAMED_PLUS_DAUB, PrimevalBlocks.FRAMED_DIVIDED_DAUB);
        getOrCreateTagBuilder(PrimevalBlockTags.SAPLINGS).add(PrimevalBlocks.OAK_SAPLING, PrimevalBlocks.BIRCH_SAPLING, PrimevalBlocks.SPRUCE_SAPLING);
        getOrCreateTagBuilder(PrimevalBlockTags.LEAVES).add(PrimevalBlocks.OAK_LEAVES, PrimevalBlocks.BIRCH_LEAVES, PrimevalBlocks.SPRUCE_LEAVES);
        getOrCreateTagBuilder(PrimevalBlockTags.TREE_TRUNKS).add(PrimevalBlocks.OAK_TRUNK, PrimevalBlocks.BIRCH_TRUNK, PrimevalBlocks.SPRUCE_TRUNK);
        getOrCreateTagBuilder(PrimevalBlockTags.LOGS).add(PrimevalBlocks.OAK_LOG, PrimevalBlocks.BIRCH_LOG, PrimevalBlocks.SPRUCE_LOG);
        getOrCreateTagBuilder(PrimevalBlockTags.PLANKS).add(PrimevalBlocks.OAK_PLANK_BLOCKS.block(), PrimevalBlocks.BIRCH_PLANK_BLOCKS.block(), PrimevalBlocks.SPRUCE_PLANK_BLOCKS.block());
        getOrCreateTagBuilder(PrimevalBlockTags.CRATES).add(PrimevalBlocks.OAK_CRATE, PrimevalBlocks.BIRCH_CRATE, PrimevalBlocks.SPRUCE_CRATE);
        for (Block block : PrimevalBlocks.OAK_PLANK_BLOCKS) getOrCreateTagBuilder(PrimevalBlockTags.OAK_PLANKS).add(block);
        for (Block block : PrimevalBlocks.BIRCH_PLANK_BLOCKS) getOrCreateTagBuilder(PrimevalBlockTags.BIRCH_PLANKS).add(block);
        for (Block block : PrimevalBlocks.SPRUCE_PLANK_BLOCKS) getOrCreateTagBuilder(PrimevalBlockTags.SPRUCE_PLANKS).add(block);
        // won't let me use <for statement> with instances of OreBlockSet or BlockSet, but it does work with WoodBlockSet idk why T_T
        // for (Block block : PrimevalBlocks.MUD_BRICKS) getOrCreateTagBuilder(PrimevalBlockTags.MUD_BRICKS).add(block);
        getOrCreateTagBuilder(PrimevalBlockTags.MUD_BRICKS).add(PrimevalBlocks.MUD_BRICKS.block(), PrimevalBlocks.MUD_BRICKS.stairs(), PrimevalBlocks.MUD_BRICKS.slab());
        getOrCreateTagBuilder(PrimevalBlockTags.COPPER_ORES).add(PrimevalBlocks.COPPER_MALACHITE_ORE.small(), PrimevalBlocks.COPPER_MALACHITE_ORE.medium(), PrimevalBlocks.COPPER_MALACHITE_ORE.large()).add(PrimevalBlocks.COPPER_NATIVE_ORE.small(), PrimevalBlocks.COPPER_NATIVE_ORE.medium(), PrimevalBlocks.COPPER_NATIVE_ORE.large());
        getOrCreateTagBuilder(PrimevalBlockTags.IRON_ORES).add(PrimevalBlocks.IRON_HEMATITE_ORE.small(), PrimevalBlocks.IRON_HEMATITE_ORE.medium(), PrimevalBlocks.IRON_HEMATITE_ORE.large());
        getOrCreateTagBuilder(PrimevalBlockTags.TIN_ORES).add(PrimevalBlocks.TIN_CASSITERITE_ORE.small(), PrimevalBlocks.TIN_CASSITERITE_ORE.medium(), PrimevalBlocks.TIN_CASSITERITE_ORE.large());
        getOrCreateTagBuilder(PrimevalBlockTags.ZINC_ORES).add(PrimevalBlocks.ZINC_SPHALERITE_ORE.small(), PrimevalBlocks.ZINC_SPHALERITE_ORE.medium(), PrimevalBlocks.ZINC_SPHALERITE_ORE.large());
        getOrCreateTagBuilder(PrimevalBlockTags.SMOOTH_STONE_BLOCKS).add(PrimevalBlocks.SMOOTH_STONE.block(), PrimevalBlocks.SMOOTH_STONE.stairs(), PrimevalBlocks.SMOOTH_STONE.slab());
        getOrCreateTagBuilder(PrimevalBlockTags.STONE_BRICKS).add(PrimevalBlocks.STONE_BRICKS.block(), PrimevalBlocks.STONE_BRICKS.stairs(), PrimevalBlocks.STONE_BRICKS.slab());
        getOrCreateTagBuilder(PrimevalBlockTags.FIRED_SHINGLE_BLOCKS).add(PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.block(), PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.stairs(), PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.slab());
        getOrCreateTagBuilder(PrimevalBlockTags.FIRED_BRICKS).add(PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.block(), PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.stairs(), PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.slab());
        getOrCreateTagBuilder(PrimevalBlockTags.FIRED_TILE_BLOCKS).add(PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.block(), PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.stairs(), PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.slab());
        getOrCreateTagBuilder(PrimevalBlockTags.DRIED_BRICKS).add(PrimevalBlocks.DRIED_BRICK_BLOCKS.block(), PrimevalBlocks.DRIED_BRICK_BLOCKS.stairs(), PrimevalBlocks.DRIED_BRICK_BLOCKS.slab());
        getOrCreateTagBuilder(PrimevalBlockTags.AXE_MINEABLE).addTag(PrimevalBlockTags.TREE_TRUNKS).addTag(PrimevalBlockTags.LOGS).addTag(PrimevalBlockTags.LEAVES).addTag(PrimevalBlockTags.STRAW_BLOCKS).addTag(PrimevalBlockTags.DAUB_BLOCKS).addTag(PrimevalBlockTags.CRATES).addTag(PrimevalBlockTags.OAK_PLANKS).addTag(PrimevalBlockTags.BIRCH_PLANKS).addTag(PrimevalBlockTags.SPRUCE_PLANKS).add(PrimevalBlocks.PIT_KILN, PrimevalBlocks.CRUDE_CRAFTING_BENCH, PrimevalBlocks.ROPE, PrimevalBlocks.ROPE_LADDER);
        getOrCreateTagBuilder(PrimevalBlockTags.CHISEL_MINEABLE).add(PrimevalBlocks.STONE);
        getOrCreateTagBuilder(PrimevalBlockTags.HOE_MINEABLE);
        getOrCreateTagBuilder(PrimevalBlockTags.PICKAXE_MINEABLE).addTag(PrimevalBlockTags.SMOOTH_STONE_BLOCKS).addTag(PrimevalBlockTags.STONE_BRICKS).addTag(PrimevalBlockTags.FIRED_SHINGLE_BLOCKS).addTag(PrimevalBlockTags.FIRED_BRICKS).addTag(PrimevalBlockTags.FIRED_TILE_BLOCKS).addTag(PrimevalBlockTags.DRIED_BRICKS).addTag(PrimevalBlockTags.COPPER_ORES).addTag(PrimevalBlockTags.IRON_ORES).addTag(PrimevalBlockTags.TIN_ORES).addTag(PrimevalBlockTags.ZINC_ORES).add(PrimevalBlocks.COBBLESTONE, PrimevalBlocks.STONE, PrimevalBlocks.TERRACOTTA, PrimevalBlocks.LARGE_FIRED_CLAY_POT, PrimevalBlocks.LARGE_DECORATIVE_FIRED_CLAY_POT);
        getOrCreateTagBuilder(PrimevalBlockTags.SHOVEL_MINEABLE).add(PrimevalBlocks.DIRT, PrimevalBlocks.COARSE_DIRT, PrimevalBlocks.MUD, PrimevalBlocks.GRASSY_DIRT, PrimevalBlocks.SAND, PrimevalBlocks.GRAVEL, PrimevalBlocks.CLAY_BLOCK, PrimevalBlocks.ASH_PILE, PrimevalBlocks.LARGE_CLAY_POT).addTag(PrimevalBlockTags.MUD_BRICKS);
        getOrCreateTagBuilder(PrimevalBlockTags.CAMPFIRE_BASE).add(PrimevalBlocks.STONE, PrimevalBlocks.COBBLESTONE);
        getOrCreateTagBuilder(PrimevalBlockTags.COLLAPSING_NO_CRUSH).add(PrimevalBlocks.MUD);
        getOrCreateTagBuilder(PrimevalBlockTags.FENCE_GATES).add(PrimevalBlocks.OAK_PLANK_BLOCKS.fenceGate(), PrimevalBlocks.BIRCH_PLANK_BLOCKS.fenceGate(), PrimevalBlocks.SPRUCE_PLANK_BLOCKS.fenceGate());
        getOrCreateTagBuilder(PrimevalBlockTags.FENCES).add(PrimevalBlocks.OAK_PLANK_BLOCKS.fence(), PrimevalBlocks.BIRCH_PLANK_BLOCKS.fence(), PrimevalBlocks.SPRUCE_PLANK_BLOCKS.fence(), PrimevalBlocks.OAK_PLANK_BLOCKS.logFence(), PrimevalBlocks.BIRCH_PLANK_BLOCKS.logFence(), PrimevalBlocks.SPRUCE_PLANK_BLOCKS.logFence());
        getOrCreateTagBuilder(PrimevalBlockTags.HEAVY_SOIL).add(PrimevalBlocks.MUD, PrimevalBlocks.GRASSY_DIRT);
        getOrCreateTagBuilder(PrimevalBlockTags.LIGHT_SOIL).add(PrimevalBlocks.SAND, PrimevalBlocks.COBBLESTONE);
        getOrCreateTagBuilder(PrimevalBlockTags.MEDIUM_SOIL).add(PrimevalBlocks.DIRT, PrimevalBlocks.COARSE_DIRT);
        getOrCreateTagBuilder(PrimevalBlockTags.NATURAL_STONE).add(PrimevalBlocks.STONE);
        getOrCreateTagBuilder(PrimevalBlockTags.NEEDS_DIAMOND_TOOL);
        getOrCreateTagBuilder(PrimevalBlockTags.NEEDS_IRON_TOOL);
        getOrCreateTagBuilder(PrimevalBlockTags.NEEDS_STONE_TOOL);
        getOrCreateTagBuilder(PrimevalBlockTags.SOIL).addTag(PrimevalBlockTags.LIGHT_SOIL).addTag(PrimevalBlockTags.MEDIUM_SOIL).addTag(PrimevalBlockTags.HEAVY_SOIL);
        getOrCreateTagBuilder(PrimevalBlockTags.SPECIAL_PLANTABLE).add(PrimevalBlocks.LARGE_DECORATIVE_FIRED_CLAY_POT);

        //minecraft
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).addTag(PrimevalBlockTags.AXE_MINEABLE);
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).addTag(PrimevalBlockTags.HOE_MINEABLE);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).addTag(PrimevalBlockTags.PICKAXE_MINEABLE);
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).addTag(PrimevalBlockTags.SHOVEL_MINEABLE);
        getOrCreateTagBuilder(BlockTags.ANIMALS_SPAWNABLE_ON).add(PrimevalBlocks.GRASSY_DIRT, PrimevalBlocks.DIRT, PrimevalBlocks.COARSE_DIRT);
        getOrCreateTagBuilder(BlockTags.BASE_STONE_OVERWORLD).add(PrimevalBlocks.STONE);
        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES);
        getOrCreateTagBuilder(BlockTags.BEEHIVES);
        getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(PrimevalBlocks.ROPE, PrimevalBlocks.ROPE_LADDER);
        getOrCreateTagBuilder(BlockTags.DOORS);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).addTag(PrimevalBlockTags.FENCE_GATES);
        getOrCreateTagBuilder(BlockTags.FENCES).addTag(PrimevalBlockTags.FENCES);
        getOrCreateTagBuilder(BlockTags.FOXES_SPAWNABLE_ON);
        getOrCreateTagBuilder(BlockTags.GOATS_SPAWNABLE_ON);
        getOrCreateTagBuilder(BlockTags.IMPERMEABLE);
        getOrCreateTagBuilder(BlockTags.INFINIBURN_OVERWORLD).add(PrimevalBlocks.PIT_KILN);
        getOrCreateTagBuilder(BlockTags.MOOSHROOMS_SPAWNABLE_ON);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).addTag(PrimevalBlockTags.NEEDS_DIAMOND_TOOL);
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).addTag(PrimevalBlockTags.NEEDS_IRON_TOOL);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).addTag(PrimevalBlockTags.NEEDS_STONE_TOOL);
        getOrCreateTagBuilder(BlockTags.PARROTS_SPAWNABLE_ON);
        getOrCreateTagBuilder(BlockTags.SIGNS);
        getOrCreateTagBuilder(BlockTags.SLABS);
        getOrCreateTagBuilder(BlockTags.STAIRS);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS);
        getOrCreateTagBuilder(BlockTags.TRAPDOORS);
        getOrCreateTagBuilder(BlockTags.WALL_POST_OVERRIDE);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS);
        getOrCreateTagBuilder(BlockTags.WALLS);
        getOrCreateTagBuilder(BlockTags.WOLVES_SPAWNABLE_ON);
    }
}
