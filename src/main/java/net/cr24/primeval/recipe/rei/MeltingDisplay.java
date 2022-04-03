package net.cr24.primeval.recipe.rei;

import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.cr24.primeval.recipe.MeltingRecipe;

import java.util.Collections;
import java.util.List;

public class MeltingDisplay implements Display {

    private final EntryIngredient input;
    private final EntryIngredient outputFluid;

    public MeltingDisplay(MeltingRecipe recipe) {
        this.input = EntryIngredients.ofIngredient(recipe.getIngredients().get(0));
        this.outputFluid = EntryIngredients.of(FluidStack.create(recipe.getFluidResult().getLeft().getFluid(), recipe.getFluidResult().getRight()));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return Collections.singletonList(input);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(outputFluid);
    }

    public final EntryIngredient getIn() {
        return this.getInputEntries().get(0);
    }

    public final EntryIngredient getOut() {
        return this.getOutputEntries().get(0);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PrimevalREIIntegration.MELTING;
    }
}
