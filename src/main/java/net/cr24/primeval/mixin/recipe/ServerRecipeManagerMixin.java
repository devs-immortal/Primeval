package net.cr24.primeval.mixin.recipe;

import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.recipe.SimpleOneToOneRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(RecipeManager.class)
public class ServerRecipeManagerMixin {
    @Mutable
    @Shadow
    @Final
    private static Map<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor> RECIPE_PROPERTY_SETS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void initServerRecipeManager(CallbackInfo ci) {
        RECIPE_PROPERTY_SETS = new HashMap<>(RECIPE_PROPERTY_SETS);
        RECIPE_PROPERTY_SETS.put(PrimevalRecipes.QUERN_GRINDING_INPUT, simpleGetter(PrimevalRecipes.QUERN_GRINDING));
        RECIPE_PROPERTY_SETS.put(PrimevalRecipes.OPEN_FIRE_INPUT, simpleGetter(PrimevalRecipes.OPEN_FIRE));
        RECIPE_PROPERTY_SETS = Collections.unmodifiableMap(RECIPE_PROPERTY_SETS);
    }

    @Unique
    private static RecipeManager.IngredientExtractor simpleGetter(RecipeType<? extends SimpleOneToOneRecipe> expectedType) {
        return (recipe) -> {
            if (recipe.getType() == expectedType && recipe instanceof SimpleOneToOneRecipe singleStackRecipe) {
                return Optional.of(singleStackRecipe.getInput());
            } else {
                return Optional.empty();
            }
        };
    }
}
