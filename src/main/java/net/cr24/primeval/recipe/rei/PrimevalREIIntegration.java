package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.cr24.primeval.PrimevalMain;

public class PrimevalREIIntegration {

    public static final CategoryIdentifier<PitKilnFiringDisplay> PIT_KILN_FIRING = CategoryIdentifier.of(PrimevalMain.getId("pit_kiln_firing"));
    public static final CategoryIdentifier<MeltingDisplay> MELTING = CategoryIdentifier.of(PrimevalMain.getId("melting"));
    public static final CategoryIdentifier<AlloyingDisplay> ALLOYING = CategoryIdentifier.of(PrimevalMain.getId("alloying"));
    public static final CategoryIdentifier<OpenFireDisplay> OPEN_FIRE = CategoryIdentifier.of(PrimevalMain.getId("open_fire"));

}
