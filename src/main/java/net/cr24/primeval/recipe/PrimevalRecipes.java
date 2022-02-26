package net.cr24.primeval.recipe;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.minecraft.fluid.Fluid;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class PrimevalRecipes {

    public static final RecipeType<PitKilnFiringRecipe> PIT_KILN_FIRING;
    public static final RecipeSerializer<PitKilnFiringRecipe> PIT_KILN_FIRING_SERIALIZER;
    public static final RecipeType<MeltingRecipe> MELTING;
    public static final RecipeSerializer<MeltingRecipe> MELTING_SERIALIZER;

    static {
        PIT_KILN_FIRING = Registry.register(Registry.RECIPE_TYPE, PrimevalMain.getId("pit_kiln_firing"), new RecipeType<PitKilnFiringRecipe>() {
            @Override
            public String toString() {return "pit_kiln_firing";}
        });
        PIT_KILN_FIRING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("pit_kiln_firing"), new PitKilnFiringRecipe.Serializer());

        MELTING = Registry.register(Registry.RECIPE_TYPE, PrimevalMain.getId("melting"), new RecipeType<MeltingRecipe>() {
            @Override
            public String toString() {return "melting";}
        });
        MELTING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("melting"), new MeltingRecipe.Serializer());

    }

    public static void init() {}

    public static Pair<Identifier, Integer> getAlloyFromFluids(HashMap<Identifier, Integer> fluids) {
        Pair<Identifier, Integer> defaultFluid = new Pair<>(Registry.FLUID.getId(PrimevalFluids.MOLTEN_BOTCHED_ALLOY), 100);
        return defaultFluid;
    }

}
