package net.cr24.primeval.recipe.rei;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.cr24.primeval.recipe.QuernRecipe;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QuernDisplay extends BasicDisplay {

    public static final DisplaySerializer<QuernDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(BasicDisplay::getInputEntries),
                    EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(BasicDisplay::getOutputEntries),
                    Identifier.CODEC.optionalFieldOf("location").forGetter(BasicDisplay::getDisplayLocation),
                    Codec.INT.fieldOf("wheel_damage").forGetter(QuernDisplay::getWheelDamage)
                    ).apply(instance, QuernDisplay::new)
            ),
            StreamCodec.composite(
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), BasicDisplay::getInputEntries,
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), BasicDisplay::getOutputEntries,
                    ByteBufCodecs.optional(Identifier.STREAM_CODEC), BasicDisplay::getDisplayLocation,
                    ByteBufCodecs.INT, QuernDisplay::getWheelDamage,
                    QuernDisplay::new)
    );

    private final int wheelDamage;

    public QuernDisplay(RecipeHolder<QuernRecipe> recipe) {
        this(Collections.singletonList(EntryIngredients.ofIngredient(recipe.value().getInput())),
                Collections.singletonList(EntryIngredients.of(recipe.value().getResult())),
                Optional.ofNullable(recipe.id().identifier()),
                recipe.value().getWheelDamage()
        );
    }

    public QuernDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location, int damage) {
        super(inputs, outputs, location);
        this.wheelDamage = damage;
    }

    public final EntryIngredient getIn() {
        return this.getInputEntries().get(0);
    }

    public final EntryIngredient getOut() {
        return this.getOutputEntries().get(0);
    }

    public final int getWheelDamage() { return this.wheelDamage; }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PrimevalREIIntegration.QUERN;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }

}
