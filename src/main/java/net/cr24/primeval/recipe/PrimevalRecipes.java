package net.cr24.primeval.recipe;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

public class PrimevalRecipes {

    public static final RecipeType<PitKilnFiringRecipe> PIT_KILN_FIRING;
    public static final RecipeSerializer<PitKilnFiringRecipe> PIT_KILN_FIRING_SERIALIZER;

    static {
        PIT_KILN_FIRING = Registry.register(Registry.RECIPE_TYPE, PrimevalMain.getId("pit_kiln_firing"), new RecipeType<PitKilnFiringRecipe>() {
            @Override
            public String toString() {return "pit_kiln_firing";}
        });
        PIT_KILN_FIRING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("pit_kiln_firing"), new PitKilnFiringRecipe.Serializer());
    }

    public static void init() {}

    public static Pair<Fluid, Integer> getFluidFromItem(Item item) {
        System.out.println(item.getTranslationKey());
        Pair<Fluid, Integer> out = switch (item.getTranslationKey()) {
            default -> new Pair<Fluid, Integer>(Fluids.EMPTY, 0);
        };
        return out;
    }

}
