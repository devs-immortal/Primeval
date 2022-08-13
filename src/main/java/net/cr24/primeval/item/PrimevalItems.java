package net.cr24.primeval.item;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.fluid.PrimevalFluidUtil;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.mixin.client.indigo.renderer.MixinItemRenderer;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static net.cr24.primeval.block.PrimevalBlocks.*;

@SuppressWarnings("unused")
public class PrimevalItems {

    /* Item Groups */
    public static final ItemGroup PRIMEVAL_ITEMS = FabricItemGroupBuilder.build(PrimevalMain.getId("items"), () -> new ItemStack(PrimevalItems.STRAW));
    public static final ItemGroup PRIMEVAL_BLOCKS = FabricItemGroupBuilder.build(PrimevalMain.getId("blocks"), () -> new ItemStack(PrimevalBlocks.DIRT));
    public static final ItemGroup PRIMEVAL_TOOLS = FabricItemGroupBuilder.create(
            PrimevalMain.getId("tools"))
            .icon(() -> new ItemStack(PrimevalItems.FLINT_AXE))
            .appendItems((stacks, itemGroup) -> {
                stacks.add(new ItemStack(Items.LEAD));
                for (Item item : Registry.ITEM) {
                    if (item.getGroup() == itemGroup) {
                        stacks.add(new ItemStack(item));
                    }
                }
            })
            .build();
    public static final ItemGroup PRIMEVAL_FOODS = FabricItemGroupBuilder.build(PrimevalMain.getId("foods"), () -> new ItemStack(PrimevalItems.FLINT_AXE));

    private static final Item.Settings GROUP_ITEMS = new Item.Settings().group(PRIMEVAL_ITEMS);
    private static final Item.Settings GROUP_TOOLS = new Item.Settings().group(PRIMEVAL_TOOLS);


