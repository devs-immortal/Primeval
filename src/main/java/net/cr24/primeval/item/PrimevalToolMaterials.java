package net.cr24.primeval.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PrimevalToolMaterials {

    public static final float BLUNT_DAMAGE_MULTIPLIER = 0.5f;
    public static final float KNIFE_DAMAGE_MULTIPLIER = 1.5f;
    public static final float SWORD_DAMAGE_MULTIPLIER = 2f;

    public static final FlintMaterial FLINT = new FlintMaterial();

    public static class FlintMaterial implements ToolMaterial {
        @Override
        public int getDurability() {
            return 200;
        }
        @Override
        public float getMiningSpeedMultiplier() {
            return 2f;
        }
        @Override
        public float getAttackDamage() {
            return 1.0f;
        }
        @Override
        public int getMiningLevel() {
            return 0;
        }
        @Override
        public int getEnchantability() {
            return 2;
        }
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(PrimevalItems.FLINT);
        }
    }
}
