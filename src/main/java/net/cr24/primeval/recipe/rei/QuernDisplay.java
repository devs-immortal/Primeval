package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.cr24.primeval.recipe.QuernRecipe;

import java.util.Collections;
import java.util.List;

public class QuernDisplay implements Display {

    private final EntryIngredient input;
    private final EntryIngredient output;

    private final int wheelDamage;

    public QuernDisplay(QuernRecipe recipe) {
        this.input = EntryIngredients.ofIngredient(recipe.getInput());
        this.output = EntryIngredients.of(recipe.getOutput());
        this.wheelDamage = recipe.getWheelDamage();
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

    public final int getWheelDamage() { return this.wheelDamage; }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PrimevalREIIntegration.QUERN;
    }
}
