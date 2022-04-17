package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.recipe.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PrimevalREIClientIntegration implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new PitKilnFiringDisplayCategory());
        registry.add(new MeltingDisplayCategory());
        registry.add(new AlloyingDisplayCategory());
        registry.add(new ClayMoldCastingDisplayCategory());

        registry.addWorkstations(PrimevalREIIntegration.PIT_KILN_FIRING, EntryStacks.of(PrimevalItems.STRAW));
        registry.addWorkstations(PrimevalREIIntegration.MELTING, EntryStacks.of(PrimevalItems.FIRED_CLAY_VESSEL));
        registry.addWorkstations(PrimevalREIIntegration.ALLOYING, EntryStacks.of(PrimevalItems.FIRED_CLAY_VESSEL));
        registry.addWorkstations(PrimevalREIIntegration.CLAY_MOLD_CASTING, EntryStacks.of(PrimevalItems.FIRED_CLAY_INGOT_MOLD));

        registry.removePlusButton(PrimevalREIIntegration.PIT_KILN_FIRING);
        registry.removePlusButton(PrimevalREIIntegration.MELTING);
        registry.removePlusButton(PrimevalREIIntegration.ALLOYING);
        registry.removePlusButton(PrimevalREIIntegration.CLAY_MOLD_CASTING);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(PitKilnFiringRecipe.class, PrimevalRecipes.PIT_KILN_FIRING, PitKilnFiringDisplay::new);
        registry.registerRecipeFiller(MeltingRecipe.class, PrimevalRecipes.MELTING, MeltingDisplay::new);
        registry.registerRecipeFiller(AlloyingRecipe.class, PrimevalRecipes.ALLOYING, AlloyingDisplay::new);
        registry.registerRecipeFiller(ClayMoldCastingRecipe.class, PrimevalRecipes.CLAY_MOLD_CASTING, ClayMoldCastingDisplay::new);
    }
}
