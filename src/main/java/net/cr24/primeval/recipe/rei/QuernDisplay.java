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
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QuernDisplay extends BasicDisplay {

    public static final DisplaySerializer<QuernDisplay> SERIALIZER;
    private final int wheelDamage;

    public QuernDisplay(RecipeEntry<QuernRecipe> recipe) {
        this(Collections.singletonList(EntryIngredients.ofIngredient(recipe.value().getInput())),
                Collections.singletonList(EntryIngredients.of(recipe.value().getResult())),
                Optional.ofNullable(recipe.id().getValue()),
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

    static {
        SERIALIZER = DisplaySerializer.of(
                RecordCodecBuilder.mapCodec((instance) -> instance.group(
                        EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(BasicDisplay::getInputEntries),
                        EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(BasicDisplay::getOutputEntries),
                        Identifier.CODEC.optionalFieldOf("location").forGetter(BasicDisplay::getDisplayLocation),
                        Codec.INT.fieldOf("WheelDamage").forGetter(QuernDisplay::getWheelDamage)
                        ).apply(instance, QuernDisplay::new)
                ),
                PacketCodec.tuple(
                        EntryIngredient.streamCodec().collect(PacketCodecs.toList()), BasicDisplay::getInputEntries,
                        EntryIngredient.streamCodec().collect(PacketCodecs.toList()), BasicDisplay::getOutputEntries,
                        PacketCodecs.optional(Identifier.PACKET_CODEC), BasicDisplay::getDisplayLocation,
                        PacketCodecs.INTEGER, QuernDisplay::getWheelDamage,
                        QuernDisplay::new
                )
        );
    }
}
