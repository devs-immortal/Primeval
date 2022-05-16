package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.cr24.primeval.recipe.OpenFireRecipe;
import net.cr24.primeval.recipe.PitKilnFiringRecipe;

import java.util.Collections;
import java.util.List;

public class OpenFireDisplay implements Display {

    private final EntryIngredient input;
    private final EntryIngredient output;

    public OpenFireDisplay(OpenFireRecipe recipe) {
        this.input = EntryIngredients.ofIngredient(recipe.getIngredients().get(0));
        this.output = EntryIngredients.of(recipe.getOutput());
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return Collections.singletonList(input);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(output);
    }

    public final EntryIngredient getIn() {
        return this.getInputEntries().get(0);
    }

    public final EntryIngredient getOut() {
        return this.getOutputEntries().get(0);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PrimevalREIIntegration.OPEN_FIRE;
    }
}