    /* Items */
    // Crafting materials
    public static final Item STRAW = registerItem("straw", new WeightedBlockItem(STRAW_PILE, GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item STICK = registerItem("stick", new FirestarterItem(GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item STRING = registerItem("string", new FirestarterItem(GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item FLINT = registerItem("flint", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    public static final Item ROCK = registerItem("rock", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    public static final Item STONE_BRICK = registerItem("stone_brick", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    public static final Item ASHES = registerItem("ashes", new WeightedItem(GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item BONE = registerItem("bone", new WeightedItem(GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item ANIMAL_FAT = registerItem("animal_fat", new WeightedItem(GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));
    public static final Item GUNPOWDER = registerItem("gunpowder", new WeightedItem(GROUP_ITEMS, Weight.VERY_LIGHT, Size.SMALL));

    public static final Item SANDY_CLAY_BALL = registerItem("sandy_clay_ball", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item SANDY_CLAY_BRICK = registerItem("sandy_clay_brick", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item DRIED_BRICK = registerItem("dried_brick", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));

    public static final Item MUD_BALL = registerItem("mud_ball", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item MUD_BRICK = registerItem("mud_brick", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item CLAY_BALL = registerItem("clay_ball", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item CLAY_BRICK = registerItem("clay_brick", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item FIRED_CLAY_BRICK = registerItem("fired_clay_brick", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item CLAY_BOWL = registerItem("clay_bowl", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item FIRED_CLAY_BOWL = registerItem("fired_clay_bowl", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item CLAY_TILE = registerItem("clay_tile", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item FIRED_CLAY_TILE = registerItem("fired_clay_tile", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    //public static final Item CLAY_JUG = registerItem("clay_jug", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM)); // TODO: add
    public static final Item CLAY_VESSEL = registerItem("clay_vessel", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));

    // Edible Items
    public static final Item PORKCHOP = registerItem("porkchop", new WeightedItem(new Item.Settings().group(PRIMEVAL_FOODS).food(PrimevalFoodComponents.PORKCHOP), Weight.NORMAL, Size.MEDIUM));
    public static final Item COOKED_PORKCHOP = registerItem("cooked_porkchop", new WeightedItem(new Item.Settings().group(PRIMEVAL_FOODS).food(PrimevalFoodComponents.COOKED_PORKCHOP), Weight.NORMAL, Size.MEDIUM));
    public static final Item CARROT = registerItem("carrot", new WeightedItem(new Item.Settings().group(PRIMEVAL_FOODS).food(PrimevalFoodComponents.CARROT), Weight.LIGHT, Size.SMALL));
    public static final Item ROTTEN_FLESH = registerItem("rotten_flesh", new WeightedItem(new Item.Settings().group(PRIMEVAL_FOODS).food(PrimevalFoodComponents.ROTTEN_FLESH), Weight.NORMAL, Size.MEDIUM));
    public static final Item SPIDER_EYE = registerItem("spider_eye", new WeightedItem(new Item.Settings().group(PRIMEVAL_FOODS).food(PrimevalFoodComponents.SPIDER_EYE), Weight.LIGHT, Size.SMALL));

    // Ore Items
    public static final Item RAW_COPPER_MALACHITE_SMALL = registerItem("raw_copper_malachite_small", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item RAW_COPPER_MALACHITE_MEDIUM = registerItem("raw_copper_malachite_medium", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item RAW_COPPER_MALACHITE_LARGE = registerItem("raw_copper_malachite_large", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.LARGE));

    public static final Item RAW_COPPER_NATIVE_SMALL = registerItem("raw_copper_native_small", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item RAW_COPPER_NATIVE_MEDIUM = registerItem("raw_copper_native_medium", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item RAW_COPPER_NATIVE_LARGE = registerItem("raw_copper_native_large", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.LARGE));

    public static final Item RAW_TIN_CASSITERITE_SMALL = registerItem("raw_tin_cassiterite_small", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item RAW_TIN_CASSITERITE_MEDIUM = registerItem("raw_tin_cassiterite_medium", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item RAW_TIN_CASSITERITE_LARGE = registerItem("raw_tin_cassiterite_large", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.LARGE));

    public static final Item RAW_ZINC_SPHALERITE_SMALL = registerItem("raw_zinc_sphalerite_small", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item RAW_ZINC_SPHALERITE_MEDIUM = registerItem("raw_zinc_sphalerite_medium", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item RAW_ZINC_SPHALERITE_LARGE = registerItem("raw_zinc_sphalerite_large", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.LARGE));

    public static final Item RAW_IRON_HEMATITE_SMALL = registerItem("raw_iron_hematite_small", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.SMALL));
    public static final Item RAW_IRON_HEMATITE_MEDIUM = registerItem("raw_iron_hematite_medium", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item RAW_IRON_HEMATITE_LARGE = registerItem("raw_iron_hematite_large", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.LARGE));

    // Tools
    public static final Item FLINT_AXE = registerItem("flint_axe", new PrimevalAxeItem(PrimevalToolMaterials.FLINT, PrimevalToolMaterials.FLINT.getAttackDamage(), -3.0f, new Item.Settings().group(PRIMEVAL_TOOLS), Weight.HEAVY, Size.LARGE));
    public static final Item FLINT_KNIFE = registerItem("flint_knife", new PrimevalSwordItem(PrimevalToolMaterials.FLINT, PrimevalToolMaterials.FLINT.getAttackDamage()*PrimevalToolMaterials.KNIFE_DAMAGE_MULTIPLIER, -3.0f, new Item.Settings().group(PRIMEVAL_TOOLS), Weight.HEAVY, Size.LARGE));
    public static final Item FLINT_SHOVEL = registerItem("flint_shovel", new PrimevalShovelItem(PrimevalToolMaterials.FLINT, PrimevalToolMaterials.FLINT.getAttackDamage()*PrimevalToolMaterials.BLUNT_DAMAGE_MULTIPLIER, -3.0f, new Item.Settings().group(PRIMEVAL_TOOLS), Weight.HEAVY, Size.LARGE));
    public static final Item[] COPPER_TOOLS = registerToolSet("copper", PrimevalToolMaterials.COPPER, new Item.Settings().group(PRIMEVAL_TOOLS), Weight.HEAVY, Size.LARGE);
    public static final Item[] BRONZE_TOOLS = registerToolSet("bronze", PrimevalToolMaterials.BRONZE, new Item.Settings().group(PRIMEVAL_TOOLS), Weight.HEAVY, Size.LARGE);


    // Metal Items
    // Primary
    public static final Item COPPER_INGOT = registerItem("copper_ingot", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item COPPER_CHUNK = registerItem("copper_chunk", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    public static final Item TIN_INGOT = registerItem("tin_ingot", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item TIN_CHUNK = registerItem("tin_chunk", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    public static final Item ZINC_INGOT = registerItem("zinc_ingot", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item ZINC_CHUNK = registerItem("zinc_chunk", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    // Alloys
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item BRONZE_CHUNK = registerItem("bronze_chunk", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    public static final Item BRASS_INGOT = registerItem("brass_ingot", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item BRASS_CHUNK = registerItem("brass_chunk", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    public static final Item BOTCHED_ALLOY_INGOT = registerItem("botched_alloy_ingot", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item BOTCHED_ALLOY_CHUNK = registerItem("botched_alloy_chunk", new WeightedItem(GROUP_ITEMS, Weight.LIGHT, Size.SMALL));
    // Tool Parts
    public static final Item[] COPPER_TOOL_PARTS = registerToolPartSet("copper", GROUP_TOOLS, Weight.NORMAL, Size.MEDIUM);
    public static final Item[] BRONZE_TOOL_PARTS = registerToolPartSet("bronze", GROUP_TOOLS, Weight.NORMAL, Size.MEDIUM);


    // Molds
    public static final Item CLAY_INGOT_MOLD = registerItem("clay_mold_ingot", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item CLAY_AXE_HEAD_MOLD = registerItem("clay_mold_axe_head", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item CLAY_CHISEL_HEAD_MOLD = registerItem("clay_mold_chisel_head", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item CLAY_KNIFE_BLADE_MOLD = registerItem("clay_mold_knife_blade", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item CLAY_PICKAXE_HEAD_MOLD = registerItem("clay_mold_pickaxe_head", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item CLAY_SHOVEL_HEAD_MOLD = registerItem("clay_mold_shovel_head", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));
    public static final Item CLAY_SWORD_BLADE_MOLD = registerItem("clay_mold_sword_blade", new WeightedItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM));

    public static final List<Item> FIRED_MOLDS = new ArrayList<>();
    public static final Item FIRED_CLAY_INGOT_MOLD = registerMoldItem("fired_clay_mold_ingot", new ClayMoldItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM, 9000));
    public static final Item FIRED_CLAY_AXE_HEAD_MOLD = registerMoldItem("fired_clay_mold_axe_head", new ClayMoldItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM, 9000*3));
    public static final Item FIRED_CLAY_CHISEL_HEAD_MOLD = registerMoldItem("fired_clay_mold_chisel_head", new ClayMoldItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM, 9000*2));
    public static final Item FIRED_CLAY_KNIFE_BLADE_MOLD = registerMoldItem("fired_clay_mold_knife_blade", new ClayMoldItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM, 9000*2));
    public static final Item FIRED_CLAY_PICKAXE_HEAD_MOLD = registerMoldItem("fired_clay_mold_pickaxe_head", new ClayMoldItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM, 9000*3));
    public static final Item FIRED_CLAY_SHOVEL_HEAD_MOLD = registerMoldItem("fired_clay_mold_shovel_head", new ClayMoldItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM, 9000));
    public static final Item FIRED_CLAY_SWORD_BLADE_MOLD = registerMoldItem("fired_clay_mold_sword_blade", new ClayMoldItem(GROUP_ITEMS, Weight.NORMAL, Size.MEDIUM, 9000*2));


    // Utility items
    //public static final Item FIRED_CLAY_JUG = registerItem("fired_clay_jug", new JugItem(GROUP_TOOLS.maxDamage(0), Weight.NORMAL, Size.LARGE)); // TODO: add
    public static final Item FIRED_CLAY_VESSEL = registerItem("fired_clay_vessel", new VesselItem(new Item.Settings().group(PRIMEVAL_TOOLS).maxCount(1), Weight.NORMAL, Size.LARGE));



    private static Item registerItem(String id, Item item) {
        return Registry.register(Registry.ITEM, PrimevalMain.getId(id), item);
    }

    private static Item registerMoldItem(String id, Item item) {
        Item registered = registerItem(id, item);
        FIRED_MOLDS.add(registered);
        return registered;
    }


    public static void init() {
        PrimevalFoodComponents.init();
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        for (Item item : FIRED_MOLDS) {
            FabricModelPredicateProviderRegistry.register(item, new Identifier("fluid"), (itemStack, clientWorld, livingEntity, var4) -> {
                NbtCompound nbt = itemStack.getOrCreateNbt();
                NbtCompound fluidNbt = nbt.getCompound("Fluid");
                int fluidAmount = fluidNbt.getInt("Amount");
                if (fluidAmount == ((ClayMoldItem)itemStack.getItem()).getCapacity()) {
                    FluidVariant variant = FluidVariant.fromNbt(fluidNbt);
                    return PrimevalFluidUtil.fluidToIntegerId(variant.getFluid());
                }
                return 0;
            });
        }
    }

    private static Item[] registerToolPartSet(String material_id, Item.Settings group, Weight weight, Size size) {
        Item[] items = new Item[6];
        items[0] = registerItem(material_id+"_axe_head", new WeightedItem(group, weight, size));
        items[1] = registerItem(material_id+"_chisel_head", new WeightedItem(group, weight, size));
        items[2] = registerItem(material_id+"_knife_blade", new WeightedItem(group, weight, size));
        items[3] = registerItem(material_id+"_pickaxe_head", new WeightedItem(group, weight, size));
        items[4] = registerItem(material_id+"_shovel_head", new WeightedItem(group, weight, size));
        items[5] = registerItem(material_id+"_sword_blade", new WeightedItem(group, weight, size));
        return items;
    }

    private static Item[] registerToolSet(String material_id, ToolMaterial material, Item.Settings group, Weight weight, Size size) {
        Item[] items = new Item[6];
        items[0] = registerItem(material_id+"_axe", new PrimevalAxeItem(material, material.getAttackDamage(), -3.0f, group, weight, size));
        items[1] = registerItem(material_id+"_chisel", new ChiselItem(material, material.getAttackDamage()*PrimevalToolMaterials.BLUNT_DAMAGE_MULTIPLIER, -3.0f, group, weight, size));
        items[2] = registerItem(material_id+"_knife", new PrimevalSwordItem(material, material.getAttackDamage()*PrimevalToolMaterials.KNIFE_DAMAGE_MULTIPLIER, -3.0f, group, weight, size));
        items[3] = registerItem(material_id+"_pickaxe", new PrimevalPickaxeItem(material, material.getAttackDamage(), -3.0f, group, weight, size));
        items[4] = registerItem(material_id+"_shovel", new PrimevalShovelItem(material, material.getAttackDamage()*PrimevalToolMaterials.BLUNT_DAMAGE_MULTIPLIER, -3.0f, group, weight, size));
        items[5] = registerItem(material_id+"_sword", new PrimevalSwordItem(material, material.getAttackDamage()*PrimevalToolMaterials.SWORD_DAMAGE_MULTIPLIER, -2.5f, group, weight, size));
        return items;
    }

}
