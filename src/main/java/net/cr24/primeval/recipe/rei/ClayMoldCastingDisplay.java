package net.cr24.primeval.recipe.rei;

import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.cr24.primeval.recipe.ClayMoldCastingRecipe;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ClayMoldCastingDisplay implements Display {

    private final EntryIngredient inputMold;
    private final EntryIngredient inputFluid;
    private final EntryIngredient outputItem;

    public ClayMoldCastingDisplay(ClayMoldCastingRecipe recipe) {
        this.inputMold = EntryIngredients.ofIngredient(recipe.getIngredients().get(0));
        this.inputFluid = EntryIngredients.of(FluidStack.create(recipe.getFluid().getFluid(), 9000L));
        this.outputItem = EntryIngredients.ofIngredient(recipe.getOutputItem());
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> list = new LinkedList<>();
        list.add(inputMold);
        list.add(inputFluid);
        return list;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(outputItem);
    }

    public final EntryIngredient getMold() {
        return this.inputMold;
    }

    public final EntryIngredient getFluid() {
        return this.inputFluid;
    }

    public final EntryIngredient getOut() {
        return this.outputItem;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PrimevalREIIntegration.CLAY_MOLD_CASTING;
    }
}
