package net.cr24.primeval.recipe;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.minecraft.fluid.Fluid;
import net.minecraft.recipe.ArmorDyeRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class PrimevalRecipes {

    public static final RecipeType<PitKilnFiringRecipe> PIT_KILN_FIRING;
    public static final RecipeSerializer<PitKilnFiringRecipe> PIT_KILN_FIRING_SERIALIZER;
    public static final RecipeType<MeltingRecipe> MELTING;
    public static final RecipeSerializer<MeltingRecipe> MELTING_SERIALIZER;
    public static final RecipeType<AlloyingRecipe> ALLOYING;
    public static final RecipeSerializer<AlloyingRecipe> ALLOYING_SERIALIZER;

    public static final RecipeType<ClayMoldBreakingRecipe> CLAY_MOLD_BREAKING_RECIPE;
    public static final SpecialRecipeSerializer<ClayMoldBreakingRecipe> CLAY_MOLD_BREAKING_RECIPE_SERIALIZER;

    static {
        PIT_KILN_FIRING = Registry.register(Registry.RECIPE_TYPE, PrimevalMain.getId("pit_kiln_firing"), new RecipeType<PitKilnFiringRecipe>() {
            @Override
            public String toString() {return "primeval:pit_kiln_firing";}
        });
        PIT_KILN_FIRING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("pit_kiln_firing"), new PitKilnFiringRecipe.Serializer());

        MELTING = Registry.register(Registry.RECIPE_TYPE, PrimevalMain.getId("melting"), new RecipeType<MeltingRecipe>() {
            @Override
            public String toString() {return "primeval:melting";}
        });
        MELTING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("melting"), new MeltingRecipe.Serializer());

        ALLOYING = Registry.register(Registry.RECIPE_TYPE, PrimevalMain.getId("alloying"), new RecipeType<AlloyingRecipe>() {
            @Override
            public String toString() {return "primeval:alloying";}
        });
        ALLOYING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("alloying"), new AlloyingRecipe.Serializer());

        CLAY_MOLD_BREAKING_RECIPE = Registry.register(Registry.RECIPE_TYPE, PrimevalMain.getId("clay_mold_breaking_recipe"), new RecipeType<ClayMoldBreakingRecipe>() {
            @Override
            public String toString() {return "primeval:clay_mold_breaking_recipe";}
        });
        CLAY_MOLD_BREAKING_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("clay_mold_breaking_recipe"), new SpecialRecipeSerializer<>(ClayMoldBreakingRecipe::new));

    }

    public static void init() {}

}
