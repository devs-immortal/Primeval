package net.cr24.primeval.recipe.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PrimevalREIClientIntegration implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new PitKilnFiringDisplayCategory());
        registry.add(new MeltingDisplayCategory());
        registry.add(new AlloyingDisplayCategory());
//        registry.add(new OpenFireDisplayCategory());
        registry.add(new QuernDisplayCategory());

        registry.addWorkstations(PrimevalREIIntegration.PIT_KILN_FIRING, EntryStacks.of(PrimevalItems.STRAW));
        registry.addWorkstations(PrimevalREIIntegration.MELTING, EntryStacks.of(PrimevalItems.FIRED_CLAY_VESSEL));
        registry.addWorkstations(PrimevalREIIntegration.ALLOYING, EntryStacks.of(PrimevalItems.FIRED_CLAY_VESSEL));
//        registry.addWorkstations(PrimevalREIIntegration.OPEN_FIRE, EntryStacks.of(PrimevalBlocks.CAMPFIRE));
        registry.addWorkstations(PrimevalREIIntegration.QUERN, EntryStacks.of(PrimevalItems.QUERN_WHEEL), EntryStacks.of(PrimevalBlocks.QUERN));
        registry.addWorkstations(CategoryIdentifier.of("minecraft", "plugins/crafting"), EntryStacks.of(PrimevalBlocks.CRUDE_CRAFTING_BENCH));
    }
}
