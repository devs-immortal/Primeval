package net.cr24.primeval.recipe.rei;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.recipe.AlloyingRecipe;
import net.cr24.primeval.util.RangedValue;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AlloyingDisplay extends BasicDisplay {

    Map<EntryIngredient, RangedValue> fluidInputs;

    public static final DisplaySerializer<AlloyingDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Codec.unboundedMap(EntryIngredient.codec(), RangedValue.CODEC).fieldOf("inputs").forGetter(AlloyingDisplay::getFluidRatios),
                    EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(BasicDisplay::getOutputEntries),
                    Identifier.CODEC.optionalFieldOf("location").forGetter(BasicDisplay::getDisplayLocation)
                    ).apply(instance, AlloyingDisplay::new)
            ),
            StreamCodec.composite(
                    ByteBufCodecs.map(HashMap::new, EntryIngredient.streamCodec(), RangedValue.PACKET_CODEC), AlloyingDisplay::getFluidRatios,
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), BasicDisplay::getOutputEntries,
                    ByteBufCodecs.optional(Identifier.STREAM_CODEC), BasicDisplay::getDisplayLocation,
                    AlloyingDisplay::new)
    );

    public AlloyingDisplay(RecipeHolder<AlloyingRecipe> recipe) {
        this(mapFluids(recipe.value().getFluidInputs()),
                Collections.singletonList(EntryIngredients.of(FluidStack.create(recipe.value().getFluidResult().value(), 10000))),
                Optional.ofNullable(recipe.id().identifier())
        );
    }

    private static Map<EntryIngredient, RangedValue> mapFluids(Map<Holder<Fluid>, RangedValue> rawFluids) {
        Map<EntryIngredient, RangedValue> fluidEntries = new HashMap<>();
        for (var entry : rawFluids.entrySet()) {
            var fluid = entry.getKey().value();
            var amount = (long) (entry.getValue().getHalfway() * 10000);
            fluidEntries.put(EntryIngredients.of(fluid, amount), entry.getValue());
        }
        return fluidEntries;
    }

    public AlloyingDisplay(Map<EntryIngredient, RangedValue> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs.keySet().stream().toList(), outputs, location);
        this.fluidInputs = inputs;
    }

    public Map<EntryIngredient, RangedValue> getFluidRatios() {
        return fluidInputs;
    }

    public final EntryIngredient getOut() {
        return this.getOutputEntries().get(0);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PrimevalREIIntegration.ALLOYING;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
