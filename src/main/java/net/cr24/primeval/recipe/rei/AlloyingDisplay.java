package net.cr24.primeval.recipe.rei;

import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.cooking.DefaultSmeltingDisplay;
import net.cr24.primeval.recipe.AlloyingRecipe;
import net.cr24.primeval.recipe.MeltingRecipe;
import net.cr24.primeval.util.RangedValue;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AlloyingDisplay implements Display {

    private final EntryIngredient outputFluid;
    private final HashMap<EntryIngredient, RangedValue> fluidInputs;

    public AlloyingDisplay(AlloyingRecipe recipe) {
        HashMap<EntryIngredient, RangedValue> fluidInputs = new HashMap<>();
        HashMap<FluidVariant, RangedValue> rawFluids = recipe.getFluidInputs();
        for (FluidVariant f : rawFluids.keySet()) {
            EntryIngredient ingredient = EntryIngredients.of(FluidStack.create(f.getFluid(), (long) (10000*rawFluids.get(f).getLower())));
            fluidInputs.put(ingredient, rawFluids.get(f));
        }
        this.outputFluid = EntryIngredients.of(FluidStack.create(recipe.getFluidResult().getFluid(), 10000));
        this.fluidInputs = fluidInputs;
    }

    public HashMap<EntryIngredient, RangedValue> getFluidRatios() {
        return this.fluidInputs;
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return this.fluidInputs.keySet().stream().toList();
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(outputFluid);
    }

    public final EntryIngredient getOut() {
        return this.getOutputEntries().get(0);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PrimevalREIIntegration.ALLOYING;
    }
}
