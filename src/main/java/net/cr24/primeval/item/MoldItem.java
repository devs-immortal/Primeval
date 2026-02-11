package net.cr24.primeval.item;

import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MoldItem extends WeightedItem {

    final int capacity;
    public final TagKey<Fluid> validFluids;
    private static final int MAX_INSERTION_AMOUNT = 9000;

    public MoldItem(Weight weight, Size size, int capacity, TagKey<Fluid> validFluids, net.minecraft.item.Item.Settings settings) {
        super(weight, size, 1, settings);
        this.capacity = capacity;
        this.validFluids = validFluids;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public static Pair<Boolean, PrimevalDataComponentTypes.FluidContentComponent> insertFluid(PrimevalDataComponentTypes.FluidContentComponent incomingFluid, ItemStack stack) {
        if (stack.getItem() instanceof MoldItem mold) {
            var heldFluid = stack.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(RegistryEntry.of(Fluids.EMPTY), 0));
            int moldCapacity = mold.getCapacity();
            // if fluid is valid to enter and either the mold is empty or it holds the same fluid and still has room
            if (mold.fluidIsValid(incomingFluid.fluid()) && (heldFluid.fluid().value() == Fluids.EMPTY || (heldFluid.fluid() == incomingFluid.fluid() && heldFluid.amount() < moldCapacity))) {
                int amountToInsert = Math.min(moldCapacity - heldFluid.amount(), Math.min(MAX_INSERTION_AMOUNT, incomingFluid.amount()));
                stack.set(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(incomingFluid.fluid(), heldFluid.amount() + amountToInsert));
                int remainingVesselAmount = incomingFluid.amount() - amountToInsert;
                if (remainingVesselAmount == 0) {
                    return new Pair<>(true, null);
                } else {
                    return new Pair<>(true, new PrimevalDataComponentTypes.FluidContentComponent(incomingFluid.fluid(), remainingVesselAmount));
                }
            }
        }
        // indicates could not fill
        return new Pair<>(false, null);
    }

    public boolean fluidIsValid(RegistryEntry<Fluid> inFluid) {
        return inFluid.isIn(validFluids);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        var contents = stack.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(RegistryEntry.of(Fluids.EMPTY), 0));
        if (contents.amount() > 0) {
            textConsumer.accept(
                    (Text.translatable("text.primeval.fluid.contains", contents.amount(), Text.translatable(
                            "block." + contents.fluid().getIdAsString().replace(':', '.')
                    ))).formatted(Formatting.GRAY));
        }
    }
}
