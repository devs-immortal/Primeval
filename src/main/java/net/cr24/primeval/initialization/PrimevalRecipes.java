package net.cr24.primeval.initialization;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.recipe.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class PrimevalRecipes {

    public static final RecipeType<PitKilnFiringRecipe> PIT_KILN_FIRING;
    public static final RecipeSerializer<PitKilnFiringRecipe> PIT_KILN_FIRING_SERIALIZER;
    public static final RecipeType<OpenFireRecipe> OPEN_FIRE;
    public static final RecipeSerializer<OpenFireRecipe> OPEN_FIRE_SERIALIZER;
    public static final ResourceKey<RecipePropertySet> OPEN_FIRE_INPUT;
    public static final RecipeType<MeltingRecipe> MELTING;
    public static final RecipeSerializer<MeltingRecipe> MELTING_SERIALIZER;
    public static final RecipeType<AlloyingRecipe> ALLOYING;
    public static final RecipeSerializer<AlloyingRecipe> ALLOYING_SERIALIZER;
    public static final RecipeType<QuernRecipe> QUERN_GRINDING;
    public static final RecipeSerializer<QuernRecipe> QUERN_GRINDING_SERIALIZER;
    public static final ResourceKey<RecipePropertySet> QUERN_GRINDING_INPUT;

    public static final RecipeType<MoldCastingRecipe> MOLD_CASTING;
    public static final RecipeSerializer<MoldCastingRecipe> MOLD_CASTING_SERIALIZER;

    public static final RecipeType<ItemDamagingRecipe> ITEM_DAMAGING;
    public static final RecipeSerializer<ItemDamagingRecipe> ITEM_DAMAGING_SERIALIZER;


    static {
        PIT_KILN_FIRING = Registry.register(BuiltInRegistries.RECIPE_TYPE, Primeval.identify("pit_kiln_firing"), new RecipeType<PitKilnFiringRecipe>() {
            @Override
            public String toString() {return "primeval:pit_kiln_firing";}
        });
        PIT_KILN_FIRING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Primeval.identify("pit_kiln_firing"), new SimpleOneToOneRecipe.Serializer<>(PitKilnFiringRecipe::new));

        OPEN_FIRE = Registry.register(BuiltInRegistries.RECIPE_TYPE, Primeval.identify("open_fire"), new RecipeType<OpenFireRecipe>() {
            @Override
            public String toString() {return "primeval:open_fire";}
        });
        OPEN_FIRE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Primeval.identify("open_fire"), new OpenFireRecipe.Serializer());
        OPEN_FIRE_INPUT = ResourceKey.create(RecipePropertySet.TYPE_KEY, Primeval.identify("open_fire_input"));

        MELTING = Registry.register(BuiltInRegistries.RECIPE_TYPE, Primeval.identify("melting"), new RecipeType<MeltingRecipe>() {
            @Override
            public String toString() {return "primeval:melting";}
        });
        MELTING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Primeval.identify("melting"), new MeltingRecipe.Serializer());

        ALLOYING = Registry.register(BuiltInRegistries.RECIPE_TYPE, Primeval.identify("alloying"), new RecipeType<AlloyingRecipe>() {
            @Override
            public String toString() {return "primeval:alloying";}
        });
        ALLOYING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Primeval.identify("alloying"), new AlloyingRecipe.Serializer());

        QUERN_GRINDING = Registry.register(BuiltInRegistries.RECIPE_TYPE, Primeval.identify("quern_grinding"), new RecipeType<QuernRecipe>() {
            @Override
            public String toString() {return "primeval:quern_grinding";}
        });
        QUERN_GRINDING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Primeval.identify("quern_grinding"), new QuernRecipe.Serializer());
        QUERN_GRINDING_INPUT = ResourceKey.create(RecipePropertySet.TYPE_KEY, Primeval.identify("quern_input"));

        MOLD_CASTING = Registry.register(BuiltInRegistries.RECIPE_TYPE, Primeval.identify("mold_casting"), new RecipeType<MoldCastingRecipe>() {
            @Override
            public String toString() {return "primeval:mold_casting";}
        });
        MOLD_CASTING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Primeval.identify("mold_casting"), new MoldCastingRecipe.Serializer());

        ITEM_DAMAGING = Registry.register(BuiltInRegistries.RECIPE_TYPE, Primeval.identify("item_damaging"), new RecipeType<ItemDamagingRecipe>() {
            @Override
            public String toString() {return "primeval:item_damaging";}
        });
        ITEM_DAMAGING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Primeval.identify("item_damaging"), new ItemDamagingRecipe.Serializer());
    }

    public static void init() {}

}
