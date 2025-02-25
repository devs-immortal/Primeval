package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.client.categories.crafting.filler.CraftingRecipeFiller;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomShapelessDisplay;
import net.cr24.primeval.item.MoldItem;
import net.cr24.primeval.recipe.MoldCastingRecipe;
import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MoldCastingRecipeFiller implements CraftingRecipeFiller<MoldCastingRecipe> {

    @Override
    public Class<MoldCastingRecipe> getRecipeClass() {
        return MoldCastingRecipe.class;
    }

    @Override
    public Collection<Display> apply(RecipeEntry<MoldCastingRecipe> recipe) {
        MoldItem mold = (MoldItem) recipe.value().getMold().getMatchingItems().findAny().get().value();
        ItemStack filledMold = new ItemStack(mold);
        filledMold.set(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(recipe.value().getFluid(), mold.getCapacity()));
        return List.of(new DefaultCustomShapelessDisplay(
                List.of(EntryIngredients.of(filledMold)),
                List.of(EntryIngredients.of(recipe.value().getResult())),
                Optional.of(recipe.id().getValue())));
    }
}
