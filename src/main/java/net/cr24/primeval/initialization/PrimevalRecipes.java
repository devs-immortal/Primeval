package net.cr24.primeval.initialization;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.recipe.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class PrimevalRecipes {

    public static final RecipeType<PitKilnFiringRecipe> PIT_KILN_FIRING;
    public static final RecipeSerializer<PitKilnFiringRecipe> PIT_KILN_FIRING_SERIALIZER;
//    public static final RecipeType<OpenFireRecipe> OPEN_FIRE;
//    public static final RecipeSerializer<OpenFireRecipe> OPEN_FIRE_SERIALIZER;
//    public static final RegistryKey<RecipePropertySet> OPEN_FIRE_INPUT;
    public static final RecipeType<MeltingRecipe> MELTING;
    public static final RecipeSerializer<MeltingRecipe> MELTING_SERIALIZER;
    public static final RecipeType<AlloyingRecipe> ALLOYING;
    public static final RecipeSerializer<AlloyingRecipe> ALLOYING_SERIALIZER;
    public static final RecipeType<QuernRecipe> QUERN_GRINDING;
    public static final RecipeSerializer<QuernRecipe> QUERN_GRINDING_SERIALIZER;
    public static final RegistryKey<RecipePropertySet> QUERN_GRINDING_INPUT;

    public static final RecipeType<MoldCastingRecipe> MOLD_CASTING;
    public static final RecipeSerializer<MoldCastingRecipe> MOLD_CASTING_SERIALIZER;

    public static final RecipeType<ItemDamagingRecipe> ITEM_DAMAGING;
    public static final RecipeSerializer<ItemDamagingRecipe> ITEM_DAMAGING_SERIALIZER;


    static {
        PIT_KILN_FIRING = Registry.register(Registries.RECIPE_TYPE, Primeval.identify("pit_kiln_firing"), new RecipeType<PitKilnFiringRecipe>() {
            @Override
            public String toString() {return "primeval:pit_kiln_firing";}
        });
        PIT_KILN_FIRING_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Primeval.identify("pit_kiln_firing"), new SimpleOneToOneRecipe.Serializer<>(PitKilnFiringRecipe::new));

//        OPEN_FIRE = Registry.register(Registries.RECIPE_TYPE, PrimevalMain.getId("open_fire"), new RecipeType<OpenFireRecipe>() {
//            @Override
//            public String toString() {return "primeval:open_fire";}
//        });
//        OPEN_FIRE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, PrimevalMain.getId("open_fire"), new OpenFireRecipe.Serializer());
//        OPEN_FIRE_INPUT = RegistryKey.of(RegistryKey.ofRegistry(Identifier.ofVanilla("recipe_property_set")), PrimevalMain.getId("open_fire"));

        MELTING = Registry.register(Registries.RECIPE_TYPE, Primeval.identify("melting"), new RecipeType<MeltingRecipe>() {
            @Override
            public String toString() {return "primeval:melting";}
        });
        MELTING_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Primeval.identify("melting"), new MeltingRecipe.Serializer());

        ALLOYING = Registry.register(Registries.RECIPE_TYPE, Primeval.identify("alloying"), new RecipeType<AlloyingRecipe>() {
            @Override
            public String toString() {return "primeval:alloying";}
        });
        ALLOYING_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Primeval.identify("alloying"), new AlloyingRecipe.Serializer());

        QUERN_GRINDING = Registry.register(Registries.RECIPE_TYPE, Primeval.identify("quern_grinding"), new RecipeType<QuernRecipe>() {
            @Override
            public String toString() {return "primeval:quern_grinding";}
        });
        QUERN_GRINDING_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Primeval.identify("quern_grinding"), new QuernRecipe.Serializer());
        QUERN_GRINDING_INPUT = RegistryKey.of(RecipePropertySet.REGISTRY, Primeval.identify("quern_input"));

        MOLD_CASTING = Registry.register(Registries.RECIPE_TYPE, Primeval.identify("mold_casting"), new RecipeType<MoldCastingRecipe>() {
            @Override
            public String toString() {return "primeval:mold_casting";}
        });
        MOLD_CASTING_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Primeval.identify("mold_casting"), new MoldCastingRecipe.Serializer());

        ITEM_DAMAGING = Registry.register(Registries.RECIPE_TYPE, Primeval.identify("item_damaging"), new RecipeType<ItemDamagingRecipe>() {
            @Override
            public String toString() {return "primeval:item_damaging";}
        });
        ITEM_DAMAGING_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Primeval.identify("item_damaging"), new ItemDamagingRecipe.Serializer());
    }

    public static void init() {}

}
