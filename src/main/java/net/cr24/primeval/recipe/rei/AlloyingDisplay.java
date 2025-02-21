//package net.cr24.primeval.recipe.rei;
//
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import dev.architectury.fluid.FluidStack;
//import me.shedaniel.rei.api.common.category.CategoryIdentifier;
//import me.shedaniel.rei.api.common.display.Display;
//import me.shedaniel.rei.api.common.display.DisplaySerializer;
//import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
//import me.shedaniel.rei.api.common.entry.EntryIngredient;
//import me.shedaniel.rei.api.common.util.EntryIngredients;
//import net.cr24.primeval.recipe.AlloyingRecipe;
//import net.cr24.primeval.recipe.MeltingRecipe;
//import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
//import net.minecraft.network.codec.PacketCodec;
//import net.minecraft.network.codec.PacketCodecs;
//import net.minecraft.recipe.RecipeEntry;
//import net.minecraft.util.Identifier;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//
//public class AlloyingDisplay extends BasicDisplay {
//
//    public static final DisplaySerializer<PitKilnFiringDisplay> SERIALIZER = DisplaySerializer.of(
//            RecordCodecBuilder.mapCodec((instance) -> instance.group(
//                            EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(BasicDisplay::getInputEntries),
//                            EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(BasicDisplay::getOutputEntries),
//                            Identifier.CODEC.optionalFieldOf("location").forGetter(BasicDisplay::getDisplayLocation)
//                    ).apply(instance, PitKilnFiringDisplay::new)
//            ),
//            PacketCodec.tuple(
//                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()), BasicDisplay::getInputEntries,
//                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()), BasicDisplay::getOutputEntries,
//                    PacketCodecs.optional(Identifier.PACKET_CODEC), BasicDisplay::getDisplayLocation,
//                    PitKilnFiringDisplay::new)
//    );
//
//    public AlloyingDisplay(AlloyingRecipe recipe) {
//        HashMap<EntryIngredient, RangedValue> fluidInputs = new HashMap<>();
//        HashMap<FluidVariant, RangedValue> rawFluids = recipe.getFluidInputs();
//        for (FluidVariant f : rawFluids.keySet()) {
//            EntryIngredient ingredient = EntryIngredients.of(FluidStack.create(f.getFluid(), (long) (10000*rawFluids.get(f).getLower())));
//            fluidInputs.put(ingredient, rawFluids.get(f));
//        }
//        this.outputFluid = EntryIngredients.of(FluidStack.create(recipe.getFluidResult().getFluid(), 10000));
//        this.fluidInputs = fluidInputs;
//    }
//
//    public AlloyingDisplay(RecipeEntry<AlloyingRecipe> recipe) {
//        this(Collections.singletonList(EntryIngredients.ofIngredient(recipe.value().getInput())),
//                Collections.singletonList(EntryIngredients.of(FluidStack.create(recipe.value().getFluidResult().getFluid(), recipe.value().getFluidAmount()))),
//                Optional.ofNullable(recipe.id().getValue())
//        );
//    }
//
//    public AlloyingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
//        super(inputs, outputs, location);
//    }
//
//    public HashMap<EntryIngredient, RangedValue> getFluidRatios() {
//        return this.fluidInputs;
//    }
//
//    @Override
//    public List<EntryIngredient> getInputEntries() {
//        return this.fluidInputs.keySet().stream().toList();
//    }
//
//    @Override
//    public List<EntryIngredient> getOutputEntries() {
//        return Collections.singletonList(outputFluid);
//    }
//
//    public final EntryIngredient getOut() {
//        return this.getOutputEntries().get(0);
//    }
//
//    @Override
//    public CategoryIdentifier<?> getCategoryIdentifier() {
//        return PrimevalREIIntegration.ALLOYING;
//    }
//}
