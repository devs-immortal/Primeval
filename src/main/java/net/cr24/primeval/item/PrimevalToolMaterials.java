package net.cr24.primeval.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PrimevalToolMaterials {

    public static final float BLUNT_DAMAGE_MULTIPLIER = 0.5f;
    public static final float KNIFE_DAMAGE_MULTIPLIER = 1.5f;
    public static final float SWORD_DAMAGE_MULTIPLIER = 2f;

    public static final FlintMaterial FLINT = new FlintMaterial();
    public static final CopperMaterial COPPER = new CopperMaterial();
    public static final BronzeMaterial BRONZE = new BronzeMaterial();

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

    public static class CopperMaterial implements ToolMaterial {
        @Override
        public int getDurability() {
            return 600;
        }
        @Override
        public float getMiningSpeedMultiplier() {
            return 3f;
        }
        @Override
        public float getAttackDamage() {
            return 1.5f;
        }
        @Override
        public int getMiningLevel() {
            return 1;
        }
        @Override
        public int getEnchantability() {
            return 3;
        }
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(PrimevalItems.COPPER_CHUNK);
        }
    }

    public static class BronzeMaterial implements ToolMaterial {
        @Override
        public int getDurability() {
            return 1000;
        }
        @Override
        public float getMiningSpeedMultiplier() {
            return 3.5f;
        }
        @Override
        public float getAttackDamage() {
            return 1.5f;
        }
        @Override
        public int getMiningLevel() {
            return 1;
        }
        @Override
        public int getEnchantability() {
            return 3;
        }
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(PrimevalItems.BRONZE_CHUNK);
        }
    }
}
