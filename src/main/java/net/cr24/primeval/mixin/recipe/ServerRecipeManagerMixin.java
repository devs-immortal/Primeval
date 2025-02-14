package net.cr24.primeval.mixin.recipe;

import net.cr24.primeval.recipe.PrimevalRecipes;
import net.cr24.primeval.recipe.SimpleOneToOneRecipe;
import net.minecraft.recipe.RecipePropertySet;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.SingleStackRecipe;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(ServerRecipeManager.class)
public abstract class ServerRecipeManagerMixin {

    @Mutable
    @Shadow
    @Final
    private static Map<RegistryKey<RecipePropertySet>, ServerRecipeManager.SoleIngredientGetter> SOLE_INGREDIENT_GETTERS;

    private static ServerRecipeManager.SoleIngredientGetter ingredientGetter(RecipeType<? extends SimpleOneToOneRecipe> expectedType) {
        return (recipe) -> {
            if (recipe.getType() == expectedType && recipe instanceof SimpleOneToOneRecipe singleStackRecipe) {
                return Optional.of(singleStackRecipe.getInput());
            } else {
                return Optional.empty();
            }
        };
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void onServerRecipeManagerInit(CallbackInfo ci) {
        SOLE_INGREDIENT_GETTERS = new HashMap<>(SOLE_INGREDIENT_GETTERS);
        SOLE_INGREDIENT_GETTERS.put(PrimevalRecipes.OPEN_FIRE_INPUT, ingredientGetter(PrimevalRecipes.OPEN_FIRE));
        SOLE_INGREDIENT_GETTERS.put(PrimevalRecipes.QUERN_GRINDING_INPUT, ingredientGetter(PrimevalRecipes.QUERN_GRINDING));
        SOLE_INGREDIENT_GETTERS = Collections.unmodifiableMap(SOLE_INGREDIENT_GETTERS);
    }
}