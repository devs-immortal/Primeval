package net.cr24.primeval.recipe.rei;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.cr24.primeval.recipe.PitKilnFiringRecipe;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PitKilnFiringDisplay extends BasicDisplay {

    public static final DisplaySerializer<PitKilnFiringDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec((instance) -> instance.group(
                            EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(BasicDisplay::getInputEntries),
                            EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(BasicDisplay::getOutputEntries),
                            Identifier.CODEC.optionalFieldOf("location").forGetter(BasicDisplay::getDisplayLocation)
                    ).apply(instance, PitKilnFiringDisplay::new)
            ),
            StreamCodec.composite(
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), BasicDisplay::getInputEntries,
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), BasicDisplay::getOutputEntries,
                    ByteBufCodecs.optional(Identifier.STREAM_CODEC), BasicDisplay::getDisplayLocation,
                    PitKilnFiringDisplay::new)
    );

    public PitKilnFiringDisplay(RecipeHolder<PitKilnFiringRecipe> recipe) {
        this(Collections.singletonList(EntryIngredients.ofIngredient(recipe.value().getInput())),
                Collections.singletonList(EntryIngredients.of(recipe.value().getResult())),
                Optional.ofNullable(recipe.id().identifier())
        );
    }

    public PitKilnFiringDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    public final EntryIngredient getIn() {
        return this.getInputEntries().get(0);
    }

    public final EntryIngredient getOut() {
        return this.getOutputEntries().get(0);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PrimevalREIIntegration.PIT_KILN_FIRING;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
