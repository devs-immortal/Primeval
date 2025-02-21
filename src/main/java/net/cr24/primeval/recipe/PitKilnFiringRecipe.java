package net.cr24.primeval.recipe;

import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;

public class PitKilnFiringRecipe extends SimpleOneToOneRecipe {
    public PitKilnFiringRecipe(Ingredient input, ItemStack result) {
        super(input, result);
    }

    @Override
    public RecipeSerializer<PitKilnFiringRecipe> getSerializer() {
        return PrimevalRecipes.PIT_KILN_FIRING_SERIALIZER;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(PrimevalItems.STRAW);
    }

    @Override
    public RecipeType<PitKilnFiringRecipe> getType() {
        return PrimevalRecipes.PIT_KILN_FIRING;
    }

}