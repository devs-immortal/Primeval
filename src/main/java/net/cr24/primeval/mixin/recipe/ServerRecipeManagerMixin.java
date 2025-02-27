package net.cr24.primeval.mixin.recipe;

import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.recipe.SimpleOneToOneRecipe;
import net.minecraft.recipe.RecipePropertySet;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.SingleStackRecipe;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(ServerRecipeManager.class)
public class ServerRecipeManagerMixin {
    @Mutable
    @Shadow
    @Final
    private static Map<RegistryKey<RecipePropertySet>, ServerRecipeManager.SoleIngredientGetter> SOLE_INGREDIENT_GETTERS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void initServerRecipeManager(CallbackInfo ci) {
        SOLE_INGREDIENT_GETTERS = new HashMap<>(SOLE_INGREDIENT_GETTERS);
        SOLE_INGREDIENT_GETTERS.put(PrimevalRecipes.QUERN_GRINDING_INPUT, simpleGetter(PrimevalRecipes.QUERN_GRINDING));
        SOLE_INGREDIENT_GETTERS.put(PrimevalRecipes.OPEN_FIRE_INPUT, simpleGetter(PrimevalRecipes.OPEN_FIRE));
        SOLE_INGREDIENT_GETTERS = Collections.unmodifiableMap(SOLE_INGREDIENT_GETTERS);
    }

    @Unique
    private static ServerRecipeManager.SoleIngredientGetter simpleGetter(RecipeType<? extends SimpleOneToOneRecipe> expectedType) {
        return (recipe) -> {
            if (recipe.getType() == expectedType && recipe instanceof SimpleOneToOneRecipe singleStackRecipe) {
                return Optional.of(singleStackRecipe.getInput());
            } else {
                return Optional.empty();
            }
        };
    }
}
