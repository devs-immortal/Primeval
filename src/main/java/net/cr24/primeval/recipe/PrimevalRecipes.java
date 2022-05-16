package net.cr24.primeval.recipe;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;
public class PrimevalRecipes {

    public static final RecipeType<PitKilnFiringRecipe> PIT_KILN_FIRING;
    public static final RecipeSerializer<PitKilnFiringRecipe> PIT_KILN_FIRING_SERIALIZER;
    public static final RecipeType<OpenFireRecipe> OPEN_FIRE;
    public static final RecipeSerializer<OpenFireRecipe> OPEN_FIRE_SERIALIZER;
    public static final RecipeType<MeltingRecipe> MELTING;
    public static final RecipeSerializer<MeltingRecipe> MELTING_SERIALIZER;
    public static final RecipeType<AlloyingRecipe> ALLOYING;
    public static final RecipeSerializer<AlloyingRecipe> ALLOYING_SERIALIZER;
    public static final RecipeSerializer<ClayMoldCastingRecipe> CLAY_MOLD_CASTING_SERIALIZER;

    static {
        PIT_KILN_FIRING = Registry.register(Registry.RECIPE_TYPE, PrimevalMain.getId("pit_kiln_firing"), new RecipeType<PitKilnFiringRecipe>() {
            @Override
            public String toString() {return "primeval:pit_kiln_firing";}
        });
        PIT_KILN_FIRING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("pit_kiln_firing"), new PitKilnFiringRecipe.Serializer());

        OPEN_FIRE = Registry.register(Registry.RECIPE_TYPE, PrimevalMain.getId("open_fire"), new RecipeType<OpenFireRecipe>() {
            @Override
            public String toString() {return "primeval:open_fire";}
        });
        OPEN_FIRE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("open_fire"), new OpenFireRecipe.Serializer());

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

        CLAY_MOLD_CASTING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, PrimevalMain.getId("clay_mold_casting"), new ClayMoldCastingRecipe.Serializer());


    }

    public static void init() {}

}
