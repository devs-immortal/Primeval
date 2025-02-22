package net.cr24.primeval.initialization;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.block.PrimevalFarmlandBlock;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.cr24.primeval.item.*;
import net.cr24.primeval.item.tool.*;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.block.*;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static net.cr24.primeval.Primeval.identify;
import static net.cr24.primeval.initialization.PrimevalItemActions.*;

public class PrimevalItems {

    private static Item.Settings SETTINGS_BASIC() { return new Item.Settings(); }

    // region CRAFTING MATERIALS
    // basic
    public static final Item STRAW = registerItem("straw", SETTINGS_BASIC(), (w, s, settings) -> new WeightedBlockItem(PrimevalBlocks.STRAW_PILE, w, s, settings), Weight.VERY_LIGHT, Size.SMALL);
    public static final Item STICK = registerItem("stick", SETTINGS_BASIC(), FirestarterItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item STRING = registerItem("string", SETTINGS_BASIC(), FirestarterItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item FLINT = registerItem("flint", SETTINGS_BASIC(), FlintItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item ROCK = registerItem("rock", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item STONE_BRICK = registerItem("stone_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item ASHES = registerItem("ashes", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item CRUSHED_TERRACOTTA = registerItem("crushed_terracotta", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CEMENT_MIX = registerItem("cement_mix", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CEMENT = registerItem("cement", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item BONE = registerItem("bone", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item BONEMEAL = registerItem("bonemeal", SETTINGS_BASIC(), (w, s, settings) -> new FertilizerItem(4, PrimevalFarmlandBlock.PrimevalFarmlandBlockFertilizerType.BONEMEAL, w, s, settings), Weight.VERY_LIGHT, Size.SMALL);
    public static final Item ANIMAL_FAT = registerItem("animal_fat", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item GUNPOWDER = registerItem("gunpowder", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);
    public static final Item CHARRED_BONE = registerItem("charred_bone", SETTINGS_BASIC(), WeightedItem::new, Weight.VERY_LIGHT, Size.SMALL);

    public static final Item SANDY_CLAY_BALL = registerItem("sandy_clay_ball", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item SANDY_CLAY_BRICK = registerItem("sandy_clay_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item DRIED_BRICK = registerItem("dried_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item MUD_BALL = registerItem("mud_ball", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item MUD_BRICK = registerItem("mud_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    // clay items
    public static final Item CLAY_BALL = registerItem("clay_ball", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CLAY_BRICK = registerItem("clay_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item FIRED_CLAY_BRICK = registerItem("fired_clay_brick", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CLAY_BOWL = registerItem("clay_bowl", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item FIRED_CLAY_BOWL = registerItem("fired_clay_bowl", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CLAY_TILE = registerItem("clay_tile", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item FIRED_CLAY_TILE = registerItem("fired_clay_tile", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item CLAY_JUG = registerItem("clay_jug", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item CLAY_VESSEL = registerItem("clay_vessel", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);

    // logs
    public static final Item OAK_LOG = registerItem("oak_log", SETTINGS_BASIC(), (w, s, settings) -> new LogItem(PrimevalBlocks.OAK_LOG_BLOCK, PrimevalBlocks.OAK_LOG_PILE, w, s, settings), Weight.HEAVY, Size.LARGE);
    public static final Item BIRCH_LOG = registerItem("birch_log", SETTINGS_BASIC(), (w, s, settings) -> new LogItem(PrimevalBlocks.BIRCH_LOG_BLOCK, PrimevalBlocks.BIRCH_LOG_PILE, w, s, settings), Weight.HEAVY, Size.LARGE);
    public static final Item SPRUCE_LOG = registerItem("spruce_log", SETTINGS_BASIC(), (w, s, settings) -> new LogItem(PrimevalBlocks.SPRUCE_LOG_BLOCK, PrimevalBlocks.SPRUCE_LOG_PILE, w, s, settings), Weight.HEAVY, Size.LARGE);



    // endregion

    // region FOODSTUFF

    public static final Item PORKCHOP = registerItem("porkchop", SETTINGS_BASIC().food(foodComponent(2, 0.3f),
            consumableComponent(new Consumable(StatusEffects.HUNGER, 600, 0, 0.3f))), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item COOKED_PORKCHOP = registerItem("cooked_porkchop", SETTINGS_BASIC().food(foodComponent(8, 0.8f)), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item ROTTEN_FLESH = registerItem("rotten_flesh", SETTINGS_BASIC().food(foodComponent(4, 0.1f),
            consumableComponent(new Consumable(StatusEffects.HUNGER, 300, 0, 0.8f), new Consumable(StatusEffects.POISON, 100, 0, 0.8f))), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item SPIDER_EYE = registerItem("spider_eye", SETTINGS_BASIC().food(foodComponent(3, 0.1f),
            consumableComponent(new Consumable(StatusEffects.HUNGER, 100, 0, 0.4f), new Consumable(StatusEffects.POISON, 300, 1, 0.9f))), WeightedItem::new, Weight.LIGHT, Size.SMALL);

    public static final Item CARROT = registerItem("carrot", SETTINGS_BASIC().food(foodComponent(4, 1f)), (w, s, settings) -> new WeightedBlockItem(PrimevalBlocks.CARROT_CROP, w, s, settings), Weight.LIGHT, Size.SMALL);
    public static final Item WHEAT = registerItem("wheat", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item CABBAGE = registerItem("cabbage", SETTINGS_BASIC().food(foodComponent(2, 4f)), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item BEANS = registerItem("beans", SETTINGS_BASIC().food(foodComponent(1, 2f)), (w, s, settings) -> new WeightedBlockItem(PrimevalBlocks.BEANS_CROP, w, s, settings), Weight.LIGHT, Size.SMALL);
    public static final Item POTATO = registerItem("potato", SETTINGS_BASIC().food(foodComponent(3, 0.2f)), (w, s, settings) -> new WeightedBlockItem(PrimevalBlocks.POTATO_CROP, w, s, settings), Weight.LIGHT, Size.SMALL);
    // Seeds
    public static final Item WHEAT_SEEDS = registerItem("wheat_seeds", SETTINGS_BASIC(), (w, s, settings) -> new WeightedBlockItem(PrimevalBlocks.WHEAT_CROP, w, s, settings), Weight.LIGHT, Size.SMALL);
    public static final Item CABBAGE_SEEDS = registerItem("cabbage_seeds", SETTINGS_BASIC(), (w, s, settings) -> new WeightedBlockItem(PrimevalBlocks.CABBAGE_CROP, w, s, settings), Weight.LIGHT, Size.SMALL);

    // endregion

    // region ORE CHUNKS

    public static final Item RAW_COPPER_MALACHITE_SMALL = registerItem("raw_copper_malachite_small", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item RAW_COPPER_MALACHITE_MEDIUM = registerItem("raw_copper_malachite_medium", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item RAW_COPPER_MALACHITE_LARGE = registerItem("raw_copper_malachite_large", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.LARGE);

    public static final Item RAW_COPPER_NATIVE_SMALL = registerItem("raw_copper_native_small", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item RAW_COPPER_NATIVE_MEDIUM = registerItem("raw_copper_native_medium", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item RAW_COPPER_NATIVE_LARGE = registerItem("raw_copper_native_large", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.LARGE);

    public static final Item RAW_TIN_CASSITERITE_SMALL = registerItem("raw_tin_cassiterite_small", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item RAW_TIN_CASSITERITE_MEDIUM = registerItem("raw_tin_cassiterite_medium", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item RAW_TIN_CASSITERITE_LARGE = registerItem("raw_tin_cassiterite_large", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.LARGE);

    public static final Item RAW_ZINC_SPHALERITE_SMALL = registerItem("raw_zinc_sphalerite_small", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item RAW_ZINC_SPHALERITE_MEDIUM = registerItem("raw_zinc_sphalerite_medium", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item RAW_ZINC_SPHALERITE_LARGE = registerItem("raw_zinc_sphalerite_large", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.LARGE);

    public static final Item RAW_GOLD_NATIVE_SMALL = registerItem("raw_gold_native_small", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item RAW_GOLD_NATIVE_MEDIUM = registerItem("raw_gold_native_medium", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item RAW_GOLD_NATIVE_LARGE = registerItem("raw_gold_native_large", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.LARGE);

    public static final Item RAW_IRON_HEMATITE_SMALL = registerItem("raw_iron_hematite_small", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item RAW_IRON_HEMATITE_MEDIUM = registerItem("raw_iron_hematite_medium", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item RAW_IRON_HEMATITE_LARGE = registerItem("raw_iron_hematite_large", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.LARGE);

    public static final Item RAW_LAZURITE_SMALL = registerItem("raw_lazurite_small", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item RAW_LAZURITE_MEDIUM = registerItem("raw_lazurite_medium", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item RAW_LAZURITE_LARGE = registerItem("raw_lazurite_large", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.LARGE);
    
    // endregion

    // region TOOLS+

    public static final Item FLINT_AXE = registerItem("flint_axe", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalAxeItem(PrimevalToolMaterials.FLINT, 1.0f, -3.0f, w, s, settings), Weight.HEAVY, Size.LARGE);
    public static final Item FLINT_KNIFE = registerItem("flint_knife", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalKnifeItem(PrimevalToolMaterials.FLINT, PrimevalToolMaterials.KNIFE_DAMAGE_MULTIPLIER, -3.0f, w, s, settings), Weight.HEAVY, Size.LARGE);
    public static final Item FLINT_SHOVEL = registerItem("flint_shovel", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalShovelItem(PrimevalToolMaterials.FLINT, PrimevalToolMaterials.BLUNT_DAMAGE_MULTIPLIER, -3.0f, w, s, settings), Weight.HEAVY, Size.LARGE);
    public static final Item FLINT_SPEAR = registerItem("flint_spear", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalSpearItem(PrimevalToolMaterials.FLINT, PrimevalToolMaterials.SPEAR_DAMAGE_MULTIPLIER, -3.5f, w, s, settings), Weight.HEAVY, Size.LARGE);
    public static final ToolSet COPPER_TOOLS = ToolSet.from("copper", PrimevalToolMaterials.COPPER,1.5f);
    public static final ToolSet BRONZE_TOOLS = ToolSet.from("bronze", PrimevalToolMaterials.BRONZE, 1.5f);

    // Tool Parts
    public static final ToolPartSet COPPER_TOOL_PARTS = ToolPartSet.from("copper");
    public static final ToolPartSet BRONZE_TOOL_PARTS = ToolPartSet.from("bronze");

    // Other
    public static final Item WOODEN_BUCKET = registerItem("wooden_bucket", SETTINGS_BASIC(), WoodenBucketItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item WOODEN_BUCKET_WATER = registerItem("wooden_bucket_water", SETTINGS_BASIC().recipeRemainder(WOODEN_BUCKET), WaterWoodenBucketItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item FIRED_CLAY_JUG = registerItem("fired_clay_jug", SETTINGS_BASIC(), EmptyJugItem::new, Weight.NORMAL, Size.LARGE);
    public static final Item FIRED_CLAY_WATER_JUG = registerItem("fired_clay_jug_filled", SETTINGS_BASIC().recipeRemainder(FIRED_CLAY_JUG).food(foodComponent(0, 0f, true), ConsumableComponents.drink().build()).useRemainder(FIRED_CLAY_JUG), WeightedItem::new, Weight.NORMAL, Size.LARGE);
    public static final Item FIRED_CLAY_VESSEL = registerItem("fired_clay_vessel", SETTINGS_BASIC(), VesselItem::new, Weight.NORMAL, Size.LARGE);
    public static final Item QUERN_WHEEL = registerItem("quern_wheel", SETTINGS_BASIC().maxDamage(99), (w, s, settings) -> new WeightedItem(w, s, 1, settings), Weight.HEAVY, Size.MEDIUM);

    // Molds
    public static final Item CLAY_INGOT_MOLD = registerItem("clay_mold_ingot", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item CLAY_AXE_HEAD_MOLD = registerItem("clay_mold_axe_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item CLAY_CHISEL_HEAD_MOLD = registerItem("clay_mold_chisel_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item CLAY_KNIFE_BLADE_MOLD = registerItem("clay_mold_knife_blade", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item CLAY_PICKAXE_HEAD_MOLD = registerItem("clay_mold_pickaxe_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item CLAY_SHOVEL_HEAD_MOLD = registerItem("clay_mold_shovel_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item CLAY_SWORD_BLADE_MOLD = registerItem("clay_mold_sword_blade", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item CLAY_HOE_HEAD_MOLD = registerItem("clay_mold_hoe_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);

    public static final List<Item> FIRED_MOLDS = new ArrayList<>(8);
    public static final Item FIRED_CLAY_INGOT_MOLD = registerMold("ingot", Weight.NORMAL, Size.MEDIUM, PrimevalTags.Fluids.ALL_MOLD_FLUIDS, 9000);
    public static final Item FIRED_CLAY_AXE_HEAD_MOLD = registerMold("axe_head", Weight.NORMAL, Size.MEDIUM, PrimevalTags.Fluids.TOOL_MOLD_FLUIDS, 9000 * 3);
    public static final Item FIRED_CLAY_CHISEL_HEAD_MOLD = registerMold("chisel_head", Weight.NORMAL, Size.MEDIUM, PrimevalTags.Fluids.TOOL_MOLD_FLUIDS, 9000 * 2);
    public static final Item FIRED_CLAY_KNIFE_BLADE_MOLD = registerMold("knife_blade", Weight.NORMAL, Size.MEDIUM, PrimevalTags.Fluids.TOOL_MOLD_FLUIDS, 9000 * 2);
    public static final Item FIRED_CLAY_PICKAXE_HEAD_MOLD = registerMold("pickaxe_head", Weight.NORMAL, Size.MEDIUM, PrimevalTags.Fluids.TOOL_MOLD_FLUIDS, 9000 * 3);
    public static final Item FIRED_CLAY_SHOVEL_HEAD_MOLD = registerMold("shovel_head", Weight.NORMAL, Size.MEDIUM, PrimevalTags.Fluids.TOOL_MOLD_FLUIDS, 9000);
    public static final Item FIRED_CLAY_SWORD_BLADE_MOLD = registerMold("sword_blade", Weight.NORMAL, Size.MEDIUM, PrimevalTags.Fluids.TOOL_MOLD_FLUIDS, 9000 * 2);
    public static final Item FIRED_CLAY_HOE_HEAD_MOLD = registerMold("hoe_head", Weight.NORMAL, Size.MEDIUM, PrimevalTags.Fluids.TOOL_MOLD_FLUIDS, 9000 * 2);

    // endregion

    // region METALS

    // Primary
    public static final Item COPPER_INGOT = registerItem("copper_ingot", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item COPPER_CHUNK = registerItem("copper_chunk", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item TIN_INGOT = registerItem("tin_ingot", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item TIN_CHUNK = registerItem("tin_chunk", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item ZINC_INGOT = registerItem("zinc_ingot", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item ZINC_CHUNK = registerItem("zinc_chunk", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    // Alloys
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item BRONZE_CHUNK = registerItem("bronze_chunk", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item BRASS_INGOT = registerItem("brass_ingot", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item BRASS_CHUNK = registerItem("brass_chunk", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item PEWTER_INGOT = registerItem("pewter_ingot", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item PEWTER_CHUNK = registerItem("pewter_chunk", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item GOLD_INGOT = registerItem("gold_ingot", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item GOLD_CHUNK = registerItem("gold_chunk", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item BOTCHED_ALLOY_INGOT = registerItem("botched_alloy_ingot", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM);
    public static final Item BOTCHED_ALLOY_CHUNK = registerItem("botched_alloy_chunk", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    
    // endregion

    // region MISC

    // Dyes
    public static final Item WHITE_DYE = registerItem("dye_white", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item ORANGE_DYE = registerItem("dye_orange", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item MAGENTA_DYE = registerItem("dye_magenta", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item LIGHT_BLUE_DYE = registerItem("dye_light_blue", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item YELLOW_DYE = registerItem("dye_yellow", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item LIME_DYE = registerItem("dye_lime", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item PINK_DYE = registerItem("dye_pink", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item DARK_GRAY_DYE = registerItem("dye_dark_gray", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item LIGHT_GRAY_DYE = registerItem("dye_light_gray", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item CYAN_DYE = registerItem("dye_cyan", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item PURPLE_DYE = registerItem("dye_purple", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item BLUE_DYE = registerItem("dye_blue", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item BROWN_DYE = registerItem("dye_brown", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item GREEN_DYE = registerItem("dye_green", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item RED_DYE = registerItem("dye_red", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    public static final Item BLACK_DYE = registerItem("dye_black", SETTINGS_BASIC(), WeightedItem::new, Weight.LIGHT, Size.SMALL);
    
    // Currency
    public static final Item COPPER_COIN = registerItem("copper_coin", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);
    public static final Item GOLD_COIN = registerItem("gold_coin", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.SMALL);

    // endregion


    public static void init() {
    }

    @SafeVarargs
    private static <T extends Item> T registerItem(String id, Item.Settings settings, ItemFactory<T> factory, Weight w, Size s, Consumer<Block>... additionalActions) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Primeval.identify(id));
        return Registry.register(Registries.ITEM, identify(id), factory.create(w, s, settings.registryKey(itemKey)));
    }

    private static Item registerMold(String formId, Weight weight, Size size, TagKey<Fluid> validFluids, int amount) {
        Item registered = registerItem("fired_clay_mold_" + formId, SETTINGS_BASIC(), (w, s, settings) -> new MoldItem(w, s, amount, validFluids, settings), weight, size);
        FIRED_MOLDS.add(registered);
        return registered;
    }

    @FunctionalInterface
    public interface ItemFactory<T extends Item> {
        T create(Weight weight, Size size, Item.Settings settings);
    }

    public record ToolSet(PrimevalAxeItem axe, ChiselItem chisel, PrimevalKnifeItem knife, PrimevalPickaxeItem pickaxe, PrimevalShovelItem shovel, PrimevalSwordItem sword, PrimevalHoeItem hoe, PrimevalSpearItem spear) implements Iterable<Item> {
        public @NotNull Iterator<Item> iterator() {
            return Arrays.stream(new Item[]{axe, chisel, knife, pickaxe, shovel, sword, hoe, spear}).iterator();
        }

        public static ToolSet from(String material_id, ToolMaterial material, float attackDamage) {
            return new ToolSet(
                    registerItem(material_id + "_axe", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalAxeItem(material, attackDamage, -3.0f, w, s, settings), Weight.HEAVY, Size.LARGE),
                    registerItem(material_id + "_chisel", SETTINGS_BASIC(), (w, s, settings) -> new ChiselItem(material, attackDamage * PrimevalToolMaterials.BLUNT_DAMAGE_MULTIPLIER, -3.0f, w, s, settings), Weight.HEAVY, Size.MEDIUM),
                    registerItem(material_id + "_knife", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalKnifeItem(material, attackDamage * PrimevalToolMaterials.KNIFE_DAMAGE_MULTIPLIER, -1.5f, w, s, settings), Weight.HEAVY, Size.MEDIUM),
                    registerItem(material_id + "_pickaxe", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalPickaxeItem(material, attackDamage, -3.0f, w, s, settings), Weight.HEAVY, Size.LARGE),
                    registerItem(material_id + "_shovel", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalShovelItem(material, attackDamage * PrimevalToolMaterials.BLUNT_DAMAGE_MULTIPLIER, -3.0f, w, s, settings), Weight.HEAVY, Size.LARGE),
                    registerItem(material_id + "_sword", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalSwordItem(material, attackDamage * PrimevalToolMaterials.SWORD_DAMAGE_MULTIPLIER, -2.5f, w, s, settings), Weight.HEAVY, Size.LARGE),
                    registerItem(material_id + "_hoe", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalHoeItem(material, attackDamage * PrimevalToolMaterials.BLUNT_DAMAGE_MULTIPLIER, -3.0f, w, s, settings), Weight.HEAVY, Size.LARGE),
                    registerItem(material_id + "_spear", SETTINGS_BASIC(), (w, s, settings) -> new PrimevalSpearItem(material, attackDamage * PrimevalToolMaterials.SPEAR_DAMAGE_MULTIPLIER, -3.5f, w, s, settings), Weight.HEAVY, Size.LARGE)
            );

        }
    }

    public record ToolPartSet(WeightedItem axe_head, WeightedItem chisel_head, WeightedItem knife_blade, WeightedItem pickaxe_head, WeightedItem shovel_head, WeightedItem sword_blade, WeightedItem hoe_head) implements Iterable<Item> {
        public @NotNull Iterator<Item> iterator() {
            return Arrays.stream(new Item[]{axe_head, chisel_head, knife_blade, pickaxe_head, shovel_head, sword_blade, hoe_head}).iterator();
        }

        public static ToolPartSet from(String material_id) {
            return new ToolPartSet(
                    registerItem(material_id + "_axe_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM),
                    registerItem(material_id + "_chisel_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM),
                    registerItem(material_id + "_knife_blade", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM),
                    registerItem(material_id + "_pickaxe_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM),
                    registerItem(material_id + "_shovel_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM),
                    registerItem(material_id + "_sword_blade", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM),
                    registerItem(material_id + "_hoe_head", SETTINGS_BASIC(), WeightedItem::new, Weight.NORMAL, Size.MEDIUM)
            );

        }
    }


}
