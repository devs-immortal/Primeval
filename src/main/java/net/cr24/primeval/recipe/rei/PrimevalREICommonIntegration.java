package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.cr24.primeval.Primeval;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.recipe.*;

public class PrimevalREICommonIntegration implements REICommonPlugin {

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        registry.beginRecipeFiller(PitKilnFiringRecipe.class).filterType(PrimevalRecipes.PIT_KILN_FIRING).fill(PitKilnFiringDisplay::new);
        registry.beginRecipeFiller(MeltingRecipe.class).filterType(PrimevalRecipes.MELTING).fill(MeltingDisplay::new);
        registry.beginRecipeFiller(AlloyingRecipe.class).filterType(PrimevalRecipes.ALLOYING).fill(AlloyingDisplay::new);
//        registry.registerRecipeFiller(OpenFireRecipe.class, PrimevalRecipes.OPEN_FIRE, OpenFireDisplay::new);
        registry.beginRecipeFiller(QuernRecipe.class).filterType(PrimevalRecipes.QUERN_GRINDING).fill(QuernDisplay::new);
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(Primeval.identify("pit_kiln_firing"), PitKilnFiringDisplay.SERIALIZER);
        registry.register(Primeval.identify("melting"), MeltingDisplay.SERIALIZER);
        registry.register(Primeval.identify("alloying"), AlloyingDisplay.SERIALIZER);
        registry.register(Primeval.identify("quern"), QuernDisplay.SERIALIZER);
    }

}
