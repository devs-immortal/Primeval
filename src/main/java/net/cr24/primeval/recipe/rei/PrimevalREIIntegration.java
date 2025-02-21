package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.cr24.primeval.Primeval;

public class PrimevalREIIntegration {

    public static final CategoryIdentifier<PitKilnFiringDisplay> PIT_KILN_FIRING = CategoryIdentifier.of(Primeval.identify("pit_kiln_firing"));
    public static final CategoryIdentifier<MeltingDisplay> MELTING = CategoryIdentifier.of(Primeval.identify("melting"));
    //public static final CategoryIdentifier<AlloyingDisplay> ALLOYING = CategoryIdentifier.of(Primeval.identify("alloying"));
//    public static final CategoryIdentifier<OpenFireDisplay> OPEN_FIRE = CategoryIdentifier.of(PrimevalMain.getId("open_fire"));
    public static final CategoryIdentifier<QuernDisplay> QUERN = CategoryIdentifier.of(Primeval.identify("quern"));

}
