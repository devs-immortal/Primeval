package net.cr24.primeval.data;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.TrunkBlock;
import net.cr24.primeval.block.functional.TimedTorchBlock;
import net.cr24.primeval.tag.PrimevalItemTags;
import net.cr24.primeval.item.PrimevalItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.state.property.Property;

public class PrimevalBlockLootTableProvider extends FabricBlockLootTableProvider {
    public PrimevalBlockLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        addDrop(PrimevalBlocks.GRASS, grassDrops(PrimevalItems.STRAW));
        addDrop(PrimevalBlocks.GRASSY_DIRT, drops(PrimevalBlocks.DIRT));
        addDrop(PrimevalBlocks.DIRT);
        addDrop(PrimevalBlocks.COARSE_DIRT);
        addDrop(PrimevalBlocks.STONE, (Block block) -> stoneDrops(block, Blocks.COBBLESTONE));
        addDrop(PrimevalBlocks.COBBLESTONE);
        addDrop(PrimevalBlocks.SAND);
        addDrop(PrimevalBlocks.GRAVEL, PrimevalBlockLootTableProvider::gravelDrops);
        addDrop(PrimevalBlocks.CLAY_BLOCK, drops(PrimevalItems.CLAY_BALL, UniformLootNumberProvider.create(3.0f, 4.0f)));
        addDrop(PrimevalBlocks.OAK_LEAVES, leaveDrops(PrimevalBlocks.OAK_SAPLING));
        addDrop(PrimevalBlocks.OAK_TRUNK, trunkDrops(PrimevalBlocks.OAK_LOG));
        addDrop(PrimevalBlocks.OAK_LOG);
        addDrop(PrimevalBlocks.OAK_SAPLING);
        addDrop(PrimevalBlocks.OAK_PLANK_BLOCKS.block());
        addDrop(PrimevalBlocks.OAK_PLANK_BLOCKS.stairs());
        addDrop(PrimevalBlocks.OAK_PLANK_BLOCKS.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.OAK_PLANK_BLOCKS.fence());
        addDrop(PrimevalBlocks.OAK_PLANK_BLOCKS.logFence());
        addDrop(PrimevalBlocks.OAK_PLANK_BLOCKS.fenceGate());
        addDrop(PrimevalBlocks.OAK_PLANK_BLOCKS.door(), BlockLootTableGenerator::doorDrops);
        addDrop(PrimevalBlocks.OAK_PLANK_BLOCKS.trapdoor());
        addDrop(PrimevalBlocks.OAK_CRATE, BlockLootTableGenerator::drops);
        addDrop(PrimevalBlocks.BIRCH_LEAVES, leaveDrops(PrimevalBlocks.BIRCH_SAPLING));
        addDrop(PrimevalBlocks.BIRCH_TRUNK, trunkDrops(PrimevalBlocks.BIRCH_LOG));
        addDrop(PrimevalBlocks.BIRCH_LOG);
        addDrop(PrimevalBlocks.BIRCH_SAPLING);
        addDrop(PrimevalBlocks.BIRCH_PLANK_BLOCKS.block());
        addDrop(PrimevalBlocks.BIRCH_PLANK_BLOCKS.stairs());
        addDrop(PrimevalBlocks.BIRCH_PLANK_BLOCKS.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.BIRCH_PLANK_BLOCKS.fence());
        addDrop(PrimevalBlocks.BIRCH_PLANK_BLOCKS.logFence());
        addDrop(PrimevalBlocks.BIRCH_PLANK_BLOCKS.fenceGate());
        addDrop(PrimevalBlocks.BIRCH_PLANK_BLOCKS.door(), BlockLootTableGenerator::doorDrops);
        addDrop(PrimevalBlocks.BIRCH_PLANK_BLOCKS.trapdoor());
        addDrop(PrimevalBlocks.BIRCH_CRATE, BlockLootTableGenerator::drops);
        addDrop(PrimevalBlocks.SPRUCE_LEAVES, leaveDrops(PrimevalBlocks.SPRUCE_SAPLING));
        addDrop(PrimevalBlocks.SPRUCE_TRUNK, trunkDrops(PrimevalBlocks.SPRUCE_LOG));
        addDrop(PrimevalBlocks.SPRUCE_LOG);
        addDrop(PrimevalBlocks.SPRUCE_SAPLING);
        addDrop(PrimevalBlocks.SPRUCE_PLANK_BLOCKS.block());
        addDrop(PrimevalBlocks.SPRUCE_PLANK_BLOCKS.stairs());
        addDrop(PrimevalBlocks.SPRUCE_PLANK_BLOCKS.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.SPRUCE_PLANK_BLOCKS.fence());
        addDrop(PrimevalBlocks.SPRUCE_PLANK_BLOCKS.logFence());
        addDrop(PrimevalBlocks.SPRUCE_PLANK_BLOCKS.fenceGate());
        addDrop(PrimevalBlocks.SPRUCE_PLANK_BLOCKS.door(), BlockLootTableGenerator::doorDrops);
        addDrop(PrimevalBlocks.SPRUCE_PLANK_BLOCKS.trapdoor());
        addDrop(PrimevalBlocks.SPRUCE_CRATE, BlockLootTableGenerator::drops);
        addDrop(PrimevalBlocks.COPPER_MALACHITE_ORE.large(), oreDrops(PrimevalItems.RAW_COPPER_MALACHITE_LARGE));
        addDrop(PrimevalBlocks.COPPER_MALACHITE_ORE.medium(), oreDrops(PrimevalItems.RAW_COPPER_MALACHITE_MEDIUM));
        addDrop(PrimevalBlocks.COPPER_MALACHITE_ORE.small(), oreDrops(PrimevalItems.RAW_COPPER_MALACHITE_SMALL));
        addDrop(PrimevalBlocks.COPPER_NATIVE_ORE.large(), oreDrops(PrimevalItems.RAW_COPPER_NATIVE_LARGE));
        addDrop(PrimevalBlocks.COPPER_NATIVE_ORE.medium(), oreDrops(PrimevalItems.RAW_COPPER_NATIVE_MEDIUM));
        addDrop(PrimevalBlocks.COPPER_NATIVE_ORE.small(), oreDrops(PrimevalItems.RAW_COPPER_NATIVE_SMALL));
        addDrop(PrimevalBlocks.IRON_HEMATITE_ORE.large(), oreDrops(PrimevalItems.RAW_IRON_HEMATITE_LARGE));
        addDrop(PrimevalBlocks.IRON_HEMATITE_ORE.medium(), oreDrops(PrimevalItems.RAW_IRON_HEMATITE_MEDIUM));
        addDrop(PrimevalBlocks.IRON_HEMATITE_ORE.small(), oreDrops(PrimevalItems.RAW_IRON_HEMATITE_SMALL));
        addDrop(PrimevalBlocks.TIN_CASSITERITE_ORE.large(), oreDrops(PrimevalItems.RAW_TIN_CASSITERITE_LARGE));
        addDrop(PrimevalBlocks.TIN_CASSITERITE_ORE.medium(), oreDrops(PrimevalItems.RAW_TIN_CASSITERITE_MEDIUM));
        addDrop(PrimevalBlocks.TIN_CASSITERITE_ORE.small(), oreDrops(PrimevalItems.RAW_TIN_CASSITERITE_SMALL));
        addDrop(PrimevalBlocks.ZINC_SPHALERITE_ORE.large(), oreDrops(PrimevalItems.RAW_ZINC_SPHALERITE_LARGE));
        addDrop(PrimevalBlocks.ZINC_SPHALERITE_ORE.medium(), oreDrops(PrimevalItems.RAW_ZINC_SPHALERITE_MEDIUM));
        addDrop(PrimevalBlocks.ZINC_SPHALERITE_ORE.small(), oreDrops(PrimevalItems.RAW_ZINC_SPHALERITE_SMALL));
        addDrop(PrimevalBlocks.DANDELION, PrimevalBlockLootTableProvider::dropsWithKnife);
        addDrop(PrimevalBlocks.OXEYE_DAISY, PrimevalBlockLootTableProvider::dropsWithKnife);
        addDrop(PrimevalBlocks.POPPY, PrimevalBlockLootTableProvider::dropsWithKnife);
        addDrop(PrimevalBlocks.WILD_CARROTS, drops(PrimevalItems.CARROT, UniformLootNumberProvider.create(2.0f, 6.0f)));
        addDrop(PrimevalBlocks.STRAW_BLOCK);
        addDrop(PrimevalBlocks.STRAW_MAT);
        addDrop(PrimevalBlocks.STRAW_MESH);
        addDrop(PrimevalBlocks.STRAW_SLAB, BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.STRAW_STAIRS);
        addDrop(PrimevalBlocks.ASH_PILE, drops(PrimevalItems.ASHES, UniformLootNumberProvider.create(1.0f, 3.0f)));
        addDrop(PrimevalBlocks.CAMPFIRE, campfireDrops());
        addDrop(PrimevalBlocks.CRUDE_TORCH, PrimevalBlockLootTableProvider::torchDrops);
        addDrop(PrimevalBlocks.CRUDE_CRAFTING_BENCH);
        addDrop(PrimevalBlocks.ROPE);
        addDrop(PrimevalBlocks.ROPE_LADDER);
        addDrop(PrimevalBlocks.SMOOTH_STONE.block());
        addDrop(PrimevalBlocks.SMOOTH_STONE.stairs());
        addDrop(PrimevalBlocks.SMOOTH_STONE.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.STONE_BRICKS.block());
        addDrop(PrimevalBlocks.STONE_BRICKS.stairs());
        addDrop(PrimevalBlocks.STONE_BRICKS.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.DRIED_BRICK_BLOCKS.block());
        addDrop(PrimevalBlocks.DRIED_BRICK_BLOCKS.stairs());
        addDrop(PrimevalBlocks.DRIED_BRICK_BLOCKS.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.block());
        addDrop(PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.stairs());
        addDrop(PrimevalBlocks.FIRED_CLAY_BRICK_BLOCKS.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.block());
        addDrop(PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.stairs());
        addDrop(PrimevalBlocks.FIRED_CLAY_SHINGLE_BLOCKS.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.block());
        addDrop(PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.stairs());
        addDrop(PrimevalBlocks.FIRED_CLAY_TILES_BLOCKS.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.LARGE_DECORATIVE_FIRED_CLAY_POT);
        addDrop(PrimevalBlocks.LARGE_FIRED_CLAY_POT);
        addDrop(PrimevalBlocks.LARGE_CLAY_POT);
        addDrop(PrimevalBlocks.DAUB);
        addDrop(PrimevalBlocks.FRAMED_DAUB);
        addDrop(PrimevalBlocks.FRAMED_PILLAR_DAUB);
        addDrop(PrimevalBlocks.FRAMED_CROSS_DAUB);
        addDrop(PrimevalBlocks.FRAMED_INVERTED_CROSS_DAUB);
        addDrop(PrimevalBlocks.FRAMED_X_DAUB);
        addDrop(PrimevalBlocks.FRAMED_PLUS_DAUB);
        addDrop(PrimevalBlocks.FRAMED_DIVIDED_DAUB);
        addDrop(PrimevalBlocks.MUD, drops(PrimevalItems.MUD_BALL, UniformLootNumberProvider.create(3.0f, 4.0f)));
        addDrop(PrimevalBlocks.MUD_BRICKS.block());
        addDrop(PrimevalBlocks.MUD_BRICKS.stairs());
        addDrop(PrimevalBlocks.MUD_BRICKS.slab(), BlockLootTableGenerator::slabDrops);
        addDrop(PrimevalBlocks.TERRACOTTA);
    }

    public static LootTable.Builder grassDrops(ItemConvertible drop) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(drop).conditionally(WITH_KNIFE).conditionally(RandomChanceLootCondition.builder(0.33f)))
                .with(ItemEntry.builder(drop).conditionally(RandomChanceLootCondition.builder(0.06f))));
    }

    public static LootTable.Builder stoneDrops(Block stone, Block cobblestone) {
        return PrimevalBlockLootTableProvider.dropsWithChisel(stone, BlockLootTableGenerator.addSurvivesExplosionCondition(stone, ItemEntry.builder(cobblestone)));
    }

    public static LootTable.Builder gravelDrops(Block block) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(block).weight(6))
                .with(ItemEntry.builder(PrimevalItems.FLINT).weight(1)));
    }

    //need fix
    public static LootTable.Builder leaveDrops(ItemConvertible sapling) {
        return PrimevalBlockLootTableProvider.dropsWithKnife(sapling, BlockLootTableGenerator.addSurvivesExplosionCondition(sapling, ItemEntry.builder(PrimevalItems.STICK).conditionally(RandomChanceLootCondition.builder(0.05f))));
    }

    //needs fix
    public static LootTable.Builder trunkDrops(Block log) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(log).conditionally(exactMatchBlockStateLootCondition(log, TrunkBlock.SIZE, 0))
                .alternatively(ItemEntry.builder(log).conditionally(exactMatchBlockStateLootCondition(log, TrunkBlock.SIZE, 1)).conditionally(RandomChanceLootCondition.builder(0.75f)))
                .alternatively(ItemEntry.builder(log).conditionally(exactMatchBlockStateLootCondition(log, TrunkBlock.SIZE, 2)).conditionally(RandomChanceLootCondition.builder(0.5f)))
                .alternatively(ItemEntry.builder(log).conditionally(exactMatchBlockStateLootCondition(log, TrunkBlock.SIZE, 3)).conditionally(RandomChanceLootCondition.builder(0.25f)))
        ));
    }

    public static LootTable.Builder campfireDrops() {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(PrimevalItems.ROCK).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))))
                .with(ItemEntry.builder(PrimevalItems.ASHES).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)))));
    }

    public static LootTable.Builder torchDrops(Block torch) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(torch).conditionally(exactMatchBlockStateLootCondition(torch, TimedTorchBlock.BURNOUT_STAGE, 0))
                .alternatively(ItemEntry.builder(PrimevalItems.STICK))
        ));
    }

    public static LootTable.Builder oreDrops(ItemConvertible ore) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(ore).conditionally(SurvivesExplosionLootCondition.builder()))
                .with(ItemEntry.builder(PrimevalBlocks.COBBLESTONE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))).conditionally(SurvivesExplosionLootCondition.builder())));
    }

    public static LootTable.Builder dropsWithChisel(Block drop, LootPoolEntry.Builder<?> child) {
        return PrimevalBlockLootTableProvider.drops(drop, WITH_CHISEL, child);
    }

    public static LootTable.Builder dropsWithKnife(ItemConvertible drop, LootPoolEntry.Builder<?> child) {
        return PrimevalBlockLootTableProvider.drops(drop, WITH_KNIFE, child);
    }

    public static LootTable.Builder dropsWithKnife(ItemConvertible drop) {
        return PrimevalBlockLootTableProvider.drops(drop, WITH_KNIFE);
    }

    public static LootTable.Builder drops(ItemConvertible drop, LootCondition.Builder conditionBuilder, LootPoolEntry.Builder<?> child) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).with(ItemEntry.builder(drop).conditionally(conditionBuilder).alternatively(child)));
    }

    public static LootTable.Builder drops(ItemConvertible drop, LootCondition.Builder conditionBuilder) {
        return LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).with(ItemEntry.builder(drop).conditionally(conditionBuilder)));
    }

    public static LootCondition.Builder exactMatchBlockStateLootCondition(Block drop, Property<Integer> property, int value) {
        return BlockStatePropertyLootCondition.builder(drop).properties(StatePredicate.Builder.create().exactMatch(property, value));
    }

    public static final LootCondition.Builder WITH_KNIFE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(PrimevalItemTags.KNIVES));
    public static final LootCondition.Builder WITH_CHISEL = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(PrimevalItemTags.CHISELS));
}
