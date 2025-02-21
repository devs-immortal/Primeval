package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import net.cr24.primeval.Primeval;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.recipe.QuernRecipe;

public class PrimevalREICommonIntegration implements REICommonPlugin {

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
//        registry.registerRecipeFiller(PitKilnFiringRecipe.class, PrimevalRecipes.PIT_KILN_FIRING, PitKilnFiringDisplay::new);
//        registry.registerRecipeFiller(MeltingRecipe.class, PrimevalRecipes.MELTING, MeltingDisplay::new);
//        registry.registerRecipeFiller(AlloyingRecipe.class, PrimevalRecipes.ALLOYING, AlloyingDisplay::new);
//        registry.registerRecipeFiller(OpenFireRecipe.class, PrimevalRecipes.OPEN_FIRE, OpenFireDisplay::new);
        registry.beginRecipeFiller(QuernRecipe.class).filterType(PrimevalRecipes.QUERN_GRINDING).fill(QuernDisplay::new);
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(Primeval.identify("quern"), QuernDisplay.SERIALIZER);
    }

}
