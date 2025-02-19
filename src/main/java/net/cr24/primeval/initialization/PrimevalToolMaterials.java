package net.cr24.primeval.initialization;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

public class PrimevalToolMaterials {

    public static final ToolMaterial FLINT = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 200, 2f, 1f, 2, PrimevalTags.Items.FLINT_TOOL_MATERIALS);
    public static final ToolMaterial COPPER = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 600, 3f, 2f, 3, PrimevalTags.Items.COPPER_TOOL_MATERIALS);
    public static final ToolMaterial BRONZE = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 1000, 3.5f, 2f, 3, PrimevalTags.Items.BRONZE_TOOL_MATERIALS);

    public static final float BLUNT_DAMAGE_MULTIPLIER = 0.5f;
    public static final float KNIFE_DAMAGE_MULTIPLIER = 1.75f;
    public static final float SWORD_DAMAGE_MULTIPLIER = 2f;
    public static final float SPEAR_DAMAGE_MULTIPLIER = 3f;

}
