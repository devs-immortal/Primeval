package net.cr24.primeval.initialization;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import net.cr24.primeval.block.*;
import net.cr24.primeval.item.WeightedBlockItem;
import net.cr24.primeval.util.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.biome.GrassColors;

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


    public static void init() {
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        // Render Layers
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                GRASSY_DIRT, GRASSY_CLAY
        );

        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D)),
                GRASSY_DIRT, GRASSY_CLAY
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

    // endregion

}
