package net.cr24.primeval.recipe.rei;

import dev.architectury.event.CompoundEventResult;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.fluid.FluidSupportProvider;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.cr24.primeval.Primeval;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.item.MoldItem;
import net.cr24.primeval.recipe.*;
import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.util.stream.Stream;

public class PrimevalREICommonIntegration implements REICommonPlugin {

    @Override
    public void registerFluidSupport(FluidSupportProvider support) {
        support.register(entry -> {
            ItemStack stack = entry.getValue();
            Item item = stack.getItem();
            if (stack.has(PrimevalDataComponentTypes.FLUID_CONTENTS)) {
                return CompoundEventResult.interruptTrue(Stream.of(EntryStacks.of(
                        stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS).fluid().value(),
                        item instanceof MoldItem ? ((MoldItem) item).getCapacity() : 9000
                        )));
            }
            return CompoundEventResult.pass();
        });
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        registry.beginRecipeFiller(PitKilnFiringRecipe.class).filterType(PrimevalRecipes.PIT_KILN_FIRING).fill(PitKilnFiringDisplay::new);
        registry.beginRecipeFiller(MeltingRecipe.class).filterType(PrimevalRecipes.MELTING).fill(MeltingDisplay::new);
        registry.beginRecipeFiller(AlloyingRecipe.class).filterType(PrimevalRecipes.ALLOYING).fill(AlloyingDisplay::new);
        registry.beginRecipeFiller(OpenFireRecipe.class).filterType(PrimevalRecipes.OPEN_FIRE).fill(OpenFireDisplay::new);
        registry.beginRecipeFiller(QuernRecipe.class).filterType(PrimevalRecipes.QUERN_GRINDING).fill(QuernDisplay::new);
        new MoldCastingRecipeFiller().registerDisplays(registry);
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(Primeval.identify("pit_kiln_firing"), PitKilnFiringDisplay.SERIALIZER);
        registry.register(Primeval.identify("melting"), MeltingDisplay.SERIALIZER);
        registry.register(Primeval.identify("alloying"), AlloyingDisplay.SERIALIZER);
        registry.register(Primeval.identify("open_fire"), OpenFireDisplay.SERIALIZER);
        registry.register(Primeval.identify("quern"), QuernDisplay.SERIALIZER);
    }

}
