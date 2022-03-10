package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.recipe.PitKilnFiringRecipe;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PrimevalREIClientIntegration implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new PitKilnFiringDisplayCategory());

        registry.addWorkstations(PrimevalREIIntegration.PIT_KILN_FIRING, EntryStacks.of(PrimevalItems.STRAW));

        registry.removePlusButton(PrimevalREIIntegration.PIT_KILN_FIRING);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(PitKilnFiringRecipe.class, PrimevalRecipes.PIT_KILN_FIRING, PitKilnFiringDisplay::new);
    }
}
