package net.cr24.primeval.initialization;

import net.cr24.primeval.block.*;
import net.cr24.primeval.block.plant.*;
import net.cr24.primeval.item.WeightedBlockItem;
import net.cr24.primeval.util.*;
import net.cr24.primeval.world.trunker.BirchTrunker;
import net.cr24.primeval.world.trunker.OakTrunker;
import net.cr24.primeval.world.trunker.SpruceTrunker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
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

    public static final Block DIRT = registerBlock("dirt", SETTINGS_SOIL(), (settings) -> new SemiSupportedBlock(0.2f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block COARSE_DIRT = registerBlock("coarse_dirt", SETTINGS_SOIL(), (settings) -> new SemiSupportedBlock(0.2f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block CLAY = registerBlock("clay", SETTINGS_SOIL(), (settings) -> new SemiSupportedBlock(0.3f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block MUD = registerBlock("mud", SETTINGS_SOIL().velocityMultiplier(0.4f), (settings) -> new MuckBlock(0.25f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block DRY_DIRT = registerBlock("dry_dirt", SETTINGS_TOUGH_SOIL(), (settings) -> new SemiSupportedBlock(0.2f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block GRASSY_DIRT = registerBlock("grassy_dirt", SETTINGS_GRASSY().ticksRandomly(), (settings) -> new GrassySoilBlock(0.35f, PrimevalBlocks.DIRT, new Block[]{DIRT}, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block GRASSY_CLAY = registerBlock("grassy_clay", SETTINGS_GRASSY().ticksRandomly(), (settings) -> new GrassySoilBlock(0.45f, PrimevalBlocks.CLAY, new Block[]{CLAY}, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block SAND = registerBlock("sand", SETTINGS_SAND(), (settings) -> new SemiSupportedBlock(0.1f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block GRAVEL = registerBlock("gravel", SETTINGS_SAND(), (settings) -> new SemiSupportedBlock(0.1f, settings), Weight.NORMAL, Size.MEDIUM);
    public static final Block COBBLESTONE = registerBlock("cobblestone", SETTINGS_STONE().strength(5.0f, 6.0f), (settings) -> new SemiSupportedBlock(0.1f, settings), Weight.HEAVY, Size.MEDIUM);
    public static final Block STONE = registerBlock("stone", SETTINGS_STONE(), (settings) -> new CascadingBlock(0.35f, COBBLESTONE, settings), Weight.HEAVY, Size.MEDIUM);
    public static final Block SANDSTONE = registerBlock("sandstone", SETTINGS_STONE(), (settings) -> new CascadingBlock(0.3f, settings), Weight.HEAVY, Size.MEDIUM);
    public static final Block DIRT_FARMLAND = registerBlockWithoutItem("farmland_dirt", SETTINGS_SOIL().ticksRandomly(), (settings) -> new PrimevalFarmlandBlock(0.2f, DIRT, new Block[]{DIRT, GRASSY_DIRT}, settings));
    public static final Block CLAY_FARMLAND = registerBlockWithoutItem("farmland_clay", SETTINGS_SOIL().ticksRandomly(), (settings) -> new PrimevalFarmlandBlock(0.3f, CLAY, new Block[]{CLAY, GRASSY_CLAY}, settings));

    // endregion

    // region PLANT BLOCKS

    // Oak Trees
    public static final Block OAK_LOG = registerBlockWithoutItem("oak_log", SETTINGS_LOG(), PillarBlock::new);
    public static final Block OAK_TRUNK = registerBlockWithoutItem("oak_trunk", SETTINGS_TRUNK().nonOpaque(), (settings) -> new TrunkBlock(OakTrunker.INSTANCE, settings));
    public static final Block OAK_LEAVES = registerBlockWithoutItem("oak_leaves", AbstractBlock.Settings.copy(Blocks.OAK_LEAVES), LeafBlock::new);
    // Birch Trees
    public static final Block BIRCH_LOG = registerBlockWithoutItem("birch_log", SETTINGS_LOG(), PillarBlock::new);
    public static final Block BIRCH_TRUNK = registerBlockWithoutItem("birch_trunk", SETTINGS_TRUNK().nonOpaque(), (settings) -> new TrunkBlock(BirchTrunker.INSTANCE, settings));
    public static final Block BIRCH_LEAVES = registerBlockWithoutItem("birch_leaves", AbstractBlock.Settings.copy(Blocks.OAK_LEAVES), LeafBlock::new);
    // Spruce Trees
    public static final Block SPRUCE_LOG = registerBlockWithoutItem("spruce_log", SETTINGS_LOG(), PillarBlock::new);
    public static final Block SPRUCE_TRUNK = registerBlockWithoutItem("spruce_trunk", SETTINGS_TRUNK().nonOpaque(), (settings) -> new TrunkBlock(SpruceTrunker.INSTANCE, settings));
    public static final Block SPRUCE_LEAVES = registerBlockWithoutItem("spruce_leaves", AbstractBlock.Settings.copy(Blocks.OAK_LEAVES), LeafBlock::new);
    // Saplings+
    public static final Block OAK_SAPLING = registerBlock("oak_sapling", SETTINGS_PLANT().ticksRandomly(), (settings) -> new GrowingSaplingBlock(OakTrunker.INSTANCE, settings), Weight.HEAVY, Size.LARGE);
    public static final Block BIRCH_SAPLING = registerBlock("birch_sapling", SETTINGS_PLANT().ticksRandomly(), (settings) -> new GrowingSaplingBlock(BirchTrunker.INSTANCE, settings), Weight.HEAVY, Size.LARGE);
    public static final Block SPRUCE_SAPLING = registerBlock("spruce_sapling", SETTINGS_PLANT().ticksRandomly(), (settings) -> new GrowingSaplingBlock(SpruceTrunker.INSTANCE, settings), Weight.HEAVY, Size.LARGE);
    public static final Block GRASS = registerBlock("grass", SETTINGS_PLANT().ticksRandomly(), GrowingGrassBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block BUSH = registerBlock("bush", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block SPIKED_PLANT = registerBlock("plant_0", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block LEAFY_PLANT = registerBlock("plant_1", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block SHRUB = registerBlock("shrub", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.LIGHT, Size.MEDIUM);
    public static final Block MOSS = registerBlock("moss", SETTINGS_PLANT().ticksRandomly(), SpreadingMossBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    // Flowers
    public static final Block POPPY = registerBlock("poppy", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block DANDELION = registerBlock("dandelion", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block OXEYE_DAISY = registerBlock("oxeye_daisy", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block CORNFLOWER = registerBlock("cornflower", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block LILY_OF_THE_VALLEY = registerBlock("lily_of_the_valley", SETTINGS_PLANT(), PrimevalPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    // Misc
    public static final Block REEDS = registerBlock("reeds", SETTINGS_PLANT().ticksRandomly().pistonBehavior(PistonBehavior.DESTROY), ReedsBlock::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Block RIVER_GRASS = registerBlock("river_grass", SETTINGS_PLANT().offset(AbstractBlock.OffsetType.XZ), PrimevalWaterPlantBlock::new, Weight.VERY_LIGHT, Size.SMALL);

    // endregion

    // region ORES

    public static final OreBlockSet COPPER_MALACHITE_ORE = registerOreBlockSet("copper_malachite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet COPPER_NATIVE_ORE = registerOreBlockSet("copper_native_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet TIN_CASSITERITE_ORE = registerOreBlockSet("tin_cassiterite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet ZINC_SPHALERITE_ORE = registerOreBlockSet("zinc_sphalerite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet GOLD_NATIVE_ORE = registerOreBlockSet("gold_native_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet IRON_HEMATITE_ORE = registerOreBlockSet("iron_hematite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final OreBlockSet LAZURITE_ORE = registerOreBlockSet("lazurite_ore", SETTINGS_STONE(), Weight.HEAVY, Size.LARGE);
    public static final Block FOSSIL = registerBlock("fossil", SETTINGS_STONE(), (settings) -> new SemiSupportedBlock(0.35f, COBBLESTONE, settings), Weight.HEAVY, Size.LARGE);

    // endregion




    public static void init() {
        OakTrunker.INSTANCE.build();
        BirchTrunker.INSTANCE.build();
        SpruceTrunker.INSTANCE.build();
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        // Render Layers
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                GRASSY_DIRT, GRASSY_CLAY,
                GRASS, BUSH, SPIKED_PLANT, LEAFY_PLANT, SHRUB,
                POPPY, DANDELION, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY,
                MOSS,
                REEDS, RIVER_GRASS,
                /* Tree */
                OAK_SAPLING,
                OAK_LEAVES,
                BIRCH_SAPLING,
                BIRCH_LEAVES,
                SPRUCE_SAPLING,
                SPRUCE_LEAVES
        );

        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.7D, 1.0D)),
                GRASSY_DIRT, GRASSY_CLAY,
                GRASS, BUSH, SPIKED_PLANT, LEAFY_PLANT, SHRUB
        );
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getColor(0.5D, 1.0D)),
                OAK_LEAVES
        );
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos)+2621440 : FoliageColors.getColor(0.5D, 1.0D)),
                BIRCH_LEAVES
        );
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos)-4082973 : FoliageColors.getColor(0.5D, 1.0D)),
                SPRUCE_LEAVES
        );

        // Block Renderers
//        BlockEntityRendererRegistry.register(PIT_KILN_BLOCK_ENTITY, PitKilnBlockEntityRenderer::new);
//        BlockEntityRendererRegistry.register(ASH_PILE_BLOCK_ENTITY, AshPileBlockEntityRenderer::new);
//        BlockEntityRendererRegistry.register(LAYING_ITEM_BLOCK_ENTITY, LayingItemBlockEntityRenderer::new);
//        BlockEntityRendererRegistry.register(CAMPFIRE_BLOCK_ENTITY, PrimevalCampfireBlockEntityRenderer::new);
//        BlockEntityRendererRegistry.register(QUERN_BLOCK_ENTITY, QuernBlockEntityRenderer::new);

//        ScreenRegistry.register(CRATE_SCREEN_HANDLER, Primeval3x5ContainerScreen::new);
    }

    // region HELPER FUNCTIONS

    private static RegistryKey<Block> blockKey(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, identify(id));
    }

    @SafeVarargs
    private static Block registerBlock(String id, AbstractBlock.Settings settings, BlockFactory<Block> factory, Weight w, Size s, Consumer<Block>... additionalActions) {
        RegistryKey<Block> blockKey = blockKey(id);
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
        var registeredBlock = Registry.register(Registries.BLOCK, blockKey, factory.create(settings.registryKey(blockKey)));
        Registry.register(Registries.ITEM, identify(id), new WeightedBlockItem(registeredBlock, w, s, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey()));
        for (var action : additionalActions) {
            action.accept(registeredBlock);
        }
        return registeredBlock;
    }

    @SafeVarargs
    private static Block registerBlockWithoutItem(String id, AbstractBlock.Settings settings, BlockFactory<Block> factory, Consumer<Block>... additionalActions) {
        RegistryKey<Block> blockKey = blockKey(id);
        var registeredBlock = Registry.register(Registries.BLOCK, blockKey, factory.create(settings.registryKey(blockKey)));
        for (var action : additionalActions) {
            action.accept(registeredBlock);
        }
        return registeredBlock;
    }

    @FunctionalInterface
    public interface BlockFactory<T extends Block> {
        T create(AbstractBlock.Settings settings);
    }

    // records

    private static OreBlockSet registerOreBlockSet(String ore_id, AbstractBlock.Settings s, Weight weight, Size size) {
        return new OreBlockSet(
                registerBlock(ore_id + "_small", s, (settings) -> new SemiSupportedBlock(0.35f, COBBLESTONE, settings), weight, size),
                registerBlock(ore_id + "_medium", s, (settings) -> new SemiSupportedBlock(0.35f, COBBLESTONE, settings), weight, size),
                registerBlock(ore_id + "_large", s, (settings) -> new SemiSupportedBlock(0.35f, COBBLESTONE, settings), weight, size)
        );
    }
    public record OreBlockSet(Block small, Block medium, Block large) {
        public @NotNull Iterator<Block> iterator() {
            return Arrays.stream(new Block[]{small, medium, large}).iterator();
        }
    }

    // endregion

}
