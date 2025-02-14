package net.cr24.primeval.initialization;

import net.cr24.primeval.block.*;
import net.cr24.primeval.item.WeightedBlockItem;
import net.cr24.primeval.util.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Consumer;

import static net.cr24.primeval.PrimevalMain.identify;

/*
 * This class stores all blocks and fluids in the mod.
 */
public class PrimevalBlocks {

    // region SETTINGS
    private static AbstractBlock.Settings SETTINGS_SOIL() { return AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(2.1f, 2.0f).sounds(BlockSoundGroup.GRAVEL); }
    private static AbstractBlock.Settings SETTINGS_TOUGH_SOIL() { return AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(3f, 2.0f).sounds(BlockSoundGroup.GRAVEL);}
    private static AbstractBlock.Settings SETTINGS_GRASSY() { return AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GREEN).strength(2.5f, 2.0f).sounds(BlockSoundGroup.GRASS);}
    private static AbstractBlock.Settings SETTINGS_SAND() { return AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(1.8f, 2.0f).sounds(BlockSoundGroup.SAND);}
    private static AbstractBlock.Settings SETTINGS_STONE() { return AbstractBlock.Settings.create().mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 6.0f).requiresTool();}
    private static AbstractBlock.Settings SETTINGS_PLANT() { return AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.05f, 0f).sounds(BlockSoundGroup.GRASS).noCollision(); }
    private static AbstractBlock.Settings SETTINGS_CROP() { return AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(0.05f, 0f).sounds(BlockSoundGroup.GRASS).noCollision().ticksRandomly(); }
    private static AbstractBlock.Settings SETTINGS_LOG() { return AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(5.0f, 6.0f).sounds(BlockSoundGroup.WOOD).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_TRUNK() { return AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(8.0f, 8.0f).sounds(BlockSoundGroup.WOOD).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_FIRED_CLAY() { return AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).strength(4.0f, 6.0f).sounds(BlockSoundGroup.STONE).requiresTool(); }
    private static AbstractBlock.Settings SETTINGS_REFINED_WOOD() { return AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).strength(3.0f, 4.0f).sounds(BlockSoundGroup.WOOD).requiresTool(); }

    // endregion


    // region TERRAIN BLOCKS

//    public static final Block DIRT = registerBlock("dirt", new SemiSupportedBlock(SETTINGS_SOIL(), 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
//    public static final Block COARSE_DIRT = registerBlock("coarse_dirt", new SemiSupportedBlock(SETTINGS_SOIL(), 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
//    public static final Block CLAY_BLOCK = registerBlock("block_of_clay", new SemiSupportedBlock(SETTINGS_SOIL(), 0.3f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
//    public static final Block MUD = registerBlock("mud", new MudBlock(SETTINGS_SOIL().velocityMultiplier(0.4f), 0.25f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
//    public static final Block DRY_DIRT = registerBlock("dry_dirt", new SemiSupportedBlock(SETTINGS_TOUGH_SOIL().sounds(BlockSoundGroup.DRIPSTONE_BLOCK), 0.2f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
//    public static final Block GRASSY_DIRT = registerBlock("grassy_dirt", new GrassySoilBlock(SETTINGS_GRASSY().ticksRandomly(), 0.35f, PrimevalBlocks.DIRT, new Block[]{DIRT}), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
//    public static final Block GRASSY_CLAY = registerBlock("grassy_clay", new GrassySoilBlock(SETTINGS_GRASSY().ticksRandomly(), 0.45f, PrimevalBlocks.CLAY_BLOCK, new Block[]{CLAY_BLOCK}), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
//    public static final Block SAND = registerBlock("sand", new SemiSupportedBlock(SETTINGS_SAND(), 0.1f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
//    public static final Block GRAVEL = registerBlock("gravel", new SemiSupportedBlock(SETTINGS_SAND(), 0.1f), Weight.NORMAL, Size.MEDIUM, PRIMEVAL_BLOCKS);
    public static final Block COBBLESTONE = registerBlock("cobblestone", new SemiSupportedBlock(0.1f, SETTINGS_STONE().registryKey(blockKey("cobblestone")).strength(5.0f, 6.0f)), Weight.HEAVY, Size.MEDIUM);
    public static final Block STONE = registerBlock("stone", new CascadingBlock(0.35f, COBBLESTONE, SETTINGS_STONE().registryKey(blockKey("stone"))), Weight.HEAVY, Size.MEDIUM);
//    public static final Block SANDSTONE = registerBlock("sandstone", new CascadingBlock(SETTINGS_STONE(), 0.3f), Weight.HEAVY, Size.MEDIUM, PRIMEVAL_BLOCKS);
//    public static final Block DIRT_FARMLAND = registerBlockWithoutItem("farmland_dirt", new PrimevalFarmlandBlock(SETTINGS_SOIL().ticksRandomly(), 0.2f, DIRT, new Block[]{DIRT, GRASSY_DIRT}));
//    public static final Block CLAY_FARMLAND = registerBlockWithoutItem("farmland_clay", new PrimevalFarmlandBlock(SETTINGS_SOIL().ticksRandomly(), 0.3f, CLAY_BLOCK, new Block[]{CLAY_BLOCK}));

    // endregion


    public static void init() {
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
    }

    // region HELPER FUNCTIONS

    private static RegistryKey<Block> blockKey(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, identify(id));
    }

    private static RegistryKey<Item> itemKey(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, identify(id));
    }

    @SafeVarargs
    private static Block registerBlock(String id, Block block, Weight w, Size s, Consumer<Block>... additionalActions) {
        RegistryKey<Block> blockKey = blockKey(id);
        RegistryKey<Item> itemKey = itemKey(id);
        var registeredBlock = Registry.register(Registries.BLOCK, blockKey, block);
        Registry.register(Registries.ITEM, identify(id), new WeightedBlockItem(registeredBlock, w, s, new Item.Settings().registryKey(itemKey)));
        for (var action : additionalActions) {
            action.accept(registeredBlock);
        }
        return registeredBlock;
    }

    @SafeVarargs
    private static Block registerBlockWithoutItem(String id, Block block, Consumer<Block>... additionalActions) {
        var registeredBlock = Registry.register(Registries.BLOCK, identify(id), block);
        for (var action : additionalActions) {
            action.accept(registeredBlock);
        }
        return registeredBlock;
    }

    // endregion

}
