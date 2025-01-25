package net.cr24.primeval.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

public class PrimevalToolMaterials {

    public static final ToolMaterial FLINT = create(BlockTags.INCORRECT_FOR_STONE_TOOL, 200, 2f, 1f, 2, PrimevalItemTags.FLINT_TOOL_MATERIALS);
    public static final ToolMaterial COPPER = create(BlockTags.INCORRECT_FOR_IRON_TOOL, 600, 3f, 2f, 3, PrimevalItemTags.COPPER_TOOL_MATERIALS);
    public static final ToolMaterial BRONZE = create(BlockTags.INCORRECT_FOR_IRON_TOOL, 1000, 3.5f, 2f, 3, PrimevalItemTags.BBRONZE_TOOL_MATERIALS);

    public static ToolMaterial create(final TagKey<Block> incorrect, int itemDurability, float miningSpeed, float attackDamage, int enchantability, TagKey<Item> repairIngredient) {
        return new ToolMaterial(incorrect, itemDurability, miningSpeed, attackDamage, enchantability, repairIngredient);
    }

    public static final float BLUNT_DAMAGE_MULTIPLIER = 0.5f;
    public static final float KNIFE_DAMAGE_MULTIPLIER = 1.75f;
    public static final float SWORD_DAMAGE_MULTIPLIER = 2f;
    public static final float SPEAR_DAMAGE_MULTIPLIER = 3f;

}
